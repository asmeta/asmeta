//Fifth refinement of Adaptive Exterior Light and Speed Control System
//Setting and modifying desired speed - Cruise control
//from SCS-1 to SCS-17

module CarSystem005Functions
import ../../StandardLibrary
import CarSystem005Domains
//import ../CarSystem004/CarSystem004Functions
import ../CarSystem000/CarSystem000CommonFunctions
export *

signature:
	// FUNCTIONS

	//monitored cruiseControlMode: CruiseControlMode // State of cruise control
	monitored sCSLever: SCSLever // Position of the Cruise control lever
	controlled sCSLeve_Previous: SCSLever // Position of the Cruise control lever in previous state 
	//-- sCSLeve_Previous (senza r perchè quando traduce in model checker la vede come regola e dice che non la trova)
	//monitored currentSpeed: CurrentSpeed // Current speed of the vehicle
	//monitored keyState: KeyPosition // Position of the key
	//controlled keyState_Previous: KeyPosition // Position of the key in the previous state
	//controlled setVehicleSpeed: CurrentSpeed // Cruise control speed
	monitored brakePedal: BrakePedal //It is discritezed 0 - 0°, 225 - 45° Resolution 0.2°
			
	//static engineOn: KeyPosition -> Boolean // Depending on keyState engineOn is true or false
	
	
definitions:	
	// FUNCTION DEFINITIONS

	//Engine state is determined by KeyPosition value. If the key is on the engine is on, otherwise is off
	//function engineOn ($key in KeyPosition)=
		//($key = KEYINIGNITIONONPOSITION)
