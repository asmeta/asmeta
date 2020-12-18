asm ferrymanSimulator_raff1

//Problema: un ferryman deve portare sull'altra sponda di un fiume un wolf, una
//goat ed un cabbage. Puo' trasportarne solo uno alla volta.
//Ci sono due situazioni di pericolo:
//il wolf mangia la goat se il ferryman non e' presente a controllare;
//la goat mangia il cabbage se il ferryman non e' presente a controllare.
//All'inizio sono tutti sulla sponda LEFT.

//In simulazione, ad ogni passo, bisogna decidere chi deve essere trasportato
//sull'altra sponda dal FERRYMAN: la GOAT, il CABBAGE, il WOLF oppure fare
//attraversare il fiume con nessuno a bordo (NONE).

import StandardLibrary

signature:
	enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	dynamic monitored carry: Actors
	dynamic controlled outMess: String
	derived oppositeSide: Side -> Side
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

	//AsmetaL invariants
	//Se la capra (GOAT) e il cavolo (CABBAGE) sono sulla stessa sponda, allora deve essere presente anche il FERRYMAN
	//Se il lupo (WOLF) e la capra (GOAT) sono sulla stessa sponda, allora deve essere presente anche il FERRYMAN
	invariant over position:position(WOLF)=position(GOAT) implies position(WOLF)=position(FERRYMAN)

	invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)
	main rule r_Main =
		if(not(allOnRightSide)) then
			r_carry[carry]
		else
			outMess := "All on right side"
		endif

default init s0:
	function position($a in Actors) = LEFT
	function outMess = "From left to right"
	