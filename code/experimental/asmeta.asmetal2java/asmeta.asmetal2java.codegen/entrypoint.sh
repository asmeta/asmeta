#!/bin/sh

# This script builds the command-line arguments from environment variables
# and then executes the Java application with those arguments.

ARGS=""

[ -n "$INPUT" ] && ARGS="$ARGS $INPUT"
[ -n "$WORKING_DIR" ] && ARGS="$ARGS $WORKING_DIR"
[ -n "$OUTPUT" ] && ARGS="$ARGS $OUTPUT"
[ -n "$MODE" ] && ARGS="$ARGS $MODE"
[ -n "$COMPILER_VERSION" ] && ARGS="$ARGS $COMPILER_VERSION"
[ -n "$CLEAN" ] && ARGS="$ARGS $CLEAN"
[ -n "$PROPERTIES" ] && ARGS="$ARGS $PROPERTIES"
[ -n "$HELP" ] && ARGS="$ARGS $HELP"

# Execute the Java application with the built arguments
echo "Executing: java -jar /app/asmetal2java.jar $ARGS"
exec java -jar /app/asmetal2java.jar $ARGS
