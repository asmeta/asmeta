asm ticTacToe_simulator

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

signature:
	domain Coord subsetof Integer
	enum domain Sign = {CROSS | NOUGHT}
	enum domain Status = {TURN_USER | TURN_PC}

	controlled board: Prod(Coord, Coord) -> Sign
	controlled status: Status
	monitored userChoiceR: Coord //row chosen by the user
	monitored userChoiceC: Coord //column chosen by the user
	derived winner: Sign -> Boolean
	derived endOfGame: Boolean

definitions:
	domain Coord = {1 : 3}

	function winner($s in Sign) =
		(exist $r in Coord with (forall $c in Coord with board($r, $c) = $s)) or
		(exist $c2 in Coord with (forall $r2 in Coord with board($r2, $c2) = $s)) or
		(forall $d in Coord with board($d, $d) = $s) or
		(forall $d1 in Coord with board($d1, 4 - $d1) = $s)

	function endOfGame =
		(exist $s in Sign with winner($s)) or
		(forall $r in Coord, $c in Coord with isDef(board($r, $c)))

	rule r_moveUser =
		//the let rule was needed in the old version of AsmetaSMV
		/*let ($r = userChoiceR, $c = userChoiceC) in
			if(isUndef(board($r, $c))) then
				par
					board($r, $c) := CROSS
					status := TURN_PC
				endpar
			endif
		endlet*/
		if(isUndef(board(userChoiceR, userChoiceC))) then
			par
				board(userChoiceR, userChoiceC) := CROSS
				status := TURN_PC
			endpar
		endif

	rule r_movePC =
		choose $r in Coord, $c in Coord with isUndef(board($r, $c)) do
			par
				board($r, $c) :=  NOUGHT
				status := TURN_USER
			endpar

	
	CTLSPEC ef(winner(CROSS))
	CTLSPEC ef(winner(NOUGHT))
	CTLSPEC not(ef(winner(NOUGHT)))

	CTLSPEC ag(not(winner(CROSS) and winner(NOUGHT)))

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
