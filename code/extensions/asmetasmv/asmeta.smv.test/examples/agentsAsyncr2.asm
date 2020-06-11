//asyncr asm agentsAsyncr2
asm agentsAsyncr2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain SubAgent1 subsetof Agent
	domain SubAgent2 subsetof Agent
	dynamic controlled foo1: Boolean
	dynamic controlled foo2: Boolean
	static agent1: SubAgent1
	static agent2: SubAgent2
	
definitions:
	
	rule r_rule1 =
		foo1 := not(foo1)

	rule r_rule2 =
		foo2 := not(foo2)

	//AsmetaL invariants
	invariant over foo1,foo2: foo1=foo2

	CTLSPEC ag(foo1=foo2)
	
	main rule r_Main =
		par
			program(agent1)
			program(agent2)
		endpar
	

default init s0:
	function foo1 = true
	function foo2 = true

	agent SubAgent1:
		r_rule1[]

	agent SubAgent2:
		r_rule2[]
