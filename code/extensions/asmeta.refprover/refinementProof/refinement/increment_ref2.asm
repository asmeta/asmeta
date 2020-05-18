asm increment_ref2

import StandardLibrary

signature:
	controlled x: Integer
	monitored mon: Boolean
	
definitions:

	main rule r_Main =
		if mon then
			if x >= 0 then
				x := x + 1
			else
				x := x - 1 //this update rule is never executed
			endif
		endif

default init s0:
	function x = 0