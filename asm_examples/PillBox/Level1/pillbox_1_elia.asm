asm pillbox_1_elia

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

// Second refinement level of the Pill Box: We consider only one kind of pill, and time controlled By the pillbox 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain LedLights = {OFF | ON | BLINKING}
	
	enum domain OutMessages = {
		TAKE_PILL | NONE | PILL_MISSED | DRAWER_NOT_CLOSED |
		CLOSE_DRAWER_IN_10_MIN 
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
	dynamic controlled requestSatisfied: Boolean	

	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic controlled systemTime: Time
	dynamic controlled drawerTimer: Time
	static time_consumption: Time
	
	static tenMinutes: Time
	
definitions:
	//*************************************************
	// DOMAIN DEFINITIONS
	//*************************************************
	domain Time = {0 : 25}
	
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	function tenMinutes = 10
	function time_consumption = 10
	
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
		
	// Rule to handle the closing of a drawer when the RED Led is blinking
	rule r_drawerClosed =
		par
			redLed := OFF
			outMess := NONE
			drawerTimer := systemTime
			requestSatisfied := true
		endpar
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and pill was not taken
	rule r_timeOutExpired_missedPill =
		par
			redLed := OFF
			outMess := NONE
			logMess := PILL_MISSED
			drawerTimer := systemTime
			r_writeToFile[] 
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the drawer has not been closed
	rule r_timeOutExpired_drawerOpened =
		par
			redLed := OFF
			outMess := NONE
			logMess := DRAWER_NOT_CLOSED 
			drawerTimer := systemTime
			r_writeToFile[]
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
		if (not(requestSatisfied) and systemTime < 25) then
			par
				// Set the SystemTime
				systemTime := (systemTime + 1) mod 25
				
				// Set the satus of the drawer
				if not opened and openSwitch  then opened := true endif
				if opened and not openSwitch  then opened := false endif
				
				// time to take the pill
				if redLed = OFF 
					and time_consumption<=systemTime
					and systemTime-time_consumption<=tenMinutes
				then
					r_pillToBeTaken[]
				endif

				// waited too long - missed pill
				if redLed = ON
					and systemTime-drawerTimer>=tenMinutes
				then
					r_timeOutExpired_missedPill[]
				endif

				// drawer opened within 10 minutes
				if redLed = ON 
					and time_consumption<=systemTime 
					and opened and not(systemTime-drawerTimer>tenMinutes)
				then
					r_drawerOpened[]
				endif

				// forgot to close the drawer
				if redLed = BLINKING and systemTime-drawerTimer>tenMinutes
				then
					r_timeOutExpired_drawerOpened[]
				endif

				// closed drawer within 10 minutes
				if redLed = BLINKING and systemTime-drawerTimer<=tenMinutes and not(opened)
				then
					r_drawerClosed[]
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

	// Initialization of the SystemTime
	function systemTime = 0
	
	// At the initialization phase, every drug has to be assumed
	function requestSatisfied = false