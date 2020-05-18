asm semaphore

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

//It can contain any number n > 0 of processes. The CTL properties predicate
//over all processes.

signature:
	domain Process subsetof Agent
	enum domain SemaphoreLight = {GREEN | RED}
	enum domain ProcessStatus =  {IDLE | ENTERING | CRITICAL | EXITING}

	dynamic controlled processStatus: Process -> ProcessStatus // stato del processo
	dynamic controlled semaphore: SemaphoreLight

	dynamic monitored wantToEnter: Process -> Boolean
	dynamic monitored wantToExit: Process -> Boolean

	static process1: Process
	static process2: Process
	static process3: Process
	static process4: Process
	static process5: Process
	//static process6: Process
	//static process7: Process
	//static process8: Process
	//static process9: Process

definitions:

	rule r_decideToEnter =
		if(processStatus(self) = IDLE) then
			if(wantToEnter(self)) then
				processStatus(self) := ENTERING
			endif
		endif

	rule r_entering =
		if(processStatus(self) = ENTERING and semaphore = GREEN) then
			par
				processStatus(self) := CRITICAL
				semaphore := RED
			endpar
		endif

	rule r_decideToExit =
		if(processStatus(self) = CRITICAL) then
			if(wantToExit(self)) then
				processStatus(self) := EXITING
			endif
		endif

	rule r_exiting =
		if(processStatus(self) = EXITING) then
			par
				processStatus(self) := IDLE
				semaphore := GREEN
			endpar
		endif

	rule r_processRun =
		par
			r_decideToEnter[]
			r_entering[]
			r_decideToExit[]
			r_exiting[]
		endpar

	//AsmetaL invariant
	//due processi non possono mai essere contemporaneamente nella regione critica
	//two processes can never be in the critical section at the same time
	invariant over processStatus: not(exist $a in Process, $b in Process with $a!=$b and
												processStatus($a)=CRITICAL and
												processStatus($b)=CRITICAL  )


	//CTL properties

	//due processi non possono mai essere contemporaneamente nella regione critica
	//two processes can never be in the critical section at the same time
	CTLSPEC ag(not(exist $a in Process, $b in Process with $a!=$b and
												processStatus($a)=CRITICAL and
												processStatus($b)=CRITICAL  ))

	//liveness property: se un processo vuole entrare, esiste un cammino in cui entra
	//liveness property: if a process wants to enter the critical section, it exists
	//a path along which it enters
	CTLSPEC (forall $p in Process with ag(processStatus($p)=ENTERING
												implies ef(processStatus($p)=CRITICAL)))

	//liveness property: se un processo e' nella regione critica, esiste un cammino per cui ne esce
	//liveness property: if a process is in the critical section, it exists
	//a path along which it exits
	CTLSPEC (forall $p in Process with ag(processStatus($p)=CRITICAL
												implies ef(processStatus($p)=EXITING)))

	main rule r_Main =
		choose $p in Process with true do
			program($p)

default init s0:
	function processStatus($p in Process) = IDLE
	function semaphore = GREEN

	agent Process:
		r_processRun[]
