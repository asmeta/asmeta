asm chooseAlwaysNotEmpty

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic monitored mon: Boolean

definitions:

	main rule r_Main =
		if mon then
			//when the choose rule is executed,
			//it can always find a value for $x that
			//satisfies the guard
			choose $x in Boolean with $x = mon and $x do
				foo := not(foo)
		endif

default init s0:
	function foo = false