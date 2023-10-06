asm crossManager
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain MasterStatus = {NORMAL | PEDESTRIAN | TRAM}
	
	monitored pedestrianCall: Boolean
	monitored tramComing: Boolean
	
	
	//FUNCTIONS
	out masterController: MasterStatus
	
definitions:	
	
	main rule r_Main = skip
	//se riceve pedestrian call o tram coming 
	//invia in output il nuovo stato
	

default init s0:	
	