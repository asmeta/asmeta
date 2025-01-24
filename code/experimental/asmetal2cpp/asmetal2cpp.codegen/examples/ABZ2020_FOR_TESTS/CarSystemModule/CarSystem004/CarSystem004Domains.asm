//Ground model of Adaptive Exterior Light and Speed Control System
//Direction Blinking
//Hazard warning light
//from ELS-1 to ELS-13

module CarSystem004Domains
import ../../StandardLibrary
import ../CarSystem003/CarSystem003Domains
export *

signature:

	// DOMAINS

	domain Voltage subsetof Integer //Voltage: we approximate to integer values
	//enum domain CruiseControlMode = {CCM0 | CCM1 | CCM2}
	// FUNCTIONS
	
definitions:
	// DOMAIN DEFINITIONS

	domain Voltage = {0 : 500}
	
