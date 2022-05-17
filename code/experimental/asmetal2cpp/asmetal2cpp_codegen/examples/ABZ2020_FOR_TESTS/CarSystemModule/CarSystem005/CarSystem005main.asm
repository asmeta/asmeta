//Fifth refinement of Adaptive Exterior Light and Speed Control System
//Setting and modifying desired speed - Cruise control
//from SCS-1 to SCS-17

asm CarSystem005main
import ../../StandardLibrary
import CarSystem005DesiredSpeedCruiseC

signature:

	
	
definitions:	

	// FUNCTION DEFINITIONS

	
	// RULE DEFINITIONS
	
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
		endpar 

// INITIAL STATE
default init s0:
	function cruiseControlMode = CCM1
	//Cruise control lever is in NEUTRAL position
	function sCSLeve_Previous = NEUTRAL
	//Key is not inserted
	function keyState_Previous = NOKEYINSERTED
	function setVehicleSpeed = 0
	function desiredSpeed = 0
	function passed2SecYes = false