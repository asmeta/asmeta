asm choose2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled var_a : Prod(MyDomain, MyDomain) -> MyDomain
	dynamic controlled var_b : MyDomain
	
definitions:
	domain MyDomain = {1..4}

	rule r_a($x in MyDomain, $y in MyDomain) =
		var_b := 3

	CTLSPEC ag( var_b = 2  )

	main rule r_Main = 
		choose $x in MyDomain, $y in MyDomain  with $x < 3 and $y > 4 do
				r_a[$x, $y]
			ifnone
				var_b := 2
		
default init s0:
	function var_b = 2
