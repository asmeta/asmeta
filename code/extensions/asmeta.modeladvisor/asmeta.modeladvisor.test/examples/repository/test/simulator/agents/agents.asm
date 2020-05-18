asm agents

import ../../../STDL/StandardLibrary

signature:
	domain SubAgent subsetof Agent
	dynamic controlled foo12: SubAgent -> Boolean
	dynamic controlled foo1: Boolean
	dynamic controlled foo2: Boolean
	static agent1: SubAgent
	static agent2: SubAgent
	
definitions:
	rule r_rule =
		foo12(self) := false

	main rule r_Main =
		seq		
			par
				program(agent1)
				program(agent2)
			endpar
			foo1 := foo12(agent1)
			foo2 := foo12(agent2)
		endseq
	

default init s0:
	function foo12($a in SubAgent) = true
	function foo1 = true
	function foo2 = true

	agent SubAgent:
		r_rule[]
