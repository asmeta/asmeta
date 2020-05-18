asm incrementRefNeedInvariant

import StandardLibrary

signature:
	controlled x: Integer
	
definitions:

	main rule r_Main =
		if x > 0 then
			x := x + 1
		else
			x := 1
		endif

default init s0:
	function x = 0