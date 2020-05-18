asm agentsDynamic

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled agentA: Agent
	dynamic controlled agentB: Agent
	
definitions:
	
	rule r_rule =
		skip

	main rule r_Main =
		par
			program(agentA)
			program(agentB)
			agentA := agentB
			agentB := agentA
		endpar
	

default init s0:

	agent Agent:
		r_rule[]
