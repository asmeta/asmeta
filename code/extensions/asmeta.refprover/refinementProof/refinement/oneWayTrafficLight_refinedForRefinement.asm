//One-Way Traffic Light
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//modello raffinato senza agenti
//molte ripetizioni; i quattro r_switchTo... potrebbero essere sostituiti da una sola regola parametrica
//in questo modo, pero', viene mantenuto il modello dell'articolo

asm oneWayTrafficLight_refinedForRefinement

import StandardLibrary

signature:
	domain TimeDomain subsetof Integer
	enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2}
	enum domain PhaseDomainRefined = { STOP1STOP2_REF | GO2STOP1_REF | STOP2STOP1_REF | GO1STOP2_REF | STOP1STOP2CHANGING | GO2STOP1CHANGING | STOP2STOP1CHANGING | GO1STOP2CHANGING }
	dynamic controlled phase_refined: PhaseDomainRefined
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	static timer: PhaseDomain -> TimeDomain
	dynamic monitored passed: TimeDomain -> Boolean
	dynamic controlled rPulse: LightUnit -> Boolean
	dynamic controlled gPulse: LightUnit -> Boolean
	derived phase: PhaseDomain
	
definitions:
	domain TimeDomain = {50, 120}

	//for refinement proof
	function phase =
		switch phase_refined
			case STOP1STOP2_REF: STOP1STOP2
			case GO2STOP1_REF: GO2STOP1 
			case STOP2STOP1_REF: STOP2STOP1
			case GO1STOP2_REF: GO1STOP2
			case STOP1STOP2CHANGING: STOP1STOP2
			case GO2STOP1CHANGING: GO2STOP1
			case STOP2STOP1CHANGING: STOP2STOP1
			case GO1STOP2CHANGING: GO1STOP2
		endswitch

	function timer($p in PhaseDomain) =	switch($p)
											case STOP1STOP2 : 50
											case GO2STOP1 : 120
											case STOP2STOP1 : 50
											case GO1STOP2 : 120
										endswitch

	rule r_switchToGo2 =
		par
			rPulse(LIGHTUNIT2) := true
			gPulse(LIGHTUNIT2) := true
		endpar
	
	rule r_switchToStop2 =
		par
			rPulse(LIGHTUNIT2) := true
			gPulse(LIGHTUNIT2) := true
		endpar

	rule r_switchToGo1 =
		par
			rPulse(LIGHTUNIT1) := true
			gPulse(LIGHTUNIT1) := true
		endpar
		
	rule r_switchToStop1 =
		par
			rPulse(LIGHTUNIT1) := true
			gPulse(LIGHTUNIT1) := true
		endpar
	
	rule r_stop1stop2_to_stop1stop2changing =
		if(phase_refined=STOP1STOP2_REF) then
			//if(passed(timer(STOP1STOP2_REF))) then
				par
					r_switchToGo2[]
					phase_refined:=STOP1STOP2CHANGING
				endpar
			//endif
		endif
		
	rule r_go2stop1_to_go2stop1changing =
		if(phase_refined=GO2STOP1_REF) then
			//if(passed(timer(GO2STOP1_REF))) then
				par
					r_switchToStop2[]
					phase_refined:=GO2STOP1CHANGING
				endpar
			//endif
		endif
	
	rule r_stop2stop1_to_stop2stop1changing =
		if(phase_refined=STOP2STOP1_REF) then
			//if(passed(timer(STOP2STOP1_REF))) then
				par
					r_switchToGo1[]
					phase_refined:=STOP2STOP1CHANGING
				endpar
			//endif
		endif
		
	rule r_go1stop2_to_go1stop2changing =
		if(phase_refined=GO1STOP2_REF) then
			//if(passed(timer(GO1STOP2_REF))) then
				par
					r_switchToStop1[]
					phase_refined:=GO1STOP2CHANGING
				endpar
			//endif
		endif
	
	rule r_pulses =
		forall $l in LightUnit with true do
			par
				if(gPulse($l)) then
					par
						goLight($l) := not(goLight($l))
						gPulse($l) := false
					endpar
				endif
				if(rPulse($l)) then
					par
						stopLight($l) := not(stopLight($l))
						rPulse($l) := false
					endpar
				endif
			endpar
	
	macro rule r_changeState =
		par
			if(phase_refined=STOP1STOP2CHANGING) then
				//if(passed(timer(STOP1STOP2_REF))) then
				if(passed(timer(STOP1STOP2))) then
					phase_refined := GO2STOP1_REF
				endif
			endif
			if(phase_refined=GO2STOP1CHANGING) then
				//if(passed(timer(GO2STOP1_REF))) then
				if(passed(timer(GO2STOP1))) then
					phase_refined := STOP2STOP1_REF
				endif
			endif
			if(phase_refined=STOP2STOP1CHANGING) then
				//if(passed(timer(STOP2STOP1_REF))) then
				if(passed(timer(STOP2STOP1))) then
					phase_refined := GO1STOP2_REF
				endif
			endif
			if(phase_refined=GO1STOP2CHANGING) then
				//if(passed(timer(GO1STOP2_REF))) then
				if(passed(timer(GO1STOP2))) then
					phase_refined := STOP1STOP2_REF
				endif
			endif
		endpar

	invariant inv_refInv over phase: isDef(phase)

	main rule r_Main =
		par
			r_stop1stop2_to_stop1stop2changing[]
			r_go2stop1_to_go2stop1changing[]
			r_stop2stop1_to_stop2stop1changing[]
			r_go1stop2_to_go1stop2changing[]
			r_pulses[]
			r_changeState[]
		endpar

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase_refined = STOP1STOP2_REF
	function rPulse($l in LightUnit) = false
	function gPulse($L in LightUnit) = false