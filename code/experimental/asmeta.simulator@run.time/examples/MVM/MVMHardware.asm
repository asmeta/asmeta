asm MVMHardware
//Third refinement: transition from inspiration to expiration and vice versa 

import CTLLibrary
import LTLLibrary
import TimeLibrary 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	
	enum domain ValveStatus = {OPEN | CLOSED}
	
	enum domain Reply = {RESPONSE | ERROR | NORESPONSE}

	enum domain StatesSup = {STARTUP | INIT | SELFTEST | VENTILATIONON | VENTILATIONOFF | FAILSAFE}
	
	enum domain Watchst = {INACTIVE | BREATHON | ALARM} //watchdogs bits status
	
	//*************************************************
	// FUNCTIONS
	//*************************************************

	
	dynamic monitored iValve: ValveStatus //con
	dynamic monitored oValve: ValveStatus //con
	dynamic monitored insp_valve: ValveStatus //sup
	dynamic monitored exp_valve: ValveStatus //sup
	
	dynamic monitored adc_reply_m: Reply
	dynamic monitored fan_working_m: Boolean
	dynamic monitored pi_6_m: Integer
	dynamic monitored pi_6_reply_m: Reply
	dynamic monitored temperature_m: Integer
	
	dynamic monitored state: StatesSup //sup state
	dynamic out watchdog_st: Watchst //It indicates the state of the supervisor to the controller
	
	
	dynamic out adc_reply: Reply
	dynamic out fan_working: Boolean
	dynamic out pi_6: Integer
	dynamic out pi_6_reply: Reply
	dynamic out temperature: Integer
	
	dynamic controlled iValveMerged: ValveStatus
	dynamic controlled oValveMerged: ValveStatus
	
		
definitions:

	rule r_setValves =
		if state = VENTILATIONON and watchdog_st = BREATHON then
				par
					iValveMerged := iValve
					oValveMerged := oValve
				endpar
		else
			par
				iValveMerged := insp_valve
				oValveMerged := exp_valve
			endpar
		endif
	
	//*************************************************
	// MAIN Rule
	//*************************************************
	main rule r_Main =
		par
			adc_reply := adc_reply_m
			fan_working := fan_working_m
			pi_6 := pi_6_m
			pi_6_reply :=pi_6_reply_m
			temperature := temperature_m
			r_setValves[]
		endpar
		

//SUM.1
default init s0:
//controlled functions

	//REQ.1.2 //CMM.1
	function iValve = CLOSED
	function oValve = OPEN

	function adc_reply = RESPONSE
	function fan_working = true
	function pi_6 = 25
	function pi_6_reply = RESPONSE
	function temperature = 25
	
	function adc_reply_m = RESPONSE
	function fan_working_m = true
	function pi_6_m = 25
	function pi_6_reply_m = RESPONSE
	function temperature_m = 25

	