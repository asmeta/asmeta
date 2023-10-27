asm tram
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain CorssManagerStatus = {NORMAL | PEDESTRIAN | TRAM}
	enum domain Lights = {RED | GREEN}
	
	//FUNCTIONS
	monitored crossManagerController: CorssManagerStatus
	monitored newTramComing: Boolean
	
	out tramComing: Boolean	
	out tramLight: Lights
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	if crossManagerController = TRAM then
		tramLight := GREEN
	else
		par
			tramLight := RED
			tramComing := newTramComing
		endpar
	endif

// INITIAL STATE
default init s0:	
 function tramComing = false	
 function tramLight = RED
 function crossManagerController = NORMAL

