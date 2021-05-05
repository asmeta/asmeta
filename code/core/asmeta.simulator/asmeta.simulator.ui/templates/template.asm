// a simple example with a tic tac toe game

asm new_file

import StandardLibrary

signature:
	// DOMAINS
	domain Coord subsetof Integer
	enum domain Sign = {CROSS | NOUGHT}
	enum domain Status = {TURN_USER | TURN_PC}
	// FUNCTIONS
	controlled board: Prod(Coord, Coord) -> Sign
	controlled status: Status
	monitored userChoiceR: Coord //row chosen by the user
	monitored userChoiceC: Coord //column chosen by the user
	derived winner: Sign -> Boolean
	derived endOfGame: Boolean

definitions:
	// DOMAIN DEFINITIONS
	domain Coord = {1 : 3}

	// FUNCTION DEFINITIONS
	function winner($s in Sign) =
		(exist $r in Coord with (forall $c in Coord with board($r, $c) = $s)) or
		(exist $c2 in Coord with (forall $r2 in Coord with board($r2, $c2) = $s)) or
		(forall $d in Coord with board($d, $d) = $s) or
		(forall $d1 in Coord with board($d1, 4 - $d1) = $s)

	function endOfGame =
		(exist $s in Sign with winner($s)) or
		(forall $r in Coord, $c in Coord with isDef(board($r, $c)))

	// RULE DEFINITIONS
	rule r_moveUser =
		let ($r = userChoiceR, $c = userChoiceC) in
			if(isUndef(board($r, $c))) then
				par
					board($r, $c) := CROSS
					status := TURN_PC
				endpar
			endif
		endlet

	rule r_movePC =
		choose $r in Coord, $c in Coord with isUndef(board($r, $c)) do
			par
				board($r, $c) :=  NOUGHT
				status := TURN_USER
			endpar

	// INVARIANTS
	invariant inv_win over winner:  not(winner(CROSS) and winner(NOUGHT))

	// MAIN RULE
	main rule r_Main =
		if not(endOfGame) then
			if status = TURN_USER then
				r_moveUser[]
			else
				r_movePC[]
			endif
		endif

// INITIAL STATE
default init s0:
	function status = TURN_USER
