//managing subsystem for robustness
/* A Self-Healing Controller runs on each camera. It detects failures of other cameras based on a broadcast ping-echo mechanism.
Essentially, we adopt the following monitoring policy. The master of an organization with slaves sends periodically ping signals
 (isAlive) based on a WAIT TIME (the period) to the self-healing controllers
of its slaves cameras in a broadcast manner.  If all slaves cameras do not reply with an echo signal (imAlive) in a certain time (ALIVE TIME), 
the master communicates to its organization controller the slaves that are failed (slaveGone).
If an alive slave that does not receive the ping signal from its master within the time WAIT TIME+delta, it communicates to its  
organization controller the master is failed (masterGone). The organization controllers will take care of restructuring the whole organization
in case of these failure situations. A master camera of a single organization does not ping.
*/

module SelfHealingSubsystem
import ../../../../../STDL/StandardLibrary

//import of the Knowledge module
import Knowledge
export *

signature:
	domain SelfHealingController subsetof Agent
	enum domain HealingState = {START | WAITFORRESPONSE | FAILED2}
	controlled stateSHC:  SelfHealingController -> HealingState
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

	//@A
	macro rule r_sendSignal =
		if  elapsed_wait_time(self) then 
		//@P
			forall $s in Camera with contains(slaves(cameraSHC(self)), $s) do 
				par
					isAlive($s) := true  //ping signal
					stateSHC(self) := WAITFORRESPONSE
				endpar
		endif

	//@A
	macro rule r_receiveSignal =
		if allSlavesAlive(cameraSHC(self)) then  //@P
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
			if  isSlave(cameraSHC(self)) then
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
