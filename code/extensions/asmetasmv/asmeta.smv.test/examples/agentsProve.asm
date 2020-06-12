asm agentsProve

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubAgent1 subsetof Agent
	static agentA: SubAgent1
	static agentB: SubAgent1
	
definitions:
	function agentA = agentB
	
	rule r_rule1 =
		skip

	
	main rule r_Main =
		par
			program(agentA)
			program(agentB)
		endpar
	

default init s0:

	agent SubAgent1:
		r_rule1[]
