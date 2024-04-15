asm chooseRefinedRefined2

import StandardLibrary

signature:
	domain ConcrDomRef subsetof Integer
	dynamic controlled x: ConcrDomRef

definitions:
	domain ConcrDomRef = {1:2}

	main rule r_Main =
		choose $x in ConcrDomRef with $x != 2 do
			x := $x

default init s0:
	function x = 1