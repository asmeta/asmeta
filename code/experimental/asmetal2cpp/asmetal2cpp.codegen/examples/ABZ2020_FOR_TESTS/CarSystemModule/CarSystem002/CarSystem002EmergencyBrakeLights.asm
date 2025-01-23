//Second refinement of Adaptive Exterior Light and Speed Control System
//Emergency Brake
//from ELS-39 to ELS-41

module CarSystem002EmergencyBrakeLights
import ../../StandardLibrary
import CarSystem002Functions
export *

signature:
	
	// DOMAINS

//====================================
	domain BrakePedal subsetof Integer // Deflection of the brake pedal
	
	// FUNCTIONS
	
//====================================		
	monitored brakePedal: BrakePedal //It is discritezed 0 - 0°, 225 - 45° Resolution 0.2°
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
			brakeLampLeft := 100
			brakeLampRight := 100
			brakeLampCenter := 100
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
