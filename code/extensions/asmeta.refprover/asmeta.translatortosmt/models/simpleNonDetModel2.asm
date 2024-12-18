asm simpleNonDetModel2

import StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain -> MyDomain

definitions:
	domain MyDomain = {1 : 4}

	main rule r_Main =
		choose $x in MyDomain, $y in MyDomain with $x < foo($y) do
			foo($y) := $x

default init s0:
	function foo($x in MyDomain) = $x