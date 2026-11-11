asm trafficlightA
 
import ../StandardLibrary

signature:
	// DOMAINS
	enum domain Lights = {ALL_OFF | RED | BLINK_YELLOW | GREEN | YELLOW}
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	enum domain ControllerOperateMode = {RED_AB | GREEN_A_RED_B | YELLOW_A_RED_B | RED_BA | GREEN_B_RED_A | YELLOW_B_RED_A}
	
	out lightsA: Lights // traffic light A
	monitored statusC: ControllerStatus
	monitored operateMode : ControllerOperateMode
	
	
definitions:

	rule r_operateSubState =
		par
	 		if operateMode = RED_AB or operateMode = RED_BA or operateMode = GREEN_B_RED_A or operateMode = YELLOW_B_RED_A then
	 			lightsA := RED
	 		endif
	 		if operateMode = GREEN_A_RED_B then
	 			lightsA := GREEN		
	 		endif
	 		if operateMode = YELLOW_A_RED_B then
	 			lightsA := YELLOW
	 		endif
	 	endpar
	 	
	main rule r_Main =
		par
			if statusC = CONTR_OFF then
				lightsA := ALL_OFF
			endif 
			if statusC = STANDBY then
				lightsA := BLINK_YELLOW
			endif
			if (statusC = OPERATE) then
			 	r_operateSubState[]
			endif
		endpar
		
// INITIAL STATE
default init s0:		
	function lightsA = ALL_OFF
