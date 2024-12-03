asm choose4

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : Prod(MyDomain, MyDomain) -> MyDomain

definitions:
	domain MyDomain = {1:4}

	rule r_a($x in MyDomain, $y in MyDomain) =
		var_a($x, $y) := $y - $x

	CTLSPEC ag((var_a(1,2) = 1 and var_a(1,3) = 2 and var_a(1,4) = 3 and
			var_a(2,3) = 1 and var_a(2,4) = 2 and var_a(3,4) = 1) iff 
			ag(var_a(1,2) = 1 and var_a(1,3) = 2 and var_a(1,4) = 3 and
			var_a(2,3) = 1 and var_a(2,4) = 2 and var_a(3,4) = 1))

	main rule r_Main =
		forall $y in MyDomain do
			choose $x in MyDomain with $x < $y do
				var_a($x, $y) := $y - $x
		
default init s0:
