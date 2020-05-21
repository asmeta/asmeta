asm pillbox_1_for_RefProv

import StandardLibrary

// Second refinement level of the Pill Box: We consider only one kind of pill, and time controlled By the pillbox 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain LedLights = {OFF | ON | BLINKING}
	
	domain Time subsetof Integer
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic controlled redLed: LedLights
	dynamic controlled time_consumption: Time

	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic controlled systemTime: Time
	
	// Derived function for refinement prove
	derived takeThePill: Boolean
	
definitions:
	//*************************************************
	// DOMAIN DEFINITIONS
	//*************************************************
	domain Time = {0:23}
	
	//*************************************************
	// DERIVED FUNCTIONS
	//*************************************************
	function takeThePill = (time_consumption<=systemTime)
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken =	redLed := ON
		
	//*************************************************
	// MAIN Rule
	//*************************************************
	// MAIN Rule
	main rule r_Main =	if takeThePill then r_pillToBeTaken[] endif
			
default init s0:	
	
	// Turn-off all the led of the Drawers
	function redLed = OFF
	
	// Initialization of the time consumption
	function time_consumption = 1

	// Initialization of the SystemTime
	function systemTime = 0
