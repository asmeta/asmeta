//update inconsistente

asm agents5

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubAgent1 subsetof Agent
	domain SubAgent2 subsetof Agent
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	static agent1: SubAgent1
	static agent2: SubAgent2
	
definitions:
	domain MyDomain = {1:4}

	rule r_rule1 =
		foo := 3

	rule r_rule2 =
		foo := 4

	main rule r_Main =
		par
			program(agent1)
			program(agent2)
		endpar
	

default init s0:
	function foo = 2

	agent SubAgent1:
		r_rule1[]

	agent SubAgent2:
		r_rule2[]
