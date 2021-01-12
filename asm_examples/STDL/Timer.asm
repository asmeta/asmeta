// definition of timers in ASM
module Timer

import StandardLibrary
export *

signature:
	abstract domain Timer
	enum domain TimerUnit={NANOSEC, MILLISEC, SEC}
	// starting time taken from the local clock
	controlled start: Timer -> Integer
	// duration in ms of the timer starting from its start
	controlled duration: Timer -> Integer
	
	// is the timer expired?
	derived expired: Prod(Timer, TimerUnit) -> Boolean

definitions:

	//function expired($t in Timer) = (currTimeMillisecs > start($t) + duration($t))
	
	function expired($t in Timer, $unit in TimerUnit) = if ($unit=NANOSEC) then (currTimeNanosecs > start($t) + duration($t))
														else if ($unit=MILLISEC) then (currTimeMillisecs > start($t) + duration($t))
														else if ($unit=SEC) then (currTimeSecs > start($t) + duration($t))
														endif endif endif

	// restart the timer
    macro rule r_reset_timer($t in Timer, $unit in TimerUnit) = if ($unit=NANOSEC) then start($t) := currTimeNanosecs
														else if ($unit=MILLISEC) then start($t) := currTimeMillisecs
														else if ($unit=SEC) then start($t) := currTimeSecs
														endif endif endif
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = duration($t) := $ms

