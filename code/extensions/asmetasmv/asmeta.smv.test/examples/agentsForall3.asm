asm agentsForall3

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

	//esiste un cammino in cui agent1 non viene mai scelto
	//Problema degli stati iniziali
	//CTLSPEC ex(chosenId!=1 and eg(chosenId!=1))
	
	//esiste un cammino in cui agent2 non viene mai scelto
	//Problema degli stati iniziali
	//CTLSPEC ex(chosenId!=2 and eg(chosenId!=2))
	
	//esiste un cammino in cui agent3 non viene mai scelto
	//Problema degli stati iniziali
	//CTLSPEC ex(chosenId!=3 and eg(chosenId!=3))
	
	//esiste uno stato in cui tutte le locazioni di foo sono false
	CTLSPEC ef(not(foo(agent1)) and not(foo(agent2)) and not(foo(agent3)))
	
	//non esiste uno cammino in cui tutte le locazioni di foo sono false
	CTLSPEC not(eg(not(foo(agent1)) and not(foo(agent2)) and not(foo(agent3))))

	//non esiste un cammino in cui le locazioni di foo sono sempre uguali
	CTLSPEC not(eg(foo(agent1)=foo(agent2) and foo(agent2)=foo(agent3)))
	
	main rule r_Main =
		forall $a in SubAgent with id($a)=chosenId do
			program($a)
	
default init s0:
	function foo($a in SubAgent) = true

	agent SubAgent:
		r_rule[]
