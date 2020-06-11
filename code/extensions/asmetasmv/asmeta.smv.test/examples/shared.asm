asm shared

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic shared foo: Boolean
	dynamic shared fooC: Boolean
		
definitions:
		
	main  rule r_Main =
		par
			fooA := not(fooA)
			if(fooA) then
				foo := not(foo)
			endif
			if(foo) then
				fooB := true
			endif
			fooC := true
			if(fooC) then
				skip
			endif
		endpar

default init s0:
	function fooA = true