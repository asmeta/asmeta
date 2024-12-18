asm incrementBoundedRefined

import StandardLibrary

signature:
	controlled x: Integer
	
definitions:

	invariant inv_invForRef over x: x >= 0 and x <= 5

	main rule r_Main =
		if x != 5 then
			x := x + 1
		endif

default init s0:
	function x = 0