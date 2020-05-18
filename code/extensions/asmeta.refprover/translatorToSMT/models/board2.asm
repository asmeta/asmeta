asm board2

import StandardLibrary

signature:
	domain Coord subsetof Integer
	controlled board: Prod(Coord, Coord) -> Boolean
	
definitions:
	domain Coord = {1..2}

	main rule r_Main =
		forall $x in Coord with true do
			choose $y in Coord with not(board($x, $y)) do
				board($x, $y):=  true

default init s0:
	function board($x in Coord, $y in Coord) = false