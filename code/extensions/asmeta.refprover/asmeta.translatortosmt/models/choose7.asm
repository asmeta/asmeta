asm choose7

import StandardLibrary

signature:
	dynamic controlled foo: Boolean -> Boolean

definitions:

	main rule r_Main =
		choose $x in Boolean with false do
			foo($x) := true

default init s0:
	function foo($x in Boolean) = false