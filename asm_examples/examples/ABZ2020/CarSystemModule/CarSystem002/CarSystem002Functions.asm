//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem002Functions
import ../../StandardLibrary
import CarSystem002Domains
import ../CarSystem001/CarSystem001Functions
export *

signature:
	// DOMAINS
	
	
	// FUNCTIONS
	//Parameters setted initially
	static armoredVehicle: Boolean // The vehicle is armored or not
	//Daytime light and ambient light are determined before starting the model and remain unchanged throughout simulation (see web page of ABZ2020)
	static daytimeLight: Boolean // Daytime running light activation. True -> ON, False -> OFF
	static ambientLighting: Boolean // Ambient light activation. True -> ON, False -> OFF


	//monitored currentSpeed: CurrentSpeed // Current speed of the vehicle
	//controlled keyState_Previous: KeyPosition // Position of the key in the previous state
	monitored lightRotarySwitch: LightSwitch // Position of the light rotary switch
	controlled lightRotarySwitchPrevious: LightSwitch // Position of the light rotary switch in the previous state

	controlled reverseLight: LightPercentage // Reverse light is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampLeft: LightPercentage // Brake lamp left is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampRight: LightPercentage // Brake lamp right is off (0%) or is on with a specific percentage of brightness 
	controlled brakeLampCenter: LightPercentage // Brake lamp center is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampCenterStatus: TailLampStatus // Brake lamp center is off (0%) or is on with a specific percentage of brightness

		
	//derived engineOn: KeyPosition -> Boolean // Depending on keyState engineOn is true or false
		
definitions:
	
	
	// FUNCTION DEFINITIONS

	function armoredVehicle = true
	function daytimeLight = true
	function ambientLighting = false
		
	//Engine state is determined by KeyPosition value. If the key is on the engine is on, otherwise is off
	//function engineOn ($key in KeyPosition)=
		//($key = KEYINIGNITIONONPOSITION)
