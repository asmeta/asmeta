/* ASM for communication between a synchronous and an asynchronous task */

asm Test_SB

import ../../STDL/StandardLibrary
import ../CommonBehaviour/Scheduler
		
// --------------------------------------------------------------------------------------------------			
signature:
// FUNCTIONS 
	// "while cycle" control
		dynamic monitored passed: Boolean
		dynamic controlled initialized: Boolean

	// 1-Shared memory (Swing Buffer)
		static shMem: SwingingBuffer
		static shMem_buffers: Seq(DataStream)
		static shMem_semaphore: Semaphore

	// 2-Asynchronous task
		static asynchronousScheduler: Scheduler	
		static asynchronousScheduler_task: Task
		static asynchronousScheduler_elaborationTime: Integer

	// 3-Synchronous task		
		static synchronousScheduler: Scheduler
		static synchronousScheduler_task: Task
		static synchronousScheduler_period: Integer
		static synchronousScheduler_elaborationTime: Integer
		
	// Reading and Writing time
		static timeRead: Integer
		static timeWrite: Integer

// --------------------------------------------------------------------------------------------------			
definitions:
	// Period of synchronous task
		function synchronousScheduler_period = 500
		function synchronousScheduler_elaborationTime = 200
	// Elaboration time of asynchronous task		
		function asynchronousScheduler_elaborationTime = 700
	// Buffers of SwingingBuffer
		function shMem_buffers = []
		
	// Reading and Writing Time
		function timeRead = baseTime
		function timeWrite = 2*baseTime

// RULES
	// Variable rule for elaboration
		rule r_elab ($c in Scheduler) =
			skip

	// Taskes, memory and Controllers initialization
		rule r_init = 
			// In Seq because the controllers have to be updated to add the phases
			seq
			// 1-SwingingBuffer			
				r_SwingingBuffer_init [shMem , shMem_semaphore, 2n , shMem_buffers]
			
			// 2-Asynchronous
				// Init task
					r_task_init [asynchronousScheduler_task, ASYNCHRONOUS , 0 , asynchronousScheduler_elaborationTime + timeRead ]
				// Init controller
					r_scheduler_init [asynchronousScheduler, asynchronousScheduler_task]	
				// Add states
					r_phases_add_READING_FROM_SB_MASTER [asynchronousScheduler, timeRead,  shMem]									// read (outside critical section)
					r_phases_add_ELABORATION [asynchronousScheduler, asynchronousScheduler_elaborationTime, <<r_elab(Scheduler)>> ]	// elaborate
					r_phases_add_WAITING_TRIGGER_ASYNC [asynchronousScheduler]														// end cycle: init next execution
				// Set starting state	
					r_phases_setStartingPoint [asynchronousScheduler, 0] 
			
			// 3-Synchronous
				// Init task
					r_task_init [synchronousScheduler_task, SYNCHRONOUS , synchronousScheduler_period , synchronousScheduler_elaborationTime + timeWrite ]
				// Init controller
					r_scheduler_init [synchronousScheduler, synchronousScheduler_task]				
				// Add states
					r_phases_add_ELABORATION [synchronousScheduler, synchronousScheduler_elaborationTime, <<r_elab(Scheduler)>> ]	// elaborate
					r_phases_add_WRITING_ON_SB_SLAVE [synchronousScheduler, timeWrite,  shMem]										// write (inside critical section)
					r_phases_add_WAITING_TRIGGER_SYNC [synchronousScheduler, synchronousScheduler_period ]							// release lock
				// Set starting state	
					r_phases_setStartingPoint [synchronousScheduler, 2] 
	
			// All initialized
				initialized := true
			endseq
	
	//Main Rule
		main rule r_main =
		seq
			if (not(initialized)) then
				r_init[] 
			endif
		
			if (passed) then
				// TODO: problem -> the forall command executes in par... it can be that 2 taskes access the
				//						critical section at the same time
					//		forall $scheduler in Scheduler with true do
					//			r_scheduler_Execute [ $scheduler ]
				// Executing all the taskes in sequence, it's possible to simulate the behaviour of a scheduler...
				// -> is there a command to do all automatically
				seq
					r_scheduler_execute [ synchronousScheduler ]
					r_scheduler_execute [ asynchronousScheduler ]
				endseq
			endif
		endseq
	
// --------------------------------------------------------------------------------------------------		
	default init initial_state:
		// Init temp variables
			function initialized = false