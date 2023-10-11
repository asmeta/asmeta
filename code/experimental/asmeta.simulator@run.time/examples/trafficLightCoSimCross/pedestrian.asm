asm pedestrian
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain CorssManagerStatus = {NORMAL | PEDESTRIAN | TRAM}	
	enum domain Lights = {RED | GREEN}
	
	//FUNCTIONS
	monitored crossManagerController: CorssManagerStatus
	monitored pedestrianComing: Boolean
	
	
	out pedestrianCall: Boolean
	out light: Lights	
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	if crossManagerController = PEDESTRIAN then
		light := GREEN
	else
		par
			light := RED
			if pedestrianComing then
				pedestrianCall := pedestrianComing
			endif
		endpar
	endif 

// INITIAL STATE
default init s0:	
 function light = RED	
 function pedestrianCall = false

