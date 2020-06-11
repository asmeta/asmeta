asm agents

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB}
	domain MyDomain subsetof Integer
	domain SubAgent1 subsetof Agent
	domain SubAgent2 subsetof Agent
	dynamic controlled fooAB: SubAgent1 -> Boolean
	dynamic controlled fooC: SubAgent2 -> MyDomain
	dynamic controlled fooD: Agent -> EnumDom
	dynamic controlled foo: MyDomain -> MyDomain
	dynamic controlled fooEnum: EnumDom -> EnumDom
	static agentA: SubAgent1
	static agentB: SubAgent1
	static agentC: SubAgent2
	static agentD: Agent
	dynamic controlled example: Boolean
	dynamic controlled fooProd: Prod(MyDomain, MyDomain) -> MyDomain
	dynamic controlled fooChoose: MyDomain -> MyDomain

definitions:
	domain MyDomain = {1..4}
	
	rule r_rule1 =
		fooAB(self) := not(fooAB(self))

	rule r_rule =
		fooD(self) := BB

	rule r_rule2 =
		if(fooC(self)<4) then
			fooC(self) := fooC(self) + 1
		else
			fooC(self) := 1
		endif

	CTLSPEC ag(fooAB(agentA)!=fooAB(agentB))
	CTLSPEC ag(fooAB(agentA) iff fooC(agentC) mod 2 = 1)
	//CTLSPEC ef(fooEnum(AA) = BB)
	CTLSPEC ag(foo(2) > 2)
	CTLSPEC ag(foo(3) > 3)
	CTLSPEC fooChoose(1) <= 1
	CTLSPEC ag(fooChoose(2) <= 2)
	CTLSPEC ag(fooChoose(3) <= 3)

	main rule r_Main =
		par
			example := true
			program(agentA)
			program(agentB)
			program(agentC)
			program(agentD)
			choose $x in MyDomain, $y in MyDomain with $x<$y do
				foo($x) := $y
			forall $e in EnumDom with true do
				if(fooEnum($e) = $e) then
					fooEnum($e) := $e
				else
					fooEnum($e) := AA
				endif
			forall $i in MyDomain with true do
				forall $j in MyDomain with true do
					fooProd($i, $j) := $j
			forall $q in MyDomain with true do
				choose $w in MyDomain with $w <= $q do
					fooChoose($q) := $w
			//choose $k in MyDomain, $l in MyDomain with $l <= $k do
			//	fooChoose($k) := $l
		endpar
	

default init s0:
	function fooAB($a in SubAgent1) = at({agentA->true,agentB->false},$a)
	function fooC($a in SubAgent2) = at({agentC->1},$a)
	function fooD($a in Agent) = at({agentD->AA},$a)
	function foo($i in MyDomain) = 4
	function fooEnum($e in EnumDom) = $e
	function fooChoose($i in MyDomain) = $i

	agent SubAgent1:
		r_rule1[]
	
	agent SubAgent2:
		r_rule2[]

	agent Agent:
		r_rule[]
