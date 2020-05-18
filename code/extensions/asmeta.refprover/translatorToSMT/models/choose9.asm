asm choose9

import StandardLibrary

signature:
	dynamic controlled foo: Boolean -> Boolean

definitions:

	main rule r_Main =
		choose $x in Boolean with true do
			foo($x) := not(foo($x))

default init s0:
	function foo($x in Boolean) = false