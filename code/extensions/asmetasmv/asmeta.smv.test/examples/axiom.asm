asm axiom

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: MyDomain
	dynamic monitored monA: Boolean
	dynamic monitored monB: Boolean

definitions:
	domain MyDomain = {1..4}

	CTLSPEC not(ef(fooA != fooB))
	CTLSPEC ag(monA and fooA=2 implies ax(fooA=3))

	main rule r_Main = 
		par
			if(monA) then
				fooA := fooA + 1
			endif
			if(monB) then
				fooB := fooB + 1
			endif
		endpar

default init s0:
	function fooA = 1
	function fooB = 1
