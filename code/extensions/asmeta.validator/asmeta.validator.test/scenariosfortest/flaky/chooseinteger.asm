asm chooseinteger
import StandardLibrary

signature:
	dynamic controlled foo1: Integer

definitions:

	main rule r_main = choose $s in {10} with true do foo1 := $s

default init s0:
	function foo1 = 1
