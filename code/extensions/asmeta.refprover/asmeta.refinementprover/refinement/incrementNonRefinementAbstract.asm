asm incrementNonRefinementAbstract

import StandardLibrary

signature:
	controlled x: Integer
	controlled stop: Boolean
	
definitions:

	main rule r_Main =
		if stop then
			stop := false
		else
			par
				x := x + 1
				stop := true
			endpar
		endif

default init s0:
	function x = 0
	function stop = true