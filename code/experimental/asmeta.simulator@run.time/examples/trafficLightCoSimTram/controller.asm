asm controller
 
import ../TimeLibrarySimple

signature:
	// DOMAINS
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	enum domain ControllerOperateMode = {RED_AB | GREEN_A_RED_B | YELLOW_A_RED_B | RED_BA | GREEN_B_RED_A | YELLOW_B_RED_A}
	enum domain ControllerTransition = {TURN_ON | TURN_OFF | OPERATE_T | STANDBY_T}
	
	
	enum domain MasterStatus = {NORMAL | PEDESTRIAN | TRAM}
	
	//FUNCTIONS
	monitored transitionC: ControllerTransition	
	out statusC: ControllerStatus
	out operateMode : ControllerOperateMode
	
	monitored masterController: MasterStatus
	controlled masterController_previous: MasterStatus
	
	//TIMER
	static timeRED: Timer
	static timeGREEN: Timer
	static timeYELLOW: Timer
	
definitions:	

rule r_operateSubState =
		if masterController = TRAM or masterController = PEDESTRIAN then
			par
				masterController_previous := masterController
		 		if (operateMode = GREEN_B_RED_A or operateMode = YELLOW_B_RED_A) then
		 			operateMode := RED_AB
		 		endif
		 		if (operateMode = GREEN_A_RED_B or operateMode = YELLOW_A_RED_B) then
		 			operateMode := RED_BA
		 		endif
	 		endpar
	 	else
		par
			masterController_previous := masterController
	 		if operateMode = RED_AB then	
	 			if expired(timeRED) or masterController_previous = TRAM or masterController_previous = PEDESTRIAN then
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
	 			if expired(timeRED) or masterController_previous = TRAM or masterController_previous = PEDESTRIAN then
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
	 	
main rule r_Main =
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

// INITIAL STATE
default init s0:		
	function statusC = CONTR_OFF
	function operateMode = RED_AB
	function masterController_previous = NORMAL
	function duration($t in Timer) = switch $t
		case timeRED: 5
		case timeGREEN: 5
		case timeYELLOW: 2
		endswitch
