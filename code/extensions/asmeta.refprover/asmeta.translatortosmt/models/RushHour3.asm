asm RushHour3

import StandardLibrary
//without modulo operation and without functions rMove, cMove, rMoveP and cMoveP
signature:
	enum domain Dir = {NORTH | SOUTH | EAST | WEST}
	domain Coord subsetof Integer
	domain Car subsetof Integer
	dynamic controlled board: Prod(Coord, Coord) -> Car
	dynamic chooseVar0: Prod(Car, Dir) -> Boolean
	derived isNextCellFree: Prod(Car, Dir) -> Boolean
	derived isMovePermitted: Prod(Car, Dir) -> Boolean
	derived redCarAtExit: Boolean

definitions:
	domain Coord = {0:1}
	domain Car = {0:1}

	function isDirMachine($car in Car, $dir in Dir) =
		if $dir = NORTH or $dir = SOUTH then
			(exist $c in Coord with board(0, $c) = $car and board(1, $c) = $car)
		else
			(exist $r2 in Coord with board($r2, 0) = $car and board($r2, 1) = $car)
		endif

	function isNextCellFree($car in Car, $dir in Dir) =
		switch($dir)
			case NORTH:
				(exist $c in Coord with board(1, $c) = $car and board(0, $c) = 0)
			case SOUTH:
				(exist $c1 in Coord with board(0, $c1) = $car and board(1, $c1) = 0)
			case WEST:
				(exist $r3 in Coord with board($r3, 1) = $car and board($r3, 0) = 0)
			case EAST:
				(exist $r2 in Coord with board($r2, 0) = $car and board($r2, 1) = 0)
		endswitch

	function isMovePermitted($car in Car, $dir in Dir) =
		isDirMachine($car, $dir) and isNextCellFree($car, $dir)

	function redCarAtExit =
		board(1, 1) = 1

	rule r_move($car in Car, $dir in Dir) =
		forall $r in Coord, $c in Coord with board($r,$c) = $car do
			switch($dir)
				case NORTH:
					if $r = 1 then
						par
							board(1, $c) := 0
							board(0, $c) := $car
						endpar
					endif
				case SOUTH:
					if $r = 0 then
						par
							board(0, $c) := 0
							board(1, $c) := $car
						endpar
					endif
				case WEST:
					if $c = 1 then
						par
							board($r, 0) := $car
							board($r, 1) := 0
						endpar
					endif
				case EAST:
					if $c = 0 then
						par
							board($r, 1) := $car
							board($r, 0) := 0
						endpar
					endif
			endswitch

	main rule r_Main =
		if not(redCarAtExit) then
			//choose a car $car ($car != 0) and a direction $dir, such that
			//the car can move in the selected direction (isMovePermitted($car, $dir))
			choose $car in Car, $dir in Dir with $car != 0 and isMovePermitted($car, $dir) do
				r_move[$car, $dir]
		endif

default init s0:
	function board($r in Coord, $c in Coord) = at({ (0,0) -> 1, (0,1) -> 0, (1,0) -> 0, (1,1) -> 0}, ($r,$c))