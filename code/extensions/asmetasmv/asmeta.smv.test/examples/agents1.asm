asm agents1

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain SubAgentA subsetof Agent
	domain SubAgentB subsetof Agent
	dynamic controlled fooA: Boolean //la usa solo agentA
	dynamic controlled fooB: Boolean //la usa solo agentB
	dynamic controlled fooAB: Boolean //la usano sia agentA, sia agentB
	static agentA: SubAgentA
	static agentB: SubAgentB
	
definitions:
	
	rule r_a =
		par
			fooA := not(fooA)
			if (fooAB) then
				fooAB := not(fooAB)
			endif
		endpar
	
	rule r_b = 
		par
			fooB := not(fooB)
			if (not(fooAB)) then
				fooAB := not(fooAB)
			endif
		endpar	
	CTLSPEC ag(fooA != fooB)
	CTLSPEC ag(fooAB = fooB) 

	main rule r_Main =
		par
			program(agentA)
			program(agentB)
		endpar
	

default init s0:
	function fooA = false
	function fooB = true
	function fooAB = true

	agent SubAgentA:
		r_a[]

	agent SubAgentB:
		r_b[]
