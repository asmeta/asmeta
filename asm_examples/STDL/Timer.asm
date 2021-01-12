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
	
	// is the timer expired?
	derived expired: Prod(Timer, TimerUnit) -> Boolean
	
	static initStart : Prod(Timer,TimerUnit)-> Integer
	
	
	static timeUnitDefault: TimerUnit
	static initStart : Timer-> Integer
	derived expired: Timer -> Boolean
	
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
	function initStart($t in Timer) = initStart($t, timeUnitDefault)
														
	function expired($t in Timer) = if (timeUnitDefault=NANOSEC) then (currTimeNanosecs > start($t, timeUnitDefault) + duration($t))
														else if (timeUnitDefault=MILLISEC) then (currTimeMillisecs > start($t, timeUnitDefault) + duration($t))
														else if (timeUnitDefault=SEC) then (currTimeSecs > start($t, timeUnitDefault) + duration($t))
														endif endif endif
	
	/*===================================================*/
	// restart the timer
	macro rule r_reset_timer($t in Timer, $unit in TimerUnit) =	start($t, $unit) :=	initStart($t, $unit)	
	
	
	/*******************************************************/
	
	
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	start($t, timeUnitDefault) :=initStart($t)										
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = duration($t) := $ms

