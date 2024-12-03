//Adaptation goal: maximize air quality inside the home
//Adaptation actions: (i) when the air quality inside is bad and worst of the outside air, the rooms windows 
//are open to restore good values. For simplicity, we consider the average value of CO2 (ppm) as collected by 
//the all home air sensors and evaluate it according to the following classification levels: 
//340-600 (Good), 601-1000 (Moderate), 1001-1500 (Unhealthy), > 1501 (Hazardous). 
//(ii) when the outside air is very bad, open doors are closed; the web service www.aqicn.org is used to obtain an air quality index based on Pm10 levels (mg/m^3). This index
//is then evaluated according to the classification levels of epa.gov: 0-50 (Good), 51-100 (Moderate),
// 101-300 (Unhealthy), > 300 (Hazardous).

asm MySmartHomeAQ_refined_forMC

import ../../../../STDL/StandardLibrary
import ../../../../STDL/LTLLibrary

signature:
	//AQ_MAPE
	domain AirMdA subsetof Agent
	domain MainAQMgA subsetof Agent
	domain AirDomain subsetof Integer
	domain Co2Domain subsetof Integer
	
	//domains added in refinement
	enum domain AirLevel = {GOOD|MODERATE|UNHEALTHY|HAZARDOUS|INVALID} //by www.epa.gov (based on Pm10 levels)
	enum domain WindowStatus = {OPEN|CLOSED} //OPEN means that at least one window is open
		
	controlled airManagedByMainAQ: MainAQMgA -> AirMdA
	derived startMainAQM: MainAQMgA -> Boolean
	derived startMainAQA: MainAQMgA -> Boolean
	//derived startMainAQP: MainAQMgA -> Boolean
	derived startMainAQE: MainAQMgA -> Boolean
	//MySmartHomeAQ
	static aqs: AirMdA
	static aq_main: MainAQMgA
	
    //functions added in refinement
	//managed items' state
	monitored outsideAqi: AirMdA -> AirDomain
	monitored aQInsideCO2: AirMdA -> Co2Domain
	controlled outsideAqiSaved: MainAQMgA -> AirDomain
	controlled aQInsideCO2Saved: MainAQMgA -> Co2Domain
    monitored windowsStatus: AirMdA -> WindowStatus
    controlled windowsStatusSaved: MainAQMgA -> WindowStatus
    controlled desiredWindowsStatus: MainAQMgA -> WindowStatus
	controlled windows_Setting: AirMdA -> WindowStatus
	derived airLevel: AirDomain -> AirLevel
	derived veryBadAir: AirDomain -> Boolean //for air outside
	derived co2Level: Co2Domain -> AirLevel
	derived veryBadAirInside: Co2Domain -> Boolean //for air inside
	controlled nolongerBadAirInside: Boolean //for restoring a normal situation after refreshing air inside
	
	//**** Function for the coordination of the overall system ****
	controlled sgnMainAQMtoA: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)
	controlled sgnMainAQAtoE: MainAQMgA -> Boolean //used for indirect interaction (instead of waterfall)

	//Used to strictly sequence different executions of the aggregate MAPE loop HC
	controlled loopAQCompleted: MainAQMgA -> Boolean
	//controlled adapting: Boolean //to realize an alternate execution schema between managing and managed agents
	//derived floorname: AirMdA -> String //Added in refinement for debugging/logging (floor name in OpenHAB)
	//out commands: AirMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)


definitions:
	domain AirDomain = {-1 : 3}
	domain Co2Domain = {0 : 4}
	
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
   
	function startMainAQM($b in MainAQMgA) =
		loopAQCompleted($b)  //***coordination***: for sequencing different MAPE loops executions

	function startMainAQA($b in MainAQMgA) = sgnMainAQMtoA($b) 

	//function startMainAQP($b in MainAQMgA) = $b=$b

	function startMainAQE($b in MainAQMgA) = sgnMainAQAtoE($b)

	rule r_Air = //Added in refinement
		//if isDef(windows_Setting(self)) then windows_Setting(self):= undef endif
		//Added in refinement for debugging/logging
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
	  	
	rule r_CleanUp_MainAQE =
		par
			sgnMainAQAtoE(self) := false
			loopAQCompleted(self) := true //Added in refinement
		endpar

	rule r_MainAQE =
		if startMainAQE(self) then
			par
				windows_Setting(airManagedByMainAQ(self)):= desiredWindowsStatus(self)//Added in refinement
				r_CleanUp_MainAQE[]
			endpar
		endif

	 //Not used
	/*
	rule r_CleanUp_MainAQP =
		skip //<<TODO>>

   rule r_MainAQP =
		if startMainAQP(self) then
			par
				skip //<<TODO>>
				r_MainAQE[]
				r_CleanUp_MainAQP[]
			endpar
		endif
    */
    
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
    
    
	rule r_CleanUp_MainAQA =
		sgnMainAQMtoA(self) := false //Added in refinement
		
	rule r_MainAQA = //refined
		if startMainAQA(self) then
			par 
			  r_checkAirAdaptationRequired[]
			  r_CleanUp_MainAQA[] 
			endpar
		endif 
		
	//Added in refinement
    //It saves the air quality-related data (sensors' values inside and the air level outside) and windows status into the knowledge
	rule r_SaveSensorsData =
		par
			outsideAqiSaved(self) := outsideAqi(airManagedByMainAQ(self))
			windowsStatusSaved(self) := windowsStatus(airManagedByMainAQ(self))
			aQInsideCO2Saved(self):= aQInsideCO2(airManagedByMainAQ(self))
		endpar
		
	rule r_CleanUp_MainAQM =
		loopAQCompleted(self) := false //added in refinement

	rule r_MainAQM =
		if startMainAQM(self) then
			par
				r_SaveSensorsData[] //added in refinement
				//r_MainAQA[] //not used
				sgnMainAQMtoA(self) := true //start A
				r_CleanUp_MainAQM[]
			endpar
		endif

	rule r_MainAQ =  //Refined
		par 
			r_MainAQM[]
			r_MainAQA[]
			r_MainAQE[]
		endpar

	//LTLSPEC g(((veryBadAirInside(aQInsideCO2Saved(aq_main)) and not(veryBadAir(outsideAqiSaved(aq_main))))) implies f(windows_Setting(aqs) = OPEN))
	LTLSPEC g(((aQInsideCO2Saved(aq_main)>=3 and not(outsideAqiSaved(aq_main)>=2))) implies f(windows_Setting(aqs) = OPEN))
	LTLSPEC g((outsideAqiSaved(aq_main) > 1 and windowsStatusSaved(aq_main) = OPEN)
				implies f(windows_Setting(aqs) = CLOSED))

	main rule r_mainRule =
		/*forall $a in Agent with true do
			program($a) */
	 par//seq
	 	forall $a in MainAQMgA do program($a)
	 	forall $b in AirMdA do program($b) 
	 endpar//endseq
	 
default init s0:
    function airManagedByMainAQ($x in MainAQMgA) =
		/*switch($x)
			case aq_main: aqs
		endswitch*/
		aqs
        
    //Added in refinement
    function sgnMainAQMtoA($a in MainAQMgA) = false
    function sgnMainAQAtoE($a in MainAQMgA) = false
	function desiredWindowsStatus($a in MainAQMgA) = CLOSED
	function loopAQCompleted ($a in MainAQMgA) = true
	function nolongerBadAirInside = true 
	//function commands ($a in AirMdA) = [] //Added in refinement for debugging/logging
	//function adapting = true
	
	agent MainAQMgA: r_MainAQ[]
	agent AirMdA: r_Air[]
