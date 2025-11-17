asm ChooseFromSubset

import ../STDL/StandardLibrary

signature:
	domain Sub subsetof Integer
	controlled count : Sub

definitions:
	domain Sub = {-10:10}

	main rule r_Main =
		choose $s in Sub do
			if $s >= 0 then count := 1 else count := 2 endif

default init s0:
	function count = -1
