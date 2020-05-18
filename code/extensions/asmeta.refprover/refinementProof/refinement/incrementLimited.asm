asm incrementLimited

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	controlled x: ConcrDom
	
definitions:
	domain ConcrDom = {0..10}

	main rule r_Main =
		if x != 10 then
			x := x + 1
		endif

default init s0:
	function x = 0