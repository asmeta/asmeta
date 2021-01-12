// definition of timers in ASM
module Timer

import StandardLibrary
export *

signature:
	abstract domain Timer
	enum domain TimerUnit={NANOSEC, MILLISEC, SEC}
	// starting time taken from the local clock
	controlled start: Prod(Timer,TimerUnit)-> Integer
	// duration in ms of the timer starting from its start
	controlled duration: Timer -> Integer
	
	//Functions used when the user specify the Timer unit
	// is the timer expired?
	derived expired: Prod(Timer, TimerUnit) -> Boolean
	//init start time to current time
	static initStart : Prod(Timer,TimerUnit)-> Integer

	//Functions used when the user uses default Timer unit
	//init start time to current time
	static initStart : Timer-> Integer
	// is the timer expired?
	derived expired: Timer -> Boolean
	
	//Timer unit used for the simulation, must be set in the user specification
	static timerUnitDefault: TimerUnit
		
	//If the user wants to use one time unit for all the simulation set "timeUnitDefault" value in function definitions,
	//then call functions/rules without passing the timer unit
	// otherwise the user can specify the timer unit passing it as function/rule parameter
	//Examples with one time unit: oneWayTrafficLight_refinedSec, oneWayTrafficLight_refinedMillisec
	//Example with multiple time unit: oneWayTrafficLight_refinedMillisecSec
	
definitions:

	
	function initStart($t in Timer, $unit in TimerUnit)= if ($unit=NANOSEC) then currTimeNanosecs
														else if ($unit=MILLISEC) then currTimeMillisecs
														else if ($unit=SEC) then currTimeSecs
														endif endif endif
														
	function expired($t in Timer, $unit in TimerUnit) = if ($unit=NANOSEC) then (currTimeNanosecs > start($t, $unit) + duration($t))
														else if ($unit=MILLISEC) then (currTimeMillisecs > start($t, $unit) + duration($t))
														else if ($unit=SEC) then (currTimeSecs > start($t, $unit) + duration($t))
														endif endif endif
														
	
	/*******************************************************/	
	function initStart($t in Timer) = initStart($t, timerUnitDefault)
														
	function expired($t in Timer) = expired($t, timerUnitDefault)
	
	
	/*===================================================*/
	// restart the timer
	macro rule r_reset_timer($t in Timer, $unit in TimerUnit) =	start($t, $unit) :=	initStart($t, $unit)	
	
	
	/*******************************************************/
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	r_reset_timer[$t, timerUnitDefault]									
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = duration($t) := $ms

