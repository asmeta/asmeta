asm pedestrian
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain CrossManagerStatus = {NORMAL | PEDESTRIAN | TRAM}	
	enum domain Lights = {RED | GREEN}
	
	//FUNCTIONS
	monitored crossManagerController: CrossManagerStatus
	monitored newPedestrianComing: Boolean
	
	
	out pedestrianComing: Boolean
	out pedestrianLight: Lights	
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	if crossManagerController = PEDESTRIAN then
		pedestrianLight := GREEN
	else
		par
			pedestrianLight := RED
			pedestrianComing := newPedestrianComing
		endpar
	endif 


default init s0:	
 function pedestrianLight = RED	
 function pedestrianComing = false
 function crossManagerController = NORMAL

