/** 
*/
asm InconsistentUpdate

import STDL/StandardLibrary

signature:
	domain Hour subsetof Integer
	controlled hours: Hour

definitions:
	domain Hour = {0:23}


	main rule r_Main = 
		par
			if hours > 2 then hours := hours -1 endif
			hours := hours + 1
		endpar

default init s0:
	function hours = 0
