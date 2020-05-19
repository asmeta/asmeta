asm swapBoard

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary

signature:
	enum domain SwapDomain = {CELL | ROW}
	domain Coord subsetof Integer
	domain Num subsetof Integer
	dynamic controlled board: Prod(Coord, Coord) -> Num
	dynamic monitored swap: SwapDomain

definitions:
	domain Coord = {1 : 3}
	domain Num = {2 : 6}

	rule r_randomlySwapCells =
		choose $r1 in Coord, $c1 in Coord, $r2 in Coord, $c2 in Coord with $r1 != $r2 or $c1 != $c2 do
			par
				board($r1, $c1) := board($r2, $c2)
				board($r2, $c2) := board($r1, $c1)
			endpar

	rule r_swapRows =
		forall $c in Coord with true do
			par
				board(1, $c) := board(3, $c)
				board(3, $c) := board(1, $c) 
			endpar

	//esiste uno stato in cui sulla prima riga c'e' la sequenza (5, 5, 4)
	CTLSPEC ef(board(1, 1) = 5 and board(1, 2) = 5 and board(1, 3) = 4)
	//la somma delle celle e' sempre uguale a 36
	CTLSPEC ag(board(1, 1) + board(1, 2) + board(1, 3) + 
			   board(2, 1) + board(2, 2) + board(2, 3) +
			   board(3, 1) + board(3, 2) + board(3, 3) = 36)
	//e' sempre possibile che l'utente prima o poi decida di invertire due celle 
	CTLSPEC ag(ef(swap = CELL))

	main rule r_Main =
		if(swap = CELL) then
			r_randomlySwapCells[]
		else
			r_swapRows[]
		endif

default init s0:
	function board($r in Coord, $c in Coord) = $r + $c
