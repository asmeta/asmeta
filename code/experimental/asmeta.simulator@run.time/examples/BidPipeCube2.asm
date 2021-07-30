asm BidPipeCube2

import StandardLibrary

signature:
	enum domain Int = {ZERO | ONE | TWO | THREE | FOUR | FIVE}
	dynamic out x: Int 
	dynamic monitored cube: Integer

definitions:

	//invariant over x:x < 20

	main rule r_Main =
		par	
			if cube >= 30 then
				x := FOUR
			endif
			if cube < 30 then
				x := FIVE
			endif
	endpar

default init s0:
	function x = FIVE