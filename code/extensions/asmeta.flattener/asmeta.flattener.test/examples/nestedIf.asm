asm nestedIf

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled x: MyDomain
	dynamic controlled y: MyDomain
	dynamic controlled k: MyDomain
	dynamic controlled z: MyDomain
	dynamic controlled f: MyDomain
	dynamic monitored a: Boolean
	dynamic monitored b: Boolean
	dynamic monitored c: Boolean
	
definitions:
	domain MyDomain = {1:4}
	
	main rule r_Main =
		par
			if a then
				par
					k := 2
					x := 3
					if b then
						y := 4
					else
						y := 2
					endif
					f := 3
				endpar
			endif
			if c then
				z := 3
			endif
		endpar

default init s0:
	function x = 1
	function y = 1
	function z = 1
	function k = 1
