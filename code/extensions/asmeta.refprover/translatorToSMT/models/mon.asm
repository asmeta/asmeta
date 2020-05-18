asm mon

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom
	dynamic monitored fooMon: EnumDom

definitions:

	main rule r_Main =
		foo := fooMon

default init s0:
	function foo = AA