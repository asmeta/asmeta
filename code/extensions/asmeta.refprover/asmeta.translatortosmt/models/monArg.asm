asm monArg

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> Boolean
	dynamic monitored fooMon: EnumDom

definitions:

	main rule r_Main =
		foo(fooMon) := not(foo(fooMon))

default init s0:
	function foo($e in EnumDom) = false