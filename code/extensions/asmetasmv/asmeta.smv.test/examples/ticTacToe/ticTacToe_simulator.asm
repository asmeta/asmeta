asm ticTacToe_simulator

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

//It permits to play Tic-Tac-Toe with the computer.
//At each step, the user and the computer, alternatively, make a move.
//The move of the user is specified through the monitored functions userChoiceX
//and userChoiceY.
//The computer makes its move using a choose rule.
//The board is represented by the binary function "board". An undef location
//represents an empty cell.

signature:
	domain Coord subsetof Integer
	enum domain Sign = {CROSS | NOUGHT}
	enum domain Status = {TURN_USER | TURN_PC}

	controlled board: Prod(Coord, Coord) -> Sign
	controlled status: Status
	monitored userChoiceX: Coord //coordinate x chosen by the user
	monitored userChoiceY: Coord //coordinate y chosen by the user
	derived winner: Sign -> Boolean
	derived endOfGame: Boolean

definitions:
	domain Coord = {1:3}

	function winner($s in Sign) =
		(exist $r in Coord with (forall $c in Coord with board($r, $c) = $s)) or
		(exist $c2 in Coord with (forall $r2 in Coord with board($r2, $c2) = $s)) or
		(forall $d in Coord with board($d, $d) = $s) or
		(forall $d1 in Coord with board($d1, 4 - $d1) = $s)

	function endOfGame =
		(exist $s in Sign with winner($s)) or
		(forall $r in Coord, $c in Coord with isDef(board($r, $c)))

	rule r_moveUser =
		let ($x = userChoiceX, $y = userChoiceY) in
			if(isUndef(board($x, $y))) then
				par
					board($x, $y) := CROSS
					status := TURN_PC
				endpar
			endif
		endlet

	rule r_movePC =
		par
			choose $x in Coord, $y in Coord with isUndef(board($x, $y)) do
				board($x, $y) :=  NOUGHT
			status := TURN_USER
		endpar

	CTLSPEC not(ef(winner(CROSS)))//CROSS can win
	CTLSPEC ef(winner(NOUGHT))//NOUGHT can win
	CTLSPEC ef(not(winner(NOUGHT)) and not(winner(CROSS)) and endOfGame) //TIE
	CTLSPEC ag(not(winner(CROSS) and winner(NOUGHT)))
	CTLSPEC e(not(winner(CROSS)) and not(winner(NOUGHT)), winner(CROSS) xor winner(NOUGHT))
	CTLSPEC ag((winner(CROSS) and not(winner(NOUGHT))) implies ag((winner(CROSS) and not(winner(NOUGHT)))))
	CTLSPEC ag((winner(NOUGHT) and not(winner(CROSS))) implies ag((winner(NOUGHT) and not(winner(CROSS)))))

	main rule r_Main =
		if(not(endOfGame)) then
			if(status = TURN_USER) then
				r_moveUser[]
			else
				r_movePC[]
			endif
		endif

default init s0:
	function status = TURN_USER