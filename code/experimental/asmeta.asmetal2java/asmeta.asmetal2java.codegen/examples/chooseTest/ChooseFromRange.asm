asm ChooseFromRange

import ../STDL/StandardLibrary

signature:
	controlled count : Integer

definitions:

	main rule r_Main =
		choose $s in {-10:10} do
			if $s >= 0 then count := 1 else count := 2 endif

default init s0:
	function count = -1
