#!/usr/bin/env python3
"""Create randomly reduced copies of original test suites (Python 3.8+).

Run without arguments: python3 random_check_removal.py
Use --seed 42 to reproduce the same random removals.
Use --remove-all to remove every check without reading OPTIMIZED_DIR.

ORIGINAL_DIR and OPTIMIZED_DIR select the input folders, relative to this
script or absolute. All approach subfolders are discovered automatically;
approach and model-suite folders must have matching names in both inputs.
By default, each test is paired with its *_optimized.avalla or same-named file.
Its copy contains exactly as many checks as the optimized test, chosen by
randomly removing complete check lines. Assumes one check per line.
OUTPUT_DIR preserves the approach/suite layout and includes
random_check_removal.log with check counts, skipped suites, and errors.
Suites with invalid or missing test pairs are skipped; processing continues.
Original scenarios are preserved and existing output directories are refused.
Keep the output at the same depth as the inputs so relative ASM paths work.
"""

import argparse
import logging
from pathlib import Path
import random
import re
import shutil
import sys


# Input/output roots, each containing one subdirectory per approach.
ORIGINAL_DIR = "scenarios"
OPTIMIZED_DIR = "check_opt_scenarios"
OUTPUT_DIR = "random_check_scenarios"

CHECK_LINE = re.compile(r"^[ \t]*check[ \t]+")


def check_lines(text):
    """Return check line indices in the generated, one-command-per-line format."""
    return [i for i, line in enumerate(text.splitlines(keepends=True))
            if CHECK_LINE.match(line)]


def remove_checks(text, indices, count, rng):
    chosen = set(rng.sample(indices, count))
    return "".join(line for i, line in enumerate(text.splitlines(keepends=True))
                   if i not in chosen)


def read_scenario(path):
    with path.open(encoding="utf-8", newline="") as handle:
        return handle.read()


def plan_suite(original, optimized=None):
    """Plan check counts before copying; optimized=None removes all checks."""
    tests = sorted(original.rglob("*.avalla"))
    plan = []
    for test in tests:
        relative = test.relative_to(original)
        text = read_scenario(test)
        indices = check_lines(text)
        remaining = 0
        if optimized is not None:
            candidates = [optimized / relative.with_name(test.stem + "_optimized.avalla"),
                          optimized / relative]
            matches = [p for p in candidates if p.is_file()]
            if len(matches) != 1:
                raise ValueError(f"Expected one optimized counterpart for {relative}, found {len(matches)}")
            remaining = len(check_lines(read_scenario(matches[0])))
        if remaining > len(indices):
            raise ValueError(f"{relative}: optimized has more checks ({remaining} > {len(indices)})")
        plan.append((relative, text, indices, remaining))
    return plan


def main(argv=None):
    base = Path(__file__).resolve().parent
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--seed", type=int, help="Reproducible random seed (default: generated and printed)")
    parser.add_argument("--remove-all", action="store_true", help="Remove all checks; OPTIMIZED_DIR is not used")
    args = parser.parse_args(argv)
    resolve = lambda p: (base / p).resolve()
    original, output = map(resolve, (ORIGINAL_DIR, OUTPUT_DIR))
    optimized = None if args.remove_all else resolve(OPTIMIZED_DIR)
    if original == optimized:
        raise ValueError("Original and optimized directories must be distinct")
    for directory in ((original,) if args.remove_all else (original, optimized)):
        if not directory.is_dir():
            raise FileNotFoundError(f"{directory} is not a directory")
        if output == directory or directory in output.parents or output in directory.parents:
            raise ValueError("Output must be separate from input directories")
    if output.exists():
        raise FileExistsError(f"Output already exists: {output}")
    seed = args.seed if args.seed is not None else random.SystemRandom().randrange(2**63)
    rng = random.Random(seed)
    errors, approaches, copied, removed = 0, 0, 0, 0
    output.mkdir(parents=True)
    with (output / "random_check_removal.log").open("w", encoding="utf-8") as log:
        logger = logging.Logger("RandomCheckRemoval", level=logging.INFO)
        formatter = logging.Formatter("%(relativeCreated)-5d [main] %(levelname)-5s %(name)s  - %(message)s")
        for stream in (log, sys.stdout):
            handler = logging.StreamHandler(stream)
            handler.setFormatter(formatter)
            logger.addHandler(handler)
        logger.info("Starting check removal: %s", "all checks" if args.remove_all else "random checks")
        logger.info("Original suites: %s", original)
        if not args.remove_all:
            logger.info("Optimized suites: %s", optimized)
        logger.info("Output: %s", output)
        if not args.remove_all:
            logger.info("Seed: %s", seed)
        try:
            for approach in sorted(p for p in original.iterdir() if p.is_dir()):
                optimized_approach = None if args.remove_all else optimized / approach.name
                if optimized_approach is not None and not optimized_approach.is_dir():
                    errors += 1
                    logger.error("Skipping approach %s: missing directory %s", approach.name, optimized_approach)
                    continue
                approaches += 1
                (output / approach.name).mkdir()
                logger.info("Processing approach %s", approach.name)
                for suite in sorted(p for p in approach.iterdir() if p.is_dir()):
                    suite_name = f"{approach.name}/{suite.name}"
                    logger.info("Processing %s", suite_name)
                    try:
                        optimized_suite = None if args.remove_all else optimized_approach / suite.name
                        plan = plan_suite(suite, optimized_suite)
                    except (OSError, ValueError) as error:
                        errors += 1
                        logger.error("Skipping %s: %s", suite_name, error)
                        continue
                    if not plan:
                        logger.info("Skipping %s: no Avalla scenarios", suite_name)
                        continue
                    destination = output / approach.name / suite.name
                    shutil.copytree(suite, destination)
                    suite_removed = 0
                    for relative, text, indices, remaining in plan:
                        count = len(indices) - remaining
                        reduced = remove_checks(text, indices, count, rng)
                        with (destination / relative).open("w", encoding="utf-8", newline="") as handle:
                            handle.write(reduced)
                        suite_removed += count
                        logger.info("%s/%s: %d checks -> %d checks (%d removed)",
                                    suite_name, relative, len(indices), remaining, count)
                    removed += suite_removed
                    copied += 1
                    logger.info("Copied %s: %d tests, %d checks removed", suite_name, len(plan), suite_removed)
        except (OSError, ValueError) as error:
            logger.error("Check removal failed: %s", error)
            raise
        logger.info("Check removal completed. Approaches: %d; suites: %d; checks removed: %d; errors: %d",
                    approaches, copied, removed, errors)
    return 1 if errors else 0


if __name__ == "__main__":
    try:
        sys.exit(main())
    except (OSError, ValueError) as error:
        print(f"Error: {error}", file=sys.stderr)
        sys.exit(1)
