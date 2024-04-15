asm simpleDetModel

import StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain -> Boolean

definitions:
	domain MyDomain = {1 : 4}

	main rule r_Main =
		forall $x in MyDomain with true do
			if $x mod 2 = 0 then
				foo($x) := not(foo($x))
			endif

default init s0:
	function foo($x in MyDomain) = false