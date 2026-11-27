asm chooseAlwaysNotEmptyIfNone

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic monitored mon: Boolean

definitions:

	main rule r_Main =
		if mon then
			//when the choose rule is executed,
			//it can always find a value for $x that
			//satisfies the guard (always not empty) TRUE
			choose $x in Boolean with $x = mon and $x do
				foo := not(foo)
			// this is never executed
			ifnone
			    foo := true
		endif

default init s0:
	function foo = false
