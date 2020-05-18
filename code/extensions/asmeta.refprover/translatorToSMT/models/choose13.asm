asm choose13

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB}
	dynamic controlled foo: Boolean -> EnumDom

definitions:

	main rule r_Main =
		par
			choose $x in Boolean with foo($x) = AA do
				foo($x) := BB
			foo(true) := BB
		endpar

default init s0:
	function foo($x in Boolean) = AA