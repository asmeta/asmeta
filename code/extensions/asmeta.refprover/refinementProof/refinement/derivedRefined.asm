asm derivedRefined

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	controlled x: ConcrDom
	derived der: Boolean
	
definitions:
	domain ConcrDom = {0 .. 5}

	function der = x > 2

	main rule r_Main =
		x := (x + 1) mod 6

default init s0:
	function x = 0