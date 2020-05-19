asm processesSchedulerShortestJobNext

//http://en.wikipedia.org/wiki/Shortest_job_next
//Shortest job next (SJN) (also known as Shortest Job First (SJF) or Shortest
//Process Next (SPN)) is a scheduling policy that selects the waiting process
//with the smallest execution time to execute next.

import ../../STDL/StandardLibrary

signature:
	dynamic domain Process subsetof Agent
	domain Scheduler subsetof Agent
	enum domain ProcessStatus = {WAITING | RUNNING | DEAD}
	dynamic controlled status: Process -> ProcessStatus
	dynamic controlled execTime: Process -> Integer
	dynamic controlled cpuOwner: Agent

	static scheduler: Scheduler

definitions:

	rule r_compute =
		if(cpuOwner = self) then
			if execTime(self) = 0n then
				par
					status(self) := DEAD
					cpuOwner := scheduler
				endpar
			else
				execTime(self) := execTime(self) - 1
			endif
		endif

	rule r_schedule =
		if(cpuOwner = self) then
			choose $p in Process with status($p) = WAITING and
									(forall $p2 in Process with ($p != $p2 and status($p2) = WAITING) implies
																				execTime($p) <= execTime($p2)) do
				par
					status($p) := RUNNING
					cpuOwner := $p
				endpar
		endif

	rule r_newProcess =
		choose $k in {1 : 100} with true do
			if($k <= 10) then
				extend Process with $newProcess do
					par
						status($newProcess) := WAITING
						choose $t in {1 : 10} with true do
							execTime($newProcess) := $t
					endpar
			endif

	//al massimo un solo processo e' nello stato RUNNING
	invariant over status: (exist unique $p in Process with status($p) = RUNNING) xor
							(forall $q in Process with status($q) != RUNNING)
	//se un processo e' nello stato di RUNNING, allora ha il controllo della cpu
	invariant over status: (forall $p in Process with status($p) = RUNNING iff (cpuOwner = $p))
	//se nessun processo e' nello stato di RUNNING, allora lo scheduler ha il controllo della cpu
	invariant over status: (forall $p in Process with status($p) != RUNNING) iff (cpuOwner = scheduler)

	main rule r_Main =
		par
			forall $p in Process with true do
				program($p)
			program(scheduler)
			r_newProcess[]	
		endpar

default init s0:
	function cpuOwner = scheduler

	agent Process:
		r_compute[]

	agent Scheduler:
		r_schedule[]
