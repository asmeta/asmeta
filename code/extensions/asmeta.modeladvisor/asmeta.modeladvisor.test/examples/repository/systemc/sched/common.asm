/*
 * Core definitions
 */
module common

	import ../../STDL/StandardLibrary
	export *

signature:
	/*
	 * Process domain
	 */
	dynamic abstract domain Process
	/*
	 * Primitive channel domain
	 */
	dynamic abstract domain PrimChannel
	/*
	 * Event domain
	 */
	dynamic abstract domain Event
	/*
	 * Timeout event domain
	 */
	dynamic domain TimeOut subsetof Event
	/*
	 * Process status domain
	 */
	enum domain Status = {RUNNABLE | SUSPENDED | EXECUTING | STOPPED}
	/*
	 * Scheduler phase domain
	 */
 	enum domain Phase = {STEP_AGAIN | ELABORATION | INITIALIZATION | EVALUATION | 
		UPDATE | DELTA_NOTIFICATION | TIMED_NOTIFICATION | STOP}

	/*
	 * Given a primitive channel, returns true if the channel must be updated
	 */
 	controlled isOutOfDate: PrimChannel -> Boolean
	/*
	 * Given a primitive channel, returns the event associated to the
	 * changing of the channel
	 */
 	controlled defaultEvent: PrimChannel -> Event
	/*
	 * Given a primitive channel, returns the update rule
	 */
	controlled updateRule: PrimChannel -> Rule(PrimChannel)
	/*
	 * Given a process, returns true if the process is statically triggered,
	 * i.e. it is resumed according his own static sensitivity list
	 */
	derived isStaticTriggered: Process -> Boolean
	/*
	 * Given a process, returns true if the process is dynamically triggered,
	 * i.e. it is resumed by an event previously specified in a waiting statement
	 */
	controlled isDynTriggered: Process -> Boolean
	/*
	 * Given a process, returns his status
	 */
	controlled status: Process -> Status
	/*
	 * Given an event, returns true if the event is waiting to be notified
	 */
	controlled isPending: Event -> Boolean
	/*
	 * Given an event, returns the time when the event is going to be notified
	 */
	controlled eventTime: Event -> Integer
	/*
	 * Given a process and an event, returns true is the event belongs to the
	 * static sensitivity list of the process. Otherwise, returns undef
	 */
	controlled isSensibleOf: Prod(Process, Event) -> Boolean
	/*
	 * Given a process and an event, returns true if the process has been
	 * suspended on the event by a waiting statement (dynamic sensitivity).
	 * Otherwise, returns undef
	 */
	controlled isDynSensibleOf: Prod(Process, Event) -> Boolean
	/*
	 * Returns the simulation time
	 */
	controlled time: Integer
	/*
	 * Returns the scheduler phase
	 */
 	controlled phase: Phase
	/*
	 * Returns the running process
	 */
	controlled current_exec: Process
	/*
	 * Given a process, returns the last frame index, i.e. the first to be
	 * completed.
	 * A frame collects the data needed to restore the execution of a 
	 * procedure suspended by a waiting statement. Such data are the 
	 * procedure body, the local variables values, the next stattement to
	 * execute...
	 */
	controlled frame_exec: Process -> Integer
	/*
	 * Given a process and a frame index, returns the step index pointing to
	 * the statement to be executed
	 */
	controlled step_exec: Prod(Process, Integer) -> Integer
	/*
	 * Given a process and a frame index, returns the rule associated to the
	 * frame
	 */
	controlled run_exec: Prod(Process, Integer) -> Rule

definitions:
	/*
	 * Returns true if the given process is statically triggered
	 */
	function isStaticTriggered($p in Process) =
		not isDynTriggered($p)
	/*
	 * Initializes the given event
	 */
	macro rule r_initEvent($e in Event) =
		seq
			eventTime($e) := 0
			isPending($e) := false
		endseq
	/*
	 * Notifies the given event immediately
	 */
	macro rule r_notifyNow($e in Event) =
		seq
			isPending($e) := false
			forall $p in Process do
				if isStaticTriggered($p) and isSensibleOf($p, $e) != undef then
					status($p) := RUNNABLE
				else if isDynTriggered($p) and isDynSensibleOf($p, $e) != undef then seq
					status($p) := RUNNABLE
					isDynTriggered($p) := false
					isDynSensibleOf($p, $e) := undef
				endseq endif endif
		endseq
	/*
	 * Notifies the given event at the given time. If the time is zero, the
	 * event is notified at the next delta cycle
	 */
	macro rule r_notify($e in Event, $t in Integer) =
		if isPending($e) then
			eventTime($e) := min(eventTime($e), time + $t)
		else seq
			isPending($e) := true
			eventTime($e) := time + $t
		endseq endif
	/*
	 * Adds the given event to the static sensitivity list of the given process
	 */
	macro rule r_sensitive($p in Process, $e in Event) =
		isSensibleOf($p, $e) := true
	/*
	 * Associates the given rule to the given process
	 */
	macro rule r_thread($p in Process, $prule in Rule) =
		seq
			status($p) := RUNNABLE
			isDynTriggered($p) := false
			frame_exec($p) := 0
			run_exec($p, 0) := $prule
			step_exec($p, 0) := 0
		endseq
	/*
	 * Suspends the given process. The process is resumed according to
	 * his own static sensitivity list
	 */
	macro rule r_waitStatic($p in Process) = 
		seq
			isDynTriggered($p) := false
			status($p) := SUSPENDED
		endseq
	/*
	 * Suspends the given process. The process is resumed when the given
	 * event is notified
	 */
	macro rule r_waitEvent($p in Process, $e in Event) =
		seq
			isDynTriggered($p) := true
			isDynSensibleOf($p, $e) := true
			status($p) := SUSPENDED
		endseq
	/*
	 * Suspends the given process. The process is resumed when the given
	 * time is elapsed
	 */
	macro rule r_waitTimeOut($p in Process, $t in Integer) =
		let ($events = {$ee in TimeOut| eventTime($ee) < time: $ee}) in
			if isEmpty($events) then
				extend TimeOut with $new do seq
					r_initEvent[$new]
					r_notify[$new, $t]
					r_waitEvent[$p, $new]
				endseq
			else let ($e = chooseone($events)) in seq
				// set it to not pending
				isPending($e) := false
				r_notify[$e, $t]
				r_waitEvent[$p, $e]
			endseq endlet endif
		endlet
	/*
	 * Stops the given process
	 */
 	macro rule r_stop($p in Process) =
 		status($p) := STOPPED
	/*
	 * The given process is not run during the initialization phase
	 */
	macro rule r_dontInitialize($p in Process) =
		status($p)  := SUSPENDED
	/*
	 * Initializes the given channel.
	 * $c a primitive channel
	 * $urule the update rule of the channel
	 */
	macro rule r_initPrimChannel($c in PrimChannel, $urule in Rule(PrimChannel)) =
		extend Event with $e do seq
			r_initEvent[$e]
			defaultEvent($c) := $e
			updateRule($c) := $urule
			isOutOfDate($c) := false
		endseq
	/*
	 * Requests an update for the given channel
	 */
	macro rule r_requestUpdate($c in PrimChannel) =
		isOutOfDate($c) := true
	/*
	 * Sets as the next statement, the statement pointed by the given label
	 */
	macro rule r_goto($label in Integer) =
		let ($proc = current_exec) in
			step_exec($proc, frame_exec($proc)) := $label
		endlet
	/*
	 * Goes to $labelTrue if $cond is true. Otherwise, goes to $labelFalse
	 */
	macro rule r_ifGoto($cond in Boolean, $labelTrue in Integer, $labelFalse in Integer) =
		if $cond then
			r_goto[$labelTrue]
		else
			r_goto[$labelFalse]
		endif
	/*
	 * Creates a new frame and associates it to the given rule
	 */
	macro rule r_enterFrame($macroRule in Rule) =
		let ($proc = current_exec) in seq
			frame_exec($proc) := frame_exec($proc) + 1
			step_exec($proc, frame_exec($proc)) := 0
			run_exec($proc, frame_exec($proc)) := $macroRule
		endseq endlet
	/*
	 * Deletes the last frame
	 */
	macro rule r_leaveFrame =
		let ($proc = current_exec) in seq			
			step_exec($proc, frame_exec($proc)) := undef
			run_exec($proc, frame_exec($proc)) := undef
			frame_exec($proc) := frame_exec($proc) - 1
		endseq endlet
		
