asm TrafficLight_4
// ANGELO
 
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
	controlled statusC: ControllerStatus
	controlled statusCOperate : ControllerSubStatusOperate
	monitored transitionC: ControllerTransition
	// lights
	controlled lightsA: Lights // traffic light A
	controlled lightsB: Lights // traffic light B
	controlled statusA: LightStatus
	controlled statusB: LightStatus	
	monitored transitionA: LightTransition 
	monitored transitionB: LightTransition 


definitions:
	// traffic light
	// in event li traduco con regole ogni evento in una regola 
	// entry action con un update opportuno
	// switchOn	
	rule r_raise_switchOn($s in LightStatus, $l in Lights) =
		if $s = OFF then 
			par 
				$s := ATTENTION
				//entryAttention
				$l:= BLINK_YELLOW
			endpar		
		endif 
	// switchOff	
	rule r_raise_switchOff($s in LightStatus, $l in Lights) =
		if $s = ATTENTION or  $s = BLOCKED or $s = RELEASED or $s = PREPARE_BLOCK then 
			par 
				$s := OFF
				//entryOFF
				$l:= ALL_OFF
			endpar		
		endif 
	// attention
	rule r_raise_attention($s in LightStatus, $l in Lights) =
	// spec originale yakindu
	//if $s = BLOCKED then 
	if $s = BLOCKED or $s = RELEASED or $s = PREPARE_BLOCK then
		par 
			$s := ATTENTION
			//entryAttention
			$l:= BLINK_YELLOW
		endpar		
	endif 

	// eventi che il controllore subisce (raised by the traffic light)
	rule r_raise_blocked =  
		par
			if  statusCOperate = RELEASED_B and statusC = OPERATE then statusCOperate := BLOCKED_A endif
			if  statusCOperate = RELEASED_A and statusC = OPERATE then statusCOperate := BLOCKED_B endif
		endpar	
	
	rule r_raise_released = 
		par
			if  statusCOperate = RELEASE_A and statusC = OPERATE then statusCOperate := RELEASED_A endif
			if  statusCOperate = RELEASE_B and statusC = OPERATE then statusCOperate := RELEASED_B endif
		endpar	



	// block
	rule r_raise_block($s in LightStatus, $l in Lights) =
	if $s = ATTENTION then 
		par 
			$s := BLOCKED
			//entryBlocked
			$l:= RED
			// raise the event 
			r_raise_blocked[]
		endpar		
	endif 
	// release
	rule r_raise_release($s in LightStatus, $l in Lights) =
	if $s = BLOCKED then 
		par 
			$s := RELEASED
			//entryRELEASED
			$l:= GREEN
		endpar		
	endif 
	// le transizioni interne invece le metto come regole a s�
	rule r_TrafficLight($l in Lights, $s in LightStatus, $t in LightTransition) =
		par
			if $s = RELEASED and transitionC != STANDBY_T and $t = RELEASE_PERIOD then par
					$s := PREPARE_BLOCK
					// entry prepare block
					$l:= YELLOW 
					// raise the event (moved from entry to exit)
					r_raise_released[]					
					endpar endif 
			if $s = PREPARE_BLOCK and  transitionC != STANDBY_T and $t = PREPARE_PERIOD then par
					$s := BLOCKED
					// entry blocked
					$l:= RED
					// raise the event 
					r_raise_blocked[]  endpar endif 
		endpar
	// controller
	// 	 in event invece sono delle monitorate, quindi 	
	rule r_Controller =
	par
	// OFF -> STANDBY
	if statusC = CONTR_OFF and transitionC = TURN_ON then
		par 
			statusC := STANDBY
			// exit
			r_raise_switchOn[statusA,lightsA]
			r_raise_switchOn[statusB,lightsB]
			// entry
			r_raise_attention[statusA,lightsA]
			r_raise_attention[statusB,lightsB]
		endpar endif
	// STANDBY -> OPERATE	 
	if statusC = STANDBY and transitionC = OPERATE_T then
		par
			statusC := OPERATE
			statusCOperate := BLOCKED_A
			r_raise_block[statusA,lightsA]
			r_raise_block[statusB,lightsB]
		endpar endif
	
	if statusC = OPERATE and transitionC != STANDBY_T then
		// internal conditions 
		par 
		// BLOCKED_A -> release A
		if statusCOperate = BLOCKED_A and transitionC = SAFE_PERIOD then
		par
			statusCOperate := RELEASE_A
			r_raise_release[statusA,lightsA]
		endpar endif
		if statusCOperate = BLOCKED_B and transitionC = SAFE_PERIOD then
		par
			statusCOperate := RELEASE_B
			r_raise_release[statusB,lightsB]
		endpar endif
	endpar endif
	// 
	if statusC = OPERATE and transitionC = STANDBY_T then
		// be careful, stand by has precedence
		par
			statusC := STANDBY
			// entry
			r_raise_attention[statusA,lightsA]
			r_raise_attention[statusB,lightsB]
		endpar endif
	// switch off
	if statusC = STANDBY and transitionC = TURN_OFF then
		par
			statusC := CONTR_OFF
			// entry
			r_raise_switchOff[statusA,lightsA]
			r_raise_switchOff[statusB,lightsB]
		endpar endif		
	endpar

// The traffic light can be turned off 
	CTLSPEC ag(statusC = STANDBY implies (statusA = ATTENTION and statusB = ATTENTION) )	
	
	main rule r_Main =
		par
			r_TrafficLight[lightsA, statusA, transitionA]
			r_TrafficLight[lightsB, statusB, transitionB]
			r_Controller[]
		endpar


// INITIAL STATE
default init s0:
	function lightsA = ALL_OFF
	function lightsB = ALL_OFF
	function statusC = CONTR_OFF
	function statusCOperate = BLOCKED_A
	function statusA = OFF
	function statusB = OFF