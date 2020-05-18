asm letRule

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom
	dynamic controlled fooA: EnumDom

definitions:

	main rule r_Main =
		let ($x = foo) in
			fooA := $x
		endlet

default init s0:
	function foo = AA