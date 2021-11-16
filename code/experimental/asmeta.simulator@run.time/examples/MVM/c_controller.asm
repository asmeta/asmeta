asm c_controller
	enum domain Ventilation = {INSPIRATION | EXPIRATION | INPAUSE | EXPAUSE | RM}

	enum domain ValveStatus = {OPEN | CLOSED}

	enum domain BreathSync = {EXP | INSP}

	//*************************************************
	// FUNCTIONS
	//*************************************************
	dynamic monitored startupEnded: Boolean
	dynamic monitored selfTestPassed: Boolean
	dynamic monitored startVentilation: Boolean
	dynamic monitored stopRequested: Boolean
	dynamic monitored respirationMode: Modes

	dynamic controlled state: States
	
	dynamic monitored flowDropPSV: Boolean
	dynamic controlled stopVentilation: Boolean
	
	dynamic controlled iValve: ValveStatus
	dynamic controlled oValve: ValveStatus
	dynamic controlled phase: Ventilation
		
	dynamic monitored cmdExPause: Boolean
	dynamic monitored cmdInPause: Boolean
	dynamic monitored cmdRm: Boolean
	
	dynamic controlled apneaBackupMode: Boolean
	
	dynamic monitored pawGTMaxPinsp: Boolean
	dynamic monitored dropPAW_ITS: Boolean
	
	dynamic out watchdog: Boolean
	dynamic out enter_self: Boolean
	dynamic out exit_self: Boolean
	dynamic out run_command: Boolean
	dynamic out stop_command: Boolean
	dynamic out breath_sync: BreathSync
	
	static timerInspirationDurPCV: Timer
	static timerExpirationDurPCV: Timer
	static timerMaxInspTimePSV: Timer
	static timerMinInspTimePSV: Timer
	static timerMinExpTimePSV: Timer	
	
	static timerMaxInPause: Timer
	static timerMaxRmTime: Timer
	static timerMaxExPause: Timer
	static timerApneaLag: Timer

	static timerTriggerWindowDelay: Timer
	
definitions:
	//*************************************************
	// RULE DEFINITIONS
	//*************************************************
	
	rule r_startup =	
			if startupEnded then 
				par
					state := SELFTEST 
					enter_self := true
				endpar
			endif
		
		
	rule r_selftest =
		if selfTestPassed then 
			par
				state := VENTILATIONOFF 
				exit_self := true
			endpar	
		endif
		
	rule r_stopVent = 
		par
			state := VENTILATIONOFF
			stopVentilation := false
			run_command := false
			stop_command := true
		endpar
	
	//set phase and valves state when inspiration
	rule r_inspPhase = 
		par
			phase := INSPIRATION
			iValve := OPEN
			oValve := CLOSED
			breath_sync := INSP
		endpar
	
	//set phase and valves state when expiration
	rule r_expPhase = 
		par
			phase := EXPIRATION
			iValve := CLOSED 
			oValve := OPEN
			breath_sync := EXP
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
	
	rule r_PCV =
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
		
	rule r_PSV =
		par
			state := PSV_STATE
			r_PSVStartInsp[]
		endpar
	
	rule r_runApnea = 
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
				if respirationMode = PCV	then r_PCV[] endif
				if respirationMode = PSV	then r_PSV[] endif
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
						if cmdInPause then
							r_InPause[]
						else if cmdRm then
							r_rm[]
						else
							r_PCVStartExp[]
						endif endif 
					endif
					if respirationMode = PSV then
						par
							state := PSV_STATE
							r_PSVStartExp[]
							r_resetApneaBackup[]
						endpar
					endif
				endpar
			else if pawGTMaxPinsp then
				r_PCVStartExp[]
			endif endif
		endpar
	
		
	//PCV expiration
	rule r_runPCVExp =	
		//if stop ventilation go to ventilation off immediately
		if stopVentilation then
				r_stopVent[]
		else 
			if stopRequested then
					r_stopVent[]
			else
			//when expiration duration is expired go to inspiration
				if expired(timerExpirationDurPCV) then
					//ex pause has precedence over inspiration
					if cmdExPause then
						r_exPause[]
					else
						r_PCVStartInsp[]
					endif	
				else if expired(timerTriggerWindowDelay) and dropPAW_ITS then
					r_PCVStartInsp[]
				endif endif 	
			endif 
		endif
	
	rule r_runInPause = 
	 	if ((not cmdInPause) or expired(timerMaxInPause)) then
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
		if (not cmdRm) or expired(timerMaxRmTime) then
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
		if (not cmdExPause) or expired(timerMaxExPause) then
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
			if (expired(timerMinInspTimePSV) and flowDropPSV) or expired(timerMaxInspTimePSV) then
				//in pause has precedence over rm and rm has precedence over expiration
				if cmdInPause then
					r_InPause[]
				else if cmdRm then
					r_rm[]
				else
					r_PSVStartExp[]
				endif endif
			else if pawGTMaxPinsp then
				r_PSVStartExp[]
			endif endif 
		endpar
	
	
	//PSV expiration	
	rule r_runPSVExp =	
		//if stop ventilation go to ventilation off immediately
		if stopVentilation then
				r_stopVent[]
		else 
			if stopRequested then
					r_stopVent[]
		else
		//when min expiration time expires go to inspiration based on selected mode
			if expired(timerTriggerWindowDelay) and dropPAW_ITS then
				r_PSVStartInsp[]
			else if expired(timerApneaLag) then
				r_runApnea[]
			else 
			if expired(timerMinExpTimePSV) then
				par
					if respirationMode = PCV then
						par
							state := PCV_STATE
							r_PCVStartInsp[]
						endpar
					endif
					if respirationMode = PSV then
						//ex pause has precedence over inspiration
						if cmdExPause then
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
		//watchdog := true
		if mCurrTimeSecs>0 then skip endif
		
		//transition from startup to selftest
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

	endpar

default init s0:
//controlled functions
	function state = STARTUP
	function phase = INSPIRATION
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
			case timerMinExpTimePSV: 2	//half of the last_inspiration_time. Shall be in the interval [0.4 : 2] sec.
			case timerMinInspTimePSV: 1	//half of the last_inspiration_time. Shall be in the interval [0.4 : 2] sec.
			case timerMaxInPause: 10
			case timerMaxRmTime: 10
			case timerMaxExPause: 10
			case timerApneaLag: 4
			case timerTriggerWindowDelay: 1
		endswitch

	//function watchdog = false
	function enter_self = false	
	function exit_self = false
