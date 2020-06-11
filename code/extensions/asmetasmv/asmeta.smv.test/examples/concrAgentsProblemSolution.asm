//Soluzione al problema descritto in concrAgentsProblem.asm
//Bisogna mettere per prima l'inizializzazione di Agent nel default init.

asm concrAgentsProblemSolution

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

	agent Agent:
		r_ruleA[]	
	
	agent SubAgent1:
		r_rule1[]

	agent SubAgent2:
		r_rule2[]
