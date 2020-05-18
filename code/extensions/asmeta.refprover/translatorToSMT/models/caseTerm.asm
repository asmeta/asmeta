asm caseTerm

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		foo := switch foo
					case AA:
						CC
					case BB:
						AA
					case CC:
						BB
				endswitch

default init s0:
	function foo = AA