asm trafficlightTram
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain ControllerTramStatus = {STOP | GO}	
	enum domain Lights = {RED | GREEN}
	
	//FUNCTIONS
	monitored controllerTramSignal: ControllerTramStatus

	out light: Lights	
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	par
		if controllerTramSignal = STOP then
			light := RED
		endif
		if  controllerTramSignal = GO then
			light := GREEN
		endif
	endpar

// INITIAL STATE
default init s0:	
 function light = RED	

