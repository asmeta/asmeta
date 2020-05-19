asm MySmartHomeAQ_HC_refined

import ../../../../STDL/StandardLibrary

signature:
	//Hierarchical_AQ_HC_MAPE
	domain SysMdA subsetof Agent
	domain IntHCMgA subsetof Agent
	domain MainAQMgA subsetof Agent
	domain MainHCMgA subsetof Agent
	domain HighAQ_HCMgA subsetof Agent
	//domains added in refinement
	enum domain HeatingSetting = {FAIRLY_HOT | VERY_HOT | STOPPED}
    enum domain HeatingStatus = {ON|OFF}
	domain Temperature subsetof Integer
	enum domain AirLevel = {GOOD|MODERATE|UNHEALTHY|HAZARDOUS|INVALID} 
	enum domain WindowStatus = {OPEN|CLOSED}
	
	controlled sysManagedByIntHC: IntHCMgA -> SysMdA
	derived startIntHCM: IntHCMgA -> Boolean
	derived startIntHCE: IntHCMgA -> Boolean
	controlled sysManagedByMainAQ: MainAQMgA -> SysMdA
	derived startMainAQM: MainAQMgA -> Boolean
	derived startMainAQA: MainAQMgA -> Boolean
	derived startMainAQP: MainAQMgA -> Boolean
	derived startMainAQE: MainAQMgA -> Boolean
	derived startMainHCM: MainHCMgA -> Boolean
	derived startMainHCA: MainHCMgA -> Boolean
	derived startMainHCP: MainHCMgA -> Boolean
	derived startMainHCE: MainHCMgA -> Boolean
	derived startHighAQ_HCA: HighAQ_HCMgA -> Boolean
	derived startHighAQ_HCE: HighAQ_HCMgA -> Boolean
	//I: IntHC.M -> MainHC.M [*-SOME,1]
	controlled sgnIntHCMMainHCM: Prod(IntHCMgA, MainHCMgA) -> Boolean
	controlled fromIntHCMtoMainHCM: IntHCMgA -> MainHCMgA
	controlled fromMainHCMtoIntHCM: MainHCMgA -> Powerset(IntHCMgA)
	//I: MainHC.E -> IntHC.E [1,*-SOME]
	controlled sgnMainHCEIntHCE: Prod(MainHCMgA, IntHCMgA) -> Boolean
	controlled fromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	//derived orSelectorfromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	controlled fromIntHCEtoMainHCE: IntHCMgA -> MainHCMgA
	//MySmartHomeAQ_HC
	static hs_ff: SysMdA
	static hs_gf: SysMdA
	static aqs: SysMdA
	static main_aq: MainAQMgA
	static main_hc: MainHCMgA
	static int_hc_gf: IntHCMgA
	static int_hc_ff: IntHCMgA
	static high_aq_hs: HighAQ_HCMgA

 //functions added in refinement
	//managed items' state
	monitored outsideAqi: SysMdA -> Integer
	monitored aQInsideCO2: SysMdA -> Integer
	controlled outsideAqiSaved: MainAQMgA -> Integer
	controlled aQInsideCO2Saved: MainAQMgA -> Integer
    monitored windowsStatus: SysMdA -> WindowStatus
    controlled windowsStatusSaved: MainAQMgA -> WindowStatus
    controlled desiredWindowsStatus: MainAQMgA -> WindowStatus
	controlled windows_Setting: SysMdA -> WindowStatus
	derived airLevel:Integer -> AirLevel
	derived veryBadAir: Integer -> Boolean //for air outside
	derived co2Level:Integer -> AirLevel
	derived veryBadAirInside: Integer -> Boolean //for air inside
	controlled nolongerBadAirInside: Boolean //for restoring a normal situation after refreshing air inside
	monitored temperature: SysMdA -> Temperature
	monitored temperature_Setpoint: SysMdA -> Temperature
	monitored temperature_Midpoint: SysMdA -> Temperature
	monitored heatingStatus: SysMdA -> HeatingStatus
    controlled temperatureSaved: IntHCMgA -> Temperature
	controlled temperature_SetpointSaved: IntHCMgA -> Temperature
	controlled temperature_MidpointSaved: IntHCMgA -> Temperature
	controlled windowsStatusSaved: IntHCMgA -> WindowStatus
    controlled heatingStatusSaved: IntHCMgA -> HeatingStatus
    derived heatingsON: Boolean
	controlled desiredHeatingSetting: IntHCMgA -> HeatingSetting //Desired heating setting
	controlled desiredWindowsStatus: IntHCMgA -> WindowStatus
	derived computeHeatingSetting : IntHCMgA -> HeatingSetting
	controlled heating_Setting: SysMdA -> HeatingSetting
		
	//**** Function for the coordination of the overall system ****
	controlled sgnMainAQMtoA: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)
	controlled sgnMainAQAtoE: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)

	//Used to strictly sequence different executions of the aggregate MAPE loop HC
	controlled loopAQCompleted: MainAQMgA -> Boolean
	controlled loopHCCompleted: IntHCMgA -> Boolean
	derived allLowerloopsCompleted: Boolean
	//controlled loopHighAQ_HCCompleted: IntHCMgA -> Boolean
	
	derived floorname: SysMdA -> String //Added in refinement for debugging/logging (floor name in OpenHAB)
	out commands: SysMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)
	

definitions:
	
	function airLevel ($v in Integer) = 
    	if ($v>=0 and $v < 51 ) then GOOD 
    	else if ($v>=51 and $v<101 ) then MODERATE
    	else if ($v>=101 and $v<301 ) then UNHEALTHY
    	else if $v > 300 then HAZARDOUS
    	else INVALID
    	endif endif endif endif 
 	 
     function co2Level ($v in Integer) = 
    	if ($v>=340 and $v < 601 ) then GOOD 
    	else if ($v>=601 and $v<1001 ) then MODERATE
    	else if ($v>=1001 and $v<1501 ) then UNHEALTHY
    	else if $v > 1501 then HAZARDOUS
    	else INVALID
    	endif endif endif endif 
     
    function veryBadAir ($v in Integer) =  airLevel($v) = UNHEALTHY or airLevel($v) = HAZARDOUS   
    function veryBadAirInside ($v in Integer) =  co2Level($v) = UNHEALTHY or co2Level($v) = HAZARDOUS
	
	//Added in refinement for debugging/logging
	function floorname($a in SysMdA) =
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
	
	function heatingsON = (exist $a in IntHCMgA with heatingStatusSaved($a) = ON)
	
	
	function startIntHCM($b in IntHCMgA) =
		loopHCCompleted($b)  //***coordination***: for sequencing different MAPE loops executions

	function startIntHCE($b in IntHCMgA) =
		sgnMainHCEIntHCE(fromIntHCEtoMainHCE($b), $b)

	function startMainAQM($b in MainAQMgA) =
		loopAQCompleted($b)  //***coordination***: for sequencing different MAPE loops executions


	function startMainAQA($b in MainAQMgA) =
		sgnMainAQMtoA($b) 
		
//	function startMainAQP($b in MainAQMgA) =
	//	$b=$b

	function startMainAQE($b in MainAQMgA) =
		sgnMainAQAtoE($b)

	function startMainHCM($b in MainHCMgA) =
		(exist $a in fromMainHCMtoIntHCM($b) with sgnIntHCMMainHCM($a, $b))

	function startMainHCA($b in MainHCMgA) = //Refined
		(heatingsON and (exist $a in fromMainHCMtoIntHCM($b) with neq(desiredHeatingSetting($a),computeHeatingSetting($a)))) or
		(heatingsON and (exist $c in fromMainHCMtoIntHCM($b) with eq(windowsStatusSaved($c),OPEN)) )
		

	function startMainHCP($b in MainHCMgA) =
		$b=$b //workaround added in refinement to avoid NullPointerException


	function startMainHCE($b in MainHCMgA) =
		$b=$b //workaround added in refinement to avoid NullPointerException


	function startHighAQ_HCA($b in HighAQ_HCMgA) =
		allLowerloopsCompleted  //The higher managing group starts when all managing groups of the lower layer are completed

	function startHighAQ_HCE($b in HighAQ_HCMgA) =
		isDef(windows_Setting(aqs))
		/* (not (windows_Setting(aqs) = windows_Setting(hs_ff)) or 
	 	    not (windows_Setting(aqs) = windows_Setting(hs_gf)) ) */

	//function orSelectorfromMainHCEtoIntHCE($b in MainHCMgA) =
		//chooseone({$a in Powerset(IntHCMgA) | not(isEmpty($a)): $a})

	function allLowerloopsCompleted = 
	 ((forall $a in IntHCMgA with loopHCCompleted($a)) and 
	  (forall $b in MainAQMgA with loopAQCompleted($b)))
	
	rule r_Sys = //Added in refinement for debugging/logging
	 if (self = hs_ff or self= hs_gf) then
     	//HC settings
	   seq	
	 	let ($item = concat("Heating_Setting",floorname(self))) in
		  if isDef(heating_Setting(self)) then
			switch(heating_Setting(self))
			case STOPPED: commands(self) :=  append([],concat($item,".sendCommand(0)"))
			case FAIRLY_HOT: commands(self) :=  append([],concat($item,".sendCommand(5)"))
			case VERY_HOT: commands(self) := append([],concat($item,".sendCommand(10)"))
		    endswitch
		  endif
		endlet
		if isDef(windows_Setting(self)) then commands(self) := append(commands(self),
			concat(concat("Windows",floorname(self)),".sendCommand(CLOSED)")) endif
	  	//heating_Setting(self):= undef
	  	//windows_Setting(self):= undef
	   endseq
	else //(self =  aqs)
    	//AQ settings
	    if isDef(windows_Setting(self)) then
		 //par 
		  if (windows_Setting(self) = CLOSED) 
		  then //commands(self) := append([],"WindowsStatus.sendCommand(CLOSED)") 
		  	 commands(self) := append(append([],"WindowsGF.sendCommand(CLOSED)"),"WindowsFF.sendCommand(CLOSED)")
	  	  else //windows_Setting(self) = OPEN 
		     //commands(self) := append([],"WindowsStatus.sendCommand(OPEN)") endif
		     commands(self) := append(append([],"WindowsGF.sendCommand(OPEN)"),"WindowsFF.sendCommand(OPEN)") endif
	  	  //windows_Setting(self):= undef
	  	 //endpar	
	  	endif
	endif
	
    //Added in refinement
    //It saves the temperature-related data of a room/zone (sensed by the managed thermostat) and windows status into the knowledge
	rule r_SaveSensorsDataHC =
		par
			temperatureSaved(self) := temperature(sysManagedByIntHC(self))
		    temperature_SetpointSaved(self) := temperature_Setpoint(sysManagedByIntHC(self))
		    temperature_MidpointSaved(self) := temperature_Midpoint(sysManagedByIntHC(self))
		    windowsStatusSaved(self) := windowsStatus(sysManagedByIntHC(self))
		    heatingStatusSaved(self) := heatingStatus(sysManagedByIntHC(self))
		endpar
    
    
	rule r_CleanUp_IntHCM =
		loopHCCompleted(self) := false //added in refinement

	rule r_IntHCM =
		if startIntHCM(self) then
			par
				r_SaveSensorsDataHC[] //added in refinement
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
				heating_Setting(sysManagedByIntHC(self)):= desiredHeatingSetting(self) //Added in refinement
				windows_Setting(sysManagedByIntHC(self)):= desiredWindowsStatus(self)
				r_CleanUp_IntHCE[]
			endpar
		endif

	rule r_IntHC =
		par
			r_IntHCM[]
			r_IntHCE[]
		endpar

	rule r_CleanUp_MainAQE =
		par
			sgnMainAQAtoE(self) := false
			loopAQCompleted(self) := true //Added in refinement
		endpar
		
	rule r_MainAQE =
		if startMainAQE(self) then
			par
				windows_Setting(sysManagedByMainAQ(self)):= desiredWindowsStatus(self)//Added in refinement
				r_CleanUp_MainAQE[]
			endpar
		endif
	
	rule r_CleanUp_MainAQA =
		sgnMainAQMtoA(self) := false //Added in refinement

	rule r_checkAirAdaptationRequired =
    //First, check if adaptation is required for external bad air
	  if (veryBadAir(outsideAqiSaved(self)) and eq(windowsStatusSaved(self),OPEN)) then
			  	par
					desiredWindowsStatus(self) := CLOSED
					sgnMainAQAtoE(self) := true //start E
				endpar
	//Second, check if adaptation is required for refreshing air inside	
	  else if (veryBadAirInside(aQInsideCO2Saved(self)) and not(veryBadAir(outsideAqiSaved(self)))) then
			    par
			       nolongerBadAirInside := false
			       desiredWindowsStatus(self) := OPEN
     			   sgnMainAQAtoE(self) := true //start E
			    endpar 
    //Third, check if adaptation is required for closing the windows after the air inside was refreshed	
	  else if (not veryBadAirInside(aQInsideCO2Saved(self)) and windowsStatusSaved(self) = OPEN and not nolongerBadAirInside) then
			    par //restore normal status
			       nolongerBadAirInside := true
			       desiredWindowsStatus(self) := CLOSED
				   sgnMainAQAtoE(self) := true //start E
			    endpar
	  else //if no adaptation was necessary, reset of loop completion flag is done here
				if loopAQCompleted(self) = false then loopAQCompleted(self) := true endif	
	  endif endif endif	    
	  
	rule r_MainAQA = //refined
		if startMainAQA(self) then
			par 
			  r_checkAirAdaptationRequired[]
			  r_CleanUp_MainAQA[] 
			endpar
		endif 

     //Added in refinement
    //It saves the air quality-related data (sensors' values inside and the air level outside) and windows status into the knowledge
	rule r_SaveSensorsDataAQ =
		par
			outsideAqiSaved(self) := outsideAqi(sysManagedByMainAQ(self))
			windowsStatusSaved(self) := windowsStatus(sysManagedByMainAQ(self))
			aQInsideCO2Saved(self):= aQInsideCO2(sysManagedByMainAQ(self))
		endpar
	rule r_CleanUp_MainAQM =
		loopAQCompleted(self) := false //added in refinement

	rule r_MainAQM =
		if startMainAQM(self) then
			par
				r_SaveSensorsDataAQ[] //added in refinement
				sgnMainAQMtoA(self) := true //start A
				r_CleanUp_MainAQM[]
			endpar
		endif

	rule r_MainAQ = //refined
		par 
			r_MainAQM[]
			r_MainAQA[]
			r_MainAQE[]
		endpar

	rule r_CleanUp_MainHCE =
		skip //<<TODO>>

	rule r_MainHCE = //refined
		if startMainHCE(self) then
			par
				//forall $a in orSelectorfromMainHCEtoIntHCE(self) do sgnMainHCEIntHCE(self, $a) := true
				forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
					sgnMainHCEIntHCE(self, $a) := true
				r_CleanUp_MainHCE[]
			endpar
		endif
		
	rule r_CleanUp_MainHCP =
		skip //<<TODO>>

	rule r_MainHCP = //refined
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
		
	rule r_CleanUp_MainHCM =
		forall $a in fromMainHCMtoIntHCM(self) with sgnIntHCMMainHCM($a,self)
		 do sgnIntHCMMainHCM($a, self) := false

	rule r_MainHCM =
		if startMainHCM(self) then
			par
				r_MainHCA[]
				r_CleanUp_MainHCM[]
			endpar
		endif

	rule r_MainHC =
		r_MainHCM[]

	rule r_CleanUp_HighAQ_HCE =
		skip //<<TODO>>

	rule r_HighAQ_HCE =
		if startHighAQ_HCE(self) then //Added in refinement: just an example on how to resolve conflicts on windows setting. 
	 		par //If the AQ windows setting is def (discordant or not) with that of the HC, undo adaptations from the HC
				windows_Setting(hs_ff):= undef 
				windows_Setting(hs_gf):= undef 
				r_CleanUp_HighAQ_HCE[]
			endpar
		endif

	
	rule r_CleanUp_HighAQ_HCA =
		skip //<<TODO>>

	rule r_HighAQ_HCA =
		if startHighAQ_HCA(self) then 
			par
			  r_HighAQ_HCE[]
			  r_CleanUp_HighAQ_HCA[]
			endpar
		endif


	rule r_HighAQ_HC =
		r_HighAQ_HCA[]

	invariant inv_windows over windows_Setting: windows_Setting(aqs) = CLOSED iff (windows_Setting(hs_ff)=CLOSED and windows_Setting(hs_gf)=CLOSED )
	
	main rule r_mainRule =
		//forall $a in Agent with true do program($a)
	 seq //Added in refinement for debugging/logging
	    par //All managing AQ and HC agents run in parallel
			forall $a in IntHCMgA do program($a)
			forall $b in MainHCMgA do program($b)
			forall $c in MainAQMgA do program($c)
		endpar	 
        forall $d in HighAQ_HCMgA do program($d) //The remaining agents run sequentially
		forall $e in SysMdA do program($e) 
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

	function sysManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: hs_gf
			case int_hc_ff: hs_ff
		endswitch
    
    function sysManagedByMainAQ($a in MainAQMgA) = aqs //Added in refinement (strange...)
  
     //Added in refinement
    function sgnMainAQMtoA($a in MainAQMgA) = false
    function sgnMainAQAtoE($a in MainAQMgA) = false
	function desiredWindowsStatus($a in MainAQMgA) = CLOSED
	function loopAQCompleted ($a in MainAQMgA) = true
	function nolongerBadAirInside = true 
	function commands ($a in SysMdA) = [] //Added in refinement for debugging/logging
	function desiredHeatingSetting($a in IntHCMgA) = STOPPED
	function loopHCCompleted ($a in IntHCMgA) = true
	
	agent IntHCMgA: r_IntHC[]
	agent MainAQMgA: r_MainAQ[]
	agent HighAQ_HCMgA: r_HighAQ_HC[]
	agent MainHCMgA: r_MainHC[]
	agent SysMdA: r_Sys[]
