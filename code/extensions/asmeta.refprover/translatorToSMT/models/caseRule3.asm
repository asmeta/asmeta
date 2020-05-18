asm caseRule3

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
			otherwise
				if foo != CC then
					foo := AA
				endif
		endswitch

default init s0:
	function foo = AA