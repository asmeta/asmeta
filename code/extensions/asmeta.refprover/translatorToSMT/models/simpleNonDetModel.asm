asm simpleNonDetModel

import StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain -> Boolean

definitions:
	domain MyDomain = {1..4}

	main rule r_Main =
		choose $x in MyDomain with true do
			foo($x) := not(foo($x))

default init s0:
	function foo($x in MyDomain) = false