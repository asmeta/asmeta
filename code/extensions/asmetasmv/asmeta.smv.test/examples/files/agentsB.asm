module agentsB

import ../../../../../../asm_examples/STDL/StandardLibrary

export agentB, r_b, fooABB, MyDomainB, fooB

signature:
	enum domain MyDomainB = {AA | BB | FF}
	domain SubAgentB subsetof Agent
	dynamic controlled fooB: Boolean
	dynamic controlled fooABB: MyDomainB
	static agentB: SubAgentB
	
definitions:
	rule r_b = 
		fooB := not(fooB)

//default init s0:
	//function fooB = true
	
