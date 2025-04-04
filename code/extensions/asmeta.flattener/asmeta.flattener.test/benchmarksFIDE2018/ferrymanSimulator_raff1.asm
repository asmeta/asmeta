asm ferrymanSimulator_raff1

import ./STDL/StandardLibrary

signature:
	enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	dynamic monitored carry: Actors
	dynamic controlled outMess: String
	static oppositeSide: Side -> Side
	derived allOnRightSide: Boolean

definitions:
	function allOnRightSide =
		(forall $a in Actors with position($a) = RIGHT)

	function oppositeSide($s in Side) =
		if($s = LEFT) then
			RIGHT
		else
			LEFT
		endif

	rule r_updateMessage =
		if(outMess = "From left to right") then
			outMess := "From right to left"
		else
			outMess := "From left to right"
		endif

	rule r_carry($a in Actors) =
		if(position(FERRYMAN)=position($a)) then
			par
				position($a) := oppositeSide(position($a))
				position(FERRYMAN) := oppositeSide(position(FERRYMAN))
				r_updateMessage[]
			endpar
		endif

	invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)
	invariant over position: position(WOLF)=position(GOAT) implies position(WOLF)=position(FERRYMAN)


	main rule r_Main =
		if(not(allOnRightSide)) then
			r_carry[carry]
		else
			outMess := "All on right side"
		endif

default init s0:
	function position($a in Actors) = LEFT
	function outMess = "From left to right"
	