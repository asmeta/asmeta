asm condTest

import StandardLibrary

signature:
	controlled cond: Boolean

definitions:
	
	main rule r_Main =
		cond := false
	
default init s0:
	function cond = true