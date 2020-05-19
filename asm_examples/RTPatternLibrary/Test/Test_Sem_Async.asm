/* Test of the semaphore: ASYNCHRONOUS PROCESS and ONE CRITICAL SECTION */

asm Test_Sem_Async

import ../../STDL/StandardLibrary
import ../CommonBehaviour/Task
import ../Pattern/Semaphore
		
// --------------------------------------------------------------------------------------------------			
signature:
// FUNCTIONS 
	dynamic monitored passed: Boolean
	dynamic controlled indexCycle: Integer
	dynamic controlled initialized: Boolean

	// Taskes
		static p1: Task
		static p2: Task
		static p3: Task
		
	// Semaphore
		static sem: Semaphore

// --------------------------------------------------------------------------------------------------
definitions:
// RULES
	// Initialization
		rule r_init = 
			par
				r_sem_init [sem,1]
				forall $pinit in Task with true do
					r_task_init [$pinit , ASYNCHRONOUS, 0, 0]
				
				initialized := true
			endpar
	
	//Main Rule
		main rule r_main =
		seq
			if (not(initialized)) then
				r_init[] 
			endif
		
			if (passed) then
				par
					indexCycle := indexCycle + 1
					if (mod(indexCycle,3)!=0) then
						choose $p1 in Task with task_currState($p1) = READY do
							r_sem_wait [sem, $p1] 
					else
						choose $p2 in Task with task_currState($p2) = EXECUTION do
							r_sem_post [sem, $p2] 
					endif	 
				endpar
			endif
		endseq
	
// --------------------------------------------------------------------------------------------------	
default init initial_state:
	// Init temp variables
		function indexCycle = 0 
		function initialized = false
		
