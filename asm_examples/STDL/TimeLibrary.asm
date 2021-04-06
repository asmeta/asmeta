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
	
	//init start time to current time
	derived initStart : Timer-> Integer
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
	function initStart($t in Timer) = if (timerUnit($t)=NANOSEC) then mCurrTimeNanosecs
										else if (timerUnit($t)=MILLISEC) then mCurrTimeMillisecs
										else if (timerUnit($t)=SEC) then mCurrTimeSecs
										else if (timerUnit($t)=MIN) then mCurrTimeMins
										else if (timerUnit($t)=HOUR) then mCurrTimeHours
										endif endif endif endif endif
														
	function expired($t in Timer) = if (timerUnit($t)=NANOSEC) then (mCurrTimeNanosecs >= start($t) + duration($t))
										else if (timerUnit($t)=MILLISEC) then (mCurrTimeMillisecs >= start($t) + duration($t))
										else if (timerUnit($t)=SEC) then (mCurrTimeSecs >= start($t) + duration($t))
										else if (timerUnit($t)=MIN) then (mCurrTimeMins >= start($t) + duration($t))
										else if (timerUnit($t)=HOUR) then (mCurrTimeHours >= start($t) + duration($t))
									endif endif endif endif endif
	
	
	/*******************************************************/
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	start($t) := initStart($t)					
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = 
    	duration($t) := $ms
    
    // change or set the timer unit	
    macro rule r_set_timer_unit($t in Timer, $unit in TimerUnit) = 
    	timerUnit($t) := $unit
 

