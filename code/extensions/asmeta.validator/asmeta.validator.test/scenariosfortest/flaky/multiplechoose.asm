asm multiplechoose
import StandardLibrary

signature:
	domain SubInt subsetof Integer
	dynamic controlled foo1: SubInt
	dynamic controlled foo2: Boolean

definitions:
	domain SubInt = {0:100}

	rule r_choose_boolean = choose $b in Boolean with true do foo2 := $b

	main rule r_main = par
		choose $i in SubInt with true do foo1 := $i
		r_choose_boolean[]
	endpar

default init s0:
	function foo1 = 0
	function foo2 = true
