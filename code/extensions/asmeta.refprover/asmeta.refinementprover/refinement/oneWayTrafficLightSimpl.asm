//One-Way Traffic Light
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//modello ground
//molte ripetizioni; i quattro r_switchTo... potrebbero essere sostituiti da una sola regola parametrica
//in questo modo, pero', viene mantenuto il modello dell'articolo

asm oneWayTrafficLightSimpl

import StandardLibrary

signature:
	enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 }
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	dynamic monitored passed: Boolean

definitions:

	macro rule r_switch($l in Boolean) =
		$l := not($l)
	
	rule r_switchToGo2 =
		par
			goLight(LIGHTUNIT2) := not(goLight(LIGHTUNIT2))
			stopLight(LIGHTUNIT2) := not(stopLight(LIGHTUNIT2))
		endpar
	
	rule r_switchToStop2 =
		par
			goLight(LIGHTUNIT2) := not(goLight(LIGHTUNIT2))
			stopLight(LIGHTUNIT2) := not(stopLight(LIGHTUNIT2))
		endpar

	rule r_switchToGo1 =
		par
			goLight(LIGHTUNIT1) := not(goLight(LIGHTUNIT1))
			stopLight(LIGHTUNIT1) := not(stopLight(LIGHTUNIT1))
		endpar

	rule r_switchToStop1 =
		par
			goLight(LIGHTUNIT1) := not(goLight(LIGHTUNIT1))
			stopLight(LIGHTUNIT1) := not(stopLight(LIGHTUNIT1))
		endpar
	
	rule r_stop1stop2_to_go2stop1 =
		if(phase=STOP1STOP2) then
			if passed then
				par
					r_switchToGo2[]
					phase:=GO2STOP1
				endpar
			endif
		endif
		
	rule r_go2stop1_to_stop2stop1 =
		if(phase=GO2STOP1) then
			if passed then
				par
					r_switchToStop2[]
					phase:=STOP2STOP1
				endpar
			endif
		endif
	
	rule r_stop2stop1_to_go1stop2 =
		if(phase=STOP2STOP1) then
			if passed then
				par
					r_switchToGo1[]
					phase:=GO1STOP2
				endpar
			endif
		endif
		
	rule r_go1stop2_to_stop1stop2 =
		if(phase=GO1STOP2) then
			if passed then
				par
					r_switchToStop1[]
					phase:=STOP1STOP2
				endpar
			endif
		endif

	main rule r_Main =
		par
			r_stop1stop2_to_go2stop1[]
			r_go2stop1_to_stop2stop1[]
			r_stop2stop1_to_go1stop2[]
			r_go1stop2_to_stop1stop2[]
		endpar

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase = STOP1STOP2