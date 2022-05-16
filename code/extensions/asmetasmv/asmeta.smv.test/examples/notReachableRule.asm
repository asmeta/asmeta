asm notReachableRule

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain

definitions:
	domain MyDomain = {1:4}

	rule r_a =
		foo := 1

	rule r_b =
		foo := 2
	
	main  rule r_Main = 
		if(foo > 1) then
			r_b[]
		else
			r_a[]
		endif

default init s0:
	function foo = 1