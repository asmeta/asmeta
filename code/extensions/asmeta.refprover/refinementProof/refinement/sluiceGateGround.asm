//Sluice Gate Control
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Borger
//ground model

//A small sluice, with a rising and falling gate, is used in a simple
//irrigation system. A computer system is needed to control the sluice
//gate: the requirement is that the gate should be held in the fully open
//position for ten minutes in every three hours and otherwise kept in
//the fully closed position.
//The gate is opened and closed by rotating vertical screws. The screws
//are driven by a small motor, which can be controlled by clockwise,
//anticlockwise, on and off pulses.
asm sluiceGateGround

import StandardLibrary

signature:
	domain Minutes subsetof Integer
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled phase: PhaseDomain
	dynamic monitored passed: Minutes -> Boolean

definitions:
	domain Minutes = {10, 170}

	rule r_open =
		skip
	
	rule r_shut =
		skip		
	
	main rule r_Main =
		par
			if phase=FULLYCLOSED then
				if passed(170) then
					par
						r_open[]
						phase := FULLYOPEN
					endpar
				endif
			endif
			if phase=FULLYOPEN then
				if passed(10) then
					par
						r_shut[]
						phase := FULLYCLOSED
					endpar
				endif
			endif
		endpar

default init s0:
	function phase = FULLYCLOSED