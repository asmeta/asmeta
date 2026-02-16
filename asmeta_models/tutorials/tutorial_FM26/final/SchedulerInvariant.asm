/*
 * A simple job scheduler that at each step randomly choose a ready job and make it running. 
 * At each step, running job may be terminated by the environment.
 * If not job is ready, the scheduler is set to idle.
 * 
 * In this version we have added an invariant:
 * a job can finish only if it is already running
 * 
 */
asm SchedulerInvariant
import ../../../STDL/StandardLibrary

signature:
	// Domain definition
	enum domain Job = {JOB1 | JOB2 | JOB3}
	enum domain Status = {RDY | RUN | FIN}
	// Controlled and out functions
	dynamic controlled st: Job -> Status
	dynamic out idle: Boolean
	// Monitored functions
	dynamic monitored fin: Job -> Boolean 
	
definitions:

	macro rule r_SetRunning =
		choose $j in Job with st($j) = RDY do
			st($j) := RUN
		ifnone
			idle := true
			
	macro rule r_SetFinished =
//		forall $j in Job with st($j) = RUN do
//			if fin($j) then
// thanks to the invariant, the rule can be simplified as follows:
        forall $j in Job with fin($j) do
				st($j) := FIN
//			endif
			
	invariant inv_fin over fin : (forall $j in Job with fin($j) implies st($j) = RUN)
			
	main rule r_Main =
		par
			r_SetRunning[]
			r_SetFinished[]
		endpar
		
default init s0:
	function idle = false
	function st($j in Job) = RDY