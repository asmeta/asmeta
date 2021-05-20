asm ComposeComposeSquare

import StandardLibrary

signature:
	dynamic out square_square_square: Integer 
	dynamic monitored square_square: Integer

definitions:
	main rule r_Main =
		square_square_square := square_square*square_square

default init s0:
	function square_square_square = 0