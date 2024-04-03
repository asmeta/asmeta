asm pillbox_FULL_elia

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

// Third refinement level of the Pill Box: We consider three drawer, and time controlled By the pillbox 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************	
	domain Drawer subsetof Integer
	
	enum domain LedLights = {OFF | ON | BLINKING}

	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored openSwitch: Drawer -> Boolean
	dynamic controlled opened: Drawer -> Boolean
	dynamic controlled redLed: Drawer -> LedLights
	dynamic controlled outMess: Drawer -> String
	dynamic controlled logMess: Drawer -> String
	dynamic controlled time_consumption: Drawer -> Seq(Natural)
	dynamic monitored drugName: Drawer -> String
	dynamic controlled drugIndex: Drawer -> Natural

	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic monitored systemTime: Natural
	dynamic controlled drawerTimer: Drawer -> Natural
	static tenMinutes: Integer
	
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
	domain Drawer = {0 : 2}
	
	//*************************************************
	// FUNCTIONS DEFINITIONS
	//*************************************************
	function tenMinutes = 600

	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule that implement the writing on the log file
	rule r_writeToFile($drawer in Drawer) = skip
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken($drawer in Drawer) =
		par
			redLed($drawer) := ON 
			outMess($drawer) := "Take " + drugName($drawer)
			logMess($drawer) := ""
		endpar	
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_drawerOpened($drawer in Drawer) =
		par
			if redLed($drawer) != BLINKING and outMess($drawer) != ("Close " + drugName($drawer) + " in 10 minutes") then drawerTimer($drawer) := systemTime endif
			redLed($drawer) := BLINKING
			outMess($drawer) := "Close " + drugName($drawer) + " in 10 minutes"				
		endpar
		
	// Rule to handle the closing of a drawer when the RED Led is blinking
	rule r_drawerClosed($drawer in Drawer) =
		par
			redLed($drawer) := OFF
			outMess($drawer) := ""
			drugIndex($drawer) := drugIndex($drawer) + 1n
		endpar
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill($drawer in Drawer) =
		par
			redLed($drawer) := OFF
			outMess($drawer) := ""
			logMess($drawer) := drugName($drawer) + " missed"	
			drugIndex($drawer) := drugIndex($drawer) + 1n
			r_writeToFile[$drawer] 
		endpar	
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed and the drawer has not been closed
	rule r_timeOutExpired_drawerOpened($drawer in Drawer) =
		par
			redLed($drawer) := OFF
			outMess($drawer) := ""
			logMess($drawer) := drugName($drawer) + " drawer not closed"
			drugIndex($drawer) := drugIndex($drawer) + 1n
			r_writeToFile[$drawer]
		endpar	
		
	//*************************************************
	// MAIN Rule
	//*************************************************	
	// MAIN Rule
	main rule r_Main =
		forall $drawer in Drawer do
			if (drugIndex($drawer)<iton(length(time_consumption($drawer)))) then
				par					
					// Set the satus of the drawer
					if not opened($drawer) and openSwitch($drawer) then opened($drawer) := true endif
					if opened($drawer) and not openSwitch($drawer) then opened($drawer) := false endif
					
					// time to take pill 
					if redLed($drawer) = OFF 
						and at(time_consumption($drawer),drugIndex($drawer))<=systemTime 
						and systemTime-at(time_consumption($drawer),drugIndex($drawer)) < tenMinutes
					then
							r_pillToBeTaken[$drawer]
					endif
        
					// drawer opened within 10 minutes
					if redLed($drawer) = ON 
						and opened($drawer) 
						and at(time_consumption($drawer),drugIndex($drawer))<=systemTime
					then
						r_drawerOpened[$drawer]
					endif
					
					// waited too long - missed pill
					if redLed($drawer) = ON 
						and systemTime-at(time_consumption($drawer),drugIndex($drawer))>tenMinutes
					then
						r_timeOutExpired_missedPill[$drawer]
					endif
					
					// forgot to close the drawer
					if redLed($drawer) = BLINKING 
						and systemTime-drawerTimer($drawer)>tenMinutes
					then
						r_timeOutExpired_drawerOpened[$drawer]
					endif						
					
					// closed drawer within 10 minutes
					if redLed($drawer) = BLINKING 
						and systemTime-drawerTimer($drawer)<=tenMinutes 
						and not(opened($drawer))
					then
						r_drawerClosed[$drawer]
					endif
				endpar
			endif
			
default init s0:	
	// Controlled function that indicates the status of the drawer
	function opened($drawer in Drawer) = false		
	
	// Reset the output display message and the log message
	function outMess($drawer in Drawer) = ""
	function logMess($drawer in Drawer) = ""
	
	// Turn-off all the led of the Drawers
	function redLed($drawer in Drawer) = OFF
	
	// Each drawer's timer starts from 0
	function drawerTimer($drawer in Drawer) = 0n

	// Initialization of the time consumption
	function time_consumption($drawer in Drawer) =
		switch($drawer)
			case drawer1 : [1n, 20n, 30n]
			case drawer2 : [40n, 50n, 60n]
			case drawer3 : [9n, 20n, 30n]
		endswitch

	// Initialization of the SystemTime
	function systemTime = 0n
	
	// Insert a drug in each drawer	
	function drugName($drawer in Drawer) =
		switch($drawer)
			case drawer1 : "Tylenol"
			case drawer2 : "Aspirine"
			case drawer3 : "Moment"
		endswitch
		
	// Every drawer has an index starting from 0
	function drugIndex($drawer in Drawer) = 0n
