asm MySmartHomeAQ_HC_refined_with_arbiter

import StandardLibrary

signature:
	//Hierarchical_AQ_HC_MAPE
	domain SysHMdA subsetof Agent
	domain SysAMdA subsetof Agent
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
	
	controlled syshManagedByIntHC: IntHCMgA -> SysHMdA
	controlled sysaManagedByIntHC: IntHCMgA -> SysAMdA
	derived startIntHCM: IntHCMgA -> Boolean
	derived startIntHCE: IntHCMgA -> Boolean
	controlled sysaManagedByMainAQ: MainAQMgA -> Powerset(SysAMdA)
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
	derived orSelectorfromMainHCEtoIntHCE: MainHCMgA -> Powerset(IntHCMgA)
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
	monitored outsideAqi: SysAMdA -> Integer
	monitored aQInsideCO2: SysAMdA -> Integer
	controlled outsideAqiSaved: MainAQMgA -> Integer
	controlled aQInsideCO2Saved: MainAQMgA -> Integer
    monitored windowsStatus: SysAMdA -> WindowStatus
    controlled windowsStatusSaved: MainAQMgA -> WindowStatus
    controlled desiredWindowsStatus: MainAQMgA -> WindowStatus
	controlled windows_Setting: SysAMdA -> WindowStatus
	derived airLevel:Integer -> AirLevel
	derived veryBadAir: Integer -> Boolean //for air outside
	derived co2Level:Integer -> AirLevel
	derived veryBadAirInside: Integer -> Boolean //for air inside
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
	derived  overallWindowsStatus: Powerset(SysAMdA) -> WindowStatus //OPEN, if at least one is open; false otherwise
	derived overallAQInsideCO2: Powerset(SysAMdA) -> Temperature
	
	//**** Function for the coordination of the overall system ****
	controlled sgnMainAQMtoA: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)
	controlled sgnMainAQAtoE: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)

	//Used to strictly sequence different executions of the aggregate MAPE loop HC
	controlled loopAQCompleted: MainAQMgA -> Boolean
	controlled loopHCCompleted: IntHCMgA -> Boolean
	derived allLowerloopsCompleted: Boolean
	//controlled loopHighAQ_HCCompleted: IntHCMgA -> Boolean
	
	derived floorname: SysAMdA -> String //Added in refinement for debugging/logging (floor name in OpenHAB)
	derived floorname: SysHMdA -> String//Added in refinement for debugging/logging
	out commands: SysAMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)
	out commands: SysHMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)
	
	controlled sgnHightoMainHC_E: MainHCMgA -> Boolean//Added in refinement to resolve AQ and HC conflicts
	controlled sgnHightoMainAQ_E: MainAQMgA -> Boolean //Added in refinement to resolve AQ and HC conflicts
	controlled desiredWindowsStatus: HighAQ_HCMgA -> WindowStatus //Added in refinement to resolve AQ and HC conflicts
	controlled sgnMainHCPtoHighA: HighAQ_HCMgA -> Boolean//Added in refinement to resolve AQ and HC conflicts
	controlled sgnMainAQAtoHighA: HighAQ_HCMgA -> Boolean //Added in refinement to resolve AQ and HC conflicts
	
definitions:

function  overallWindowsStatus ($set in Powerset(SysAMdA)) = 
  if (exist $a in $set with windowsStatus($a) = OPEN) then OPEN else CLOSED endif

function overallAQInsideCO2 ($set in Powerset(SysAMdA)) =
  //idiv((sum(<$k in asBag($set): aQInsideCO2($k)>),size($set))
  //(sum(<$k in asBag($set): aQInsideCO2($k)>)/size($set)) //average
  let ($agf=first(asSequence($set)), $aff=last(asSequence($set))) in  
         idiv((aQInsideCO2($agf) + aQInsideCO2($aff)),2)
  endlet
  
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
	function floorname($a in SysAMdA) =
	 if $a = aqs_gf then "_GF" else  "_FF" endif

    //Added in refinement for debugging/logging
	function floorname($a in SysHMdA) =
	 if $a = hs_gf then "_GF" else  "_FF" endif

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
		(exist $a in fromMainHCMtoIntHCM($b) with sgnIntHCMMainHCM($a, $b))

	function startMainHCA($b in MainHCMgA) = //Refined
		(heatingsON and (exist $a in fromMainHCMtoIntHCM($b) with neq(desiredHeatingSetting($a),computeHeatingSetting($a)))) or
		(heatingsON and (exist $c in fromMainHCMtoIntHCM($b) with eq(windowsStatusSaved($c),OPEN)) )
		

	function startMainHCP($b in MainHCMgA) =
	 $b=$b //workaround added in refinement to avoid NullPointerException


	function startMainHCE($b in MainHCMgA) =
		sgnHightoMainHC_E($b)

	function startHighAQ_HCA($b in HighAQ_HCMgA) = //Refined
	  //sgnMainHCPtoHighA(self) and sgnMainAQAtoHighA(self)//PA April 1, 2019
	  sgnMainHCPtoHighA($b) or sgnMainAQAtoHighA($b)
	   //	(not(sgnHightoMainHC_E(main_hc)) and not(sgnHightoMainAQ_E(main_aq))) and
	   //	(isDef(desiredWindowsStatus(main_aq)) and (isDef(desiredWindowsStatus(int_hc_ff)) or
	   //	isDef(desiredWindowsStatus(int_hc_gf)))) 
		

	function startHighAQ_HCE($b in HighAQ_HCMgA) = 
	   $b=$b //workaround added in refinement to avoid NullPointerException
	   	

	function allLowerloopsCompleted = 
	 ((forall $a in IntHCMgA with loopHCCompleted($a)) and 
	  (forall $b in MainAQMgA with loopAQCompleted($b)))
	  
	  
	rule r_SysA = //Added in refinement for debugging/logging
		if isDef(windows_Setting(self)) then
		 par 
		  if (windows_Setting(self) = CLOSED) 
		  then //commands(self) := append([],"WindowsStatus.sendCommand(CLOSED)") 
		  	 commands(self) := append(append([],"WindowsGF.sendCommand(CLOSED)"),"WindowsFF.sendCommand(CLOSED)")
	  	  else //windows_Setting(self) = OPEN 
		     //commands(self) := append([],"WindowsStatus.sendCommand(OPEN)") endif
		     commands(self) := append(append([],"WindowsGF.sendCommand(OPEN)"),"WindowsFF.sendCommand(OPEN)") endif
	  	  windows_Setting(self):= undef
	  	 endpar	
	  	endif

	rule r_SysH = //Added in refinement for debugging/logging
	 	let ($item = concat("Heating_Setting",floorname(self))) in
		  if isDef(heating_Setting(self)) then
			switch(heating_Setting(self))
			case STOPPED: commands(self) :=  append([],concat($item,".sendCommand(0)"))
			case FAIRLY_HOT: commands(self) :=  append([],concat($item,".sendCommand(5)"))
			case VERY_HOT: commands(self) := append([],concat($item,".sendCommand(10)"))
		    endswitch
		  endif
		endlet
		

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
			sgnMainAQAtoHighA(self):= false
		endpar

	rule r_MainAQE =
		if startMainAQE(self) then
			par
				forall  $a in sysaManagedByMainAQ(self) do
					windows_Setting($a):= desiredWindowsStatus(self)//Added in refinement
				r_CleanUp_MainAQE[]
			endpar
		//else //if no adaptation was enabled by the arbiter yet, cleaning of the flag from A to E is done here to allow the E component restart
		//	sgnMainAQAtoE(self):= true //it's still true
		endif

	
	rule r_CleanUp_MainAQA =
		sgnMainAQMtoA(self) := false //Added in refinement
    
		    
rule r_checkAirAdaptationRequired =
    //First, check if adaptation is required for external bad air
	  if (veryBadAir(outsideAqiSaved(self)) and eq(windowsStatusSaved(self),OPEN)) then
			  	par
					desiredWindowsStatus(self) := CLOSED
					sgnMainAQAtoE(self) := true //start E
					sgnMainAQAtoHighA(high_aq_hs):= true //alert the arbiter
				endpar
	//Second, check if adaptation is required for refreshing air inside	
	  else if (veryBadAirInside(aQInsideCO2Saved(self)) and not(veryBadAir(outsideAqiSaved(self)))) then
			    par
			       nolongerBadAirInside := false
			       desiredWindowsStatus(self) := OPEN
     			   sgnMainAQAtoE(self) := true //start E
     			   sgnMainAQAtoHighA(high_aq_hs):= true //alert the arbiter
			    endpar 
    //Third, check if adaptation is required for closing the windows after the air inside was refreshed	
	  else if (not veryBadAirInside(aQInsideCO2Saved(self)) and windowsStatusSaved(self) = OPEN and not nolongerBadAirInside) then
			    par //restore normal status
			       nolongerBadAirInside := true
			       desiredWindowsStatus(self) := CLOSED
				   sgnMainAQAtoE(self) := true //start E
				   sgnMainAQAtoHighA(high_aq_hs):= true //alert the arbiter
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
			outsideAqiSaved(self) := outsideAqi(first(asSequence(sysaManagedByMainAQ(self))))
			windowsStatusSaved(self) := overallWindowsStatus(sysaManagedByMainAQ(self))
			aQInsideCO2Saved(self):= overallAQInsideCO2(sysaManagedByMainAQ(self))
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
	 par 
	     sgnHightoMainHC_E(self) := false//Added in refinement
		 //sgnMainHCPtoHighA(high_aq_hs):= false//PA April 1, 2019
		 skip
	 endpar	    
	
	rule r_MainHCE =//refined
		if startMainHCE(self) then
			par
				if isDef(desiredWindowsStatus(high_aq_hs)) then desiredWindowsStatus(self) := desiredWindowsStatus(high_aq_hs) endif //Added in refinement for the arbiter
				forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
					sgnMainHCEIntHCE(self, $a) := true
				r_CleanUp_MainHCE[]
			endpar
		else //if no adaptation was enabled by the arbiter yet, cleaning of loop completion flags is done here to allow the loop restart
		   //par
			 //sgnMainHCPtoHighA(high_aq_hs):= false//PA change April 1, 2019 
			 forall $b in fromMainHCMtoIntHCM(self) with loopHCCompleted($b) = false do
				loopHCCompleted($b) := true	
		   //endpar	
		endif
		
				
	rule r_CleanUp_MainHCP =
		skip //<<TODO>>

	rule r_MainHCP =
		if startMainHCP(self) then
			par
				forall $a in fromMainHCMtoIntHCM(self) do  //Added in refinement
				   par
					desiredHeatingSetting($a) := computeHeatingSetting($a)
				    if (heatingsON and windowsStatusSaved($a) = OPEN) then
				    par
				    	desiredWindowsStatus($a) := CLOSED
				    	sgnMainHCPtoHighA(high_aq_hs):=  true //alert the arbiter 
				    endpar
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
		skip

	rule r_HighAQ_HCE =
		if startHighAQ_HCE(self) then //Added in refinement: just an example on how to resolve conflicts on windows setting. 
	 		par //If the AQ location value desiredWindowsStatus is def (discordant or not in value) with that of the HC, undo adaptations from the HC by changing the desired setting to that of AQ
				//desiredWindowsStatus(int_hc_ff):=  desiredWindowsStatus(main_aq)
				//desiredWindowsStatus(int_hc_gf):= desiredWindowsStatus(main_aq)
				if (isDef(desiredWindowsStatus(main_aq))) then desiredWindowsStatus(self):= desiredWindowsStatus(main_aq) 
				else desiredWindowsStatus(self):= undef endif
				if (not(sgnHightoMainHC_E(main_hc))) then sgnHightoMainHC_E(main_hc):= true endif
				if (not(sgnHightoMainAQ_E(main_aq))) then sgnHightoMainAQ_E(main_aq):= true endif
				r_CleanUp_HighAQ_HCE[]
			endpar
		endif

	rule r_CleanUp_HighAQ_HCA =
	   par 
	   		//if isDef(desiredWindowsStatus(self)) then desiredWindowsStatus(self):= undef endif
	   		//if not(sgnHightoMainHC_E(main_hc)) then sgnHightoMainHC_E(main_hc):= true endif
	   		//if not(sgnHightoMainAQ_E(main_aq)) then sgnHightoMainAQ_E(main_aq):= true endif
	   		//sgnMainHCPtoHighA(self):= false //does inconsistent update here
		    //sgnMainAQAtoHighA(self):= false
		    if sgnMainHCPtoHighA(self) then sgnMainHCPtoHighA(self) := false endif
		    if sgnMainAQAtoHighA(self) then sgnMainAQAtoHighA(self) := false endif
	   endpar  
	 

	rule r_HighAQ_HCA =
		if startHighAQ_HCA(self) then 
			  r_HighAQ_HCE[] //Adaptation required
		else  
			r_CleanUp_HighAQ_HCA[] //Adaptation not required
		endif
		
	rule r_HighAQ_HC =
		r_HighAQ_HCA[]

	main rule r_mainRule =
	 //seq 
	    par //All managing agents run in parallel
	    	forall $a in IntHCMgA do program($a)
			forall $b in MainHCMgA do program($b)
			forall $c in MainAQMgA do program($c)
			forall $d in HighAQ_HCMgA do program($d)
		endpar	 
       // forall $d in HighAQ_HCMgA do program($d)
         //The managed agents run sequentially
		//forall $e in SysAMdA do program($e) 
		//forall $f in SysHMdA do program($f) 
	 //endseq	
	 

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

	function sysaManagedByMainAQ($x in MainAQMgA) =
		switch($x)
			case main_aq: {aqs_gf, aqs_ff}
		endswitch

	function syshManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: hs_gf
			case int_hc_ff: hs_ff
		endswitch

	function sysaManagedByIntHC($x in IntHCMgA) =
		switch($x)
			case int_hc_gf: aqs_gf
			case int_hc_ff: aqs_ff
		endswitch


	 //Added in refinement
    function sgnMainAQMtoA($a in MainAQMgA) = false
    function sgnMainAQAtoE($a in MainAQMgA) = false
    function sgnHightoMainHC_E($a in MainHCMgA)=false //Added in refinement to resolve AQ and HC conflicts
    function sgnHightoMainAQ_E($a in MainAQMgA)=false //Added in refinement to resolve AQ and HC conflicts
    function sgnMainHCPtoHighA($a in HighAQ_HCMgA)= false //Added in refinement to resolve AQ and HC conflicts
	function sgnMainAQAtoHighA($a in HighAQ_HCMgA) = false //Added in refinement to resolve AQ and HC conflicts
	
	//function desiredWindowsStatus($a in MainAQMgA) = CLOSED
	function loopAQCompleted ($a in MainAQMgA) = true
	function nolongerBadAirInside = true 
	function commands ($a in SysAMdA) = [] //Added in refinement for debugging/logging
	function commands ($a in SysHMdA) = [] //Added in refinement for debugging/logging
	function desiredHeatingSetting($a in IntHCMgA) = STOPPED
	function loopHCCompleted ($a in IntHCMgA) = true
	
	agent IntHCMgA: r_IntHC[]
	agent MainAQMgA: r_MainAQ[]
	agent HighAQ_HCMgA: r_HighAQ_HC[]
	agent MainHCMgA: r_MainHC[]
	agent SysHMdA: r_SysA[]
	agent SysAMdA: r_SysH[]
