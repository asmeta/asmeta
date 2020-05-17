#!/bin/bash
#
# external function
function parse_asm {
	timeout 10 java -jar testASM.jar $1 || echo " timed out"
}
export -f parse_asm
#
# find all the asm file and test them
find ../../../../asm_examples  -name "*.asm" -exec bash -c 'parse_asm "$0"' {} \;