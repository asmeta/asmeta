asm TrafficLight_3
//====================================NEW====================================
//Added three substates when controller is in OPERATE state: BLOCKED_B, RELEASE_B, RELEASED_B
 
import ../StandardLibrary
import ../CTLlibrary

signature:
	// DOMAINS
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	enum domain ControllerSubStatusOperate = {BLOCKED_A | RELEASE_A | RELEASED_A | BLOCKED_B | RELEASE_B | RELEASED_B}
	enum domain ControllerTransition = {TURN_ON | TURN_OFF | OPERATE_T | STANDBY_T | SAFE_PERIOD}
	enum domain Lights = {ALL_OFF | RED | BLINK_YELLOW | GREEN | YELLOW}
	enum domain LightTransition = {RELEASE_PERIOD | PREPARE_PERIOD}
	enum domain LightStatus = {OFF | ATTENTION | BLOCKED | RELEASED | PREPARE_BLOCK}
	// FUNCTIONS
	controlled lightsA: Lights // traffic light A
	controlled lightsB: Lights // traffic light B
	controlled statusC: ControllerStatus
	controlled statusCOperate : ControllerSubStatusOperate
	monitored transitionC: ControllerTransition
	monitored transitionA: LightTransition 
	controlled statusA: LightStatus
	controlled statusB: LightStatus	
	monitored transitionB: LightTransition 


definitions:
	// DOMAIN DEFINITIONS

	rule r_setAttention =
		par
			lightsA := BLINK_YELLOW
			lightsB := BLINK_YELLOW
			statusA := ATTENTION
			statusB := ATTENTION
		endpar
	
	rule r_setAllOff = 
		par
			lightsA := ALL_OFF
			lightsB := ALL_OFF
			statusA := OFF
			statusB := OFF
		endpar
	
	rule r_setBlock = 
		par
			lightsA := RED
			lightsB := RED
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
				lightsA := GREEN
				statusA := RELEASED
				statusCOperate := RELEASE_A
			endpar
		endif
	
	rule r_releaseA =
		if statusA = RELEASED then 
	 		if transitionA = RELEASE_PERIOD then
	 			par
	 				lightsA := YELLOW
	 				statusA := PREPARE_BLOCK		
	 				statusCOperate := RELEASED_A
	 			endpar
	 		endif
	 	endif
		 	
	rule r_releasedA =
	 	if statusA = PREPARE_BLOCK then
	 		if transitionA = PREPARE_PERIOD then
	 			par
	 				lightsA := RED
	 				statusA := BLOCKED
	 				statusCOperate := BLOCKED_B
	 			endpar
	 		endif
	 	endif
	
	rule r_blockedB =
		if transitionC = SAFE_PERIOD then
			par
				lightsB := GREEN
				statusB := RELEASED
				statusCOperate := RELEASE_B
			endpar
		endif
	
	rule r_releaseB =
		if statusB = RELEASED then 
	 		if transitionB = RELEASE_PERIOD then
	 			par
	 				lightsB := YELLOW
	 				statusB := PREPARE_BLOCK		
	 				statusCOperate := RELEASED_B
	 			endpar
	 		endif
	 	endif
		 	
	rule r_releasedB =
	 	if statusB = PREPARE_BLOCK then
	 		if transitionB = PREPARE_PERIOD then
	 			par
	 				lightsB := RED
	 				statusB := BLOCKED
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
	 		if statusCOperate = BLOCKED_B then
	 			r_blockedB[]
	 		endif
	 		if statusCOperate = RELEASE_B then
	 			r_releaseB[]
	 		endif
	 		if statusCOperate = RELEASED_B then
	 			r_releasedB[]
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

// The traffic light can be turned off 
	CTLSPEC ag((statusC = OPERATE or statusC = STANDBY) implies ef(statusC = CONTR_OFF))
// When controller is off traffic lights are off
	CTLSPEC ag((statusC = CONTR_OFF) implies (statusA = OFF and statusB = OFF))
// When controller is standby traffic lights are in attention
	CTLSPEC ag((statusC = STANDBY) implies (statusA = ATTENTION and statusB = ATTENTION))
// When controller is All blocked A traffic light are blocked
	CTLSPEC ag((statusC = OPERATE and statusCOperate = BLOCKED_A) implies (statusA = BLOCKED and statusB = BLOCKED))
// When controller is Release A traffic light A is released and traffic light B is blocked
	CTLSPEC ag((statusC = OPERATE and statusCOperate = RELEASE_A) implies (statusA = RELEASED and statusB = BLOCKED))
// When controller is Released A traffic light A is preparing for block and traffic light B is blocked
	CTLSPEC ag((statusC = OPERATE and statusCOperate = RELEASED_A) implies (statusA = PREPARE_BLOCK and statusB = BLOCKED))		
// When controller is All blocked B traffic light are blocked
	CTLSPEC ag((statusC = OPERATE and statusCOperate = BLOCKED_B) implies (statusA = BLOCKED and statusB = BLOCKED))
// When controller is Release B traffic light B is released and traffic light A is blocked
	CTLSPEC ag((statusC = OPERATE and statusCOperate = RELEASE_B) implies (statusA = BLOCKED and statusB = RELEASED))
// When controller is Released B traffic light B is preparing for block and traffic light A is blocked
	CTLSPEC ag((statusC = OPERATE and statusCOperate = RELEASED_B) implies (statusA = BLOCKED and statusB = PREPARE_BLOCK))		
	
	
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
	function lightsA = ALL_OFF
	function lightsB = ALL_OFF
	function statusC = CONTR_OFF
	function statusCOperate = BLOCKED_A
	function statusA = OFF
	function statusB = OFF