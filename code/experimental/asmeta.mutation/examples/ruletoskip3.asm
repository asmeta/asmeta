asm ruletoskip3
import ../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo1: Integer
	dynamic controlled foo2: Integer

definitions:

	main rule r_main =
		par
			if foo1 > 2 then 
				foo1 := 5 
			else 
				foo1:=7 
			endif
			foo2 := 5
		endpar

default init s0:
	function foo1 = 1
	function foo2 = 1