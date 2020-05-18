asm forallTerm

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain -> MyDomain
	dynamic controlled result1: Boolean
	dynamic controlled result2: Boolean
	dynamic controlled result3: Boolean

definitions:
	domain MyDomain = {1..3}
	
	invariant over result1, result2: result1 and result2 and not(result3)
	
	//CTL properties
	CTLSPEC ag(result1 and result2 and not(result3))

	main rule r_Main = 
		par
			if(forall $x in MyDomain with foo($x)=$x) then
				result1 := true
			else
				result1 := false
			endif
			result2 := (forall $y in MyDomain with foo($y)=$y)
			result3 := (forall $z in MyDomain with foo($z)=2)
		endpar
		
default init s0:
	function foo($x in MyDomain) = $x
	function result1 = true
	function result2 = true
	function result3 = false
