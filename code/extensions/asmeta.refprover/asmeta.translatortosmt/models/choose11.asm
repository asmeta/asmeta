asm choose11

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> Boolean

definitions:

	main rule r_Main =
		choose $x in EnumDom with not(foo($x)) do
			foo($x) := true

default init s0:
	function foo($x in EnumDom) = false