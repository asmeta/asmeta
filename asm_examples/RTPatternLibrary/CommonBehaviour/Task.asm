/* ASM description of threads working with critical sections */
asm Task

import ../../STDL/StandardLibrary
export *

// --------------------------------------------------------------------------------------------------		
signature:
// DOMAINS
	// Domain "Thread"
		abstract domain Task
	// Stream of data managed by a thread (it can be an image, a set-point vector, ...)
		dynamic abstract domain DataStream
// ENUMERATIVE DOMAINS
	// Kind of task	
		enum domain TaskKind = {SYNCHRONOUS | ASYNCHRONOUS}	
	// Priority of task for synchronization
		enum domain TaskSynchronizationPriority = {MASTER | SLAVE}
	// Status referred to Critical Section (ready to enter in CS, into CS, waiting for CS)
		enum domain TaskCSState = {READY | EXECUTION | WAITING}
	
// FUNCTIONS
	// Task Status and kind
		dynamic controlled task_currState: Task -> TaskCSState
		dynamic controlled task_kind: Task -> TaskKind
	// Time structures (in units of time)	
		// Task period (if synchronous, 0 otherwise)
			dynamic controlled task_period: Task->Integer
		// Time required for main task data elaboration
			dynamic controlled task_timeForElaboration: Task->Integer
		// Evolving times (elapsed time into cycle, elapsed time during elaboration, elapsed time in waiting)
			dynamic controlled task_elapsedTimeOfPeriod: Task->Integer 
			dynamic controlled task_timeInWait: Task->Integer 
	// DataStream managed by the Task	
		dynamic controlled task_dataStream: Task -> DataStream

// DERIVED FUNCTIONS
	derived thereIsTime: Task->Boolean

// --------------------------------------------------------------------------------------------------		
// DEFINITIONS 
definitions:
// function thereIsTime -> returns true if a synchronous task can wait to access a critical section because
//							it has time to do it
	function thereIsTime ($p in Task) = 
		task_period($p) - task_timeForElaboration($p) - task_timeInWait($p) > 0

// --------------------------------------------------------------------------------------------------		
// RULES

// Task initialization (Task, kindOfTask, period, timeForElaboration)
	rule r_task_init ($p in Task, $kind in TaskKind, $tp in Integer, $te in Integer) =
		par
			// Task kind and status
				task_kind($p) := $kind 
				task_currState($p) := READY
			// Task times
				task_timeInWait($p) := 0
				task_elapsedTimeOfPeriod($p) :=0
				task_timeForElaboration($p) := $te 
				// Period			
					if ($kind = ASYNCHRONOUS) then
						task_period($p) := 0
					else
						task_period($p) := $tp 
					endif
			// Task data stream
				task_dataStream($p) := undef		
		endpar		

// Increments all the correct times at the end of a clock execution
	rule r_update_Time ($p in Task, $step in Integer) =
		par
			task_elapsedTimeOfPeriod($p) := task_elapsedTimeOfPeriod($p) + $step 
			if (task_currState($p) = WAITING) then
				task_timeInWait($p) := task_timeInWait($p) + $step 
			endif
		endpar

// Resets the time at the end of the execution cycle	
	rule r_reset_Time ($p in Task) =
		par
			task_elapsedTimeOfPeriod($p) := 0
			task_timeInWait($p) := 0
		endpar