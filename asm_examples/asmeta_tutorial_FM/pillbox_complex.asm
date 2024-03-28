asm pillbox_complex

import StandardLibrary
import CTLlibrary
import LTLlibrary

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	abstract domain Drawer	
	enum domain LedLights = {OFF | ON }
	enum domain Drugs = {TYLENOL | ASPIRINE | MOMENT}
	//*************************************************
	// MONITORED AND CONTROLLED FUNCTIONS
	//*************************************************
	dynamic monitored openSwitch: Drawer -> Boolean
	dynamic controlled opened: Drawer -> Boolean
	dynamic controlled redLed: Drawer -> LedLights
	dynamic controlled time_consumption: Drawer -> Seq(Natural)
	dynamic controlled drug: Drawer -> Drugs
	dynamic controlled drugIndex: Drawer -> Natural
	// The systemTime is expressed as the number of hours passed since the 01/01/1970
	dynamic monitored systemTime: Natural
	dynamic controlled drawerTimer: Drawer -> Natural
	static tenMinutes: Integer	
	//*************************************************
	// DERIVED FUNCTIONS
	//*************************************************
	// Functions checking the status of a Drawer
	derived drawerClosed: Drawer -> Boolean	
	derived drawerOpened: Drawer -> Boolean	
	// It is true when the time_consumption is exceeded by ten minutes
	derived tenMinutesPassed: Drawer -> Boolean	
	// Functions checking the status of a Led
	derived isOn: Drawer -> Boolean
	derived isOff: Drawer -> Boolean
	derived areOthersOn: Drawer -> Boolean
	// It is true when the pill has to be taken
	derived pillDeadlineHit: Drawer -> Boolean 
	// It is true if there is any other pill in that drawer still to be taken
	derived isThereAnyOtherDeadline: Drawer -> Boolean
	//*************************************************
	// STATIC FUNCTIONS
	//*************************************************
	static drawer1: Drawer
	static drawer2: Drawer
	static drawer3: Drawer	
	
definitions:
	//*************************************************
	// STATIC AND DERIVED FUNCTIONS DEFINITIONS
	//*************************************************
	function tenMinutes = 600
	function tenMinutesPassed($d in Drawer) = (systemTime-drawerTimer($d)>tenMinutes)
	
	function drawerClosed($d in Drawer) = (not openSwitch($d) and opened($d))	
	function drawerOpened($d in Drawer) = (openSwitch($d) and not opened($d))
	
	function isOn($d in Drawer) = (redLed($d) = ON)	
	function isOff($d in Drawer) = (redLed($d) = OFF)
	function areOthersOn($d in Drawer) = switch($d)
			case drawer1 : isOn(drawer2) or isOn(drawer3)
			case drawer2 : isOn(drawer1) or isOn(drawer3)
			case drawer3 : isOn(drawer2) or isOn(drawer1)
		endswitch
	
	function pillDeadlineHit ($d in Drawer) = (at(time_consumption($d),drugIndex($d))<=systemTime)	
	function isThereAnyOtherDeadline ($d in Drawer) = (drugIndex($d)<iton(length(time_consumption($d))))		
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule to reset the Drawer due to one of the possible reasons (Timeout, Pill taken, etc.)
	rule r_reset($drawer in Drawer) = par
			redLed($drawer) := OFF
			drugIndex($drawer) := drugIndex($drawer) + 1n
		endpar
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken($drawer in Drawer) = par
			if not isOn($drawer) then drawerTimer($drawer) := systemTime endif
			redLed($drawer) := ON 
		endpar	
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_drawerOpened($drawer in Drawer) = r_reset[$drawer]
		
	// Rule to take the system back to the initial state when the drawer is open and redLed is ON
	rule r_pillTaken_drawerOpened($drawer in Drawer) = r_reset[$drawer]
		
	// Rule to handle the closing of a drawer when the RED Led is blinking
	rule r_drawerClosed($drawer in Drawer) = r_reset[$drawer]
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed
	rule r_timeOutExpired_missedPill($drawer in Drawer) = r_reset[$drawer]
		
	// Rule to take the system back to the initial state when the Blinking timeout is passed 
	// and the drawer has not been closed
	rule r_timeOutExpired_drawerOpened($drawer in Drawer) = r_reset[$drawer]
		
	// Rule to set the red led blinking, after the drawer opening
	rule r_takeInTimeout($drawer in Drawer) = r_reset[$drawer]
		
	// Rule setting the status of the drawer
	rule r_set($drawer in Drawer) = par
			if drawerOpened($drawer) then opened($drawer) := true endif
			if drawerClosed($drawer) then opened($drawer) := false endif
		endpar
		
	// System evolution starting from the ON State
	rule r_ON($drawer in Drawer) = if isOn($drawer) then par
				// It is open, drug to be taken, it becomes closed
				if tenMinutesPassed($drawer) and drawerClosed($drawer) then 
					r_pillTaken_drawerOpened[$drawer] endif
				// It is closed, drug to be taken, and it becomes open     
				if drawerOpened($drawer) then r_drawerOpened[$drawer] endif
				// It is closed, drug to be taken, and timeout 
				if tenMinutesPassed($drawer) then 
					if opened($drawer) then r_drawerOpened[$drawer] else 
						if not openSwitch($drawer) then r_takeInTimeout[$drawer] endif endif 
				endif 
			endpar
		endif
		
	// Non-determinism: Only a single RedLight is to be on at a time, so choose randomly the order
	// of the pills
	rule r_choosePillToTake = choose $drawer in Drawer with 
			pillDeadlineHit($drawer) and isOff($drawer) and not areOthersOn($drawer) do
				r_pillToBeTaken[$drawer]
	
	// Set other drawers status		
	rule r_setOtherDrawers = forall $drawer in Drawer do
		if (isThereAnyOtherDeadline($drawer)) then par					
				// Set the satus of the drawer
				r_set[$drawer]
					
				// Handle the evolution of the System when the LED is in ON state
				r_ON[$drawer]
			endpar
		endif
	//*************************************************
	// INVARIANTS
	//*************************************************	
	invariant inv_drawer1 over Drawer: (forall $d in Drawer with isOn($d) implies not areOthersOn($d)) 
	//*************************************************
	// MAIN Rule
	//*************************************************	
	main rule r_Main = par
			// Non-determistic choice of the pill
			r_choosePillToTake[]
			
			// Handle other states
			r_setOtherDrawers[]			
		endpar
			
default init s0:	
	// Controlled function that indicates the status of the drawer
	function opened($drawer in Drawer) = false
	
	// Turn-off all the led of the Drawers
	function redLed($drawer in Drawer) = OFF
	
	// Each drawer's timer starts from 0
	function drawerTimer($drawer in Drawer) = 0n

	// Initialization of the time consumption
	function time_consumption($drawer in Drawer) = switch($drawer)
			case drawer1 : [1n, 20n, 30n]
			case drawer2 : [40n, 50n, 60n]
			case drawer3 : [9n, 20n, 30n]
		endswitch

	// Initialization of the SystemTime
	function systemTime = 0n
	
	// Insert a drug in each drawer	
	function drug($drawer in Drawer) = switch($drawer)
			case drawer1 : TYLENOL
			case drawer2 : ASPIRINE
			case drawer3 : MOMENT
		endswitch
		
	// Every drawer has an index starting from 0
	function drugIndex($drawer in Drawer) = 0n
