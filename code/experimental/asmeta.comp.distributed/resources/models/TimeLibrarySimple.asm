// definition of timers in ASM
// WITHOUT TIME UNITS 
module TimeLibrarySimple


import StandardLibrary
export *

signature:
	abstract domain Timer
	//enum domain TimerUnit={NANOSEC, MILLISEC, SEC, MINUTES, HOUR}
	// starting time taken from the local clock
	controlled start: Timer-> Integer
	// duration in ms of the timer starting from its start
	controlled duration: Timer -> Integer
	//Timer unit
	// controlled timerUnit: Timer -> TimerUnit
	
	//get the current time for the timer
	derived currentTime : Timer-> Integer
	// is the timer expired?
	derived expired: Timer -> Boolean
	
	/* use seconds for now */ 	
	monitored mCurrTimeSecs: Integer

	
definitions:
														
	
	/*******************************************************/	
	function currentTime($t in Timer) = mCurrTimeSecs

	function expired($t in Timer) = (currentTime($t) >= start($t) + duration($t))
	
	
	/*******************************************************/
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	start($t) := currentTime($t)					
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = 
    	duration($t) := $ms
    

