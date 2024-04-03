// ground pillbox without time
asm pillbox_ground

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary
import ../STDL/LTLlibrary

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
	dynamic monitored pillDeadlineHit: Drawer -> Boolean 
	dynamic controlled isPillTobeTaken: Drawer -> Boolean
	//*************************************************
	// DERIVED FUNCTIONS
	//*************************************************
	// Functions checking the status of a Led
	derived isOn: Drawer -> Boolean
	derived isOff: Drawer -> Boolean
	derived areOthersOn: Drawer -> Boolean
	// It is true when the pill has to be taken
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
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	// Rule to reset the Drawer due to one of the possible reasons (Timeout, Pill taken, etc.)
	rule r_reset($drawer in Drawer) = 
			drawerLed($drawer) := OFF
		
	// Non-determinism: Only a single RedLight is to be on at a time, so choose randomly the order
	// of the pills
	rule r_choosePillToTake = choose $drawer in Drawer with 
			isPillTobeTaken($drawer) and isOff($drawer) and not areOthersOn($drawer) do
				// Rule to set the led red ON when the pill has to be taken
				drawerLed($drawer) := ON 
	
	// Set the status for other drawers		
	rule r_setOtherDrawers = forall $drawer in Drawer do
		par		
		if pillDeadlineHit($drawer) then  isPillTobeTaken($drawer) := true endif		
		// Handle the evolution of the System when the LED is in ON state
		//r_take[$drawer]
		if isOn($drawer) and
			// The pill has been taken, or the timer expires
			isPillTaken($drawer) then r_reset[$drawer] endif
		endpar
		
	//*************************************************
	// INVARIANTS AND TEMPORAL PROPERTIES
	//*************************************************	

	// max one led is ON
	// TODO
	CTLSPEC ag(isOn(drawer1) implies (isOff(drawer2) and isOff(drawer3)))
	CTLSPEC ag(isOn(drawer1) implies (not areOthersOn(drawer1)))
	
	CTLSPEC ag((forall $d in Drawer with isOn($d) implies (not areOthersOn($d))))
	
	// If the patient takes the pill, red light will turn-off
	CTLSPEC ag(pillDeadlineHit(drawer1) implies ef(isOn(drawer1)))
	// with forall
	CTLSPEC (forall $d in Drawer with ag(pillDeadlineHit($d) implies ef(not areOthersOn($d))))

	// these are false (it may happen that a new pill is never taken
	CTLSPEC ag(pillDeadlineHit(drawer1) implies af(isOn(drawer1)))
	LTLSPEC g(pillDeadlineHit(drawer1) implies f(isOn(drawer1)))
	
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

	// Insert a drug in each drawer	
	function drug($drawer in Drawer) = switch($drawer)
			case drawer1 : TYLENOL
			case drawer2 : ASPIRINE
			case drawer3 : MOMENT
		endswitch
