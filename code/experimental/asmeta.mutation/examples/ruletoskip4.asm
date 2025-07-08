// ONLY IF THEN ELSE
asm ruletoskip4
import ../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo1: Integer
	dynamic controlled foo2: Integer

definitions:

	main rule r_main =
			if foo1 > 2 then foo1 := 5 
			else foo1:=7 
			endif

default init s0:
	function foo1 = 1
	function foo2 = 1