asm initUndef

import StandardLibrary

signature:
	domain MyAgent subsetof Agent
	enum domain EnumDom = {AA | BB | CC}
	static a1: MyAgent
	static a2: MyAgent
	controlled foo: MyAgent -> EnumDom

definitions:

	rule r_myAgentRule =
		if isDef(foo(self)) then
			foo(self) := BB
		else
			foo(self) := AA
		endif

	main rule r_Main =
		forall $a in MyAgent with true do
			program($a)

default init s0:

	agent MyAgent: r_myAgentRule[]