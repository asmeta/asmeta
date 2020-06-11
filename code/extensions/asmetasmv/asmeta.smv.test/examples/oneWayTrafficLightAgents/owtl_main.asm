//One-Way Traffic Light
//from paper "The Abstract State Machines Method for High-Level System Design and Analysis" di Egon Borger
//models with agents
//A lot of repetitions; the 4 r_switchTo... could be substituted by a single parametric rule
//However, in this way we strictly follow the model reported in the paper

//We needed to add the states ...CHANGING

asm owtl_main

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

import owtl_ctl
import owtl_pulses

signature:
	
definitions:
	
	CTLSPEC 
	ag(((phase=STOP1STOP2 or phase=STOP2STOP1) and stopLight(LIGHTUNIT1) and not(goLight(LIGHTUNIT1)) and stopLight(LIGHTUNIT2) and not(goLight(LIGHTUNIT2))) or
	(phase=GO2STOP1 and not(stopLight(LIGHTUNIT2)) and goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1) and not(goLight(LIGHTUNIT1))) or
	(phase=GO1STOP2 and not(stopLight(LIGHTUNIT1)) and goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2) and not(goLight(LIGHTUNIT2))) or
	(phase=STOP1STOP2CHANGING or phase=GO2STOP1CHANGING or phase=STOP2STOP1CHANGING or phase=GO1STOP2CHANGING))

	//rPulse and gPulse signals are always read and reset in one step
	//i segnali rPulse e gPulse sono sempre letti ed azzerati in un solo passo 
	CTLSPEC ag(rPulse(LIGHTUNIT1) implies ax(not(rPulse(LIGHTUNIT1))))
	CTLSPEC ag(rPulse(LIGHTUNIT2) implies ax(not(rPulse(LIGHTUNIT2))))
	CTLSPEC ag(gPulse(LIGHTUNIT1) implies ax(not(gPulse(LIGHTUNIT1))))
	CTLSPEC ag(gPulse(LIGHTUNIT2) implies ax(not(gPulse(LIGHTUNIT2))))

	//in each state (for each traffic light), either the red light or the green light is turned on
    CTLSPEC ag(goLight(LIGHTUNIT1) xor stopLight(LIGHTUNIT1))
    CTLSPEC ag(goLight(LIGHTUNIT2) xor stopLight(LIGHTUNIT2))

    //if a traffic light is green, the other is red, or both are red
    CTLSPEC ag((goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)) xor 
                   (goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)) xor
                   (stopLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)))

    //equivalent to the previous one
    CTLSPEC ag(not(goLight(LIGHTUNIT1) and goLight(LIGHTUNIT2)))
    
   //CTLSPEC over phase: ag( (phase=STOP1STOP2 or phase=STOP2STOP1) iff
     //             (stopLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)) )
    //CTLSPEC ag(phase=GO1STOP2 iff (goLight(LIGHTUNIT1) and stopLight(LIGHTUNIT2)))
    //CTLSPEC ag(phase=GO2STOP1 iff (goLight(LIGHTUNIT2) and stopLight(LIGHTUNIT1)))

    //correct transitions between states
    CTLSPEC ag(phase=STOP1STOP2 implies ax(phase=STOP1STOP2 or phase=STOP1STOP2CHANGING))
    CTLSPEC ag(phase=GO2STOP1 implies ax(phase=GO2STOP1 or phase=GO2STOP1CHANGING))
    CTLSPEC ag(phase=STOP2STOP1 implies ax(phase=STOP2STOP1 or phase=STOP2STOP1CHANGING))
    CTLSPEC ag(phase=GO1STOP2 implies ax(phase=GO1STOP2 or phase=GO1STOP2CHANGING))

    //if the traffic light is red, eventually it will become green
    CTLSPEC ag(stopLight(LIGHTUNIT1) implies ef(goLight(LIGHTUNIT1)))
    CTLSPEC ag(stopLight(LIGHTUNIT2) implies ef(goLight(LIGHTUNIT2)))

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
