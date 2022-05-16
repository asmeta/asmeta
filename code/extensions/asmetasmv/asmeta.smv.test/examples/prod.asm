asm prod

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled d: Prod(MyDomain, MyDomain) -> MyDomain
	
definitions:
	
	domain MyDomain = {1:10}
	
	main  rule r_Main = 
		skip
		
default init s0:
	function d($x in MyDomain, $y in MyDomain) = 8
