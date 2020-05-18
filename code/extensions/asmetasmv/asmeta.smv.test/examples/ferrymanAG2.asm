asm ferrymanAG

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

//Questo modello ASM e' l'implementazione di un comportamento errato
//(che porta a situazioni di pericolo) da parte del FERRYMAN.
//Entrambe le situazioni di pericolo possono verificarsi.

signature:
	enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	//all the actors are on the right bank
	//sono arrivati tutti sulla sponda destra
	derived allOnRightSide : Boolean
	// pericolo
	derived danger : Boolean
		
definitions:

	//sono arrivati tutti sulla sponda destra
	function allOnRightSide =
		(forall $a in Actors with position($a) = RIGHT)

	function danger = 
		(position(GOAT)=position(CABBAGE) and position(GOAT)!=position(FERRYMAN)) 
		or  (position(GOAT)=position(WOLF) and position(GOAT)!=position(FERRYMAN))

	rule r_travelLeftToRight =
		if(position(FERRYMAN)= LEFT) then
			par
				position(FERRYMAN) := RIGHT
				// se scegli ferryman va vuota
				choose $a in Actors with position($a) = LEFT do
					position($a) := RIGHT
			endpar 
		endif
	
	rule r_travelRightToLeft =
		if(position(FERRYMAN)=RIGHT) then
		par
			position(FERRYMAN) := LEFT
			// se scegli ferryman va vuota
			choose $a in Actors with position($a) = RIGHT do
					position($a) := LEFT
		endpar
			endif
	// è sempre sicura??? --> questa mi aspetto che sia falsa
	//CTLSPEC ag(position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN))
	//CTLSPEC ag(position(WOLF)=position(GOAT) implies position(WOLF)=position(FERRYMAN))
	CTLSPEC ag(not danger)
	
	// posso portare di là le cose
	CTLSPEC ef(allOnRightSide)
	// per trovare il percorso 
	CTLSPEC not ef(allOnRightSide)
	// però così ho pericolo .... 		 
	// non posso mai portare tutti di là in modo sicuro
	CTLSPEC not e(not danger, allOnRightSide)

	main rule r_Main =
		par
			r_travelLeftToRight[]
			r_travelRightToLeft[]
		endpar

default init s0:
	function position($a in Actors) = LEFT 