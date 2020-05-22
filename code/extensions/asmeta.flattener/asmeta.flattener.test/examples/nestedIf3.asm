asm nestedIf3

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled x: MyDomain
	dynamic controlled y: MyDomain
	dynamic monitored a: Boolean
	dynamic monitored b: Boolean
	
definitions:
	domain MyDomain = {1..4}
	
	main rule r_Main =
		par
			if a and b then
				x := 2
			endif
			if b and a then
				y := 3
			endif
		endpar

default init s0:
	function x = 1
	function y = 1