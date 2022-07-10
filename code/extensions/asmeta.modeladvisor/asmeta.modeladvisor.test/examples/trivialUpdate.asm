//il trivial update e il couldBeStatic coincidono?
asm trivialUpdate

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom
	dynamic controlled fooB: EnumDom
	dynamic controlled fooC: EnumDom
	dynamic controlled fooD: EnumDom
	dynamic controlled fooE: EnumDom
	dynamic controlled fooF: EnumDom
	dynamic controlled fooG: EnumDom
	dynamic controlled fooH: EnumDom

definitions:
 
	main  rule r_Main =
		par
			fooA := AA //trivial (rientra anche nel caso couldBeStatic perche' fooA vale sempre AA)
			fooB := BB //no trivial (nello stato iniziale fooB vale AA)
			if(fooA = BB) then
				fooC := AA //unreachable, quindi non trivial
			else
				fooC := BB //non trivial (nello stato iniziale fooC vale AA)
			endif
			if(fooA = BB) then
				fooD := BB //unreachable, quindi non trivial
			else
				fooD := AA //trivial (rientra anche nel caso couldBeStatic perche� fooD vale sempre AA)
			endif
			fooE := fooA //trivial
			fooH := fooC //non trivial
			if(fooB = AA) then
				fooF := BB //no trivial (avviene solo dopo lo stato iniziale in cui fooF vale AA)
			else
				fooF := BB //trivial (ma NON rientra anche nel caso couldBeStatic, perche' fooF nello stato iniziale vale AA)
			endif
			if(fooB = AA) then
				fooG := AA //trivial (rientra anche nel caso couldBeStatic perche' fooG vale sempre AA)
			else
				fooG := AA //trivial (rientra anche nel caso couldBeStatic perche' fooG vale sempre AA)
			endif
		endpar

default init s0:
	function fooA = AA
	function fooB = AA
	function fooC = AA
	function fooD = AA
	function fooE = AA
	function fooF = AA
	function fooG = AA
	function fooH = AA
