//Sluice Gate Control
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Borger
//refined model

//A small sluice, with a rising and falling gate, is used in a simple
//irrigation system. A computer system is needed to control the sluice
//gate: the requirement is that the gate should be held in the fully open
//position for ten minutes in every three hours and otherwise kept in
//the fully closed position.
//The gate is opened and closed by rotating vertical screws. The screws
//are driven by a small motor, which can be controlled by clockwise,
//anticlockwise, on and off pulses.
asm sluiceGateMotorCtlWithInv

import StandardLibrary

signature:
	domain Minutes subsetof Integer
	enum domain Position = { TOP| BOTTOM }
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	enum domain PhaseDomainRef = { FULLYCLOSED_REF | OPENING | FULLYOPEN_REF | CLOSING }
	enum domain DirectionDomain = { CLOCKWISE | ANTICLOCKWISE }
	enum domain MotorDomain = { ON | OFF }
	dynamic controlled phaseRefined: PhaseDomainRef
	dynamic controlled dir: DirectionDomain
	dynamic controlled motor: MotorDomain
	static openPeriod: Minutes
	static closedPeriod: Minutes
	static top: Position
	static bottom: Position
	dynamic monitored passed: Minutes -> Boolean
	dynamic monitored event: Position -> Boolean

	derived phase: PhaseDomain

definitions:
	domain Minutes = {10, 170}
	function openPeriod = 10
	function closedPeriod = 170 //(3*60 - 10)

	function phase =
		if phaseRefined = FULLYCLOSED_REF or phaseRefined = OPENING then
			FULLYCLOSED
		else
			FULLYOPEN
		endif

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

	invariant inv_invForRef over phase: isDef(phase)

	main rule r_Main =
		par
			if(phaseRefined=FULLYCLOSED_REF) then
				par
					r_start_to_raise[]
					phaseRefined := OPENING
				endpar
			endif
			if(phaseRefined=OPENING) then
				if(event(TOP)) then
					if(passed(closedPeriod)) then
						par
							r_stop_motor[]
							phaseRefined := FULLYOPEN_REF
						endpar
					endif
				endif
			endif
			if(phaseRefined=FULLYOPEN_REF) then
				par
					r_start_to_lower[]
					phaseRefined := CLOSING
				endpar
			endif
			if(phaseRefined=CLOSING) then
				if(event(BOTTOM)) then
					if(passed(openPeriod)) then
						par
							r_stop_motor[]
							phaseRefined := FULLYCLOSED_REF
						endpar
					endif
				endif
			endif
		endpar

default init s0:
	function phaseRefined = FULLYCLOSED_REF
	function motor = OFF
	function dir = ANTICLOCKWISE
	