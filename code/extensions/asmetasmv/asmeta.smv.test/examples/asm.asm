asm asm

import ../../../../../asm_examples/STDL/StandardLibrary

signature:

	domain MyDomain subsetof Integer
	dynamic controlled aa: MyDomain
	dynamic monitored b: Boolean
	dynamic controlled c: MyDomain
	dynamic controlled d: Prod(MyDomain,MyDomain) -> MyDomain
	
	
definitions:
	
	domain MyDomain = {1..10}
	
	invariant over aa, c: aa = c and (c != 2)
	invariant over d: d(1,1) > d(2,2)

	
	main  rule r_Main = 
		if(b) then
			par
				d(1,1) := 5
				d(1,1) := 2
				aa:= 4
			endpar
		else
			par
				d(1,1) := 6
				d(2,2) := 4
				c := 2
			endpar
		endif
		
	

default init s0:
	function aa = 3
	function c = 6
	
//CTL
//AG (var = 4 -> EF (var_a = 2))
