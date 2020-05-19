asm ferrymanSimulator_raff1_old

//Problema: un ferryman deve portare sull'altra sponda di un fiume un wolf, una
//goat ed un cabbage. Puo' trasportarne solo uno alla volta.
//Ci sono due situazioni di pericolo:
//il wolf mangia la goat se il ferryman non e' presente a controllare;
//la goat mangia il cabbage se il ferryman non e' presente a controllare.
//All'inizio sono tutti sulla sponda LEFT.

//In simulazione, ad ogni passo, bisogna decidere chi deve essere trasportato
//sull'altra sponda dal FERRYMAN: la GOAT, il CABBAGE, il WOLF oppure fare
//attraversare il fiume con nessuno a bordo (NONE).

//Primo raffinamento: la simulazione si deve interrompere quando sono arrivati tutti
//sulla sponda destra.

import ../../STDL/StandardLibrary

signature:
	enum domain Actors = {FERRYMAN | GO | CA | WO}
	enum domain CarryDomain = {GOAT | CABBAGE | WOLF | NONE}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	dynamic monitored carry: CarryDomain
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
		if($a!=FERRYMAN and position(FERRYMAN)=position($a)) then
			par
				position($a) := oppositeSide(position($a))
				position(FERRYMAN) := oppositeSide(position(FERRYMAN))
				r_updateMessage[]
			endpar
		endif

	rule r_travel =
		switch(carry)
			case GOAT:
				r_carry[GO]
			case CABBAGE:
				r_carry[CA]
			case WOLF:
				r_carry[WO]
			case NONE:
				par
					position(FERRYMAN) := oppositeSide(position(FERRYMAN))
					r_updateMessage[]
				endpar
		endswitch

	//AsmetaL invariant
	//Se la capra (GO) e il cavolo (CA) sono sulla stessa sponda, allora deve essere presente anche il FERRYMAN
	invariant over position: position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
	//Se il lupo (WO) e la capra (GO) sono sulla stessa sponda, allora deve essere presente anche il FERRYMAN
	invariant over position: position(WO)=position(GO) implies position(WO)=position(FERRYMAN)

	main rule r_Main =
		if(not(allOnRightSide)) then
			r_travel[]
		else
			outMess := "All on right side"
		endif

default init s0:
	function position($a in Actors) = LEFT
	function outMess = "From left to right"
