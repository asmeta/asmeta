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

//maggio 2007
//con regole solo nella FSM
// PA autore, con il trucco di un primo step per leggere le monitorate
// e settare le condivise (non piu' necessario ora)

asm LIFT

import ../libraries/StandardLibrary

signature:
		
		abstract domain Lift
        enum domain Dir = {UP | DOWN} //direction
		domain Floor subsetof Integer
		enum domain State = {HALTING | MOVING} //lift control states
		
        dynamic controlled direction:  Lift -> Dir //lift direction of travel, initially UP (see initial state s0)
        dynamic controlled ctlState: Lift -> State //lift control state, initially halting (see initial state s0)
        dynamic controlled floor:  Lift -> Floor   //lift current floor, initially ground (see initial state s0)
        
        
        //Lift Predicates
        derived attracted: Prod(Dir, Lift) -> Boolean
		derived canContinue: Prod(Dir,Lift) -> Boolean
	    
	    dynamic controlled hasToDeliverAt: Prod(Lift, Integer) -> Boolean
		dynamic controlled existsCallFromTo: Prod(Integer, Dir) -> Boolean	
		dynamic monitored insideCall: Prod(Lift, Integer) -> Boolean
		dynamic monitored outsideCall: Prod(Integer, Dir) -> Boolean
		
		
		//con variabili modificabili dal sistema e da env
		//dynamic shared hasToDeliverAt: Prod(Lift, Integer) -> Boolean
		//dynamic shared existsCallFromTo: Prod(Integer, Dir) -> Boolean	
		
		static plusorminus: Dir -> Integer
		static opposite: Dir -> Dir
		static ground: Integer
		static top: Integer

        static lift1: Lift // there is one lift
	

definitions:

	domain Floor = {0 : 2} // e.g. for m=2
	function ground = 0
	function top = 2

	function plusorminus($d in Dir) =
		if($d = UP) then
			1
		else
			-1
		endif
	
	// function that returns the opposite one of the direction of the elevator			
	function opposite ($d in Dir) =
		if ($d = UP) then 
            DOWN
		else 
            UP
		endif

	function canContinue($d in Dir, $l in Lift) =	
	( 	( ($d =UP) and (attracted(UP,$l) and not(hasToDeliverAt($l,floor($l))) 
										 and not(existsCallFromTo(floor($l),UP))))
	or (($d =DOWN) and (attracted(DOWN,$l) and not(hasToDeliverAt($l,floor($l)))
												and not(existsCallFromTo(floor($l),DOWN))))  )
		
	function attracted($d in Dir, $l in Lift) =
	if($d = UP) then
		if (exist $f in Floor with ($f > floor($l) and (hasToDeliverAt($l,$f) or existsCallFromTo($f,UP) or existsCallFromTo($f,DOWN) ))) then
			true
		else
			false
		endif
	else
		if(exist $g in Floor with ($g < floor($l) and (hasToDeliverAt($l,$g) or existsCallFromTo($g,UP) or existsCallFromTo($g,DOWN) ))) then
			true	
		else
			false
		endif
	endif
	

	macro rule r_costraintHasToDeliver =
	forall $f in Floor, $l in Lift with  (hasToDeliverAt ($l,$f) and ctlState($l) = HALTING and floor($l) = $f) do
 		hasToDeliverAt ($l,$f) := false
	
	macro rule r_costraintCallFromTo =
	forall $f in Floor, $d in Dir, $l in Lift with (existsCallFromTo($f,$d) and 
														(($f=ground and $d=DOWN) or 
													  	($f=top and $d=UP) or 
													 	(ctlState($l)=HALTING and floor($l) = $f))) do
		existsCallFromTo ($f,$d) := false
	
	
	// rule of departure of the elevator		
	rule r_Depart($l in Lift) =
		floor($l) := floor($l) + plusorminus(direction($l))

	// rule of continuation of the elevator
	rule r_Continue($l  in Lift) =
		floor($l) := floor($l) + plusorminus(direction($l))
		
	// rule of cancellation of the application	
	macro rule r_CancelRequest($f in Floor,$d in Dir, $l in Lift) =
		par
			hasToDeliverAt($l,$f) := false
			existsCallFromTo($f,$d) := false
		endpar

	// rule of stop of elevator
	rule r_Stop($l in Lift) =
		r_CancelRequest[floor($l),direction($l), $l]

	// rule of change of direction of the elevator
	rule r_Change ($l in Lift) =
	par	
		direction($l) := opposite(direction($l))
		r_CancelRequest[floor($l),opposite(direction($l)), $l]
	endpar
	
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
		seq
			//imposto le chiamate dell'esterno
			forall $f in Floor do
				par
					if(outsideCall($f,DOWN)=true) then
						existsCallFromTo($f,DOWN):= true
					endif
					if(outsideCall($f,UP)=true) then
						existsCallFromTo($f,UP):= true
					endif
					if(insideCall($l,$f)=true) then
						hasToDeliverAt($l,$f):= true
					endif
				endpar
			r_costraintCallFromTo[]
			r_costraintHasToDeliver[]
			//la valutazione delle condizione avviene anche dentro le regole
			par
				r_Fsm[$l, HALTING, attracted(direction($l), $l), 
					<<r_Depart(Lift)>>, MOVING]
				r_Fsm[$l, MOVING, canContinue(direction($l),$l), 
					<<r_Continue(Lift)>>, MOVING]
				r_Fsm[$l, MOVING, not canContinue(direction($l),$l), 
					<<r_Stop(Lift)>>, HALTING]
				r_Fsm[$l, HALTING, 
					not attracted(direction($l), $l) and 
					attracted(opposite(direction($l)), $l), 
					<<r_Change(Lift)>>, HALTING]
			endpar
		endseq

// define the initial states 
default init s0:
	
	function floor($l in Lift) = 0
	function direction($l in Lift) = UP
	function ctlState($l in Lift)= HALTING
	function hasToDeliverAt($l in Lift, $i in Integer) = false
	function existsCallFromTo($i in Integer, $d in Dir) = false
		
