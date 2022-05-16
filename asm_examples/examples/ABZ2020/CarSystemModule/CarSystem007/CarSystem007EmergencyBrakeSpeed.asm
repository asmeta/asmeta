//Seventh refinement of Adaptive Exterior Light and Speed Control System
//Emergency Brake
//SCS-27 and SCS-28

module CarSystem007EmergencyBrakeSpeed
import ../../StandardLibrary
import ../CarSystem006/CarSystem006Functions
export *

signature:
	// DOMAINS
	domain BrakePressure subsetof Integer
	domain TimeImpactBrake subsetof Integer
	
	// FUNCTIONS
	controlled brakePressure: BrakePressure // Brake pressure
	monitored stationaryObstacle: Boolean // A stationary obstacle is present 
	monitored movingObstacle: Boolean // A moving obstacle is present
	controlled acousticSignalImpact: Boolean // Acoustic signal of brake emergency assistant
	monitored timeImpact: TimeImpactBrake // Time to impact: depending on this value emergency brake is activated at 20%, 60% or 100%

definitions:	
	domain BrakePressure = {0 : 100}
	domain TimeImpactBrake = {0 : 100}

			
	//SCS-28
	macro rule r_activateEmergencyBrake = 
		if (timeImpact != 0) then
			par
				acousticSignalImpact := true
				if (timeImpact = 1) then
					brakePressure := 20
				endif
				if (timeImpact = 2) then
					brakePressure := 60
				endif
				if (timeImpact = 3) then
					brakePressure := 100
				endif
			endpar
		else 
			brakePressure :=0
		endif
	
	//SCS-27
	macro rule r_EmergencyBrakeSpeed = 
		if ((currentSpeed > 0 and currentSpeed <= 600 and stationaryObstacle) or (currentSpeed > 0 and currentSpeed <= 1200 and movingObstacle)) then
			r_activateEmergencyBrake[] 	
		else
			if (acousticSignalImpact) then
				acousticSignalImpact := false
			endif
		endif
				
