asm caseRule

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		switch foo
			case AA:
				foo := BB
			case BB:
				foo := CC
			case CC:
				foo := AA
		endswitch

default init s0:
	function foo = AA