asm TrafficLight_0
//The controller has three states: OFF, STANDBY and OPERATE
//In state OFF  traffic lights are OFF
//In state STANDBY traffic lights are YELLOW and BLINK
//In state OPERATE traffic lights are RED
//Each traffic lights state corresponds to a traffic light color:
//OFF -> ALL_OFF
//RED -> BLOCKED
//BLINK_YELLOW -> ATTENTION 


import ../StandardLibrary
import ../CTLlibrary

signature:
	// DOMAINS
	enum domain ControllerStatus = {CONTR_OFF | STANDBY | OPERATE}
	enum domain ControllerTransition = {TURN_ON | TURN_OFF | OPERATE_T | STANDBY_T}
	enum domain Lights = {NONE}
	enum domain LightStatus = {OFF | ATTENTION | BLOCKED}
	// FUNCTIONS
	controlled lightsA: Lights // traffic light A
	controlled lightsB: Lights // traffic light B
	controlled statusC: ControllerStatus
	monitored transitionC: ControllerTransition
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
				endpar
			endif
		endpar	
	
	rule r_Operate =
		if transitionC = STANDBY_T then
			par
				r_setAttention[]
				statusC := STANDBY
			endpar
		endif	

// The traffic light can always be turned off 
	CTLSPEC ag((statusC = OPERATE or statusC = STANDBY) implies ef(statusC = CONTR_OFF))
// When controller is off traffic lights are off
	CTLSPEC ag((statusC = CONTR_OFF) implies (statusA = OFF and statusB = OFF))
// When controller is standby traffic lights are in attention
	CTLSPEC ag((statusC = STANDBY) implies (statusA = ATTENTION and statusB = ATTENTION))
// The lights are always NONE
	CTLSPEC ag(lightsA=NONE and lightsB=NONE)
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
	function lightsA = NONE
	function lightsB = NONE
	function statusC = CONTR_OFF
	function statusA = OFF
	function statusB = OFF
