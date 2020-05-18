asm agentsChoose

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain SubAgent subsetof Agent
	dynamic controlled foo: SubAgent -> Boolean
	static agent1: SubAgent
	static agent2: SubAgent
	static agent3: SubAgent
	
definitions:
	
	rule r_rule =
		foo(self) := not(foo(self))

	//e' falso: per controllare che funzioni il choose
	CTLSPEC ag(foo(agent1)=foo(agent2))
	CTLSPEC ag(foo(agent2)=foo(agent3))
	
	//reachability
	CTLSPEC ef(foo(agent1))
	CTLSPEC ef(not(foo(agent1)))
	CTLSPEC ef(foo(agent2))
	CTLSPEC ef(not(foo(agent2)))
	CTLSPEC ef(foo(agent3))
	CTLSPEC ef(not(foo(agent3)))
	
	

	main rule r_Main =
		choose $a in SubAgent with true do
			program($a)
	
default init s0:
	function foo($a in SubAgent) = true

	agent SubAgent:
		r_rule[]
