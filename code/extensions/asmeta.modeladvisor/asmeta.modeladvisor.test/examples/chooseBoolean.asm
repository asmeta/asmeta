asm chooseBoolean

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean

definitions:

	main rule r_Main =
		choose $b in Boolean with true do
			if $b then
				foo := true
			else
				foo := false
			endif

default init s0:
	function foo = true