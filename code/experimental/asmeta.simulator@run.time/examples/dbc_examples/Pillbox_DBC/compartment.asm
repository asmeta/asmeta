asm compartment

import  ../StandardLibrary

// Fourth refinement level of the Pill Box. We further added the function actualConsTime to record the actual assumption time of pills 

signature:

enum domain LedLights = {OFF | ON | BLINKING}

//IN from patient
monitored compartmentOpen: Boolean //free monitored variable -> set in the script
monitored outMess: Integer ->  String
monitored led: Integer ->  LedLights

monitored myID: Integer //free monitored variable -> set in the script
controlled myID_Cont: Integer

//OUT to patient 
out ledStatus: LedLights
out displayMessage: String
// OUT pillbox
out openSwitch: Integer -> Boolean


definitions:


	main rule r_Main =
		par
			if (myID_Cont=undef) then
				myID_Cont := myID
			endif
			ledStatus := led(myID_Cont)
			displayMessage := outMess(myID_Cont)
			openSwitch(myID_Cont) := compartmentOpen
		endpar
	
default init s0: