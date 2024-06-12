asm mainForModelCheckingWithPatterns

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLLibrary

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






	//Knowledge
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
		let ($cameraOCc = cameraOC($c)) in
			stateC($cameraOCc)
		endlet




	//SIGNAL COMMUNICATION POLICIES

	//A signal is sent if it has not already been sent
	//macro rule r_setSignal($s in Boolean) =
		//$s = true

	//When the receiver reades a signal, it must also "remove" it 
	//macro rule r_unsetSignal($s in Boolean) =
		//$s = false





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
	//Remove all slaves (if any) turning MASTER2
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
					//s_offer($s) := false //non dovrebbe essere il master ha resettare il segnale degli slaves
				endpar
		endlet

	//@P
	macro rule r_turnSlave ($master in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			par
				if not newSlave($master, $cameraOCself) then //set signal
					newSlave($master, $cameraOCself) := true
				endif
				r_setMaster[$master]
				r_removeSlavesTurningSlave[]
			endpar
		endlet

	macro rule r_turnMaster = //@E
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
				//state(self) := MASTERWITHSLAVES2
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

	macro rule r_analyzeCongestion = //@E
		let ($cameraOCself = cameraOC(self)) in 
			if m_offer($cameraOCself) then
				r_turnMasterWithSlaves[]
			else
				if s_offer($cameraOCself) then
					par
						let ($prevCameraOCself = prev($cameraOCself)) in
							r_turnSlave[$prevCameraOCself]
						endlet
						s_offer($cameraOCself) := false //unset signal
					endpar
				endif
			endif
		endlet

	//@A
	macro rule r_receiveOrgSignals =
		let ($cameraOCself = cameraOC(self)) in
			par
				if change_master($cameraOCself) then //Master changed!
					//@P
					par
						let ($getMasterCameraOCself = getMaster($cameraOCself)) in
							let ($prevGetMasterCameraOCself = prev($getMasterCameraOCself)) in
								par
									//r_setMaster[prev(getMaster($cameraOCself))]
									r_setMaster[$prevGetMasterCameraOCself]
									if not newSlave($prevGetMasterCameraOCself, $cameraOCself) then //set signal
										//newSlave(prev(getMaster($cameraOCself)), $cameraOCself) := true
										newSlave($prevGetMasterCameraOCself, $cameraOCself) := true					
									endif				
								endpar
							endlet
						endlet
						change_master($cameraOCself) := false //unset signal
					endpar
				endif
				if masterGone($cameraOCself) then
					par
						r_turnMaster[]
						masterGone($cameraOCself) := false //unset signal
					endpar
				endif
				if m_offer($cameraOCself) //There are pending slaves not engaged
				then
					r_notifyPendingSlavesMasterChanged[]
				endif
			endpar
		endlet

	//@A
	macro rule r_analyzeOrganization =
		let ($cameraOCself = cameraOC(self)) in
			if m_offer($cameraOCself) then
				r_addNewSlave[]
			else
				//PA: slaves changed because of model checking
				//if isEmpty(slaves($cameraOCself)) //Simply turn master
				if (forall $s in Camera with not(slaves($cameraOCself, $s))) //Simply turn master
				then
					r_turnMaster[]
				else
					if (s_offer($cameraOCself) and congested(self)) then
						par
							//r_turnSlave[prev($cameraOCself)]
							let ($prevCameraOCself = prev($cameraOCself)) in
								r_turnSlave[$prevCameraOCself]
							endlet
							s_offer($cameraOCself) := false //unset signal
						endpar
					endif
				endif
			endif
		endlet

	//@M_s self_aware (no A function)
	macro rule r_selfFailureAdapt =
		let ($cameraOCself = cameraOC(self)) in
			par
				if stopCam($cameraOCself) then //@M_s
					if stateOC(self) != FAILED then //@A
						stateC($cameraOCself) := FAILED //@E
					endif
				endif
				if startCam($cameraOCself) then //@M_s
					if stateOC(self) = FAILED then //@A
						r_turnMaster[] //@E
					endif
				endif
			endpar
		endlet

	//@M_c context_aware (no A function)
	macro rule r_failureAdapt =
		let ($cameraOCself = cameraOC(self)) in
			if not stopCam($cameraOCself) then //@M_c context_aware
				if stateOC(self) != FAILED then //@A
					par
						if stateOC(self) = MASTERWITHSLAVES and slavesNotAlive($cameraOCself) then //@A
							r_removeSlaveGone[]
						endif
						if stateOC(self) = SLAVE and masterGone($cameraOCself) then //@A
							r_turnMaster[] //@E
						endif
					endpar
				endif
			endif
		endlet

	macro rule r_master_congestionAdapt =
		let ($cameraOCself = cameraOC(self)) in
			if not stopCam($cameraOCself) then //@M
        		if cong($cameraOCself) then //@M
            		if stateOC(self) != FAILED then //@A
                		if not congested(self) then //@A
                    		par //@A
                        		congested(self) := true 
                        		if isDef(next($cameraOCself)) then
                            		s_offer(next($cameraOCself)) := true
                        		endif
                    		endpar
                		else
                    		r_analyzeCongestion[] //@E
                		endif
            		endif
        		else
            		if stateOC(self) != FAILED and congested(self) then //@A
                		r_analyzeCongestion[] //@E
            		endif
        		endif
    		endif
    	endlet

	macro rule r_slave_congestionAdapt =
		let ($cameraOCself = cameraOC(self)) in
		    if not stopCam($cameraOCself) then //@M
		        if no_cong($cameraOCself) then //@M
		            if stateOC(self) != FAILED and not masterGone($cameraOCself) then //@A
		                par
		                    congested(self) := false
		                    slaveGone(getMaster($cameraOCself), $cameraOCself) := true
		                    r_turnMaster[]
		                endpar
					endif
		        else
		            if stateOC(self) != FAILED and not masterGone($cameraOCself) then //@A
		                r_receiveOrgSignals[]
		            endif
		        endif
		    endif
    	endlet

	macro rule r_masterWithSlaves_congestionAdapt =
		let ($cameraOCself = cameraOC(self)) in
		    if not stopCam($cameraOCself) then //@M
		        if no_cong($cameraOCself) then //@M
		            if stateOC(self) != FAILED and not slavesNotAlive($cameraOCself) then //@A
		                par
		                    congested(self) := false
		                    r_removeSlavesTurningMaster[]
		                endpar
		            endif
		        else
		            if stateOC(self) != FAILED and not slavesNotAlive($cameraOCself) then //@A
		                r_analyzeOrganization[]
		            endif
		        endif
		    endif
		endlet

	//@M_c context_aware
	macro rule r_congestionAdapt =
		par
			r_master_congestionAdapt[]
			r_slave_congestionAdapt[]
			r_masterWithSlaves_congestionAdapt[]
		endpar

	//MAPE control loops
	macro rule r_organizationControl =
		par
			r_selfFailureAdapt[] //Adaptation due to internal failure
			r_failureAdapt[] //Adaptation due to external failure (silent nodes)
			r_congestionAdapt[] //Adaptation due to congestion
		endpar

	macro rule r_organizationController =
		par  
			r_organizationControl[] //Adaptation due to congestion 
			r_failureAdapt[] //Adaptation due to external failure
			r_selfFailureAdapt[] //Adaptation due to internal failure
		endpar



	//SelfHealingSubsystem
	macro rule r_sendSignal = //@P
		if elapsed_wait_time(self) then
			let ($cameraSCHself = cameraSHC(self)) in
				forall $s in Camera with slaves($cameraSCHself, $s) do
					par
						if not isAlive($s) then //set signal
							isAlive($s) := true //ping signal
						endif
						stateSHC(self) := WAITFORRESPONSE
					endpar
			endlet
		endif

	macro rule r_receiveSignal = //@P
		let ($cameraSCHself = cameraSHC(self)) in
			if allSlavesAlive($cameraSCHself) then
				par
					stateSHC(self) := START //@E
					forall $c in Camera with slaves($cameraSCHself, $c) do //unset signal
						imAlive($cameraSCHself, $c) := false
				endpar
			else
				if elapsed_alive_time(self) then
					par
						forall $s in Camera with slaves($cameraSCHself, $s) do
							if not imAlive($cameraSCHself,$s) then
								if not slaveGone($cameraSCHself, $s) then //set signal
									slaveGone($cameraSCHself, $s) := true //@E
								endif
							else imAlive($cameraSCHself, $s) := false //unset signal
							endif
						stateSHC(self) := START //@E
					endpar
				endif
			endif
		endlet

	macro rule r_tryGenerateEcho = //@P
		let ($cameraSCHself = cameraSHC(self)) in
			if isAlive($cameraSCHself) then
				par
					let ($getMasterCameraSCHself = getMaster($cameraSCHself)) in
						if not(imAlive($getMasterCameraSCHself, $cameraSCHself)) then //set signal
							imAlive($getMasterCameraSCHself, $cameraSCHself) := true //echo signal @E
						endif
					endlet
					isAlive($cameraSCHself) := false //unset signal
				endpar
			else
				let ($getMasterCameraSCHself2 = getMaster($cameraSCHself)) in
					if slaves($getMasterCameraSCHself2, $cameraSCHself) then
						if elapsed_wait_time_plus_delta(self) then //Communication between self-healer and organization controller
							if not masterGone($cameraSCHself) then //set signal
								masterGone($cameraSCHself) := true
							endif
						endif
					endif
				endlet
			endif
		endlet

	//@M_s self-aware
	macro rule r_selfFailureDetect =
		let ($cameraSCHself = cameraSHC(self)) in
			par
				if stopCam($cameraSCHself) then //@M_s self-aware
					if stateSHC(self) = START or stateSHC(self) = WAITFORRESPONSE then //@A
						stateSHC(self) := FAILED2 //@E
					endif
				endif
				if startCam($cameraSCHself) then //@M_s self-aware
					if stateSHC(self) = FAILED2 then //@A
						stateSHC(self) := START //@E
					endif
				endif
			endpar
		endlet

	//@M_c context-aware
	macro rule r_failureDetect =
		let ($cameraSCHself = cameraSHC(self)) in
			if not stopCam($cameraSCHself) then //@M_c context-aware
				par //@A
					if isMasterWithSlaves($cameraSCHself) then
						if stateSHC(self) = START then
							r_sendSignal[] //@P: send heart beat signals to all slaves
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

	macro rule r_selfHeal =
		par 
			r_failureDetect[] //Adaptation due to external failure
			r_selfFailureDetect[] //Adaptation due to internal failure
		endpar

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
				)

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
