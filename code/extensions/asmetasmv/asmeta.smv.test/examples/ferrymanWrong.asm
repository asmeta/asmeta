asm ferrymanWrong

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

//Questo modello ASM e' l'implementazione di un comportamento errato
//(che porta a situazioni di pericolo) da parte del FERRYMAN.
//Entrambe le situazioni di pericolo possono verificarsi.

signature:
	enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
		
definitions:

	rule r_travelLeftToRight =
		if(position(FERRYMAN)= LEFT) then
			par
				position(FERRYMAN) := RIGHT
				choose $a in Actors with $a!=FERRYMAN and position($a) = LEFT do
					position($a) := RIGHT
			endpar 
		endif
	
	rule r_travelRightToLeft =
		if(position(FERRYMAN)=RIGHT and	(position(GOAT)= LEFT or
												position(CABBAGE)= LEFT or
												position(WOLF)= LEFT)) then
				position(FERRYMAN) := LEFT
			endif

	CTLSPEC ag(position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN))
	CTLSPEC ag(position(WOLF)=position(GOAT) implies position(WOLF)=position(FERRYMAN))

	main rule r_Main =
		par
			r_travelLeftToRight[]
			r_travelRightToLeft[]
		endpar

default init s0:
	function position($a in Actors) = LEFT 