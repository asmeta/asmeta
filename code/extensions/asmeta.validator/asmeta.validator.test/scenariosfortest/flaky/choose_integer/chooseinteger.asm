asm chooseinteger
import ../StandardLibrary

signature:
	domain BoundedDomain subsetof Integer
	dynamic controlled int1: BoundedDomain
	dynamic controlled int2: Integer

definitions:
	domain BoundedDomain = {10}

	main rule r_main =
		par
			choose $x1 in {10} with true do int1 := $x1
			choose $x2 in BoundedDomain with true do int2 := $x2
		endpar

default init s0:
	function int1 = 10
	function int2 = 10
