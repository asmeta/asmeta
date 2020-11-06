//Sixth refinement of Adaptive Exterior Light and Speed Control System
//Setting and modifying desired speed - 
//Cruise control - Speed limit - Traffic sign detection
//from SCS-1 to SCS-17
//from SCS-29 to SCS-35
//from SCS-36 to SCS-39

asm CarSystem006main
import ../../StandardLibrary
import CarSystem006SpeedLimitTrafficDet

signature:
	
definitions:	
		
	macro rule r_ReadMonitorFunctions = 
		par
			sCSLeve_Previous := sCSLever
			keyState_Previous := keyState
		endpar 
		
			
	// INVARIANTS
	
	
	//PROPERTIES
	
	
	// MAIN RULE
	main rule r_Main =
		par
			r_ReadMonitorFunctions[] 
			r_DesiredSpeedVehicleSpeed[] 
			r_BrakePedal[] 
			r_SpeedLimit[] 
		endpar 

// INITIAL STATE
default init s0:
	function cruiseControlMode = CCM0
	//Cruise control lever is in NEUTRAL position
	function sCSLeve_Previous = NEUTRAL
	//Key is not inserted
	function keyState_Previous = NOKEYINSERTED
	function setVehicleSpeed = 0
	function desiredSpeed = 0
	function passed2SecYes = false
	function orangeLed = false
	function speedLimitActive = false
	function speedLimitTempDeacti = false
	function speedLimitSpeed = 0