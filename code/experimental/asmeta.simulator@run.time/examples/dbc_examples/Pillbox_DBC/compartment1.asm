asm compartment1

import  ../StandardLibrary

// Fourth refinement level of the Pill Box. We further added the function actualConsTime to record the actual assumption time of pills 

signature:

abstract domain Compartment

enum domain LedLights = {OFF | ON | BLINKING}

//IN from patient
monitored compartmentOpen: Boolean
monitored outMess: Compartment ->  String
monitored redLed: Compartment ->  LedLights

//OUT to patient 
out ledStatus: LedLights
out displayMessage: String
out openSwitch: Compartment -> Boolean

static compartment1: Compartment

definitions:

	main rule r_Main =
		par
			ledStatus := redLed(compartment1)
			displayMessage := outMess(compartment1)
			openSwitch(compartment1) := compartmentOpen
		endpar
	
default init s0:
function displayMessage = ""
function ledStatus = OFF
function openSwitch($compartment in Compartment) = false