asm agents3

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain SubAgent subsetof Agent
	dynamic controlled foo12: SubAgent -> Boolean
	static agent1: SubAgent
	static agent2: SubAgent
	
definitions:
	
	rule r_rule =
		if(self=agent1) then
			foo12(agent1) := not(foo12(agent1))
		else
			foo12(agent2) := not(foo12(agent2))
		endif	

	CTLSPEC ag(foo12(agent1)!=foo12(agent2))

	main rule r_Main =
		par
			program(agent1)
			program(agent2)
		endpar
	

default init s0:
	function foo12($a in SubAgent) = at({agent1->true,agent2->false},$a)

	agent SubAgent:
		r_rule[]
