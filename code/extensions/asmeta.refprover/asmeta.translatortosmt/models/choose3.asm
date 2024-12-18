asm choose3

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom

definitions:
	domain ConcrDom = {1 : 3}

	main rule r_Main =
		choose $x in ConcrDom, $y in ConcrDom with $x + $y = 3 do
			foo := $x

default init s0:
	function foo = 1
