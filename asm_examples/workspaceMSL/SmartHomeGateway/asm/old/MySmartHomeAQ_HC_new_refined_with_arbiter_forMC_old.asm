asm MySmartHomeAQ_HC_new_refined_with_arbiter_forMC

import ../../../../STDL/StandardLibrary

signature:
	//Hierarchical_AQ_HC_MAPE
	domain SysHMdA subsetof Agent
	domain SysAMdA subsetof Agent
	domain IntHCMgA subsetof Agent
	domain MainAQMgA subsetof Agent
	domain MainHCMgA subsetof Agent
	domain HighAQ_HCMgA subsetof Agent
	domain AirDomain subsetof Integer
	domain Co2Domain subsetof Integer

	//domains added in refinement
	enum domain HeatingSetting = {FAIRLY_HOT | VERY_HOT | STOPPED}
    enum domain HeatingStatus = {ON|OFF}
	domain Temperature subsetof Integer
	enum domain AirLevel = {GOOD|MODERATE|UNHEALTHY|HAZARDOUS|INVALID} 
	enum domain WindowStatus = {OPEN|CLOSED}
	
	controlled syshManagedByIntHC: IntHCMgA -> SysHMdA
	controlled sysaManagedByIntHC: IntHCMgA -> SysAMdA
	derived startIntHCM: IntHCMgA -> Boolean
	derived startIntHCE: IntHCMgA -> Boolean
	//controlled sysaManagedByMainAQ: MainAQMgA -> Powerset(SysAMdA)
	derived startMainAQM: MainAQMgA -> Boolean
	derived startMainAQA: MainAQMgA -> Boolean
	//derived startMainAQP: MainAQMgA -> Boolean
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
	//controlled fromMainHCMtoIntHCM: MainHCMgA -> Powerset(IntHCMgA)
	//I: MainHC.E -> IntHC.E [1,*-SOME]
	controlled sgnMainHCEIntHCE: Prod(MainHCMgA, IntHCMgA) -> Boolean
	//controlled fromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	//derived orSelectorfromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
	controlled fromIntHCEtoMainHCE: IntHCMgA -> MainHCMgA
	//MySmartHomeAQ_HC
	static hs_ff: SysHMdA
	static hs_gf: SysHMdA
	static aqs_gf: SysAMdA
	static aqs_ff: SysAMdA
	static main_aq: MainAQMgA
	static main_hc: MainHCMgA
	static int_hc_gf: IntHCMgA
	static int_hc_ff: IntHCMgA
	static high_aq_hs: HighAQ_HCMgA
	
//functions added in refinement
	//managed items' state
	monitored outsideAqi: SysAMdA -> AirDomain
	monitored aQInsideCO2: SysAMdA -> Co2Domain
	controlled outsideAqiSaved: MainAQMgA -> AirDomain
	controlled aQInsideCO2Saved: MainAQMgA -> Co2Domain
    monitored windowsStatus: SysAMdA -> WindowStatus
    controlled windowsStatusSaved: MainAQMgA -> WindowStatus
    controlled desiredWindowsStatus: MainAQMgA -> WindowStatus
	controlled windows_Setting: SysAMdA -> WindowStatus
	derived airLevel: AirDomain -> AirLevel
	derived veryBadAir: AirDomain -> Boolean //for air outside
	derived co2Level: Co2Domain -> AirLevel
	derived veryBadAirInside: Co2Domain -> Boolean //for air inside
	controlled nolongerBadAirInside: Boolean //for restoring a normal situation after refreshing air inside
	monitored temperature: SysHMdA -> Temperature
	monitored temperature_Setpoint: SysHMdA -> Temperature
	monitored temperature_Midpoint: SysHMdA -> Temperature
	monitored heatingStatus: SysHMdA -> HeatingStatus
    controlled temperatureSaved: IntHCMgA -> Temperature
	controlled temperature_SetpointSaved: IntHCMgA -> Temperature
	controlled temperature_MidpointSaved: IntHCMgA -> Temperature
	controlled windowsStatusSaved: IntHCMgA -> WindowStatus
    controlled heatingStatusSaved: IntHCMgA -> HeatingStatus
    derived heatingsON: Boolean
	controlled desiredHeatingSetting: IntHCMgA -> HeatingSetting //Desired heating setting
	controlled desiredWindowsStatus: IntHCMgA -> WindowStatus
	derived computeHeatingSetting : IntHCMgA -> HeatingSetting
	controlled heating_Setting: SysHMdA -> HeatingSetting
	//derived  overallWindowsStatus: Powerset(SysAMdA) -> WindowStatus //OPEN, if at least one is open; false otherwise
	derived  overallWindowsStatus: WindowStatus //OPEN, if at least one is open; false otherwise
	//derived overallAQInsideCO2: Powerset(SysAMdA) -> Temperature
	derived overallAQInsideCO2: Co2Domain
	
	//**** Function for the coordination of the overall system ****
	controlled sgnMainAQMtoA: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)
	controlled sgnMainAQAtoE: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)

	//Used to strictly sequence different executions of the aggregate MAPE loop HC
	controlled loopAQCompleted: MainAQMgA -> Boolean
	controlled loopHCCompleted: IntHCMgA -> Boolean
	derived allLowerloopsCompleted: Boolean
	//controlled loopHighAQ_HCCompleted: IntHCMgA -> Boolean

	//derived floorname: SysAMdA -> String //Added in refinement for debugging/logging (floor name in OpenHAB)
	//derived floorname: SysHMdA -> String//Added in refinement for debugging/logging
	//out commands: SysAMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)
	//out commands: SysHMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)
	
	controlled sgnHightoMainHC_E: MainHCMgA -> Boolean//Added in refinement to resolve AQ and HC conflicts
	controlled sgnHightoMainAQ_E: MainAQMgA -> Boolean //Added in refinement to resolve AQ and HC conflicts
	
	
	controlled arbiterTurn: Boolean
	
definitions:
domain Temperature = {0 : 5}
domain AirDomain = {-1 : 3}
domain Co2Domain = {0 : 4}

//function  overallWindowsStatus ($set in Powerset(SysAMdA)) = 
//  if (exist $a in $set with windowsStatus($a) = OPEN) then OPEN else CLOSED endif
function  overallWindowsStatus =
	 if (exist $a in SysAMdA with windowsStatus($a) = OPEN) then OPEN else CLOSED endif

//function overallAQInsideCO2 ($set in Powerset(SysAMdA)) =
  //idiv((sum(<$k in asBag($set): aQInsideCO2($k)>),size($set))
  //(sum(<$k in asBag($set): aQInsideCO2($k)>)/size($set)) //average
  /*let ($agf=first(asSequence($set)), $aff=last(asSequence($set))) in  
         idiv((aQInsideCO2($agf) + aQInsideCO2($aff)),2)
  endlet*/
function overallAQInsideCO2 =
  	idiv((aQInsideCO2(aqs_gf) + aQInsideCO2(aqs_ff)),2)
  
function airLevel ($v in AirDomain) = 
    	if ($v = 0) then GOOD 
    	else if ($v = 1) then MODERATE
    	else if ($v = 2) then UNHEALTHY
    	else if $v = 3 then HAZARDOUS
    	else INVALID
    	endif endif endif endif
 	 
     function co2Level ($v in Co2Domain) = 
    	if ($v = 1) then GOOD 
    	else if ($v = 2) then MODERATE
    	else if ($v = 3) then UNHEALTHY
    	else if $v = 4 then HAZARDOUS
    	else INVALID
    	endif endif endif endif 
     
    function veryBadAir ($v in AirDomain) =  airLevel($v) = UNHEALTHY or airLevel($v) = HAZARDOUS   
    function veryBadAirInside ($v in Co2Domain) =  co2Level($v) = UNHEALTHY or co2Level($v) = HAZARDOUS
	
	//Added in refinement for debugging/logging
	//function floorname($a in SysAMdA) =
	// if $a = aqs_gf then "_GF" else  "_FF" endif

    //Added in refinement for debugging/logging
	//function floorname($a in SysHMdA) =
	// if $a = hs_gf then "_GF" else  "_FF" endif

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

	//function startMainAQP($b in MainAQMgA) =
		//true

	function startMainAQE($b in MainAQMgA) =
			(sgnMainAQAtoE($b) and sgnHightoMainAQ_E($b)) //added in refinement 
			

	function startMainHCM($b in MainHCMgA) =
		//(exist $a in fromMainHCMtoIntHCM($b) with sgnIntHCMMainHCM($a, $b))
		sgnIntHCMMainHCM(int_hc_gf, $b) or sgnIntHCMMainHCM(int_hc_ff, $b)

	function startMainHCA($b in MainHCMgA) = //Refined
		//(heatingsON and (exist $a in fromMainHCMtoIntHCM($b) with neq(desiredHeatingSetting($a),computeHeatingSetting($a)))) or
		//(heatingsON and (exist $c in fromMainHCMtoIntHCM($b) with eq(windowsStatusSaved($c),OPEN)) )
		(heatingsON and
			(
				(neq(desiredHeatingSetting(int_hc_gf),computeHeatingSetting(int_hc_gf)) or neq(desiredHeatingSetting(int_hc_ff),computeHeatingSetting(int_hc_ff)))
				or
				(eq(windowsStatusSaved(int_hc_gf),OPEN) or eq(windowsStatusSaved(int_hc_ff),OPEN))
			)
		)
		

	function startMainHCP($b in MainHCMgA) =
	 $b=$b //workaround added in refinement to avoid NullPointerException


	function startMainHCE($b in MainHCMgA) =
		sgnHightoMainHC_E($b)

	function startHighAQ_HCA($b in HighAQ_HCMgA) = //Refined
    	//(not(sgnHightoMainHC_E(main_hc)) and not(sgnHightoMainAQ_E(main_aq))) and
		//not(allLowerloopsCompleted) and
		//((sgnMainAQAtoE(main_aq) and (sgnMainHCEIntHCE(fromIntHCEtoMainHCE(int_hc_gf), int_hc_gf) 
		//and sgnMainHCEIntHCE(fromIntHCEtoMainHCE(int_hc_ff), int_hc_ff))) and
		//(isDef(desiredWindowsStatus(main_aq)) and (isDef(desiredWindowsStatus(int_hc_ff)) or
		//isDef(desiredWindowsStatus(int_hc_gf)))) )
		(isDef(desiredWindowsStatus(main_aq)) or (isDef(desiredWindowsStatus(int_hc_ff)) or
		isDef(desiredWindowsStatus(int_hc_gf)))) 
		

	function startHighAQ_HCE($b in HighAQ_HCMgA) = 
	   $b=$b //workaround added in refinement to avoid NullPointerException
	   	
		
	//function orSelectorfromMainHCEtoIntHCE($b in MainHCMgA) =
	//	chooseone({$a in Powerset(IntHCMgA) | not(isEmpty($a)): $a})

	function allLowerloopsCompleted = 
	 ((forall $a in IntHCMgA with loopHCCompleted($a)) and 
	  (forall $b in MainAQMgA with loopAQCompleted($b)))
	  
	  
	rule r_SysA = //Added in refinement for debugging/logging
		if isDef(windows_Setting(self)) then
		 /*par 
		  if (windows_Setting(self) = CLOSED) 
		  then //commands(self) := append([],"WindowsStatus.sendCommand(CLOSED)") 
		  	 commands(self) := append(append([],"WindowsGF.sendCommand(CLOSED)"),"WindowsFF.sendCommand(CLOSED)")
	  	  else //windows_Setting(self) = OPEN 
		     //commands(self) := append([],"WindowsStatus.sendCommand(OPEN)") endif
		     commands(self) := append(append([],"WindowsGF.sendCommand(OPEN)"),"WindowsFF.sendCommand(OPEN)") endif*/
	  	  windows_Setting(self):= undef
	  	 //endpar	
	  	endif

	rule r_SysH = //Added in refinement for debugging/logging
	 	skip
	 	/*let ($item = concat("Heating_Setting",floorname(self))) in
		  if isDef(heating_Setting(self)) then
			switch(heating_Setting(self))
			case STOPPED: commands(self) :=  append([],concat($item,".sendCommand(0)"))
			case FAIRLY_HOT: commands(self) :=  append([],concat($item,".sendCommand(5)"))
			case VERY_HOT: commands(self) := append([],concat($item,".sendCommand(10)"))
		    endswitch
		  endif
		endlet*/
		

	//Added in refinement
    //It saves the temperature-related data of a room/zone (sensed by the managed thermostat) and windows status into the knowledge
	rule r_SaveSensorsDataHC =
		par
			temperatureSaved(self) := temperature(syshManagedByIntHC(self))
		    temperature_SetpointSaved(self) := temperature_Setpoint(syshManagedByIntHC(self))
		    temperature_MidpointSaved(self) := temperature_Midpoint(syshManagedByIntHC(self))
		    windowsStatusSaved(self) := windowsStatus(sysaManagedByIntHC(self))
		    heatingStatusSaved(self) := heatingStatus(syshManagedByIntHC(self))
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
				heating_Setting(syshManagedByIntHC(self)):= desiredHeatingSetting(self) //Added in refinement
				windows_Setting(sysaManagedByIntHC(self)):= desiredWindowsStatus(self)
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
			sgnHightoMainAQ_E(self):=false //Added in refinement
		endpar

	rule r_MainAQE =
		if startMainAQE(self) then
			par
				//forall  $a in sysaManagedByMainAQ(self) do
				//	windows_Setting($a):= desiredWindowsStatus(self)//Added in refinement
				windows_Setting(aqs_gf):= desiredWindowsStatus(self)
				windows_Setting(aqs_ff):= desiredWindowsStatus(self)
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

	rule r_MainAQA =
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
			//outsideAqiSaved(self) := outsideAqi(first(asSequence(sysaManagedByMainAQ(self))))
			outsideAqiSaved(self) := outsideAqi(aqs_gf)

			//windowsStatusSaved(self) := overallWindowsStatus(sysaManagedByMainAQ(self))
			windowsStatusSaved(self) := overallWindowsStatus

			//aQInsideCO2Saved(self):= overallAQInsideCO2(sysaManagedByMainAQ(self))
			aQInsideCO2Saved(self):= overallAQInsideCO2
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

	rule r_MainAQ =
		par 
			r_MainAQM[]
			r_MainAQA[]
			r_MainAQE[]
		endpar
		
	rule r_CleanUp_MainHCE =
		sgnHightoMainHC_E(self) := false//Added in refinement
		
	
	
	rule r_MainHCE =//refined
		if startMainHCE(self) then
			par
				//forall $a in orSelectorfromMainHCEtoIntHCE(self) do sgnMainHCEIntHCE(self, $a) := true
				//forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
				//	sgnMainHCEIntHCE(self, $a) := true
				sgnMainHCEIntHCE(self, int_hc_gf) := true
				sgnMainHCEIntHCE(self, int_hc_ff) := true
				r_CleanUp_MainHCE[]
			endpar
		endif

	rule r_CleanUp_MainHCP =
		skip //<<TODO>>

	rule r_MainHCP =
		if startMainHCP(self) then
			par
				/*forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
				   par
					desiredHeatingSetting($a) := computeHeatingSetting($a)
				    if (heatingsON and windowsStatusSaved($a) = OPEN) then
				    	desiredWindowsStatus($a) := CLOSED
				    endif
				   endpar*/
				par
					desiredHeatingSetting(int_hc_gf) := computeHeatingSetting(int_hc_gf)
				    if (heatingsON and windowsStatusSaved(int_hc_gf) = OPEN) then
				    	desiredWindowsStatus(int_hc_gf) := CLOSED
				    endif
				    desiredHeatingSetting(int_hc_ff) := computeHeatingSetting(int_hc_ff)
				    if (heatingsON and windowsStatusSaved(int_hc_ff) = OPEN) then
				    	desiredWindowsStatus(int_hc_ff) := CLOSED
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
			//forall $a in fromMainHCMtoIntHCM(self) with loopHCCompleted($a) = false do
				//loopHCCompleted($a) := true
			par
		    	loopHCCompleted(int_hc_gf) := true
		    	loopHCCompleted(int_hc_ff) := true
		    endpar
		endif
		
	rule r_CleanUp_MainHCM =
		//forall $a in fromMainHCMtoIntHCM(self) with sgnIntHCMMainHCM($a,self)
		// do sgnIntHCMMainHCM($a, self) := false
		par
	    	sgnIntHCMMainHCM(int_hc_gf, self) := false
	    	sgnIntHCMMainHCM(int_hc_ff, self) := false
	    endpar

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
	 		par //If the AQ location value desiredWindowsStatus is def (discordant or not in value) with that of the HC, undo adaptations from the HC by changing the desired setting to that of AQ
				desiredWindowsStatus(int_hc_ff):=  desiredWindowsStatus(main_aq)
				desiredWindowsStatus(int_hc_gf):= desiredWindowsStatus(main_aq)
				sgnHightoMainHC_E(main_hc):= true
				sgnHightoMainAQ_E(main_aq):= true
				r_CleanUp_HighAQ_HCE[]
			endpar
		endif

	rule r_CleanUp_HighAQ_HCA =
	  /* if (not(sgnHightoMainHC_E(main_hc)) and not(sgnHightoMainAQ_E(main_aq)))  then 
	   	par 
	   		sgnHightoMainHC_E(main_hc):= true	
	   		sgnHightoMainAQ_E(main_aq):= true 
		endpar
	   endif */
	   skip //<<TODO>>
	

	rule r_HighAQ_HCA =
		if startHighAQ_HCA(self) then 
			  r_HighAQ_HCE[] //Adaptation required
		else  //Added in refinement to trigger the main E components of the intermediate layer
			r_CleanUp_HighAQ_HCA[] //Adaptation not required
		endif
		
	rule r_HighAQ_HC =
		r_HighAQ_HCA[]

	main rule r_mainRule =
	 par//seq 
	    if not arbiterTurn then
		    par //All managing agents run in parallel
		    	forall $a in IntHCMgA do program($a)
				forall $b in MainHCMgA do program($b)
				forall $c in MainAQMgA do program($c)
			endpar
		else
			forall $d in HighAQ_HCMgA do program($d)
        endif
        arbiterTurn := not arbiterTurn
         //The managed agents run sequentially
		//forall $e in SysAMdA, $f in SysHMdA do par program($e) program($f) endpar 
	 endpar//endseq	
	 

default init s0:
	function sgnIntHCMMainHCM($a in IntHCMgA, $b in MainHCMgA) = false
	function sgnMainHCEIntHCE($a in MainHCMgA, $b in IntHCMgA) = false
	function fromIntHCMtoMainHCM($a in IntHCMgA) =
		/*switch($a)
			case int_hc_gf: main_hc
			case int_hc_ff: main_hc
		endswitch*/
		main_hc

	/*function fromMainHCMtoIntHCM($a in MainHCMgA) =
		switch($a)
			case main_hc: {int_hc_gf, int_hc_ff}
		endswitch*/

	/*function fromMainHCEtoIntHCE($a in MainHCMgA) =
		switch($a)
			case main_hc: {int_hc_gf, int_hc_ff}
		endswitch*/

	function fromIntHCEtoMainHCE($a in IntHCMgA) =
		/*switch($a)
			case int_hc_gf: main_hc
			case int_hc_ff: main_hc
		endswitch*/
		main_hc

	//function sysaManagedByMainAQ($x in MainAQMgA) =
		/*switch($x)
			case main_aq: {aqs_gf, aqs_ff}
		endswitch*/
		//{aqs_gf, aqs_ff}

	function syshManagedByIntHC($x in IntHCMgA) =
		/*switch($x)
			case int_hc_gf: hs_gf
			case int_hc_ff: hs_ff
		endswitch*/
		if $x = int_hc_gf then hs_gf else hs_ff endif

	function sysaManagedByIntHC($x in IntHCMgA) =
		/*switch($x)
			case int_hc_gf: aqs_gf
			case int_hc_ff: aqs_ff
		endswitch*/
		if $x = int_hc_gf then aqs_gf else aqs_ff endif


	 //Added in refinement
    function sgnMainAQMtoA($a in MainAQMgA) = false
    function sgnMainAQAtoE($a in MainAQMgA) = false
    function sgnHightoMainHC_E($a in MainHCMgA)=false
    function sgnHightoMainAQ_E($a in MainAQMgA)=false
	//function desiredWindowsStatus($a in MainAQMgA) = CLOSED
	function loopAQCompleted ($a in MainAQMgA) = true
	function nolongerBadAirInside = true 
	//function commands ($a in SysAMdA) = [] //Added in refinement for debugging/logging
	//function commands ($a in SysHMdA) = [] //Added in refinement for debugging/logging
	function desiredHeatingSetting($a in IntHCMgA) = STOPPED
	function loopHCCompleted ($a in IntHCMgA) = true

	function arbiterTurn = false

	agent IntHCMgA: r_IntHC[]
	agent MainAQMgA: r_MainAQ[]
	agent HighAQ_HCMgA: r_HighAQ_HC[]
	agent MainHCMgA: r_MainHC[]
	agent SysHMdA: r_SysA[]
	agent SysAMdA: r_SysH[]
