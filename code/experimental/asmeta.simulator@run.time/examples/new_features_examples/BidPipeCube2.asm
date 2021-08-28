asm BidPipeCube2

import StandardLibrary

signature:
	enum domain Int = {ZERO | ONE | TWO | THREE | FOUR | FIVE}
	enum domain State = {ON | OFF}
	dynamic out x: Int 
	dynamic monitored cube: Integer
	dynamic monitored y: State
definitions:

	//invariant over x:x < 20

	main rule r_Main =
		par	
			if (cube >= 30 and y = OFF) then
				x := FOUR
			endif
			if (cube < 30 and y = OFF) then
				x := FIVE
			endif
			if (cube >= 30 and y = ON) then 
				x := ZERO
			endif
			if (cube < 30 and y = ON) then
				x := THREE
			endif
		endpar

default init s0:
	function x = FIVE