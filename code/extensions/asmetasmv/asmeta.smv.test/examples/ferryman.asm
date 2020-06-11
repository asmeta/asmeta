asm ferryman

//Problema: un ferryman deve portare sull'altra sponda di un fiume un wolf, una
//goat ed un cabbage. Puo' trasportarne solo uno alla volta.
//Ci sono due situazioni di pericolo:
//il wolf mangia la goat se il ferryman non e' presente a controllare;
//la goat mangia il cabbage se il ferryman non e' presente a controllare.
//All'inizio sono tutti sulla sponda LEFT.

//Questo modello ASM dovrebbe essere l'implementazione di un comportamento
//corretto (che non porta a situazioni di pericolo) da parte del FERRYMAN.

//In realta' anche questo modello si ferma quando sono tutti sulla sponda destra.

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain Actors = {FERRYMAN | GOAT | CABBAGE | WOLF}
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	derived goodSituationSide: Prod(Actors, Actors, Side) -> Boolean
	
definitions:
	function goodSituationSide($a in Actors, $b in Actors, $s in Side) =
		//(position($a)=$s and position($b)=$s) implies goodCouple($a, $b)
		(position($a)=$s and position($b)=$s)
		implies 
		(not($a=GOAT and $b=CABBAGE) and not($b=GOAT and $a=CABBAGE) and
		not($a=WOLF and $b=GOAT) and not($b=WOLF and $a=GOAT))
	
	rule r_travelLeftToRight =
		//se il ferryman e' a sinistra deve sicuramente andare a destra
		if(position(FERRYMAN)= LEFT) then
			par
				position(FERRYMAN) := RIGHT
				
				//sceglie un attore in modo tale che quelli che rimangono non diano problemi
				choose $a in Actors with $a!=FERRYMAN and position($a) = LEFT and
										(forall $x in Actors, $y in Actors with (($x!=$a and $y!=$a) implies
																				  goodSituationSide($x, $y, LEFT))) do
					position($a) := RIGHT
			endpar
		endif
	
	rule r_travelRightToLeft =
		if(position(FERRYMAN)=RIGHT and	(position(GOAT)= LEFT or
												position(CABBAGE)= LEFT or
												position(WOLF)= LEFT)) then
				par
					position(FERRYMAN) := LEFT

					//se c'e' un attore che non puo' rimanere solo con un altro attore
					//il ferryman lo riporta indietro con lui
					choose $c in Actors with $c!=FERRYMAN and position($c) = RIGHT
						and (exist $k in Actors with not(goodSituationSide($c, $k, RIGHT))) do
						position($c) := LEFT
				endpar
			endif

	
	//AsmetaL invariants
	invariant over position: position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN)
	invariant over position: position(WOLF)=position(GOAT) implies position(WOLF)=position(FERRYMAN)
	
	CTLSPEC ag(position(GOAT)=position(CABBAGE) implies position(GOAT)=position(FERRYMAN))
	CTLSPEC ag(position(WOLF)=position(GOAT) implies position(WOLF)=position(FERRYMAN))
	CTLSPEC ef(position(WOLF)= RIGHT and position(GOAT)=RIGHT and position(CABBAGE)=RIGHT and position(FERRYMAN)=RIGHT)
	CTLSPEC ef(position(WOLF)= RIGHT and position(GOAT)=LEFT and position(CABBAGE)=RIGHT and position(FERRYMAN)=RIGHT)

	//e' possibile che il cabbage sia da solo sulla sponda destra
	CTLSPEC ef(position(WOLF)= LEFT and position(GOAT)=LEFT and position(CABBAGE)=RIGHT and position(FERRYMAN)=LEFT)
	//CTLSPEC ef(position(WOLF)= RIGHT and position(GOAT)=RIGHT and position(CABBAGE)=LEFT and position(FERRYMAN)=LEFT)
	//CTLSPEC not(ef((position(WOLF)= RIGHT and position(GOAT)=RIGHT and position(CABBAGE)=RIGHT and position(FERRYMAN)=RIGHT)))
	CTLSPEC ag((position(WOLF)= RIGHT and position(GOAT)=RIGHT and position(CABBAGE)=RIGHT and position(FERRYMAN)=RIGHT)
						iff (position(WOLF)= RIGHT and position(GOAT)=RIGHT and position(CABBAGE)=RIGHT and position(FERRYMAN)=RIGHT))

	main rule r_Main =
		par
			r_travelLeftToRight[]
			r_travelRightToLeft[]
		endpar

default init s0:
	function position($a in Actors) = LEFT
