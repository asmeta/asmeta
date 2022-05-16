asm chooseAbstract

import StandardLibrary

signature:
	domain ConcrDomAbs subsetof Integer
	dynamic controlled x: ConcrDomAbs

definitions:
	domain ConcrDomAbs = {1 : 3}
	
	main rule r_Main =
		choose $x in ConcrDomAbs with true do
			x := $x

default init s0:
	function x = 1