asm Lift_extramon

import StandardLibrary

export *

signature:

        enum domain Dir = {UP | DOWN} //direction
		domain Floor subsetof Integer
		enum domain State = {HALTING | MOVING} //lift control states

        dynamic controlled direction:  Dir //lift direction of travel, initially UP (see initial state s0)
        dynamic controlled ctlState: State //lift control state, initially halting (see initial state s0)
        dynamic controlled floor:  Floor   //lift current floor, initially ground (see initial state s0)
        
        dynamic monitored insideCall: Integer -> Boolean
		dynamic monitored outsideCall: Prod(Integer, Dir) -> Boolean
		
		dynamic monitored mon: Boolean
		

definitions:

	domain Floor = {0 : +2} // e.g. for m=2


	// main rule
	main rule r_Main = floor := floor +1

// define the initial states 
default init s0:
	
	function floor  = 0
	function direction = UP
	function ctlState = HALTING
        
        