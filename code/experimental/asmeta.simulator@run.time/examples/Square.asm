asm Square

import StandardLibrary

signature:
	enum domain Int = {ZERO | ONE | TWO | THREE | FOUR | FIVE}
	dynamic out square: Integer 
	dynamic monitored x: Int

definitions:

	invariant over square:square < 20

	main rule r_Main =
		par	
			if x = ZERO then
				square := 0
			endif
			if x = ONE then
				square := 1
			endif
			if x = TWO then
				square := 4
			endif
			if x = THREE then
				square := 9
			endif
			if x = FOUR then
				square := 16
			endif
			if x = FIVE then
				square := 25
			endif
	endpar

default init s0:
	function square = 0