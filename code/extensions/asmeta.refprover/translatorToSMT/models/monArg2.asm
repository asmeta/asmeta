asm monArg2

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> Boolean
	dynamic monitored fooMon: EnumDom

definitions:

	main rule r_Main =
		if foo(fooMon) then
			foo(fooMon) := false
		endif

default init s0:
	function foo($e in EnumDom) = true