asm simpleModel2

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		if foo = AA then
			foo := BB
		else
			if foo = BB then
				foo := CC
			endif
		endif


default init s0:
	function foo = AA