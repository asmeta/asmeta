//Second refinement of Adaptive Exterior Light and Speed Control System
//Low Beam headlights and Cornering light
//from ELS-1 to ELS-29
//from ELS-39 to ELS-41

asm CarSystem004main
import ../../StandardLibrary
import CarSystem004HW
import CarSystem004Cornering
import CarSystem004EmergencyBrakeLights
import CarSystem004HighBeam
export *

signature:
	
		
definitions:
	// DOMAIN DEFINITIONS

	// FUNCTION DEFINITIONS
	
	macro rule r_ReadMonitorFunctions = 
		par
			keyState_Previous := keyState
			lightRotarySwitchPrevious := lightRotarySwitch
		endpar 
		
	// MAIN RULE
	main rule r_Main =
		par
			r_ReadMonitorFunctions[] 
			r_HazardWarningLight[] 
			r_LowBeamHeadlights[]
			r_CorneringLights[] 
			r_ReverseLight[] 
			r_EmergencyBrakeLights[]
			r_HighBeam[] 
		endpar
		

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
	function tailLampLeftFixValue = 0
	function tailLampLeftStatus = FIX
	function tailLampRightBlinkValue  = 0
	function tailLampRightFixValue  = 0
	function tailLampRightStatus = FIX
	//Light rotary switch
	function lightRotarySwitchPrevious = OFF
	//Cornering lights off
	function corneringLightRight = 0
	function corneringLightLeft = 0
	//Low beam headlight
	function lowBeamLeft = 0
	function lowBeamRight = 0
	function parkingLightON = false 
	function reverseLight = 0
	function brakeLampLeft = 0
	function brakeLampRight = 0
	function brakeLampCenter = 0
	function brakeLampCenterStatus = FIX
	//High beam
	function highBeamOn = false
	function pitmanArmForthBack = NEUTRAL_FB  
	function pitmanArmForthBackPrevious = NEUTRAL_FB 
	function oncomingTraffic = false
	function cruiseControlMode = CCM0
	function setVehicleSpeed = 0