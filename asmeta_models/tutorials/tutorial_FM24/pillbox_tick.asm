// 
// refined version with time as tick
//
asm pillbox_tick

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

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
	dynamic monitored isPillTaken: Drawer -> Boolean
	dynamic controlled drawerLed: Drawer -> LedLights
	dynamic controlled drug: Drawer -> Drugs
	dynamic controlled isPillTobeTaken: Drawer -> Boolean
	dynamic controlled time_consumption: Drawer -> Integer
	// time
	dynamic controlled tick: Integer
	static tenMinutes: Integer
	//*************************************************
	// DERIVED FUNCTIONS
	//*************************************************
	// Functions checking the status of a Led
	derived isOn: Drawer -> Boolean
	derived isOff: Drawer -> Boolean
	derived areOthersOn: Drawer -> Boolean
	// It is true when the pill has to be taken
	derived pillDeadlineHit: Drawer -> Boolean 
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

	function tenMinutes  = 600

	function isOn($d in Drawer) = (drawerLed($d) = ON)	
	function isOff($d in Drawer) = (drawerLed($d) = OFF)
	function areOthersOn($d in Drawer) = switch($d)
			case drawer1 : isOn(drawer2) or isOn(drawer3)
			case drawer2 : isOn(drawer1) or isOn(drawer3)
			case drawer3 : isOn(drawer2) or isOn(drawer1)
		endswitch
	
	function pillDeadlineHit ($d in Drawer) = (time_consumption($d)<=tick)
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule to reset the Drawer due to one of the possible reasons (Timeout, Pill taken, etc.)
	rule r_reset($drawer in Drawer) = par
			drawerLed($drawer) := OFF
			isPillTobeTaken($drawer):= false
		endpar

	// System evolution starting from the ON State
	rule r_take($drawer in Drawer) = if isOn($drawer) then
			// The pill has been taken, or the timer expires
			if time_consumption($drawer) + tenMinutes <= tick  or isPillTaken($drawer) then
				r_reset[$drawer] endif
		endif
		
	// Non-determinism: Only a single RedLight is to be on at a time, so choose randomly the order
	// of the pills
	rule r_choosePillToTake = choose $drawer in Drawer with 
			isPillTobeTaken($drawer) and isOff($drawer) and not areOthersOn($drawer) do
				// Rule to set the led red ON when the pill has to be taken
				drawerLed($drawer) := ON
	
	// Set the status for other drawers		
	rule r_setOtherDrawers = forall $drawer in Drawer do par
		if pillDeadlineHit($drawer) then isPillTobeTaken($drawer):= true endif				
		// Handle the evolution of the System when the LED is in ON state
		r_take[$drawer]
		endpar
		
	//*************************************************
	// INVARIANTS AND TEMPORAL PROPERTIES
	//*************************************************	

	
	// If the patient takes the pill, red light will turn-off
	LTLSPEC g(pillDeadlineHit(drawer1) implies f(isOn(drawer1)))
	
	//*************************************************
	// MAIN Rule
	//*************************************************	
	main rule r_Main = par
			// Non-determistic choice of the pill
			r_choosePillToTake[]
			// Handle other states
			r_setOtherDrawers[]
			tick := tick+1
		endpar
			
default init s0:	
	// Turn-off all the led of the Drawers
	function drawerLed($drawer in Drawer) = OFF

	// Initialization of the time consumption
	function time_consumption($drawer in Drawer) = switch($drawer)
			case drawer1 : 60
			case drawer2 : 2400
			case drawer3 : 180
		endswitch
	
	// Insert a drug in each drawer	
	function drug($drawer in Drawer) = switch($drawer)
			case drawer1 : TYLENOL
			case drawer2 : ASPIRINE
			case drawer3 : MOMENT
		endswitch
	
	// time
	function tick = 0
	function isPillTobeTaken($d in Drawer) = false

