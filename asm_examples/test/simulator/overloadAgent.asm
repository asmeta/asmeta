asm overloadAgent

import ../../STDL/StandardLibrary

signature:
	domain AgentA subsetof Agent
	domain AgentB subsetof Agent
	dynamic controlled foo: AgentA -> Boolean
	dynamic controlled foo: AgentB -> Boolean
	static agentA1: AgentA
	static agentA2: AgentA
	static agentB1: AgentB
	static agentB2: AgentB

definitions:

	main rule r_Main =
		par
			foo(agentA1) := true
			foo(agentB1) :=  false
			foo(agentA2) := not(foo(agentA2))
			foo(agentB2) :=  not(foo(agentB2))
		endpar

default init s0:
	function foo($c in AgentA) = false
	function foo($c in AgentB) = true