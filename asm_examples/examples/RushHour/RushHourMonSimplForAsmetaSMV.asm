asm RushHourMonSimplForAsmetaSMV

//https://en.wikipedia.org/wiki/Rush_Hour_(puzzle)
//simplified version of RushHourMonForAsmetaSMV
//it should speed up the model checking

//the choice of the car to be moved and the direction
//is done (in the main rule) with two monitored functions

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary
import ../../STDL/LTLLibrary

signature:
	enum domain Dir = {FIRSTDIR | SECONDDIR}
	enum domain CarDir = {NS | EW}
	domain Coord subsetof Integer
	domain Num subsetof Integer
	domain Car subsetof Integer
	dynamic controlled board: Prod(Coord, Coord) -> Car
	dynamic monitored carMon: Car
	dynamic monitored dirMon: Dir
	static carDir: Car -> CarDir
	derived isMovePermitted: Prod(Car, Dir) -> Boolean
	static rMove: Prod(Car, Dir) -> Num
	static cMove: Prod(Car, Dir) -> Num
	static rMoveP: Prod(Car, Dir) -> Num
	static cMoveP: Prod(Car, Dir) -> Num
	static redCar: Num
	derived redCarAtExit: Boolean

definitions:
	domain Coord = {0 : 5}
	domain Num = {1, 5, 0}
	domain Car = {1 :
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

	//puzzle 3 (beginner)
	function carDir($car in Car) = at({1 -> EW, 2 -> NS, 3 -> EW, 4 -> NS, 5 -> NS, 6 -> EW}, $car)
	//puzzle 40 (expert)
	//function carDir($car in Car) = at({1 -> NS, 2 -> EW, 3 -> NS, 4 -> NS, 5 -> NS, 6 -> NS, 7 -> EW, 8 -> EW, 9 -> NS, 10 -> NS, 11 -> EW, 12 -> EW, 13 -> EW}, $car)

	function rMove($car in Car, $d in Dir) =
		if(carDir($car) = NS) then
			if($d = FIRSTDIR) then
				5
			else
				1
			endif
		else
			0
		endif

	function cMove($car in Car, $d in Dir) =
		if(carDir($car) = EW) then
			if($d = FIRSTDIR) then
				5
			else
				1
			endif
		else
			0
		endif
		
	function rMoveP($car in Car, $d in Dir) =
		if($d = FIRSTDIR) then
			rMove($car, SECONDDIR)
		else
			rMove($car, FIRSTDIR)
		endif

	function cMoveP($car in Car, $d in Dir) =
		if($d = FIRSTDIR) then
			cMove($car, SECONDDIR)
		else
			cMove($car, FIRSTDIR)
		endif

	function isMovePermitted($car in Car, $dir in Dir) =
		if(carDir($car) = NS) then
			if($dir = FIRSTDIR) then
				(exist $r in Coord, $c in Coord with $r > 0 and board($r,$c) = $car and isUndef(board(($r+5) mod 6, $c)))
			else
				(exist $r1 in Coord, $c1 in Coord with $r1 < 5 and board($r1,$c1) = $car and isUndef(board(($r1+1) mod 6,$c1)))
			endif
		else
			if($dir = FIRSTDIR) then
				(exist $r3 in Coord, $c3 in Coord with $c3 > 0 and board($r3,$c3) = $car and isUndef(board($r3,($c3+5) mod 6)))
			else
				(exist $r2 in Coord, $c2 in Coord with $c2 < 5 and board($r2,$c2) = $car and isUndef(board($r2,($c2+1) mod 6)))
			endif
		endif

	function redCar = 1 //the red car has always number 1 in all the puzzles

	function redCarAtExit =
		//board(2,5) = redCar means that the red card has reached the exit (note that cars cannot rotate)
		board(2,5) = redCar

	rule r_move($car in Car, $dir in Dir) =
		forall $r in Coord, $c in Coord with board($r,$c) = $car do
			par
				//the let rule was needed in the old version of AsmetaSMV
				/*//the first empty cell is filled with the selected car
				let ($x = rMove($car, $dir), $y=cMove($car, $dir)) in
					if isUndef(board(($r + $x) mod 6,($c + $y) mod 6)) then
						board(($r + $x) mod 6,($c + $y) mod 6) := $car
					endif
				endlet
				//the cell at the end of the car is emptied
				let ($x1 = rMoveP($car, $dir), $y1=cMoveP($car, $dir)) in
					if(board(($r + $x1) mod 6,($c + $y1) mod 6) != $car) then
						board($r, $c) := undef
					endif
				endlet*/
				//the first empty cell is filled with the selected car
				if isUndef(board(($r + rMove($car, $dir)) mod 6,($c + cMove($car, $dir)) mod 6)) then
					board(($r + rMove($car, $dir)) mod 6,($c + cMove($car, $dir)) mod 6) := $car
				endif
				//the cell at the end of the car is emptied
				if(board(($r + rMoveP($car, $dir)) mod 6,($c + cMoveP($car, $dir)) mod 6) != $car) then
					board($r, $c) := undef
				endif
			endpar

	CTLSPEC ef(redCarAtExit)
	LTLSPEC f(redCarAtExit)

	main rule r_Main =
		if not(redCarAtExit) then
			let ($car=carMon, $dir=dirMon) in
				if isMovePermitted($car, $dir) then
					r_move[$car, $dir]
				endif
			endlet
		endif
		

default init s0:
	//puzzle 3 (beginner)
	function board($r in Coord, $c in Coord) = at({
													
																(2,1) -> redCar, (2,2) -> redCar, (2,3) -> 2,
																(3,1) -> 3,      (3,2) -> 3,      (3,3) -> 2,			  (3,5) -> 4,
																(4,1) -> 5,      				  (4,3) -> 2, 			  (4,5) -> 4,
																(5,1) -> 5,      (5,2) -> 6,      (5,3) -> 6, 			  (5,5) -> 4}, ($r,$c))
	//puzzle 11 (intermediate)
	/*function board($r in Coord, $c in Coord) = at({ (0,0) -> 2, (0,1) -> 3,      (0,2) -> 3,      (0,3) -> 4,
													(1,0) -> 2,  (1,3) -> 4,
													(2,0) -> 2, (2,1) -> redCar, (2,2) -> redCar, (2,3) -> 4,
													(3,2) -> 5,      (3,3) -> 6, (3,4) -> 6, (3,5) -> 6,
													(4,2) -> 5,      (4,5) -> 8,
													(5,2) -> 7,      (5,3) -> 7, (5,4) -> 7, (5,5) -> 8}, ($r,$c))*/
	//puzzle 40 (expert)
	/*function board($r in Coord, $c in Coord) = at({ (0,0) -> 7, (0,1) -> 2, (0,2) -> 2, (0,4) -> 3,
													(1,0) -> 7, (1,1) -> 4, (1,2) -> 5, (1,4) -> 3,      (1,5) -> 6,
													(2,0) -> 7, (2,1) -> 4, (2,2) -> 5, (2,3) -> redCar, (2,4) -> redCar, (2,5) -> 6,
													(3,0) -> 8, (3,1) -> 8, (3,2) -> 8, (3,3) -> 9,            (3,5) -> 6,
													(4,2) -> 10, (4,3) -> 9,     (4,4) -> 11,     (4,5) -> 11,
													(5,0) -> 12, (5,1) -> 12, (5,2) -> 10, (5,3) -> 13,  (5,4) -> 13}, ($r,$c))*/
