//Sluice Gate Control
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//modello ground

asm sluiceGateGround

import ../../STDL/StandardLibrary

signature:
	domain Minutes subsetof Integer
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled phase: PhaseDomain
	static openPeriod: Minutes
	static closedPeriod: Minutes
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
				if(passed(170)) then
					par
						r_open[]
						phase := FULLYOPEN
					endpar
				endif
			endif
			if(phase=FULLYOPEN) then
				if(passed(10)) then
					par
						r_shut[]
						phase := FULLYCLOSED
					endpar
				endif
			endif
		endpar	

default init s0:
	function phase = FULLYCLOSED