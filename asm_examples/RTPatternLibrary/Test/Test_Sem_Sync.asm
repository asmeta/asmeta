/* Test of the semaphore: SYNCHRONOUS PROCESS and ONE CRITICAL SECTION */

asm Test_Sem_Sync

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
			seq
				r_sem_init [sem,1]
				forall $pinit in Task with true do
					r_task_init [$pinit , SYNCHRONOUS, 0, 0]
					
				r_task_init [p2 , SYNCHRONOUS, 200, 3]	
				
				initialized := true
			endseq
	
	//Main Rule
		main rule r_main =
		seq
			if (not(initialized)) then
				r_init[] 
			endif
		
			if (passed) then
				par
					indexCycle := indexCycle + 1
					if (mod(indexCycle,5)=0) then
						choose $p1 in Task with task_currState($p1) = READY do
							r_sem_trywait [sem, $p1]
					endif 
					if (mod(indexCycle,7)=0) then
						choose $p2 in Task with (task_currState($p2) = WAITING and task_kind($p2) = SYNCHRONOUS) do
							r_sem_trywait [sem, $p2] 
					endif
					if (mod(indexCycle,6)=0) then
						choose $p3 in Task with task_currState($p3) = EXECUTION do
							r_sem_post [sem, $p3] 
					endif	 
				endpar
			endif
		endseq
	
// --------------------------------------------------------------------------------------------------
default init initial_state:
	// Init temp variables
		function indexCycle = 0 
		function initialized = false
		
