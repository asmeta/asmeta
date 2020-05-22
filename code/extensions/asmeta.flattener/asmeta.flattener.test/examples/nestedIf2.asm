asm nestedIf2

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled k: MyDomain
	dynamic monitored a: Boolean
	dynamic monitored b: Boolean
	
definitions:
	domain MyDomain = {1..4}
	
	main rule r_Main =
		if a then
			if b then
				k := 2
			else
				k := 3
			endif
		else
			k := 4
		endif

default init s0:
	function k = 1
