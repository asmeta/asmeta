asm forall

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		forall $x in EnumDom with true do
			if $x = AA then
				foo := BB
			endif

default init s0:
	function foo = AA