asm updateRule

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	
definitions:
	domain MyDomain = {1:4}
	
	main  rule r_Main = 
		foo := foo + 1
	
default init s0:
	function foo = 1
