asm mainForValidation
//main ASM: Camera system of systems

import ../../../../STDL/StandardLibrary

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
	static id : Camera -> Integer
	controlled stateC: Camera -> CameraState
	//Functions shared with OrganizationController
	/* controlled master: Camera -> Boolean
	controlled slave: Camera -> Boolean
	controlled masterWithSlaves: Camera -> Boolean */

	controlled state2: TrafficMonitor -> TrafficMonitorState 
	controlled cars: TrafficMonitor -> Integer
	static capacity: Integer
	//ID of the monitored camera
	static cameraTM: TrafficMonitor -> Camera 
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
	controlled slaves: Camera -> Seq(Camera)

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
	//main
	function capacity = 3

	/* function camera(trafficMonitor1)= c1
	function camera(trafficMonitor2)= c2
	function camera(selfHealingController1)= c1
	function camera(selfHealingController2)= c2
	function camera(organizationController1)= c1
	function camera(organizationController2)= c2 */
	
	function cameraTM($a in TrafficMonitor) =
		   switch($a)
				case trafficMonitor1: c1
				case trafficMonitor2: c2
				case trafficMonitor3: c3
				case trafficMonitor4: c4
			endswitch
	
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
	
	function id($c in Camera) =
	        switch($c)
				case c1: 1
				case c2: 2
				case c3: 3
				case c4: 4
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






	//Knowledge
	function isMaster($c in Camera) = ( stateC($c) = MASTER or stateC($c) = MASTERWITHSLAVES ) 
	function isMasterWithSlaves($c in Camera) =  stateC($c) = MASTERWITHSLAVES
	function isSlave($c in Camera) =  stateC($c) = SLAVE
	function hasNext($c in Camera) = isDef(next($c))

	function next($c in Camera) =
		if isUndef(nextNeighbor($c)) then
			undef
		else
			if stateC(nextNeighbor($c)) = FAILED then
				next(nextNeighbor($c))
			else
				nextNeighbor($c)
			endif
		endif

	function prev($c in Camera) =
		if isUndef(prevNeighbor($c)) then
			undef
		else
			if stateC(prevNeighbor($c)) = FAILED then
				prev(prevNeighbor($c))
			else
				prevNeighbor($c)
			endif
		endif

	function slavesNotAlive ($c in Camera) =
	   (exist $s in Camera with ( contains(slaves($c), $s) and  slaveGone($c, $s) ))

	function allSlavesAlive ($c in Camera) =
	  ( forall $s in Camera with ( contains(slaves($c), $s) and  imAlive($c, $s) ))   

	function m_offer ($c in Camera) =
	   (exist $s in Camera with newSlave($c,$s) )








	//OrganizationMiddleware
	function stateOC($c in OrganizationController) = stateC(cameraOC($c))



	//main
	macro rule r_skip = skip //for debugging





	//CameraAgentSubsystem
	/* 
	We prefer for control state ASMs the parallel synchronous understanding of ASMs as firing in each step every rule (by the par rule). We control possible conflicts, e.g. by taking care that the rule guards of rules fireable in a control state are disjoint. In case of possible conflicts we sequencialize the transitions giving priority to certain events (e.g., those coming from the environment) that cannot be delayed.
	
	Here for the camera behavior, we give priority to the event stopCam from the environment (a monitored function). We assume the other guards are disjoint since related to actions executed by the same agent (the organization controller) in two separate modes. 
	*/

	macro rule r_cameraBehavior =
	/*seq  //Gives rise to inconsistent updates do to par rules and separate msg master, slave, masterWithSlaves
	 if stopCam(self) then stateC(self):= FAILED endif
	 par
	  //MASTER (initial state)
	  if (stateC(self) = MASTER) 
	  then  par 
	          if masterWithSlaves(self) 
	          then par stateC(self):= MASTERWITHSLAVES masterWithSlaves(self):= false endpar endif	       
	          if slave(self) then par stateC(self):= SLAVE slave(self):= false endpar endif 
	         endpar	endif    	
	  //SLAVE
	  if ( stateC(self) = SLAVE and master(self) )
	  then 
	   par stateC(self):= MASTER master(self):= false endpar  endif	
	  //MASTERWITHSLAVES
	  if stateC(self) = MASTERWITHSLAVES 
	  then  
	     par 
	      if master(self) then 
	         par stateC(self):= MASTER master(self):= false endpar endif	       
	      if slave(self) then 
	         par stateC(self):= SLAVE slave(self):= false endpar endif 
	     endpar	endif           
	  //FAILED
	  if (stateC(self) = FAILED and startCam(self)) 
	  then stateC(self):= MASTER  endif	 
	 endpar
	endseq
	*/
	//if ( stateC(self) = FAILED and startCam(self) ) then  stateC(self):= MASTER  
	//else if ( not(stateC(self) = FAILED) and stopCam(self) ) then  stateC(self):= FAILED endif endif
		skip

	macro rule r_observeCars =
		par
			if (camEnter(cameraTM(self)) and not (camLeave(cameraTM(self)) )) then
				cars(self):= cars(self) + 1
			endif
			if (not (camEnter(cameraTM(self))) and camLeave(cameraTM(self)) ) then
				cars(self):= cars(self) - 1
			endif
		endpar

	//We give priority to the guards related to the CAPACITY
	//because it drives the transitions from state CONGESTION
	//to NOCONGESTION and viceversa. The other guards are assumed //disjoint for the same reasons above for the camera behavior.
	macro rule r_trafficMonitor =
		par
			//NOCONGESTION (initial state)
			if state2(self) = NOCONGESTION then
				if cars(self) >= capacity then
					par
						cong(cameraTM(self)):= true
						state2(self) := CONGESTION
					endpar
				//else r_observeCars[] 
				endif
			endif
			//CONGESTION
			if state2(self) = CONGESTION then
				if cars(self) < capacity then
					par
						no_cong(cameraTM(self)):= true
						state2(self) := NOCONGESTION
					endpar
				//else r_observeCars[]
				endif 
			endif
			r_observeCars[]
		endpar









	//OrganizationMiddleware
	//Macro rules for atomic adaptation actions in a master/slave organization
 
	//@E 
	macro rule r_clearSlaves =
		slaves(cameraOC(self)) := []

	//@E
	macro rule r_addSlave ($s in Camera) =
		slaves(cameraOC(self)) := append(slaves(cameraOC(self)), $s)

	//@E 
	macro rule r_removeSlave ($s in Camera) =
		slaves(cameraOC(self)) := excluding(slaves(cameraOC(self)), $s)

	//@E
	macro rule r_setMaster($newMaster in Camera) =
		getMaster(cameraOC(self)) := $newMaster


	//Macro rules for modularization

	//@P
	macro rule r_clean = //TO DO: to test
		par
			r_clearSlaves[]
			congested(self) := false 
			s_offer(cameraOC(self)) := false
			stateC(cameraOC(self)) := MASTER
			isAlive(cameraOC(self)) := false
			//master(cameraOC(self)) := false
			//masterWithSlaves(cameraOC(self)) := false
			//slave(cameraOC(self)) := false
			cong(cameraOC(self)) := false
			no_cong(cameraOC(self)) := false
			change_master(cameraOC(self)) := false
			//masterGone(cameraOC(self)) := false //Gives rise to exceptions
			forall $c in Camera do
				par
					imAlive($c, cameraOC(self)) := false
					imAlive(cameraOC(self), $c) := false
					//newSlave($c, cameraOC(self)) := false
					newSlave(cameraOC(self), $c) := false
					//slaveGone($c, cameraOC(self)) := false
					slaveGone(cameraOC(self), $c) := false
				endpar
		endpar

	//@P
	//Remove all slaves (if any) turning SLAVE2
	macro rule r_removeSlavesTurningSlave =
		par
			if (not isEmpty(slaves(cameraOC(self)))) then
				seq
					forall $s in Camera with contains(slaves(cameraOC(self)), $s) do
					//forall $s in asSet(slaves(cameraOC(self))) do
						change_master($s) := true
					r_clearSlaves[] //PA: mi sembra che questo possa andare in parallelo
				endseq
			endif
			stateC(cameraOC(self)) := SLAVE
		endpar

	//@P
	//Remove all slaves (if any) turning MASTER2
	macro rule r_removeSlavesTurningMaster =
		par
			if (not isEmpty(slaves(cameraOC(self)))) then
				seq
					forall $s in Camera with contains(slaves(cameraOC(self)), $s) do
					//forall $s in asSet(slaves(cameraOC(self))) do
						masterGone($s) := true
					r_clearSlaves[]  //o r_clean[]? //PA: mi sembra che questo possa andare in parallelo
				endseq
			endif
			//master(cameraOC(self)) := true
			stateC(cameraOC(self)) := MASTER
		endpar

	//@P
	//Remove all slaves gone
	macro rule r_removeSlaveGone =
		forall $s in Camera with ( contains(slaves(cameraOC(self)), $s) and slaveGone(cameraOC(self), $s) )
		//forall $s in asSet(slaves(cameraOC(self))) with slaveGo(cameraOC(self), $s)
		do
			par
				r_removeSlave[$s]
				slaveGone(cameraOC(self), $s) := false
			endpar

	//@P
	//Add all new slaves on one shot since there could be
	//more than one SLAVE2 setting function s_offer to the
	//same value true (so without generating inconsistent update).
	macro rule r_addNewSlave =
		forall $s in Camera with newSlave(cameraOC(self), $s) do
			par
				r_addSlave[$s]
				newSlave(cameraOC(self), $s) := false
				s_offer($s) := false
			endpar

	//@P
	macro rule r_turnSlave ($master in Camera) =
		par
			newSlave($master, cameraOC(self)) := true
			r_setMaster[$master]
			r_removeSlavesTurningSlave[]
			//s_offer(cameraOC(self)) := false
		endpar

	//@P
	macro rule r_turnMaster =
		par
			r_setMaster[cameraOC(self)]
			stateC(cameraOC(self)) := MASTER
			//if not (congested(self)) then r_clean[] endif
			r_clean[]
			//gives rise to inconsistent updates
			//if masterGone(cameraOC(self)) then masterGone(cameraOC(self)) := false endif  
		endpar

	//@P
	macro rule r_turnMasterWithSlaves =
		par 
			r_addNewSlave[] 
			stateC(cameraOC(self)) := MASTERWITHSLAVES 
			//state(self) := MASTERWITHSLAVES2
		endpar

	//@P
	macro rule r_notifyPendingSlavesMasterChanged =
		forall $s in Camera with newSlave(cameraOC(self), $s) do
			par 
				newSlave(cameraOC(self), $s) := false
				change_master($s) := true
			endpar

	//@A
	macro rule r_analyzeCongestion =
		//Else to avoid inconsistent updates 
		if m_offer(cameraOC(self)) then
			r_turnMasterWithSlaves[] 
		else
			if s_offer(cameraOC(self))//(s_offer(cameraOC(self)) and congested(self) ) 
			then 
				r_turnSlave[prev(cameraOC(self))]
			endif
		endif
		//  r_turnSlave[getMaster(prev(cameraOC(self)))] endif endif

	//@A
	macro rule r_receiveOrgSignals = 
		par
			if change_master(cameraOC(self)) then //Master changed!
				//@P
				par 
					r_setMaster[prev(getMaster(cameraOC(self)))]
					newSlave(prev(getMaster(cameraOC(self))), cameraOC(self)) := true
					change_master(cameraOC(self)) := false
				endpar
			endif
			if masterGone(cameraOC(self)) then
				r_turnMaster[]
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
			if isEmpty(slaves(cameraOC(self))) //Simply turn master
			then
				r_turnMaster[] 
			else
				if (s_offer(cameraOC(self)) and congested(self)) then
					r_turnSlave[prev(cameraOC(self))]
				endif
			endif
		endif

	//@M_s self_aware (no A function)
	macro rule r_selfFailureAdapt =
		par
			if stopCam(cameraOC(self)) then //@P
				stateC(cameraOC(self)) := FAILED
			endif
			if (stateOC(self) = FAILED and startCam(cameraOC(self)) ) then
				r_turnMaster[]
			endif
		endpar

	//@M_c context_aware (no A function)
	macro rule r_failureAdapt =
		par 
			if ( stateOC(self) = MASTERWITHSLAVES and slavesNotAlive(cameraOC(self)) ) then
				r_removeSlaveGone[]
			endif
			if ( stateOC(self) = SLAVE and masterGone(cameraOC(self)) ) then
				r_turnMaster[]
			endif
		endpar

	//@M_c context_aware
	macro rule r_congestionAdapt =
		par
			//MASTER (initial state)
	 		if stateOC(self) = MASTER then
				//congestion is for debugging purposes
				if (congestion(cameraOC(self)) and not congested(self)) //Congestion detected!
				//if ( cong(cameraOC(self)) and not congested(self) )//Congestion detected! 
				then //@P
					par
						congested(self) := true 
						cong(cameraOC(self)) := false 
						if isDef(next(cameraOC(self))) then
							s_offer(next(cameraOC(self))) := true
						endif
					endpar
				else
					if congested(self) then
						r_analyzeCongestion[]
					endif
				endif
			endif 	
			//SLAVE
			if stateOC(self) = SLAVE then
				if not(congestion(cameraOC(self))) //for debugging
				//if no_cong(cameraOC(self)) //No longer congested!
				then //@P
					par
						no_cong(cameraOC(self)) := false
						congested(self) := false
						slaveGone(getMaster(cameraOC(self)),cameraOC(self)) := true
						r_turnMaster[]
					endpar
				else
					r_receiveOrgSignals[]
				endif
			endif
			//MASTERWITHSLAVE
			if stateOC(self) = MASTERWITHSLAVES then
				if not(congestion(cameraOC(self))) //for debugging
				//if no_cong(cameraOC(self)) //No longer congested!
				then
					par
						r_removeSlavesTurningMaster[]
						no_cong(cameraOC(self)) := false
						congested(self) := false
					endpar
				else
					r_analyzeOrganization[]
				endif
			endif
	endpar

	//MAPE control loops
	macro rule r_organizationControl =
		seq
			r_selfFailureAdapt[] //Adaptation due to internal failure
			r_failureAdapt[] //ROBOUSTNESS: Adaptation due to external failure (silent nodes)
			r_congestionAdapt[] //FLEXIBILITY: Adaptation due to congestion
		endseq





	//SelfHealingSubsystem
	//@A
	macro rule r_sendSignal =
		if elapsed_wait_time(self) then 
		//@P
			forall $s in Camera with contains(slaves(cameraSHC(self)), $s) do 
				par
					isAlive($s) := true //ping signal
					stateSHC(self) := WAITFORRESPONSE
				endpar
		endif

	//@A
	macro rule r_receiveSignal =
		if allSlavesAlive(cameraSHC(self)) then //@P
			stateSHC(self) := START
		else
			if elapsed_alive_time(self) then
				//@P
				par
					forall $s in Camera with (contains(slaves(cameraSHC(self)), $s) and not imAlive(cameraSHC(self),$s) ) do
						//Communication between self-healer and organization controller
						slaveGone(cameraSHC(self), $s) := true
					stateSHC(self) := START
				endpar
			endif
		endif

	//@A
	macro rule r_tryGenerateEcho =
		if isAlive(cameraSHC(self)) then //@P
			imAlive(getMaster(cameraSHC(self)), cameraSHC(self)) := true //echo signal
		else
			if elapsed_wait_time_plus_delta(self) then //@P
				masterGone(cameraSHC(self)) := true    //Communication between self-healer and organization controller
			endif
		endif

	//@M_s self-aware
	macro rule r_selfFailureDetect =
		par
			if ((stateSHC(self) = START or stateSHC(self) = WAITFORRESPONSE) and stopCam(cameraSHC(self))) then //@P
				stateSHC(self) := FAILED2
			endif
			if (stateSHC(self) = FAILED2 and startCam(cameraSHC(self)) ) then //@P
				stateSHC(self) := START
			endif
		endpar

	//@M_c context-aware
	macro rule r_failureDetect =
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

	macro rule r_selfHeal =
		//seq
		// r_selfFailureDetect[] //MAPE control loop for adaptation due to internal failure
		r_failureDetect[] //Monitoring for adaptation due to external failure (ROBOUSTNESS)
		//endseq


	main rule r_main =
		forall $a in Agent do
			program($a)

default init s0:
	function stateC($c in Camera) = MASTER
	function imAlive($c in Camera,$s in Camera) = false
	function isAlive($c in Camera) = false
	function slaves($c in Camera) = []
	//function master($c in Camera) = false
	//function masterWithSlaves($c in Camera) = false
	//function slave($c in Camera) = false
	function cong($c in Camera) = false
	function no_cong($c in Camera) = false
	function s_offer($c in Camera) = false
	function change_master($c in Camera) = false
	function newSlave($c in Camera,$s in Camera) = false
	function slaveGone($c in Camera,$s in Camera) = false
	function getMaster($c in Camera) = $c
	function masterGone($c in Camera) = false

	function state2($t in TrafficMonitor) = NOCONGESTION
	function cars($t in TrafficMonitor) = 0
	function stateSHC($s in SelfHealingController) = START

	//function state($o in OrganizationController) = MASTER2
	function congested($o in OrganizationController) = false

	agent Camera:
		r_cameraBehavior[]

	agent TrafficMonitor:
		r_skip[] //for debugging
		//r_trafficMonitor[] 

	agent SelfHealingController:
		r_selfHeal[]

	agent OrganizationController:
		r_organizationControl[]
