//One-Way Traffic Light
//from the paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger
//refined model with agents
//molte ripetizioni; i quattro r_switchTo... potrebbero essere sostituiti da una sola regola parametrica
//in questo modo, pero', viene mantenuto il modello dell'articolo
//aggiunti gli stati ...CHANGING

asm oneWayTrafficLight_refined_with_agents

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
	enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
	domain PULSESAgent subsetof Agent
	domain ONEWAYTRAFLIGHTCTLTAgent subsetof Agent
	enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 | STOP1STOP2CHANGING | GO2STOP1CHANGING | STOP2STOP1CHANGING | GO1STOP2CHANGING }
	domain Intervals subsetof Integer
	dynamic controlled phase: PhaseDomain
	dynamic controlled stopLight: LightUnit -> Boolean
	dynamic controlled goLight: LightUnit -> Boolean
	
	dynamic monitored passed: Intervals -> Boolean
	dynamic controlled rPulse: LightUnit -> Boolean
	dynamic controlled gPulse: LightUnit -> Boolean
		
	static pulses: PULSESAgent
	static onewaytraflightctl: ONEWAYTRAFLIGHTCTLTAgent
	
	
definitions:
	domain Intervals = {50, 120}

	rule r_switch($l in Boolean) =
		$l := not($l)
	
	rule r_emit($pulse in Boolean) =
		$pulse := true

	rule r_switchToGo2 =
		par
			r_emit[rPulse(LIGHTUNIT2)]
			r_emit[gPulse(LIGHTUNIT2)]
		endpar

	rule r_switchToStop2 =
		par
			r_emit[rPulse(LIGHTUNIT2)]
			r_emit[gPulse(LIGHTUNIT2)]
		endpar

	rule r_switchToGo1 =
		par
			r_emit[rPulse(LIGHTUNIT1)]
			r_emit[gPulse(LIGHTUNIT1)]
		endpar

	rule r_switchToStop1 =
		par
			r_emit[rPulse(LIGHTUNIT1)]
			r_emit[gPulse(LIGHTUNIT1)]
		endpar

	rule r_stop1stop2_to_stop1stop2changing =
		if(phase=STOP1STOP2) then
			if(passed(50)) then
				par
					r_switchToGo2[]
					phase:=STOP1STOP2CHANGING
				endpar
			endif
		endif

	rule r_go2stop1_to_go2stop1changing =
		if(phase=GO2STOP1) then
			if(passed(120)) then
				par
					r_switchToStop2[]
					phase:=GO2STOP1CHANGING
				endpar
			endif
		endif

	rule r_stop2stop1_to_stop2stop1changing =
		if(phase=STOP2STOP1) then
			if(passed(50)) then
				par
					r_switchToGo1[]
					phase:=STOP2STOP1CHANGING
				endpar
			endif
		endif

	rule r_go1stop2_to_go1stop2changing =
		if(phase=GO1STOP2) then
			if(passed(120)) then
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

	rule r_changeState =
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

	rule r_ONEWAYTRAFLIGHTCTLTAgent = 
		par
			r_stop1stop2_to_stop1stop2changing[]
			r_go2stop1_to_go2stop1changing[]
			r_stop2stop1_to_stop2stop1changing[]
			r_go1stop2_to_go1stop2changing[]
			r_changeState[]
		endpar
	
	CTLSPEC 
	ag(((phase=STOP1STOP2 or phase=STOP2STOP1) and stopLight(LIGHTUNIT1) and not(goLight(LIGHTUNIT1)) and stopLight(LIGHTUNIT2) and not(goLight(LIGHTUNIT2))) or
	(phase=GO2STOP1 and not(stopLight(LIGHTUNIT2)) and goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1) and not(goLight(LIGHTUNIT1))) or
	(phase=GO1STOP2 and not(stopLight(LIGHTUNIT1)) and goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2) and not(goLight(LIGHTUNIT2))) or
	(phase=STOP1STOP2CHANGING or phase=GO2STOP1CHANGING or phase=STOP2STOP1CHANGING or phase=GO1STOP2CHANGING))

	//i segnali rPulse e gPulse sono sempre letti ed azzerati in un solo passo
	//the rPulse e gPulse signals are always read and resetted in a single step 
	CTLSPEC ag(rPulse(LIGHTUNIT1) implies ax(not(rPulse(LIGHTUNIT1))))
	CTLSPEC ag(rPulse(LIGHTUNIT2) implies ax(not(rPulse(LIGHTUNIT2))))
	CTLSPEC ag(gPulse(LIGHTUNIT1) implies ax(not(gPulse(LIGHTUNIT1))))
	CTLSPEC ag(gPulse(LIGHTUNIT2) implies ax(not(gPulse(LIGHTUNIT2))))

	//in each state, in each traffic light,
    //either the red light or the green light is turned on (not both)
    CTLSPEC ag(goLight(LIGHTUNIT1) xor stopLight(LIGHTUNIT1))
    CTLSPEC ag(goLight(LIGHTUNIT2) xor stopLight(LIGHTUNIT2))
    
    //if a traffic light is green, the other one is red or, otherwise are
    //not red
    CTLSPEC ag((goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)) xor 
                   (goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)) xor
                   (stopLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)))

    //this property is equivalent to the previous one
    CTLSPEC ag(not(goLight(LIGHTUNIT1) and goLight(LIGHTUNIT2)))
    
    //proprieta' di corretta associazione tra le fasi e le luci accese
	//CTLSPEC ag( (phase=STOP1STOP2 or phase=STOP2STOP1) iff
    //            (stopLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)) )
    //CTLSPEC ag(phase=GO1STOP2 iff (goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)))
    //CTLSPEC ag(phase=GO2STOP1 iff (goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)))

    //the transitions between the states are correct
    CTLSPEC ag(phase=STOP1STOP2 implies ax(phase=STOP1STOP2 or phase=STOP1STOP2CHANGING))
    CTLSPEC ag(phase=GO2STOP1 implies ax(phase=GO2STOP1 or phase=GO2STOP1CHANGING))
    CTLSPEC ag(phase=STOP2STOP1 implies ax(phase=STOP2STOP1 or phase=STOP2STOP1CHANGING))
    CTLSPEC ag(phase=GO1STOP2 implies ax(phase=GO1STOP2 or phase=GO1STOP2CHANGING))

    //liveness: if the traffic light is red, sooner or later it will become green
    CTLSPEC ag(stopLight(LIGHTUNIT1) implies ef(goLight(LIGHTUNIT1)))
    CTLSPEC ag(stopLight(LIGHTUNIT2) implies ef(goLight(LIGHTUNIT2)))

    //deadlock absence (always true)
    CTLSPEC ag(ex(true))	

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
	
	agent ONEWAYTRAFLIGHTCTLTAgent:
		r_ONEWAYTRAFLIGHTCTLTAgent[]
			
	agent PULSESAgent:
		r_pulses[]
