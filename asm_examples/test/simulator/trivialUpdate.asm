asm trivialUpdate

import ../../STDL/StandardLibrary

signature:
	dynamic controlled foo: Integer

definitions:

	main rule r_Main =
		if foo < 4 then
			foo := foo + 1
		else
			foo := 4
		endif

default init s0:
	function foo = 0