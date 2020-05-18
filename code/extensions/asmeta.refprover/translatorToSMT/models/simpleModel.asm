asm simpleModel

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
			else
				foo := AA
			endif
		endif


default init s0:
	function foo = AA