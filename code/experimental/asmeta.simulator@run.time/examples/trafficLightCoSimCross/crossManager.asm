asm crossManager
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain CorssManagerStatus = {NORMAL | PEDESTRIAN | TRAM}
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	
	//FUNCTIONS
	monitored pedestrianCall: Boolean
	monitored tramComing: Boolean
	monitored statusC: ControllerStatus
	
	
	out crossManagerController: CorssManagerStatus
	
	//TIMER
	static timeTRAM: Timer 
	static timePEDESTRIAN: Timer
	
definitions:	
	
	main rule r_Main = 
	if (statusC = OPERATE) then
		if crossManagerController = NORMAL then
			if pedestrianCall then
				par
					crossManagerController := PEDESTRIAN
					r_reset_timer[timePEDESTRIAN]
				endpar
			else
				if tramComing then
					par
						crossManagerController := TRAM
						r_reset_timer[timeTRAM]
					endpar
				endif
			endif
		else
			par
				if crossManagerController = PEDESTRIAN then
					if expired(timePEDESTRIAN) then
						crossManagerController := NORMAL
					endif
				endif
				if crossManagerController = TRAM then
					if expired(timeTRAM) then
						crossManagerController := NORMAL
					endif
				endif
			endpar
		endif
	else
		crossManagerController := NORMAL
	endif
	

default init s0:	
	function duration($t in Timer) = switch $t
		case timeTRAM: 2
		case timePEDESTRIAN: 2
	endswitch
	function crossManagerController = NORMAL