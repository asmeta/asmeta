asm choose

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain -> MyDomain

definitions:
	domain MyDomain = {1:4}

	rule r_a($x in MyDomain) =
		var_a($x) := $x + 3

	CTLSPEC ag(var_a(1) = 4)
	CTLSPEC ag(var_a(2) = 4 and var_a(3) = 4 and var_a(4) = 4)

	main rule r_Main = 
		choose $x in MyDomain with $x < 2 do
				r_a[$x]
			ifnone
				var_a(1) := 3

default init s0:
	function var_a($x in MyDomain) = 4
