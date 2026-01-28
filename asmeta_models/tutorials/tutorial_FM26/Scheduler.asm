/*
 * A simple job scheduler that at each step randomly choose a ready job and make it running. 
 * At each step, running job are terminated using a controlled variable.
 * If not job is ready, the scheduler is set to idle.
 * 
 */
asm Scheduler

import ../../STDL/StandardLibrary

signature:
	enum domain Job = {JOB1|JOB2|JOB3}
	enum domain Status = {RDY|RUN|FIN}
	controlled st: Job -> Status
	controlled idle: Boolean
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
			
	main rule r_Main =
		par 
			r_SetRunning[] 
			r_SetFinished[] 
		endpar
		
default init s0:
	function idle = false
	function st($j in Job) = RDY