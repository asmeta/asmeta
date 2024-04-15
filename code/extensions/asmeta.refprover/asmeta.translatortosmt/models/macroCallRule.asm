asm macroCallRule

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> EnumDom

definitions:

	macro rule r_update($x in EnumDom, $y in EnumDom) =
		foo($x) := $y

	main rule r_Main =
		par
			r_update[AA, BB]
			r_update[BB, CC]
		endpar

default init s0:
	function foo($x in EnumDom) = AA