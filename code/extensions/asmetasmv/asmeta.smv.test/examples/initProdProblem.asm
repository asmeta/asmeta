asm initProdProblem

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: Prod(MyDomain, MyDomain) -> MyDomain
	
definitions:
	domain MyDomain = {1:10}
	
	CTLSPEC (forall $x in MyDomain with foo($x, $x) = 8) 
	CTLSPEC ax(ag((forall $x in MyDomain, $y in MyDomain
						with ($x!=$y) implies (isUndef(foo($x, $y))))))
	CTLSPEC ag((forall $x in MyDomain with foo($x, $x) = 8))
	
	main  rule r_Main = 
		forall $x in MyDomain, $y in MyDomain with $x!=$y do
			foo($x, $y) := undef

default init s0:
	function foo($x in MyDomain, $y in MyDomain) = 8