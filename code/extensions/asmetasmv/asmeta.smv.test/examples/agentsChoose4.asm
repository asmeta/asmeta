asm agentsChoose4

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	domain SubAgent subsetof Agent
	dynamic controlled foo: SubAgent -> Boolean
	static id: SubAgent -> MyDomain
	dynamic monitored chosenId: MyDomain
	static agent1: SubAgent
	static agent2: SubAgent
	static agent3: SubAgent
	
definitions:
	domain MyDomain = {1:3}
	function id($a in SubAgent) = at({agent1 -> 1, agent2 -> 2, agent3 -> 3}, $a)
	
	rule r_rule =
		foo(self) := not(foo(self))

	//foo(agent3) e' sempre vera perche' non viene mai scelto agent3
	CTLSPEC ag(foo(agent3))

	//esiste un cammino in cui viene scelto solo agent1
	//Per il problema degli stati iniziali e' falsa
	//CTLSPEC ex(chosenId=2 and eg(chosenId=2))
	//Bisogna cercare il controesempio di:
	CTLSPEC ax(not(chosenId=2 and eg(chosenId=2)))

	//esiste un cammino in cui 
	CTLSPEC ef(((foo(agent1) and foo(agent2) and foo(agent3))) and eg(foo(agent1) and foo(agent2) and foo(agent3)))
	
	main rule r_Main =
		choose $a in SubAgent with id($a)<chosenId do
			program($a)
	
default init s0:
	function foo($a in SubAgent) = true

	agent SubAgent:
		r_rule[]