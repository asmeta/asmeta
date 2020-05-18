asm macroCallRule2

import StandardLibrary

signature:

definitions:
	rule r_c =
		skip
	
	rule r_b =
		r_c[]

	rule r_a =
		r_c[]

	main rule r_Main =
		par
			r_a[]
			r_b[]
		endpar