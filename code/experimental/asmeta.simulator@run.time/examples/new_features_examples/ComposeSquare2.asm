asm ComposeSquare2

import StandardLibrary

signature:
	enum domain Int = {ZERO | ONE | TWO | THREE | FOUR | FIVE}
	dynamic out square_square: Integer 
	dynamic monitored square: Integer
	dynamic monitored y: Int

definitions:
	main rule r_Main =
	if (y=ZERO) then
		square_square := square*square
	endif

default init s0:
	function square_square = 0
