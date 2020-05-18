module agentsA

import ../../../../../../asm_examples/STDL/StandardLibrary

export agentA, r_a, fooABA, MyDomain, fooA, SubAgentA

signature:
	enum domain MyDomain = {DD | CC}	
	domain SubAgentA subsetof Agent
	dynamic controlled fooA: Boolean
	dynamic controlled fooABA: MyDomain -> Boolean
	static agentA: SubAgentA
	
definitions:
	
	rule r_a =
		fooA := not(fooA)

//default init s0:
	//function fooA = true
	//function fooABA($x in MyDomain) = true
	
