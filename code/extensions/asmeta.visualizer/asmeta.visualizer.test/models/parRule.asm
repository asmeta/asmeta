asm parRule

import StandardLibrary

signature:
	controlled x: Integer
	controlled y: Integer

definitions:

	main rule r_Main =
		par
			x := 10
			skip
			y := 5
		endpar