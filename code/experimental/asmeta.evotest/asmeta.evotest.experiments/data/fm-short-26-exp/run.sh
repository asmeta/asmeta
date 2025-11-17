#!/usr/bin/env bash

# ---------------------------------------------------------------
# This script runs fm-experiments-runner.jar on each ASM listed
# in model_list.txt, but always in single-file mode.
#
# Purpose:
#   - Avoids the "too many open files" error by running models
#     one-by-one in separate JVM processes.
#   - Reproduces the cleanup behavior of the original folder mode.
#
# Usage:
#   ./run_all.sh <asm_examples_folder> <time_budget_seconds>
#
# Example:
#   ./run_all.sh asm_examples 60
# ---------------------------------------------------------------

# Read command-line arguments
ASM_ROOT="$1"      # absolute path to asm_examples
BUDGET="$2"        # time budget in seconds
JAR="fm-experiments-runner.jar"

# These must match the names used inside the Java program.
MODEL_LIST="model_list.txt"
TARGET_DIR="scenarios"
RESULTS_CSV="result.csv"

echo "== Cleaning previous results =="
rm -rf "${TARGET_DIR}"
rm -f "${RESULTS_CSV}"
mkdir -p "${TARGET_DIR}"
touch "${RESULTS_CSV}"

echo "== Starting batch execution =="
spec_counter=0

# Read each line from model_list.txt
while IFS= read -r line; do
	# Strip possible Windows carriage return at end of line
    line="${line%$'\r'}"

    # Skip empty lines
    [[ -z "$line" ]] && continue

    # Skip commented lines beginning with //
    [[ "$line" == //* ]] && continue

    # Build absolute path to the ASM file
    normalized_line="${line//\\//}"
    asm_path="${ASM_ROOT}/${normalized_line}"

    # Ensure the file exists
    if [[ ! -f "$asm_path" ]]; then
        echo "Warning: ASM not found: $asm_path (skipping)"
        ((spec_counter++))
        continue
    fi

    echo "--------------------------------------------------"
    echo "Running ASM #${spec_counter}: $asm_path"

    # Run the jar in single-file mode:
    #   java -jar <jar> <asm-path> <budget> <numerical-prefix>
    java -jar "${JAR}" "${asm_path}" "${BUDGET}" "${spec_counter}" >> log.txt 2>&1 || true

    ((spec_counter++))

done < "${MODEL_LIST}"

echo "== All models processed =="
