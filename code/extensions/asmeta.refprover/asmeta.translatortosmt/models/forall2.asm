asm forall2

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> EnumDom

definitions:

	main rule r_Main =
		forall $x in EnumDom with foo($x) = AA do
			foo($x) := BB

default init s0:
	function foo($x in EnumDom) = AA