asm ChooseFromSubset2

import ../STDL/StandardLibrary

signature:
	domain Sub subsetof Integer
	controlled count : Sub

definitions:
	domain Sub = {-10:10}

	main rule r_Main =
		choose $s in Sub do
			count := $s

default init s0:
	function count = -1
