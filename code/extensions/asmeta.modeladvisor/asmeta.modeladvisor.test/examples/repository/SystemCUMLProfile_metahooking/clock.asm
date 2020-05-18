/*
 * A clock
 */
module clock

	import StandardLibrary
	//import common
	import chan
	export Clock, r_initClock, clockOutput, defaultEvent, posEdgeEvent, negEdgeEvent, owner
	
signature:
	/*
	 * Clock domain
	 */
	dynamic abstract domain Clock
	/*
	 * Given a clock, returns the boolean channel associated
	 */
	controlled chan: Clock -> ChanBoolean
	/*
	 * Given a clock, returns the period
	 */
	controlled period: Clock -> Integer
	/*
	 * Given a clock, returns the duty cycle
	 */
	controlled dutyCycle: Clock -> Real
	/*
	 * Given a clock, returns the start time
	 */
	controlled startTime: Clock -> Integer
	/*
	 * Given a clock, returns true if the first impulse emitted by the clock
	 * is the true value. Otherwise, returns false
	 */
	controlled posEdgeFirst: Clock -> Boolean
	/*
	 * Given a clock, returns the next step to execute
	 */
	controlled step: Clock -> Integer
	/*
	 * Given a process, returns the clock to which the process belongs
	 *///Process
	controlled owner: Process -> Clock
	/*
	 * Given a clock, returns the event associated to the
	 * changing of it
	 */
	derived defaultEvent: Clock -> Event
	/*
	 * Given a clock, returns the event associated with the transition
	 * from a false value to a true value
	 */
	derived posEdgeEvent: Clock -> Event
	/*
	 * Given a clock, returns the event associated with the transition
	 * from a true value to a false value
	 */
	derived negEdgeEvent: Clock -> Event
	/*
	 * Given a clock, returns the boolean channel associated
	 */
	derived clockOutput: Clock -> ChanBoolean

	static c: Sc_Thread
	
definitions:
	/*
	 * Returns the event associated to the changing of the given clock
	 */
	function defaultEvent($c in Clock) =
		defaultEvent(chan($c))
	/*
	 * Returns the event associated with the transition from a false value 
	 * to a true value of the given clock
	 */
	function posEdgeEvent($c in Clock) =
		posEdgeEvent(chan($c))
	/*
	 * Returns the event associated with the transition from a true value 
	 * to a false value of the given a clock
	 */
	function negEdgeEvent($c in Clock) =
		negEdgeEvent(chan($c))
	/*
	 * Returns the boolean channel associated with the given a clock
	 */
	function clockOutput($c in Clock) =
		chan($c)
	/*
	 * The clock's process
	 */
	macro rule r_runClock =
		let ($p = current_exec,
			$clock = owner($p)) in
			if step($clock) = 0 then seq
				r_write[chan($clock), not posEdgeFirst($clock)]
				step($clock) := 1
				r_waitTimeOut[$p, startTime($clock)]
			endseq else
				let ($value = readBool(chan($clock))) in seq
					r_write[chan($clock), not $value]
					if $value then
						r_waitTimeOut[$p, 
							rtoi(itor(period($clock)) * (1.0 - dutyCycle($clock)))]
					else
						r_waitTimeOut[$p, 
							rtoi(itor(period($clock)) * dutyCycle($clock))]
					endif
				endseq endlet
			endif
		endlet
	/*
	 * Initializes the given clock.
	 * $c a clock
	 * $period period
	 * $dutyCycle duty cycle
	 * $startTime start time
	 * $posEdgeFirst first emitted impulse's value
	 */
	macro rule r_initClock(
	$c in Clock, 
	$period in Integer, 
	$dutyCycle in Real,
	$startTime in Integer,
	$posEdgeFirst in Boolean) =
			//extend Sc_Thread with $proc do 
			seq
				r_init[lOAD]
				chan($c) := lOAD
				period($c) := $period
				dutyCycle($c) := $dutyCycle
				startTime($c) := $startTime
				posEdgeFirst($c) := $posEdgeFirst
				step($c) := 0
				owner(c) := $c
				r_thread[c, <<r_runClock>>] 
				//r_runClock[period($c), dutyCycle($c)]
			endseq

