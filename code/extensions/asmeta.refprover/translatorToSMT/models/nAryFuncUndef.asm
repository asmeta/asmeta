asm nAryFuncUndef

import StandardLibrary

signature:
	domain MyAgent subsetof Agent
	dynamic controlled foo: Prod(MyAgent, MyAgent) -> Boolean
	static a1: MyAgent
	static a2: MyAgent

definitions:

	rule r_myAgentRule =
		foo(self, self) := not(foo(self, self))

	main rule r_Main =
		forall $a in MyAgent with true do
			program($a)

default init s0:
	function foo($a in MyAgent, $b in MyAgent) = false

	agent MyAgent:
		r_myAgentRule[]