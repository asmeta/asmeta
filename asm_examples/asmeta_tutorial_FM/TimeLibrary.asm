// definition of timers in ASM
module TimeLibrary


import StandardLibrary
export *

signature:
	abstract domain Timer
	// starting time taken from the local clock
	controlled start: Timer-> Real
	// duration in sec of the timer starting from its start
	controlled duration: Timer -> Real
	
	//get the current time for the timer
	derived currentTime : Timer-> Real
	// is the timer expired?
	derived expired: Timer -> Boolean
	
	
/*----------- Java time  as monitored function (experimental) --------------*/

	monitored mCurrTimeSecs: Real


	
definitions:
														
	
	/*******************************************************/	
	function currentTime($t in Timer) = mCurrTimeSecs
														
	function expired($t in Timer) = (currentTime($t) >= start($t) + duration($t))
	
	
	/*******************************************************/
	// restart the timer
	macro rule r_reset_timer($t in Timer) =	start($t) := currentTime($t)					
														
    
    
	// change or set the duration of a timer
    macro rule r_set_duration($t in Timer, $ms in Real) = 
    	duration($t) := $ms

 

