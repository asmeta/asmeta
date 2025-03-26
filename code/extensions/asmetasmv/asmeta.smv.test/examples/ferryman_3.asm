asm ferryman_3

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
 enum domain Actors = {FERRYMAN, GOAT, CABBAGE, WOLF}
 enum domain Side  = {LEFT, RIGHT}

 dynamic controlled position: Actors -> Side
 
 dynamic monitored carry: Actors
 
 derived pericolo : Boolean
 derived allRight : Boolean

definitions:

 function pericolo = ((position(GOAT) = position(WOLF) 
 						or position(GOAT) = position(CABBAGE)) and
 					position(FERRYMAN) != position(GOAT))
 
 function allRight =  position(FERRYMAN)= RIGHT and position(GOAT) = RIGHT and
 					position(CABBAGE) =RIGHT and position(WOLF) = RIGHT	

  //invariant inv_1 over pericolo : not pericolo

// 1. prendi uno a caso (con il choose) e portalo dall'altra parte
/* 	let ($p = position(FERRYMAN)) in
		choose $a in Actors with position($a) = $p do
				if $p = LEFT then par
					position(FERRYMAN):= RIGHT
					position($a):= RIGHT
				endpar else par
					position(FERRYMAN):= LEFT
					position($a):= LEFT
				endpar endif
	endlet*/
	
// non c'� mai pericolo
//CTLSPEC ag(not pericolo)	
	
// posso portare di l� tutto 	--> VERA
//CTLSPEC ef(allRight)

// non posso portare di l� tutto 	--> FALSA
//CTLSPEC not(ef(allRight))

// non c'è mai pericolo --> FALSA
//CTLSPEC ag(not allRight)

// non posso portare di l� tutto --> FALSA
CTLSPEC not eu(not pericolo, allRight)


main rule r_main = 
	let ($p = position(FERRYMAN)) in
		if position(carry) = $p then
				if $p = LEFT then par
					position(FERRYMAN):= RIGHT
					position(carry):= RIGHT
				endpar else par
					position(FERRYMAN):= LEFT
					position(carry):= LEFT
				endpar endif
		endif
	endlet


default init state:
  function position($a in Actors) = LEFT