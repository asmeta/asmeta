//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem002Domains
import ../../StandardLibrary
import ../CarSystem001/CarSystem001Domains
export *

signature:

	// DOMAINS

	enum domain LightSwitch = {OFF | AUTO | ON} // Light rotary switch positions

	//domain CurrentSpeed subsetof Integer // Speed
	
	// FUNCTIONS
	
definitions:
	// DOMAIN DEFINITIONS

	//domain CurrentSpeed = {0 : 2000} //5000 Si pu� ridurre a 2000? che corrisponde a velocit� massima di 200km/h??
	//1km/h = 10 unit�
	
