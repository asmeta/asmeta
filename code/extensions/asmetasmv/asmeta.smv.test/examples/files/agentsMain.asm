asm agentsMain

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

import agentsB
import agentsA

signature:
	dynamic controlled fooMain: MyDomain

definitions:
	//AsmetaL invariant
	invariant over fooA, fooB: fooA != fooB
	
	CTLSPEC ag(fooA != fooB)
	CTLSPEC ag(fooMain = DD)
	CTLSPEC ag(fooABA(DD))

	main rule r_Main =
		par
			fooMain := DD
			fooABB := AA
			fooABA(DD) := true
			program(agentA)
			program(agentB)
		endpar

default init s0:
	function fooA = false
	function fooB = true
	function fooABA($x in MyDomain) = true
	function fooMain = DD

	agent SubAgentA:
		r_a[]

	agent SubAgentB:
		r_b[]
		
