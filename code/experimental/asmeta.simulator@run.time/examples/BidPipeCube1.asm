asm BidPipeCube1

import StandardLibrary

signature:
	enum domain Int = {ZERO | ONE | TWO | THREE | FOUR | FIVE}
	dynamic out cube: Integer 
	dynamic monitored x: Int

definitions:

	//invariant over cube:cube < 70

	main rule r_Main =
		par	
			if x = ZERO then
				cube := 0
			endif
			if x = ONE then
				cube := 1
			endif
			if x = TWO then
				cube := 8
			endif
			if x = THREE then
				cube := 27
			endif
			if x = FOUR then
				cube := 64
			endif
			if x = FIVE then
				cube := 125
			endif
	endpar

default init s0:
	function cube = 0