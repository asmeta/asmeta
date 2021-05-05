asm chooseRefinedNoCorrectRef

import StandardLibrary

//This is not a correct refinement of chooseAbstractNoCorrectRef
//because in the abstract machine "x" can never become 3
signature:
	domain ConcrDomRef subsetof Integer
	dynamic controlled x: Integer

definitions:
	domain ConcrDomRef = {1 : 3}

	main rule r_Main =
		choose $x in ConcrDomRef with true do
			x := $x

default init s0:
	function x = 1