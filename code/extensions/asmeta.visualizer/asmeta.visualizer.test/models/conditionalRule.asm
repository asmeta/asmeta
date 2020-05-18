asm conditionalRule

import StandardLibrary

signature:
	controlled x: Integer

definitions:

	main rule r_Main =
		if x > 10 then
			x := 10
		else
			x := 6
		endif

default init s0:
	function x = 5