
// Sluice Gate Control SIMPLIFIED

asm sluiceGateMotorCtl

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

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

	rule r_stop_motor =
		par
			dir := CLOCKWISE
			motor := OFF
		endpar
	

	main rule r_Main =
			if(phase=OPENING) then
				if(event(top)) then
					par
						r_stop_motor[]
						phase := FULLYOPEN
					endpar
				endif
			endif

default init s0:
	function phase = FULLYCLOSED
	function motor = OFF
	function dir = ANTICLOCKWISE
	