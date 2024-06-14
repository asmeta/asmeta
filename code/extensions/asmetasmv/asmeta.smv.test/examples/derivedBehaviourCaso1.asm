asm derivedBehaviourCaso1

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	
	
	derived der: Boolean

definitions:
	domain MyDomain = {0 : 3}

	function der = fooA
	
	//AsmetaL invariant
	invariant over der, fooB: fooB = der //false
	
	CTLSPEC ag(fooB = der) //dovrebbe essere falsa
	
	main rule r_Main =
		par
			fooA := not(fooA)
			fooB := der			
		endpar
		
		
default init s0:
	function fooA = true
	function fooB = der
