//One-Way Traffic Light
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//modello raffinato senza agenti
//molte ripetizioni; i quattro r_switchTo... potrebbero essere sostituiti da una sola regola parametrica
//in questo modo, pero', viene mantenuto il modello dell'articolo

asm oneWayTrafficLight_refined

import ../../STDL/StandardLibrary

signature:
	abstract domain LightUnit
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 | STOP1STOP2CHANGING | GO2STOP1CHANGING | STOP2STOP1CHANGING | GO1STOP2CHANGING }
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	static timer: PhaseDomain -> Integer
	static lightUnit1: LightUnit
	static lightUnit2: LightUnit
	dynamic monitored passed: Integer -> Boolean
	dynamic controlled rPulse: LightUnit -> Boolean
	dynamic controlled gPulse: LightUnit -> Boolean
	
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
	
	rule r_stop1stop2_to_stop1stop2changing =
		if(phase=STOP1STOP2) then
			if(passed(timer(STOP1STOP2))) then
				par
					r_switchToGo2[]
					phase:=STOP1STOP2CHANGING
				endpar
			endif
		endif
		
	rule r_go2stop1_to_go2stop1changing =
		if(phase=GO2STOP1) then
			if(passed(timer(GO2STOP1))) then
				par
					r_switchToStop2[]
					phase:=GO2STOP1CHANGING
				endpar
			endif
		endif
	
	rule r_stop2stop1_to_stop2stop1changing =
		if(phase=STOP2STOP1) then
			if(passed(timer(STOP2STOP1))) then
				par
					r_switchToGo1[]
					phase:=STOP2STOP1CHANGING
				endpar
			endif
		endif
		
	rule r_go1stop2_to_go1stop2changing =
		if(phase=GO1STOP2) then
			if(passed(timer(GO1STOP2))) then
				par
					r_switchToStop1[]
					phase:=GO1STOP2CHANGING
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
	
	macro rule r_changeState =
		par
			if(phase=STOP1STOP2CHANGING) then
				phase := GO2STOP1
			endif
			if(phase=GO2STOP1CHANGING) then
				phase := STOP2STOP1
			endif
			if(phase=STOP2STOP1CHANGING) then
				phase := GO1STOP2
			endif
			if(phase=GO1STOP2CHANGING) then
				phase := STOP1STOP2
			endif
		endpar
	
	main rule r_Main =
		//versione parallela; il passaggio di stato avviene in due transizioni
		par
			r_stop1stop2_to_stop1stop2changing[]
			r_go2stop1_to_go2stop1changing[]
			r_stop2stop1_to_stop2stop1changing[]
			r_go1stop2_to_go1stop2changing[]
			r_pulses[]
			r_changeState[]
		endpar
			
		
		//versione sequenziale; in una sola transizione viene eseguito il passaggio di stato
		/*seq
		
			par
				r_stop1stop2_to_stop1stop2changing[]
				r_go2stop1_to_go2stop1changing[]
				r_stop2stop1_to_stop2stop1changing[]
				r_go1stop2_to_go1stop2changing[]
			endpar
			r_pulses[]
			r_changeState[]
		endseq*/

default init s0:
	function stopLight($l in LightUnit) = true
	function goLight($l in LightUnit) = false
	function phase = STOP1STOP2
	function rPulse($l in LightUnit) = false
	function gPulse($L in LightUnit) = false