asm RushHourSimple

import StandardLibrary

signature:
	enum domain Dir = {NORTH | SOUTH | EAST | WEST}
	domain Coord subsetof Integer
	domain Num subsetof Integer
	domain Car subsetof Integer
	dynamic controlled board: Prod(Coord, Coord) -> Car
	derived isNextCellFree: Prod(Car, Dir) -> Boolean
	derived isMovePermitted: Prod(Car, Dir) -> Boolean
	derived rMove: Dir -> Num
	derived cMove: Dir -> Num
	derived rMoveP: Dir -> Num
	derived cMoveP: Dir -> Num
	static redCar: Num
	static redCarAtExit: Boolean

definitions:
	domain Coord = {0:1}
	domain Num = {1, 0}
	domain Car = {0:2}

	function rMove($d in Dir) =
		if($d = NORTH) then
			1
		else if($d = SOUTH) then
			1
		else
			0
		endif endif

	function cMove($d in Dir) =
		if($d = WEST) then
			1
		else if($d = EAST) then
			1
		else
			0
		endif endif
		
	function rMoveP($d in Dir) =
		if($d = NORTH) then
			rMove(SOUTH)
		else if($d = SOUTH) then
			rMove(NORTH)
		else
			0
		endif endif

	function cMoveP($d in Dir) =
		if($d = WEST) then
			cMove(EAST)
		else if($d = EAST) then
			cMove(WEST)
		else
			0
		endif endif

	function isNextCellFree($car in Car, $dir in Dir) =
		switch($dir)
			case NORTH:
				(exist $r in Coord, $c in Coord with $r > 0 and board($r,$c) = $car and board(($r+1) mod 2, $c) = 0)
			case SOUTH:
				(exist $r1 in Coord, $c1 in Coord with $r1 < 1 and board($r1,$c1) = $car and board(($r1+1) mod 2, $c1) = 0)
			case WEST:
				(exist $r3 in Coord, $c3 in Coord with $c3 > 0 and board($r3,$c3) = $car and board($r3, ($c3+1) mod 2) = 0)
			case EAST:
				(exist $r2 in Coord, $c2 in Coord with $c2 < 1 and board($r2,$c2) = $car and board($r2, ($c2+1) mod 2) = 0)
		endswitch

	function isMovePermitted($car in Car, $dir in Dir) =
		isNextCellFree($car, $dir)

	function redCar = 1 //the red car has always number 1 in all the puzzles

	function redCarAtExit =
		//board(1, 1) = redCar means that the red card has reached the exit (note that cars cannot rotate)
		board(1, 1) = redCar

	rule r_move($car in Car, $dir in Dir) =
		forall $r in Coord, $c in Coord with board($r,$c) = $car do
			par
				if(board(($r + rMove($dir)) mod 2, ($c + cMove($dir)) mod 2) = 0) then
					board(($r + rMove($dir)) mod 2, ($c + cMove($dir)) mod 2) := $car
				endif
				if(board(($r + rMoveP($dir)) mod 2,($c + cMoveP($dir)) mod 2) != $car) then
					board($r,$c) := 0
				endif
			endpar

	main rule r_Main =
		if not(redCarAtExit) then
			choose $car in Car, $dir in Dir with $car != 0 and isMovePermitted($car, $dir) do
				r_move[$car, $dir]
		endif

default init s0:
	function board($r in Coord, $c in Coord) = at({ (0,0) -> redCar, (0,1) -> 0,
													(1,0) -> 0,      (1,1) -> 2}, ($r,$c))