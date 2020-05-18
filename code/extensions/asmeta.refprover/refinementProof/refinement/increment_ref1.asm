asm increment_ref1

import StandardLibrary

signature:
	controlled x: Integer
	monitored mon: Boolean
	
definitions:

	main rule r_Main =
		if mon then
			x := x + 1
		endif

default init s0:
	function x = 0