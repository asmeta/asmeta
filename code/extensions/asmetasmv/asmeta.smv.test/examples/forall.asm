asm forall

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain -> MyDomain
	
definitions:
	domain MyDomain = {1:10}

	rule r_a($x in MyDomain) =
		var_a($x) := $x + 2
	
	CTLSPEC ag(var_a(1)<var_a(2))	

	main rule r_Main = 
		forall $x in MyDomain with $x < 4 do
			r_a[$x]
		
default init s0:
	function var_a($x in  MyDomain) = $x
