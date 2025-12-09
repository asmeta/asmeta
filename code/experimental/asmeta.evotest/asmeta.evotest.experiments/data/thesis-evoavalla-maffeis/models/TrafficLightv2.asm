asm TrafficLightv2

import STDL/StandardLibrary

/*
 * The model includes four states, red, green, yellow, and pending, and an internal variable timer.
 * The time is explicit, i.e., each transition consumes a unit of time (seconds for instance). 
 * The system starts in red and waits 60s seconds checking the variable timer and then moves to green.
 * Then, the system waits for pedestrians.
 * When a pedestrian arrives, if the timer is less than 60s, the system goes to pending,
 * letting the pedestrian wait for the timer to reach 60.
 * Instead, if the traffic light was green for at least 60s, the light becomes yellow for 5 seconds before going to red.
 * The EFSM has also outputs that correspond to light colours.
 */
 // considering 60 second as 6 steps, 5 seconds as 1 step.

signature:
	// DOMAINS
	enum domain States = {RED | GREEN | YELLOW | PENDING}
	domain TimerDomain subsetof Integer
	// FUNCTIONS
	controlled timer: TimerDomain
	controlled status: States
	
	monitored pedestrian: Boolean
	
	derived timeOut: Boolean
	derived yellow_timeOut: Boolean
	derived interupt: Boolean

definitions:
	// DOMAIN DEFINITIONS
	domain TimerDomain = {0 : 6}

	// FUNCTION DEFINITIONS
	function timeOut = (timer >= 6)
	function yellow_timeOut = (timer >= 1)
	function interupt = (pedestrian = true and timer < 6)

	// RULE DEFINITIONS
	rule r_red = 
		if(timeOut) then 
			par
				status := GREEN
				timer := 0
			endpar
		else
			timer := timer + 1
		endif

	rule r_green = 
		if(interupt) then
			par
				status := PENDING
				timer := 0
			endpar
		else if(timeOut) then 
			par
				status := YELLOW
				timer := 0
			endpar
		else
			timer := timer + 1
		endif endif
	
	rule r_yellow = 
		if(yellow_timeOut) then 
			par
				status := RED
				timer := 0
			endpar
		else
			timer := timer + 1
		endif
		
	rule r_pending = 
		if(timeOut) then 
			par
				status := RED
				timer := 0
			endpar
		else
			timer := timer + 1
		endif
	
	// INVARIANTS

	// MAIN RULE
	main rule r_Main =
		switch status
		case RED:
			r_red[]
		case GREEN:
			r_green[]
		case YELLOW:
			r_yellow[]
		case PENDING:
			r_pending[]
		endswitch

// INITIAL STATE
default init s0:
	function status = RED
	function timer = 0
