#!/bin/sh

# This script builds the command-line arguments from environment variables
# and then executes the Java application with those arguments.

ARGS=""

[ -n "$INPUT" ] && ARGS="$ARGS $INPUT"
[ -n "$WORKING_DIR" ] && ARGS="$ARGS $WORKING_DIR"
[ -n "$OUTPUT" ] && ARGS="$ARGS $OUTPUT"
[ -n "$CLEAN" ] && ARGS="$ARGS $CLEAN"
[ -n "$PARSER" ] && ARGS="$ARGS $PARSER"
[ -n "$HELP" ] && ARGS="$ARGS $HELP"

# Execute the Java application with the built arguments
echo "Executing: java -jar /app/junit2avalla.jar $ARGS"
exec java -jar /app/junit2avalla.jar $ARGS
