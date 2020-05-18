asm initStateAbstract

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	controlled x: ConcrDom
	monitored mon: ConcrDom
	
definitions:
	domain ConcrDom = {0, 1, 2}

	main rule r_Main =
		skip

default init s0:
	function x = mon