//One-Way Traffic Light
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger
//refined model with agents
//senza gli stati intermedi; l'utente, per fare una simulazione corretta, deve
//impostare alternativamente a true e false le variabili monitorate
//se impostiamo l'invariante viene subito violato dopo la prima monitorata a true

asm oneWayTrafficLight_refined_with_agents_problem

import ../../STDL/StandardLibrary

signature:
	abstract domain LightUnit
	domain PULSESAgent subsetof Agent
	domain ONEWAYTRAFLIGHTCTLTAgent subsetof Agent
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 }
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	static timer: PhaseDomain -> Integer
	static lightUnit1: LightUnit
	static lightUnit2: LightUnit
	dynamic monitored passed: Integer -> Boolean
	dynamic controlled rPulse: LightUnit -> Boolean
	dynamic controlled gPulse: LightUnit -> Boolean
		
	static pulses: PULSESAgent
	static onewaytraflightctl: ONEWAYTRAFLIGHTCTLTAgent
	
	
definitions:

	function timer($p in PhaseDomain) =	switch($p)
											case STOP1STOP2 : 50
											case GO2STOP1 : 120
											case STOP2STOP1 : 50
											case GO1STOP2 : 120
										endswitch
	
	macro rule r_switch($l in Boolean) =
		$l := not($l)
	
	macro rule r_emit($pulse in Boolean) =
		$pulse := true
		
	rule r_switchToGo2 =
		par
			r_emit[rPulse(lightUnit2)]
			r_emit[gPulse(lightUnit2)]
		endpar
	
	rule r_switchToStop2 =
		par
			r_emit[rPulse(lightUnit2)]
			r_emit[gPulse(lightUnit2)]
		endpar

	rule r_switchToGo1 =
		par
			r_emit[rPulse(lightUnit1)]
			r_emit[gPulse(lightUnit1)]
		endpar
		
	rule r_switchToStop1 =
		par
			r_emit[rPulse(lightUnit1)]
			r_emit[gPulse(lightUnit1)]
		endpar
	
	rule r_stop1stop2_to_stop1stop2 =
		if(phase=STOP1STOP2) then
			if(passed(timer(STOP1STOP2))) then
				par
					r_switchToGo2[]
					phase:=GO2STOP1
				endpar
			endif
		endif
		
	rule r_go2stop1_to_go2stop1 =
		if(phase=GO2STOP1) then
			if(passed(timer(GO2STOP1))) then
				par
					r_switchToStop2[]
					phase:=STOP2STOP1
				endpar
			endif
		endif
	
	rule r_stop2stop1_to_stop2stop1 =
		if(phase=STOP2STOP1) then
			if(passed(timer(STOP2STOP1))) then
				par
					r_switchToGo1[]
					phase:=GO1STOP2
				endpar
			endif
		endif
		
	rule r_go1stop2_to_go1stop2 =
		if(phase=GO1STOP2) then
			if(passed(timer(GO1STOP2))) then
				par
					r_switchToStop1[]
					phase:=STOP1STOP2
				endpar
			endif
		endif
	
	rule r_pulses =
		forall $l in LightUnit with true do
			par
				if(gPulse($l)) then
					par
						r_switch[goLight($l)]
						gPulse($l) := false
					endpar
				endif
				if(rPulse($l)) then
					par
						r_switch[stopLight($l)]
						rPulse($l) := false
					endpar
				endif
			endpar

	macro rule r_agent =
		par
			r_stop1stop2_to_stop1stop2[]
			r_go2stop1_to_go2stop1[]
			r_stop2stop1_to_stop2stop1[]
			r_go1stop2_to_go1stop2[]
		endpar


	invariant over phase: 
	
	((phase=STOP1STOP2 or phase=STOP2STOP1) and stopLight(lightUnit1) and not(goLight(lightUnit1)) and stopLight(lightUnit2) and not(goLight(lightUnit2))) or
	(phase=GO2STOP1 and not(stopLight(lightUnit2)) and goLight(lightUnit2) and stopLight(lightUnit1) and not(goLight(lightUnit1))) or
	(phase=GO1STOP2 and not(stopLight(lightUnit1)) and goLight(lightUnit1) and stopLight(lightUnit2) and not(goLight(lightUnit2)))

	main rule r_Main =
		par
			program(onewaytraflightctl)
			program(pulses)
		endpar
	
	

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase = STOP1STOP2
	function rPulse($l in LightUnit) = false
	function gPulse($L in LightUnit) = false
	
	agent ONEWAYTRAFLIGHTCTLTAgent:  r_agent[]
		
			
	agent PULSESAgent:
		r_pulses[]