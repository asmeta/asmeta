asm agentsProblem

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubAgent subsetof Agent
	domain MyDomain subsetof Integer
	static agents: MyDomain -> SubAgent
	
definitions:
	domain MyDomain = {1:4}

	rule r_rule =
		skip

	main rule r_Main =
		choose $x in MyDomain with true do
			program(agents($x))
	

default init s0:

	agent SubAgent:
		r_rule[]
