asm MVMcontroller03
//Third refinement: transition from inspiration to expiration and vice versa 

import CTLlibrary
import LTLlibrary
import TimeLibrary 

signature:
	//*************************************************
	// DOMAINS
	//*************************************************
	enum domain States = {FIRST | STARTUP | SELFTEST | VENTILATIONOFF | PCV_STATE | PSV_STATE}

	enum domain Modes = {PCV | PSV}
	
	enum domain Ventilation = {INSPIRATION | EXPIRATION | INPAUSE | EXPAUSE | RM}

	enum domain ValveStatus = {OPEN | CLOSED}
	
	enum domain Watchst = {INACTIVE | BREATHON | ALARM} //watchdogs bits status
	
	enum domain BreathSync = {EXP | INSP} 

	enum domain Reply = {RESPONSE | ERROR | NORESPONSE}

	enum domain StatusSelf = {NOTSTART | PERFORMING | ENDED}
	
	enum domain Alarm = {NONE | LOW | MEDIUM | HIGH}
	
	domain Num subsetof Integer
	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored startupEnded: Boolean
	dynamic monitored selfTestPassed: Boolean
	dynamic monitored startVentilation: Boolean
	dynamic monitored stopRequested: Boolean
	dynamic monitored respirationMode: Modes

	dynamic controlled state: States
	
	dynamic monitored flowDropPSV: Boolean //PSV.31
	dynamic controlled stopVentilation: Boolean
	
	dynamic out iValve: ValveStatus
	dynamic out oValve: ValveStatus
	dynamic controlled phase: Ventilation
		
	dynamic monitored cmdExPause: Boolean
	dynamic monitored cmdInPause: Boolean
	dynamic monitored cmdRm: Boolean
	
	dynamic monitored watchdog_st: Watchst  
	dynamic monitored status_selftest: StatusSelf
	
	dynamic controlled apneaBackupMode: Boolean
	
	dynamic monitored pawGTMaxPinsp: Boolean
	dynamic monitored dropPAW_ITS: Boolean
	
	/*
	 adc_reply=true;
	 fan_working=true;
	 pi_6=25;
	 pi_6_reply=true;
	 temperature=25;  
	 */
	
	dynamic monitored adc_reply_m: Reply
	dynamic monitored fan_working_m: Boolean
	dynamic monitored pi_6_m: Num
	dynamic monitored pi_6_reply_m: Reply
	dynamic monitored temperature_m: Num
	
	dynamic monitored al_bit: Alarm
	dynamic out all_cont: Alarm
	dynamic out snooze: Boolean
	
	dynamic out adc_reply: Reply
	dynamic out fan_working: Boolean
	dynamic out pi_6: Num
	dynamic out pi_6_reply: Reply
	dynamic out temperature: Num
	
	dynamic out mCurrSecs: Integer
	
	dynamic out breath_sync: BreathSync
	dynamic out watchdog: Boolean
	dynamic out enter_self: Boolean 
	dynamic out exit_self: Boolean
	
	dynamic out run_command: Boolean
	dynamic out stop_command: Boolean
	
	static timerInspirationDurPCV: Timer
	static timerExpirationDurPCV: Timer
	static timerMaxInspTimePSV: Timer //PSV.4
	static timerMinInspTimePSV: Timer
	static timerMinExpTimePSV: Timer	
	
	static timerMaxInPause: Timer
	static timerMaxRmTime: Timer
	static timerMaxExPause: Timer
	static timerApneaLag: Timer

	static timerTriggerWindowDelay: Timer
	
definitions:
	domain Num = {-1000:1000}

	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	rule r_startup = 
		if startupEnded then	//REQ.1.1 
			par
				state := SELFTEST 
				//watchdog := true //SUM.4
				enter_self := true
			endpar
		endif		

	//REQ.4.1	
	rule r_selftest =	
		if selfTestPassed then 
			par
					state := VENTILATIONOFF 
					exit_self := true
			endpar
		endif
		
	rule r_stopVent = 
		par
			state := VENTILATIONOFF //REQ.8
			stopVentilation := false  //iValve := CLOSED //oValve := OPEN CMM.1
			stop_command := true
			run_command := false
		endpar
	
	//set phase and valves state when inspiration
	rule r_inspPhase = 
		par
			breath_sync := INSP
			phase := INSPIRATION
			iValve := OPEN //CMM.2
			oValve := CLOSED
		endpar
	
	//set phase and valves state when expiration
	rule r_expPhase = //CMM.3
		par
			breath_sync := EXP
			phase := EXPIRATION
			iValve := CLOSED 
			oValve := OPEN
		endpar
			
	rule r_PCVStartInsp =
		par
			r_inspPhase[]
			r_reset_timer[timerInspirationDurPCV] 
		endpar
	
	rule r_PCVStartExp =
		par
			r_expPhase[]
			r_reset_timer[timerExpirationDurPCV]
			r_reset_timer[timerTriggerWindowDelay]
		endpar
	
	rule r_PCV = //PCV.2
		par
			state := PCV_STATE
			r_PCVStartInsp[]
		endpar
	
	rule r_PSVStartInsp =
		par
			r_inspPhase[]
			r_reset_timer[timerMinExpTimePSV]
			r_reset_timer[timerMaxInspTimePSV]
		endpar
		
	rule r_PSVStartExp =
		par
			r_expPhase[]
			r_reset_timer[timerMinExpTimePSV]
			r_reset_timer[timerApneaLag]
			r_reset_timer[timerTriggerWindowDelay]
		endpar
		
	rule r_PSV = //PSV.2
		par
			state := PSV_STATE
			r_PSVStartInsp[]
		endpar
	
	rule r_runApnea = //REQ.9.1  PSV.9
		par
			state := PCV_STATE
			r_PCVStartInsp[]
			apneaBackupMode := true
		endpar
	
	//When in PCV because of apnea, reset the variable when the user goes back to PSV voluntary 	
	rule r_resetApneaBackup =
		if apneaBackupMode then
			apneaBackupMode := false
		endif
			
	rule r_ventilationoff =
		if startVentilation	then
			par
				run_command := true
				stop_command := false
				if respirationMode = PCV	then r_PCV[] endif //REQ.6
				if respirationMode = PSV	then r_PSV[] endif //REQ.5
			endpar
		endif
	
	//Inspiratory Pause
	rule r_InPause =
		par
			phase := INPAUSE
			iValve := CLOSED 
			r_reset_timer[timerMaxInPause]
		endpar

	//Recruitment Maneuver
	rule r_rm =
		par
			phase := RM
			r_reset_timer[timerMaxRmTime]
		endpar
	
	//Expiratory Pause
	rule r_exPause =
		par
			phase := EXPAUSE
			oValve := CLOSED 
			r_reset_timer[timerMaxExPause]
		endpar
			
	rule r_runPCVInsp =	
		par
			//if stop ventilation is requested store the request if not already requested
			if not stopVentilation then
				if stopRequested then
					stopVentilation := true 
				endif
			endif
			//when inspiration duration expires go to expiration based on selected mode
			if expired(timerInspirationDurPCV)	then
				par
					if respirationMode = PCV then
						//in pause has precedence over rm and rm has precedence over expiration
						if cmdInPause then //CMM.4
							r_InPause[]
						else if cmdRm then //PCV.5 //CMM.6
							r_rm[]
						else
							r_PCVStartExp[]
						endif endif 
					endif
					if respirationMode = PSV then
						par
							state := PSV_STATE //REQ.7
							r_PSVStartExp[]
							r_resetApneaBackup[]
						endpar
					endif
				endpar
			else if pawGTMaxPinsp then //CMM.7
				r_PCVStartExp[]
			endif endif
		endpar
	
		
	//PCV expiration
	rule r_runPCVExp =	
		//if stop ventilation go to ventilation off immediately
		if stopVentilation then
			r_stopVent[] //REQ.8
		else 
			if stopRequested then
				r_stopVent[]
			else
			//when expiration duration is expired go to inspiration
				if expired(timerExpirationDurPCV) then
					//ex pause has precedence over inspiration
					if cmdExPause then //PCV.8 //CMM.5
						r_exPause[]
					else
						r_PCVStartInsp[] //PCV.6
					endif	
				else if expired(timerTriggerWindowDelay) and dropPAW_ITS then //PCV.7 CMM.9
					r_PCVStartInsp[]
				endif endif 	
			endif 
		endif
	
	rule r_runInPause = 
	 	if ((not cmdInPause) or expired(timerMaxInPause)) then //CMM.4.2
			par
				if state = PCV_STATE then
					r_PCVStartExp[]
				endif
				if state = PSV_STATE then
					r_PSVStartExp[]
				endif
			endpar
		endif
	
	rule r_runRm = 
		if (not cmdRm) or expired(timerMaxRmTime) then //CMM.6.1 //CMM.6.2
			par
				if state = PCV_STATE then
					r_PCVStartExp[]
				endif
				if state = PSV_STATE then
					r_PSVStartExp[]
				endif
			endpar
		endif
	
	rule r_runExPause = 
		if (not cmdExPause) or expired(timerMaxExPause) then //CMM.5.2
			par
				if state = PCV_STATE then
					r_PCVStartInsp[]
				endif
				if state = PSV_STATE then
					r_PSVStartInsp[]
				endif
			endpar
		endif
		
		
	//PCV mode					
	rule r_runPCV =
		par
			if phase = INSPIRATION then
				r_runPCVInsp[]
			endif
			if phase = EXPIRATION then
				r_runPCVExp[]
			endif
			if phase = INPAUSE then
				r_runInPause[]
			endif
			if phase = RM then
				r_runRm[]
			endif
			if phase = EXPAUSE then
				r_runExPause[]
			endif
		endpar
	
	//PSV inspiration
	rule r_runPSVInsp =	
		par
			//if stop ventilation is requested store the request if not already requested
			if not stopVentilation then
				if stopRequested then
					stopVentilation := true
				endif
			endif
			//when min inspiration time is expired and flow drop or max expiration time is expired go to expiration
			if (expired(timerMinInspTimePSV) and flowDropPSV) or expired(timerMaxInspTimePSV) then //PSV.4 PSV.5
				//in pause has precedence over rm and rm has precedence over expiration
				if cmdInPause then //PSV.6 CMM.4
					r_InPause[]
				else if cmdRm then  //CMM.6
					r_rm[]
				else
					r_PSVStartExp[]
				endif endif
			else if pawGTMaxPinsp then //CMM.7
				r_PSVStartExp[]
			endif endif 
		endpar
	
	
	//PSV expiration	
	rule r_runPSVExp =	
		//if stop ventilation go to ventilation off immediately
		if stopVentilation then //REQ.10
			r_stopVent[]
		else 
			if stopRequested then
				r_stopVent[]
		else
		//when min expiration time expires go to inspiration based on selected mode
			if expired(timerTriggerWindowDelay) and dropPAW_ITS then //PSV.8.1
				r_PSVStartInsp[]
			else if expired(timerApneaLag) then
				r_runApnea[] //PSV.9
			else 
			if expired(timerMinExpTimePSV) then //CMM.9
				par
					if respirationMode = PCV then //REQ.9.2
						par
							state := PCV_STATE 
							r_PCVStartInsp[]
						endpar
					endif
					if respirationMode = PSV then
						//ex pause has precedence over inspiration
						if cmdExPause then //PSV.8.2 //CMM.5
							r_exPause[]
						endif
					endif
				endpar
			endif
		endif	endif	endif endif
	
	//PSV mode
	rule r_runPSV =
		par
			if phase = INSPIRATION then
				r_runPSVInsp[]
			endif
			if phase = EXPIRATION then
				r_runPSVExp[]
			endif
			if phase = INPAUSE then
				r_runInPause[]
			endif
			if phase = RM then
				r_runRm[]
			endif
			if phase = EXPAUSE then
				r_runExPause[]
			endif
		endpar
		
		
		rule r_comm =
			par
				adc_reply := adc_reply_m 
		    	fan_working := fan_working_m 
				pi_6 := pi_6_m  
				pi_6_reply := pi_6_reply_m
				temperature := temperature_m
				mCurrSecs := mCurrTimeSecs
			endpar
			
	//*************************************************
	// Property Verification
	//*************************************************
 	//safety: valves are never both open or both closed at the same time
	//LTLSPEC not f(iValve=CLOSED and oValve=CLOSED)
	//LTLSPEC not f(iValve=OPEN and oValve=OPEN)
	//LTLSPEC not f(iValve=oValve)
	//when ventilation is off, out valve is open and in valve is closed
	LTLSPEC g(state=VENTILATIONOFF implies (iValve=CLOSED and oValve=OPEN))
	//when ventilation is in inspiration, in valve is open and out valve is closed
	LTLSPEC g((phase=INSPIRATION and (state = PCV_STATE or state = PSV_STATE)) implies (iValve=OPEN and oValve=CLOSED))
	//when ventilation is in expiration, out valve is open and in valve is closed
	LTLSPEC g((phase=EXPIRATION and (state = PCV_STATE or state = PSV_STATE)) implies (iValve=CLOSED and oValve=OPEN))
	//when ventilation is in in pause or ex pause, valves are closed
	LTLSPEC g(((phase=INPAUSE or phase=EXPAUSE) and (state = PCV_STATE or state = PSV_STATE)) implies (iValve=CLOSED and oValve=CLOSED))
	//when ventilation is in rm, in valve is open and out valve is closed
	LTLSPEC g((phase=RM and (state = PCV_STATE or state = PSV_STATE)) implies (iValve=OPEN and oValve=CLOSED))
	//both valves closed only happens during pauses
	LTLSPEC g((iValve=CLOSED and oValve=CLOSED) implies ((phase=INPAUSE or phase=EXPAUSE) and (state = PCV_STATE or state = PSV_STATE)))

	//*************************************************
	// MAIN Rule
	//*************************************************
	main rule r_Main =
		par
			//if mCurrTimeSecs>0 then skip endif
			//transition from startup to selftest
			
			if state = FIRST then
				par
					watchdog := true
					state := STARTUP
					//ADDED BECAUSE OF LAZY EVALUATION
					all_cont := NONE
				endpar
			endif
			
			if state = STARTUP then
					r_startup[]
			endif
		 
			//transition from selftest to ventilation off
			if state = SELFTEST then
				r_selftest[]
			endif
	
			//start ventilation, either pcv or psv
			if state = VENTILATIONOFF then
				r_ventilationoff[]
			endif
	
			//transition from PCV to ventilation off if requested
			if state = PCV_STATE then
				r_runPCV[]
			endif
	
			//transition from PSV to ventilation off if requested
			if state = PSV_STATE	then
				r_runPSV[]
			endif
			
			r_comm[]
			
		endpar

//SUM.1
default init s0:
//controlled functions
	function state = FIRST
	function phase = INSPIRATION
	
	//REQ.1.2 //CMM.1
	function iValve = CLOSED
	function oValve = OPEN
	
	function stopVentilation = false 
	function flowDropPSV = false
		
	function startupEnded = false
	function selfTestPassed = false
	function startVentilation = false
	function stopRequested = false
	function respirationMode  = PCV	
	
	function cmdExPause = false
	function cmdInPause = false
	function cmdRm = false
	function apneaBackupMode = false
		
	function pawGTMaxPinsp = false
	function dropPAW_ITS = false
		
	function start($t in Timer) = currentTime($t)
	function duration($t in Timer) = switch $t
			case timerInspirationDurPCV: 2 //set by the doctor RR 10 and I:E 0,5
	    	case timerExpirationDurPCV: 4 //set by the doctor RR 10 and I:E 0,5
			case timerMaxInspTimePSV: 7
			case timerMinExpTimePSV: 2	//half of the last_inspiration_time. Shall be in the interval [0.4 : 2] sec. //PSV.8.3
			case timerMinInspTimePSV: 1	//half of the last_inspiration_time. Shall be in the interval [0.4 : 2] sec.
			case timerMaxInPause: 10
			case timerMaxRmTime: 10
			case timerMaxExPause: 10
			case timerApneaLag: 4
			case timerTriggerWindowDelay: 1
		endswitch

	function enter_self = false	
	function exit_self = false	
	function breath_sync = EXP	
	function run_command = false	
	function stop_command = false	
	
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
	
	function al_bit = NONE
	function all_cont = NONE
	function snooze = false
	
	function status_selftest = NOTSTART
	function watchdog_st = INACTIVE
