/*
 * SystemC scheduler
 */
module sched

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/clock
	export r_start
	
signature:
	static min: Seq(Integer) -> Integer
	
definitions:
	/*
	 * Returns the minimum value of the given integer list
	 */
	function min($s in Seq(Integer)) =
		if length($s) = 1 then
			first($s)
		else
			min(first($s), min(tail($s)))
		endif
	/*
	 * Calls the update rules of the out-of-date channels
	 */
 	macro rule r_updatePhase =
		forall $c in PrimChannel with isOutOfDate($c) do seq
			isOutOfDate($c) := false
			updateRule($c)[$c]
		endseq
	/*
	 * Notifies all the pending events
	 */
	macro rule r_notificationPhase =
		let ($pendingEvents = {$e in Event| isPending($e) and eventTime($e) = time: $e}) in
			forall $e1 in $pendingEvents do
				r_notifyNow[$e1]
		endlet
	/*
	 * Chooses a runnable process and executes it
	 */
	macro rule r_evaluationPhase =
		choose $p in Process with status($p) = RUNNABLE do seq
			current_exec := $p
			run_exec($p, frame_exec($p))
			if status($p) = RUNNABLE then
				phase := STEP_AGAIN
			endif
		endseq
	/*
	 * Starts the simulation.
	 * $endTime duration of the simulation
	 * $initTop constructor of the top-most module
	 */
	macro rule r_start($endTime in Integer, $initTop in Rule) =		
		if phase = undef then seq
			// init the scheduler
			phase := ELABORATION
			time := 0
		endseq else if phase = STOP then
			skip
		else if time >= $endTime then
			// simulation time elapsed
			phase := STOP
		else if phase = STEP_AGAIN then
			// the current process must continue his execution
			// this step happens when a process executes a goto statement
			let ($proc = current_exec) in seq
				run_exec($proc, frame_exec($proc))
				if status($proc) != RUNNABLE then
					phase := EVALUATION
				endif
			endseq endlet
		else if phase = ELABORATION then seq
			// constructs the module hierarchy
			$initTop
			phase := INITIALIZATION
		endseq else if phase = INITIALIZATION then seq
			// initialization phase
			r_updatePhase[]
			r_notificationPhase[]
			phase := EVALUATION
		endseq else if phase = EVALUATION then seq
			// executes a runnable process
			r_evaluationPhase[]
			if (exist $p in Process with status($p) = RUNNABLE) then
				phase := EVALUATION
			else
				phase := UPDATE
			endif
		endseq else if phase = UPDATE then seq
			// updates the primitive channels
			r_updatePhase[]
			phase := DELTA_NOTIFICATION
		endseq else if phase = DELTA_NOTIFICATION then seq
			// delta notification
			r_notificationPhase[]
			if (exist $p1 in Process with status($p1) = RUNNABLE) then
				phase := EVALUATION
			else
				phase := TIMED_NOTIFICATION
			endif			
		endseq else if phase = TIMED_NOTIFICATION then
			let ($pendingEvents = {$e in Event| isPending($e): $e}) in
				if isEmpty($pendingEvents) then
					phase := STOP
				else seq
					// advances simulation time
					time := min(asSequence({$e1 in $pendingEvents: eventTime($e1)}))
					r_notificationPhase[]
					if (exist $p2 in Process with status($p2) = RUNNABLE) then
						phase := EVALUATION
					else
						phase := TIMED_NOTIFICATION
					endif
				endseq endif
			endlet
		endif endif endif endif endif endif endif endif endif endif
		
