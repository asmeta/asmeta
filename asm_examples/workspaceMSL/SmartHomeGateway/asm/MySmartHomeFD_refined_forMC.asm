//Adaptation goal: guarantee fire detection
//Adaptation actions: Being a domestic building, fire detection takes the form of a fire alarm system, incorporating three smoke devices in two zones: one in the Corridor (First Floor), and two in the
//kitchen and LivingRoom (Ground floor). In addition, if the temperature of a specific room is higher than 45�C the fire alarm is set to ON. To compute this  
//we consider the max temperature of all room temperatures per each floor and compare it with the threeshold 45�C.  
 
//Managed items: group switch gSmokeFF, gSmokeGF, Number maxTemperatureGF, maxTemperatureFF, Switch  fireAlarmGF, Switch  fireAlarmFF 
asm MySmartHomeFD_refined_forMC

import StandardLibrary
import LTLLibrary

signature:
	//FD_MAPE
	domain FireDetectionMdA subsetof Agent
	domain MasterFDMgA subsetof Agent
	domain SlaveFDMgA subsetof Agent
	//domains added in refinement
	enum domain AlarmStatus = {ON|OFF}
	domain Temperature subsetof Integer

	derived startMasterFDA: MasterFDMgA -> Boolean
	derived startMasterFDP: MasterFDMgA -> Boolean
	controlled firedetectionManagedBySlaveFD: SlaveFDMgA -> FireDetectionMdA
	derived startSlaveFDM: SlaveFDMgA -> Boolean
	derived startSlaveFDE: SlaveFDMgA -> Boolean
	//I: SlaveFD.M -> MasterFD.A [*-SOME,1]
	controlled sgnSlaveFDMMasterFDA: Prod(SlaveFDMgA, MasterFDMgA) -> Boolean
	controlled fromSlaveFDMtoMasterFDA: SlaveFDMgA -> MasterFDMgA
	//controlled fromMasterFDAtoSlaveFDM: MasterFDMgA -> Powerset(SlaveFDMgA)
	//I: MasterFD.P -> SlaveFD.E [1,*-SOME]
	controlled sgnMasterFDPSlaveFDE: Prod(MasterFDMgA, SlaveFDMgA) -> Boolean
	//controlled fromMasterFDPtoSlaveFDE: MasterFDMgA -> Powerset(SlaveFDMgA)
	//derived orSelectorfromMasterFDPtoSlaveFDE: MasterFDMgA -> Powerset(SlaveFDMgA)
	controlled fromSlaveFDEtoMasterFDP: SlaveFDMgA -> MasterFDMgA
	//MySmartHomeFD
	static fd_ff: FireDetectionMdA
	static fd_gf: FireDetectionMdA
	static fd_master: MasterFDMgA
	static gf_slave: SlaveFDMgA
	static ff_slave: SlaveFDMgA

    //functions added in refinement
	//managed items' state
	monitored maxTemperature: FireDetectionMdA -> Temperature
    controlled maxTemperatureSaved: SlaveFDMgA -> Temperature
    monitored gsmoke: FireDetectionMdA -> AlarmStatus
    controlled gsmokeSaved: SlaveFDMgA -> AlarmStatus
	controlled desiredFireAlarmSetting: SlaveFDMgA -> AlarmStatus
	controlled fireAlarm: FireDetectionMdA -> AlarmStatus
	derived detectFire: SlaveFDMgA -> AlarmStatus
	//derived floorname: SlaveFDMgA -> String //Added in refinement for debugging/logging (floor name in OpenHAB)
	//out commands: FireDetectionMdA ->  Seq(String) //Added in refinement for debugging/logging (sequence of OpenHAB commands)

	//**** Function for the coordination of the overall system ****
	//Used to strictly sequence different executions of the aggregate MAPE loop HC
	controlled loopFDCompleted: SlaveFDMgA -> Boolean
	derived allSubloopsFDCompleted: Boolean
	
definitions:
	domain Temperature = {0 : 5}
    //Added in refinement for debugging/logging
	//function floorname($a in SlaveFDMgA) = if $a = ff_slave then "_FF" else  "_GF" endif

   	//Added in refinement
    function allSubloopsFDCompleted = (forall $a in SlaveFDMgA with loopFDCompleted($a))

	//Added in refinement
	function detectFire($a in SlaveFDMgA) =	
	 if gsmokeSaved($a) = ON then ON //first, check if the smoke sensor is active
	 else //otherwise, check if the max temperature overtakes the threshold 45C�
		if isDef( maxTemperatureSaved($a) ) then
			//for model checking, using 4 instead of 45
			if (maxTemperatureSaved($a) >= 4) then ON else OFF endif 
		else OFF //No further knowledge to detect a fire
		endif
	 endif
		
	function startMasterFDA($b in MasterFDMgA) = //Refined
		//(exist $a in fromMasterFDAtoSlaveFDM($b) with (sgnSlaveFDMMasterFDA($a, $b) and detectFire($a) = ON) )
		(sgnSlaveFDMMasterFDA(gf_slave, $b) and detectFire(gf_slave) = ON) or (sgnSlaveFDMMasterFDA(ff_slave, $b) and detectFire(ff_slave) = ON)


	function startMasterFDP($b in MasterFDMgA) =
		$b=$b //workaround added in refinement to avoid NullPointerException

	function startSlaveFDM($b in SlaveFDMgA) =
		loopFDCompleted($b)  //***coordination***: for sequencing different MAPE loops execution
		
	function startSlaveFDE($b in SlaveFDMgA) =
		sgnMasterFDPSlaveFDE(fromSlaveFDEtoMasterFDP($b), $b)

	//function orSelectorfromMasterFDPtoSlaveFDE($b in MasterFDMgA) =
	//	chooseone({$a in Powerset(SlaveFDMgA) | not(isEmpty($a)): $a})


	rule r_FDsys = //Refined for debugging/logging
		if isDef(fireAlarm(self)) then 
			/*par 
			  if fireAlarm(self) = OFF then commands(self) := append([],concat(concat("fireAlarm",floorname(self)),".sendCommand(OFF)"))  
		  	  else //ON 
			  	  commands(self) := append([],concat(concat("fireAlarm",floorname(self)),".sendCommand(ON)")) 
		  	  endif*/ 	        
	  	      fireAlarm(self) := undef
	  	    //endpar
	  	//else commands(self) := [] //none command    
	    endif
	  
	rule r_CleanUp_MasterFDP =
		skip //<<TODO>>

	rule r_MasterFDP =
		if startMasterFDP(self) then
			par
				//forall $a in orSelectorfromMasterFDPtoSlaveFDE(self) do 
				/*forall $a in fromMasterFDAtoSlaveFDM(self) with sgnSlaveFDMMasterFDA($a, self) do 
				  par	
				    desiredFireAlarmSetting($a) := detectFire($a)
					sgnMasterFDPSlaveFDE(self, $a) := true
				  endpar*/
				//{gf_slave, ff_slave}
				par
					if sgnSlaveFDMMasterFDA(gf_slave, self) then
						par
						    desiredFireAlarmSetting(gf_slave) := detectFire(gf_slave)
							sgnMasterFDPSlaveFDE(self, gf_slave) := true
						endpar
					endif
					if sgnSlaveFDMMasterFDA(ff_slave, self) then
						par 
						    desiredFireAlarmSetting(ff_slave) := detectFire(ff_slave)
							sgnMasterFDPSlaveFDE(self, ff_slave) := true
						endpar
					endif
				  endpar	
				r_CleanUp_MasterFDP[]
			endpar
		endif

	rule r_CleanUp_MasterFDA =
		//forall $a in fromMasterFDAtoSlaveFDM(self) do sgnSlaveFDMMasterFDA($a, self) := false
		par
			sgnSlaveFDMMasterFDA(gf_slave, self) := false
			sgnSlaveFDMMasterFDA(ff_slave, self) := false
		endpar

	rule r_MasterFDA =
		if startMasterFDA(self) then
			par
				r_MasterFDP[]
				r_CleanUp_MasterFDA[]
			endpar
		else //if no adaptation was necessary, cleaning of loop completion flags is done here
			/*forall $a in fromMasterFDAtoSlaveFDM(self) with loopFDCompleted($a) = false do
				loopFDCompleted($a) := true*/
			//{gf_slave, ff_slave}
			par
				if not(loopFDCompleted(gf_slave)) then
					loopFDCompleted(gf_slave) := true
				endif
				if not(loopFDCompleted(gf_slave)) then
					loopFDCompleted(gf_slave) := true
				endif
			endpar
		endif

	rule r_MasterFD =
		r_MasterFDA[]

    //Added in refinement
    //It saves the temperature-related data and smoke sensors status into the knowledge
	rule r_SaveSensorsData =
		par
			gsmokeSaved(self) := gsmoke(firedetectionManagedBySlaveFD(self))
		    maxTemperatureSaved(self) := maxTemperature(firedetectionManagedBySlaveFD(self))
		endpar
		
	rule r_CleanUp_SlaveFDM =
		loopFDCompleted(self) := false //added in refinement

	rule r_SlaveFDM =
		if startSlaveFDM(self) then
			par
				r_SaveSensorsData[] //added in refinement
				if not sgnSlaveFDMMasterFDA(self,fromSlaveFDMtoMasterFDA(self)) then sgnSlaveFDMMasterFDA(self,fromSlaveFDMtoMasterFDA(self)) := true endif
				r_CleanUp_SlaveFDM[]
			endpar
		endif

	rule r_CleanUp_SlaveFDE = //Refined
		par	
			sgnMasterFDPSlaveFDE(fromSlaveFDEtoMasterFDP(self), self) := false
			loopFDCompleted(self) := true
		endpar

	rule r_SlaveFDE =
		if startSlaveFDE(self) then
			par
				fireAlarm(firedetectionManagedBySlaveFD(self)):= desiredFireAlarmSetting(self) //Added in refinement
				r_CleanUp_SlaveFDE[]
			endpar
		endif

	rule r_SlaveFD =
		par
			r_SlaveFDM[]
			r_SlaveFDE[]
		endpar

	LTLSPEC g(maxTemperatureSaved(gf_slave) >= 4 implies f(desiredFireAlarmSetting(gf_slave) = ON))
	LTLSPEC g(maxTemperatureSaved(ff_slave) >= 4 implies f(desiredFireAlarmSetting(ff_slave) = ON))

	main rule r_mainRule =
		//forall $a in Agent with true do program($a)
	 /*seq
	    par
			forall $a in SlaveFDMgA do program($a) 
			forall $b in MasterFDMgA do program($b)
		endpar		
		    forall $c in FireDetectionMdA do program($c)
	 endseq*/
	 par
		forall $a in SlaveFDMgA do program($a) 
		forall $b in MasterFDMgA do program($b)
		//forall $c in FireDetectionMdA do program($c)
	endpar	

default init s0:
	function sgnSlaveFDMMasterFDA($a in SlaveFDMgA, $b in MasterFDMgA) = false
	function sgnMasterFDPSlaveFDE($a in MasterFDMgA, $b in SlaveFDMgA) = false
	function fromSlaveFDMtoMasterFDA($a in SlaveFDMgA) =
		/*switch($a)
			case gf_slave: fd_master
			case ff_slave: fd_master
		endswitch*/
		fd_master

	/*function fromMasterFDAtoSlaveFDM($a in MasterFDMgA) =
		switch($a)
			case fd_master: {gf_slave, ff_slave}
		endswitch*/

	/*function fromMasterFDPtoSlaveFDE($a in MasterFDMgA) =
		switch($a)
			case fd_master: {gf_slave, ff_slave}
		endswitch*/

	function fromSlaveFDEtoMasterFDP($a in SlaveFDMgA) =
		/*switch($a)
			case gf_slave: fd_master
			case ff_slave: fd_master
		endswitch*/
		fd_master

	function firedetectionManagedBySlaveFD($x in SlaveFDMgA) =
		/*switch($x)
			case gf_slave: fd_gf
			case ff_slave: fd_ff
		endswitch*/
		if $x = gf_slave then
			fd_gf
		else
			fd_ff
		endif

    //Added in refinement
	//function desiredFireAlarmSetting($a in SlaveFDMgA) = OFF
	function loopFDCompleted ($a in SlaveFDMgA) = true
	//function commands ($a in FireDetectionMdA) = [] //Added in refinement for debugging/logging

	agent FireDetectionMdA: r_FDsys[]
	agent MasterFDMgA: r_MasterFD[]
	agent SlaveFDMgA: r_SlaveFD[]
