//One-Way Traffic Light
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger
//ground model
//molte ripetizioni; i quattro r_switchTo... potrebbero essere sostituiti da una sola regola parametrica
//in questo modo, pero', viene mantenuto il modello dell'articolo

asm oneWayTrafficLight_inline

import ../../STDL/StandardLibrary

signature:
	abstract domain LightUnit
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 }
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	static timer: PhaseDomain -> Integer
	static lightUnit1: LightUnit
	static lightUnit2: LightUnit
	dynamic monitored passed: Integer -> Boolean

definitions:

	function timer($p in PhaseDomain) =	switch($p)
											case STOP1STOP2 : 50
											case GO2STOP1 : 120
											case STOP2STOP1 : 50
											case GO1STOP2 : 120
										endswitch
		
	macro rule r_switch($l in Boolean) =
		$l := not($l)
	
	rule r_switchToGo2 =
		par
			r_switch[goLight(lightUnit2)]
			r_switch[stopLight(lightUnit2)]
		endpar
	
	rule r_switchToStop2 =
		par
			r_switch[goLight(lightUnit2)]
			r_switch[stopLight(lightUnit2)]
		endpar

	rule r_switchToGo1 =
		par
			r_switch[goLight(lightUnit1)]
			r_switch[stopLight(lightUnit1)]
		endpar
		
	rule r_switchToStop1 =
		par
			r_switch[goLight(lightUnit1)]
			r_switch[stopLight(lightUnit1)]
		endpar
	
		

	//invariant over goLight($l in LightUnit), stopLight($l in LightUnit): 
	//	(goLight($l) and not(stopLight($l))) or (not(goLight($l)) and stopLight($l))
	
	//invariant over goLight(), stopLight(): 
	//	(goLight(lightUnit1) and not(stopLight(lightUnit1))) or (not(goLight(lightUnit1)) and stopLight(lightUnit1))
	main rule r_Main =
		par
			if(phase=STOP1STOP2) then
				if(passed(timer(STOP1STOP2))) then
					par
						r_switchToGo2[]
						phase:=GO2STOP1
					endpar
				endif
			endif
			if(phase=GO2STOP1) then
				if(passed(timer(GO2STOP1))) then
					par
						r_switchToStop2[]
						phase:=STOP2STOP1
					endpar
				endif
			endif
			if(phase=STOP2STOP1) then
				if(passed(timer(STOP2STOP1))) then
					par
						r_switchToGo1[]
						phase:=GO1STOP2
					endpar
				endif
			endif
			if(phase=GO1STOP2) then
				if(passed(timer(GO1STOP2))) then
					par
						r_switchToStop1[]
						phase:=STOP1STOP2
					endpar
				endif
			endif
		endpar

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase = STOP1STOP2