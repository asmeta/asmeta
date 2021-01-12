// definition of timers in ASM
module Timer

import StandardLibrary
export *

signature:
	abstract domain Timer
	// starting time taken from the local clock
	controlled start: Timer -> Integer
	// duration in ms of the timer starting from its start
	controlled duration: Timer -> Integer
	
	// is the timer expired?
	derived expired: Timer -> Boolean 
		

definitions:

	function expired($t in Timer) = (currTimeMillisecs > start($t) + duration($t))

	// restart the timer
    macro rule r_reset_timer($t in Timer) = start($t) :=  currTimeMillisecs
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Integer) = duration($t) := $ms

