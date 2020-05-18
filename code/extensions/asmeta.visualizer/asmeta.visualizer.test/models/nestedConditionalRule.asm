asm nestedConditionalRule

import StandardLibrary

signature:
	controlled x: Integer

definitions:

	main rule r_Main =
		if x > 10 then
			x := 10
		else
			if x > 10 then
				x := 11
			else
				x := 6
			endif
		endif

default init s0:
	function x = 5