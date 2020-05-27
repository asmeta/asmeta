asm macroChoose

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain -> MyDomain
	
definitions:
	domain MyDomain = {1:4}
	
	rule r_b($y in MyDomain) =
		foo($y) := $y
	
	rule r_a($x in MyDomain) =
		r_b[$x]
	
	main rule r_Main =
		choose $a in MyDomain with true do
			r_a[$a]

default init s0:
	function foo($a in MyDomain) = 1
