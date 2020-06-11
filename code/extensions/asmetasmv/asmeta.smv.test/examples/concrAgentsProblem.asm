//Invariante violato: viene aggiornata solo fooA.
//Per i tre agenti viene utilizzata solo l'inizializzazione di Agent.
//Per i due agenti agent1 e agent2 (concrete domain) viene utilizzata l'inizializzazione
//di Agent (loro type domain) e non le loro inizializzazioni specifiche.
//Il problema viene risolto mettendo per prima l'inizializzazione di Agent in default init
//Soluzione presente nel file concrAgentsProblemSolution.asm

asm concrAgentsProblem

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubAgent1 subsetof Agent
	domain SubAgent2 subsetof Agent
	dynamic controlled foo1: Boolean
	dynamic controlled foo2: Boolean
	dynamic controlled fooA: Boolean
	static agent1: SubAgent1
	static agent2: SubAgent2
	static agentA: Agent
	
definitions:
	
	rule r_rule1 =
		foo1 := not(foo1)

	rule r_rule2 =
		foo2 := not(foo2)

	rule r_ruleA =
		fooA := not(fooA)

	invariant over foo1, foo2, fooA: foo1=foo2 and foo2=fooA

	main rule r_Main =
		par
			program(agent1)
			program(agent2)		
			program(agentA)
		endpar
	

default init s0:
	function foo1 = true
	function foo2 = true
	function fooA = true

	agent SubAgent1:
		r_rule1[]

	agent SubAgent2:
		r_rule2[]

	agent Agent:
		r_ruleA[]
