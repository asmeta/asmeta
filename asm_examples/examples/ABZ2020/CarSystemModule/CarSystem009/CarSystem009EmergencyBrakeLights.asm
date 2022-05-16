//Ninth refinement of Adaptive Exterior Light and Speed Control System
//Fault handling and general properties
//from SCS-40 to SCS-43

module CarSystem009EmergencyBrakeLights
import ../../StandardLibrary
import ../CarSystem004/CarSystem004Domains
import ../CarSystem004/CarSystem004Functions
import ../CarSystem006/CarSystem006Functions
import ../CarSystem007/CarSystem007EmergencyBrakeSpeed
export *

signature:
	// DOMAINS
	
	// FUNCTIONS
	/*
	controlled reverseLight: LightPercentage // Reverse light is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampLeft: LightPercentage // Brake lamp left is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampRight: LightPercentage // Brake lamp right is off (0%) or is on with a specific percentage of brightness 
	controlled brakeLampCenter: LightPercentage // Brake lamp center is off (0%) or is on with a specific percentage of brightness
	controlled brakeLampCenterStatus: TailLampStatus // Brake lamp center is off (0%) or is on with a specific percentage of brightness
	*/
	derived automaticEmergencyBrake : Boolean //Emergency brake activated due to SCS-27 SCS-28
	derived brakeIsOn: Boolean //Brake lamps are on
	
definitions:	
	// DOMAIN DEFINITIONS
	
	
	// FUNCTION DEFINITIONS		
	
	function brakeIsOn = 
		 (brakeLampLeft != 0 and brakeLampRight != 0 and brakeLampCenter != 0)		
		 
	function automaticEmergencyBrake =
	// 	(currentSpeed > 0 and currentSpeed <= 60 and stationaryObstacle) or (currentSpeed > 0 and currentSpeed <= 120 and movingObstacle)
		(currentSpeed > 0 and currentSpeed <= 600 and stationaryObstacle) or (currentSpeed > 0 and currentSpeed <= 1200 and movingObstacle) 

	macro rule r_setBrakeValue ($value in LightPercentage) =
		par
			brakeLampLeft := setOverVoltageValueLight($value)
			brakeLampRight := setOverVoltageValueLight($value)
			brakeLampCenter := setOverVoltageValueLight($value)
		endpar
		
	//SCS-27
	//SCS-43
	macro rule r_EmergencyBrakeLights = 
		par
			if ((brakePedal > 15 or automaticEmergencyBrake) and (not brakeIsOn)) then
				r_setBrakeValue[100]
			endif
			if (brakePedal < 5 and not automaticEmergencyBrake and brakeIsOn) then
				r_setBrakeValue[0]
			endif
			if ((brakePedal > 200 or timeImpact = 3) and brakeLampCenterStatus = FIX) then
				brakeLampCenterStatus := BLINK
			endif
			if (brakePedal = 0 and timeImpact != 3 and brakeLampCenterStatus = BLINK) then 
				brakeLampCenterStatus := FIX
			endif
		endpar
