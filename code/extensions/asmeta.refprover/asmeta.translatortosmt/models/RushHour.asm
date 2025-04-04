asm RushHour

import StandardLibrary

signature:
	enum domain Dir = {NORTH | SOUTH | EAST | WEST}
	domain Coord subsetof Integer
	domain Num subsetof Integer
	domain Car subsetof Integer
	dynamic controlled board: Prod(Coord, Coord) -> Car
	//dynamic chooseVar0: Prod(Car, Dir) -> Boolean
	derived isNextCellFree: Prod(Car, Dir) -> Boolean
	derived isMovePermitted: Prod(Car, Dir) -> Boolean
	derived isDirMachine: Prod(Car, Dir) -> Boolean
	static rMove: Dir -> Num
	static cMove: Dir -> Num
	static rMoveP: Dir -> Num
	static cMoveP: Dir -> Num
	static redCar: Num
	derived redCarAtExit: Boolean

definitions:
	domain Coord = {0:5}
	domain Num = {1, 5, 0}
	domain Car = {0:
						 6 //puzzle 3
						// 7 //puzzles 4, 21
						// 8 //puzzles 1, 11, 12, 19
						// 9 //puzzles 7, 18
						//10 //puzzles 20, 23, 24, 27, 30
						//11 //puzzles 2, 5, 6, 16, 31, 32, 35, 38
						//12 //puzzles 9, 10, 14, 17, 22, 26, 28, 29, 33, 34, 36, 39
						//13 //puzzles 13, 25, 37, 40
						//14 //puzzles 8, 15
				}

	function isDirMachine($car in Car, $dir in Dir) =
		if($dir = NORTH or $dir = SOUTH) then
			(exist $r in Coord, $c in Coord with board($r,$c) = $car and board(($r + 1) mod 6, $c) = $car)
		else
			(exist $r2 in Coord, $c2 in Coord with board($r2,$c2) = $car and board($r2, ($c2 + 1) mod 6) = $car)
		endif

	function rMove($d in Dir) =
		if($d = NORTH) then
			5
		else if($d = SOUTH) then
			1
		else
			0
		endif endif

	function cMove($d in Dir) =
		if($d = WEST) then
			5
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
				(exist $r in Coord, $c in Coord with $r > 0 and board($r,$c) = $car and board(($r+5) mod 6,$c) = 0)
			case SOUTH:
				(exist $r1 in Coord, $c1 in Coord with $r1 < 5 and board($r1,$c1) = $car and board(($r1+1) mod 6,$c1) = 0)
			case WEST:
				(exist $r3 in Coord, $c3 in Coord with $c3 > 0 and board($r3,$c3) = $car and board($r3,($c3+5) mod 6) = 0)
			case EAST:
				(exist $r2 in Coord, $c2 in Coord with $c2 < 5 and board($r2,$c2) = $car and board($r2,($c2+1) mod 6) = 0)
		endswitch

	function isMovePermitted($car in Car, $dir in Dir) =
		isDirMachine($car, $dir) and isNextCellFree($car, $dir)

	function redCar = 1 //the red car has always number 1 in all the puzzles

	function redCarAtExit =
		//board(2,5) = redCar means that the red card has reached the exit (note that cars cannot rotate)
		board(2,5) = redCar

	rule r_move($car in Car, $dir in Dir) =
		forall $r in Coord, $c in Coord with board($r,$c) = $car do
			par
				if(board(($r + rMove($dir)) mod 6,($c + cMove($dir)) mod 6) = 0) then
					board(($r + rMove($dir)) mod 6,($c + cMove($dir)) mod 6) := $car
				endif
				if(board(($r + rMoveP($dir)) mod 6,($c + cMoveP($dir)) mod 6) != $car) then
					board($r,$c) := 0
				endif
			endpar

	//to check that no car is created/destroyed/reduced/enlarged
	//puzzle 40 (expert)
	//invariant inv_0 over board: size(< $r in Coord, $c in Coord | board($r,$c)=0 : board($r,$c)>)=7 //why the bag term requires bag domains as domains for the range variables?
	/*invariant inv_0 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=0 : $r*6+$c})=7
	invariant inv_1 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=redCar : $r*6+$c})=2
	invariant inv_2 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=2 : $r*6+$c})=2
	invariant inv_3 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=3 : $r*6+$c})=2
	invariant inv_4 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=4 : $r*6+$c})=2
	invariant inv_5 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=5 : $r*6+$c})=2
	invariant inv_6 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=6 : $r*6+$c})=3
	invariant inv_7 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=7 : $r*6+$c})=3
	invariant inv_8 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=8 : $r*6+$c})=3
	invariant inv_9 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=9 : $r*6+$c})=2
	invariant inv_10 over board: size({ $r in Coord, $c in Coord | board($r,$c)=10: $r*6+$c})=2
	invariant inv_11 over board: size({ $r in Coord, $c in Coord | board($r,$c)=11: $r*6+$c})=2
	invariant inv_12 over board: size({ $r in Coord, $c in Coord | board($r,$c)=12: $r*6+$c})=2
	invariant inv_13 over board: size({ $r in Coord, $c in Coord | board($r,$c)=13: $r*6+$c})=2*/
	//puzzle 3 (beginner)
	/*invariant inv_0 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=0 : $r*6+$c})=22
	invariant inv_1 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=redCar : $r*6+$c})=2
	invariant inv_2 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=2 : $r*6+$c})=3
	invariant inv_3 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=3 : $r*6+$c})=2
	invariant inv_4 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=4 : $r*6+$c})=3
	invariant inv_5 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=5 : $r*6+$c})=2
	invariant inv_6 over board:  size({ $r in Coord, $c in Coord | board($r,$c)=6 : $r*6+$c})=2*/

	main rule r_Main =
		if not(redCarAtExit) then
			//choose a car $car ($car != 0) and a direction $dir, such that
			//the car can move in the selected direction (isMovePermitted($car, $dir))
			choose $car in Car, $dir in Dir with $car != 0 and isMovePermitted($car, $dir) do
				r_move[$car, $dir]
		endif

default init s0:
	//puzzle 3 (beginner)
	function board($r in Coord, $c in Coord) = at({ (0,0) -> 0, (0,1) -> 0,      (0,2) -> 0,      (0,3) -> 0, (0,4) -> 0, (0,5) -> 0,
													(1,0) -> 0, (1,1) -> 0,      (1,2) -> 0,      (1,3) -> 0, (1,4) -> 0, (1,5) -> 0,
													(2,0) -> 0, (2,1) -> redCar, (2,2) -> redCar, (2,3) -> 2, (2,4) -> 0, (2,5) -> 0,
													(3,0) -> 0, (3,1) -> 3,      (3,2) -> 3,      (3,3) -> 2, (3,4) -> 0, (3,5) -> 4,
													(4,0) -> 0, (4,1) -> 5,      (4,2) -> 0,      (4,3) -> 2, (4,4) -> 0, (4,5) -> 4,
													(5,0) -> 0, (5,1) -> 5,      (5,2) -> 6,      (5,3) -> 6, (5,4) -> 0, (5,5) -> 4}, ($r,$c))
	//puzzle 11 (intermediate)
	/*function board($r in Coord, $c in Coord) = at({ (0,0) -> 2, (0,1) -> 3,      (0,2) -> 3,      (0,3) -> 4, (0,4) -> 0, (0,5) -> 0,
													(1,0) -> 2, (1,1) -> 0,      (1,2) -> 0,      (1,3) -> 4, (1,4) -> 0, (1,5) -> 0,
													(2,0) -> 2, (2,1) -> redCar, (2,2) -> redCar, (2,3) -> 4, (2,4) -> 0, (2,5) -> 0,
													(3,0) -> 0, (3,1) -> 0,      (3,2) -> 5,      (3,3) -> 6, (3,4) -> 6, (3,5) -> 6,
													(4,0) -> 0, (4,1) -> 0,      (4,2) -> 5,      (4,3) -> 0, (4,4) -> 0, (4,5) -> 8,
													(5,0) -> 0, (5,1) -> 0,      (5,2) -> 7,      (5,3) -> 7, (5,4) -> 7, (5,5) -> 8}, ($r,$c))*/
	//puzzle 40 (expert)
	/*function board($r in Coord, $c in Coord) = at({ (0,0) -> 7, (0,1) -> 2, (0,2) -> 2, (0,3) -> 0,      (0,4) -> 3,      (0,5) -> 0,
													(1,0) -> 7, (1,1) -> 4, (1,2) -> 5, (1,3) -> 0,      (1,4) -> 3,      (1,5) -> 6,
													(2,0) -> 7, (2,1) -> 4, (2,2) -> 5, (2,3) -> redCar, (2,4) -> redCar, (2,5) -> 6,
													(3,0) -> 8, (3,1) -> 8, (3,2) -> 8, (3,3) -> 9,      (3,4) -> 0,      (3,5) -> 6,
													(4,0) -> 0, (4,1) -> 0, (4,2) -> 10, (4,3) -> 9,     (4,4) -> 11,     (4,5) -> 11,
													(5,0) -> 12, (5,1) -> 12, (5,2) -> 10, (5,3) -> 13,  (5,4) -> 13,     (5,5) -> 0}, ($r,$c))*/
