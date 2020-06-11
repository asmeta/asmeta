//il forall esegue tutti gli agenti

asm agentsForall

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain SubAgent subsetof Agent
	dynamic controlled foo: SubAgent -> Boolean
	static agent1: SubAgent
	static agent2: SubAgent
	static agent3: SubAgent
	
definitions:
	
	rule r_rule =
		foo(self) := not(foo(self))

	CTLSPEC ag(foo(agent1)=foo(agent2) and foo(agent2)=foo(agent3))
	CTLSPEC ag(foo(agent1) iff ax(not(foo(agent1))))
	CTLSPEC ag(not(foo(agent1)) iff ax(foo(agent1)))
	CTLSPEC ag(foo(agent2) iff ax(not(foo(agent2))))
	CTLSPEC ag(not(foo(agent2)) iff ax(foo(agent2)))
	CTLSPEC ag(foo(agent3) iff ax(not(foo(agent3))))
	CTLSPEC ag(not(foo(agent3)) iff ax(foo(agent3)))
	
	main rule r_Main =
		forall $a in SubAgent with true do
			program($a)

default init s0:
	function foo($a in SubAgent) = true

	agent SubAgent:
		r_rule[]