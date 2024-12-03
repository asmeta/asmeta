asm forallForall

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: Prod(EnumDom, EnumDom) -> EnumDom

definitions:

	main rule r_Main =
		forall $x in EnumDom with true do
			forall $y in EnumDom with true do
				switch foo($x, $y)
					case AA:
						foo($x, $y) := BB
					case BB:
						foo($x, $y) := CC
					case CC:
						foo($x, $y) := AA
				endswitch

default init s0:
	function foo($x in EnumDom, $y in EnumDom) = AA