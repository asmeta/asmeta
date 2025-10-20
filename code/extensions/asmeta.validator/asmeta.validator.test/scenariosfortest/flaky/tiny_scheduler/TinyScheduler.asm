/*
 * A simple job scheduler that at each step randomly choose a ready job and make it running. 
 * At each step, each running job may finish with probability 40%.
 * If not job is ready, the scheduler is set to idle.
 */
asm TinyScheduler

import ../StandardLibrary
signature:
    enum domain Job = { JOB1 | JOB2 | JOB3 }
    enum domain Status = { READY | RUNNING | FINISHED}

    controlled jobStatus: Job -> Status
    controlled idle: Boolean
    
definitions:

    main rule r_Main =
		par
			// Randomly choose a job that is ready
			choose $j1 in Job with jobStatus($j1) = READY do
				jobStatus($j1) := RUNNING
			ifnone
				idle:= true
			// Any running job has a 40% probability of finishing
			forall $j2 in Job with jobStatus($j2) = RUNNING do
				choose $p in {1:100} with true do
					if $p <= 40 then
						jobStatus($j2) := FINISHED
					endif
		endpar
		
default init s0:
    function idle = false
    function jobStatus($j in Job) = READY
