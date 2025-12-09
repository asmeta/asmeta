asm SmartHome

//Adapted from "Self-Adaptation with End-User Preferences: Using Run-Time Models and Constraint Solving?"

//with managed agents

//different MAPE use different channels to communicate with rules doing the E

import STDL/StandardLibrary
import STDL/LTLLibrary
import STDL/CTLLibrary

signature:
	//managing agents that start the MAPE loops
	domain HouseManaging subsetof Agent
	domain RoomManaging subsetof Agent
	//managing agents that finish the MAPE loops
	domain HeaterManaging subsetof Agent
	domain WaterHeaterManaging subsetof Agent
	domain WindowManaging subsetof Agent
	
	//managed agents
	domain HeaterManaged subsetof Agent
	domain WaterHeaterManaged subsetof Agent
	domain WindowManaged subsetof Agent 

	enum domain HeaterStatus = {OFF | FAIRLY_HOT | VERY_HOT}
	enum domain WindowStatus = {OPEN | CLOSED}
	enum domain WaterHeaterStatus = {WE_ON | WE_OFF}

	domain Temperature subsetof Integer
	enum domain Time = {EARLY_MORN | MORN_AFT | EVENING}

	monitored esAdaptHeater: Boolean // used to nondeterministically choose between two adaptations actions

	//shared between managed and managing agents
	dynamic controlled setWaterHeaterStatus: WaterHeaterStatus
	dynamic controlled setHeaterStatus: HeaterStatus
	dynamic controlled setWindowStatus: WindowStatus

	//Knowledge - sensors
	dynamic monitored timePassed: Boolean
	dynamic monitored badAir: Boolean
	dynamic monitored roomTemp: Temperature
	//Knowledge - signals
	dynamic controlled sgnHeaterOFF_CH: Boolean
	dynamic controlled sgnHeaterFAIRLY_HOT_CH: Boolean
	dynamic controlled sgnHeaterFAIRLY_HOT_ES: Boolean
	dynamic controlled sgnHeaterVERY_HOT_CH: Boolean
	dynamic controlled sgnWaterHeaterON_MWH: Boolean
	dynamic controlled sgnWaterHeaterOFF_ES: Boolean
	dynamic controlled sgnOpenWindow_AQ: Boolean
	dynamic controlled sgnCloseWindow_AQ: Boolean
	dynamic controlled sgnCloseWindow_MD: Boolean
	//Knowledge - statuses of managed agents
	dynamic controlled waterHeaterStatus: WaterHeaterStatus
	dynamic controlled heaterStatus: HeaterStatus
	dynamic controlled windowStatus: WindowStatus

	//managing agents
	static houseManaging: HouseManaging
	static roomManaging: RoomManaging
	static windowManaging: WindowManaging
	static heaterManaging: HeaterManaging
	static waterHeaterManaging: WaterHeaterManaging
	
	//managed agents
	static windowManaged: WindowManaged
	static heaterManaged: HeaterManaged
	static waterHeaterManaged: WaterHeaterManaged

	dynamic controlled time: Time

	dynamic controlled monitor: Boolean
	derived monMAPE_CH: Boolean
	derived monMAPE_MD: Boolean
	derived monMAPE_AQ: Boolean
	derived monMAPE_MWH: Boolean
	derived monMAPE_ES: Boolean
	derived execMAPE_CH: Boolean
	derived execMAPE_MD: Boolean
	derived execMAPE_AQ: Boolean
	derived execMAPE_MWH: Boolean
	derived execMAPE_ES: Boolean
	
	derived checkForAdaptation: Boolean

definitions:
	domain Temperature = {0 : 35}
	function monMAPE_CH = monitor
	function monMAPE_MD = monitor
	function monMAPE_AQ = monitor
	function monMAPE_MWH = monitor
	function monMAPE_ES = monitor
	function execMAPE_CH = not monitor
	function execMAPE_MD = not monitor
	function execMAPE_AQ = not monitor
	function execMAPE_MWH = not monitor
	function execMAPE_ES = not monitor
	
	function checkForAdaptation = isUndef(setWaterHeaterStatus) and isUndef(setHeaterStatus) and isUndef(setWindowStatus)

	rule r_modifyHeaterStatus($newHeaterStatus in HeaterStatus, $signal in Boolean) =
		par
			setHeaterStatus := $newHeaterStatus
			$signal := false
		endpar

	rule r_modifyWaterHeaterStatus($newWaterHeaterStatus in WaterHeaterStatus, $signal in Boolean) =
		par
			setWaterHeaterStatus := $newWaterHeaterStatus
			$signal := false
		endpar

	rule r_modifyWindowStatus($newWindowStatus in WindowStatus, $signal in Boolean) =
		par
			setWindowStatus := $newWindowStatus
			$signal := false
		endpar

	rule r_adaptHeaterMAPE_CH =
		par
			if sgnHeaterVERY_HOT_CH then
				r_modifyHeaterStatus[VERY_HOT, sgnHeaterVERY_HOT_CH]
			endif
			if sgnHeaterFAIRLY_HOT_CH then
				r_modifyHeaterStatus[FAIRLY_HOT, sgnHeaterFAIRLY_HOT_CH]
			endif
			if sgnHeaterOFF_CH then
				r_modifyHeaterStatus[OFF, sgnHeaterOFF_CH]
			endif
		endpar

	rule r_adaptHeaterMAPE_ES =
		if sgnHeaterFAIRLY_HOT_ES then
			r_modifyHeaterStatus[FAIRLY_HOT, sgnHeaterFAIRLY_HOT_ES]
		endif

	rule r_adaptWindowMAPE_MD =
		if sgnCloseWindow_MD then
			r_modifyWindowStatus[CLOSED, sgnCloseWindow_MD]
		endif

	rule r_adaptWindowMAPE_AQ =
		par
			if sgnOpenWindow_AQ then
				r_modifyWindowStatus[OPEN, sgnOpenWindow_AQ]
			endif
			if sgnCloseWindow_AQ then
				r_modifyWindowStatus[CLOSED, sgnCloseWindow_AQ]
			endif
		endpar

	rule r_adaptWaterHeaterMAPE_MWH =
		if sgnWaterHeaterON_MWH then
			r_modifyWaterHeaterStatus[WE_ON, sgnWaterHeaterON_MWH]
		endif

	rule r_adaptWaterHeaterMAPE_ES =
		if sgnWaterHeaterOFF_ES then
			r_modifyWaterHeaterStatus[WE_OFF, sgnWaterHeaterOFF_ES]
		endif






	rule r_adaptHeater =
		par
			//MAPE_CH: when it is cold, the heaters should be at sufficient settings for comfort
			if execMAPE_CH then
				r_adaptHeaterMAPE_CH[] //@E_MAPE_CH
			endif
			//MAPE_ES: when the electricity is expensive do not use water heater and strong heating together
			if execMAPE_ES then
				r_adaptHeaterMAPE_ES[] //@E_MAPE_ES
			endif
		endpar

	rule r_adaptWindow =
		par
			//MAPE_MD: do not open windows (close the open windows) when the heater is on 
			if execMAPE_MD then
				r_adaptWindowMAPE_MD[] //@E_MAPE_MD
			endif
			//MAPE_AQ: do open window when air quality is bad
			if execMAPE_AQ then
				r_adaptWindowMAPE_AQ[] //@E_MAPE_AQ
			endif
		endpar

	rule r_adaptWaterHeater =
		par
			//MAPE_MWH: keep water heater on in the early morning
			if execMAPE_MWH then
				r_adaptWaterHeaterMAPE_MWH[] //@E_MAPE_MWH
			endif
			//MAPE_ES: when the electricity is expensive do not use water heater and strong heating together
			if execMAPE_ES then
				r_adaptWaterHeaterMAPE_ES[] //@E_MAPE_ES
			endif
		endpar








	//Starting rules of the MAPE loops

	//MAPE_CH: when it is cold, the heaters should be at sufficient settings for comfort
	rule r_checkRoomTempMAPE_CH =
		if roomTemp < 10 then
			sgnHeaterVERY_HOT_CH := true // send signal to set the heater to VERY_HOT
		else
			if roomTemp < 18 then
				sgnHeaterFAIRLY_HOT_CH := true // send signal to set the heater to FAIRLY_HOT
			else
				sgnHeaterOFF_CH := true // send signal to turn the heater off
			endif
		endif

	//MAPE_MD: do not open windows (close the open windows) when the heater is on 
	rule r_checkWindowAndHeaterMAPE_MD =
		if heaterStatus != OFF and windowStatus = OPEN then
			sgnCloseWindow_MD := true // send signal to close the window
		endif

	//MAPE_AQ: do open window when air quality is bad
	rule r_checkAirQualityMAPE_AQ =
		if badAir then
			sgnOpenWindow_AQ := true // send signal to open the window
		else
			sgnCloseWindow_AQ := true // send signal to close the window
		endif

	//MAPE_MWH: keep water heater on in the early morning
	rule r_checkHotWaterMorningMAPE_MWH =
		if time = EARLY_MORN then
			sgnWaterHeaterON_MWH := true // send signal to turn the water heater on
		endif

	//MAPE_ES: when the electricity is expensive do not use water heater and strong heating together
	rule r_checkElectrMAPE_ES =
		//electricity is expensive during the day
		if time = MORN_AFT then
			if heaterStatus = VERY_HOT and waterHeaterStatus = WE_ON then
				if esAdaptHeater then
					sgnHeaterFAIRLY_HOT_ES := true // send signal to decrease the heater
				else
					sgnWaterHeaterOFF_ES := true // send signal to turn the water heater off
				endif
			endif
		endif




	//Programs of the managing agents that start the MAPE loops
	rule r_monitorAndAnalyzeRoom =
		par
			if monMAPE_CH then
				r_checkRoomTempMAPE_CH[] //@MA_MAPE_CH
			endif
			if monMAPE_MD then
				r_checkWindowAndHeaterMAPE_MD[] //@MA_MAPE_MD
			endif
			if monMAPE_AQ then
				r_checkAirQualityMAPE_AQ[] //@MA_MAPE_AQ
			endif
		endpar

	rule r_monitorAndAnalyzeHouse =
		par
			if monMAPE_MWH then
				r_checkHotWaterMorningMAPE_MWH[] //@MA_MAPE_MWH
			endif
			if monMAPE_ES then
				r_checkElectrMAPE_ES[] //@MA_MAPE_ES
			endif
		endpar

	rule r_manageTime =
		if timePassed then
			switch time
				case EARLY_MORN:
					time := MORN_AFT
				case MORN_AFT:
					time := EVENING
				case EVENING:
					time := EARLY_MORN
			endswitch
		endif

	//managed agents
	rule r_waterHeater =
		if isDef(setWaterHeaterStatus) then
			par
				waterHeaterStatus := setWaterHeaterStatus //actuator
				setWaterHeaterStatus := undef
			endpar
		endif

	rule r_window =
		if isDef(setWindowStatus) then
			par
				windowStatus := setWindowStatus //actuator
				setWindowStatus := undef
			endpar
		endif

	rule r_heater =
		if isDef(setHeaterStatus) then
			par
				heaterStatus := setHeaterStatus //actuator
				setHeaterStatus := undef
			endpar
		endif


	// -- MAPE-k correctness verification --
	LTLSPEC g((	(checkForAdaptation and execMAPE_CH and sgnHeaterOFF_CH) or
				(checkForAdaptation and execMAPE_CH and sgnHeaterFAIRLY_HOT_CH) or
				(checkForAdaptation and execMAPE_CH and sgnHeaterVERY_HOT_CH) )
				implies
				o(	(checkForAdaptation and monMAPE_CH and roomTemp < 10) or
					(checkForAdaptation and monMAPE_CH and not(roomTemp < 10) and roomTemp < 18) or
					(checkForAdaptation and monMAPE_CH and not(roomTemp < 10) and not(roomTemp < 18)
				))
			)
	LTLSPEC g((checkForAdaptation and execMAPE_MD and sgnCloseWindow_MD) implies o(checkForAdaptation and monMAPE_MD and heaterStatus != OFF and windowStatus = OPEN))
	LTLSPEC g(((checkForAdaptation and execMAPE_AQ and sgnOpenWindow_AQ) or (checkForAdaptation and execMAPE_AQ and sgnCloseWindow_AQ)) implies o((checkForAdaptation and monMAPE_AQ and badAir) or (checkForAdaptation and monMAPE_AQ and not(badAir))))
	LTLSPEC g((checkForAdaptation and execMAPE_MWH and sgnWaterHeaterON_MWH) implies o(checkForAdaptation and monMAPE_MWH and time = EARLY_MORN))
	LTLSPEC g((checkForAdaptation and execMAPE_ES and sgnHeaterFAIRLY_HOT_ES) implies o(checkForAdaptation and monMAPE_ES and time = MORN_AFT and heaterStatus = VERY_HOT and waterHeaterStatus = WE_ON and esAdaptHeater))
	LTLSPEC g((checkForAdaptation and execMAPE_ES and sgnWaterHeaterOFF_ES) implies o(checkForAdaptation and monMAPE_ES and time = MORN_AFT and heaterStatus = VERY_HOT and waterHeaterStatus = WE_ON and not(esAdaptHeater)))


	// -- Requirement verification properties --
	//Liveness
	CTLSPEC (forall $s in WindowStatus with ag(ef(windowStatus = $s)))
	CTLSPEC (forall $s in HeaterStatus with ag(ef(heaterStatus = $s)))
	CTLSPEC (forall $s in WaterHeaterStatus with ag(ef(waterHeaterStatus = $s)))
	//Reachability
	CTLSPEC ef(windowStatus = OPEN and heaterStatus != OFF)

	main rule r_Main =
		//managed agents are not adapting
		if checkForAdaptation then
			par
				//these agents start the MAPE loops
				program(roomManaging)
				program(houseManaging)
				//these agents finish the MAPE loops
				program(windowManaging)
				program(heaterManaging)
				program(waterHeaterManaging)

				if monitor then
					r_manageTime[]
				endif
				monitor := not monitor
			endpar
		else
			//managed agents are adapting
			par
				//managed agents
				program(windowManaged)
				program(heaterManaged)
				program(waterHeaterManaged)
			endpar
		endif

default init s0:
	//statuses of the managed elements
	function waterHeaterStatus = WE_OFF
	function heaterStatus = OFF
	function windowStatus = OPEN
	//signals
	function sgnHeaterOFF_CH = false
	function sgnHeaterFAIRLY_HOT_CH = false
	function sgnHeaterFAIRLY_HOT_ES = false
	function sgnHeaterVERY_HOT_CH = false
	function sgnWaterHeaterON_MWH = false
	function sgnWaterHeaterOFF_ES = false
	function sgnOpenWindow_AQ = false
	function sgnCloseWindow_AQ = false
	function sgnCloseWindow_MD = false

	function time = EARLY_MORN
	function monitor = true

//managing agents that start the MAPE loops
agent HouseManaging: r_monitorAndAnalyzeHouse[]
agent RoomManaging: r_monitorAndAnalyzeRoom[]
//managing agents that finish the MAPE loops
agent WaterHeaterManaging: r_adaptWaterHeater[]
agent WindowManaging: r_adaptWindow[]
agent HeaterManaging: r_adaptHeater[]

//managed agents
agent WaterHeaterManaged: r_waterHeater[]
agent WindowManaged: r_window[]
agent HeaterManaged: r_heater[]
