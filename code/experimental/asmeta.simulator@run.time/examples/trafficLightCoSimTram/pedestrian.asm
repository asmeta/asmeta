asm pedestrian
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain MasterStatus = {NORMAL | PEDESTRIAN | TRAM}	
	enum domain Lights = {RED | GREEN}
	
	//FUNCTIONS
	monitored masterController: MasterStatus
	monitored pedestrianCall: Boolean
	
	
	out pedestrianCall_out: Boolean
	out light: Lights	
	
	//TIMER

definitions:	

	 	
main rule r_Main =
	par
		if masterController = PEDESTRIAN then
			light := GREEN
		endif
		if  masterController = NORMAL or masterController = TRAM then
			par
				light := RED
				if pedestrianCall then
					pedestrianCall_out := pedestrianCall
				endif
			endpar
		endif 
	endpar

// INITIAL STATE
default init s0:	
 function light = RED	
 function pedestrianCall_out = false

