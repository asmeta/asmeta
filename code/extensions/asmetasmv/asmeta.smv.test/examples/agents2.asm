//errore ora risolto (mantenuta descrizione come documentazione)
//sembra che self sia trattato come un terzo agente
//se non si inizializza foo12(self) in esecuzione errore UnresolvedReferenceException
//su foo12(self)

asm agents2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain SubAgent subsetof Agent
	dynamic controlled foo12: SubAgent -> Boolean
	static agent1: SubAgent
	static agent2: SubAgent
	
definitions:
	
	rule r_rule =
		foo12(self) := not(foo12(self))	

	//CTLSPEC ag(foo12(agent1)!=foo12(agent2))

	main rule r_Main =
		par
			program(agent1)
			program(agent2)
		endpar
	

default init s0:
	//function foo12($a in SubAgent) = at({agent1->true,agent2->true,self->false},$a)
	function foo12($a in SubAgent) = at({agent1->true,agent2->false},$a)//errore su self (ora risolto)
	//function foo12($a in SubAgent) = false

	agent SubAgent:
		r_rule[]
