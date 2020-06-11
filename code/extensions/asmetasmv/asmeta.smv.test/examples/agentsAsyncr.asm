asyncr asm agentsAsyncr
//asm agentsAsyncr

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	domain SubAgent1 subsetof Agent
	domain SubAgent2 subsetof Agent
	dynamic controlled foo1: MyDomain
	dynamic controlled foo2: MyDomain
	static agent1: SubAgent1
	static agent2: SubAgent2
	
definitions:
	domain MyDomain = {1..4}
	
	rule r_rule1 =
		if(foo1<4) then
			foo1 := foo1 + 1
		else
			foo1 := 1
		endif

	rule r_rule2 =
		if(foo2<4) then
			foo2 := foo2 + 1
		else
			foo2 := 1
		endif

	//AsmetaL invariants
	invariant over foo1,foo2: foo1=foo2

	CTLSPEC ag(foo1=foo2)
	
	main rule r_Main =
		par
			program(agent1)
			program(agent2)
		endpar
	

default init s0:
	function foo1 = 1
	function foo2 = 1

	agent SubAgent1:
		r_rule1[]

	agent SubAgent2:
		r_rule2[]
