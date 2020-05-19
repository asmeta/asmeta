//Lift Control (N.Davis, 1984; see J-R Abrial: B-Book, 1996) 
//Design the logic to move n lifts bw m floors, and prove it to be well functioning.
//----------------------------------------------------------------------------------
//Each lift has for each floor one button which, if pressed, causes the lift to visit (i.e. move to and stop at) that floor.
//Each floor (except ground and top) has two buttons to request an up-lift and a down-lift. 
//They are cancelled when a lift visits the floor and is either travelling in the desired direction, or visits the floor
//with no requests outstanding. In the latter case, if both floor request buttons are illuminated, only one should be //cancelled.
//A lift without requests should remain in its final destination and await further requests.
//Each lift has an emergency button which, if pressed, causes a warning to be sent to the site manager. The lift is then //deemed �out of service�. Each lift has a mechanism to cancel its �out of service� status.
//
//Authors: Daniela Guglietta
asm LIFTDC

import ../../STDL/StandardLibrary

signature:
	abstract domain Lift  //set of lifts 
	enum domain Dir = {UP | DOWN} //direction
	domain Floor subsetof Integer    // interval from -1 (ground) to m (top)
	enum domain State = {HALTING | MOVING} //lift control states
	
	dynamic controlled direction:  Lift -> Dir //lift direction of travel, initially UP (see initial state s0)
	dynamic controlled ctlState: Lift -> State //lift control state, initially halting (see initial state s0)
	dynamic controlled floor:  Lift -> Floor   //lift current floor, initially ground (see initial state s0)

	//Lift Predicates
	dynamic monitored attracted: Prod(Dir, Lift) -> Boolean // (set by the env)
	dynamic monitored canContinue: Lift -> Boolean //(set by the env)

	static opposite: Dir -> Dir
	static ground: Integer
	static top: Integer
	
	static lift1: Lift // there is one lift

definitions:
	domain Floor = {-1 : +2} // e.g. for m=2
	function ground = 0
	function top = +2

	// function that returns the opposite one of the direction of 
	// the elevator			
	function opposite ($d in Dir) =
		if ($d = UP) then 
            DOWN
		else 
            UP
		endif

// rule that defines the movement of the elevator
	rule r_MoveLift($l in Lift) =
        par
            if direction($l) = UP then
                floor($l) := floor($l) + 1
            endif
            if direction($l) = DOWN then
                floor($l) := floor($l) - 1
            endif
        endpar

	// rule of departure of the elevator		
	rule r_Depart($l in Lift) =
		r_MoveLift[$l]

	// rule of continuation of the elevator
	rule r_Continue($l  in Lift) =
		r_MoveLift [$l]

	// rule of cancellation of the application	
	rule r_CancelRequest($d in Dir, $l in Lift)=
		skip

	// rule of stop of elevator
	rule r_Stop($l in Lift)=
		r_CancelRequest[direction($l), $l]

	// rule of change of direction of the elevator
	rule r_Change ($l in Lift)=
			let ($d = opposite(direction($l))) in
				seq
					direction($l) := $d
					r_CancelRequest[$d, $l]
				endseq
			endlet

	// macro simulating a generic FSM
       rule r_Fsm($l in Lift, $s1 in State, $cond in Boolean, $rule in Rule(Lift), $s2 in State) =
		if ctlState($l) = $s1 and $cond then
			par
				$rule[$l]
				ctlState($l) := $s2
			endpar
		endif


	// main rule
	main rule r_Main =
		forall $l in Lift with true do
			par
				r_Fsm[$l, HALTING, attracted(direction($l), $l), 
					<<r_Depart(Lift)>>, MOVING]
				r_Fsm[$l, MOVING, canContinue($l), 
					<<r_Continue(Lift)>>, MOVING]
				r_Fsm[$l, MOVING, not canContinue($l), 
					<<r_Stop(Lift)>>, HALTING]
				r_Fsm[$l, HALTING, 
					not attracted(direction($l), $l) and 
					attracted(opposite(direction($l)), $l), 
					<<r_Change(Lift)>>, HALTING]
			endpar

// define the initial states 
default init s0:
	function floor($l in Lift) = 0
	function direction($l in Lift) = UP
	function ctlState($l in Lift)= HALTING
