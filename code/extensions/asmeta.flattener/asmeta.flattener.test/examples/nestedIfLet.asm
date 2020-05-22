asm nestedIfLet

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
	dynamic monitored d: Boolean
	dynamic controlled foo: MyDomain -> MyDomain
	
	
definitions:
	domain MyDomain = {1..4}
	
	main rule r_Main =
		if a then
			par
				k := 2
				x := 3
				if b then
					par	
						x := 2
						let ($x = 2) in
							par
							foo($x) := $x
							if c then
								if d then
									z := 3
								else
									z := 2
								endif
							endif
							endpar
						endlet
					endpar
				endif
			endpar
		else
			if c then
				if d then
					z := 3
				else
					z := 2
				endif
			endif
		endif

default init s0:
	function x = 1
	function y = 1
	function z = 1
	function k = 1
	function f = 1
	function foo($x in MyDomain) = 1