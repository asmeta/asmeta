asm TrafficLight_4
 
import ../StandardLibrary
import ../CTLLibrary

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
	
	rule r_setLightsStatusA($lA in Lights, $sA in LightStatus) = 
		par 
			lightsA := $lA
			statusA := $sA
		endpar
	
	rule r_setLightsStatusB($lB in Lights, $sB in LightStatus) = 
		par 
			lightsB := $lB
			statusB := $sB
		endpar
	
	rule r_setLightsStatus($lA in Lights, $lB in Lights, $sA in LightStatus, $sB in LightStatus) = 
		par 
			r_setLightsStatusA[$lA,$sA]
			r_setLightsStatusB[$lB,$sB]
		endpar
	
	rule r_setCOperateA($lA in Lights, $sA in LightStatus, $cO in ControllerSubStatusOperate)=
		par
			r_setLightsStatusA[$lA, $sA]
			statusCOperate := $cO
		endpar
	
	rule r_setCOperateB($lB in Lights, $sB in LightStatus, $cO in ControllerSubStatusOperate)=
		par
			r_setLightsStatusB[$lB, $sB]
			statusCOperate := $cO
		endpar
		
	rule r_blockedA =
		if transitionC = SAFE_PERIOD then
			r_setCOperateA[GREEN, RELEASED, RELEASE_A]
		endif
	
	rule r_releaseA =
		if statusA = RELEASED and transitionA = RELEASE_PERIOD then
	 		r_setCOperateA[YELLOW, PREPARE_BLOCK, RELEASED_A]
	 	endif
		 	
	rule r_releasedA =
	 	if statusA = PREPARE_BLOCK and transitionA = PREPARE_PERIOD then
	 		r_setCOperateA[RED, BLOCKED, BLOCKED_B]
	 	endif
	
	rule r_blockedB =
		if transitionC = SAFE_PERIOD then
			r_setCOperateB[GREEN, RELEASED, RELEASE_B]
		endif
	
	rule r_releaseB =
		if statusB = RELEASED and transitionB = RELEASE_PERIOD then
	 		r_setCOperateB[YELLOW, PREPARE_BLOCK, RELEASED_B]
	 	endif
		 	
	rule r_releasedB =
	 	if statusB = PREPARE_BLOCK and transitionB = PREPARE_PERIOD then
	 		r_setCOperateB[RED, BLOCKED, BLOCKED_A]
	 	endif
		
	//If controller is in operate state check the substate
	rule r_operateSubState =
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
			if statusC = CONTR_OFF and transitionC = TURN_ON then
				par
					r_setLightsStatus[BLINK_YELLOW, BLINK_YELLOW, ATTENTION, ATTENTION]
					statusC := STANDBY
				endpar
			endif 
			if statusC = STANDBY and transitionC = TURN_OFF  then
				par
					r_setLightsStatus[ALL_OFF, ALL_OFF, OFF, OFF]
					statusC := CONTR_OFF
				endpar
			endif
			if statusC = STANDBY and transitionC = OPERATE_T then
				par
					r_setLightsStatus[RED, RED, BLOCKED, BLOCKED]
					statusC := OPERATE
					statusCOperate := BLOCKED_A
				endpar
			endif
			if (statusC = OPERATE) then
				if transitionC = STANDBY_T then
					par
						r_setLightsStatus[BLINK_YELLOW, BLINK_YELLOW, ATTENTION, ATTENTION]
						statusC := STANDBY
					endpar
				else
					r_operateSubState[]
				endif	
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