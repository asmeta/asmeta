//Sluice Gate Control
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//modello ground

//A small sluice, with a rising and falling gate, is used in a simple
//irrigation system. A computer system is needed to control the sluice
//gate: the requirement is that the gate should be held in the fully open
//position for ten minutes in every three hours and otherwise kept in
//the fully closed position.
//The gate is opened and closed by rotating vertical screws. The screws
//are driven by a small motor, which can be controlled by clockwise,
//anticlockwise, on and off pulses.
asm sluiceGateGround

import STDL/StandardLibrary

signature:
	domain Minutes subsetof Integer
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled phase: PhaseDomain
	derived openPeriod: Minutes
	derived closedPeriod: Minutes
	dynamic monitored passed: Minutes -> Boolean

	
definitions:
	domain Minutes = {10, 170}
	function openPeriod = 10
	function closedPeriod = 170 //(3*60 - 10)
	
	
	rule r_open =
		skip
	
	rule r_shut =
		skip		
	
	main rule r_Main =
		par
			if(phase=FULLYCLOSED) then
				if(passed(closedPeriod)) then
					par
						r_open[]
						phase := FULLYOPEN
					endpar
				endif
			endif
			if(phase=FULLYOPEN) then
				if(passed(openPeriod)) then
					par
						r_shut[]
						phase := FULLYCLOSED
					endpar
				endif
			endif
		endpar	

default init s0:
	function phase = FULLYCLOSED
