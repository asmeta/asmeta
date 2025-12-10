// 
// first refined version with time (simple)
// it refines pillDeadlineHit from monitored to derived (according to the time)
//  
//
asm pillbox_time

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary
import ../../STDL/LTLLibrary
import ../../STDL/TimeLibrarySimple

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
	static tenMinutes: Timer	
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
	function isOn($d in Drawer) = (drawerLed($d) = ON)	
	function isOff($d in Drawer) = (drawerLed($d) = OFF)
	function areOthersOn($d in Drawer) = switch($d)
			case drawer1 : isOn(drawer2) or isOn(drawer3)
			case drawer2 : isOn(drawer1) or isOn(drawer3)
			case drawer3 : isOn(drawer2) or isOn(drawer1)
		endswitch
	
	function pillDeadlineHit ($d in Drawer) = (time_consumption($d)<=mCurrTimeSecs)	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule to reset the Drawer due to one of the possible reasons (Timeout, Pill taken, etc.)
	rule r_reset($drawer in Drawer) = par
			drawerLed($drawer) := OFF
			isPillTobeTaken($drawer):= false
		endpar
	
	// Rule to set the led red ON when the pill has to be taken
	rule r_pillToBeTaken($drawer in Drawer) = par
			if isOff($drawer) then r_reset_timer[tenMinutes] endif
			drawerLed($drawer) := ON 
		endpar	
		
	// System evolution starting from the ON State
	rule r_take($drawer in Drawer) = if isOn($drawer) then
			// The pill has been taken, or the timer expires
			if expired(tenMinutes) or isPillTaken($drawer) then 
				r_reset[$drawer] endif
		endif
		
	// Non-determinism: Only a single RedLight is to be on at a time, so choose randomly the order
	// of the pills
	rule r_choosePillToTake = choose $drawer in Drawer with 
			isPillTobeTaken($drawer) and isOff($drawer) and not areOthersOn($drawer) do
				r_pillToBeTaken[$drawer]
	
	// Set the status for other drawers		
	rule r_setOtherDrawers = forall $drawer in Drawer do par
		if pillDeadlineHit($drawer) then isPillTobeTaken($drawer):= true endif				
		// Handle the evolution of the System when the LED is in ON state
		r_take[$drawer]
		endpar
		
	//*************************************************
	// INVARIANTS AND TEMPORAL PROPERTIES
	//*************************************************
	invariant inv_drawer1 over Drawer: (forall $d in Drawer with isOn($d) implies not areOthersOn($d)) 	
	LTLSPEC g(drawerLed(drawer1)=ON or drawerLed(drawer1)=OFF)
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
	
	// Timer initialization
	function duration($t in Timer) = 600
	function start($t in Timer) = currentTime($t)

	function isPillTobeTaken($d in Drawer) = false

