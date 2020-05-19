asm oneWayTrafficLight
//One-Way Traffic Light
//from the paper "The Abstract State Machines Method for High-Level System Design and Analysis" by Egon Boerger

/*
...the traffic is controlled by a pair of simple portable traffic light
units... one unit at each end of the one-way section...connect(ed)...to
a small computer that controls the sequence of lights
Each unit has a Stop light and a Go light.
The computer controls the lights by emitting RPulses and GPulses,
to which the units respond by turning the light on and off.
The regime for the lights repeats a fixed cycle of four phases. First,
for 50 seconds, both units show Stop; then, for 120 seconds, one unit
shows Stop and the other Go; then for 50 seconds both show Stop
again; then for 120 seconds the unit that previously showed Go shows
Stop, and the other shows Go. Then the cycle is repeated.
*/

//ground model

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
    enum domain LightUnit = {LIGHTUNIT1 | LIGHTUNIT2}
    enum domain PhaseDomain = { STOP1STOP2 | GO2STOP1 |
				STOP2STOP1 | GO1STOP2 }
    domain Intervals subsetof Integer
    dynamic controlled phase: PhaseDomain
    dynamic controlled stopLight: LightUnit -> Boolean
    dynamic controlled goLight: LightUnit -> Boolean
    dynamic monitored passed: Intervals -> Boolean

definitions:
    domain Intervals = {50, 120}

    rule r_switch($l in Boolean) =
        $l := not($l)

    rule r_switchLightUnit($l in LightUnit) =
        par
            r_switch[goLight($l)]
            r_switch[stopLight($l)]
        endpar

    rule r_stop1stop2_to_go2stop1 =
        if(phase=STOP1STOP2) then
	        if(passed(50)) then
	            par
	                r_switchLightUnit[LIGHTUNIT2]
	                phase:=GO2STOP1
	            endpar
	        endif
        endif

    rule r_go2stop1_to_stop2stop1 =
        if(phase=GO2STOP1) then
	        if(passed(120)) then
	            par
	                r_switchLightUnit[LIGHTUNIT2]
	                phase:=STOP2STOP1
	            endpar
	        endif
        endif

    rule r_stop2stop1_to_go1stop2 =
        if(phase=STOP2STOP1) then
	        if(passed(50)) then
	            par
	                r_switchLightUnit[LIGHTUNIT1]
	                phase:=GO1STOP2
	            endpar
	        endif
        endif

    rule r_go1stop2_to_stop1stop2 =
        if(phase=GO1STOP2) then
	        if(passed(120)) then
	            par
	                r_switchLightUnit[LIGHTUNIT1]
	                phase:=STOP1STOP2
	            endpar
	        endif
        endif

    //in each state, in each traffic light,
    //either the red light or the green light is turned on (not both)
    CTLSPEC ag(goLight(LIGHTUNIT1) xor stopLight(LIGHTUNIT1))
    CTLSPEC ag(goLight(LIGHTUNIT2) xor stopLight(LIGHTUNIT2))

    //if a traffic light is green, the other one is red or, otherwise are
    //not red
    CTLSPEC ag((goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)) xor 
			    (goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)) xor
			    (stopLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1))
			    )

    //this property is equivalent to the previous one
    CTLSPEC ag(not(goLight(LIGHTUNIT1) and goLight(LIGHTUNIT2)))

    //the phases and the lights are updated correctly
    CTLSPEC ag( (phase=STOP1STOP2 or phase=STOP2STOP1) iff
                  (stopLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)) )
    CTLSPEC ag(phase=GO1STOP2 iff (goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)))
    CTLSPEC ag(phase=GO2STOP1 iff (goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)))

    //the transitions between the states are correct
    CTLSPEC ag(phase=STOP1STOP2 implies ax(phase=GO2STOP1 or phase=STOP1STOP2))
    CTLSPEC ag(phase=GO2STOP1 implies ax(phase=STOP2STOP1 or phase=GO2STOP1))
    CTLSPEC ag(phase=STOP2STOP1 implies ax(phase=GO1STOP2 or phase=STOP2STOP1))
    CTLSPEC ag(phase=GO1STOP2 implies ax(phase=STOP1STOP2 or phase=GO1STOP2))

    //liveness: if the traffic light is red, sooner or later it will become green
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
