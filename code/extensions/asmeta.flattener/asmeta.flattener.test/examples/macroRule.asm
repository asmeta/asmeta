asm macroRule

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Actors = {WOLF | GOAT | CABBAGE | FERRYMAN }
	enum domain Side = {LEFT | RIGHT}
	dynamic controlled position: Actors -> Side
	dynamic monitored choice: Actors
	static opposite : Side -> Side
	
definitions:

	function opposite($l in Side) =
		if($l = LEFT) then
			RIGHT
		else
			LEFT
		endif

	rule r_move2($b in Actors) =
		if (position(FERRYMAN) = position($b)) then
			par
				position($b):= opposite(position($b))
				position(FERRYMAN):= opposite(position(FERRYMAN))
			endpar
		endif
		
	rule r_move($a in Actors) =
		r_move2[$a]

	main rule r_Main = 
		r_move[choice]

default init s0:
	function position($a in Actors)=LEFT
