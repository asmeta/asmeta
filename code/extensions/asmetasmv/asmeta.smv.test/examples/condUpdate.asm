asm condUpdate

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom
	dynamic monitored mon: Boolean

definitions:
	main rule r_Main =
		if(mon) then
			foo := AA
		endif

default init s0:
	function foo = BB
