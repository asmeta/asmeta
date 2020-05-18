asm derived

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom
	derived der: EnumDom

definitions:
	function der =
		switch foo
			case AA:
				CC
			case BB:
				AA
			otherwise
				BB
		endswitch

	main rule r_Main =
		foo := CC

default init s0:
	function foo = AA