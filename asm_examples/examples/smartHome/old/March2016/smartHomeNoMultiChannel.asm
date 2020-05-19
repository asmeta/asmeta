asm smartHomeNoMultiChannel

//Adapted from "Self-Adaptation with End-User Preferences: Using Run-Time Models and Constraint Solving?"

//different MAPE can use the same channel to communicate with rules doing the E

import ../../../../STDL/StandardLibrary
import ../../../../STDL/LTLlibrary

signature:
	//managing agents that start the MAPE loops
	domain HouseSensor subsetof Agent
	domain RoomSensor subsetof Agent
	domain WaterHeaterSensor subsetof Agent
	//managing agents that finish the MAPE loops
	domain HeaterManager subsetof Agent
	domain WaterHeaterManager subsetof Agent
	domain WindowManager subsetof Agent

	enum domain HeatingStatus = {OFF | FAIRLY_HOT | VERY_HOT}
	enum domain WindowStatus = {OPEN | CLOSED}
	enum domain WaterHeaterStatus = {WE_ON | WE_OFF}

	domain Temperature subsetof Integer
	enum domain Time = {EARLY_MORN | MORN_AFT | EVENING}

	monitored esAdaptHeater: Boolean // used to nondeterministically choose between two adaptations actions

	//Knowledge - statuses of the managed elements
	dynamic controlled waterHeaterStatus: WaterHeaterStatus
	dynamic controlled heatingStatus: HeatingStatus
	dynamic controlled windowStatus: WindowStatus

	//sensors
	dynamic monitored badAir: Boolean
	dynamic monitored roomTemp: Temperature

	//Knowledge - signals
	dynamic controlled setHeaterOFF: Boolean
	dynamic controlled setHeaterFAIRLY_HOT: Boolean
	dynamic controlled setHeaterVERY_HOT: Boolean
	dynamic controlled setWaterHeaterON: Boolean
	dynamic controlled setWaterHeaterOFF: Boolean
	dynamic controlled openWindow: Boolean
	dynamic controlled closeWindow: Boolean

	//managing agents
	static houseSensor: HouseSensor
	static roomSensor: RoomSensor
	static windowManager: WindowManager
	static heaterManager: HeaterManager
	static waterHeaterSensor: WaterHeaterSensor
	static waterHeaterManager: WaterHeaterManager

	dynamic monitored timePassed: Boolean
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
	derived execMAPE_ES_H: Boolean
	derived execMAPE_ES_WH: Boolean

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
	function execMAPE_ES_H = not monitor
	function execMAPE_ES_WH = not monitor

	rule r_setHeaterVERY_HOT =
		if setHeaterVERY_HOT then
			par
				heatingStatus := VERY_HOT
				setHeaterVERY_HOT := false
			endpar
		endif

	rule r_setHeaterFAIRLY_HOT =
		if setHeaterFAIRLY_HOT then
			par
				heatingStatus := FAIRLY_HOT
				setHeaterFAIRLY_HOT := false
			endpar
		endif

	rule r_setHeaterOFF =
		if setHeaterOFF then
			par
				heatingStatus := OFF
				setHeaterOFF := false
			endpar
		endif

	rule r_manageHeaterMAPE_CH =
		par
			r_setHeaterVERY_HOT[]
			r_setHeaterFAIRLY_HOT[]
			r_setHeaterOFF[]
		endpar

	rule r_manageHeaterMAPE_ES =
		r_setHeaterFAIRLY_HOT[]

	rule r_openWindow =
		if openWindow then
			par
				windowStatus := OPEN
				openWindow := false
			endpar
		endif
		
	rule r_closeWindow =
		if closeWindow then
			par
				windowStatus := CLOSED
				closeWindow := false
			endpar
		endif

	rule r_manageWindowMAPE_MD =
		r_closeWindow[]

	rule r_manageWindowMAPE_AQ =
		par
			r_openWindow[]
			r_closeWindow[]
		endpar

	rule r_manageWaterHeaterMAPE_MWH =
		if setWaterHeaterON then
			par
				waterHeaterStatus := WE_ON
				setWaterHeaterON := false
			endpar
		endif

	rule r_manageWaterHeaterMAPE_ES =
		if setWaterHeaterOFF then
			par
				waterHeaterStatus := WE_OFF
				setWaterHeaterOFF := false
			endpar
		endif






	rule r_manageHeater =
		par
			//MAPE_CH: when it is cold, the heatings should be at sufficient settings for comfort
			if execMAPE_CH then
				r_manageHeaterMAPE_CH[] //@E_MAPE_CH
			endif
			//MAPE_ES: when the electricity is expensive do not use water heater and strong heating together
			if execMAPE_ES_H then
				r_manageHeaterMAPE_ES[] //@E_MAPE_ES
			endif
		endpar

	rule r_manageWindow =
		par
			//MAPE_MD: do not open windows (close the open windows) when the heating is on 
			if execMAPE_MD then
				r_manageWindowMAPE_MD[] //@E_MAPE_MD
			endif
			//MAPE_AQ: do open window when air quality is bad
			if execMAPE_AQ then
				r_manageWindowMAPE_AQ[] //@E_MAPE_AQ
			endif
		endpar

	rule r_manageWaterHeater =
		par
			//MAPE_MWH: keep water heater on in the early morning
			if execMAPE_MWH then
				r_manageWaterHeaterMAPE_MWH[] //@E_MAPE_MWH
			endif
			//MAPE_ES: when the electricity is expensive do not use water heater and strong heating together
			if execMAPE_ES_WH then
				r_manageWaterHeaterMAPE_ES[] //@E_MAPE_ES
			endif
		endpar








	//Starting rules of the MAPE loops

	//MAPE_CH: when it is cold, the heatings should be at sufficient settings for comfort
	rule r_checkRoomTempMAPE_CH =
		if roomTemp < 10 then
			setHeaterVERY_HOT := true // send signal to set the heater to VERY_HOT
		else
			if roomTemp < 18 then
				setHeaterFAIRLY_HOT := true // send signal to set the heater to FAIRLY_HOT
			else
				setHeaterOFF := true // send signal to turn the heater off
			endif
		endif

	//MAPE_MD: do not open windows (close the open windows) when the heating is on 
	rule r_checkWindowAndHeatingMAPE_MD =
		if heatingStatus != OFF and windowStatus = OPEN then
			closeWindow := true // send signal to close the window
		endif		

	//MAPE_AQ: do open window when air quality is bad
	rule r_checkAirQualityMAPE_AQ =
		if badAir then
			openWindow := true // send signal to open the window
		else
			closeWindow := true // send signal to close the window
		endif

	//MAPE_MWH: keep water heater on in the early morning
	rule r_checkHotWaterMorningMAPE_MWH =
		if time = EARLY_MORN then
			setWaterHeaterON := true // send signal to turn the water heater on
		endif

	//MAPE_ES: when the electricity is expensive do not use water heater and strong heating together
	rule r_checkElectrMAPE_ES =
		//electricity is expensive during the day
		if time = MORN_AFT then
			if heatingStatus = VERY_HOT and waterHeaterStatus = WE_ON then
				//choose $b in Boolean with true do
					//if $b then
				if esAdaptHeater then
					setHeaterFAIRLY_HOT := true // send signal to decrease the heater
				else
					setWaterHeaterOFF := true // send signal to turn the water heater off
				endif
			endif
		endif




	//Programs of the managing agents that start the MAPE loops
	rule r_monitorRoom =
		par
			if monMAPE_CH then
				r_checkRoomTempMAPE_CH[] //@MA_MAPE_CH
			endif
			if monMAPE_MD then
				r_checkWindowAndHeatingMAPE_MD[] //@MA_MAPE_MD
			endif
			if monMAPE_AQ then
				r_checkAirQualityMAPE_AQ[] //@MA_MAPE_AQ
			endif
		endpar

	rule r_monitorWaterHeater =
		if monMAPE_MWH then
			r_checkHotWaterMorningMAPE_MWH[] //@MA_MAPE_MWH
		endif

	rule r_monitorHouse =
		if monMAPE_ES then
			r_checkElectrMAPE_ES[] //@MA_MAPE_ES
		endif



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

	//Pattern-driven verification
	//LTLSPEC g(execMAPE_CH implies o(monMAPE_CH))
	LTLSPEC g((	(execMAPE_CH and setHeaterOFF) or
				(execMAPE_CH and setHeaterFAIRLY_HOT) or
				(execMAPE_CH and setHeaterVERY_HOT) )
				implies
				o(	(monMAPE_CH and roomTemp < 10) or
					(monMAPE_CH and not(roomTemp < 10) and roomTemp < 18) or
					(monMAPE_CH and not(roomTemp < 10) and not(roomTemp < 18)
				))
			)
	
	//LTLSPEC g(execMAPE_MD implies o(monMAPE_MD))
	LTLSPEC g((execMAPE_MD and closeWindow) implies o(monMAPE_MD and heatingStatus != OFF and windowStatus = OPEN))

	//LTLSPEC g(execMAPE_AQ implies o(monMAPE_AQ))
	LTLSPEC g(((execMAPE_AQ and openWindow) or (execMAPE_AQ and closeWindow)) implies o((monMAPE_AQ and badAir) or (monMAPE_AQ and not(badAir))))

	//LTLSPEC g(execMAPE_MWH implies o(monMAPE_MWH))
	LTLSPEC g((execMAPE_MWH and setWaterHeaterON) implies o(monMAPE_MWH and time = EARLY_MORN))

	//LTLSPEC g(execMAPE_ES_H implies o(monMAPE_ES))
	LTLSPEC g((execMAPE_ES_H and setHeaterFAIRLY_HOT) implies o(monMAPE_ES and time = MORN_AFT and heatingStatus = VERY_HOT and waterHeaterStatus = WE_ON and esAdaptHeater))

	//LTLSPEC g(execMAPE_ES_WH implies o(monMAPE_ES))
	LTLSPEC g((execMAPE_ES_WH and setWaterHeaterOFF) implies o(monMAPE_ES and time = MORN_AFT and heatingStatus = VERY_HOT and waterHeaterStatus = WE_ON and not(esAdaptHeater)))

	main rule r_Main =
		par
			//these agents start the MAPE loops
			program(roomSensor)
			program(houseSensor)
			program(waterHeaterSensor)
			//these agents finish the MAPE loops
			program(windowManager)
			program(heaterManager)
			program(waterHeaterManager)

			if monitor then
				r_manageTime[]
			endif
			monitor := not monitor
		endpar

default init s0:
	//statuses of the managed elements
	function waterHeaterStatus = WE_OFF
	function heatingStatus = OFF
	function windowStatus = OPEN
	//signals
	function setHeaterOFF = false
	function setHeaterFAIRLY_HOT = false
	function setHeaterVERY_HOT = false
	function setWaterHeaterON = false
	function setWaterHeaterOFF = false
	function openWindow = false
	function closeWindow = false

	function time = EARLY_MORN
	function monitor = true

//managing agents that start the MAPE loops
agent HouseSensor: r_monitorHouse[]
agent RoomSensor: r_monitorRoom[]
agent WaterHeaterSensor: r_monitorWaterHeater[]
//managing agents that finish the MAPE loops
agent WaterHeaterManager: r_manageWaterHeater[]
agent WindowManager: r_manageWindow[]
agent HeaterManager: r_manageHeater[]
