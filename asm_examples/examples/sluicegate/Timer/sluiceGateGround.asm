//Sluice Gate Control
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger
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

import ../../../STDL/TimeLibrary

signature:
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled phase: PhaseDomain
	static timer10MinPassed: Timer
	static timer3hPassed: Timer
	
	
definitions:
	
	main rule r_Main =
		par
			if phase=FULLYCLOSED and expired(timer3hPassed) then
				par
					phase := FULLYOPEN
					r_reset_timer[timer10MinPassed]
				endpar
			endif
			if phase=FULLYOPEN and expired(timer10MinPassed) then
				par
					phase := FULLYCLOSED
					r_reset_timer[timer3hPassed]
				endpar
			endif
		endpar	

default init s0:
	function phase = FULLYCLOSED
	function duration($t in Timer) = if $t = timer10MinPassed 	then 10 //600=10 min in sec
    								else 
    									if $t = timer3hPassed	then 3 endif //10200=3h-10min in sec
   									endif
   									
	function start($t in Timer) = currentTime($t)
	
	function timerUnit($t in Timer) = if $t = timer10MinPassed 	then MIN 
    									else 
    										if $t = timer3hPassed	then HOUR endif 
   									endif