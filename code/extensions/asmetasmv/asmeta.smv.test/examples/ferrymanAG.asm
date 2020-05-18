asm ferrymanAG

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	
	derived allRight : Boolean
	derived pericolo : Boolean
		
definitions:
	
	function allRight = (forall $a in Actors with position($a) = RIGHT) 

	function pericolo = 
	(position(GOAT)=position(CABBAGE) and position(GOAT)!=position(FERRYMAN)) 
	or  (position(GOAT)=position(WOLF) and position(GOAT)!=position(FERRYMAN))
	

	rule r_lefttoright =
		if position(FERRYMAN) = LEFT then
			par
			choose $a in Actors with position($a) = LEFT do
				position($a) := RIGHT
			position(FERRYMAN):= RIGHT
			endpar
		endif
	rule r_rightroleft =
		if position(FERRYMAN) = RIGHT then
			par
			choose $a in Actors with position($a) = RIGHT do
				position($a) := LEFT
			position(FERRYMAN):= LEFT
			endpar
		endif
	// voglio trovare gli stati se esitono e il percorso per raggiungerli
	// tali che sian tutti di là
	CTLSPEC ag(not allRight) // -> questo lo uso per avere il percorso

	CTLSPEC ef(allRight) // vera perchè esiste un percorso per portarli tutti di là.
	
	CTLSPEC af(allRight) // è falsa perchè il feryman potrebbe andare avanti e indietro

	CTLSPEC ef(pericolo)
	CTLSPEC ag(not pericolo)
	
	// esiste un percorso in cui sono tutti di là senza mai pericolo?
	// uso UNTIL
	//CTLSPEC e(not pericolo,allRight)
	// non esiste un percorso  
	CTLSPEC not e(not pericolo,allRight) 

	main rule r_Main =
		par
			r_rightroleft[]
			r_lefttoright[]
		endpar

default init s0:
	function position($a in Actors) = LEFT 