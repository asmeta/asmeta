//Sixth refinement of Adaptive Exterior Light and Speed Control System
//Setting and modifying desired speed - 
//Cruise control - Speed limit - Traffic sign detection
//from SCS-1 to SCS-17
//from SCS-29 to SCS-35
//from SCS-36 to SCS-39

module CarSystem006Functions
import ../../StandardLibrary
import ../CarSystem005/CarSystem005Functions
export*

signature:
	
	controlled speedLimitActive: Boolean //speedLimiter is running
	controlled desiredSpeed: CurrentSpeed // Desired speed 
	
	
//====================================	
	
definitions:	
	