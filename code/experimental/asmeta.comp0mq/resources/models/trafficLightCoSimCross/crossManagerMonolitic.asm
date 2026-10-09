asm crossManagerMonolitic
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain CorssManagerStatus = {NORMAL | PEDESTRIAN | TRAM}
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	enum domain LightsPedTram = {RED_TP | GREEN_TP}
	enum domain ControllerOperateMode = {RED_AB | GREEN_A_RED_B | YELLOW_A_RED_B | RED_BA | GREEN_B_RED_A | YELLOW_B_RED_A}
	enum domain ControllerTransition = {TURN_ON | TURN_OFF | OPERATE_T | STANDBY_T}
	enum domain Lights = {ALL_OFF | RED | BLINK_YELLOW | GREEN | YELLOW}
	
	
	//FUNCTIONS
	monitored newPedestrianComing: Boolean
	monitored newTramComing: Boolean
	monitored transitionC: ControllerTransition	
	
	out crossManagerController_previous: CorssManagerStatus
	out statusC: ControllerStatus
	out operateMode : ControllerOperateMode
	out crossManagerController: CorssManagerStatus
	
	out pedestrianLight: LightsPedTram		
	out tramLight: LightsPedTram
	out lightsA1: Lights // traffic light A
	out lightsA2: Lights // traffic light A
	out lightsB1: Lights // traffic light B
	out lightsB2: Lights // traffic light B
	
	
	//TIMER
	static timeTRAM: Timer 
	static timePEDESTRIAN: Timer
	static timeRED: Timer
	static timeGREEN: Timer
	static timeYELLOW: Timer
	
definitions:	
	
	rule r_operateSubStateTLA1 =
		par
	 		if operateMode = RED_AB or operateMode = RED_BA or operateMode = GREEN_B_RED_A or operateMode = YELLOW_B_RED_A then
	 			lightsA1 := RED
	 		endif
	 		if operateMode = GREEN_A_RED_B then
	 			lightsA1 := GREEN	
	 		endif
	 		if operateMode = YELLOW_A_RED_B then
	 			lightsA1 := YELLOW
	 		endif
	 	endpar
	 	
	 rule r_operateSubStateTLA2 =
		par
	 		if operateMode = RED_AB or operateMode = RED_BA or operateMode = GREEN_B_RED_A or operateMode = YELLOW_B_RED_A then
	 			lightsA2 := RED
	 		endif
	 		if operateMode = GREEN_A_RED_B then
	 			lightsA2 := GREEN
	 		endif
	 		if operateMode = YELLOW_A_RED_B then
	 			lightsA2 := YELLOW
	 		endif
	 	endpar
	 	
	 rule r_operateSubStateTLB1 =
		par
	 		if operateMode = RED_AB or operateMode = RED_BA or operateMode = GREEN_A_RED_B or operateMode = YELLOW_A_RED_B then
	 			lightsB1 := RED
	 		endif
	 		if operateMode = GREEN_B_RED_A then
	 			lightsB1 := GREEN
	 		endif
	 		if operateMode = YELLOW_B_RED_A then
	 			lightsB1 := YELLOW
	 		endif
	 	endpar
	 	
	  rule r_operateSubStateTLB2 =
		par
	 		if operateMode = RED_AB or operateMode = RED_BA or operateMode = GREEN_A_RED_B or operateMode = YELLOW_A_RED_B then
	 			lightsB2 := RED
	 		endif
	 		if operateMode = GREEN_B_RED_A then
	 			lightsB2 := GREEN	
	 		endif
	 		if operateMode = YELLOW_B_RED_A then
	 			lightsB2 := YELLOW
	 		endif
	 	endpar
	 	
	rule r_TrafficLightA1 = 
		par
			if statusC = CONTR_OFF then
				lightsA1 := ALL_OFF
			endif 
			if statusC = STANDBY then
				lightsA1 := BLINK_YELLOW
			endif
			if (statusC = OPERATE) then
			 	r_operateSubStateTLA1[]
			endif
		endpar
		
	rule r_TrafficLightA2 = 
		par
			if statusC = CONTR_OFF then
				lightsA2 := ALL_OFF
			endif 
			if statusC = STANDBY then
				lightsA2 := BLINK_YELLOW
			endif
			if (statusC = OPERATE) then
			 	r_operateSubStateTLA2[]
			endif
		endpar
	
	rule r_TrafficLightB1 = 
		par
			if statusC = CONTR_OFF then
				lightsB1 := ALL_OFF
			endif 
			if statusC = STANDBY then
				lightsB1 := BLINK_YELLOW
			endif
			if (statusC = OPERATE) then
			 	r_operateSubStateTLB1[]
			endif
		endpar
	
	rule r_TrafficLightB2 = 
		par
			if statusC = CONTR_OFF then
				lightsB2 := ALL_OFF
			endif 
			if statusC = STANDBY then
				lightsB2 := BLINK_YELLOW
			endif
			if (statusC = OPERATE) then
			 	r_operateSubStateTLB2[]
			endif
		endpar
		
	rule r_operateSubState =
		if crossManagerController = TRAM or crossManagerController = PEDESTRIAN then
			par
				crossManagerController_previous := crossManagerController
		 		if (operateMode = GREEN_B_RED_A or operateMode = YELLOW_B_RED_A) then
		 			operateMode := RED_AB
		 		endif
		 		if (operateMode = GREEN_A_RED_B or operateMode = YELLOW_A_RED_B) then
		 			operateMode := RED_BA
		 		endif
	 		endpar
	 	else
		par
			crossManagerController_previous := crossManagerController
	 		if operateMode = RED_AB then	
	 			if expired(timeRED) or crossManagerController_previous = TRAM or crossManagerController_previous = PEDESTRIAN then
		 			par	
		 				operateMode := GREEN_A_RED_B
						r_reset_timer[timeGREEN]
		 			endpar
	 			endif
	 		endif
	 		if operateMode = GREEN_A_RED_B then
	 			if expired(timeGREEN) then
		 			par	
		 				operateMode := YELLOW_A_RED_B
						r_reset_timer[timeYELLOW]
		 			endpar
		 		endif		 		
	 		endif
	 		if operateMode = YELLOW_A_RED_B then
	 			if expired(timeYELLOW) then
		 			par	
		 				operateMode := RED_BA
						r_reset_timer[timeRED]
		 			endpar
	 			endif
	 		endif
	 		if operateMode = RED_BA then
	 			if expired(timeRED) or crossManagerController_previous = TRAM or crossManagerController_previous = PEDESTRIAN then
		 			par	
		 				operateMode := GREEN_B_RED_A
						r_reset_timer[timeGREEN]
		 			endpar
	 			endif
	 		endif
	 		if operateMode = GREEN_B_RED_A then
	 			if expired(timeGREEN) then
		 			par	
		 				operateMode := YELLOW_B_RED_A
						r_reset_timer[timeYELLOW]
		 			endpar
		 		endif	
	 		endif
	 		if operateMode = YELLOW_B_RED_A then
	 			if expired(timeYELLOW) then
		 			par	
		 				operateMode := RED_AB
						r_reset_timer[timeRED]
		 			endpar
	 			endif
	 		endif
	 	endpar
	 endif
		
	macro rule r_Controller = 
		par
			if statusC = CONTR_OFF and transitionC = TURN_ON then
				statusC := STANDBY
			endif 
			if statusC = STANDBY then
				par
					if transitionC = TURN_OFF then
						statusC := CONTR_OFF
					endif
					if transitionC = OPERATE_T then
						par
							statusC := OPERATE
							operateMode := RED_AB
							r_reset_timer[timeRED]
						endpar
					endif
				endpar
			endif
			if (statusC = OPERATE) then
				if transitionC = STANDBY_T then
					statusC := STANDBY
				else
					r_operateSubState[]
				endif	
			endif
		endpar
	
	
	macro rule r_Tram = 
		if crossManagerController = TRAM then
		tramLight := GREEN_TP
	else
		tramLight := RED_TP
	endif
	
	macro rule r_Pedestrian	= 
		if crossManagerController = PEDESTRIAN then
			pedestrianLight := GREEN_TP
		else
			pedestrianLight := RED_TP
		endif 
	
	macro rule r_CrossManager =	
		if (statusC = OPERATE) then
			if crossManagerController = NORMAL then
				if newPedestrianComing then
					par
						crossManagerController := PEDESTRIAN
						r_reset_timer[timePEDESTRIAN]
					endpar
				else
					if newTramComing then
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
	
	main rule r_Main = 
		seq
			par
				r_Tram[]
				r_Pedestrian[]
			endpar
				par
					r_CrossManager[]
					seq
						r_Controller[]
							par
								r_TrafficLightA1[]
								r_TrafficLightA2[]
								r_TrafficLightB1[]
								r_TrafficLightB2[]
							endpar
					endseq
				endpar
		endseq
	

default init s0:	
	function duration($t in Timer) = switch $t
		case timeTRAM: 2
		case timePEDESTRIAN: 2
		case timeRED: 3
		case timeGREEN: 3
		case timeYELLOW: 1
	endswitch
	function crossManagerController = NORMAL
	function crossManagerController_previous = NORMAL
	function pedestrianLight = RED_TP	
	function newPedestrianComing = false
	function newTramComing = false	
 	function tramLight = RED_TP
 	function statusC = CONTR_OFF
	function operateMode = RED_AB
	function lightsA1 = ALL_OFF
	function lightsA2 = ALL_OFF
	function lightsB1 = ALL_OFF
	function lightsB2 = ALL_OFF
	