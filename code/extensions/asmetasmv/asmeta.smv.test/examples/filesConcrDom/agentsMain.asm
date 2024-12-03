asm agentsMain

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

import agentsB
import agentsA

signature:
	dynamic controlled fooMain: ImportedDomain -> Boolean
	dynamic controlled fooMainProd: Prod(ImportedDomain, ImportedDomain) -> Boolean

definitions:

	CTLSPEC ef(fooMain(1) and not(fooMain(2)) and not(fooMain(3)))
	//CTLSPEC not(ef(fooMain(1) and not(fooMain(2)) and not(fooMain(3))))
	CTLSPEC ef(fooEnum(ZZ) and not(fooEnum(RR)))
	CTLSPEC ag(fooMainProd(1, 1) iff ag(fooMainProd(1, 1)))
	CTLSPEC ag(fooMainProd(2, 2) iff ag(fooMainProd(2, 2)))
	CTLSPEC ag(fooMainProd(3, 3) iff ag(fooMainProd(3, 3)))
	CTLSPEC ef(not(fooMainProd(1, 2)) and not(fooMainProd(1, 3)) and 
		not(fooMainProd(2, 1)) and not(fooMainProd(2, 3)) and not(fooMainProd(3, 1)) and
		not(fooMainProd(3, 2)) )
	CTLSPEC ag(fooBimp(1)<=1 and fooBimp(2)<=2)

	main rule r_Main =
		par
			choose $g in ImportedDomain, $h in ImportedDomain with $g != $h do
				fooMainProd($g, $h) := false
			choose $x in ImportedDomain with true do
				par
					choose $b in Boolean with true do
						fooMain($x) := $b
					switch($x)
						case 1: fooMainProd(1,1) := true
						case 2: fooMainProd(2,2) := true
						case 3: fooMainProd(3,3) := true
					endswitch			
				endpar
			forall $e in EnumDomain with true do
				choose $t in Boolean with true do
					fooEnum($e) := $t		
			program(agentA)
			program(agentB)
		endpar
	

default init s0:
	function fooA = 1
	function fooB = 1
	function fooMain($i in ImportedDomain) = true
	function fooEnum($e in EnumDomain) = true
	function fooBimp($l in ImportedDomain) = $l
	
	agent SubAgentA:
		r_a[]	

	agent SubAgentB:
		r_b[]
