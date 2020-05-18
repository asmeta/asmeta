asm macroRuleCalled

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	enum domain EnumDom = {AA | BB | CC | DD | EE}
	dynamic controlled fooA: EnumDom
	dynamic controlled fooB: EnumDom
	dynamic controlled fooC: EnumDom
	dynamic controlled fooD: EnumDom
	dynamic controlled fooE: EnumDom

definitions:
	domain SubDom = {1..2}

	macro rule r_a =
		fooA := AA

	rule r_b =
		fooB := BB

	macro rule r_c =
		fooC := AA

	rule r_d =
		fooD := AA

	rule r_e =
		fooE := EE
	
	main  rule r_Main =
		par
			r_c[]
			r_d[]
			if(fooB = BB) then
				r_e[] //called, but not reachable
			endif
		endpar

default init s0:
	function fooA = AA
	function fooB = AA
	function fooC = AA
	function fooD = AA
	function fooE = AA
