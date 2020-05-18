//il trivial update e il couldBeStatic coincidono?
asm trivialUpdateSimple

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom

definitions:
 
	main  rule r_Main =
		par
			if fooA != BB  then
				fooA := BB
			endif
			if fooA = BB  then
				fooA := BB
			endif
		endpar

default init s0:
	function fooA = AA
