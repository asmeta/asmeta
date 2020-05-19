//managing subsystem for flexibility
module OrganizationMiddleware
import ../../../../../STDL/StandardLibrary
//import of the Knowledge module
import Knowledge
export *

/* 
An Organization Controller runs on each camera and it is responsible for managing organizations, based on the information it gets from the traffic monitor agent (the signal cong) of the camera. A master/slave control model is adopted to structure organizations in case of congestion. To keep the master election policy simple, we assume the camera id is a monotonically increasing function on the traffic direction and the camera with the lowest id becomes master.   
 
A camera starts as MASTER of a single member organization. 
When a congestion is detected (in the role MASTER) the organization controller sends a request to join as slave of the organization (the s_offer signal) to the next alive camera (if any) in the direction of the traffic flow. Depending on the traffic condition of the next camera and its current role, the organizations may be restructured. If traffic is not jammed (congested = false) in the viewing range of the next camera, organizations are not changed, otherwise  organizations are joined: the next camera  becomes slave and the camera becomes master of the joined organization. 
   
When a SLAVE or a MASTER with slaves detects the traffic in its viewing range is no longer jammed based on the information it gets from the traffic monitor (the signal no_cong), it leaves the organization it belongs to and becomes MASTER of a single organization again. In case of a MASTER with slaves, all its depending slaves (whose traffic may still be jammed) will turn MASTER too of single organizations. In case these slaves are still congested, the effect is that they  will immediately re-organize. A MASTER with slaves can add and remove slaves dynamically. When no slaves remains, the master with slaves becomes master of a single organization again. 

Whenever, the role of a camera changes, the organization controller informs the camera agent to update its status via signals slave, master, and masterWithSlaves. 

If the organization controller receives the stopCam signal, it will go to a FAILURE state. The controller will not respond until it is recovered via the startCam signal.

*/

signature:
	domain OrganizationController subsetof Agent
	derived stateOC: OrganizationController -> CameraState     
	static cameraOC: OrganizationController -> Camera //ID of the camera  
	controlled congested: OrganizationController -> Boolean //flag     
	monitored congestion: Camera -> Boolean //for debugging

definitions:
	function stateOC($c in OrganizationController) = stateC(cameraOC($c))

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
			//masterGone(cameraOC(self)) := false  //Gives rise to exceptions
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
					r_clearSlaves[]
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
					r_clearSlaves[]  //o r_clean[]?
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
			if ( stateC(self) = MASTERWITHSLAVES and slavesNotAlive(cameraOC(self)) ) then
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
