asm mutualExclusive

import StandardLibrary

signature:
	enum domain Mode = {MODE_A | MODE_B | MODE_C}
	dynamic controlled mode: Mode

definitions:

	macro rule r_a =
		skip
		
	macro rule r_b =
		skip
	
	macro rule r_c =
		skip
		
	main rule r_Main =
		par 
			if mode = MODE_A then
				r_a[]
			endif	
			if mode = MODE_B then
				r_b[]
			endif
			if mode = MODE_C then
				r_c[]
			endif
		endpar	

default init s0:
	function mode = MODE_A