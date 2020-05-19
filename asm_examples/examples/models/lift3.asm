// with r_cancelRequest moved to r_depart
asm lift3

	import ../../STDL/StandardLibrary
	
signature:
	abstract domain Lift
	domain Floor subsetof Integer
	enum domain Dir = {UP | DOWN}
	enum domain State = {HALTING | MOVING}
	
	// lift direction of travel
	dynamic controlled dir:  Lift -> Dir
	// lift control state
	dynamic controlled ctlState: Lift -> State
	//lift current floor
	dynamic controlled floor:  Lift -> Floor
	// internal request
	dynamic shared hasToDeliverAt: Prod(Lift, Floor) -> Boolean
	// external request
	dynamic shared existsCallFromTo: Prod(Floor, Dir) -> Boolean
	derived hasToVisit: Prod(Lift, Floor) -> Boolean
	derived attracted: Prod(Dir, Lift) -> Boolean
	derived canContinue: Lift -> Boolean
	static opposite: Dir -> Dir
	
	// consts
	static ground: Integer
	static top: Integer
	static lift1: Lift
	
definitions:

	domain Floor = {0 : 4}
	function ground = 0
	function top = 4
	
	function opposite ($d in Dir) =
		if ($d = UP) then DOWN else UP endif
		
	function hasToVisit($l in Lift, $floor in Floor) =
		hasToDeliverAt($l, $floor) 
		or existsCallFromTo($floor, UP) 
		or existsCallFromTo($floor, DOWN)
		
	function attracted($dir in Dir, $l in Lift) =
		$dir = UP and (exist $floor in Floor with $floor > floor($l) and hasToVisit($l, $floor))
		or
		$dir = DOWN and (exist $floor2 in Floor with $floor2 < floor($l) and hasToVisit($l, $floor2))

	function canContinue($l in Lift) =
		attracted(dir($l), $l)
		and not hasToDeliverAt($l, floor($l))
		and not existsCallFromTo(floor($l), dir($l))
		
	macro rule r_cancelRequest($dir in Dir, $l in Lift) =
		par
			hasToDeliverAt($l, floor($l)) := false
			existsCallFromTo(floor($l), $dir) := false
		endpar
		
	macro rule r_moveLift($l in Lift) =
		par
			if dir($l) = UP then
				floor($l) := floor($l) + 1
			endif
			if dir($l) = DOWN then
				floor($l) := floor($l) - 1
			endif
		endpar
		
	macro rule r_depart($l in Lift) =
		if ctlState($l) = HALTING and attracted(dir($l), $l) then par
			r_moveLift[$l]
			r_cancelRequest[dir($l), $l]
			ctlState($l) := MOVING
		endpar endif
		
	macro rule r_continue($l in Lift) =
		if ctlState($l) = MOVING and canContinue($l) then
			r_moveLift[$l]
		endif
		
	macro rule r_stop($l in Lift) =
		if ctlState($l) = MOVING and not canContinue($l) then par
			r_cancelRequest[dir($l), $l]
			ctlState($l) := HALTING
		endpar endif
		
	macro rule r_change($l in Lift) =
		let ($d = dir($l), $d2 = opposite($d)) in
			if ctlState($l) = HALTING and not attracted($d, $l) and attracted($d2, $l) then par
				dir($l) := $d2
				r_cancelRequest[$d2, $l]
			endpar endif
		endlet
		
	macro rule r_lift($l in Lift) =
		par
			r_depart[$l]
			r_continue[$l]
			r_stop[$l]
			r_change[$l]
		endpar
		
	invariant over existsCallFromTo: 
		not existsCallFromTo(ground, DOWN) and not existsCallFromTo(top, UP)
		
	main rule r_main =
		r_lift[lift1]
		
default init s0:
	
	function floor($l in Lift) = ground
	function dir($l in Lift) = UP
	function ctlState($l in Lift)= HALTING
	//function hasToDeliverAt($l in Lift, $f in Floor) = false
	//function existsCallFromTo($f in Floor, $d in Dir) = false

