asm agentsChoose3

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	domain SubAgent subsetof Agent
	dynamic controlled foo: SubAgent -> MyDomain
	static agent1: SubAgent
	static agent2: SubAgent
	static agent3: SubAgent

	static uno: MyDomain
	static due: MyDomain
	static tre: MyDomain
	
definitions:
	domain MyDomain = {1..3}
	function uno = 1
	function due = 2
	function tre = 3
	
	rule r_rule =
		if(self = agent2) then
			par
				foo(agent1) := foo(agent3)
				foo(agent3) := foo(agent1)
			endpar
		endif

	//Le seguenti proprieta' CTL davano il seguente errore (lasciato per documentazione).
	//Problems locating the function neq(Prod(MyDomain,Integer))
	//Exception: asmeta.definitions.domains.impl.ConcreteDomainImpl
	//cannot be cast to asmeta.definitions.domains.TypeDomain
	CTLSPEC ag(foo(agent1)!=foo(agent3))
	CTLSPEC ag(foo(agent1)!=2)
	CTLSPEC ag(foo(agent3)!=2)
	CTLSPEC ag(foo(agent1)=1 iff ax(foo(agent1)=3))
	CTLSPEC ag(foo(agent1)=3 iff ax(foo(agent1)=1))
	CTLSPEC ag(foo(agent3)=1 iff ax(foo(agent3)=3))
	CTLSPEC ag(foo(agent3)=3 iff ax(foo(agent3)=1))
	CTLSPEC ag(foo(agent1)=1 iff ax(foo(agent3)=1))
	CTLSPEC ag(foo(agent1)=3 iff ax(foo(agent3)=3))
	CTLSPEC ag(foo(agent3)=1 iff ax(foo(agent1)=1))
	CTLSPEC ag(foo(agent3)=3 iff ax(foo(agent1)=3))
	
	main rule r_Main =
		//choose $a in SubAgent with foo($a) = 2 do
		choose $a in SubAgent with foo($a) = due do
			program($a)
	
default init s0:
	function foo($a in SubAgent) = at({agent1->1, agent2->2, agent3->3}, $a)

	agent SubAgent:
		r_rule[]