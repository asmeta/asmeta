asm forallTerm2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain -> MyDomain
	dynamic controlled result1: Boolean
	dynamic controlled result2: Boolean
	dynamic controlled result3: Boolean

definitions:
	domain MyDomain = {1:2}
	
	main rule r_Main = 
		par
			if(forall $x in MyDomain with foo(foo($x))=1) then
				result1 := true
			else
				result1 := false
			endif
			result2 := (forall $y in MyDomain with 2=foo(foo($y)))
			result3 := (forall $z in MyDomain with 1=foo(foo(2)))
		endpar
		
default init s0:
	function foo($x in MyDomain) = $x
	function result1 = true
	function result2 = true
	function result3 = false
