asm semaphore2processes

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

//With two processes; the CTL properties predicate only on two processes (process1 and process2). 

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
	//i due processi non possono mai essere contemporaneamente nella regione critica
	//the two processes can never be in the critical section at the same time
	invariant over processStatus: not(processStatus(process1)=CRITICAL and processStatus(process2)=CRITICAL)

	//CTL properties
	//safety property: i due processi non possono mai essere contemporaneamente nella regione critica
	//safety property: the two processes can never be in the critical section at the same time
	CTLSPEC ag(not(processStatus(process1)=CRITICAL and processStatus(process2)=CRITICAL))

	//liveness property: se un processo vuole entrare, esiste un cammino in cui entra
	//liveness property: if a process wants to enter the critical section, it exists
	//a path along which it enters
	CTLSPEC ag(processStatus(process1)=ENTERING implies ef(processStatus(process1)=CRITICAL))
	CTLSPEC ag(processStatus(process2)=ENTERING implies ef(processStatus(process2)=CRITICAL))

	//liveness property: se un processo e' nella regione critica, esiste un cammino per cui ne esce
	//liveness property: if a process is in the critical section, it exists
	//a path along which it exits
	CTLSPEC ag(processStatus(process1)=CRITICAL implies ef(processStatus(process1)=EXITING))
	CTLSPEC ag(processStatus(process2)=CRITICAL implies ef(processStatus(process2)=EXITING))

	main rule r_Main =
		choose $p in Process with true do
			program($p)

default init s0:
	function processStatus($p in Process) = IDLE
	function semaphore = GREEN

	agent Process:
		r_processRun[]
