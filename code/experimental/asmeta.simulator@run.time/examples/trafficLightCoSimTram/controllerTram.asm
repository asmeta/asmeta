asm controllerTram
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain ControllerTramStatus = {STOP | GO}	
	enum domain MasterStatus = {NORMAL | PEDESTRIAN | TRAM}
	
	//FUNCTIONS
	monitored masterController: MasterStatus
	monitored newTramComing: Boolean
	
	out tramComing_out: Boolean	
	out controllerTramSignal: ControllerTramStatus
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	par
		tramComing_out := newTramComing
		if masterController = TRAM then
			controllerTramSignal := GO
		else
			controllerTramSignal := STOP
		endif
	endpar

// INITIAL STATE
default init s0:	
 function tramComing_out = false	
 function controllerTramSignal = STOP

