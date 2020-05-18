asm derived

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	
	dynamic controlled fooA: Boolean
	dynamic controlled fooC: Boolean
	derived fooB: Boolean
	
	dynamic controlled foo : Boolean
	derived fooG: Boolean
	dynamic controlled fooH: Boolean
	
	derived fooI: Boolean
	dynamic monitored fooL: Boolean
	
	dynamic controlled fooM: Boolean

	dynamic controlled fooW: MyDomain
	derived fooZ: MyDomain

	dynamic controlled fooWW: MyDomain
	derived fooZZ: MyDomain

definitions:
	domain MyDomain = {1..4}

	function fooB = fooC
	function fooG = fooH
	function fooI = fooL

	function fooZ =
		if(fooW = 4) then
			1
		else
			fooW + 1
		endif

	function fooZZ =
		(fooWW mod 4) + 1
	
	//AsmetaL invariants
	invariant over fooB, fooC: fooB = fooC
	invariant over fooG, fooH: fooG = fooH
	invariant over fooI, fooL: fooI = fooL
	
	//CTL properties
	CTLSPEC ag(fooB = fooC)
	CTLSPEC ag(fooG = fooH)
	CTLSPEC ag(fooI = fooL)
	CTLSPEC ag(fooC iff ax(not(fooC)))
	CTLSPEC ag(not(fooC) iff ax(fooC))
	CTLSPEC ag(fooH iff ax(not(fooH)))
	CTLSPEC ag(not(fooH) iff ax(fooH))
	CTLSPEC ag((forall $b in Boolean with (fooG = $b iff ax(foo = $b))))
	CTLSPEC ag((forall $b in Boolean with (fooI = $b iff ax(fooM = $b))))

	main rule r_Main =
		par
			fooC := not(fooC)
			fooA := fooB
			fooH := not(fooH)
			foo := fooG
			fooM := fooI
			if(fooW < 4) then
				fooW := fooW + 1
			else
				fooW := 1
			endif
			if(fooWW < 3) then
				fooWW := fooWW + 1
			else
				fooWW := 1
			endif
		endpar
		
default init s0:
	function foo = fooG
	function fooH = false
	function fooM = fooI
	function fooA = true
	function fooW = 1
	function fooWW = 1