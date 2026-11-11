asm Counter

import ../StandardLibrary

signature:
	domain Count subsetof Integer
	controlled counter: Count

definitions:
	domain Count = {0 : 2}

	main rule r_Main =
		counter := counter + 1

default init s0:
	function counter = 0
