module agentsA

import ../../../../../../asm_examples/STDL/StandardLibrary

export *

signature:
	domain SubAgentA subsetof Agent
	domain MyDomain subsetof Integer
	dynamic controlled fooA: MyDomain
	static agentA: SubAgentA
	
definitions:
	domain MyDomain = {1:4}
	
	rule r_a =
		fooA := 2
