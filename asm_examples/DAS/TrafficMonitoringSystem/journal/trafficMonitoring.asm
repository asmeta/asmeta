asm trafficMonitoring
//main ASM: Camera system of systems
/* 
We prefer for control state ASMs the parallel synchronous understanding of ASMs as firing in each step every rule (by the par rule). We control possible conflicts, e.g. by taking care that the rule guards of rules fireable in a 
control state are disjoint. In case of possible conflicts we sequencialize the transitions giving priority to certain events (e.g., those coming from the environment) that cannot be delayed.
Here for the camera behavior, we give priority to the event stopCam from the environment (a monitored function). We assume the other guards are disjoint since related to actions executed by the same agent 
(the organization controller) in two separate modes. 
*/

//Annotations convention: If a MAPE loop  MAPEBIG is the composition, for example, of two subloops  MAPEBIG_SUB1 and MAPEBIG_SUB2, a MAPE computation X  in the first sub-loop is annotated (in its occurrence, i.e. call rule, not in its declaration) by @X_MAPEBIG_SUB1
//while in the second sub-loop it is annotated by  @X_MAPEBIG_SUB2

import ../../../STDL/StandardLibrary
import ../../../STDL/LTLLibrary

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
	controlled stateC: Camera -> CameraState

	//ID of the monitored camera
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

	//Knowledge:
	function isMaster($c in Camera) = ( stateC($c) = MASTER or stateC($c) = MASTERWITHSLAVES ) 
	function isMasterWithSlaves($c in Camera) =  stateC($c) = MASTERWITHSLAVES
	function isSlave($c in Camera) =  stateC($c) = SLAVE
	function hasNext($c in Camera) = isDef(next($c))

	function next($c in Camera) =
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
		(exist $s in Camera with (slaves($c, $s) and  slaveGone($c, $s)))

	function allSlavesAlive($c in Camera) =
		(forall $s in Camera with (slaves($c, $s) and imAlive($c, $s)))

	function m_offer($c in Camera) =
	   (exist $s in Camera with newSlave($c, $s) )

	//OrganizationMiddleware
	function stateOC($c in OrganizationController) =
		let ($cameraOCc = cameraOC($c)) in
			stateC($cameraOCc)
		endlet

	macro rule r_skip = skip //for debugging

	//---------------------------------------------------------------------------------------------------------------
	//CameraAgentSubsystem (the managed system)
    //----------------------------------------------------------------------------------------------------------------
	
	//We abstract its behavior by the skip rule
	macro rule r_cameraBehavior =
		skip

    //---------------------------------------------------------------------------------------------------------------
	//OrganizationMiddlewareSubsystem:
    //----------------------------------------------------------------------------------------------------------------

	//------------------------------------------------------
	//
	//Macro rules for (atomic) adaptation actions in a master/slave organization:
    //

	macro rule r_clearSlaves =
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera do
				slaves($cameraOCself, $s) := false
		endlet

	macro rule r_addSlave ($s in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			slaves($cameraOCself, $s) := true
		endlet

	macro rule r_removeSlave ($s in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			slaves($cameraOCself, $s) := false
		endlet


	macro rule r_setMaster($newMaster in Camera) =
		let ($cameraOCself = cameraOC(self)) in
			getMaster($cameraOCself) := $newMaster
		endlet

	//------------------------------------------------------
        
	//------------------------------------------------------
	//
	//Macro rules for planning and executing adaptation actions in a master/slave organization:
    //
	
	//Remove all slaves (if any) turning SLAVE
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

	//Remove all slaves (if any) turning MASTER
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

	
	//Remove all slaves gone
	macro rule r_removeSlaveGone =
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera with ( slaves($cameraOCself, $s) and slaveGone($cameraOCself, $s) ) do
				par
					r_removeSlave[$s] 
					slaveGone($cameraOCself, $s) := false //unset signal @E
				endpar
		endlet

	
	//Add all new slaves on one shot since there could be
	//more than one SLAVE setting function s_offer to the
	//same value true (so without generating inconsistent update).
	macro rule r_addNewSlave =
		let ($cameraOCself = cameraOC(self)) in
			forall $s in Camera with newSlave($cameraOCself, $s) do
				par
					r_addSlave[$s] 
					newSlave($cameraOCself, $s) := false //unset signal  @E
				endpar
		endlet
	
	macro rule r_turnSlave ($master in Camera) =
		par
			r_setMaster[$master] 
			r_removeSlavesTurningSlave[]
		endpar

	
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

	macro rule r_notifyPendingSlavesMasterChanged($c in Camera) =
		forall $s in Camera with newSlave($c, $s) do
			par 
				newSlave($c, $s) := false //unset signal
				if not change_master($s) then //set signal
					change_master($s) := true 
				endif
			endpar

	macro rule r_turnSingleOrganization($c in Camera) =
		par
			congested(self) := false 
			let ($getMasterCameraOCself = getMaster($c)) in
				if not slaveGone($getMasterCameraOCself, $c) then //set signal
					slaveGone($getMasterCameraOCself, $c) := true 
				endif
			endlet
			r_turnMaster[]
		endpar

	macro rule r_changeOrganization($c in Camera) =	
		par
			let ($getMasterCameraOCself = getMaster($c)) in
				let ($prevGetMasterCameraOCself = prev($getMasterCameraOCself)) in
					par
						r_setMaster[$prevGetMasterCameraOCself] 
						if not newSlave($prevGetMasterCameraOCself, $c) then //set signal
							newSlave($prevGetMasterCameraOCself, $c) := true  			
						endif
					endpar
				endlet
			endlet
			change_master($c) := false //unset signal @E
		endpar

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
			s_offer($c) := false //unset signal  @E
		endpar

	//------------------------------------------------------

    //Macro rule for knowledge updates carried out by a monitor computations only: 
     macro rule r_send_s_offer_message($c in Camera) =
		let ($nextCameraOCself = next($c)) in
			if isDef($nextCameraOCself) then
				if not s_offer($nextCameraOCself) then //set signal
					s_offer($nextCameraOCself) := true 
				endif
			endif
		endlet

	//Monitor computation: detect congestion for the first time (cong($c) and not congested(self))
	macro rule r_detectCongestion($c in Camera) =
		if not(stopCam($c)) and cong($c) and not congested(self) then
			par  //Knowledge's  updates for undirect triggering (decentralized computation): s_offer for communication with other cameras' organization controllers, congested for enacting other sub-loops locally
				congested(self) := true   
				r_send_s_offer_message[$c] 
			endpar
		endif

	macro rule r_turnMasterWithSlaves =
		let ($cameraOCself = cameraOC(self)) in
			par
				r_addNewSlave[]
				stateC($cameraOCself) := MASTERWITHSLAVES 
			endpar
		endlet

	macro rule r_analyzeCongestionTMS($c in Camera) =
		if congested(self) and m_offer($c) then
			r_turnMasterWithSlaves[] //@PE_MAPEflex_M_TMS
		endif

	macro rule r_analyzeCongestionTS($c in Camera) = 
		if congested(self) and not(m_offer($c)) and s_offer($c) then
			r_joinOrganization[$c] //@PE_MAPEflex_M_TS
		endif

	//Two MAPE sub-loops executed in a conditional manner (by their mutual exclusive guards) to give priority to m offer signals w.r.t. s offer signals
	macro rule r_analyzeCongestion($c in Camera) =
		par
			r_analyzeCongestionTMS[$c]//@MA_MAPEflex_M_TMS
			r_analyzeCongestionTS[$c]//@MA_MAPEflex_M_TS
		endpar

	//MAPEflex_M: MAPE loop for the flexibility sub-concern in the MASTER role (default role): negotiations of the master-election mechanism when congestion is detected.
	//It is made of three MAPE subloops:  MAPEflex_M_CD (only a monitor computation) for detecting congestion for the first time, MAPEflex_M_TMS to turn master with slaves when the next alive camera accept to become slave 
	//in a congested situation, and MAPEflex_M_TS to turn slave joining the organization of the requester camera  when receive the request to become slave in a congested situation.
	//Information Sharing pattern among the M computations of the organization controllers; shared information: signals m_offer and s_offer. 
	macro rule r_masterBehaviour($c in Camera) =
		par	//Truly, M and APE computations are executed by the organization controller of the same camera in alternative mode since their triggering conditions are mutually exclusive.
			r_detectCongestion[$c]    //@M_MAPEflex_M_CD
			r_analyzeCongestion[$c]
		endpar

	macro rule r_checkForMasterChanged($c in Camera) =
		if change_master($c) then //Master changed!
			r_changeOrganization[$c] //@PE_PE1_MAPEflex_S_sub2
		endif

	macro rule r_checkForMasterGone($c in Camera) =
		if masterGone($c) then //Turn master of a single single organization
			//@PE_PE2_MAPEflex_S_sub2
			par
				r_turnMaster[]
				masterGone($c) := false //unset signal
			endpar
		endif

	macro rule r_notifyNotYetEngagedSlaves($c in Camera) =
		if m_offer($c) then //Notify slaves not yet engaged in the organization that the master is another camera! 
			r_notifyPendingSlavesMasterChanged[$c] //@PE_PE3_MAPEflex_S_sub2
		endif

	//Three A computations executed in parallel 
	macro rule r_analyzeOrgSignals ($c in Camera) =
		par
			r_checkForMasterChanged[$c] //@A_A1_MAPEflex_S_sub2
			r_checkForMasterGone[$c] //@A_A2_MAPEflex_S_sub2
			r_notifyNotYetEngagedSlaves[$c] //@A_A3_MAPEflex_S_sub2
		endpar

	//MAPEflex_S: MAPE loop for the flexibility sub-concern in the SLAVE role: deal with the end of congestion (first sub-loop) and master changes in a congested situation (second sub-loop) .
	//Information Sharing pattern among the M computations of the organization controllers; shared information: signals m_offer and s_offer. 
	macro rule r_slaveBehaviour($c in Camera) =
	//MAPEflex_S_sub1: First sub-loop, centralized (waterfall) schema 
		//@M_MAPEflex_S_sub1 No longer congested!
		if not(stopCam($c)) and  not(congestion($c)) then //@A_MAPEflex_S_sub1
			if not(masterGone($c)) then
				r_turnSingleOrganization[$c] //@PE_MAPEflex_S_sub1
			endif
		else // Still congested!
			//MAPEflex_S_sub2: Second sub-loop, centralized (waterfall) schema 
			//@M_MAPEflex_S_sub2
			if not(stopCam($c)) then
				r_analyzeOrgSignals[$c] //Decentralized A computation (three A sub-computations)
			endif 
		endif


	//Three A computations executed in an conditional manner to prioritize the events (guard can be refactored to replace the conditional rule by a par rule)
	macro rule r_analyzeOrganization ($c in Camera)=
		//@A_A1_MAPEflex_MWS_sub2
		if m_offer($c) then
			r_addNewSlave[] //@P_P1_MAPEflex_MWS_sub2 A new slave asked to join the organization
		else
			//@A_A2_MAPEflex_MWS_sub2 If there are no slaves simply turn master
			if (forall $s in Camera with not(slaves($c, $s))) then
				r_turnMaster[]  //@P_P2_MAPEflex_MWS_sub2
			else 
			        //@A_A3_MAPEflex_MWS_sub2 Turn slave if invited by the master of a previous organization in the traffic direction.
				if s_offer($c) then
					r_joinOrganization[$c] //@P_P3_MAPEflex_MWS_sub2
				endif 
			endif
		endif

	//MAPEflex_MWS: MAPE loop for the flexibility sub-concern in the MASTERWITHSLAVES role: deal with the end of congestion (first sub-loop) and  slavery relation changes in the organization in a congested situation (second sub-loop) .
	//Information Sharing pattern among the M computations of the organization controllers; shared information: signals m_offer and s_offer. 	
	macro rule r_masterWithSlavesBehaviour($c in Camera) =
		//MAPEflex_MWS_sub1: First sub-loop, centralized (waterfall) schema 
		//@MA_MAPEflex_MWS_sub1 No longer congested!
		if not(stopCam($c)) and  not(congestion($c)) then
			//@PE_MAPEflex_MWS_sub1 turn single organization 
			par
				r_removeSlavesTurningMaster[]
				congested(self) := false
			endpar
		else //still congested!	
			//MAPEflex_MWS_sub2: Second sub-loop, centralized (waterfall) schema 
			//@M_MAPEflex_MWS_sub2
			if not(stopCam($c)) then
				r_analyzeOrganization[$c] 
			endif
		endif

	macro rule r_orgContrFlexBehaviour =
		let ($cameraOCself = cameraOC(self)) in
			par
				if stateOC(self) = MASTER then
					r_masterBehaviour[$cameraOCself]
				endif //MASTER MAPE subloop (initial state)
				if stateOC(self) = SLAVE then
					r_slaveBehaviour[$cameraOCself]
				endif //SLAVE MAPE subloop
				if stateOC(self) = MASTERWITHSLAVES then
					r_masterWithSlavesBehaviour[$cameraOCself]
				endif //MASTERWITHSLAVE MAPE subloop
			endpar
		 endlet

	//MAPEintFail: MAPE loop for the ROBOUSTNESS concern due to internal failures.
	macro rule r_selfFailureAdapt =
		let ($cameraOCself = cameraOC(self)) in
			par
			//MAPEintFail_sub1: First subloop, centralized (waterfall) schema  
			//@M_MAPEintFail_sub1
				if startCam($cameraOCself)  then 
					//@A_MAPEintFail_sub1
					if stateOC(self) = FAILED then
						r_turnMaster[] //@P_MAPEintFail_sub1
					endif
				endif
			//MAPEintFail_sub2: Second subloop, centralized (waterfall) schema, no P computation
			//@M_MAPEintFail_sub2
				if stopCam($cameraOCself) then 
					//@A_MAPEintFail_sub2
					if stateOC(self) != FAILED then 
					stateC($cameraOCself) := FAILED //@E_MAPEintFail_sub2
					endif
				endif
			endpar
		endlet

	//MAPEextFail: MAPE loop for the ROBOUSTNESS concern due to external failures.
 	//The main M_MAPEextFail computation of this loop  is in the rule r_failureDetect executed by the self-healer controller (indirect communication trhough signals masterGone and slaveGone) depending on the role of the camera.
   	//A monitor computation M0_MAPEextFail is also realized locally by the organization controller to adapt the camera from an external failure if it is not failed and it is not failed (stopped)  in the next state.
	//The remaining A P and E computations are two centralized subloops sub1 and sub2.
	macro rule r_failureAdapt =
		let ($cameraOCself = cameraOC(self)) in
			//@M_M0_MAPEextFail A camera can adapt from an external failure condition if it is not failed and it is not failed (stopped)  in the next state
			if stateOC(self) != FAILED and not stopCam($cameraOCself) then
				par
					//@A_MAPEextFail_sub1: First analyze computation (first subloop)
					if stateOC(self) = MASTERWITHSLAVES and slavesNotAlive($cameraOCself) then
						r_removeSlaveGone[]//@PE_MAPEextFail_sub1
					endif
					//@A_MAPEextFail_sub2: Second analyze computation (second subloop)
					if stateOC(self) = SLAVE and masterGone($cameraOCself) then
						par //Turn single organization
							r_turnMaster[] //@PE_MAPEextFail_sub2
							masterGone($cameraOCself) := false //@E_MAPEextFail_sub2 unset signal
						endpar
					endif
				endpar
			endif
		endlet  

	//MAPE control loops
	macro rule r_organizationControl =
		par 
			r_selfFailureAdapt[] //MAPEintFail -- ROBOUSTNESS concern for adaptation due to internal failure
			r_failureAdapt[] //MAPEextFail -- ROBOUSTNESS concern for adaptation due to external failure (silent nodes)
			r_orgContrFlexBehaviour[] //MAPEflex -- FLEXIBILITY concern for adaptation due to congestion
		endpar

	//---------------------------------------------------------------------------------------------------------------
	//SelfHealingSubsystem:
	//----------------------------------------------------------------------------------------------------------------

	//Knowledge updates for the Heart Beat mechanism (in the MASTER role, START mode)
	macro rule r_sendSignal =
		if elapsed_wait_time(self) then //Period elapsed
			let ($cameraSHCself = cameraSHC(self)) in
				forall $s in Camera with slaves($cameraSHCself, $s) do
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
		let ($cameraSHCself = cameraSHC(self)) in
			if allSlavesAlive($cameraSHCself) then 
				par //Restart the mechanism when a feedback is received from all slaves
					stateSHC(self) := START
					forall $c in Camera with slaves($cameraSHCself, $c) do //unset signal
						imAlive($cameraSHCself, $c) := false
				endpar
			else //Timeout elapsed 
				if elapsed_alive_time(self) then
					par
						forall $s in Camera with slaves($cameraSHCself, $s) do
							if not imAlive($cameraSHCself,$s) then
								if not slaveGone($cameraSHCself, $s) then //set signal
									slaveGone($cameraSHCself, $s) := true //Indirect communication between the self-healer and the organization controller of the local camera
								endif
							else
								imAlive($cameraSHCself, $s) := false //unset signal
							endif
						stateSHC(self) := START
					endpar
				endif
			endif
		endlet

	//Knowledge updates for the Heart Beat mechanism (in the SLAVE role)
	macro rule r_tryGenerateEcho =
		let ($cameraSHCself = cameraSHC(self)) in
			if isAlive($cameraSHCself) then 
				par
					let ($getMastercameraSHCself = getMaster($cameraSHCself)) in
						if not(imAlive($getMastercameraSHCself, $cameraSHCself)) then //set signal
							imAlive($getMastercameraSHCself, $cameraSHCself) := true //echo signal
						endif
					endlet
					isAlive($cameraSHCself) := false
				endpar
			else //Timeout elapsed for receiving the isAlive signal from the master; the master is silent.
				let ($getMastercameraSHCself2 = getMaster($cameraSHCself)) in
					if slaves($getMastercameraSHCself2, $cameraSHCself) then
						if elapsed_wait_time_plus_delta(self) then 
							if not masterGone($cameraSHCself) then //set signal
								masterGone($cameraSHCself) := true    //Indirect communication between the self-healer and the organization controller of the local camera
							endif
						endif
					endif
				endlet
			endif
		endlet

	//Monitor computation for the ROBOUSTNESS sub-concern due to external failures  in the role slave: deal with the heart-beat protocol in the role of SLAVE .
	macro rule r_slaveSHBehaviour($c in Camera) =
		if not stopCam($c) and  stateSHC(self) = START then
			r_tryGenerateEcho[]
		endif //knowledge updates: send echo beat signal (imAlive)  with timeout to the master of the organization.

	//Monitor computation for the ROBOUSTNESS sub-concern due to external failures  in the role master with slaves: deal with the heart-beat protocol in the role of MASTER WITH SLAVES .
	macro rule r_masterWithSlavesSHBehaviour($c in Camera) = 
		if not stopCam($c) then  
			par
				if stateSHC(self) = START then
					r_sendSignal[] //knowledge updates: send beat signals (isAlive) to all slaves of the organization
				endif
	      		if stateSHC(self) = WAITFORRESPONSE then
					r_receiveSignal[]
				endif //knowledge updates: receive echo beat signals (imAlive) with timeout from the slaves of the organization
            endpar
		endif

	//Depending on the role, a monitor computation executed by the self-healing subsystem gathers the required data locally from the managed camera, but it also requires coordination (Information Sharing) with monitor computations executed 
	//by the self-healing subsystems of the other cameras in order to gather the status of the other cameras and, eventually, recover from a silent node failure. Such a coordination is realized by an heart-beat protocol; the share
	//information are the beat signals imAlive and isAlive. 
	//Monitor computations executed by the self-healer controller depending on the camera's role;  the remaining APE computations are executed by the organization controller (decentralized computation through 
	//knowledge signals masterGone and slaveGone).
	macro rule r_failureDetect =
		let ($cameraSHCself = cameraSHC(self)) in
			par
				//Two monitor computations depending on the camera's role:
				if isSlave($cameraSHCself) then //@M_M1_MAPEextFail
					r_slaveSHBehaviour[$cameraSHCself]
				endif
				if isMasterWithSlaves($cameraSHCself) then //@M_M2_MAPEextFail
					r_masterWithSlavesSHBehaviour[$cameraSHCself]
				endif
			endpar
		endlet

    //@MAPE Two centralized subloops for failing and restarting, respectively.
    macro rule r_selfFailureDetect =
		let ($cameraSHCself = cameraSHC(self)) in
			par
				//First sub-loop @MA_MAE1
				if ((stateSHC(self) = START or stateSHC(self) = WAITFORRESPONSE) and stopCam($cameraSHCself)) then 
					stateSHC(self) := FAILED2 
				endif
				//Second sub-loop //@MA_MAE2				  
				if stateSHC(self) = FAILED2  and startCam($cameraSHCself) then
						stateSHC(self) := START 
				endif
			endpar
		endlet

	//MAPE control subloops for the ROBOUSTNESS concerns	
	macro rule r_selfHeal =
		par
			r_selfFailureDetect[] //MAPE control loop for adaptation due to internal failure
			r_failureDetect[] //Monitor computations for adaptation due to external failure 
		endpar

	// -- Requirement verification properties --
	/*LTLSPEC g((stateOC(organizationController1) = MASTER and congested(organizationController1) and m_offer(cameraOC(organizationController1))) implies
				o(stateOC(organizationController1) = MASTER and not(stopCam(cameraOC(organizationController1))) and cong(cameraOC(organizationController1)) and not(congested(organizationController1))) )
	LTLSPEC g((stateOC(organizationController2) = MASTER and congested(organizationController2) and not(m_offer(cameraOC(organizationController2))) and s_offer(cameraOC(organizationController2))) implies
				o(stateOC(organizationController1) = MASTER and not(stopCam(cameraOC(organizationController1))) and cong(cameraOC(organizationController1)) and not congested(organizationController1)) )
	LTLSPEC g((stateOC(organizationController1) = MASTER and congested(organizationController1) and m_offer(cameraOC(organizationController1))) implies
				o(stateOC(organizationController2) = MASTER and congested(organizationController2) and not(m_offer(cameraOC(organizationController2))) and s_offer(cameraOC(organizationController2))) )

	LTLSPEC g((stateOC(organizationController2) = MASTER and congested(organizationController2) and m_offer(cameraOC(organizationController2))) implies
				o(stateOC(organizationController2) = MASTER and not(stopCam(cameraOC(organizationController2))) and cong(cameraOC(organizationController2)) and not(congested(organizationController2))) )
	LTLSPEC g((stateOC(organizationController3) = MASTER and congested(organizationController3) and not(m_offer(cameraOC(organizationController3))) and s_offer(cameraOC(organizationController3))) implies
				o(stateOC(organizationController2) = MASTER and not(stopCam(cameraOC(organizationController2))) and cong(cameraOC(organizationController2)) and not congested(organizationController2)) )
	LTLSPEC g((stateOC(organizationController2) = MASTER and congested(organizationController2) and m_offer(cameraOC(organizationController2))) implies
				o(stateOC(organizationController3) = MASTER and congested(organizationController3) and not(m_offer(cameraOC(organizationController3))) and s_offer(cameraOC(organizationController3))) )

	LTLSPEC g((stateOC(organizationController3) = MASTER and congested(organizationController3) and m_offer(cameraOC(organizationController3))) implies
				o(stateOC(organizationController3) = MASTER and not(stopCam(cameraOC(organizationController3))) and cong(cameraOC(organizationController3)) and not(congested(organizationController3))) )
	LTLSPEC g((stateOC(organizationController4) = MASTER and congested(organizationController4) and not(m_offer(cameraOC(organizationController4))) and s_offer(cameraOC(organizationController4))) implies
				o(stateOC(organizationController3) = MASTER and not(stopCam(cameraOC(organizationController3))) and cong(cameraOC(organizationController3)) and not congested(organizationController3)) )
	LTLSPEC g((stateOC(organizationController3) = MASTER and congested(organizationController3) and m_offer(cameraOC(organizationController3))) implies
				o(stateOC(organizationController4) = MASTER and congested(organizationController4) and not(m_offer(cameraOC(organizationController4))) and s_offer(cameraOC(organizationController4))) )*/
	//arguments must be explicit
	LTLSPEC g((stateOC(organizationController1) = MASTER and congested(organizationController1) and m_offer(c1)) implies
				o(stateOC(organizationController1) = MASTER and not(stopCam(c1)) and cong(c1) and not(congested(organizationController1))) )
	LTLSPEC g((stateOC(organizationController2) = MASTER and congested(organizationController2) and not(m_offer(c2)) and s_offer(c2)) implies
				o(stateOC(organizationController1) = MASTER and not(stopCam(c1)) and cong(c1) and not congested(organizationController1)) )
	LTLSPEC g((stateOC(organizationController1) = MASTER and congested(organizationController1) and m_offer(c1)) implies
				o(stateOC(organizationController2) = MASTER and congested(organizationController2) and not(m_offer(c2)) and s_offer(c2)) )

	LTLSPEC g((stateOC(organizationController2) = MASTER and congested(organizationController2) and m_offer(c2)) implies
				o(stateOC(organizationController2) = MASTER and not(stopCam(c2)) and cong(c2) and not(congested(organizationController2))) )
	LTLSPEC g((stateOC(organizationController3) = MASTER and congested(organizationController3) and not(m_offer(c3)) and s_offer(c3)) implies
				o(stateOC(organizationController2) = MASTER and not(stopCam(c2)) and cong(c2) and not congested(organizationController2)) )
	LTLSPEC g((stateOC(organizationController2) = MASTER and congested(organizationController2) and m_offer(c2)) implies
				o(stateOC(organizationController3) = MASTER and congested(organizationController3) and not(m_offer(c3)) and s_offer(c3)) )

	LTLSPEC g((stateOC(organizationController3) = MASTER and congested(organizationController3) and m_offer(c3)) implies
				o(stateOC(organizationController3) = MASTER and not(stopCam(c3)) and cong(c3) and not(congested(organizationController3))) )
	LTLSPEC g((stateOC(organizationController4) = MASTER and congested(organizationController4) and not(m_offer(c4)) and s_offer(c4)) implies
				o(stateOC(organizationController3) = MASTER and not(stopCam(c3)) and cong(c3) and not congested(organizationController3)) )
	LTLSPEC g((stateOC(organizationController3) = MASTER and congested(organizationController3) and m_offer(c3)) implies
				o(stateOC(organizationController4) = MASTER and congested(organizationController4) and not(m_offer(c4)) and s_offer(c4)) )

	main rule r_main =
		par
			forall $c in Camera with true do
				program($c)
			forall $t in TrafficMonitor with true do
				program($t)
			forall $oc in OrganizationController with true do
				program($oc)
			forall $shc in SelfHealingController with true do
				program($shc)
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
