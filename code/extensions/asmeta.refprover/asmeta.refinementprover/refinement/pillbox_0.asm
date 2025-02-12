asm pillbox_0

import StandardLibrary

/* First refinement level of the Pill Box: We consider only one kind of pill, no time (only some boolean functions that signal
   when the drugs have to be taken and the status of the red led) */

signature:
	//*************************************************
	// DOMAINS
	//*************************************************

	enum domain LedLights = {OFF | ON | BLINKING}
	
	dynamic controlled redLed: LedLights
	// Functions that substitute the time management
	dynamic monitored takeThePill: Boolean
	
definitions:
	
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
	
	// Initialization of the functions that substitute the time management
	function takeThePill = false
