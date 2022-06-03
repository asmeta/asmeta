asm mvm0

/*
	Only the PCV mode is implemented in this model
*/

import StandardLibrary

signature:

	// TIMER
	enum domain Timer = {TIMER_INSPIRATION_DURATION_MS, TIMER_EXPIRATION_DURATION_MS, TIMER_TRIGGERWINDOWDELAY_MS}

	domain MyTime subsetof Integer

	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain States = {MAIN_REGION_STARTUP | MAIN_REGION_SELFTEST | MAIN_REGION_VENTILATIONOFF | MAIN_REGION_PCV_R1_INSPIRATION | MAIN_REGION_PCV_R1_EXPIRATION | OFF}

	enum domain Modes = {PCV}

	enum domain ValveStatus = {OPEN | CLOSED}

	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored poweroff: Boolean
	dynamic monitored startupEnded: Boolean
	dynamic monitored selfTestPassed: Boolean
	dynamic monitored resume: Boolean
	dynamic monitored startVentilation: Boolean
	dynamic monitored stopVentilation: Boolean
	dynamic monitored mode: Modes
	dynamic monitored pawGTMaxPinsp: Boolean
	dynamic monitored dropPAW_ITS_PCV: Boolean

	dynamic controlled time: MyTime
	
	dynamic controlled stopVentilationRequested: Boolean
	dynamic controlled state: States
	dynamic controlled iValve: ValveStatus
	dynamic controlled oValve: ValveStatus

	// starting time taken from the local clock
	controlled start: Timer-> MyTime
	
	static durationTIMER_INSPIRATION_DURATION_MS : MyTime
	static durationTIMER_EXPIRATION_DURATION_MS: MyTime
	static durationTIMER_TRIGGERWINDOWDELAY_MS : MyTime

	derived expiredTIMER_INSPIRATION_DURATION_MS : Boolean
	derived expiredTIMER_EXPIRATION_DURATION_MS: Boolean
	derived expiredTIMER_TRIGGERWINDOWDELAY_MS : Boolean
													
definitions:

	domain MyTime = {0:600}		
		
	function durationTIMER_INSPIRATION_DURATION_MS 	=  2 	//settato dal dottore. valore indicativo per respiratory rate 10 e I:E 0,5
    	function durationTIMER_EXPIRATION_DURATION_MS =  4 	//settato dal dottore. valore indicativo
	function durationTIMER_TRIGGERWINDOWDELAY_MS =  1 	// 0.7
	
	/*******************************************************/	
	function expiredTIMER_INSPIRATION_DURATION_MS  = (time >= start(TIMER_INSPIRATION_DURATION_MS) + durationTIMER_INSPIRATION_DURATION_MS)
	function expiredTIMER_EXPIRATION_DURATION_MS = (time >= start(TIMER_EXPIRATION_DURATION_MS) + durationTIMER_EXPIRATION_DURATION_MS)
	function expiredTIMER_TRIGGERWINDOWDELAY_MS  = (time >= start(TIMER_TRIGGERWINDOWDELAY_MS) + durationTIMER_TRIGGERWINDOWDELAY_MS)
	
	/*******************************************************/
	// restart the TIMER
	macro rule r_reset_TIMER($t in Timer) =	start($t) := time
	
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	
	rule r_startupEnded =
			state := MAIN_REGION_SELFTEST

	rule r_ventOffRequested =
			stopVentilationRequested := true

	rule r_ventOffPCV =
		par
			state := MAIN_REGION_VENTILATIONOFF
			stopVentilationRequested := false
		endpar
		
	rule r_ventOffFirstTime =
		state := MAIN_REGION_VENTILATIONOFF

	rule r_turnOff =
		par
			iValve := CLOSED
			oValve := OPEN
			state := OFF
		endpar

	rule r_PCVinsp =
		par
			state := MAIN_REGION_PCV_R1_INSPIRATION
			iValve := OPEN
			r_reset_TIMER[TIMER_INSPIRATION_DURATION_MS]
		endpar
		
	rule r_PCVinspOValve =
		par
			r_PCVinsp[]
			oValve := CLOSED
		endpar
		
	rule r_PCVexp =
		par
			state := MAIN_REGION_PCV_R1_EXPIRATION 
			oValve := OPEN
			r_reset_TIMER[TIMER_EXPIRATION_DURATION_MS]
			r_reset_TIMER[TIMER_TRIGGERWINDOWDELAY_MS]
		endpar
			
	rule r_PCVexpIValve =
		par
			r_PCVexp[]
			iValve := CLOSED		
		endpar

	
	//*************************************************
	// MAIN Rule
	//*************************************************
	main rule r_Main =
		par 
			time:= time+1
		
			//if the button is pressed, shut down the ventilator
			if poweroff then r_turnOff[]
			
			//if stop of the ventilation is requested and current state is an expiration
			//go to state MAIN_REGION_VENTILATIONOFF immediately
			else 
				if state=MAIN_REGION_PCV_R1_EXPIRATION and (stopVentilationRequested or stopVentilation) then 
					r_ventOffPCV[]
				else		
					par
						//if ventilation stop is requested and ventilation is on, 
						//store the stop request in controlled function stopVentilationRequested
						if stopVentilation then
							if state!=MAIN_REGION_STARTUP and state!=MAIN_REGION_SELFTEST and state!=MAIN_REGION_VENTILATIONOFF then r_ventOffRequested[] endif
						endif
				
						//transition from startup to selftest
						if state = MAIN_REGION_STARTUP then
							if startupEnded then r_startupEnded[] endif
						endif
		
						//transition from selftest to ventilation off
						if state = MAIN_REGION_SELFTEST then
							if (selfTestPassed or resume) then r_ventOffFirstTime[] endif
						endif
		
						//start ventilation, in PCV mode
						if state = MAIN_REGION_VENTILATIONOFF then
							if startVentilation	then
								if mode = PCV then r_PCVinspOValve[]endif
							endif
						endif
		
						//transition from inspiration to expiration
						if state = MAIN_REGION_PCV_R1_INSPIRATION then
							if expiredTIMER_INSPIRATION_DURATION_MS then
								if mode = PCV then 
								 	r_PCVexpIValve[]
								endif
							else 
								if pawGTMaxPinsp then 
									r_PCVexpIValve[] 
								endif
							endif
						endif
					
						if state = MAIN_REGION_PCV_R1_EXPIRATION then
							if expiredTIMER_EXPIRATION_DURATION_MS then 
								r_PCVinspOValve[] 
							else 
								if expiredTIMER_TRIGGERWINDOWDELAY_MS then
									if dropPAW_ITS_PCV then 
										r_PCVinspOValve[] 
									endif
								endif
							endif
						endif				
					endpar
				endif
			endif
		endpar

default init s0:
	function time = 0
	function state = MAIN_REGION_STARTUP
	function iValve = CLOSED
	function oValve = OPEN
	function stopVentilationRequested = false	
	function start($t in Timer) = 0