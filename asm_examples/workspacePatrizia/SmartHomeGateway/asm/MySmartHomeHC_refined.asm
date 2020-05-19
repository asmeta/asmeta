//Adaptation goal: maximize heating comfort
//Adaptation actions: (i) when it is cold, the heatings should be at sufficient settings for comfort";
//the heating system can work on different settings: 0 for STOPPED, 5 for fairly hot and 10 for very hot.
//(ii) do not open window when the heating is on"
//
//Managed items of both systems hs_gf and hs_ff:
//group Number HeatingStatus, group Switch WindowsStatus
//Managed items of system hs_gf:
// group Switch HeatingGF, Number Temperature_Setpoint_GF, Number Temperature_Midpoint_GF, Number Heating_Setting_GF, group Number TemperatureGF, group Contact WindowsFF
//Managed heating system hs_ff:
// group Switch HeatingFF, Number Temperature_Setpoint_FF, Number Temperature_Midpoint_FF, Number Heating_Setting_FF, group Number TemperatureFF, group Contact WindowsFF

asm MySmartHomeHC_refined

import StandardLibrary

signature:
	//HC_MAPE
	domain HeatingMdA subsetof Agent
	domain MainHCMgA subsetof Agent
	domain IntHCMgA subsetof Agent

	//domains added in refinement
	enum domain HeatingSetting = {FAIRLY_HOT | VERY_HOT | STOPPED}
    enum domain HeatingStatus = {ON|OFF}
	enum domain WindowStatus = {OPEN|CLOSED}
	domain Temperature subsetof Integer

	derived startMainHCM: MainHCMgA -> Boolean
	derived startMainHCA: MainHCMgA -> Boolean
	derived startMainHCP: MainHCMgA -> Boolean
	derived startMainHCE: MainHCMgA -> Boolean
	controlled heatingManagedByIntHC: IntHCMgA -> HeatingMdA
	derived startIntHCM: IntHCMgA -> Boolean
	derived startIntHCE: IntHCMgA -> Boolean
	//I: IntHC.M -> MainHC.M [*-SOME,1]
	controlled sgnIntHCMMainHCM: Prod(IntHCMgA, MainHCMgA) -> Boolean
	controlled fromIntHCMtoMainHCM: IntHCMgA -> MainHCMgA
	controlled fromMainHCMtoIntHCM: MainHCMgA -> Powerset(IntHCMgA)
	//I: MainHC.E -> IntHC.E [1,*-SOME]
	controlled sgnMainHCEIntHCE: Prod(MainHCMgA, IntHCMgA) -> Boolean
	controlled fromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	derived orSelectorfromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	controlled fromIntHCEtoMainHCE: IntHCMgA -> MainHCMgA
	//MySmartHomeHC
	static hs_ff: HeatingMdA
	static hs_gf: HeatingMdA
	static main_hc: MainHCMgA
	static int_hc_gf: IntHCMgA
	static int_hc_ff: IntHCMgA

	//functions added in refinement
	//managed items' state
	monitored temperature: HeatingMdA -> Temperature
	monitored temperature_Setpoint: HeatingMdA -> Temperature
	monitored temperature_Midpoint: HeatingMdA -> Temperature
	monitored windowsStatus: HeatingMdA -> WindowStatus
	monitored heatingStatus: HeatingMdA -> HeatingStatus
    controlled temperatureSaved: IntHCMgA -> Temperature
	controlled temperature_SetpointSaved: IntHCMgA -> Temperature
	controlled temperature_MidpointSaved: IntHCMgA -> Temperature
	controlled windowsStatusSaved: IntHCMgA -> WindowStatus
    controlled heatingStatusSaved: IntHCMgA -> HeatingStatus
    derived heatingsON: Boolean
	controlled desiredHeatingSetting: IntHCMgA -> HeatingSetting //Desired heating setting
	controlled desiredWindowsStatus: IntHCMgA -> WindowStatus
	derived computeHeatingSetting : IntHCMgA -> HeatingSetting
	controlled heating_Setting: HeatingMdA -> HeatingSetting
	controlled windows_Setting: HeatingMdA -> WindowStatus
	
	derived floorname: HeatingMdA -> String //Added in refinement for debugging/logging (floor name in OpenHAB)
	out commands: HeatingMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)

	//**** Function for the coordination of the overall system ****
	//Used to strictly sequence different executions of the aggregate MAPE loop HC
	controlled loopHCCompleted: IntHCMgA -> Boolean
	derived allSubloopsHCCompleted: Boolean

definitions:
    //Added in refinement for debugging/logging
	function floorname($a in HeatingMdA) =
	 if $a = hs_ff then "_FF" else  "_GF" endif

	//Added in refinement
	function computeHeatingSetting($a in IntHCMgA) =
		let ($t = temperatureSaved($a)) in
			if $t < temperature_MidpointSaved($a) then //e.g. < 10
				VERY_HOT
			else
				if $t < temperature_SetpointSaved($a) then //e.g. < 18
					FAIRLY_HOT
				else
					STOPPED
				endif
			endif
		endlet

	function startMainHCM($b in MainHCMgA) =
		(exist $a in fromMainHCMtoIntHCM($b) with sgnIntHCMMainHCM($a, $b))

    function heatingsON = (exist $a in IntHCMgA with heatingStatusSaved($a) = ON)

	function startMainHCA($b in MainHCMgA) =  //Refined
		(heatingsON and (exist $a in fromMainHCMtoIntHCM($b) with neq(desiredHeatingSetting($a),computeHeatingSetting($a)))) or
		(heatingsON and (exist $c in fromMainHCMtoIntHCM($b) with eq(windowsStatusSaved($c),OPEN)) )

	function startMainHCP($b in MainHCMgA) =
		$b=$b //workaround added in refinement to avoid NullPointerException

	function startMainHCE($b in MainHCMgA) =
		$b=$b //workaround added in refinement to avoid NullPointerException

	function startIntHCM($b in IntHCMgA) =
		loopHCCompleted($b)  //***coordination***: for sequencing different MAPE loops executions

	function startIntHCE($b in IntHCMgA) =
		sgnMainHCEIntHCE(fromIntHCEtoMainHCE($b), $b)

    function allSubloopsHCCompleted = (forall $a in IntHCMgA with loopHCCompleted($a))

	//function orSelectorfromMainHCEtoIntHCE($b in MainHCMgA) =
	//	chooseone({$a in Powerset(IntHCMgA) | not(isEmpty($a)): $a})

	rule r_Heating = //Added in refinement for debugging/logging
	  seq
		commands(self) := [] //reset
		let ($item = concat("Heating_Setting",floorname(self))) in
		  if isDef(heating_Setting(self)) then
			switch(heating_Setting(self))
			case STOPPED: commands(self) :=  append(commands(self),concat($item,".sendCommand(0)"))
			case FAIRLY_HOT: commands(self) :=  append(commands(self),concat($item,".sendCommand(5)"))
			case VERY_HOT: commands(self) := append(commands(self),concat($item,".sendCommand(10)"))
		    endswitch
		  endif
		endlet
		if isDef(windows_Setting(self)) then commands(self) := append(commands(self),
			concat(concat("Windows",floorname(self)),".sendCommand(CLOSED)")) endif
	  	heating_Setting(self):= undef
	  	windows_Setting(self):= undef
	  endseq

	rule r_CleanUp_MainHCE =
		skip //<<TODO>>

	rule r_MainHCE =
		if startMainHCE(self) then
			par
				//forall $a in orSelectorfromMainHCEtoIntHCE(self) do sgnMainHCEIntHCE(self, $a) := true
				forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
					sgnMainHCEIntHCE(self, $a) := true
				r_CleanUp_MainHCE[]
			endpar
		endif

	rule r_CleanUp_MainHCP =
		skip

	rule r_MainHCP =
		if startMainHCP(self) then
			par
				forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
				   par
					desiredHeatingSetting($a) := computeHeatingSetting($a)
				    if (heatingsON and windowsStatusSaved($a) = OPEN) then
				    	desiredWindowsStatus($a) := CLOSED
				    endif
				   endpar
				r_MainHCE[]
				r_CleanUp_MainHCP[]
			endpar
		endif

	rule r_CleanUp_MainHCA =
		skip //<<TODO>>

	rule r_MainHCA =
		if startMainHCA(self) then
			par
				r_MainHCP[]
				r_CleanUp_MainHCA[]
			endpar
		else //if no adaptation was necessary, cleaning of loop completion flags is done here
			forall $a in fromMainHCMtoIntHCM(self) with loopHCCompleted($a) = false do
				loopHCCompleted($a) := true
		endif

	rule r_CleanUp_MainHCM = //Refined
		forall $a in fromMainHCMtoIntHCM(self) with sgnIntHCMMainHCM($a,self) do
	    	sgnIntHCMMainHCM($a, self) := false

	rule r_MainHCM =
		if startMainHCM(self) then
			par
				r_MainHCA[]
				r_CleanUp_MainHCM[]
			endpar
		endif

	rule r_MainHC =
		r_MainHCM[]

    //Added in refinement
    //It saves the temperature-related data of a room/zone (sensed by the managed thermostat) and windows status into the knowledge
	rule r_SaveSensorsData =
		par
			temperatureSaved(self) := temperature(heatingManagedByIntHC(self))
		    temperature_SetpointSaved(self) := temperature_Setpoint(heatingManagedByIntHC(self))
		    temperature_MidpointSaved(self) := temperature_Midpoint(heatingManagedByIntHC(self))
		    windowsStatusSaved(self) := windowsStatus(heatingManagedByIntHC(self))
		    heatingStatusSaved(self) := heatingStatus(heatingManagedByIntHC(self))
		endpar

	rule r_CleanUp_IntHCM =
		loopHCCompleted(self) := false //added in refinement

	rule r_IntHCM =
		if startIntHCM(self) then
			par
				r_SaveSensorsData[] //added in refinement
				if not sgnIntHCMMainHCM(self,fromIntHCMtoMainHCM(self)) then sgnIntHCMMainHCM(self,fromIntHCMtoMainHCM(self)) := true endif
				r_CleanUp_IntHCM[]
			endpar
		endif

    //refined foor loop coordiantion
	rule r_CleanUp_IntHCE =
		par
			sgnMainHCEIntHCE(fromIntHCEtoMainHCE(self), self) := false
			loopHCCompleted(self) := true
	 	endpar

	rule r_IntHCE =
		if startIntHCE(self) then
			par
				heating_Setting(heatingManagedByIntHC(self)):= desiredHeatingSetting(self) //Added in refinement
				windows_Setting(heatingManagedByIntHC(self)):= desiredWindowsStatus(self)
				r_CleanUp_IntHCE[]
			endpar
		endif

	rule r_IntHC =
		par
			r_IntHCM[]
			r_IntHCE[]
		endpar

	main rule r_mainRule =
	//forall $a in Agent with true do program($a)
	  //Added in refinement for debugging/logging
	   seq
	    par
			forall $a in IntHCMgA  do program($a) 
			forall $b in MainHCMgA do program($b)
		endpar		
		    forall $c in HeatingMdA do program($c)
	   endseq	

default init s0:
	function sgnIntHCMMainHCM($a in IntHCMgA, $b in MainHCMgA) = false
	function sgnMainHCEIntHCE($a in MainHCMgA, $b in IntHCMgA) = false
	function fromIntHCMtoMainHCM($a in IntHCMgA) =
		switch($a)
			case int_hc_gf: main_hc
			case int_hc_ff: main_hc
		endswitch

	function fromMainHCMtoIntHCM($a in MainHCMgA) =
		switch($a)
			case main_hc: {int_hc_gf, int_hc_ff}
		endswitch

	function fromMainHCEtoIntHCE($a in MainHCMgA) =
		switch($a)
			case main_hc: {int_hc_gf, int_hc_ff}
		endswitch

	function fromIntHCEtoMainHCE($a in IntHCMgA) =
		switch($a)
			case int_hc_gf: main_hc
			case int_hc_ff: main_hc
		endswitch

	function heatingManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: hs_gf
			case int_hc_ff: hs_ff
		endswitch

	//Added in refinement
	function desiredHeatingSetting($a in IntHCMgA) = STOPPED
	function loopHCCompleted ($a in IntHCMgA) = true
	function commands ($a in HeatingMdA) = [] //Added in refinement for debugging/logging


	agent IntHCMgA: r_IntHC[]
	agent HeatingMdA: r_Heating[]
	agent MainHCMgA: r_MainHC[]
