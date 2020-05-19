//@ parser error: function defined twice
//managing subsystem for robustness
/* A Self-Healing Controller runs on each camera. It detects failures of other cameras based on a ping-echo mechanism.
It sends periodically ping signals (isAlive(ping)) based on a WAIT TIME to the self-healing controllers
of the dependent cameras. If a camera does not respond in a certain time (ALIVE TIME) it 
triggers the organizational controller, either by removing a dependency in case a slave failed, or by
restructuring the organization in case the master of the organization failed.
It is also responsible to respond to the ping signals sent by other cameras to check whether
a particular camera is alive or not. */
module SelfHealingSubsystem_old
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

//For the PING-ECHO mechanism:
//Functions shared between various SealfHealingController agents 
controlled imAlive: Camera -> Boolean
controlled isAlive: Camera -> Boolean

//Next camera in the traffic direction to ping ?
//TO DO: definire la politica di chi pinga chi
controlled ping: SelfHealingController -> Camera //? necessaria
controlled caller: Camera -> Camera 
controlled failureDetected: SelfHealingController -> Boolean



definitions:



macro rule r_save($c in Camera, $v in Boolean)= skip

//self-aware
macro rule r_sendSignal =
 if ( elapsed_wait_time(self) and hasNext(camera(self)) ) then 
   seq //seq eliminabile!
     ping(self) := next(camera(self)) //next camera in the organization to ping
     caller(ping(self)) := camera(self) //the caller camera  
     isAlive(ping(self)) := true
     state(self) := WAITFORRESPONSE
   endseq  
  endif 

//self-aware
macro rule r_receiveSignal = 
if imAlive(camera(self)) 
then 
     par 
           r_save[ping(self),true] //Eliminabile? In knowledge?
           state(self) := START 
     endpar 	       
else if elapsed_alive_time(self) then 
     seq
       failureDetected(self) := true //A failure flag useful for verification/debugging purposes
       r_save[ping(self),false] //ping(self) is silent
       if isMaster(ping(self)) 
       then masterGone(camera(self)) := true   ////Comunicazione tra self-healer e organization controller
       else par 
             //slaveGone(camera(self)) := true   //Comunicazione tra self-healer e organization controller
             slaveGone(getMaster(camera(self))) := true
             //slaveGo(camera(self),ping(self)) := true
             slaveGo(getMaster(camera(self)),ping(self)) := true
            endpar endif 
       state(self) := START 
      endseq endif 
 endif


macro rule r_failureDetect =
par
  //START (initial state)
 if state(self) = START 
 then 
    if (stopCam(camera(self))) then //context_aware
          state(self) := FAILED2  
    else r_sendSignal[]
    endif endif    	
 //WAITFORRESPONSE
 if state(self) = WAITFORRESPONSE 
 then  
    if stopCam(camera(self)) then 
         state(self) := FAILED2 
    else r_receiveSignal[] 
    endif endif          
//FAILED
 if (state(self) = FAILED2 and startCam(camera(self))) 
 then state(self) := START  endif	
endpar


macro rule r_generate =
if (neq(state(self),FAILED2) and isAlive(camera(self))) then  //Echo response generation 
                        imAlive(caller(self)) := true
             endif 


macro rule r_selfHeal = par r_failureDetect[] r_generate[] endpar


