asm overloadAgent3

import ../../STDL/StandardLibrary

signature:
	domain AgentA subsetof Agent
	domain AgentB subsetof Agent
	dynamic controlled foo: AgentA -> Boolean
	dynamic controlled foo: AgentB -> Boolean
	dynamic monitored monAgentA: AgentA
	dynamic monitored monAgentB: AgentB
	static agentA1: AgentA
	static agentA2: AgentA
	static agentB1: AgentB
	static agentB2: AgentB

definitions:

	main rule r_Main =
		par
			foo(monAgentA) := not(foo(monAgentA))
			foo(monAgentB) :=  not(foo(monAgentB))
		endpar

default init s0:
	function foo($c in AgentA) = false
	function foo($c in AgentB) = true