asm functionOverload

import StandardLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic controlled foo: Boolean -> Boolean
	dynamic controlled foo: Prod(Boolean, Boolean) -> Boolean

definitions:

	main rule r_Main =
		par
			foo := false
			foo(false) := false
			foo(false, false) := false
		endpar

default init s0:
	function foo = true
	function foo($a in Boolean) = true
	function foo($a in Boolean, $b in Boolean) = true