//update of a monitored variable--> error

asm monVarUpdate

import ../../../STDL/StandardLibrary

signature:

	monitored m: Integer

definitions:

main rule r_main =	m := 0
