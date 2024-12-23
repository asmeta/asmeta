asm Pillbox_1

import STDL/StandardLibrary
import STDL/CTLLibrary
import STDL/LTLLibrary

// Second refinement level of the Pill Box: We consider only one kind of pill, and time controlled By the pillbox 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain LedLights = {OFF | ON | BLINKING}
	
	enum domain OutMessages = {
		TAKE_PILL | NONE | PILL_MISSED | DRAWER_NOT_CLOSED |
		CLOSE_DRAWER_IN_10_MIN | TAKE_PILL_IN_10_MIN 
	}
	
	domain Time subsetof Integer
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored openSwitch: Boolean
	dynamic controlled opened: Boolean
	dynamic controlled redLed: LedLights
	dynamic controlled outMess: OutMessages
	dynamic controlled logMess: OutMessages
	dynamic controlled time_consumption: Time
	dynamic controlled requestSatisfied: Boolean	

	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic controlled systemTime: Time
	dynamic controlled drawerTimer: Time
	
	static tenMinutes: Time
	
definitions:
	//*************************************************
	// DOMAIN DEFINITIONS
	//*************************************************
	domain Time = {0 : 23}
	
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	function tenMinutes = 1
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule that implement the writing on the log file
	rule r_writeToFile = skip
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken =
		par
			if redLed != ON then drawerTimer := systemTime endif
			redLed := ON 
			outMess := TAKE_PILL			
		endpar	
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_drawerOpened =
		par
			if redLed != BLINKING and outMess != CLOSE_DRAWER_IN_10_MIN then drawerTimer := systemTime endif
			redLed := BLINKING
			outMess := CLOSE_DRAWER_IN_10_MIN			
		endpar
		
	// Rule to take the system back to the initial state when the drawer is open and redLed is ON
	rule r_pillTaken_drawerOpened =
		par
			redLed := OFF
			outMess := NONE
			drawerTimer := systemTime
			requestSatisfied := true
		endpar
		
	// Rule to handle the closing of a drawer when the RED Led is blinking
	rule r_drawerClosed =
		par
			redLed := OFF
			outMess := NONE
			drawerTimer := systemTime
			requestSatisfied := true
		endpar
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill =
		par
			redLed := OFF
			outMess := NONE
			logMess := PILL_MISSED
			drawerTimer := systemTime
			requestSatisfied := true
			r_writeToFile[] 
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the drawer has not been closed
	rule r_timeOutExpired_drawerOpened =
		par
			redLed := OFF
			outMess := NONE
			logMess := DRAWER_NOT_CLOSED 
			drawerTimer := systemTime
			requestSatisfied := true
			r_writeToFile[]
		endpar	
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_takeInTimeout =
		par
			if redLed != BLINKING and outMess != TAKE_PILL_IN_10_MIN then drawerTimer := systemTime endif
			redLed := BLINKING
			outMess := TAKE_PILL_IN_10_MIN			
		endpar
	
	//*************************************************
	// Property Verification
	//*************************************************
	// If the pill has to be taken, red led will lights up
	CTLSPEC ag((time_consumption<systemTime and not requestSatisfied and redLed = OFF) implies ax(redLed = ON))
	// If the patient does not take the pill or the drawer has to be closed, the red light will blink
	CTLSPEC ag(((redLed = ON and not opened and openSwitch) or (redLed = ON and systemTime-drawerTimer=tenMinutes and not(opened))) implies ax(redLed = BLINKING))
	// The red light will change value after 10 minutes if the patient doesn't take the pill
	CTLSPEC ag((redLed = ON and systemTime-drawerTimer=tenMinutes and not(opened)) implies ax(redLed = BLINKING))
	CTLSPEC ag((redLed = BLINKING and systemTime-drawerTimer>tenMinutes and not(openSwitch)) implies ax(redLed = OFF))
	// If the patient takes the pill, red light will turn-off
	CTLSPEC ag((time_consumption<systemTime and not requestSatisfied and opened and not(openSwitch) and not(redLed = OFF) and not(systemTime-drawerTimer>=tenMinutes)) implies ax(redLed = OFF))
		
	//*************************************************
	// MAIN Rule
	//*************************************************
	// MAIN Rule
	main rule r_Main =
		if (not(requestSatisfied) and systemTime < 24) then
			par
				// Set the SystemTime
				systemTime := (systemTime + 1) mod 24
				
				// Set the satus of the drawer
				if not opened and openSwitch  then opened := true endif
				if opened and not openSwitch  then opened := false endif
				
				// Starting from the IDLE state, the pill has to be taken 
				if redLed = OFF then if (time_consumption<=systemTime and not requestSatisfied) then r_pillToBeTaken[] endif endif
				// It is open, drug to be taken, it becomes closed
				if redLed = ON and not(systemTime-drawerTimer>=tenMinutes) and opened and not openSwitch  then r_pillTaken_drawerOpened[] endif
				// It is closed drug to be taken and it becomes open     
				if redLed = ON and not opened and openSwitch  then r_drawerOpened[] endif
				// It is closed drug to be taken and timeout     
				if redLed = ON then 
					if systemTime-drawerTimer>=tenMinutes then 
						if opened then r_drawerOpened[] else if not openSwitch then r_takeInTimeout[] endif endif 
					endif 
				endif
				// It is blinking, and it becomes closed (or remains closed) or timeout   
				if redLed = BLINKING then 
					if not openSwitch and opened then r_drawerClosed[] 
					else 
						if systemTime-drawerTimer>tenMinutes then
							if openSwitch then r_timeOutExpired_drawerOpened[] else r_timeOutExpired_missedPill[] endif
						else 
							if openSwitch then r_drawerOpened[] endif
						endif 
					endif 
				endif
			endpar
		endif
			
default init s0:	
	// Controlled function that indicates the status of the drawer
	function  opened = false		
		
	// Reset the output display message and the log message
	function outMess = NONE
	function logMess = NONE
	
	// Turn-off all the led of the Drawers
	function redLed = OFF
	
	// Each drawer's timer starts from 0
	function drawerTimer = 0

	// Initialization of the time consumption
	function time_consumption = 1

	// Initialization of the SystemTime
	function systemTime = 0
	
	// At the initialization phase, every drug has to be assumed
	function requestSatisfied = false
