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
	//Timer unit
	controlled timerUnit: Timer -> TimerUnit
	
	//init start time to current time
	static initStart : Timer-> Integer
	// is the timer expired?
	derived expired: Timer -> Boolean
			
	
	
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
	macro rule r_reset_timer($t in Timer) =	start($t) := initStart($t)					
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = 
    	duration($t) := $ms
    
    // change or set the timer unit	
    macro rule r_set_timer_unit($t in Timer, $unit in TimerUnit) = 
    	timerUnit($t) := $unit
 
