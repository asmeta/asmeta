/* Complete Application */

asm CompleteApplication

import ../../STDL/StandardLibrary
import ../CommonBehaviour/Scheduler
		
// --------------------------------------------------------------------------------------------------			
signature:
// FUNCTIONS 
	// "while cycle" control
		dynamic monitored passed: Boolean
		dynamic controlled initialized: Boolean

	// 1-Shared memory (Swinging Buffer)
		// Camera TO VisualServoing	
			static cam2Vs_SB: SwingingBuffer
			static cam2Vs_data: Seq(DataStream)
			static cam2Vs_sem: Semaphore
	
		// VisualServoing TO Robot	
			static vs2Rob_SB: SwingingBuffer
			static vs2Rob_data: Seq(DataStream)
			static vs2Rob_sem: Semaphore	
	
		// Encoders TO Robot	
			static enc2Rob_SB: SwingingBuffer
			static enc2Rob_data: Seq(DataStream)
			static enc2Rob_sem: Semaphore
			
		// Robot TO Motors	
			static rob2Mot_SB: SwingingBuffer
			static rob2Mot_data: Seq(DataStream)
			static rob2Mot_sem: Semaphore
			
	// 2-Synchronous taskes
		// Camera
			static camera_scheduler: Scheduler
			static camera_task: Task
			static camera_period: Integer
			static camera_elaborationTime: Integer
			static camera_writeTime: Integer
			static camera_globalTaskTime: Integer
	
		// RobotController
			static robot_scheduler: Scheduler
			static robot_task: Task
			static robot_period: Integer
			static robot_readTime_vs: Integer
			static robot_readTime_enc: Integer
			static robot_writeTime: Integer
			static robot_elaborationTime: Integer
			static robot_globalTaskTime: Integer	
	
		// Encoders
			static encoders_scheduler: Scheduler
			static encoders_task: Task
			static encoders_period: Integer
			static encoders_elaborationTime: Integer
			static encoders_writeTime: Integer
			static encoders_globalTaskTime: Integer

		// Motors
			static motors_scheduler: Scheduler
			static motors_task: Task
			static motors_period: Integer
			static motors_readTime_enc: Integer
			static motors_readTime_rob: Integer
			static motors_elaborationTime: Integer
			static motors_globalTaskTime: Integer	

	// 3-Asynchronous taskes
		// VisualServoing
			static vs_scheduler: Scheduler	
			static vs_task: Task
			static vs_elaborationTime: Integer
			static vs_readTime: Integer
			static vs_writeTime: Integer
			static vs_globalTaskTime: Integer	


// --------------------------------------------------------------------------------------------------			
definitions:
	// Period of synchronous tasks (us)
		// Camera
			function camera_period = 10000
			function camera_elaborationTime = 6000
			function camera_writeTime = baseTime
			function camera_globalTaskTime = camera_elaborationTime + camera_writeTime
	
		// Robot
			function robot_period = 5000
			function robot_readTime_vs = baseTime
			function robot_readTime_enc = baseTime
			function robot_elaborationTime = 2000
			function robot_writeTime = baseTime
			function robot_globalTaskTime = 
						robot_elaborationTime + robot_readTime_vs + robot_readTime_enc + robot_writeTime + robot_elaborationTime	

		// Encoders
			function encoders_period = 500
			function encoders_elaborationTime = baseTime
			function encoders_writeTime = baseTime
			function encoders_globalTaskTime = encoders_elaborationTime + encoders_writeTime 	
		
		// Motor		
			function motors_period = 1000
			function motors_readTime_enc = baseTime
			function motors_readTime_rob = baseTime
			function motors_elaborationTime = 300
			function motors_globalTaskTime = 
						motors_readTime_enc + motors_readTime_rob + motors_elaborationTime		
	
	// Elaboration time of asynchronous tasks (us)
			function vs_elaborationTime = 12000
			function vs_readTime = baseTime
			function vs_writeTime = baseTime
			function vs_globalTaskTime = vs_elaborationTime + vs_readTime + vs_writeTime
	
	// Buffers of SwingingBuffer
		function cam2Vs_data = []
		function vs2Rob_data = []
		function enc2Rob_data = []
		function rob2Mot_data = []

// RULES
	// Variable rule for elaboration
		rule r_elab ($c in Scheduler) =
			skip

	// Taskes, memory and Controllers initialization
		rule r_init = 
			// In Seq because the controllers have to be updated to add the phases
			seq
			// 1-SwingingBuffer	
				r_SwingingBuffer_init [cam2Vs_SB , cam2Vs_sem, 2n , cam2Vs_data]		
				r_SwingingBuffer_init [vs2Rob_SB , vs2Rob_sem, 2n , vs2Rob_data]
				r_SwingingBuffer_init [enc2Rob_SB , enc2Rob_sem, 2n , enc2Rob_data]
				r_SwingingBuffer_init [rob2Mot_SB , rob2Mot_sem, 2n , rob2Mot_data]
							
			// 2-Synchronous		
				// Camera 
					// Init task
						r_task_init [camera_task, SYNCHRONOUS , camera_period , camera_globalTaskTime ]
					// Init controller
						r_scheduler_init [camera_scheduler, camera_task]				
					// Add states
						r_phases_add_ELABORATION [ camera_scheduler, camera_elaborationTime, <<r_elab(Scheduler)>> ]
						r_phases_add_WRITING_ON_SB_SLAVE [ camera_scheduler, camera_writeTime,  cam2Vs_SB ]
						r_phases_add_WAITING_TRIGGER_SYNC [ camera_scheduler, camera_period ]
					// Set starting state	
						r_phases_setStartingPoint [ camera_scheduler , 0 ]
							
				// Robot Controller 
					// Init task
						r_task_init [robot_task, SYNCHRONOUS , robot_period , robot_globalTaskTime ]
					// Init controller
						r_scheduler_init [robot_scheduler, robot_task ]				
					// Add states
						r_phases_add_READING_FROM_SB_SLAVE [ robot_scheduler, robot_readTime_vs,  vs2Rob_SB ]
						r_phases_add_READING_FROM_SB_MASTER [ robot_scheduler, robot_readTime_enc,  enc2Rob_SB ]
						r_phases_add_ELABORATION [ robot_scheduler, robot_elaborationTime, <<r_elab(Scheduler)>> ]
						r_phases_add_WRITING_ON_SB_MASTER [ robot_scheduler, robot_writeTime,  rob2Mot_SB ]
						r_phases_add_WAITING_TRIGGER_SYNC [ robot_scheduler, robot_period ]
					// Set starting state	
						r_phases_setStartingPoint [ robot_scheduler , 0 ]	
						
				// Encoders Controller 
					// Init task
						r_task_init [encoders_task, SYNCHRONOUS , encoders_period , encoders_globalTaskTime ]
					// Init controller
						r_scheduler_init [encoders_scheduler, encoders_task ]				
					// Add states
						r_phases_add_ELABORATION [ encoders_scheduler, encoders_elaborationTime, <<r_elab(Scheduler)>> ]					
						r_phases_add_WRITING_ON_SB_SLAVE [ encoders_scheduler, encoders_writeTime,  enc2Rob_SB ]
						r_phases_add_WAITING_TRIGGER_SYNC [ encoders_scheduler, encoders_period ]
					// Set starting state	
						r_phases_setStartingPoint [ encoders_scheduler , 0 ]	
						
				// Motors Controller 
					// Init task
						r_task_init [motors_task, SYNCHRONOUS , motors_period , motors_globalTaskTime ]
					// Init controller
						r_scheduler_init [motors_scheduler, motors_task ]				
					// Add states
						r_phases_add_READING_FROM_SB_SLAVE [ motors_scheduler, motors_readTime_enc,  enc2Rob_SB ]
						r_phases_add_READING_FROM_SB_SLAVE [ motors_scheduler, motors_readTime_rob,  rob2Mot_SB ]
						r_phases_add_ELABORATION [ motors_scheduler, motors_elaborationTime, <<r_elab(Scheduler)>> ]
						r_phases_add_WAITING_TRIGGER_SYNC [ motors_scheduler, motors_period ]
					// Set starting state	
						r_phases_setStartingPoint [ motors_scheduler , 0 ]		
			
			// 3-Asynchronous
				// Visual Servoing
					// Init task
						r_task_init [vs_task, ASYNCHRONOUS , 0 , vs_globalTaskTime ]
					// Init controller
						r_scheduler_init [vs_scheduler, vs_task ]	
					// Add states
						r_phases_add_READING_FROM_SB_MASTER [ vs_scheduler, vs_readTime,  cam2Vs_SB ]
						r_phases_add_ELABORATION [ vs_scheduler, vs_elaborationTime, <<r_elab(Scheduler)>> ]
						r_phases_add_WRITING_ON_SB_MASTER [ vs_scheduler, vs_writeTime,  vs2Rob_SB ]
						r_phases_add_WAITING_TRIGGER_ASYNC [ vs_scheduler ]
					// Set starting state	
						r_phases_setStartingPoint [ vs_scheduler , 0 ]
	
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
				// Executing all the taskes in par... 
				// it can be that 2 taskes access the critical section at the same time
				// Executing all the taskes in sequence, it's possible to simulate the behaviour of a scheduler...
				// -> from higher priority
				seq
					r_scheduler_execute [ encoders_scheduler ]
					r_scheduler_execute [ motors_scheduler ]
					r_scheduler_execute [ robot_scheduler ]
					r_scheduler_execute [ camera_scheduler ]
					r_scheduler_execute [ vs_scheduler ]
				endseq
			endif
		endseq
	
// --------------------------------------------------------------------------------------------------		
	default init initial_state:
		// Init temp variables
			function initialized = false