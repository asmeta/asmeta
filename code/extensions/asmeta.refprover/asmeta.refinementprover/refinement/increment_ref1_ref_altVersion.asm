asm increment_ref1_ref_altVersion

//refinement of increment_ref1
//alternative version of increment_ref1_ref
//the function "mon" becomes derived 

import StandardLibrary

signature:
	controlled x: Integer
	monitored monMon: Boolean
	derived mon: Boolean
	
definitions:
	function mon = not monMon

	main rule r_Main =
		if not monMon then
			x := x + 1
		endif

default init s0:
	function x = 0