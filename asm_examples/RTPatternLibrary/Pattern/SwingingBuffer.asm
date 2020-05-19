/* ASM for Swinginging (Flip) Buffer structure */

asm SwingingBuffer

import ../../STDL/StandardLibrary
import ../CommonBehaviour/Task
import ./Semaphore
export *

// --------------------------------------------------------------------------------------------------	
signature:
// DOMAINS
	// SwingingBuffer domain
		abstract domain SwingingBuffer
		
// FUNCTIONS
	// Index of cell from/to which read/write
		dynamic controlled indexRead: SwingingBuffer -> Natural
		dynamic controlled indexWrite: SwingingBuffer -> Natural
	// Number of buffers 
		dynamic controlled dimension: SwingingBuffer -> Natural

	// Indexes for reading/writing have been updated/evaluated? -> control
		dynamic controlled indexUpdated: SwingingBuffer -> Boolean		
	// Has a task, at least, written to the buffers?
		dynamic controlled sb_firstWrite: SwingingBuffer->Boolean
				
	// Buffers		
		dynamic controlled buffers: SwingingBuffer -> Seq (DataStream)	
	// Critical section semaphore
		dynamic controlled sem: SwingingBuffer->Semaphore	// TODO: e questo, funziona... o è dynamic controlled???

// --------------------------------------------------------------------------------------------------	
definitions:
// RULES
	
	// Swinging Buffer Initialization (SwingingBuffer, Semaphore, Dimension, Buffers)
		rule r_SwingingBuffer_init ($sb in SwingingBuffer , $semph in Semaphore , $i in Natural , $sqnc in Seq(DataStream)) =
			par
				// Indexes controls
					indexRead ($sb) := 1n
					indexWrite ($sb) := 0n
					indexUpdated($sb) := false
					sb_firstWrite($sb) := false	// true when the 1st writing operation is done
				
				// Buffers				
					buffers($sb) := $sqnc 					
					if ($i > 1n) then
						dimension ($sb) := $i 
					else
						dimension ($sb) := 2n
					endif
				
				// Semaphore
					r_sem_init [$semph , 1]
					sem($sb) := $semph 
			endpar
			
	// Writes to swinging buffer (without lock acquisition)
		rule r_SwingingBuffer_write ($sb in SwingingBuffer, $w in DataStream , $p in Task) =
			// Task can write the buffer only if it's not blocked (NOT waiting)
			if (task_currState($p) != WAITING ) then
				par
					// update dataStream managed by the main task
						task_dataStream($p ) := $w 
					// WRITE
						if (length(buffers($sb) ) <= ntoi(indexWrite($sb)) ) then // Buffers empty -> append dataStream
							par
								buffers($sb) := append(buffers($sb) , $w )
								sb_firstWrite($sb) := true
							endpar
						else // Buffers not empty -> replace old dataStream
							buffers($sb) := replaceAt(buffers($sb) , indexWrite($sb) , $w )
						endif
				endpar
			endif
	
	// Reads from SwingingBuffer (without lock acquisition)
		rule r_SwingingBuffer_read ($sb in SwingingBuffer, $p in Task) =
			// Task can read the buffer only if it's not blocked (NOT waiting)		
			if (task_currState($p) != WAITING ) then
				if (sb_firstWrite($sb)
					and length(buffers($sb) ) > ntoi(indexRead($sb)) ) then // If the buffer has already been written
					task_dataStream($p) := at(buffers($sb) , indexRead($sb) )
				else // If the buffer has not already been written: read non-valid value
					task_dataStream($p) := undef
				endif
			endif

	// Updates SwingingBuffer indexes (without lock acquisition)
		rule r_SwingingBuffer_updateIndexes ($sb in SwingingBuffer, $p in Task) =
			// To update: buffers have been written + task admitted to CS + indexes not yet updated
			if (task_currState($p) = EXECUTION ) then
				if (sb_firstWrite($sb )
						and not(indexUpdated($sb) )) then
					par
						indexRead($sb) := mod(indexRead($sb) + 1n , dimension($sb) )
						indexWrite($sb) := mod(indexWrite($sb) + 1n , dimension($sb) )
						indexUpdated($sb) := true	
					endpar
				else
					indexUpdated($sb) := true
				endif
			endif 
	
	// Get lock
	// At the end of the call of this rule, the status of the caller has been updated...
	//			-> if task in EXECUTION, I can access the critical section
	//			-> if task READY, the task is SYNCHRONOUS and it has no time to wait the Semaphore is free
	//					(critical section not accessed and elaboration with old data)
		rule r_SwingingBuffer_getLock ($sb in SwingingBuffer, $p in Task) = 
			if (task_kind($p) = ASYNCHRONOUS) then // ASYNCHRONOUS TASK
				r_sem_wait [sem($sb) , $p ] // If task is already waiting, this call does nothing...
			else // SYNCHRONOUS TASK
				if (thereIsTime ($p )) then // There is remaining time -> try to wait
					r_sem_trywait [sem($sb) , $p ]
				else // There isn't remaining time -> task skips the critical section code
					task_currState ($p) := READY	
				endif
			endif

	// Release lock: post to the Critical Section Semaphore			
	rule r_SwingingBuffer_releaseLock ($sb in SwingingBuffer, $p in Task) =
		r_sem_post [sem($sb) , $p ]