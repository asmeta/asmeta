ASM_DIR="asm_examples"
BUDGET=10

while true; do
  echo "Starting fm-experiments-runner..."
  java -Xmx4g -jar fm-experiments-runner.jar "$ASM_DIR" "$BUDGET" >> log.txt 
  EXIT_CODE=$?

  echo "fm-experiments-runner exited with code $EXIT_CODE"

  # If it finished normally, break out
  if [ $EXIT_CODE -eq 0 ]; then
    echo "Run completed successfully. Exiting watcher."
    break
  fi

  # Otherwise wait a bit and restart
  echo "Run failed, restarting..."
  sleep 5
done