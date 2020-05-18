asm TictactoeSimple

import StandardLibrary


signature:
	domain Coord subsetof Integer
	enum domain Sign = {CROSS | NOUGHT | EMPTY}
	controlled board: Prod(Coord, Coord) -> Sign //rappresenta la scacchiera
	controlled turnUser: Boolean
	derived noSquareLeft: Boolean

definitions:
	domain Coord = {1..2}
 
 	//indica se tutte le caselle sono occupate
	function noSquareLeft =
		not (exist $x in Coord, $y in Coord with board($x, $y) = EMPTY)

	rule r_moveUser =
		choose $x in Coord, $y in Coord with board($x, $y)=EMPTY do
			board($x, $y):=  CROSS

	rule r_movePC =
		choose $x in Coord, $y in Coord with board($x, $y)=EMPTY do
			board($x, $y):=  NOUGHT

	main rule r_Main =
		par
			if turnUser then
				r_moveUser[]
			else
				r_movePC[]
			endif
			turnUser := not(turnUser)
		endpar

default init s0:
	function board($x in Coord, $y in Coord) = EMPTY
	function turnUser = true