asm agents

import StandardLibrary

signature:
	domain MyAgent subsetof Agent
	static a1: MyAgent
	static a2: MyAgent
	controlled foo: MyAgent -> Boolean

definitions:

	rule r_myAgentRule =
		foo(self) := not foo(self)

	main rule r_Main =
		forall $a in MyAgent with true do
			program($a)

default init s0:
	function foo($a in MyAgent) = false

	agent MyAgent: r_myAgentRule[]