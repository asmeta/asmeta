asm forallForall2

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: Prod(EnumDom, EnumDom) -> EnumDom

definitions:

	main rule r_Main =
		forall $x in EnumDom with true do
			forall $y in EnumDom with true do
				foo($x, $y) := foo($y, $x)

default init s0:
	function foo($x in EnumDom, $y in EnumDom) = $x