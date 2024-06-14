asm forall3

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain -> MyDomain
	
definitions:
	domain MyDomain = {1:3}

	CTLSPEC ag(foo(1) = 1 and foo(2)=2 and foo(3)=3)	

	main rule r_Main = 
		forall $x in MyDomain with true do
			foo($x) := $x
		
default init s0:
	function foo($x in  MyDomain) = $x
