//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

asm CarSystem001main
import ../../StandardLibrary
import CarSystem001HW
export *

signature:
	
		
definitions:
	// DOMAIN DEFINITIONS

	// FUNCTION DEFINITIONS
	
	
	// MAIN RULE
	main rule r_Main =
		r_HazardWarningLight[] 
		

// INITIAL STATE
default init s0:
	//Pitman arm Up Down is in NEUTRAL position
	function pitmanArmUpDown = NEUTRAL_UD
	function pitmanArmUpDown_RunnReq = NEUTRAL_UD	
	function pitmanArmUpDown_Buff = NEUTRAL_UD
	// Hazard Warning is not active
	function hazardWarningSwitchOn = false
	function hazardWarningSwitchOn_Runn = false
	function hazardWarningSwitchOn_Start = false
	//Direction blinkers are off
	function blinkLeft = 0
	function blinkLeftPulseRatio = NOPULSE
	function blinkRight = 0
	function blinkRightPulseRatio = NOPULSE
	//Key is not inserted
	function keyState = NOKEYINSERTED
	//Tail lamp is fixed
	function tailLampLeftBlinkValue = 0
	function tailLampLeftStatus = FIX
	function tailLampRightBlinkValue = 0
	function tailLampRightStatus = FIX
