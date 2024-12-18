asm choose12

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB}
	dynamic controlled foo: Boolean -> EnumDom

definitions:

	main rule r_Main =
		choose $x in Boolean with foo($x) = AA do
			foo($x) := BB

default init s0:
	function foo($x in Boolean) = AA