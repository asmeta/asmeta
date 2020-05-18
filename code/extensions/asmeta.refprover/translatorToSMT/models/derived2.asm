asm derived2

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom
	derived der: ConcrDom

definitions:
	domain ConcrDom = {0 .. 2}

	function der = (foo + 1) mod 3 

	main rule r_Main =
		foo := der

default init s0:
	function foo = 0