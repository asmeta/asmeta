//Sluice Gate Control
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger
//first refinement

//A small sluice, with a rising and falling gate, is used in a simple
//irrigation system. A computer system is needed to control the sluice
//gate: the requirement is that the gate should be held in the fully open
//position for ten minutes in every three hours and otherwise kept in
//the fully closed position.
//The gate is opened and closed by rotating vertical screws. The screws
//are driven by a small motor, which can be controlled by clockwise,
//anticlockwise, on and off pulses.
asm sluiceGateMotorCtl

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary
import ../../../STDL/Timer

signature:
	abstract domain Position
	enum domain PhaseDomain = { FULLYCLOSED | OPENING | FULLYOPEN | CLOSING }
	enum domain DirectionDomain = { CLOCKWISE | ANTICLOCKWISE }
	enum domain MotorDomain = { ON | OFF }//per mantenere i termini dell'articolo
	dynamic controlled phase: PhaseDomain
	dynamic controlled dir: DirectionDomain
	dynamic controlled motor: MotorDomain
	static top: Position
	static bottom: Position
	dynamic monitored event: Position -> Boolean
	
	static timer10MinPassed: Timer
	derived xtimer10MinPassed: Boolean
	static timer3hPassed: Timer
	derived xtimer3hPassed: Boolean

definitions:

	function xtimer10MinPassed = expired(timer10MinPassed)
	function xtimer3hPassed = expired(timer3hPassed)

	rule r_start_to_raise =
		par
			dir := CLOCKWISE
			motor := ON
		endpar
	
	rule r_start_to_lower =
		par
			dir := ANTICLOCKWISE
			motor := ON
		endpar
		
	rule r_stop_motor =
		motor := OFF
		

	CTLSPEC ef(phase=FULLYOPEN)

	main rule r_Main =
		par
			if(phase=FULLYCLOSED) then
				if xtimer3hPassed then
					par
						r_start_to_raise[]
						phase := OPENING
					endpar
				endif
			endif
			if(phase=OPENING) then
				if(event(top)) then
					par
						r_stop_motor[]
						phase := FULLYOPEN
					endpar
				endif
			endif
			if(phase=FULLYOPEN) then
				if xtimer10MinPassed then
					par
						r_start_to_lower[]
						phase := CLOSING
					endpar
				endif
			endif
			if(phase=CLOSING) then
				if(event(bottom)) then
					par
						r_stop_motor[]
						phase := FULLYCLOSED
					endpar
				endif
			endif
		endpar	

default init s0:
	function phase = FULLYCLOSED
	function motor = OFF
	function dir = ANTICLOCKWISE
	
	function duration($t in Timer) = if $t = timer10MinPassed 	then 10 //600=10 min in sec
    								else 
    									if $t = timer3hPassed	then 3 endif //10200=3h-10min in sec
   									endif
   									
	function start($t in Timer) = initStart($t)
	
	function timerUnit($t in Timer) = if $t = timer10MinPassed 	then MIN 
    									else 
    										if $t = timer3hPassed	then HOUR endif 
   									endif