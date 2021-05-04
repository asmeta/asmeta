asm board

import StandardLibrary

signature:
	domain Coord subsetof Integer
	controlled board: Coord -> Boolean
	
definitions:
	domain Coord = {1:2}

	main rule r_Main =
		choose $x in Coord with not(board($x)) do
			board($x):=  true

default init s0:
	function board($x in Coord) = false