asm macroNesting

import ../../../../asm_examples/STDL/StandardLibrary

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
	dynamic monitored d: Boolean
	
definitions:
	domain MyDomain = {1..4}
	
	rule r_a =
		if c then
			if d then
				z := 3
			else
				z := 2
			endif
		endif
	
	main rule r_Main =
		if a then
			par
				k := 2
				x := 3
				if b then
					y := 4
				endif
			endpar
		else
			r_a[]
		endif

default init s0:
	function x = 1
	function y = 1
	function z = 1
	function k = 1
	function f = 1