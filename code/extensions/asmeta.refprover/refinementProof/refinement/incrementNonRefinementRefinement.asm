asm incrementNonRefinementRefinement

import StandardLibrary

signature:
	controlled x: Integer
	
definitions:

	main rule r_Main =
		x := x + 1

default init s0:
	function x = 0