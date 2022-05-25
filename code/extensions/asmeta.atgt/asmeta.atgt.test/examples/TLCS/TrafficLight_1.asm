asm TrafficLight_1
//====================================NEW====================================
//When controller is in OPERATE state it has three substates: BLOCKED_A, RELEASE_A, RELEASED_A
//New transition added to controller: SAFE_PERIOD
//Lights can be also GREEN and YELLOW and the corresponding traffic lights state is:
//GREEN -> RELEASED
//YELLOW -> PREPARE_BLOCK
//Traffic light B is always BLOCKED when controller is in OPERATE state

import ../StandardLibrary

signature:
	// DOMAINS
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	enum domain ControllerSubStatusOperate = {BLOCKED_A | RELEASE_A | RELEASED_A}
	enum domain ControllerTransition = {TURN_ON | TURN_OFF | OPERATE_T | STANDBY_T | SAFE_PERIOD}
	//enum domain Lights = {NONE}
	enum domain LightTransition = {RELEASE_PERIOD | PREPARE_PERIOD}
	enum domain LightStatus = {OFF | ATTENTION | BLOCKED | RELEASED | PREPARE_BLOCK}
	// FUNCTIONS
	//controlled lightsA: Lights // traffic light A
	//controlled lightsB: Lights // traffic light B
	controlled statusC: ControllerStatus
	controlled statusCOperate : ControllerSubStatusOperate
	monitored transitionC: ControllerTransition
	monitored transitionA: LightTransition 
	controlled statusA: LightStatus
	controlled statusB: LightStatus


definitions:
	// DOMAIN DEFINITIONS

	rule r_setAttention =
		par
			statusA := ATTENTION
			statusB := ATTENTION
		endpar
	
	rule r_setAllOff = 
		par
			statusA := OFF
			statusB := OFF
		endpar
	
	rule r_setBlock = 
		par
			statusA := BLOCKED
			statusB := BLOCKED
		endpar
		
	rule r_ContrOff =
		if transitionC = TURN_ON then
			par
				r_setAttention[]
				statusC := STANDBY
			endpar
		endif	
		
	rule r_Standby = 
		par
			if transitionC = TURN_OFF  then
				par
					r_setAllOff[]
					statusC := CONTR_OFF
				endpar
			endif	
			if transitionC = OPERATE_T then
				par
					r_setBlock[]
					statusC := OPERATE
					statusCOperate := BLOCKED_A
				endpar
			endif
		endpar	
	
	rule r_blockedA =
		if transitionC = SAFE_PERIOD then
			par
				statusA := RELEASED
				statusCOperate := RELEASE_A
			endpar
		endif
	
	rule r_releaseA =
		if statusA = RELEASED then 
	 		if transitionA = RELEASE_PERIOD then
	 			par
	 				statusA := PREPARE_BLOCK		
	 				statusCOperate := RELEASED_A
	 			endpar
	 		endif
	 	endif
		 	
	rule r_releasedA =
	 	if statusA = PREPARE_BLOCK then
	 		if transitionA = PREPARE_PERIOD then
	 			par
	 				statusA := BLOCKED
	 				statusCOperate := BLOCKED_A
	 			endpar
	 		endif
	 	endif
	
		
	//If controller is in operate state check the substate
	rule r_checkOperateSubState =
		par
	 		if statusCOperate = BLOCKED_A then
	 			r_blockedA[]
	 		endif
	 		if statusCOperate = RELEASE_A then
	 			r_releaseA[]
	 		endif
	 		if statusCOperate = RELEASED_A then
	 			r_releasedA[]
	 		endif
	 	endpar
	
	rule r_Operate =
		if transitionC = STANDBY_T then
			par
				r_setAttention[]
				statusC := STANDBY
			endpar
		else
			r_checkOperateSubState[]
		endif	

	main rule r_Main =
		par
			if (statusC = CONTR_OFF) then
				r_ContrOff[]
			endif 
			if (statusC = STANDBY) then
				r_Standby[]
			endif
			if (statusC = OPERATE) then
				r_Operate[]
			endif
		endpar


// INITIAL STATE
default init s0:
//	function lightsA = NONE
//	function lightsB = NONE
	function statusC = CONTR_OFF
	function statusCOperate = BLOCKED_A
	function statusA = OFF
	function statusB = OFF
