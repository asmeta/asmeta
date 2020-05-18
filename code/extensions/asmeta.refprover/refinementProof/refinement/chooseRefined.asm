asm chooseRefined

import StandardLibrary

signature:
	domain ConcrDomRef subsetof Integer
	dynamic controlled x: ConcrDomRef

definitions:
	domain ConcrDomRef = {1..2}

	main rule r_Main =
		choose $x in ConcrDomRef with true do
			x := $x

default init s0:
	function x = 1