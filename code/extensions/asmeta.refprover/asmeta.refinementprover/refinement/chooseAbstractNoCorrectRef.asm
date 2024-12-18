asm chooseAbstractNoCorrectRef

import StandardLibrary

signature:
	domain ConcrDomAbs subsetof Integer
	dynamic controlled x: Integer

definitions:
	domain ConcrDomAbs = {1:2}
	
	main rule r_Main =
		choose $x in ConcrDomAbs with true do
			x := $x

default init s0:
	function x = 1