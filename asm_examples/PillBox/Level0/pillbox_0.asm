asm pillbox_0

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

/* First refinement level of the Pill Box: We consider only one kind of pill, no time (only some boolean functions that signal
   when the drugs have to be taken and the status of the red led) */

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain LedLights = {OFF | ON | BLINKING}
	
	enum domain OutMessages = {
		TAKE_PILL | NONE | PILL_MISSED | DRAWER_NOT_CLOSED |
		CLOSE_DRAWER_IN_10_MIN | TAKE_PILL_IN_10_MIN 
	}
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored openSwitch: Boolean
	dynamic controlled opened: Boolean
	dynamic controlled redLedSwitch: LedLights
	dynamic controlled outMess: OutMessages
	dynamic controlled logMess: OutMessages

	// Functions that substitute the time management
	dynamic monitored takeThePill: Boolean
	dynamic monitored timeDiffOver600: Boolean
	
definitions:
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule that implement the writing on the log file
	rule r_writeToFile = skip
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken =
		par
			redLedSwitch := ON
			outMess := TAKE_PILL
		endpar
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_drawerOpened =
		par
			redLedSwitch := BLINKING
			outMess := CLOSE_DRAWER_IN_10_MIN
		endpar
		
	// Rule to take the system back to the initial state when the drawer is open and redLed is ON
	rule r_pillTaken_drawerOpened =
		par
			redLedSwitch := OFF
			outMess := NONE
		endpar
		
	// Rule to handle the closing of a drawer when the RED Led is blinking
	rule r_drawerClosed =
		par
			redLedSwitch := OFF
			outMess := NONE
		endpar
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill =
		par
			redLedSwitch := OFF
			outMess := NONE
			logMess := PILL_MISSED
			r_writeToFile[] 
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the drawer has not been closed
	rule r_timeOutExpired_drawerOpened =
		par
			redLedSwitch := OFF
			outMess := NONE
			logMess := DRAWER_NOT_CLOSED 
			r_writeToFile[]
		endpar	
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_takeInTimeout =
		par
			redLedSwitch := BLINKING
			outMess := TAKE_PILL_IN_10_MIN
		endpar
	
	//*************************************************
	// Property Verification
	//*************************************************
	// If the pill has to be taken, red led will lights up
	CTLSPEC ag((takeThePill and redLedSwitch = OFF) implies ax(redLedSwitch = ON))
	// If the patient does not take the pill or the drawer has to be closed, the red light will blink
	CTLSPEC ag(((redLedSwitch = ON and not opened and openSwitch) or (redLedSwitch = ON and timeDiffOver600 and not(opened))) implies ax(redLedSwitch = BLINKING))
	// The red light will change value after 10 minutes if the patient doesn't take the pill
	CTLSPEC ag((redLedSwitch = ON and timeDiffOver600 and not(opened)) implies ax(redLedSwitch = BLINKING))
	CTLSPEC ag((redLedSwitch = BLINKING and timeDiffOver600 and not(openSwitch)) implies ax(redLedSwitch = OFF))
	// If the patient takes the pill, red light will turn-off
	CTLSPEC ag((takeThePill and opened and not(openSwitch) and not(redLedSwitch = OFF) and not timeDiffOver600) implies ax(redLedSwitch = OFF))
		
	//*************************************************
	// MAIN Rule
	//*************************************************
	// MAIN Rule
	main rule r_Main =
		par
			// Set the satus of the drawer
			if not opened and openSwitch  then opened := true endif
			if opened and not openSwitch  then opened := false endif
			
			// Starting from the IDLE state, the pill has to be taken 
			if redLedSwitch = OFF then if takeThePill then r_pillToBeTaken[] endif endif
			// It is open, drug to be taken, it becomes closed
			if redLedSwitch = ON and not timeDiffOver600 and opened and not openSwitch  then r_pillTaken_drawerOpened[] endif
			// It is closed drug to be taken and it becomes open     
			if redLedSwitch = ON and not opened and openSwitch  then r_drawerOpened[] endif
			// It is closed drug to be taken and timeout     
			if redLedSwitch = ON then
				if timeDiffOver600 then 
					if opened then r_drawerOpened[] else if not openSwitch then r_takeInTimeout[] endif endif 
				endif 
			endif
			// It is blinking, and it becomes closed (or remains closed) or timeout   
			if redLedSwitch = BLINKING then
				if not openSwitch and opened then r_drawerClosed[] 
				else 
					if timeDiffOver600 then
						if openSwitch then r_timeOutExpired_drawerOpened[] else r_timeOutExpired_missedPill[] endif
					else 
						if openSwitch then r_drawerOpened[] endif
					endif 
				endif 
			endif
		endpar
			
default init s0:	
	// Controlled function that indicates the status of the drawer
	function  opened = false		
		
	// Reset the output display message and the log message
	function outMess = NONE
	function logMess = NONE
	
	// Turn-off all the led of the Drawers
	function redLedSwitch = OFF
	
	// Initialization of the functions that substitute the time management
	function takeThePill = false
	function timeDiffOver600 = false
