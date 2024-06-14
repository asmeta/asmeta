//Assumptions:
//- A MAPE loop can be made of several MAPE subloops. These subloops are composed trough a rule constructor such as par, seq, if-then-else, etc.
//- In a MAPE (sub)loop, computations A and P can be missing when the adaptation is direct or envolves only knowledge updates.
//- The body part of the two rule schemas for a MAPE computation, the centralized one and the decentralized one, can be combined. So, e.g. one can have:
// @M_c @M_s If Cond then par updates_K  Analyze andpar 

//Proposta:Perch� non ammettiamo solo l'annotazione  @M?

asm mainForModelChecking_New
//main ASM: Camera system of systems

import ../../../../STDL/StandardLibrary
import ../../../../STDL/CTLLibrary

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
    monitored startCam: Camera -> Boolean
	monitored stopCam: Camera -> Boolean
	
	//Functions shared between the managed system (the camera)  and the OrganizationController
	//They could be turned into monitored functions if the camera behaviour is commented.
	controlled cong: Camera -> Boolean
	controlled no_cong: Camera -> Boolean

	//Knowledge:
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

	//shared functions among organization controllers (declared controlled for simulation purposes)
	controlled s_offer : Camera -> Boolean
	derived m_offer: Camera -> Boolean
	controlled newSlave : Prod(Camera,Camera)-> Boolean
	controlled change_master : Camera -> Boolean
	//controlled slaves: Camera -> Seq(Camera) //PA: not supported by model checking
	controlled slaves: Prod(Camera, Camera) -> Boolean //Is the second camera (second argument) slave of the first camera (first argument)?

	//Functions shared among self-healers and organization controllers
	controlled slaveGone: Prod(Camera,Camera)-> Boolean 
	controlled masterGone : Camera -> Boolean


	//For the PING-ECHO mechanism:
	//Functions shared among SealfHealingController agents 
	controlled imAlive: Prod(Camera,Camera)-> Boolean
	controlled isAlive: Camera -> Boolean

	derived allSlavesAlive: Camera -> Boolean
	derived slavesNotAlive: Camera -> Boolean
	

	//OrganizationMiddleware's functions
	derived stateOC: OrganizationController -> CameraState
	static cameraOC: OrganizationController -> Camera //ID of the camera
	controlled congested: OrganizationController -> Boolean //flag
	monitored congestion: Camera -> Boolean //for debugging


	//SelfHealingSubsystem's functions
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
	//function capacity = 3 //PA: commentato perche' viene usato in r_trafficMonitor che non viene usato

	 //PA: commentato perche' usato in r_trafficMonitor che non viene usato
	/*function cameraTM($a in TrafficMonitor) =
		   switch($a)
				case trafficMonitor1: c1
				case trafficMonitor2: c2
				case trafficMonitor3: c3
				case trafficMonitor4: c4
			endswitch*/
	
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
	
	//PA: commentato perche' non viene usato
	/*function id($c in Camera) =
	        switch($c)
				case c1: 1
				case c2: 2
				case c3: 3
				case c4: 4
			endswitch*/
	
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


	//Knowledge:
	function isMaster($c in Camera) = ( stateC($c) = MASTER or stateC($c) = MASTERWITHSLAVES ) 
	function isMasterWithSlaves($c in Camera) =  stateC($c) = MASTERWITHSLAVES
	function isSlave($c in Camera) =  stateC($c) = SLAVE
	function hasNext($c in Camera) = isDef(next($c))

	function next($c in Camera) =
		//PA: riscritto per il model checking
		/*if isUndef(nextNeighbor($c)) then
			undef
		else
			if stateC(nextNeighbor($c)) = FAILED then
				next(nextNeighbor($c))
			else
				nextNeighbor($c)
			endif
		endif*/
		/*let ($n = nextNeighbor($c)) in
			if isUndef($n) then
				undef
			else
				if stateC($n) = FAILED then
					next($n)
				else
					$n
				endif
			endif
		endlet*/
		switch $c
			case c1:
					if stateC(c2) != FAILED then
						c2
					else
						if stateC(c3) != FAILED then
							c3
						else
							if stateC(c4) != FAILED then
								c4
							else
								undef
							endif
						endif
					endif
			case c2:
					if stateC(c3) != FAILED then
						c3
					else
						if stateC(c4) != FAILED then
							c4
						else
							undef
						endif
					endif
			case c3:
					if stateC(c4) != FAILED then
						c4
					else
						undef
					endif
			case c4: undef
		endswitch

	function prev($c in Camera) =
		/*if isUndef(prevNeighbor($c)) then
			undef
		else
			if stateC(prevNeighbor($c)) = FAILED then
				prev(prevNeighbor($c))
			else
				prevNeighbor($c)
			endif
		endif*/
		/*let ($p = prevNeighbor($c)) in
			if isUndef($p) then
				undef
			else
				if stateC($p) = FAILED then
					prev($p)
				else
					$p
				endif
			endif
		endlet*/
		switch $c
			case c4:
					if stateC(c3) != FAILED then
						c3
					else
						if stateC(c2) != FAILED then
							c2
						else
							if stateC(c1) != FAILED then
								c1
							else
								undef
							endif
						endif
					endif
			case c3:
					if stateC(c2) != FAILED then
						c2
					else
						if stateC(c1) != FAILED then
							c1
						else
							undef
						endif
					endif
			case c2:
					if stateC(c1) != FAILED then
						c1
					else
						undef
					endif
			case c1: undef
		endswitch

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


	//OrganizationMiddleware
	function stateOC($c in OrganizationController) =
		//PA: for model checking
		//stateC(cameraOC($c))
		let ($cameraOCc = cameraOC($c)) in
			stateC($cameraOCc)
		endlet

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

	//PA: commentato perche' non viene usato
	/*macro rule r_observeCars =
		par
			if (camEnter(cameraTM(self)) and not (camLeave(cameraTM(self)) )) then
				cars(self):= cars(self) + 1
			endif
			if (not (camEnter(cameraTM(self))) and camLeave(cameraTM(self)) ) then
				cars(self):= cars(self) - 1
			endif
		endpar*/

	//We give priority to the guards related to the CAPACITY
	//because it drives the transitions from state CONGESTION
	//to NOCONGESTION and viceversa. The other guards are assumed //disjoint for the same reasons above for the camera behavior.
	/*macro rule r_trafficMonitor = //PA: commentato perche' non viene usato
		par
			//NOCONGESTION (initial state)
			if state2(self) = NOCONGESTION then
				if cars(self) >= capacity then
					par
						if not cong(cameraTM(self)) then //set signal
							cong(cameraTM(self)):= true
						endif
						state2(self) := CONGESTION
					endpar
				//else r_observeCars[] 
				endif
			endif
			//CONGESTION
			if state2(self) = CONGESTION then
				if cars(self) < capacity then
					par
						if not no_cong(cameraTM(self)) then //set signal
							no_cong(cameraTM(self)):= true
						endif
						state2(self) := NOCONGESTION
					endpar
				//else r_observeCars[]
				endif 
			endif
			r_observeCars[]
		endpar*/



	//OrganizationMiddleware
	//Macro rules for atomic adaptation actions in a master/slave organization
 
	//@E 
	macro rule r_clearSlaves =
		let ($cameraOCself = cameraOC(self)) in
			//PA: slaves changed because of model checking
			//slaves($cameraOCself) := []
			forall $s in Camera do
				slaves($cameraOCself, $s) := false
		endlet

	//@E
	macro rule r_addSlave ($s in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			//PA: slaves changed because of model checking
			//slaves($cameraOCself) := append(slaves($cameraOCself), $s)
			slaves($cameraOCself, $s) := true
		endlet

	//@E 
	macro rule r_removeSlave ($s in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			//PA: slaves changed because of model checking
			//slaves($cameraOCself) := excluding(slaves($cameraOCself), $s)
			slaves($cameraOCself, $s) := false
		endlet

	//@E
	macro rule r_setMaster($newMaster in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			getMaster($cameraOCself) := $newMaster
		endlet


	//Macro rules for modularization

	//@P
	/*macro rule r_clean = //TO DO: to test
		let ($cameraOCself = cameraOC(self)) in
			par
				r_clearSlaves[]
				congested(self) := false 
				s_offer($cameraOCself) := false
				stateC($cameraOCself) := MASTER
				isAlive($cameraOCself) := false
				//master($cameraOCself) := false
				//masterWithSlaves($cameraOCself) := false
				//slave($cameraOCself) := false
				cong($cameraOCself) := false
				no_cong($cameraOCself) := false
				change_master($cameraOCself) := false
				//masterGone($cameraOCself) := false //Gives rise to exceptions
				forall $c in Camera do
					par
						imAlive($c, $cameraOCself) := false
						imAlive($cameraOCself, $c) := false
						//newSlave($c, $cameraOCself) := false
						newSlave($cameraOCself, $c) := false
						//slaveGone($c, $cameraOCself) := false
						slaveGone($cameraOCself, $c) := false
					endpar
			endpar
		endlet*/

	//@P
	//Remove all slaves (if any) turning SLAVE2
	macro rule r_removeSlavesTurningSlave =
		let ($cameraOCself = cameraOC(self)) in
			par
				//PA: slaves changed because of model checking
				//if (not isEmpty(slaves($cameraOCself))) then
				if (exist $c in Camera with slaves($cameraOCself, $c)) then
					//seq //PA: commentato
					par //PA: inserito
						//PA: slaves changed because of model checking
						//forall $s in Camera with contains(slaves($cameraOCself), $s) do
						forall $s in Camera with slaves($cameraOCself, $s) do
							if not change_master($s) then //set signal
								change_master($s) := true
							endif
						r_clearSlaves[] //PA: mi sembra che questo possa andare in parallelo
					//endseq //PA: commentato
					endpar //PA: inserito
				endif
				stateC($cameraOCself) := SLAVE
			endpar
		endlet

	//@P
	//Remove all slaves (if any) turning MASTER
	macro rule r_removeSlavesTurningMaster =
		let ($cameraOCself = cameraOC(self)) in
			par
				//PA: slaves changed because of model checking
				//if (not isEmpty(slaves($cameraOCself))) then
				if (exist $c in Camera with slaves($cameraOCself, $c)) then
					//seq //PA: commentato
					par //PA: inserito
						//PA: slaves changed because of model checking
						//forall $s in Camera with contains(slaves($cameraOCself), $s) do
						forall $s in Camera with slaves($cameraOCself, $s) do
							if not masterGone($s) then //set signal
								masterGone($s) := true
							endif
						r_clearSlaves[]  //o r_clean[]? //PA: mi sembra che questo possa andare in parallelo
					//endseq //PA: commentato
					endpar //PA: inserito
				endif
				//master($cameraOCself) := true
				stateC($cameraOCself) := MASTER
			endpar
		endlet

	//@P
	//Remove all slaves gone
	macro rule r_removeSlaveGone =
		let ($cameraOCself = cameraOC(self)) in
			//PA: slaves changed because of model checking
			//forall $s in Camera with ( contains(slaves($cameraOCself), $s) and slaveGone($cameraOCself, $s) )
			forall $s in Camera with ( slaves($cameraOCself, $s) and slaveGone($cameraOCself, $s) )
			do
				par
					r_removeSlave[$s]
					slaveGone($cameraOCself, $s) := false //unset signal
				endpar
		endlet

	//@P
	//Add all new slaves on one shot since there could be
	//more than one SLAVE setting function s_offer to the
	//same value true (so without generating inconsistent update).
	macro rule r_addNewSlave =
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera with newSlave($cameraOCself, $s) do
				par
					r_addSlave[$s]
					newSlave($cameraOCself, $s) := false //unset signal
				endpar
		endlet

	//@P
	macro rule r_turnSlave ($master in Camera) =
		//let ($cameraOCself = cameraOC(self)) in
			par
				//if not newSlave($master, $cameraOCself) then //set signal
				//	newSlave($master, $cameraOCself) := true
				//endif
				r_setMaster[$master]
				r_removeSlavesTurningSlave[]
			endpar
		//endlet

	//@P 
	macro rule r_turnMaster =
		let ($cameraOCself = cameraOC(self)) in
			par 
				r_setMaster[$cameraOCself] 
				stateC($cameraOCself) := MASTER
				forall $s in Camera with true do
					par
						slaves($cameraOCself, $s) := false
						newSlave($cameraOCself, $s) := false 
			        endpar
			endpar        
		endlet

	//@P
	macro rule r_turnMasterWithSlaves =
		let ($cameraOCself = cameraOC(self)) in
			par
				r_addNewSlave[] 
				stateC($cameraOCself) := MASTERWITHSLAVES 
			endpar
		endlet

	//@P
	macro rule r_notifyPendingSlavesMasterChanged =
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera with newSlave($cameraOCself, $s) do
				par 
					newSlave($cameraOCself, $s) := false //unset signal
					if not change_master($s) then //set signal
						change_master($s) := true
					endif
				endpar
		endlet  
    
    
    //Knowledge updates 
	macro rule r_send_s_offer_message($c in Camera) =
      par    	congested(self) := true 
				//cong($c) := false //unset signal 
				let ($nextCameraOCself = next($c)) in
					if isDef($nextCameraOCself) then
						if not s_offer($nextCameraOCself) then //set signal
							s_offer($nextCameraOCself) := true 
						endif
					endif
				endlet
    	endpar
  
  //@P (no condition)
  macro rule r_joinOrganization($c in Camera) =	
    par	
		let ($prevCameraOCself = prev($c)) in
       		par 
       		  if not newSlave($prevCameraOCself, $c) then //set signal
		          newSlave($prevCameraOCself, $c) := true 
		       endif
		    r_turnSlave[$prevCameraOCself] 
		   endpar
		endlet
		s_offer($c) := false //unset signal 
	endpar

   //MASTER Monitor computations (default behavior)
   //@M_c @M_s
	macro rule r_masterBehaviour($c in Camera) =
	if stateOC(self) != FAILED and not(stopCam($c)) then //First part of the triggering condition of the computations M. 
	//This conditions are grouped into one for avoiding redundancy in the specification.
      par		
		//M1: First M computation, decentralized schema; Information Sharing pattern among the M computations of the organization controllers 
		//congestion is for debugging purposes
		if congestion($c) and not congested(self)// Second part of the triggering condition -- Congestion detected!
		//if cong($c) and not congested(self) // Second part of the triggering condition -- Congestion detected!
		    then r_send_s_offer_message[$c] endif
		//M2: Second M computation (second subloop), centralized (waterfall) schema, no A computation 
		if m_offer($c) and congested(self) then r_turnMasterWithSlaves[]  
	       else //Else to avoid inconsistent updates 
	    //M3:Third M computation (third subloop), centralized (waterfall) schema, no A computation 
	    if s_offer($c) and congested(self) 
				then r_joinOrganization[$c]		
				endif
			endif
	 endpar
	endif
	
	//@P (no condition)
	macro rule r_turnSingleOrganization($c in Camera) =
	 par
				//no_cong($c) := false //unset signal @E
				congested(self) := false //@E
				let ($getMasterCameraOCself = getMaster($c)) in
					if not slaveGone($getMasterCameraOCself, $c) then //set signal
						slaveGone($getMasterCameraOCself, $c) := true //@E
					endif
				endlet
				r_turnMaster[]
			endpar
	
	//@P (no condition)
	macro rule r_changeOrganization($c in Camera) =	
					par
						let ($getMasterCameraOCself = getMaster($c)) in
							let ($prevGetMasterCameraOCself = prev($getMasterCameraOCself)) in
								par
									//r_setMaster[prev(getMaster($cameraOCself))]
									r_setMaster[$prevGetMasterCameraOCself]
									if not newSlave($prevGetMasterCameraOCself, $c) then //set signal
										//newSlave(prev(getMaster($cameraOCself)), $cameraOCself) := true
										newSlave($prevGetMasterCameraOCself, $c) := true  			
									endif				
								endpar
							endlet
						endlet
						change_master($c) := false //unset signal 
					endpar
	
	//SLAVE Monitor computations 
	//@M_c @M_s
	macro rule r_slaveBehaviour($c in Camera) =
	if stateOC(self) != FAILED and not(stopCam($c)) then //First part of the triggering condition of the computations M. 
	//Conditions are decomposed for avoiding redundancy in the specification.
	    //M1: First M computation (first subloop), centralized (waterfall) schema, no A computation 
		if not(congestion($c)) //for debugging
		//if no_cong($c) //No longer congested!
		then r_turnSingleOrganization[$c] 
		else
			par
				//M2: Second M computation (second subloop), centralized (waterfall) schema, no A computation
				if change_master($c) then //Master changed!
					r_changeOrganization[$c]
				endif
				//M3: Third M computation (third subloop), centralized (waterfall) schema, no A computation
				if masterGone($c) then //Turn single organization
					par
						r_turnMaster[]
						masterGone($c) := false //unset signal
					endpar
				endif
				//M4: Fourth M computation (fourth subloop), centralized schema, no A computation
				if m_offer($c) then //There are pending slaves not engaged
					r_notifyPendingSlavesMasterChanged[]
				endif
			endpar
		endif
	endif
	
	//MASTERWITHSLAVES Monitor computations 
   //@M_c @M_s
	macro rule r_masterWithSlavesBehaviour($c in Camera) =
	if stateOC(self) != FAILED and not(stopCam($c)) then //First part of the triggering condition of the computations M. 
	//Conditions are decomposed for avoiding redundancy in the specification.	
		//M1: First M computation (first subloop), centralized (waterfall) schema, no A computation 
		if not(congestion($c)) //for debugging
		//if no_cong($c) //No longer congested!
		then //turn single organization 
			par
				r_removeSlavesTurningMaster[]
				//no_cong($c) := false //unset signal
				congested(self) := false
			endpar
		else
		    //M2: Second M computation (second subloop), centralized (waterfall) schema, no A computation, no P computation
			if m_offer($c) then r_addNewSlave[]
			else
			  //M3: Third M computation (third subloop), centralized (waterfall) schema, no A computation, no P computation	
				//PA: slaves changed because of model checking
				//if isEmpty(slaves($cameraOCself)) //Simply turn master
				if (forall $s in Camera with not(slaves($c, $s))) //Simply turn master
				then r_turnMaster[]
				else
				  //M4: Fourth M computation (fourth subloop), centralized schema, no A computation
					if s_offer($c) and congested(self) then
						r_joinOrganization[$c]	
					endif
				endif
			endif			
		endif
    endif
	
	
	macro rule r_orgContrFlexBehaviour =
	  //This rule only select the right behavior to execute depending on the role of the camera. It is not a MAPE computation.
		let ($cameraOCself = cameraOC(self)) in
			//if stateOC(self) != FAILED and not(stopCam($cameraOCself)) then 
				par
					//MASTER (initial state)
			 		if stateOC(self) = MASTER then
						r_masterBehaviour[$cameraOCself]
					endif
					//SLAVE
					if stateOC(self) = SLAVE and not(masterGone($cameraOCself)) then
						r_slaveBehaviour[$cameraOCself]
					endif
					//MASTERWITHSLAVE
					if stateOC(self) = MASTERWITHSLAVES and not(slavesNotAlive($cameraOCself)) then
						r_masterWithSlavesBehaviour[$cameraOCself]
					endif
				endpar
			 //endif	
			endlet
			/*let ($cameraOCself = cameraOC(self)) in
			if stateOC(self) != FAILED and not(stopCam($cameraOCself)) then
				par
					//MASTER (initial state)
			 		if stateOC(self) = MASTER then
						r_masterBehaviour[$cameraOCself]
					endif
					//SLAVE
					if stateOC(self) = SLAVE and not(masterGone($cameraOCself)) then
						r_slaveBehaviour[$cameraOCself]
					endif
					//MASTERWITHSLAVE
					if stateOC(self) = MASTERWITHSLAVES and not(slavesNotAlive($cameraOCself)) then
						r_masterWithSlavesBehaviour[$cameraOCself]
					endif
				endpar
			endlet*/    
			
	//@M_s 
	macro rule r_selfFailureAdapt =
		let ($cameraOCself = cameraOC(self)) in
			//OLD
			/*par
				if stopCam($cameraOCself) then //@P
					stateC($cameraOCself) := FAILED
				endif
				if (stateOC(self) = FAILED and startCam($cameraOCself) ) then
					r_turnMaster[]
				endif
			endpar*/
			/*if (stateOC(self) = FAILED) then
				if (startCam($cameraOCself) ) then
					r_turnMaster[]
				endif
			else
				if stopCam($cameraOCself) then //@P
					stateC($cameraOCself) := FAILED //@E
				endif
			endif*/
			par
 		       //M1: First M computation (first subloop), centralized (waterfall) schema  
			    if startCam($cameraOCself)  then 
			     //@A
				  if stateOC(self) = FAILED then r_turnMaster[] endif
		        endif		
			    //M2: Second M computation (second subloop), centralized (waterfall) schema, no P computation
			    if stopCam($cameraOCself) then 
				  //@A
				  if stateOC(self) != FAILED then 
				      stateC($cameraOCself) := FAILED //@E
				  endif 
				endif	 
			 endpar 	
		endlet
		
		
	//Information sharing pattern
	//@M centralized (waterfall) schema M->A->P->E executed by the organization controller. It is made of two subloops.
	//An M computation is also in the rule r_failureDetect executed by the self-healer controller
	macro rule r_failureAdapt =
		let ($cameraOCself = cameraOC(self)) in
			//A camera can adapt from an external failure condition if it is not failed and it is not failed (stopped) in the next state
			//First part of the analyze condition. It is common to both subloops.   
			if stateOC(self) != FAILED and not stopCam($cameraOCself) then
				par
					//@A A1: First analyze computation (first subloop)
					if stateOC(self) = MASTERWITHSLAVES and slavesNotAlive($cameraOCself) then
						r_removeSlaveGone[]
					endif
					//@A A2: Second analyze computation (second subloop)
					if stateOC(self) = SLAVE and masterGone($cameraOCself) then
						par //Turn single organization
							r_turnMaster[]
							masterGone($cameraOCself) := false //unset signal
						endpar
					endif
				endpar
			endif
		endlet
		
		
	//MAPE control loops
	macro rule r_organizationControl =
		/*seq //PA: commentato
			r_selfFailureAdapt[] //Adaptation due to internal failure
			r_failureAdapt[] //ROBOUSTNESS: Adaptation due to external failure (silent nodes)
			r_congestionAdapt[] //FLEXIBILITY: Adaptation due to congestion
		endseq*/
		par //PA: proviamo in par
			r_selfFailureAdapt[] //Adaptation due to internal failure
			r_failureAdapt[] //ROBOUSTNESS: Adaptation due to external failure (silent nodes)
			r_orgContrFlexBehaviour[] //FLEXIBILITY: Adaptation due to congestion
		endpar



	//SelfHealingSubsystem
	
	//Knowledge updates for the Heart Beat mechanism (in the MASTER role, START mode)
	macro rule r_sendSignal =
		if elapsed_wait_time(self) then //Period elapsed
			let ($cameraSCHself = cameraSHC(self)) in
				//PA: slaves changed because of model checking
				//forall $s in Camera with contains(slaves($cameraSCHself), $s) do 
				forall $s in Camera with slaves($cameraSCHself, $s) do
					par
						if not isAlive($s) then //set signal
							isAlive($s) := true 
						endif
						stateSHC(self) := WAITFORRESPONSE
					endpar
			endlet
		endif

	//Knowledge updates for the Heart Beat mechanism (in the MASTER role)
	macro rule r_receiveSignal =
		let ($cameraSCHself = cameraSHC(self)) in
			if allSlavesAlive($cameraSCHself) then 
				par //Restart the mechanism when a feedback is received from all slaves
					stateSHC(self) := START
					forall $c in Camera with slaves($cameraSCHself, $c) do //unset signal
						//par
						imAlive($cameraSCHself, $c) := false
						//isAlive($c) := false endpar  Patrizia: pi� corretto qu�?
				endpar
			else //Timeout elapsed 
				if elapsed_alive_time(self) then
					par
						//PA: slaves changed because of model checking
						//forall $s in Camera with (contains(slaves($cameraSCHself), $s) and not imAlive($cameraSCHself,$s) ) do
						forall $s in Camera with slaves($cameraSCHself, $s) do
							//Communication between self-healer and organization controller
						 //par 
							//isAlive($s) := false endpar  Patrizia: pi� corretto qu�?
							if not imAlive($cameraSCHself,$s) then
								if not slaveGone($cameraSCHself, $s) then //set signal
									slaveGone($cameraSCHself, $s) := true
								endif
							else imAlive($cameraSCHself, $s) := false //unset signal
							endif //endpar
						stateSHC(self) := START
					endpar
				endif
			endif
		endlet

	//Knowledge updates for the Heart Beat mechanism (in the SLAVE role)
	macro rule r_tryGenerateEcho =
		let ($cameraSCHself = cameraSHC(self)) in
			if isAlive($cameraSCHself) then //@P
				par
					//imAlive(getMaster($cameraSCHself), $cameraSCHself) := true //echo signal
					let ($getMasterCameraSCHself = getMaster($cameraSCHself)) in
						if not(imAlive($getMasterCameraSCHself, $cameraSCHself)) then //set signal
							imAlive($getMasterCameraSCHself, $cameraSCHself) := true //echo signal
						endif
					endlet
					isAlive($cameraSCHself) := false //unset signal  ??? Patrizia: non dovremmo permettere a uno slave di resettare il segnale isAlive inviato dal suo master; dovrebbe farlo il master!
				endpar
			else
				let ($getMasterCameraSCHself2 = getMaster($cameraSCHself)) in
					if slaves($getMasterCameraSCHself2, $cameraSCHself) then
					//if stateC($getMasterCameraSCHself2) = MASTERWITHSLAVES then
						if elapsed_wait_time_plus_delta(self) then 
							if not masterGone($cameraSCHself) then //set signal
								masterGone($cameraSCHself) := true    //Indirect communication between the self-healer and the organization controller
							endif
						endif
					endif
				endlet
			endif
		endlet

	
	

	//@M_c Monitor computation executed by the self-healer controller; decentralized schema (the remaining part A->P->E is executed by the organization controller)
	macro rule r_failureDetect =
	   //The nested conditions can be grouped differnetly in order to be intended as unitary conditions of different M computations (subloops)
		let ($cameraSCHself = cameraSHC(self)) in
			if not stopCam($cameraSCHself) then
				par
					//Depending on the role:
					if isMasterWithSlaves($cameraSCHself) then
						if stateSHC(self) = START then
							r_sendSignal[] //send beat signals to all slaves of the organization
						else
							if stateSHC(self) = WAITFORRESPONSE then
								r_receiveSignal[]
							endif
						endif
					endif
					if isSlave($cameraSCHself) then
						if stateSHC(self) = START then
							r_tryGenerateEcho[]
						endif
					endif
				endpar
			endif
		endlet

    //@M_s
    macro rule r_selfFailureDetect =
		let ($cameraSCHself = cameraSHC(self)) in
			par
				//M1: First M computation (first subloop), centralized (waterfall) schema, no A and P computations  
				if ((stateSHC(self) = START or stateSHC(self) = WAITFORRESPONSE) and stopCam($cameraSCHself)) then 
					stateSHC(self) := FAILED2 //@E
				endif
				//M2: Second M computation (second subloop), centralized (waterfall) schema, no A and P computations  
				if stateSHC(self) = FAILED2  and startCam($cameraSCHself) then
						stateSHC(self) := START //@E
				endif
			endpar
		endlet
		
	macro rule r_selfHeal =
		par
			r_selfFailureDetect[] //MAPE control loop for adaptation due to internal failure
			r_failureDetect[] //Monitoring for adaptation due to external failure (ROBOUSTNESS)
		endpar

	//Si potrebbero avere delle proprieta' di safety sui valori delle funzioni
	CTLSPEC ag((forall $c1 in Camera, $c2 in Camera with
	            slaves($c1,$c2) implies stateC($c1) = MASTERWITHSLAVES and stateC($c2) = SLAVE))

	//Invariants (mapped to AG)
	//I1: it's not possible that all cameras are slave (or that all cameras are master with slaves)
	//all cameras cannot be slaves (or master with slaves) at the same time
	invariant over stateC: not (forall $c in Camera with stateC($c) = SLAVE)
	invariant over stateC: not (forall $c in Camera with stateC($c) = MASTERWITHSLAVES)
	//I2: non supportiamo il deadlock in modo diretto
	
	
	//F0: when a camera C detects a congestion and the next camera is master and congested, then
	//the two cameras form an organization where C is master with slaves
	CTLSPEC ag((congested(organizationController1) and congested(organizationController2) and stateC(c2) = MASTER) implies
	 			af(stateC(c1) = MASTERWITHSLAVES and slaves(c1,c2) and stateC(c2) = SLAVE))
	//F0a
	CTLSPEC ag((congested(organizationController1) and stateC(c1) = MASTER and not(congested(organizationController2))) implies
	 			ax(getMaster(c2) = c2))

	//These properties are also checked with scenario-based validation
	//F1: when a camera C detects a congestion and the next camera is master with slaves,
	//then C joins the organization and becomes master with slaves
	CTLSPEC ag((congested(organizationController1) and stateC(c2) = MASTERWITHSLAVES) implies
	 			af(stateC(c1) = MASTERWITHSLAVES and slaves(c1,c2)))
	CTLSPEC ag((congested(organizationController2) and stateC(c3) = MASTERWITHSLAVES) implies
	 			af(stateC(c2) = MASTERWITHSLAVES and slaves(c2,c3)))
	CTLSPEC ag((congested(organizationController3) and stateC(c4) = MASTERWITHSLAVES) implies
	 			af(stateC(c3) = MASTERWITHSLAVES and slaves(c3,c4)))

	//F2: sbagliata, perché una camera che vede congestione guarda sempre verso destra
	//CTLSPEC ag((congested(organizationController3) and stateC(c2) = SLAVE and slaves(c1,c2)) implies
	// 			af(stateC(c1) = MASTERWITHSLAVES and stateC(c3) = SLAVE and slaves(c1,c3)))
	//CTLSPEC ag((congested(organizationController4) and stateC(c3) = SLAVE and slaves(c2,c3)) implies
	// 			af(stateC(c2) = MASTERWITHSLAVES and stateC(c4) = SLAVE and slaves(c2,c4)))
	//CTLSPEC ag((congested(organizationController4) and stateC(c3) = SLAVE and slaves(c1,c3)) implies
	// 			af(stateC(c1) = MASTERWITHSLAVES and stateC(c4) = SLAVE and slaves(c1,c4)))
	
	
	//F3 per giustificare la presenza della regola r_notifyPendingSlavesMasterChanged
	CTLSPEC ag((congested(organizationController1) and congested(organizationController2) and
	            congested(organizationController3) and not(congested(organizationController4))
				and stateC(c1) = MASTER and stateC(c2) = MASTER and stateC(c3) = MASTER and stateC(c4) = MASTER) implies
				ef(stateC(c1) = MASTERWITHSLAVES and slaves(c1,c2) and slaves(c1,c3))
				)

	
	//R1: if camera 1 ("master with slaves") fails and c2 is slave of c1, then eventually the sealf healing controller of
	//c2 informs c2 that its master is gone
	//CTLSPEC ag((stateC(c1) = FAILED and slaves(c1,c2)) implies ef(masterGone(c2))) //wrong
	CTLSPEC ag((stateC(c1) = FAILED and slaves(c1,c2)) implies ef(not(slaves(c1,c2))))

	//R2: when the slaves have detected a failure, their organization controllers will form a new organization
	CTLSPEC ag((stateC(c1) = FAILED and slaves(c1,c2) and slaves(c1,c3)) implies
				 eu(((congested(organizationController2) and congested(organizationController3)),
				    (stateC(c2) = MASTERWITHSLAVES and stateC(c3) = SLAVE and slaves(c2,c3) and congested(organizationController2) and congested(organizationController3)))
				))

	//R3: implicata dalla R2?

	//R4: the neighbor relations are correctly arranged after a failure
	CTLSPEC ag((stateC(c1) != FAILED and stateC(c2) = FAILED) implies next(c1) = c3)
	CTLSPEC ag((stateC(c2) != FAILED and stateC(c3) = FAILED) implies next(c2) = c4)
	CTLSPEC ag((stateC(c2) = FAILED and stateC(c3) != FAILED) implies prev(c3) = c1)
	CTLSPEC ag((stateC(c3) = FAILED and stateC(c4) != FAILED) implies prev(c4) = c2)
	CTLSPEC ag((stateC(c1) = FAILED and stateC(c2) != FAILED) implies isUndef(prev(c2)))
	CTLSPEC ag((stateC(c4) = FAILED and stateC(c3) != FAILED) implies isUndef(next(c3)))

	main rule r_main =
		//PA: commentato
		/*forall $a in Agent do
			program($a)*/
		//PA: con par in r_organizationControl
		par
			program(c1)
			program(trafficMonitor1)
			program(organizationController1)
			program(selfHealingController1)
			program(c2)
			program(trafficMonitor2)
			program(organizationController2)
			program(selfHealingController2)
			program(c3)
			program(trafficMonitor3)
			program(organizationController3)
			program(selfHealingController3)
			program(c4)
			program(trafficMonitor4)
			program(organizationController4)
			program(selfHealingController4)
		endpar

default init s0:
	function stateC($c in Camera) = MASTER
	function imAlive($c in Camera,$s in Camera) = false
	function isAlive($c in Camera) = false
	//PA: slaves changed because of model checking
	//function slaves($c in Camera) = []
	function slaves($c in Camera, $s in Camera) = false
	
	function cong($c in Camera) = false
	function no_cong($c in Camera) = false
	function s_offer($c in Camera) = false
	function change_master($c in Camera) = false
	function newSlave($c in Camera, $s in Camera) = false
	function slaveGone($c in Camera, $s in Camera) = false
	function getMaster($c in Camera) = $c
	function masterGone($c in Camera) = false

	//function state2($t in TrafficMonitor) = NOCONGESTION  //PA: commentato perche' usato in r_trafficMonitor che non viene usato
	//function cars($t in TrafficMonitor) = 0 //PA: commentato perche' usato in r_trafficMonitor che non viene usato
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