asm controllerTram
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain ControllerTramStatus = {STOP | GO}	
	enum domain CorssManagerStatus = {NORMAL | PEDESTRIAN | TRAM}
	
	//FUNCTIONS
	monitored crossManagerController: CorssManagerStatus
	monitored newTramComing: Boolean
	
	out tramComing: Boolean	
	out controllerTramSignal: ControllerTramStatus
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	if crossManagerController = TRAM then
		controllerTramSignal := GO
	else
		par
			controllerTramSignal := STOP
			tramComing := newTramComing
		endpar
	endif

// INITIAL STATE
default init s0:	
 function tramComing = false	
 function controllerTramSignal = STOP
 function crossManagerController = NORMAL

