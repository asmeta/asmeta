// definition of timers in ASM
module Timer

import StandardLibrary
export *

signature:
	abstract domain Timer
	enum domain TimerUnit={NANOSEC, MILLISEC, SEC}
	// starting time taken from the local clock
	controlled start: Timer-> Integer
	// duration in ms of the timer starting from its start
	controlled duration: Timer -> Integer
	controlled timerUnit: Timer -> TimerUnit
	
	//Functions used when the user uses default Timer unit
	//init start time to current time
	static initStart : Timer-> Integer
	// is the timer expired?
	derived expired: Timer -> Boolean
			
	//If the user wants to use one time unit for all the simulation set "timeUnitDefault" value in function definitions,
	//then call functions/rules without passing the timer unit
	// otherwise the user can specify the timer unit passing it as function/rule parameter
	//Examples with one time unit: oneWayTrafficLight_refinedSec, oneWayTrafficLight_refinedMillisec
	//Example with multiple time unit: oneWayTrafficLight_refinedMillisecSec
	
definitions:
														
	
	/*******************************************************/	
	function initStart($t in Timer) = if (timerUnit($t)=NANOSEC) then currTimeNanosecs
														else if (timerUnit($t)=MILLISEC) then currTimeMillisecs
														else if (timerUnit($t)=SEC) then currTimeSecs
														endif endif endif
														
	function expired($t in Timer) = if (timerUnit($t)=NANOSEC) then (currTimeNanosecs > start($t) + duration($t))
														else if (timerUnit($t)=MILLISEC) then (currTimeMillisecs > start($t) + duration($t))
														else if (timerUnit($t)=SEC) then (currTimeSecs > start($t) + duration($t))
														endif endif endif
	
	
	/*******************************************************/
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	start($t) :=initStart($t)					
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = 
    	duration($t) := $ms
 

