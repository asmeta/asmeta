//Forth refinement  of Adaptive Exterior Light and Speed Control System
//Emergency Brake
//from ELS-39 to ELS-41

module CarSystem004EmergencyBrakeLights
import ../../StandardLibrary
import CarSystem004Functions
export *

signature:
	
	// DOMAINS

//====================================
	domain BrakePedal subsetof Integer // Deflection of the brake pedal
	
	// FUNCTIONS
	
//====================================		
	monitored brakePedal: BrakePedal //It is discritezed 0 - 0°, 225 - 45° Resolution 0.2°
	/*
	controlled reverseLight: LightPercentage // Reverse light is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampLeft: LightPercentage // Brake lamp left is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampRight: LightPercentage // Brake lamp right is off (0%) or is on with a specific percentage of brightness 
	controlled brakeLampCenter: LightPercentage // Brake lamp center is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampCenterStatus: TailLampStatus // Brake lamp center is off (0%) or is on with a specific percentage of brightness
	*/
//====================================			
	derived brakeIsOn: Boolean //Brake lamps are on
	
definitions:
	// DOMAIN DEFINITIONS
//====================================		
	domain BrakePedal = {0 :225} //res 0.2° 0° - 45° -> 1° = 5 unità
	
	
	// FUNCTION DEFINITIONS
		
//====================================			
	
	function brakeIsOn = 
		 (brakeLampLeft != 0 and brakeLampRight != 0 and brakeLampCenter != 0)			
	
	// RULE DEFINITIONS
	
	macro rule r_setBrakeValue ($value in LightPercentage) =
		par
			brakeLampLeft := setOverVoltageValueLight($value)
			brakeLampRight := setOverVoltageValueLight($value)
			brakeLampCenter := setOverVoltageValueLight($value)
		endpar
		
	//ELS-39 ELS-40
	macro rule r_EmergencyBrakeLights = 
		par
			if (brakePedal > 15 and (not brakeIsOn)) then
				r_setBrakeValue[100] 
			endif
			if (brakePedal < 5 and brakeIsOn) then
				r_setBrakeValue[0]
			endif
			if (brakePedal > 200 and brakeLampCenterStatus = FIX) then
				brakeLampCenterStatus := BLINK
			endif
			if (brakePedal = 0 and brakeLampCenterStatus = BLINK) then 
				brakeLampCenterStatus := FIX
			endif
		endpar
		
	// INVARIANTS
	
	
	//PROPERTIES
