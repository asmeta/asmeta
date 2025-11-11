asm compartment

import  ../StandardLibrary

// Fourth refinement level of the Pill Box. We further added the function actualConsTime to record the actual assumption time of pills 

signature:

enum domain LedLights = {OFF | ON | BLINKING}

//IN from patient
monitored compartmentOpen: Boolean //free monitored variable -> set in the script
monitored outMess: Natural ->  String
monitored led: Natural ->  LedLights

//Natural 1..5
monitored myID: Natural //free monitored variable -> set in the script
controlled myID_Cont: Natural

//OUT to patient 
out ledStatus: LedLights
out displayMessage: String
// OUT pillbox
out openSwitch: Natural -> Boolean


definitions:

//@pre
//Check semantic consistency between the status of redLed and the given the out message
invariant over outMess: contains(outMess(myID_Cont), "Take")  implies (led(myID_Cont) = ON or led(myID_Cont) = BLINKING)
invariant over outMess: contains(outMess(myID_Cont), "Close")  implies led(myID_Cont) = BLINKING 
invariant over outMess: (contains(outMess(myID_Cont), "") or contains(outMess(myID_Cont), "taken") or contains(outMess(myID_Cont), "missed")) implies led(myID_Cont) = OFF 

//@post
//Check semantic consistency between the status of redLed and the given out message
invariant over displayMessage: contains(displayMessage, "Take")  implies (ledStatus = ON or ledStatus = BLINKING)
invariant over displayMessage: contains(displayMessage, "Close")  implies ledStatus = BLINKING 
invariant over displayMessage: (contains(displayMessage, "") or contains(displayMessage, "taken") or contains(displayMessage, "missed")) implies ledStatus = OFF 


	main rule r_Main =
		seq
			if (myID_Cont=undef) then
				myID_Cont := myID
			endif
		par
			ledStatus := led(myID_Cont)
			displayMessage := outMess(myID_Cont)
			openSwitch(myID_Cont) := compartmentOpen
		endpar
		endseq
	
default init s0:
	function outMess($myID_Cont in Natural)=""
	function led($myID_Cont in Natural)=OFF