//Sluice Gate Control
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//first refinement

asm sluiceGateMotorCtl

import ../../STDL/StandardLibrary

signature:
	domain Minutes subsetof Integer
	abstract domain Position
	enum domain PhaseDomain = { FULLYCLOSED | OPENING | FULLYOPEN | CLOSING }
	enum domain DirectionDomain = { CLOCKWISE | ANTICLOCKWISE }
	enum domain MotorDomain = { ON | OFF }//per mantenere i termini dell'articolo
	dynamic controlled phase: PhaseDomain
	dynamic controlled dir: DirectionDomain
	dynamic controlled motor: MotorDomain
	static openPeriod: Minutes
	static closedPeriod: Minutes
	static top: Position
	static bottom: Position
	dynamic monitored passed: Minutes -> Boolean
	dynamic monitored event: Position -> Boolean
	
	
definitions:
	domain Minutes = {10, 170}
	function openPeriod = 10
	function closedPeriod = 170 //(3*60 - 10)
	
	
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
		
		
	main rule r_Main =
		par
			if(phase=FULLYCLOSED) then
				if(passed(170)) then
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
				if(passed(10)) then
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
	