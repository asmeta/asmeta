//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem001Functions

import ../../StandardLibrary
import CarSystem001Domains
import ../CarSystem000/CarSystem000CommonFunctions
export *
//export marketCode


signature:
	// DOMAINS
	
	
	// FUNCTIONS
	//Parameters setted initially
	static marketCode: MarketCode //Market code
	
	monitored pitmanArmUpDown: PitmanArmUpDown // Position of the pitman arm - vertical position
	controlled pitmanArmUpDown_Buff: PitmanArmUpDown // Save the last position of the pitman arm if something is in execution
	controlled pitmanArmUpDown_RunnReq: PitmanArmUpDown // Save the action running based on pitmanArmUpDown request
	//monitored keyState: KeyPosition // Position of the key
	controlled tailLampLeftBlinkValue: LightPercentage // Tail lamp left is off (0%) or is on with a specific percentage of brightness
	controlled tailLampRightBlinkValue: LightPercentage // Tail lamp right is off (0%) or is on with a specific percentage of brightness
	
	static turnLeft: PitmanArmUpDown -> Boolean //True if pitmanArmUpDown_RunnReq is in position turn left (DOWNWARD5 or DOWNWARD5_LONG or DOWNWARD7)
	static turnRight: PitmanArmUpDown -> Boolean //True if pitmanArmUpDown_Buff is in position turn right (UPWARD5 or UPWARD5_LONG or UPWARD7)
	
definitions:
	
	
	// FUNCTION DEFINITIONS
	
	function marketCode = EU
	

	//True if pitman is in position turn left (DOWNWARD5 or DOWNWARD5_LONG or DOWNWARD7)
	function turnLeft ($position in PitmanArmUpDown)  = 
		($position = DOWNWARD5 or $position = DOWNWARD5_LONG or $position = DOWNWARD7) 
	
	//True if pitman is in position turn right (UPWARD5 or UPWARD5_LONG or UPWARD7)
	function turnRight ($position in PitmanArmUpDown) = 
		($position = UPWARD5 or $position = UPWARD5_LONG or $position = UPWARD7)
