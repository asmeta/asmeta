//all'inizio il forall esegue solo agent2
//dopo un passo tutte le locazioni di foo valgono 1 e il forall non esegue
//piu' nessun agente

asm agentsForall2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	domain SubAgent subsetof Agent
	dynamic controlled foo: SubAgent -> MyDomain
	static agent1: SubAgent
	static agent2: SubAgent
	static agent3: SubAgent
	
definitions:
	domain MyDomain = {1..3}
	
	rule r_rule =
		foo(self) := 1

	CTLSPEC af(foo(agent1)=1 and foo(agent2)=1 and foo(agent3)=1)
	
	main rule r_Main =
		forall $a in SubAgent with (foo($a) mod 2)=0 do
			program($a)
default init s0:
	function foo($a in SubAgent) = at({agent1->1, agent2->2, agent3->1}, $a)

	agent SubAgent:
		r_rule[]