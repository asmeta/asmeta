asm incrementBoundedAbstract

import StandardLibrary

signature:
	controlled x: Integer
	
definitions:

	invariant inv_invForRef over x: x >= 0 and x <= 10

	main rule r_Main =
		if x != 10 then
			x := x + 1
		endif

default init s0:
	function x = 0