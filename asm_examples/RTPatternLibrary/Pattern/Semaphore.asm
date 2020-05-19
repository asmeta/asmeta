/* ASM implementation of a generic semaphore to control the access to a critical section */
asm Semaphore

import ../../STDL/StandardLibrary
import ../CommonBehaviour/Task
export *

// --------------------------------------------------------------------------------------------------		
signature:
// DOMAINS
	// Semaphore domain
		abstract domain Semaphore
	
// FUNCTIONS
	// Counter of task admittet together to the CRITICAL SECTION 
		dynamic controlled semacount: Semaphore -> Integer
	// Queue of task waiting for access to the critical section
		dynamic controlled semaq: Semaphore -> Seq(Task)
	
// DERIVED FUNCTIONS
	derived isLocked: Semaphore->Boolean

// --------------------------------------------------------------------------------------------------	
definitions:
	// function isLocked -> returns true if the semaphore is locked, so the task can't access the Critical Section
	function isLocked($s in Semaphore) =
		semacount($s) < 1

// --------------------------------------------------------------------------------------------------		
// RULES

	// Semaphore initialization (Semaphore, counterOfTaskAdmittedInCS)
		rule r_sem_init ($s in Semaphore, $i in Integer) =
			par
				semaq ($s) := []
				semacount ($s) := $i 
			endpar

	// rtf_sem_wait
		rule r_sem_wait ($s in Semaphore, $p in Task) =
			// WAIT: only for asynchronous task AND
			// If the calling task can access the semaphore critical section
			if(task_kind($p) = ASYNCHRONOUS and
					task_currState($p) = READY) then
				if (isLocked($s)) then // If the semaphore isn't free, put the task in the semaphore queue
					par
						semaq($s) := append(semaq($s), $p)
						task_currState ($p) := WAITING
					endpar	
				else // If the semaphore is free, put the caller in execution and decrement semacount
					par
						semacount($s) := semacount($s) - 1 
						task_currState ($p) := EXECUTION
					endpar
				endif	
			endif
			
	// rtf_sem_trywait	
		rule r_sem_trywait($s in Semaphore, $p in Task) = 
			// If the calling task can access the semaphore critical section, i.e. it's not in EXECUTION 
			if (not(task_currState ($p) = EXECUTION)) then
				if (not(isLocked($s) )) then // Only if the semaphore is free it's possible to put the caller in execution
					par
						semacount($s) := semacount($s) - 1 
						task_currState ($p) := EXECUTION
					endpar
				else
					// For a synchronous task waiting means that it can wait because it has time to complete its
					// execution in a period...
					task_currState ($p) := WAITING
				endif
			endif
			
	// rtf_sem_post	
		rule r_sem_post ($s in Semaphore, $p in Task) =
			// If the task that notify has the lock on the critical section
			if (task_currState($p) = EXECUTION) then
				par
					// Task leaves the Critical Section -> it becomes READY
					task_currState($p) := READY
				
					// Free the semaphore
					if (not(isEmpty(semaq ($s) ))) then // WAITING TASKS: Notify the first waiting task
						par
							task_currState (first (semaq($s) )) := EXECUTION
							semaq ($s) := excluding (semaq($s) , first (semaq ($s)) )
						endpar
					else // NO WAITING TASKS: Increment the semaphore count
						semacount($s) := semacount($s) + 1
					endif
				endpar
			endif