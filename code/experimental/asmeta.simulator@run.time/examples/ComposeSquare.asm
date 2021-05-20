asm ComposeSquare

import StandardLibrary

signature:
	dynamic out square_square: Integer 
	dynamic monitored square: Integer

definitions:
	main rule r_Main =
		square_square := square*square

default init s0:
	function square_square = 0
