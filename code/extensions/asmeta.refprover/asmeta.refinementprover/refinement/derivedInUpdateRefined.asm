asm derivedInUpdateRefined

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	monitored k: ConcrDom
	controlled y: Boolean
	derived der: Boolean
	
definitions:
	domain ConcrDom = {0 .. 5}

	function der = k > 3

	main rule r_Main =
		y := der

default init s0:
	function y = false