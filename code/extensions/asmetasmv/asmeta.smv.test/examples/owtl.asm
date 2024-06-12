//One-Way Traffic Light
//da articolo "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//modello ground
//molte ripetizioni; i quattro r_switchTo... potrebbero essere sostituiti da una sola regola parametrica
//in questo modo, pero', viene mantenuto il modello dell'articolo

asm owtl

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
    enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
    enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 | STOP2STOP1 | GO1STOP2 }
    domain Intervals subsetof Integer
    dynamic controlled phase: PhaseDomain
    dynamic controlled stopLight: LightUnit -> Boolean
    dynamic controlled goLight: LightUnit -> Boolean
    dynamic monitored passed: Intervals -> Boolean

definitions:
    domain Intervals = {50, 120}
        
    macro rule r_switch($l in Boolean) =
        $l := not($l)
    
    rule r_switchLightUnit($l in LightUnit) =
        par
            r_switch[goLight($l)]
            r_switch[stopLight($l)]
        endpar
    
    rule r_stop1stop2_to_go2stop1 =
        if(phase=STOP1STOP2 and passed(50)) then
            par
                r_switchLightUnit[LIGHTUNIT2]
                phase:=GO2STOP1
            endpar
        endif
        
    rule r_go2stop1_to_stop2stop1 =
        if(phase=GO2STOP1 and passed(120)) then
            par
                r_switchLightUnit[LIGHTUNIT2]
                phase:=STOP2STOP1
            endpar
        endif
    
    rule r_stop2stop1_to_go1stop2 =
        if(phase=STOP2STOP1 and passed(50)) then
            par
                r_switchLightUnit[LIGHTUNIT1]
                phase:=GO1STOP2
            endpar
        endif
        
    rule r_go1stop2_to_stop1stop2 =
        if(phase=GO1STOP2 and passed(120)) then
            par
                r_switchLightUnit[LIGHTUNIT1]
                phase:=STOP1STOP2
            endpar
        endif

	CTLSPEC ag(stopLight(LIGHTUNIT1) implies ef(goLight(LIGHTUNIT1)))
	CTLSPEC ag(stopLight(LIGHTUNIT2) implies ef(goLight(LIGHTUNIT2)))

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
