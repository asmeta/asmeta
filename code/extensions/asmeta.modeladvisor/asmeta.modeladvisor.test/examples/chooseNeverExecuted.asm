asm chooseNeverExecuted

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic monitored mon: Boolean

definitions:

	main rule r_Main =
		if false then
			//the choose rule is never executed
			choose $x in Boolean with true do
				foo := not(foo)
		endif

default init s0:
	function foo = false