asm choose2

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom

definitions:

	main rule r_Main =
		choose $x in EnumDom with $x != foo do
			foo := $x

default init s0:
	function foo = AA