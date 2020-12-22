//Sixth refinement of Adaptive Exterior Light and Speed Control System
//Speed Limit and Traffic Sign Detection
//from SCS-29 to SCS-39

module CarSystem006SpeedLimitTrafficDet
import ../../StandardLibrary
import CarSystem006DesiredSpeedCruiseC
export*

signature:
	// DOMAINS
	enum domain SpeedLimitSignDetection = {UNLIMITED | SIGNDETECTED | NOSIGNDETECTED}
	

	domain GasPedalPerc subsetof Integer // Deflection of the gas pedal in percentage
	// FUNCTIONS
	
	
	controlled orangeLed: Boolean //The orange led is on (true) or off (false)
	monitored gasPedal: GasPedalPerc //Gas pedal value in percentage
	controlled speedLimitSpeed: CurrentSpeed //Limit speed
	controlled speedLimitTempDeacti: Boolean //speedLimiter is temporarily deactivated
	monitored detectedTrafficSign: SpeedLimitSignDetection //Type of signed recognized
	monitored speedLimitDetected: CurrentSpeed //Speed detected when traffic sign detection is active
	//monitored setSpeedLimitManually: Boolean //Set speed limit manually or automaticcly using traffic sign detection
	monitored trafficSignDetectionOn: Boolean // Traffic sign detector is on
	
//====================================	
	
definitions:	
	domain GasPedalPerc = {0 : 100}
	
	// FUNCTION DEFINITIONS

	// RULE DEFINITIONS
	
	macro rule r_SpeedLimit = 
	//from SCS-29 to SCS-35
		if (speedLimitActive = false) then
			if (sCSLever = HEAD) then
				par
					speedLimitActive := true
					orangeLed := true
				endpar
			endif
		else
			//if ((cruiseControlMode = CCM2 and trafficSignDetectionOn and setSpeedLimitManually) or (cruiseControlMode = CCM1) or (cruiseControlMode = CCM2 and not trafficSignDetectionOn)) then
			//	r_SetModifySpeed[] 
			//endif
			//SCS-35
			if (sCSLever = HEAD or sCSLever = BACKWARD_SCS) then
				par
					speedLimitActive := false
					speedLimitTempDeacti := false
					orangeLed := false
				endpar
			else
				par
					//SCS-33
					if (gasPedal > 90 and not speedLimitTempDeacti) then
						speedLimitTempDeacti := true
					endif
					//SCS-34
					if (gasPedal < 90 and speedLimitTempDeacti) then
						speedLimitTempDeacti := false
					endif
				endpar
			endif
		endif
	
	macro rule r_TrafficSignDetection = 
		//SCS-36
		//	if (cruiseControlMode = CCM2 and trafficSignDetectionOn and not setSpeedLimitManually) then
		//If traffic sign detection is active, but the user changes the speed manually (SCSLever moved) the speed is modificed by the SCSLever
		//if (cruiseControlMode = CCM2 and trafficSignDetectionOn and (not manualSpeed) and brakePedal!=0) then
		if (cruiseControlMode = CCM2 and trafficSignDetectionOn and (not manualSpeed) and brakePedal=0) then
			//SCS-37
			if (gasPedal = 0) then
				par
				//SCS-39
					if (detectedTrafficSign = UNLIMITED) then
						if (setVehicleSpeed < 1200) then
							setVehicleSpeed := 1200
						endif
					endif
				//SCS-38
					if (detectedTrafficSign = SIGNDETECTED) then
						setVehicleSpeed := speedLimitDetected
					endif
					if (detectedTrafficSign = NOSIGNDETECTED) then
						skip
					endif
				endpar
			endif
		endif
	