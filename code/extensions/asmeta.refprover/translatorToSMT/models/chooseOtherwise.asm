asm chooseOtherwise

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		choose $x in EnumDom with $x = AA and $x != foo do
			foo := $x
		ifnone
			foo := BB

default init s0:
	function foo = AA