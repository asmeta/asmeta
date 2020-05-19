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
import ../../STDL/StandardLibrary

//import of the Knowledge module
import Knowledge
export *
signature:
domain SelfHealingController subsetof Agent	
enum domain HealingState = {START | WAITFORRESPONSE | FAILED2}
controlled state:  SelfHealingController -> HealingState  
//ID of the camera     
static camera: SelfHealingController -> Camera

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
   forall $s in Camera with contains(slaves(camera(self)),$s) do 
       par
         isAlive($s) := true  //ping signal
         state(self) := WAITFORRESPONSE
       endpar 
 endif
 
 
 

//@A
macro rule r_receiveSignal = 
if allSlavesAlive(camera(self)) 
then  //@P
   state(self) := START 
else if elapsed_alive_time(self) then 
   //@P
   par 
     forall $s in Camera with (contains(slaves(camera(self)),$s) and not imAlive(camera(self),$s) ) do
       //Communication between self-healer and organization controller
             slaveGone(camera(self),$s) := true 
     state(self) := START 
   endpar    
endif endif       
 	      

//@A
macro rule r_tryGenerateEcho = 
if isAlive(camera(self)) 
then //@P 
  imAlive(getMaster(camera(self)),camera(self)) := true //echo signal
else 
 if elapsed_wait_time_plus_delta(self) 
 then //@P
      masterGone(camera(self)) := true    //Communication between self-healer and organization controller
endif endif


//@M_s self-aware
macro rule r_selfFailureDetect =
par
 if ((state(self) = START or state(self) = WAITFORRESPONSE) and  stopCam(camera(self))) 
 then //@P 
      state(self) := FAILED2  endif
 if (state(self) = FAILED2 and startCam(camera(self)) ) 
 then //@P
      state(self) := START  endif	
endpar          

//@M_c context-aware
macro rule r_failureDetect =
par
 //Depending on the role: 
  if isMasterWithSlaves(camera(self))
  then  if state(self) = START then r_sendSignal[] //send ping signals to all slaves of the organization
        else if state(self) = WAITFORRESPONSE then r_receiveSignal[]  endif  endif endif
  
  if  isSlave(camera(self)) 
  then if state(self) = START then r_tryGenerateEcho[] endif endif    
endpar


macro rule r_selfHeal =  
 //seq 
   // r_selfFailureDetect[] //MAPE control loop for adaptation due to internal failure
    r_failureDetect[] //Monitoring for adaptation due to external failure (ROBOUSTNESS)
 //endseq  

