asm forall2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : Prod(MyDomain, MyDomain) -> MyDomain
	dynamic monitored var_b: MyDomain	

definitions:
	domain MyDomain = {1:10}

	rule r_a($x in MyDomain, $y in MyDomain) =
		var_a($x, $y) := $y - $x
	
	CTLSPEC ag(var_a(1,1)<var_a(2,2))	

	main rule r_Main = 
		forall $x in MyDomain with $x < 4 do
			forall $y in MyDomain with $y > 7 do
				r_a[$x, $y]

default init s0:
	function var_a($x in  MyDomain, $y in  MyDomain) = $x
