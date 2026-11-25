#!/usr/bin/env python3
"""Analyze each model suite in a fresh JVM, with 16 GiB of heap (Python 3.8+).

Place beside run.sh, experiments-runner.jar, asm_examples/ and scenarios/.
Run without arguments: python3 parallel_analysis.py

SCENARIOS_DIR selects the source folder, relative to this script or absolute.
Keep asm_examples/ beside that folder; run.sh and JARs stay beside this script.
APPROACHES = None uses all subfolders; a list of names selects specific ones.
Those approaches run in
parallel; within each approach, model-suite subdirectories run sequentially.
Each suite gets its own Java process, so a failed suite does not stop the next.
A new analysis_results_* directory contains the combined data.csv and
analysis.log, plus each suite's CSV and log. Failures are described in the log;
partial results from failed processes are excluded from the combined CSV.
An analyzed_scenarios_* directory beside the source folder preserves the resulting
suite folders, including metadata and the Avalla files left by AnalysisRunner.
Its directory depth matches the originals so relative model paths still work.
Failed runs also preserve their suite as it was left, possibly partly filtered;
consult analysis.log before treating those suites as fully validated.
Original scenarios and existing results are preserved. Each invocation starts
an independent run; previous results are not used to skip suites.
"""

import csv
import os
from pathlib import Path
import re
import shutil
import subprocess
import sys
import tempfile
import threading
import time
from concurrent.futures import ThreadPoolExecutor, as_completed


SCENARIOS_DIR = "check_opt_scenarios"
APPROACHES = None  # None means all subfolders, for specific subfolders use for instance ["atgt", "random_pick"].


def read_result(path):
    with path.open(encoding="utf-8-sig", newline="") as handle:
        reader = csv.reader(handle)
        header = next(reader, [])
        rows = list(reader)
    if "status" not in header or len(rows) != 1 or len(rows[0]) != len(header):
        raise ValueError("Expected exactly one complete result row with a status column")
    if not rows[0][header.index("status")]:
        raise ValueError("Empty analysis status")
    return header, rows[0]


def run_suite(work, approach, suite, output):
    started = time.monotonic()
    output.mkdir()
    status, details, result = "RUNNER_ERROR", "", None
    try:
        # A startup failure must not reuse the preceding suite's CSV or log.
        for name in ("analysis.log", "data.csv", "temp.csv"):
            (work / name).unlink(missing_ok=True)
        shutil.copytree(suite, work / approach / suite.name)
        env = os.environ.copy()
        env.pop("_JAVA_OPTIONS", None)
        env.pop("JDK_JAVA_OPTIONS", None)
        env["JAVA_TOOL_OPTIONS"] = "-Xmx16g"
        # Shell diagnostics and Java's redirected log go into the same saved log.
        with (output / "analysis.log").open("w", encoding="utf-8") as log:
            try:
                process = subprocess.run(["bash", "run.sh", "analysis", "."],
                                         cwd=str(work), env=env, stdout=log,
                                         stderr=subprocess.STDOUT)
            finally:
                if (work / "analysis.log").exists():
                    with (work / "analysis.log").open(encoding="utf-8", errors="replace") as java_log:
                        shutil.copyfileobj(java_log, log)
        if (work / "data.csv").exists():
            shutil.copy2(work / "data.csv", output / "data.csv")
        oom = completed = False
        errors = 0
        with (output / "analysis.log").open(encoding="utf-8", errors="replace") as log:
            for line in log:
                oom |= "OutOfMemoryError" in line
                completed |= "Analysis completed." in line
                errors += bool(re.search(r"\bERROR\b", line))
        if oom:
            status, details = "OUT_OF_MEMORY", "OutOfMemoryError found in log"
        elif process.returncode != 0:
            status, details = "PROCESS_ERROR", f"run.sh exited with {process.returncode}"
        elif not completed:
            status, details = "INCOMPLETE", "Analysis completion marker missing"
        else:
            result = read_result(output / "data.csv")
            status = result[1][result[0].index("status")]
            if errors and status == "OK":
                status, details = "LOG_ERRORS", f"{errors} ERROR messages despite OK result"
    except Exception as error:
        details = f"{type(error).__name__}: {error}"
        with (output / "analysis.log").open("a", encoding="utf-8") as log:
            log.write(f"\nRUNNER_ERROR: {details}\n")
    elapsed = time.monotonic() - started
    return status, details, elapsed, result


class CombinedResults:
    """Publish whole suite logs and CSV rows without interleaving worker writes."""

    def __init__(self, log, data):
        self.log = log
        self.data = data
        self.writer = csv.writer(data)
        self.header = None
        self.lock = threading.Lock()

    def note(self, message):
        with self.lock:
            self.log.write(message + "\n")
            self.log.flush()

    def publish(self, approach, suite, output, outcome):
        status, details, elapsed, result = outcome
        with self.lock:
            self.log.write(f"\n=== {approach}/{suite.name}: {status} ({elapsed:.1f}s) {details} ===\n")
            with (output / "analysis.log").open(encoding="utf-8", errors="replace") as source:
                shutil.copyfileobj(source, self.log)
            if result is not None:
                header, row = result
                if self.header is None:
                    self.header = header
                    self.writer.writerow(header)
                if header != self.header:
                    self.log.write("\nRUNNER_ERROR: incompatible CSV header; row omitted\n")
                    status = "RUNNER_ERROR"
                else:
                    self.writer.writerow(row)
                    self.data.flush()
            self.log.write(f"\n=== END {approach}/{suite.name} ===\n")
            self.log.flush()
        print(f"{approach}/{suite.name}: {status}", flush=True)
        return status not in ("OK", "NO_VALID_SCENARIOS")


def run_approach(base, scenarios, approach, results, analyzed, combined):
    output = results / approach
    output.mkdir()
    saved_approach = analyzed / approach
    saved_approach.mkdir()
    problems = 0
    # Sibling of the source folder: preserve ../../../asm_examples references.
    with tempfile.TemporaryDirectory(prefix=".analysis_worker_", dir=str(scenarios.parent)) as temporary:
        work = Path(temporary)
        shutil.copy2(base / "run.sh", work / "run.sh")
        for jar in base.glob("*.jar"):
            shutil.copy2(jar, work / jar.name)
        (work / approach).mkdir()
        suites = sorted(p for p in (scenarios / approach).iterdir() if p.is_dir())
        for suite in suites:
            suite_output = output / suite.name
            outcome = run_suite(work, approach, suite, suite_output)
            # Save the actual post-analysis tree, including Java's deletions.
            # Move it out before the temporary workspace is cleaned up. A separate
            # tree avoids collisions with per-suite analysis.log/data.csv files.
            copy = work / approach / suite.name
            if copy.exists():
                destination = saved_approach / suite.name
                assert copy.resolve().parent == (work / approach).resolve()
                assert destination.resolve().parent == saved_approach.resolve()
                if destination.exists():
                    raise FileExistsError(destination)
                shutil.move(str(copy), str(destination))
                with (suite_output / "analysis.log").open("a", encoding="utf-8") as log:
                    log.write(f"\nSaved resulting scenario suite: {destination}\n")
            problems += combined.publish(approach, suite, suite_output, outcome)
    return problems


def main():
    if len(sys.argv) != 1:
        raise ValueError("This script takes no arguments; run python3 parallel_analysis.py")
    base = Path(__file__).resolve().parent
    scenarios = (base / SCENARIOS_DIR).resolve()
    if not scenarios.is_dir():
        raise FileNotFoundError(scenarios)
    approaches = (sorted(p.name for p in scenarios.iterdir() if p.is_dir())
                  if APPROACHES is None else list(APPROACHES))
    if not approaches or len(set(approaches)) != len(approaches):
        raise ValueError("Expected at least one approach with distinct directory names")
    for approach in approaches:
        if Path(approach).name != approach or approach in (".", ".."):
            raise ValueError(f"Invalid approach directory name: {approach}")
    for filename in ("run.sh", "experiments-runner.jar"):
        if not (base / filename).is_file():
            raise FileNotFoundError(base / filename)
    for directory in [scenarios.parent / "asm_examples"] + [scenarios / a for a in approaches]:
        if not directory.is_dir():
            raise FileNotFoundError(directory)
    if not shutil.which("bash") or not shutil.which("java"):
        raise RuntimeError("bash and java must be available in PATH")

    results = Path(tempfile.mkdtemp(prefix="analysis_results_", dir=str(base)))
    analyzed = scenarios.parent / results.name.replace("analysis_results_", "analyzed_scenarios_", 1)
    analyzed.mkdir()
    print(f"Results: {results}", flush=True)
    print(f"Resulting scenario suites: {analyzed}", flush=True)
    problems = 0
    with (results / "analysis.log").open("w", encoding="utf-8") as log:
        with (results / "data.csv").open("w", encoding="utf-8", newline="") as data:
            combined = CombinedResults(log, data)
            combined.note(f"Resulting scenario suites: {analyzed}")
            with ThreadPoolExecutor(max_workers=len(approaches)) as executor:
                futures = {executor.submit(run_approach, base, scenarios, a, results, analyzed, combined): a for a in approaches}
                for future in as_completed(futures):
                    try:
                        problems += future.result()
                    except Exception as error:
                        problems += 1
                        message = f"RUNNER_ERROR {futures[future]}: {type(error).__name__}: {error}"
                        combined.note(message)
                        print(message, file=sys.stderr, flush=True)
            combined.note(f"Problems: {problems}")
    print(f"Problems: {problems}. Combined CSV and log: {results}", flush=True)
    return 1 if problems else 0


if __name__ == "__main__":
    try:
        sys.exit(main())
    except (OSError, ValueError, RuntimeError) as error:
        print(f"Error: {error}", file=sys.stderr)
        sys.exit(1)
