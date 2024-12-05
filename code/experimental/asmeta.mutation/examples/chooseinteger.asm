asm chooseinteger
import ../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo1: Integer

definitions:

	main rule r_main = choose $s in Integer with true do foo1 := $s

default init s0:
	function foo1 = 1
