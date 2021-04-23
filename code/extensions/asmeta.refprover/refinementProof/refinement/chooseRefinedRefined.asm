//correct refinement of chooseRefined
asm chooseRefinedRefined

import StandardLibrary

signature:
	domain ConcrDomRef subsetof Integer
	dynamic controlled x: ConcrDomRef

definitions:
	domain ConcrDomRef = {1:2}

	main rule r_Main =
		x := 1

default init s0:
	function x = 1