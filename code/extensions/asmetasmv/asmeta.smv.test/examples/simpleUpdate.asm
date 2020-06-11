asm simpleUpdate

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: Boolean -> EnumDom

definitions:
	main  rule r_Main = 
		par
			foo(true) := AA
			foo(false) := CC
		endpar

default init s0:
	function foo($b in Boolean) = BB
