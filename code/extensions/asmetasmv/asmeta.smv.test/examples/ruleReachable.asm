asm ruleReachable

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : MyDomain -> MyDomain
	
definitions:
	domain MyDomain = {1:10}

	rule r_a($x in MyDomain) =
		var_a($x) := $x + 2
	rule r_b =
		var_a(1) := 3
	
	invariant over var_a: var_a(1)<var_a(2)	

	main rule r_Main =
		par
			forall $x in MyDomain with $x < 4 do
				r_a[$x]
			if(false) then
				r_b[]
			endif
		endpar
		
default init s0:
	function var_a($x in  MyDomain) = $x
