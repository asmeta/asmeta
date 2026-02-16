/*
 * A simple job scheduler that at each step randomly choose a ready job and make it running. 
 * At each step, running jobs may be terminated by the environment.
 * If not job is ready, the scheduler is set to idle.
 *
 * VERSION with temporal properties to be proved with the model checker (not related to this tutorial)
 */
 
asm Scheduler_TLProp

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

signature:
	enum domain Job = {JOB1|JOB2|JOB3}
	enum domain Status = {RDY|RUN|FIN}
	controlled st: Job -> Status
	out idle: Boolean
	monitored fin: Job -> Boolean
definitions:

	macro rule r_SetRunning = 
		choose $j1 in Job with st($j1) = RDY do 
			st($j1) := RUN
		ifnone 
			idle:= true
			
	macro rule r_SetFinished = 
		forall $j2 in Job with st($j2) = RUN do 
			if fin($j2) then
				st($j2) := FIN 
			endif
	
	// assumption about the environment - must be written as INVAR
	INVAR (forall $j in Job with fin($j) implies st($j) = RUN)
	
	// property - if a job is finished, its status will become FIN
	CTLSPEC (forall $j in Job with ag(fin($j) implies ax(st($j) = FIN))) 

	// (assumption) only running jobs can finish
	invariant inv_fin over fin : (forall $j in Job with fin($j) implies st($j) = RUN)
	
	// it is possible to have all the jobs finished
	CTLSPEC ef((forall $j in Job with st($j) = FIN)) 
	// it is possible to have no job finished
	CTLSPEC ef((forall $j in Job with st($j) != FIN)) 


	// if a job is finished, it cannot be resumed
	CTLSPEC (forall $j in Job with ag(st($j) = FIN implies ag(st($j) = FIN))) 

	// a job remains ready or running until (weak) fin becomes true
	CTLSPEC (forall $j in Job with aw(st($j) = RUN or st($j) = RDY, fin($j))) 

			
	main rule r_Main =
		par 
			r_SetRunning[] 
			r_SetFinished[] 
		endpar
		
default init s0:
	function idle = false
	function st($j in Job) = RDY