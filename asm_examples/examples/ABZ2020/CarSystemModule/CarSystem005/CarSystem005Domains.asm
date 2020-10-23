//Fifth refinement of Adaptive Exterior Light and Speed Control System
//Setting and modifying desired speed - Cruise control
//from SCS-1 to SCS-17

module CarSystem005Domains
import ../../StandardLibrary
//import ../CarSystem004/CarSystem004Domains
import ../CarSystem000/CarSystem000CommonDomains
export *

signature:
	// DOMAINS
	enum domain SCSLever = {DOWNWARD5_SCS | UPWARD5_SCS | DOWNWARD7_SCS | UPWARD7_SCS | NEUTRAL | FORWARD_SCS | BACKWARD_SCS | HEAD} // Cruise control lever positions
	//enum domain KeyPosition = {NOKEYINSERTED | KEYINSERTED | KEYINIGNITIONONPOSITION} // Key state
	//enum domain CruiseControlMode = {CCM0 | CCM1 | CCM2}
	//CCM0: cruise control disabled
	//CCM1: cruise control active
	//CCM2: adaptive cruise control active
	//domain CurrentSpeed subsetof Integer // Speed
	domain BrakePedal subsetof Integer // Deflection of the brake pedal
	
	
definitions:	
	//domain CurrentSpeed = {0 : 2000} //5000 Si può ridurre a 2000? che corrisponde a velocità massima di 200km/h??
	//1km/h = 10 unità
	domain BrakePedal = {0 : 225} //res 0.2° 0° - 45° -> 1° = 5 unità
	
