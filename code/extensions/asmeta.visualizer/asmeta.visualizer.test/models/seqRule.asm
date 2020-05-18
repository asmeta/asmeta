asm seqRule

import StandardLibrary

signature:
	controlled x: Integer

definitions:

	main rule r_Main =
		seq
			x := 10
			skip
			x := 5
		endseq