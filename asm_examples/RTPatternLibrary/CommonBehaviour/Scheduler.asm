/* ASM of a Task Scheduler (Camera, Visual Servoing, Robot, Sensors, Motor) */
asm Scheduler

import ../../STDL/StandardLibrary
import ../Pattern/Semaphore
import ../Pattern/SwingingBuffer
import ./Task
import ./CommonDefinitions

export *

// --------------------------------------------------------------------------------------------------	
signature:
// DOMAINS
	// General Controller domain definition
		abstract domain Scheduler
	// Controller phase
		dynamic abstract domain SchedulePhase	// dynamic because it has to be extended during initialization...
// ENUMERATIVE DOMAINS	
	// Agent (Controller) Action
		enum domain ScheduleAction_outer = {WRITING_ON_SB | READING_FROM_SB | ELABORATING | WAITING_TRIGGER}
		enum domain ScheduleAction_inner = {WRITING | READING | MANAGING_SB}
	
// FUNCTIONS
	// Phase functions
		// Time		
			dynamic controlled time: SchedulePhase->Integer
		// Task Action (from enumerative)
			dynamic controlled action: SchedulePhase -> ScheduleAction_outer
		// Shared memory structure (SwingingBuffer)
			dynamic controlled sharedMemory: SchedulePhase -> SwingingBuffer
		// Swinging buffer action
			dynamic controlled sb_ctrlState: SchedulePhase -> ScheduleAction_inner	
			
		// Elaboration rule (if needed)
			// Variable elaboration rule
				controlled elaborationRule : SchedulePhase -> Rule(Scheduler)
		// For Synchronous Slow -> it's the master task in this phase
			dynamic controlled syncPriority: SchedulePhase -> TaskSynchronizationPriority
			
	// Controller thread		
		dynamic controlled scheduler_mainThread: Scheduler -> Task

	// Seguence of controller phases		
		dynamic controlled scheduler_seqPhase: Scheduler -> Seq(SchedulePhase)
	// Current action and index of this action in the sequence	
		dynamic controlled scheduler_currentPhaseIndex: Scheduler -> Integer
		dynamic controlled scheduler_currentPhase: Scheduler -> SchedulePhase	
		
	// Evolving	fields
		dynamic controlled scheduler_currentScheduleTime: Scheduler -> Integer

	// Derived Functions
		derived trigger: Scheduler->Boolean
		derived tElapsed: Scheduler->Boolean
		derived outsidePatternMachine: Scheduler->Boolean
		derived swingingBufferManaged: Scheduler->Boolean
		derived watchdog: Scheduler->Boolean

// --------------------------------------------------------------------------------------------------	
definitions:
// DERIVED FUNCTION
	// function trigger -> returns true if the scheduler generates a trigger event
		function trigger($c in Scheduler) =
			( task_kind( scheduler_mainThread($c) ) = SYNCHRONOUS 
				and task_elapsedTimeOfPeriod( scheduler_mainThread($c) ) > time( scheduler_currentPhase($c) ) )
				or task_kind( scheduler_mainThread($c) ) = ASYNCHRONOUS

	// function	tElapsed -> returns true if the current action time is elapsed
		function tElapsed($c in Scheduler) =
			scheduler_currentScheduleTime($c)  > time(scheduler_currentPhase($c) )
		
	// function outsidePatternMachine -> returns true if the scheduler is executing the main machine
	//	(not the pattern machine
		function outsidePatternMachine ($c in Scheduler) =
			isUndef (sb_ctrlState(scheduler_currentPhase($c) ) )
			
	// function swingingBufferManaged -> return true if lock has been acquired and swinging buffer managed
		function swingingBufferManaged ($c in Scheduler) =
			( syncPriority( scheduler_currentPhase($c) ) = MASTER
				and indexUpdated(sharedMemory( scheduler_currentPhase ($c ) ) ) )
			or ( syncPriority( scheduler_currentPhase($c) ) = SLAVE
				and task_currState (scheduler_mainThread ($c) ) = EXECUTION )
	
	// function watchdog -> returns true if there's no time to wait for lock acquisition
		function watchdog ($c in Scheduler) =
			task_currState( scheduler_mainThread($c) ) = READY
			

// INIT RULES
	// Controller initialization (Controller, Task, ElaborationRule)
		rule r_scheduler_init ($c in Scheduler, $proc in Task) =
			par
				// Task
					scheduler_mainThread($c) := $proc 
	
				// Control state and current index			
					scheduler_currentPhaseIndex($c) := 0

				// Phases	
					scheduler_seqPhase($c) := []
					scheduler_currentPhase($c) := undef
				
				// Evolving fields
					scheduler_currentScheduleTime($c) :=0	
			endpar

// --------------------------------------------------------------------------------------------------	
// Add phases
	// Add elaboration phase
		rule r_phases_add_ELABORATION ($c in Scheduler, $t in Integer, $erule in Rule(Scheduler) ) =
			extend SchedulePhase with $ap do
				par
					time($ap) := $t 
					action($ap) := ELABORATING 
					sharedMemory($ap) := undef
					elaborationRule($ap) := $erule 
					syncPriority($ap) := undef 
					sb_ctrlState($ap) := undef
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar
				
	// Add Waiting_Trigger Phase:
		rule r_phases_add_WAITING_TRIGGER_ASYNC ($c in Scheduler) =
			extend SchedulePhase with $ap do
				par
					time($ap) := 0 
					action($ap) := WAITING_TRIGGER 
					sharedMemory($ap) := undef
					elaborationRule($ap) := undef
					syncPriority($ap) := undef  
					sb_ctrlState($ap) := undef
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar
		rule r_phases_add_WAITING_TRIGGER_SYNC ($c in Scheduler, $t in Integer) =
			extend SchedulePhase with $ap do
				par
					time($ap) := $t 
					action($ap) := WAITING_TRIGGER 
					sharedMemory($ap) := undef
					elaborationRule($ap) := undef
					syncPriority($ap) := undef
					sb_ctrlState($ap) := undef  
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar
				
	// Add reading/writing phase
		rule r_phases_add_READING_FROM_SB_MASTER ($c in Scheduler, $t in Integer,  $m in SwingingBuffer) =
			extend SchedulePhase with $ap do
				par
					time($ap) := $t 
					action($ap) := READING_FROM_SB 
					sharedMemory($ap) := $m 
					elaborationRule($ap) := undef
					syncPriority($ap) := MASTER
					sb_ctrlState($ap) := undef 
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar
		rule r_phases_add_READING_FROM_SB_SLAVE ($c in Scheduler, $t in Integer,  $m in SwingingBuffer) =
			extend SchedulePhase with $ap do
				par
					time($ap) := $t 
					action($ap) := READING_FROM_SB 
					sharedMemory($ap) := $m 
					elaborationRule($ap) := undef
					syncPriority($ap) := SLAVE 
					sb_ctrlState($ap) := undef
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar				
				
		rule r_phases_add_WRITING_ON_SB_MASTER ($c in Scheduler, $t in Integer,  $m in SwingingBuffer) =
			extend SchedulePhase with $ap do
				par
					time($ap) := $t 
					action($ap) := WRITING_ON_SB 
					sharedMemory($ap) := $m 
					elaborationRule($ap) := undef
					syncPriority($ap) := MASTER
					sb_ctrlState($ap) := undef 
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar
		rule r_phases_add_WRITING_ON_SB_SLAVE ($c in Scheduler, $t in Integer,  $m in SwingingBuffer) =
			extend SchedulePhase with $ap do
				par
					time($ap) := $t 
					action($ap) := WRITING_ON_SB 
					sharedMemory($ap) := $m 
					elaborationRule($ap) := undef
					syncPriority($ap) := SLAVE
					sb_ctrlState($ap) := undef 
					scheduler_seqPhase($c) := append(scheduler_seqPhase($c) , $ap )
				endpar				
				
	// Set init phase
		rule r_phases_setStartingPoint ($c in Scheduler, $i in Integer) =
			par
				scheduler_currentPhaseIndex ($c) := $i 
				scheduler_currentPhase($c) := at(scheduler_seqPhase($c) , iton( $i )) 
			endpar

// --------------------------------------------------------------------------------------------------	
// RULES
	// WRITE rule
		rule r_write ($c in Scheduler , $phase in SchedulePhase) =	
			extend DataStream with $newStream do
				r_SwingingBuffer_write [ sharedMemory( $phase ) , $newStream , scheduler_mainThread ($c) ]

	// READ rule		
		rule r_read ($c in Scheduler , $phase in SchedulePhase) =	
			r_SwingingBuffer_read [ sharedMemory( $phase ) , scheduler_mainThread ($c) ]
	
	// WAIT_TRIGGER rule	
		rule r_waitTrigger ($c in Scheduler , $phase in SchedulePhase) =
			skip

	// ELABORATE rule
		rule r_elaborate ($c in Scheduler) =
			elaborationRule( scheduler_currentPhase ( $c ) ) [ $c ]

	// MANAGE_SwingingBuffer (pattern depending on MASTER/SLAVE)			
		rule r_manageSb ($c in Scheduler , $phase in SchedulePhase) =
			par
				// MASTER: get_Lock + update_indexes
					if ( syncPriority($phase) = MASTER ) then
						seq
							r_SwingingBuffer_getLock [ sharedMemory( $phase ) , scheduler_mainThread( $c ) ]
							r_SwingingBuffer_updateIndexes [ sharedMemory( $phase ) , scheduler_mainThread( $c ) ]
						endseq				
					endif
				// SLAVE: get_lock
					if ( syncPriority($phase) = SLAVE ) then
						r_SwingingBuffer_getLock [ sharedMemory( $phase ) , scheduler_mainThread( $c ) ]
					endif
			endpar
			
	// RELEASE_SB
		rule r_releaseSb ($c in Scheduler) =
			par
				if ( syncPriority( scheduler_currentPhase($c) ) = MASTER ) then
					indexUpdated ( sharedMemory( scheduler_currentPhase ($c) ) ) := false
				endif
				r_SwingingBuffer_releaseLock [ sharedMemory( scheduler_currentPhase ($c) ) , scheduler_mainThread($c) ]		
			endpar
			
// --------------------------------------------------------------------------------------------------	
// SWINGING BUFFER PATTERN MACHINES

// ASYNCHRONOUS - MASTER - WRITING
	rule r_async_master_writing ( $c in Scheduler) =
		par
		// Undef state: Enter into pattern machine
			if ( outsidePatternMachine ( $c ) ) then
				par
					r_write [ $c , scheduler_currentPhase ($c) ]
					sb_ctrlState(scheduler_currentPhase($c) ) := WRITING
				endpar
			endif
		// WRITING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = WRITING ) then
				if ( tElapsed ( $c ) ) then
					par
						r_manageSb [ $c , scheduler_currentPhase ($c) ] 
						sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
					endpar
				endif
			endif
		// MANAGING_SB
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = MANAGING_SB ) then
				if ( swingingBufferManaged ( $c ) ) then
				// Index updated: true
					par
						r_releaseSb [ $c ]
						// Exit from pattern machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					endpar
				else
				// Index updated: false
					par
						// Try again to acquire lock
							r_manageSb [ $c , scheduler_currentPhase ($c) ] 
							sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
					endpar
				endif
			endif
		endpar

// ASYNCHRONOUS - MASTER - READING
	rule r_async_master_reading ( $c in Scheduler) =
		par
		// Undef state: Enter into pattern machine
			if ( outsidePatternMachine ( $c ) ) then
				par
					r_manageSb [ $c , scheduler_currentPhase ($c) ] 
					sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
				endpar
			endif
		// MANAGING_SB
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = MANAGING_SB ) then
				if ( swingingBufferManaged ( $c ) ) then
				// Index updated: true
					par
						r_releaseSb [ $c ]
						r_read [ $c , scheduler_currentPhase ($c) ]
						sb_ctrlState(scheduler_currentPhase($c) ) := READING
					endpar
				else
				// Index updated: false				
					par
						// Try again to acquire lock
							r_manageSb [ $c , scheduler_currentPhase ($c) ] 
							sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
					endpar
				endif
			endif
		// READING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = READING ) then
				if ( tElapsed ( $c ) ) then
					// Exit from pattern machine
						sb_ctrlState(scheduler_currentPhase($c) ) := undef					
				endif
			endif
		endpar

// ASYNCHRONOUS - SLAVE
	rule r_async_slave_action ( $c in Scheduler) =
		par
		// Undef state: Enter into pattern machine
			if ( outsidePatternMachine ( $c ) ) then
				par
					r_manageSb [ $c , scheduler_currentPhase ($c) ] 
					sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
				endpar
			endif
		// MANAGING_SB
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = MANAGING_SB ) then
				if ( swingingBufferManaged ( $c ) ) then
				// Lock acquired: true
					par
						// Choose ACTION
							if (action( scheduler_currentPhase ($c) ) = WRITING_ON_SB) then
								par
									r_write [ $c , scheduler_currentPhase ($c) ]
									sb_ctrlState(scheduler_currentPhase($c) ) := WRITING
								endpar
							endif
							if (action( scheduler_currentPhase ($c) ) = READING_FROM_SB) then
								par
									r_read [ $c , scheduler_currentPhase ($c) ]
									sb_ctrlState(scheduler_currentPhase($c) ) := READING
								endpar
							endif
					endpar
				else
				// Lock acquired: false
					par
						// Try again to acquire lock
							r_manageSb [ $c , scheduler_currentPhase ($c) ] 
							sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
					endpar
				endif
			endif
		// READING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = READING ) then
				if ( tElapsed ( $c ) ) then
					par
						r_releaseSb [ $c ]
						// Exit from Pattern Machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					endpar
				endif
			endif
		// WRITING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = WRITING ) then
				if ( tElapsed ( $c ) ) then
					par
						r_releaseSb [ $c ]
						// Exit from Pattern Machine						
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					endpar
				endif
			endif
		endpar

// SYNCHRONOUS - MASTER - WRITING
	rule r_sync_master_writing ($c in Scheduler) =
		par
		// Undef state: Enter into pattern machine
			if ( outsidePatternMachine ( $c ) ) then
				par
					r_write [ $c , scheduler_currentPhase ($c) ]
					sb_ctrlState(scheduler_currentPhase($c) ) := WRITING
				endpar
			endif
		// WRITING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = WRITING ) then
				if ( tElapsed ( $c ) ) then
					par
						r_manageSb [ $c , scheduler_currentPhase ($c) ] 
						sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
					endpar
				endif
			endif
		// MANAGING_SB
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = MANAGING_SB ) then
				if ( swingingBufferManaged ( $c ) ) then
				// Index updated: true
					par
						r_releaseSb [ $c ]
						// Exit from Pattern Machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					endpar
				else
				// Index updated: false
					if ( watchdog ( $c ) ) then
						// Watchdog
						// Exit from Pattern Machine: skip read/write action
								sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					else
						// NO Watchdog
						par
							// There is time: try again to acquire lock
								r_manageSb [ $c , scheduler_currentPhase ($c) ] 
								sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
						endpar
					endif
				endif
			endif
		endpar

// SYNCHRONOUS - MASTER - READING
	rule r_sync_master_reading ( $c in Scheduler ) = 
		par
		// Undef state: Enter into pattern machine
			if ( outsidePatternMachine ( $c ) ) then
				par
					r_manageSb [ $c , scheduler_currentPhase ($c) ] 
					sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
				endpar
			endif
		// MANAGING_SB
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = MANAGING_SB ) then
				if ( swingingBufferManaged ( $c ) ) then
				// Index updated: true				
					par
						r_releaseSb [ $c ]
						// Read
							r_read [ $c , scheduler_currentPhase ($c) ]
							sb_ctrlState(scheduler_currentPhase($c) ) := READING
					endpar
				else
				// Index updated: false
					if ( watchdog ( $c ) ) then
						// Watchdog
						par
							// Read old data
								r_read [ $c , scheduler_currentPhase ($c) ]
								sb_ctrlState(scheduler_currentPhase($c) ) := READING
						endpar
					else
						// NO Watchdog
						par
							// There is time: try again to acquire lock
							r_manageSb [ $c , scheduler_currentPhase ($c) ] 
							sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
						endpar
					endif
				endif
			endif
		// WRITING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = READING ) then
				if ( tElapsed ( $c ) ) then
						// Exit from Pattern Machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
				endif
			endif
		endpar

// SYNCHRONOUS - SLAVE
	rule r_sync_slave_action ( $c in Scheduler ) =
		par
		// Undef state: Enter into pattern machine
			if ( outsidePatternMachine ( $c ) ) then
				par
					r_manageSb [ $c , scheduler_currentPhase ($c) ] 
					sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
				endpar
			endif
		// MANAGING_SB
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = MANAGING_SB ) then
				if ( swingingBufferManaged ( $c ) ) then
				// Lock acquired: true
					par
						// Choose ACTION
							if (action( scheduler_currentPhase ($c) ) = WRITING_ON_SB) then
								par
									r_write [ $c , scheduler_currentPhase ($c) ]
									sb_ctrlState(scheduler_currentPhase($c) ) := WRITING
								endpar
							endif
							if (action( scheduler_currentPhase ($c) ) = READING_FROM_SB) then
								par
									r_read [ $c , scheduler_currentPhase ($c) ]
									sb_ctrlState(scheduler_currentPhase($c) ) := READING
								endpar
							endif
					endpar
				else
				// Lock acquired: false
					if ( watchdog ( $c ) ) then
						// Watchdog
						// Exit from Pattern Machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					else
						// No watchdog: try again to acquire lock
						par
							r_manageSb [ $c , scheduler_currentPhase ($c) ] 
							sb_ctrlState(scheduler_currentPhase($c) ) := MANAGING_SB
						endpar
					endif
				endif
			endif
		// READING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = READING ) then
				if ( tElapsed ( $c ) ) then
					par
						r_releaseSb [ $c ]
						// Exit from Pattern Machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					endpar
				endif
			endif
		// WRITING STATE
			if (  sb_ctrlState(scheduler_currentPhase($c) ) = WRITING ) then
				if ( tElapsed ( $c ) ) then
					par
						r_releaseSb [ $c ]
						// Exit from Pattern Machine
							sb_ctrlState(scheduler_currentPhase($c) ) := undef					
					endpar
				endif
			endif
		endpar

// --------------------------------------------------------------------------------------------------	
// RUN SCHEDULE RULES

	// Pattern Machines Execution
		rule r_patternMachine_execute ($c in Scheduler) =
			par
				if (task_kind( scheduler_mainThread($c) ) = ASYNCHRONOUS 
						and syncPriority( scheduler_currentPhase($c) ) = MASTER
						and  action( scheduler_currentPhase($c) ) = WRITING_ON_SB ) then
					r_async_master_writing [ $c ]
				endif
				if (task_kind( scheduler_mainThread($c) ) = ASYNCHRONOUS 
						and syncPriority( scheduler_currentPhase($c) ) = MASTER
						and  action( scheduler_currentPhase($c) ) = READING_FROM_SB ) then
					r_async_master_reading [ $c ]
				endif
				if (task_kind( scheduler_mainThread($c) ) = ASYNCHRONOUS 
						and syncPriority( scheduler_currentPhase($c) ) = SLAVE ) then
					r_async_slave_action [ $c ]
				endif

				if (task_kind( scheduler_mainThread($c) ) = SYNCHRONOUS 
						and syncPriority( scheduler_currentPhase($c) ) = MASTER
						and  action( scheduler_currentPhase($c) ) = WRITING_ON_SB ) then
					r_sync_master_writing [$c] 
				endif
				if (task_kind( scheduler_mainThread($c) ) = SYNCHRONOUS 
						and syncPriority( scheduler_currentPhase($c) ) = MASTER
						and  action( scheduler_currentPhase($c) ) = READING_FROM_SB ) then
					r_sync_master_reading [$c] 
				endif
				if (task_kind( scheduler_mainThread($c) ) = SYNCHRONOUS 
						and syncPriority( scheduler_currentPhase($c) ) = SLAVE ) then
					r_sync_slave_action [$c] 
				endif
			endpar
			
	// Evaluate Outer Machine Action
		rule r_evaluateAction ($c in Scheduler)	=
			par
				if (action( scheduler_currentPhase ($c) ) = ELABORATING) then
					r_elaborate [ $c ]
				endif
				if (action( scheduler_currentPhase ($c) ) = WAITING_TRIGGER) then
					r_waitTrigger [ $c , scheduler_currentPhase ($c) ]
				endif
				if (action( scheduler_currentPhase ($c) ) = WRITING_ON_SB) then
					r_patternMachine_execute [ $c ]
				endif
				if (action( scheduler_currentPhase ($c) ) = READING_FROM_SB) then
					r_patternMachine_execute [ $c ]
				endif
			endpar

	// State Machine Update		
		rule r_doNextActionAndUpdateState ($c in Scheduler) =
			seq
				if (scheduler_currentPhaseIndex($c) + 1 < length(scheduler_seqPhase($c)) ) then
					// Next phase: update state
					scheduler_currentPhaseIndex($c) := scheduler_currentPhaseIndex($c) + 1 
				else
					// Reset state machine
					par						
						r_reset_Time [ scheduler_mainThread ($c) ]
						scheduler_currentPhaseIndex($c) := 0
					endpar
				endif
								
				scheduler_currentPhase($c) := at(scheduler_seqPhase($c) , iton(scheduler_currentPhaseIndex($c) ))
			
				// Update schedule time
					scheduler_currentScheduleTime ($c) := 0
				
				// Evaluate Action
					r_evaluateAction [$c] 
			endseq

	// Update Schedule Time
		rule r_scheduler_updateSchedule ($c in Scheduler, $inc in Integer) =		
			par
				r_update_Time [scheduler_mainThread($c) , $inc ]	// Update time
				scheduler_currentScheduleTime ($c) := scheduler_currentScheduleTime ($c) + $inc // Execute schedule
			endpar

// Main Rule of Outer States Machine
	rule r_scheduler_mainRule ($c in Scheduler) =
		if ( outsidePatternMachine( $c ) ) then
			// OUTSIDE PATTERN MACHINE
			par
				if (action( scheduler_currentPhase ($c) ) = ELABORATING) then
					// Wait for elaboration time, then go to next phase
					if ( tElapsed ($c ) ) then
						r_doNextActionAndUpdateState [ $c ]
					endif
				endif
				
				if (action( scheduler_currentPhase ($c) ) = WAITING_TRIGGER) then
					if (trigger ($c) ) then
						r_doNextActionAndUpdateState [ $c ]
					endif
				endif
					
				// Exit from pattern machine (and recompute time)
				if (action( scheduler_currentPhase ($c) ) = WRITING_ON_SB
						or action( scheduler_currentPhase ($c) ) = READING_FROM_SB) then
					seq
						// recompute schedule (don't lose time in returning from pattern action)
							r_scheduler_updateSchedule [$c , -baseTime] 
						// Compute next action of outer machine
							r_doNextActionAndUpdateState [ $c ]
					endseq
				endif
			endpar
		else
			// INSIDE PATTERN MACHINE
			r_patternMachine_execute [ $c ]
		endif

// --------------------------------------------------------------------------------------------------	
// OUTER STATES MACHINES SCHDULER EXECUTION
	rule r_scheduler_execute ($c in Scheduler) =
		seq
			r_scheduler_updateSchedule [$c , baseTime] 
			r_scheduler_mainRule [$c] 
		endseq
	
