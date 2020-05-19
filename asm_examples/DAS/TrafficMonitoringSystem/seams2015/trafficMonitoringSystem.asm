asm trafficMonitoringSystem

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
	//CameraAgentSubsystem
	domain Camera subsetof Agent
	enum domain CameraState = {MASTER | SLAVE | MASTERWITHSLAVES | FAILED}
	domain TrafficMonitor subsetof Agent
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

	controlled stateC: Camera -> CameraState
	monitored camEnter: Camera -> Boolean
	monitored camLeave: Camera -> Boolean
	controlled cong: Camera -> Boolean
	controlled no_cong: Camera -> Boolean


	//Knowledge
	derived isMaster: Camera -> Boolean
	derived isMasterWithSlaves: Camera -> Boolean
	derived isSlave: Camera -> Boolean
	static nextNeighbor: Camera -> Camera 
	static prevNeighbor: Camera -> Camera
	derived next: Camera -> Camera // Next camera not in FAILED state in the traffic direction.
	derived prev: Camera -> Camera // Previous camera not in FAILED state in the traffic direction.
	derived hasNext: Camera -> Boolean 
	controlled getMaster : Camera -> Camera 
	controlled s_offer : Camera -> Boolean
	derived m_offer: Camera -> Boolean
	controlled newSlave : Prod(Camera,Camera)-> Boolean
	controlled change_master : Camera -> Boolean
	controlled slaves: Prod(Camera, Camera) -> Boolean
	controlled slaveGone: Prod(Camera,Camera)-> Boolean 
	controlled masterGone : Camera -> Boolean
	derived allSlavesAlive: Camera -> Boolean
	derived slavesNotAlive: Camera -> Boolean

	controlled imAlive: Prod(Camera,Camera)-> Boolean
	controlled isAlive: Camera -> Boolean

	monitored startCam: Camera -> Boolean
	monitored stopCam: Camera -> Boolean


	//OrganizationMiddleware
	derived stateOC: OrganizationController -> CameraState
	static cameraOC: OrganizationController -> Camera //ID of the camera
	controlled congested: OrganizationController -> Boolean
	monitored congestion: Camera -> Boolean


	//SelfHealingSubsystem
	controlled stateSHC: SelfHealingController -> HealingState
	static cameraSHC: SelfHealingController -> Camera
	monitored elapsed_wait_time: SelfHealingController -> Boolean
	monitored elapsed_alive_time: SelfHealingController -> Boolean
	monitored elapsed_wait_time_plus_delta: SelfHealingController -> Boolean

definitions:
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

	function slavesNotAlive($c in Camera) =
		(exist $s in Camera with (slaves($c, $s) and  slaveGone($c, $s)))

	function allSlavesAlive($c in Camera) =
		( forall $s in Camera with (slaves($c, $s) and imAlive($c, $s)))

	function m_offer($c in Camera) =
	   (exist $s in Camera with newSlave($c, $s) )


	//OrganizationMiddleware
	function stateOC($c in OrganizationController) =
		let ($cameraOCc = cameraOC($c)) in
			stateC($cameraOCc)
		endlet


	//main
	macro rule r_skip = skip

	macro rule r_cameraBehavior =
		skip


	//OrganizationMiddleware
 
	macro rule r_clearSlaves = //@E
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera do
				slaves($cameraOCself, $s) := false
		endlet

	//@E
	macro rule r_addSlave ($s in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			slaves($cameraOCself, $s) := true
		endlet

	//@E 
	macro rule r_removeSlave ($s in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			slaves($cameraOCself, $s) := false
		endlet

	//@E
	macro rule r_setMaster($newMaster in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			getMaster($cameraOCself) := $newMaster
		endlet

	//@P
	macro rule r_removeSlavesTurningSlave =
		let ($cameraOCself = cameraOC(self)) in
			par
				if (exist $c in Camera with slaves($cameraOCself, $c)) then
					par
						forall $s in Camera with slaves($cameraOCself, $s) do
							if not change_master($s) then //set signal
								change_master($s) := true
							endif
						r_clearSlaves[]
					endpar
				endif
				stateC($cameraOCself) := SLAVE
			endpar
		endlet

	//@P
	macro rule r_removeSlavesTurningMaster =
		let ($cameraOCself = cameraOC(self)) in
			par
				if (exist $c in Camera with slaves($cameraOCself, $c)) then
					par
						forall $s in Camera with slaves($cameraOCself, $s) do
							if not masterGone($s) then //set signal
								masterGone($s) := true
							endif
						r_clearSlaves[]
					endpar
				endif
				stateC($cameraOCself) := MASTER
			endpar
		endlet

	//@P
	macro rule r_removeSlaveGone =
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera with ( slaves($cameraOCself, $s) and slaveGone($cameraOCself, $s) )
			do
				par
					r_removeSlave[$s]
					slaveGone($cameraOCself, $s) := false //unset signal
				endpar
		endlet

	//@P
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
				if change_master($cameraOCself) then //@P
					par
						let ($getMasterCameraOCself = getMaster($cameraOCself)) in
							let ($prevGetMasterCameraOCself = prev($getMasterCameraOCself)) in
								par
									r_setMaster[$prevGetMasterCameraOCself]
									if not newSlave($prevGetMasterCameraOCself, $cameraOCself) then //set signal
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
				if m_offer($cameraOCself) then
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
				if (forall $s in Camera with not(slaves($cameraOCself, $s))) then
					r_turnMaster[]
				else
					if (s_offer($cameraOCself) and congested(self)) then
						par
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
	
	//F4
	CTLSPEC ag((congested(organizationController1) and congested(organizationController2) and
	            congested(organizationController3) and not(congested(organizationController4))
				and stateC(c1) = MASTER and stateC(c2) = MASTER and stateC(c3) = MASTER and stateC(c4) = MASTER) implies
				ef(stateC(c1) = MASTERWITHSLAVES and slaves(c1,c2) and slaves(c1,c3))
				)

	//R1: if camera 1 ("master with slaves") fails and c2 is slave of c1, then eventually the sealf healing controller of
	//c2 informs c2 that its master is gone
	CTLSPEC ag((stateC(c1) = FAILED and slaves(c1,c2)) implies ef(not(slaves(c1,c2))))

	//R2: when the slaves have detected a failure, their organization controllers will form a new organization
	CTLSPEC ag((stateC(c1) = FAILED and slaves(c1,c2) and slaves(c1,c3)) implies
				  e((congested(organizationController2) and congested(organizationController3)),
				    (stateC(c2) = MASTERWITHSLAVES and stateC(c3) = SLAVE and slaves(c2,c3) and congested(organizationController2) and congested(organizationController3)))
				)

	//R4: the neighbor relations are correctly arranged after a failure
	CTLSPEC ag((stateC(c1) != FAILED and stateC(c2) = FAILED) implies next(c1) = c3)
	CTLSPEC ag((stateC(c2) != FAILED and stateC(c3) = FAILED) implies next(c2) = c4)
	CTLSPEC ag((stateC(c2) = FAILED and stateC(c3) != FAILED) implies prev(c3) = c1)
	CTLSPEC ag((stateC(c3) = FAILED and stateC(c4) != FAILED) implies prev(c4) = c2)
	CTLSPEC ag((stateC(c1) = FAILED and stateC(c2) != FAILED) implies isUndef(prev(c2)))
	CTLSPEC ag((stateC(c4) = FAILED and stateC(c3) != FAILED) implies isUndef(next(c3)))

	main rule r_main =
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