// definition of timers in ASM
module TimeLibrary


import StandardLibrary
export *

signature:
	abstract domain Timer
	enum domain TimerUnit={NANOSEC, MILLISEC, SEC, MIN, HOUR}
	// starting time taken from the local clock
	controlled start: Timer-> Integer
	// duration in ms of the timer starting from its start
	controlled duration: Timer -> Integer
	//Timer unit
	controlled timerUnit: Timer -> TimerUnit
	
	//get the current time for the timer
	derived currentTime : Timer-> Integer
	// is the timer expired?
	derived expired: Timer -> Boolean
	
	
/*----------- Java time  as monitored function (experimental) --------------*/
	monitored mCurrTimeNanosecs: Integer
	monitored mCurrTimeMillisecs: Integer
	monitored mCurrTimeSecs: Integer
	monitored mCurrTimeMins: Integer
	monitored mCurrTimeHours: Integer

	
definitions:
														
	
	/*******************************************************/	
	function currentTime($t in Timer) = if (timerUnit($t)=NANOSEC) then mCurrTimeNanosecs
										else if (timerUnit($t)=MILLISEC) then mCurrTimeMillisecs
										else if (timerUnit($t)=SEC) then mCurrTimeSecs
										else if (timerUnit($t)=MIN) then mCurrTimeMins
										else if (timerUnit($t)=HOUR) then mCurrTimeHours
										endif endif endif endif endif
														
	function expired($t in Timer) = (currentTime($t) >= start($t) + duration($t))
	
	
	/*******************************************************/
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	start($t) := currentTime($t)					
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = 
    	duration($t) := $ms
    
    // change or set the timer unit	
    macro rule r_set_timer_unit($t in Timer, $unit in TimerUnit) = 
    	timerUnit($t) := $unit
 

