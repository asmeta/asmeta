//Second refinement of Adaptive Exterior Light and Speed Control System
//Cornering light
//from ELS-24 to ELS-27

module CarSystem002Cornering
import ../../StandardLibrary
import CarSystem002LowBeam
export *

signature:
	
	domain SteeringAngle subsetof Integer // Steering wheel angle	

	controlled corneringLightLeft: LightPercentage // Cornering light left is off (0%) or is on with a specific percentage of brightness
	controlled corneringLightRight: LightPercentage // Cornering light right is off (0%) or is on with a specific percentage of brightness
	monitored steeringAngle: SteeringAngle // Angle of the steering wheel
	monitored reverseGear: Boolean // Reverse gear is engaged (True) or not (False)
		
definitions:
	// DOMAIN DEFINITIONS
	domain SteeringAngle = {0 :1022}
	//0 = sensor is calibrating
	//1-410 = steering wheel rotation to the left (Resolution: 1 starting from 10 deflection)
	//411-510 = steering wheel rotation to the left (Resolution: 0.1 for 0-10 deflection)
	//511-513 = steering wheel in neutral position
	//514-613 = steering wheel rotation to the right (Resolution: 0.1 for 0-10 deflection)
	//614-1022 = steering wheel rotation to the right (Resolution: 1 starting from 10 deflection)
	
	// FUNCTION DEFINITIONS
		
	
	// RULE DEFINITIONS
	
	
	//Turn cornering lights off
	macro rule r_CorneringLightsOff =
		par
			if (corneringLightRight != 0) then
				corneringLightRight := 0
			endif
			if (corneringLightLeft != 0) then
				corneringLightLeft := 0
			endif
		endpar
	
	//Turn cornering lights on	
	macro rule r_CorneringLightsOn ($leftRight in LeftRight) = 
	//ELS-24
		par
			if ($leftRight = RIGHT) then
				corneringLightRight := 100
			endif
			if ($leftRight = LEFT) then
				corneringLightLeft := 100
			endif
		endpar
	
		
	//ELS-24 ELS-25 ELS-26 ELS-27
	macro rule r_CorneringLights =
	
			//ELS-25
			if (not(armoredVehicle and darknessModeSwitchOn ) and lowBeamLightingOn) then
				par
					if (currentSpeed > 0 and  currentSpeed <= 100) then
						par
							//ELS-26 ELS-24
							if ((steeringAngle >= 614 and steeringAngle <= 1022) or (turnRight(pitmanArmUpDown_RunnReq))) then
								r_CorneringLightsOn[RIGHT] 
							endif
							if ((steeringAngle >= 1 and steeringAngle <= 410) or (turnLeft(pitmanArmUpDown_RunnReq))) then
								r_CorneringLightsOn[LEFT] 
							endif
						endpar
					endif
					//ELS-27
					if (reverseGear) then
						par
							r_CorneringLightsOn[RIGHT] 
							r_CorneringLightsOn[LEFT] 
						endpar
					endif
					if ((corneringLightRight != 0 and corneringLightLeft != 0 and reverseGear = false) and ((corneringLightRight != 0 or corneringLightLeft != 0) and ((steeringAngle >= 411 and steeringAngle <= 613) and (pitmanArmUpDown_RunnReq = NEUTRAL_UD and passed5Sec = true)))) then
						r_CorneringLightsOff[] 
					endif
				endpar
			endif

		
	//ELS-41
	macro rule r_ReverseLight = 
		if (reverseGear) then
			reverseLight := 100
		else
			reverseLight := 0
		endif
	

		
	// INVARIANTS
	
	
	//PROPERTIES
