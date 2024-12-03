asm nAryFunc

import StandardLibrary

signature:
	dynamic controlled foo: Prod(Boolean, Boolean) -> Integer

definitions:

	main rule r_Main =
		foo(true, true) := foo(true, true) + 1 

default init s0:
	function foo($a in Boolean, $b in Boolean) = 0