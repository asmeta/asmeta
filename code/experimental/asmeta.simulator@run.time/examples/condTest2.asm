asm condTest2

import StandardLibrary

signature:
	controlled cond: Boolean

definitions:
	
	main rule r_Main =
		if cond = true then
			cond := false
		else
			cond := true
		endif
	
default init s0:
	function cond = true