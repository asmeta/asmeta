asm compartment

import  ../StandardLibrary

// Fourth refinement level of the Pill Box. We further added the function actualConsTime to record the actual assumption time of pills 

signature:

enum domain LedLights = {OFF | ON | BLINKING}

	enum domain States = {INIT | NORMAL}
	controlled state: States
	
//IN from patient
monitored compartmentOpen:Natural -> Boolean //free monitored variable -> set in the script
monitored outMess: Natural ->  String
monitored led: Natural ->  LedLights

//Natural 1..5
monitored myID: Natural //free monitored variable -> set in the script
controlled myID_Cont: Natural

//OUT to patient 
out ledStatus: Natural -> LedLights
out displayMessage: Natural -> String
// OUT pillbox
out openSwitch: Natural -> Boolean


definitions:

//@pre
//Check semantic consistency between the status of redLed and the given the out message
invariant inv_compOutMess1 over outMess: contains(outMess(myID_Cont), "Take")  implies (led(myID_Cont) = ON or led(myID_Cont) = BLINKING)
invariant inv_compOutMess2 over outMess: contains(outMess(myID_Cont), "Close")  implies led(myID_Cont) = BLINKING 
invariant inv_compOutMess3 over outMess: (outMess(myID_Cont)="" or contains(outMess(myID_Cont), "taken") or contains(outMess(myID_Cont), "missed")) implies led(myID_Cont) = OFF 

//@post
//Check semantic consistency between the status of redLed and the given out message
invariant inv_compDispMess1 over displayMessage: contains(displayMessage(myID_Cont), "Take")  implies (ledStatus(myID_Cont) = ON or ledStatus(myID_Cont) = BLINKING)
invariant inv_compDispMess2 over displayMessage: contains(displayMessage(myID_Cont), "Close")  implies ledStatus(myID_Cont) = BLINKING 
invariant inv_compDispMess3 over displayMessage: (displayMessage(myID_Cont)="" or contains(displayMessage(myID_Cont), "taken") or contains(displayMessage(myID_Cont), "missed")) implies ledStatus(myID_Cont) = OFF 


	main rule r_Main =
		if state = INIT then
			par
				if (myID_Cont=undef) then
						myID_Cont := myID
				endif
				state:=NORMAL
			endpar
		else
					par
						ledStatus(myID_Cont) := led(myID_Cont)
						displayMessage(myID_Cont) := outMess(myID_Cont)
						openSwitch(myID_Cont) := compartmentOpen(myID_Cont)
					endpar
		endif
	
default init s0:
	function state = INIT