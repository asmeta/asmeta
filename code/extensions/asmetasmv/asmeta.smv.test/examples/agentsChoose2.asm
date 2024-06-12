//dopo tre passi tutte le locazioni di foo dovrebbero essere false

asm agentsChoose2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain SubAgent subsetof Agent
	dynamic controlled foo: SubAgent -> Boolean
	static agent1: SubAgent
	static agent2: SubAgent
	static agent3: SubAgent
	
definitions:
	
	rule r_rule =
		foo(self) := not(foo(self))

	CTLSPEC af(not(foo(agent1)) and not(foo(agent2)) and not(foo(agent3)))
	
	//Per il problema degli stati iniziali risulta falsa
	//CTLSPEC ef(not(foo(agent1)) and foo(agent2) and not(foo(agent3)))
	//Bisogna vedere se e' falsa:
	CTLSPEC ag(not(not(foo(agent1)) and foo(agent2) and not(foo(agent3))))

	main rule r_Main =
		choose $a in SubAgent with foo($a) do
			program($a)
	
default init s0:
	function foo($a in SubAgent) = true

	agent SubAgent:
		r_rule[]