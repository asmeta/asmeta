asm ChooseFromDT

import ../STDL/StandardLibrary

signature:
	controlled count : Integer

definitions:

	main rule r_Main =
		choose $b in Boolean do
			if $b then count := 1 else count := 2 endif

default init s0:
	function count = -1

