asm pillbox_2_elia

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

// Third refinement level of the Pill Box: We consider three drawer, and time controlled By the pillbox 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************	
	enum domain LedLights = {OFF | ON | BLINKING}
	
	enum domain OutMessages = {
		TAKE_ASPIRINE | TAKE_TYLENOL | TAKE_MOMENT | 
		NONE | 
		ASPIRINE_MISSED | TYLENOL_MISSED | MOMENT_MISSED | 
		ASPIRINE_DRAWER_NOT_CLOSED | TYLENOL_DRAWER_NOT_CLOSED | MOMENT_DRAWER_NOT_CLOSED |
		CLOSE_ASPIRINE_DRAWER_IN_10_MIN | CLOSE_TYLENOL_DRAWER_IN_10_MIN | CLOSE_MOMENT_DRAWER_IN_10_MIN
	}
	
	enum domain Drugs = {
		ASPIRINE | TYLENOL | MOMENT
	}
		
	domain Time subsetof Integer
	domain Drawer subsetof Integer
	
	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored openSwitch: Drawer -> Boolean
	dynamic controlled opened: Drawer -> Boolean
	dynamic controlled redLed: Drawer -> LedLights
	dynamic controlled outMess: Drawer -> OutMessages
	dynamic controlled logMess: Drawer -> OutMessages
	dynamic controlled time_consumption: Drawer -> Time
	dynamic controlled requestSatisfied: Drawer -> Boolean	
	dynamic controlled name: Drawer -> Drugs

	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic controlled systemTime: Time
	dynamic controlled drawerTimer: Drawer -> Time
	
	static tenMinutes: Time
	
	//*************************************************
	// STATIC VARIABLES
	//*************************************************
	static drawer1: Drawer
	static drawer2: Drawer
	static drawer3: Drawer
	
definitions:
	//*************************************************
	// DOMAIN DEFINITIONS
	//*************************************************
	domain Time = {0 : 60}
	domain Drawer = {0 : 2}
	
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	function tenMinutes = 10
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule that implement the writing on the log file
	rule r_writeToFile($drawer in Drawer) = skip
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken($drawer in Drawer) =
		par
			if redLed($drawer) != ON then drawerTimer($drawer) := systemTime endif
			redLed($drawer) := ON
			if ($drawer=drawer1) then
				outMess($drawer) := TAKE_TYLENOL
			else 
				if ($drawer=drawer2) then
					outMess($drawer) := TAKE_ASPIRINE
				else
					outMess($drawer) := TAKE_MOMENT
				endif
			endif			
		endpar	
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_drawerOpened($drawer in Drawer) =
		par
			if redLed($drawer) != BLINKING and outMess($drawer) != CLOSE_ASPIRINE_DRAWER_IN_10_MIN and outMess($drawer) != CLOSE_TYLENOL_DRAWER_IN_10_MIN and
				outMess($drawer) != CLOSE_MOMENT_DRAWER_IN_10_MIN then drawerTimer($drawer) := systemTime endif
			redLed($drawer) := BLINKING
			if ($drawer=drawer1) then
				outMess($drawer) := CLOSE_TYLENOL_DRAWER_IN_10_MIN
			else 
				if ($drawer=drawer2) then
					outMess($drawer) := CLOSE_ASPIRINE_DRAWER_IN_10_MIN
				else
					outMess($drawer) := CLOSE_MOMENT_DRAWER_IN_10_MIN
				endif
			endif				
		endpar
				
	// Rule to handle the closing of a drawer when the RED Led is blinking
	rule r_drawerClosed($drawer in Drawer) =
		par
			redLed($drawer) := OFF
			outMess($drawer) := NONE
			drawerTimer($drawer) := systemTime
			requestSatisfied($drawer) := true
		endpar
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill($drawer in Drawer) =
		par
			redLed($drawer) := OFF
			outMess($drawer) := NONE
			if ($drawer=drawer1) then
				logMess($drawer) := TYLENOL_MISSED
			else 
				if ($drawer=drawer2) then
					logMess($drawer) := ASPIRINE_MISSED
				else
					logMess($drawer) := MOMENT_MISSED
				endif
			endif	
			drawerTimer($drawer) := systemTime
			requestSatisfied($drawer) := true
			r_writeToFile[$drawer] 
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the drawer has not been closed
	rule r_timeOutExpired_drawerOpened($drawer in Drawer) =
		par
			redLed($drawer) := OFF
			outMess($drawer) := NONE
			if ($drawer=drawer1) then
				logMess($drawer) := TYLENOL_DRAWER_NOT_CLOSED 
			else 
				if ($drawer=drawer2) then
					logMess($drawer) := ASPIRINE_DRAWER_NOT_CLOSED
				else
					logMess($drawer) := MOMENT_DRAWER_NOT_CLOSED
				endif
			endif	
			drawerTimer($drawer) := systemTime
			requestSatisfied($drawer) := true
			r_writeToFile[$drawer]
		endpar	

	//*************************************************
	// Property Verification
	//*************************************************
	// If the pill has to be taken, red led will lights up
	CTLSPEC ag((time_consumption(drawer1)<systemTime and not requestSatisfied(drawer1) and redLed(drawer1) = OFF) implies ax(redLed(drawer1) = ON))
	CTLSPEC ag((time_consumption(drawer2)<systemTime and not requestSatisfied(drawer2) and redLed(drawer2) = OFF) implies ax(redLed(drawer2) = ON))
	CTLSPEC ag((time_consumption(drawer3)<systemTime and not requestSatisfied(drawer3) and redLed(drawer3) = OFF) implies ax(redLed(drawer3) = ON))
	// If the patient does not take the pill or the drawer has to be closed, the red light will blink
	CTLSPEC ag(((redLed(drawer1) = ON and not opened(drawer1) and openSwitch(drawer1)) or (redLed(drawer1) = ON and systemTime-drawerTimer(drawer1)=tenMinutes and not(opened(drawer1)))) implies ax(redLed(drawer1) = BLINKING))
	CTLSPEC ag(((redLed(drawer2) = ON and not opened(drawer2) and openSwitch(drawer2)) or (redLed(drawer2) = ON and systemTime-drawerTimer(drawer2)=tenMinutes and not(opened(drawer2)))) implies ax(redLed(drawer2) = BLINKING))
	CTLSPEC ag(((redLed(drawer3) = ON and not opened(drawer3) and openSwitch(drawer3)) or (redLed(drawer3) = ON and systemTime-drawerTimer(drawer3)=tenMinutes and not(opened(drawer3)))) implies ax(redLed(drawer3) = BLINKING))
	// The red light will change value after 10 minutes if the patient doesn't take the pill
	CTLSPEC ag((redLed(drawer1) = ON and systemTime-drawerTimer(drawer1)=tenMinutes and not(opened(drawer1))) implies ax(redLed(drawer1) = BLINKING))
	CTLSPEC ag((redLed(drawer2) = ON and systemTime-drawerTimer(drawer2)=tenMinutes and not(opened(drawer2))) implies ax(redLed(drawer2) = BLINKING))
	CTLSPEC ag((redLed(drawer3) = ON and systemTime-drawerTimer(drawer3)=tenMinutes and not(opened(drawer3))) implies ax(redLed(drawer3) = BLINKING))
	CTLSPEC ag((redLed(drawer1) = BLINKING and systemTime-drawerTimer(drawer1)>tenMinutes and not(openSwitch(drawer1))) implies ax(redLed(drawer1) = OFF))
	CTLSPEC ag((redLed(drawer2) = BLINKING and systemTime-drawerTimer(drawer2)>tenMinutes and not(openSwitch(drawer2))) implies ax(redLed(drawer2) = OFF))
	CTLSPEC ag((redLed(drawer3) = BLINKING and systemTime-drawerTimer(drawer3)>tenMinutes and not(openSwitch(drawer3))) implies ax(redLed(drawer3) = OFF))
	// If the patient takes the pill, red light will turn-off
	CTLSPEC ag((time_consumption(drawer1)<systemTime and not requestSatisfied(drawer1) and opened(drawer1) and not(openSwitch(drawer1)) and not(redLed(drawer1) = OFF) and not(systemTime-drawerTimer(drawer1)>=tenMinutes)) implies ax(redLed(drawer1) = OFF))
	CTLSPEC ag((time_consumption(drawer2)<systemTime and not requestSatisfied(drawer2) and opened(drawer2) and not(openSwitch(drawer2)) and not(redLed(drawer2) = OFF) and not(systemTime-drawerTimer(drawer2)>=tenMinutes)) implies ax(redLed(drawer2) = OFF))
	CTLSPEC ag((time_consumption(drawer3)<systemTime and not requestSatisfied(drawer3) and opened(drawer3) and not(openSwitch(drawer3)) and not(redLed(drawer3) = OFF) and not(systemTime-drawerTimer(drawer3)>=tenMinutes)) implies ax(redLed(drawer3) = OFF))
		
	//*************************************************
	// MAIN Rule
	//*************************************************
	// MAIN Rule
	main rule r_Main =
		par
			// Set the SystemTime
			systemTime := (systemTime + 1) mod 60
			
			forall $drawer in Drawer do
				if (not(requestSatisfied($drawer)) and systemTime < 60) then
					par					
						// Set the satus of the drawer
						if not opened($drawer) and openSwitch($drawer) then opened($drawer) := true endif
						if opened($drawer) and not openSwitch($drawer) then opened($drawer) := false endif
						            
						// time to take pill 
						if redLed($drawer) = OFF 
						then
							if time_consumption($drawer)<=systemTime and systemTime-time_consumption($drawer)<=tenMinutes
							then
								r_pillToBeTaken[$drawer]
							endif
						endif
            
						// drawer opened within 10 minutes
						if redLed($drawer) = ON and opened($drawer) and systemTime-drawerTimer($drawer)<=tenMinutes
						then
							r_drawerOpened[$drawer]
						endif
						
						// waited too long - missed pill
						if redLed($drawer) = ON and systemTime-time_consumption($drawer)>tenMinutes
						then
							r_timeOutExpired_missedPill[$drawer]
						endif
						
						// forgot to close the drawer
						if redLed($drawer) = BLINKING and systemTime-drawerTimer($drawer)>tenMinutes
						then
							r_timeOutExpired_drawerOpened[$drawer]
						endif						
						
						// closed drawer within 10 minutes
						if redLed($drawer) = BLINKING and systemTime-drawerTimer($drawer)<=tenMinutes and not(opened($drawer))
						then
							r_drawerClosed[$drawer]
						endif
					endpar
				endif
		endpar
			
default init s0:	
	// Controlled function that indicates the status of the drawer
	function opened($drawer in Drawer) = false		
		
	// Reset the output display message and the log message
	function outMess($drawer in Drawer) = NONE
	function logMess($drawer in Drawer) = NONE
	
	// Turn-off all the led of the Drawers
	function redLed($drawer in Drawer) = OFF
	
	// Each drawer's timer starts from 0
	function drawerTimer($drawer in Drawer) = 0

	// Initialization of the time consumption
	function time_consumption($drawer in Drawer) =
		switch($drawer)
			case drawer1 : 10
			case drawer2 : 50
			case drawer3 : 30
		endswitch

	// Initialization of the SystemTime
	function systemTime = 0
	
	// At the initialization phase, every drug has to be assumed
	function requestSatisfied($drawer in Drawer) = false
	
	// Insert a drug in each drawer	
	function name($drawer in Drawer) =
		switch($drawer)
			case drawer1 : TYLENOL
			case drawer2 : ASPIRINE
			case drawer3 : MOMENT
		endswitch
