asm BidPipeCube1

import StandardLibrary

signature:
	enum domain Int = {ZERO | ONE | TWO | THREE | FOUR | FIVE}
	enum domain State = {ON | OFF}
	dynamic out cube: Integer
	dynamic out y: State
	dynamic out z: Int
	dynamic monitored x: Int
definitions:

	//invariant over cube:cube < 70

	main rule r_Main =
		par	
			if x = ZERO then
				par
					cube := 0
					y := ON
					z := ZERO
				endpar
			endif
			if x = ONE then
				par
					cube := 1
					y := OFF
					z := ONE
				endpar
			endif
			if x = TWO then
				par
					cube := 8
					y := ON
					z := TWO
				endpar
			endif
			if x = THREE then
				par
					cube := 27
					y := OFF
					z := THREE
				endpar
			endif
			if x = FOUR then
				par
					cube := 64
					y := ON
					z := FOUR
				endpar
			endif
			if x = FIVE then
				par
					cube := 125
					y := OFF
					z := FIVE
				endpar
			endif
	endpar

default init s0:
	function cube = 0
	function y = OFF
	function z = ZERO