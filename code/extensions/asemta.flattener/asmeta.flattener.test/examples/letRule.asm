asm letRule

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean

definitions:

	main rule r_main =
		let ($x = true) in
			foo := $x
		endlet
		

default init s0:
	function foo = false