//in una derived non ci puo' essere una choose rule?
asm derivedChoose

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain
	derived der_func: MyDomain

definitions:
	domain MyDomain = {1:4}

	function der_func =
/*NO RULE		choose $x in MyDomain with $x<3 	$x*/
//		chooseone({$x in MyDomain| $x < 3: $x})
		chooseone({$x in MyDomain| $x < foo: $x})
	main rule r_Main =
		foo := der_func
		
default init s0:
