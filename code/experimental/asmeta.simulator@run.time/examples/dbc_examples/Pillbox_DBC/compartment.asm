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

//@pre
//Check the status of redLed given the out message
invariant over outMess: contains(outMess(myID_Cont), "Take")  implies (led(myID_Cont) = ON or led(myID_Cont) = BLINKING)
invariant over outMess: contains(outMess(myID_Cont), "Close")  implies led(myID_Cont) = BLINKING 
invariant over outMess: (contains(outMess(myID_Cont), "") or contains(outMess(myID_Cont), "taken") or contains(outMess(myID_Cont), "missed")) implies led(myID_Cont) = OFF 

//@post
//Check the status of redLed given the out message
invariant over displayMessage: contains(displayMessage, "Take")  implies (ledStatus = ON or ledStatus = BLINKING)
invariant over displayMessage: contains(displayMessage, "Close")  implies ledStatus = BLINKING 
invariant over displayMessage: (contains(displayMessage, "") or contains(displayMessage, "taken") or contains(displayMessage, "missed")) implies ledStatus = OFF 


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
	function outMess($myID_Cont in Integer)=""
	function led($myID_Cont in Integer)=OFF