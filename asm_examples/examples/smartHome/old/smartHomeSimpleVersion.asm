asm smartHomeSimpleVersion

//Adapted from "Self-Adaptation with End-User Preferences: Using Run-Time Models and Constraint Solving?"

import ../../../STDL/StandardLibrary

signature:
	domain Temperature subsetof Integer
	domain HouseManager subsetof Agent
	domain RoomManager subsetof Agent
	domain WaterHeaterManager subsetof Agent
	enum domain Time = {EARLY_MORNING | MORNING_AFTERNOON | EVENING}
	enum domain HeatingSetting = {OFF | FAIRLY_HOT | VERY_HOT}
	enum domain WindowStatus = {OPEN | CLOSED}
	enum domain WaterHeaterStatus = {WE_ON | WE_OFF}
	dynamic controlled waterHeater: WaterHeaterStatus
	dynamic controlled heatingStatus: HeatingSetting
	dynamic controlled windowStatus: WindowStatus
	dynamic controlled time: Time
	dynamic monitored roomTemperature: Temperature
	dynamic monitored badAir: Boolean

	//managing agents
	static houseManager: HouseManager
	static roomManager: RoomManager
	static waterHeaterManager: WaterHeaterManager

	dynamic monitored timePassed: Boolean

definitions:
	domain Temperature = {0 : 35}




	//Rules of the adaptation goals

	//when the electricity is expensive do not use water heater and strong heating together
	rule r_electricitySavingAdaptGoal =
		//electricity is expensive during the day
		if time = MORNING_AFTERNOON then //@M
			if heatingStatus = VERY_HOT and waterHeater = WE_ON then //@M
				choose $b in Boolean with true do
					if $b then
						heatingStatus := FAIRLY_HOT //@E
					else
						waterHeater := WE_OFF //@E
					endif
			endif
		endif

	//keep water heater on in the early morning
	rule r_hotWaterAdaptGoal =
		if time = EARLY_MORNING then //@M
			waterHeater := WE_ON //@E
		endif

	//do not open windows (close the open windows) when the heating is on 
	rule r_minimizeDispersionAdaptGoal =
		if heatingStatus != OFF and windowStatus = OPEN then //@M
			windowStatus := CLOSED //@E
		endif

	//when it is cold, the heatings should be at sufficient settings for comfort
	rule r_temperatureAdaptGoal =
		if roomTemperature < 10 then //@M
			heatingStatus := VERY_HOT
		else
			if roomTemperature < 18 then //@M
				heatingStatus := FAIRLY_HOT //@E
			else
				heatingStatus := OFF //@E
			endif
		endif

	//do open window when air quality is bad
	rule r_airQualityAdaptGoal =
		par
			if badAir then //@M
				windowStatus := OPEN //@E
			endif
			if not badAir and windowStatus = OPEN then //@M
				windowStatus := CLOSED //@E
			endif
		endpar




	//Programs of the managing agents
	rule r_manageHouse =
		r_electricitySavingAdaptGoal[]

	rule r_manageWaterHeater =
		r_hotWaterAdaptGoal[]

	rule r_manageRoom =
		par
			r_temperatureAdaptGoal[]
			r_airQualityAdaptGoal[]
			r_minimizeDispersionAdaptGoal[]
		endpar





	rule r_manageTime =
		if timePassed then
			switch time
				case EARLY_MORNING:
					time := MORNING_AFTERNOON
				case MORNING_AFTERNOON:
					time := EVENING
				case EVENING:
					time := EARLY_MORNING
			endswitch
		endif

	main rule r_Main =
		par
			program(roomManager)
			program(houseManager)
			program(waterHeaterManager)
			r_manageTime[]
		endpar

default init s0:
	function waterHeater = WE_OFF
	function heatingStatus = OFF
	function windowStatus = OPEN
	function time = EARLY_MORNING

agent HouseManager: r_manageHouse[]
agent RoomManager: r_manageRoom[]
agent WaterHeaterManager: r_manageWaterHeater[]
