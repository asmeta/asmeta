//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem003Domains
import ../../StandardLibrary
import ../CarSystem002/CarSystem002Domains
export *

signature:

	// DOMAINS

	domain HighBeamRange subsetof Integer // High beam luminous strenght
		
	// FUNCTIONS
	
definitions:
	// DOMAIN DEFINITIONS

	domain HighBeamRange = {0 :100} //Percentage of high beam brightness (0 :300 desired light range)
	
	