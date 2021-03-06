asm caseTerm3

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		foo := switch foo
					case AA:
						BB
					case BB:
						CC
					otherwise
						if foo != CC then
							AA
						endif
				endswitch

default init s0:
	function foo = AA