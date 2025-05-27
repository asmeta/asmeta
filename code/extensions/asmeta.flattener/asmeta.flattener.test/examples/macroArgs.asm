asm macroArgs

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
		
	rule r_move($a in Actors) =
		if (position(FERRYMAN) = position($a)) then
			par
				position($a):= opposite(position($a))
				position(FERRYMAN):= opposite(position(FERRYMAN))
			endpar
		endif

	main rule r_Main = 
		r_move[choice]

default init s0:
	function position($a in Actors)=LEFT
