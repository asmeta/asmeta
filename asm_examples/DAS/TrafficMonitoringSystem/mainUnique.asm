asm mainUnique
//main ASM: Camera system of systems

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

signature:
	//CameraAgentSubsystem
	domain Camera subsetof Agent
	enum domain CameraState = {MASTER | SLAVE | MASTERWITHSLAVES | FAILED}
	domain TrafficMonitor subsetof Agent //sensor
	enum domain TrafficMonitorState = {CONGESTION | NOCONGESTION}



	//OrganizationMiddleware
	domain OrganizationController subsetof Agent





	//SelfHealingSubsystem
	domain SelfHealingController subsetof Agent
	enum domain HealingState = {START | WAITFORRESPONSE | FAILED2}





	//main
	//Camera system 1
	static c1: Camera
	static trafficMonitor1: TrafficMonitor
	static organizationController1: OrganizationController
	static selfHealingController1: SelfHealingController
	
	//Camera system 2
	static c2: Camera
	static trafficMonitor2: TrafficMonitor
	static organizationController2: OrganizationController
	static selfHealingController2: SelfHealingController
	
	//Camera system 3
	static c3: Camera
	static trafficMonitor3: TrafficMonitor
	static organizationController3: OrganizationController
	static selfHealingController3: SelfHealingController
	
	//Camera system 4
	static c4: Camera
	static trafficMonitor4: TrafficMonitor
	static organizationController4: OrganizationController
	static selfHealingController4: SelfHealingController






	//CameraAgentSubsystem
	//static id : Camera -> Integer //PA: commentato perche' non viene usato
	controlled stateC: Camera -> CameraState
	//Functions shared with OrganizationController
	/* controlled master: Camera -> Boolean
	controlled slave: Camera -> Boolean
	controlled masterWithSlaves: Camera -> Boolean */

	//controlled state2: TrafficMonitor -> TrafficMonitorState 
	//controlled cars: TrafficMonitor -> Integer //PA: commentato perche' usato in r_trafficMonitor che non viene usato
	//static capacity: Integer //PA: commentato perche' viene usato in r_trafficMonitor che non viene usato
	//ID of the monitored camera
	//static cameraTM: TrafficMonitor -> Camera  //PA: commentato perche' usato in r_trafficMonitor che non viene usato
	monitored camEnter: Camera -> Boolean
	monitored camLeave: Camera -> Boolean

	//Functions shared with OrganizationController
	controlled cong: Camera -> Boolean
	controlled no_cong: Camera -> Boolean







	//Knowledge
	derived isMaster: Camera -> Boolean
	derived isMasterWithSlaves: Camera -> Boolean
	derived isSlave: Camera -> Boolean

	//neighboring camera in the traffic direction, 
	//to define in the main ASM
	static nextNeighbor: Camera -> Camera 
	static prevNeighbor: Camera -> Camera
	//We distinguish between the phisical next camera in the traffic direction by the function neighbor, and the
	//the first next alive camera in the traffic direction by the function next.
	derived next: Camera -> Camera // Next camera not in FAILED state in the traffic direction.
	derived prev: Camera -> Camera // Previous camera not in FAILED state in the traffic direction.

	derived hasNext: Camera -> Boolean 

	//Get the master of a given slave camera
	controlled getMaster : Camera -> Camera 

	//shared functions with other organization controllers (declared controlled for simulation purposes)
	controlled s_offer : Camera -> Boolean
	derived m_offer: Camera -> Boolean
	controlled newSlave : Prod(Camera,Camera)-> Boolean
	controlled change_master : Camera -> Boolean
	//controlled slaves: Camera -> Seq(Camera) //PA: not supported by model checking
	controlled slaves: Prod(Camera, Camera) -> Boolean //Is the second camera (second argument) slave of the first camera (first argument)?

	//Functions shared among self-healers and organization controllers
	controlled slaveGone: Prod(Camera,Camera)-> Boolean 
	controlled masterGone : Camera -> Boolean

	derived allSlavesAlive: Camera -> Boolean
	derived slavesNotAlive: Camera -> Boolean

	//For the PING-ECHO mechanism:
	//Functions shared between various SealfHealingController agents 
	controlled imAlive: Prod(Camera,Camera)-> Boolean
	controlled isAlive: Camera -> Boolean

	monitored startCam: Camera -> Boolean
	monitored stopCam: Camera -> Boolean









	//OrganizationMiddleware
	derived stateOC: OrganizationController -> CameraState
	static cameraOC: OrganizationController -> Camera //ID of the camera
	controlled congested: OrganizationController -> Boolean //flag
	monitored congestion: Camera -> Boolean //for debugging






	//SelfHealingSubsystem
	controlled stateSHC: SelfHealingController -> HealingState
	//ID of the camera
	static cameraSHC: SelfHealingController -> Camera

	//To separate the issues related to (an implementation of) the timing model from the analysis of the user requirements, 
	//we use two so-called monitored locations elapsed_wait_time, elapsed_alive_time. 
	//Their truth values are assumed to be controlled correctly by the environment and 
	//to indicate when the intended time periods have elapsed.
	monitored elapsed_wait_time: SelfHealingController -> Boolean
	monitored elapsed_alive_time: SelfHealingController -> Boolean
	monitored elapsed_wait_time_plus_delta: SelfHealingController -> Boolean

definitions:
	//START: -- main module -------------------
	function cameraSHC($a in SelfHealingController) =
		   switch($a)
				case selfHealingController1: c1
				case selfHealingController2: c2
				case selfHealingController3: c3
				case selfHealingController4: c4
		  endswitch		
				
	function cameraOC($a in OrganizationController) =
		   switch($a)		
				case organizationController1: c1
				case organizationController2: c2
				case organizationController3: c3
				case organizationController4: c4
	     endswitch
	
	function nextNeighbor($c in Camera) = 
	      switch($c)
				case c1 : c2
				case c2 : c3
				case c3 : c4
				case c4 : undef
		  endswitch
	
	function prevNeighbor($c in Camera) = 
	      switch($c)
				case c1 : undef
				case c2 : c1
				case c3 : c2
				case c4 : c3
		  endswitch
	//END: -- main module -------------------







	//START: -- Knowledge -------------------
	function isMaster($c in Camera) = ( stateC($c) = MASTER or stateC($c) = MASTERWITHSLAVES ) 
	function isMasterWithSlaves($c in Camera) =  stateC($c) = MASTERWITHSLAVES
	function isSlave($c in Camera) =  stateC($c) = SLAVE
	function hasNext($c in Camera) = isDef(next($c))

	function next($c in Camera) = 
     if isUndef(nextNeighbor($c)) then undef
     else if stateC(nextNeighbor($c)) = FAILED then next(nextNeighbor($c))
     else nextNeighbor($c) endif endif

	function prev($c in Camera) = 
     if isUndef(prevNeighbor($c)) then undef
     else if stateC(prevNeighbor($c)) = FAILED then prev(prevNeighbor($c))
     else prevNeighbor($c) endif endif

	function slavesNotAlive($c in Camera) =
		//PA: slaves changed because of model checking
		//(exist $s in Camera with ( contains(slaves($c), $s) and  slaveGone($c, $s) ))
		(exist $s in Camera with (slaves($c, $s) and  slaveGone($c, $s)))

	function allSlavesAlive($c in Camera) =
		//PA: slaves changed because of model checking
		//( forall $s in Camera with ( contains(slaves($c), $s) and  imAlive($c, $s) ))
		( forall $s in Camera with (slaves($c, $s) and imAlive($c, $s)))

	function m_offer($c in Camera) =
	   (exist $s in Camera with newSlave($c, $s) )
	//END: -- Knowledge -------------------









	//START: -- OrganizationMiddleware -------------------
	function stateOC($c in OrganizationController) =
		//PA: for model checking
		//stateC(cameraOC($c))
		let ($cameraOCc = cameraOC($c)) in
			stateC($cameraOCc)
		endlet
	//END: -- OrganizationMiddleware -------------------	
		
		


	//START: -- main module -------------------
	macro rule r_skip = skip //for debugging

	//END: -- main module -------------------








	//START: -- CameraAgentSubsystem -------------------
	/* 
	We prefer for control state ASMs the parallel synchronous understanding of ASMs as firing in each step every rule (by the par rule). We control possible conflicts, e.g. by taking care that the rule guards of rules fireable in a control state are disjoint. In case of possible conflicts we sequencialize the transitions giving priority to certain events (e.g., those coming from the environment) that cannot be delayed.
	
	Here for the camera behavior, we give priority to the event stopCam from the environment (a monitored function). We assume the other guards are disjoint since related to actions executed by the same agent (the organization controller) in two separate modes. 
	*/
	macro rule r_cameraBehavior =
	    skip
	//END: -- CameraAgentSubsystem -------------------










	//START: -- OrganizationMiddleware -------------------
	//Macro rules for atomic adaptation actions in a master/slave organization
 
	//@E 
	macro rule r_clearSlaves =
		forall $s in Camera do
			slaves(cameraOC(self), $s) := false

	//@E
	macro rule r_addSlave ($s in Camera) =
		slaves(cameraOC(self), $s) := true

	//@E 
	macro rule r_removeSlave ($s in Camera) =
		slaves(cameraOC(self), $s) := false

	//@E
	macro rule r_setMaster($newMaster in Camera) =
		getMaster(cameraOC(self)) := $newMaster


	//Macro rules for modularization

	//@P
	//Remove all slaves (if any) turning SLAVE2
	macro rule r_removeSlavesTurningSlave =
		par
			if (exist $c in Camera with slaves(cameraOC(self), $c)) then
				par
					forall $s in Camera with slaves(cameraOC(self), $s) do
						if not change_master($s) then //set signal
							change_master($s) := true
						endif
					r_clearSlaves[]
				endpar
			endif
			stateC(cameraOC(self)) := SLAVE
		endpar

	//@P
	//Remove all slaves (if any) turning MASTER2
	macro rule r_removeSlavesTurningMaster =
		par
			if (exist $c in Camera with slaves(cameraOC(self), $c)) then
				par
					forall $s in Camera with slaves(cameraOC(self), $s) do
						if not masterGone($s) then //set signal
							masterGone($s) := true
						endif
					r_clearSlaves[]  //o r_clean[]? //PA: mi sembra che questo possa andare in parallelo
				endpar
			endif
			stateC(cameraOC(self)) := MASTER
		endpar

	//@P
	//Remove all slaves gone
	macro rule r_removeSlaveGone =
		forall $s in Camera with slaves(cameraOC(self), $s) and slaveGone(cameraOC(self), $s) do
			par
				r_removeSlave[$s]
				slaveGone(cameraOC(self), $s) := false //unset signal
			endpar

	//@P
	//Add all new slaves on one shot since there could be
	//more than one SLAVE setting function s_offer to the
	//same value true (so without generating inconsistent update).
	macro rule r_addNewSlave =
		forall $s in Camera with newSlave(cameraOC(self), $s) do
			par
				r_addSlave[$s]
				newSlave(cameraOC(self), $s) := false //unset signal
				//s_offer($s) := false //non dovrebbe essere il master ha resettare il segnale degli slaves
			endpar

	//@P
	macro rule r_turnSlave ($master in Camera) =
		par
			if not newSlave($master, cameraOC(self)) then //set signal
				newSlave($master, cameraOC(self)) := true
			endif
			r_setMaster[$master]
			r_removeSlavesTurningSlave[]
		endpar

	//@P
	macro rule r_turnMaster =
		par
			r_setMaster[cameraOC(self)]
			stateC(cameraOC(self)) := MASTER
			forall $s in Camera with true do
				par
					slaves(cameraOC(self), $s) := false
					newSlave(cameraOC(self), $s) := false
				endpar  
		endpar

	//@P
	macro rule r_turnMasterWithSlaves =
		par
			r_addNewSlave[] 
			stateC(cameraOC(self)) := MASTERWITHSLAVES
		endpar

	//@P
	macro rule r_notifyPendingSlavesMasterChanged =
		forall $s in Camera with newSlave(cameraOC(self), $s) do
			par 
				newSlave(cameraOC(self), $s) := false //unset signal
				if not change_master($s) then //set signal
					change_master($s) := true
				endif
			endpar

	//@A
	macro rule r_analyzeCongestion =
		//Else to avoid inconsistent updates 
		if m_offer(cameraOC(self)) then
			r_turnMasterWithSlaves[]
		else
			if s_offer(cameraOC(self)) then
				par
					r_turnSlave[prev(cameraOC(self))]
					s_offer(cameraOC(self)) := false //unset signal
				endpar
			endif
		endif

	//@A
	macro rule r_receiveOrgSignals =
		par
			if change_master(cameraOC(self)) then //Master changed!
				//@P
				par
					par
						r_setMaster[prev(getMaster(cameraOC(self)))]
						if not newSlave(prev(getMaster(cameraOC(self))), cameraOC(self)) then //set signal
							newSlave(prev(getMaster(cameraOC(self))), cameraOC(self)) := true					
						endif				
					endpar
					change_master(cameraOC(self)) := false //unset signal
				endpar
			endif
			if masterGone(cameraOC(self)) then
				par
					r_turnMaster[]
					masterGone(cameraOC(self)) := false //unset signal
				endpar
			endif
			if m_offer(cameraOC(self)) //There are pending slaves not engaged
			then
				r_notifyPendingSlavesMasterChanged[]
			endif
		endpar

	//@A
	macro rule r_analyzeOrganization =
		if m_offer(cameraOC(self)) then
			r_addNewSlave[]
		else
			if (forall $s in Camera with not(slaves(cameraOC(self), $s))) //Simply turn master
			then
				r_turnMaster[]
			else
				if (s_offer(cameraOC(self)) and congested(self)) then
					par
						r_turnSlave[prev(cameraOC(self))]
						s_offer(cameraOC(self)) := false //unset signal
					endpar
				endif
			endif
		endif

	macro rule r_masterBehaviour($c in Camera) =
		//congestion is for debugging purposes
		if (congestion($c) and not congested(self)) //Congestion detected!
		//if ( cong($c) and not congested(self) )//Congestion detected! 
		then //@P
			par
				congested(self) := true 
				cong($c) := false //unset signal
				let ($nextCameraOCself = next($c)) in
					if isDef($nextCameraOCself) then
						if not s_offer($nextCameraOCself) then //set signal
							s_offer($nextCameraOCself) := true
						endif
					endif
				endlet
			endpar
		else
			if congested(self) then
				r_analyzeCongestion[]
			endif
		endif
	
	macro rule r_slaveBehaviour($c in Camera) =
		if not(congestion($c)) //for debugging
		//if no_cong($c) //No longer congested!
		then //@P
			par
				no_cong($c) := false //unset signal
				congested(self) := false
				if not slaveGone(getMaster($c), $c) then //set signal
					slaveGone(getMaster($c), $c) := true
				endif
				r_turnMaster[]
			endpar
		else
			r_receiveOrgSignals[]
		endif

	macro rule r_masterWithSlavesBehaviour($c in Camera) =
		if not(congestion($c)) //for debugging
		//if no_cong($c) //No longer congested!
		then
			par
				r_removeSlavesTurningMaster[]
				no_cong($c) := false //unset signal
				congested(self) := false
			endpar
		else
			r_analyzeOrganization[]
		endif

	//@M_s self_aware (no A function)
	macro rule r_selfFailureAdapt =
		if (stateOC(self) = FAILED) then
			if startCam(cameraOC(self)) then
				r_turnMaster[]
			endif
		else
			if stopCam(cameraOC(self)) then //@P
				stateC(cameraOC(self)) := FAILED
			endif
		endif

	//@M_c context_aware (no A function)
	macro rule r_failureAdapt =
		//puoi fare failure adapt solo se non sei fallito e non fallisci al prossimo stato
		if stateOC(self) != FAILED and not stopCam(cameraOC(self)) then
			par
				if stateOC(self) = MASTERWITHSLAVES and slavesNotAlive(cameraOC(self)) then
					r_removeSlaveGone[]
				endif
				if stateOC(self) = SLAVE and masterGone(cameraOC(self)) then
					par
						r_turnMaster[]
						masterGone(cameraOC(self)) := false //unset signal
					endpar
				endif
			endpar
		endif

	//@M_c context_aware
	macro rule r_congestionAdapt =
		//usare un predicato unico (da riutilizzare anche sopra)
		if stateOC(self) != FAILED and not(stopCam(cameraOC(self))) then
			par
				//MASTER (initial state)
		 		if stateOC(self) = MASTER then
					r_masterBehaviour[cameraOC(self)]
				endif
				//SLAVE
				if stateOC(self) = SLAVE and not(masterGone(cameraOC(self))) then
					r_slaveBehaviour[cameraOC(self)]
				endif
				//MASTERWITHSLAVE
				if stateOC(self) = MASTERWITHSLAVES and not(slavesNotAlive(cameraOC(self))) then
					r_masterWithSlavesBehaviour[cameraOC(self)]
				endif
			endpar
		endif

	//MAPE control loops
	macro rule r_organizationControl =
		par
			r_selfFailureAdapt[] //Adaptation due to internal failure
			r_failureAdapt[] //ROBOUSTNESS: Adaptation due to external failure (silent nodes)
			r_congestionAdapt[] //FLEXIBILITY: Adaptation due to congestion
		endpar
	//END: -- OrganizationMiddleware -------------------










	//START: -- SelfHealingSubsystem -------------------
	//@A
	macro rule r_sendSignal =
		if elapsed_wait_time(self) then
		//@P
			forall $s in Camera with slaves(cameraSHC(self), $s) do
				par
					if not isAlive($s) then //set signal
						isAlive($s) := true //ping signal
					endif
					stateSHC(self) := WAITFORRESPONSE
				endpar
		endif

	//@A
	macro rule r_receiveSignal =
		if allSlavesAlive(cameraSHC(self)) then //@P
			par
				stateSHC(self) := START
				forall $c in Camera with slaves(cameraSHC(self), $c) do //unset signal
					imAlive(cameraSHC(self), $c) := false
			endpar
		else
			if elapsed_alive_time(self) then
				//@P
				par
					forall $s in Camera with slaves(cameraSHC(self), $s) do
						//Communication between self-healer and organization controller
						if not imAlive(cameraSHC(self), $s) then
							if not slaveGone(cameraSHC(self), $s) then //set signal
								slaveGone(cameraSHC(self), $s) := true
							endif
						else imAlive(cameraSHC(self), $s) := false //unset signal
						endif
					stateSHC(self) := START
				endpar
			endif
		endif

	//@A
	macro rule r_tryGenerateEcho =
		if isAlive(cameraSHC(self)) then //@P
			par
				if not(imAlive(getMaster(cameraSHC(self)), cameraSHC(self))) then //set signal
					imAlive(getMaster(cameraSHC(self)), cameraSHC(self)) := true //echo signal
				endif
				isAlive(cameraSHC(self)) := false //unset signal
			endpar
		else
			if slaves(getMaster(cameraSHC(self)), cameraSHC(self)) then
				if elapsed_wait_time_plus_delta(self) then //@P
					if not masterGone(cameraSHC(self)) then //set signal
						masterGone(cameraSHC(self)) := true    //Communication between self-healer and organization controller
					endif
				endif
			endif
		endif

	//@M_s self-aware
	macro rule r_selfFailureDetect =
		par
			if (stateSHC(self) = START or stateSHC(self) = WAITFORRESPONSE) and stopCam(cameraSHC(self)) then //@P
				stateSHC(self) := FAILED2
			endif
			if stateSHC(self) = FAILED2 and startCam(cameraSHC(self)) then //@P
				stateSHC(self) := START
			endif
		endpar

	//@M_c context-aware
	macro rule r_failureDetect =
		if not stopCam(cameraSHC(self)) then
			par
				//Depending on the role:
				if isMasterWithSlaves(cameraSHC(self)) then
					if stateSHC(self) = START then
						r_sendSignal[] //send ping signals to all slaves of the organization
					else
						if stateSHC(self) = WAITFORRESPONSE then
							r_receiveSignal[]
						endif
					endif
				endif
				if isSlave(cameraSHC(self)) then
					if stateSHC(self) = START then
						r_tryGenerateEcho[]
					endif
				endif
			endpar
		endif

	macro rule r_selfHeal =
		par
			r_selfFailureDetect[] //MAPE control loop for adaptation due to internal failure
			r_failureDetect[] //Monitoring for adaptation due to external failure (ROBOUSTNESS)
		endpar
	//END: -- SelfHealingSubsystem -------------------




	main rule r_main =
		forall $a in Agent do
			program($a)

default init s0:
	function stateC($c in Camera) = MASTER
	function imAlive($c in Camera,$s in Camera) = false
	function isAlive($c in Camera) = false
	function slaves($c in Camera, $s in Camera) = false
	
	function cong($c in Camera) = false
	function no_cong($c in Camera) = false
	function s_offer($c in Camera) = false
	function change_master($c in Camera) = false
	function newSlave($c in Camera, $s in Camera) = false
	function slaveGone($c in Camera, $s in Camera) = false
	function getMaster($c in Camera) = $c
	function masterGone($c in Camera) = false

	function stateSHC($s in SelfHealingController) = START

	function congested($o in OrganizationController) = false
	
	agent Camera:
		r_cameraBehavior[]

	agent TrafficMonitor:
		r_skip[]

	agent SelfHealingController:
		r_selfHeal[]

	agent OrganizationController:
		r_organizationControl[]