asm increment_ref1_refAnotherMon

import StandardLibrary

signature:
	controlled x: Integer
	monitored monA: Boolean
	monitored monB: Boolean
	
definitions:

	main rule r_Main =
		if monA and monB then
			x := x + 1
		endif

default init s0:
	function x = 0