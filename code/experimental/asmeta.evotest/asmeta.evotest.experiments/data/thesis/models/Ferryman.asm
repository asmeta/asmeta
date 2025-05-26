asm Ferryman

import STDL/StandardLibrary

signature:

	enum domain Actor = {FERRYMAN | WOLF | GOAT | CABBAGE}
	enum domain Side = {LEFT | RIGHT}
	enum domain Status = {WORKING | SOLVED}

	controlled status: Status
	controlled position: Actor -> Side

	monitored carry: Actor

	derived allRight: Boolean
	derived danger: Boolean

definitions:
	
	function allRight = (forall $a in Actor with position($a) = RIGHT)
	
	function danger = ( ((position(WOLF) = position(GOAT)) and (position(WOLF) != position(FERRYMAN)))
		or ((position(GOAT) = position(CABBAGE)) and (position(GOAT) != position(FERRYMAN))) )
	
	rule r_MoveFerryman =
		if position(FERRYMAN) = LEFT then position(FERRYMAN) := RIGHT
		else position(FERRYMAN) := LEFT
		endif

	rule r_MoveCarry =
		if position(carry) = position(FERRYMAN) then
			if position(carry) = LEFT then position(carry) := RIGHT
			else position(carry) := LEFT
			endif
		endif

	invariant inv_danger over danger : not danger

	main rule r_ferrymanProblem =
		switch status
			case WORKING:
				par
					r_MoveFerryman[]
					r_MoveCarry[]
					if allRight then status := SOLVED endif
				endpar
			case SOLVED:
				skip
		endswitch

default init s0:
	function status = WORKING
	function position($a in Actor) = LEFT
