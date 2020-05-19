//managing subsystem for flexibility
module OrganizationMiddleware_old
import ../../STDL/StandardLibrary
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
enum domain OrganizationState = {MASTER2 | SLAVE2 | MASTERWITHSLAVES2 | PINGMASTER | FAILED2}
controlled state:  OrganizationController -> OrganizationState     
static camera: OrganizationController -> Camera //ID of the camera  
controlled congested: OrganizationController -> Boolean //flag     
monitored congestion: Camera -> Boolean //for debugging




definitions:
 //Macro rules for atomic adaptation actions in a master/slave organization
 
//@E 
macro rule r_clearSlaves  = slaves(camera(self)) := []
 //@E
macro rule r_addSlave ($s in Camera) = slaves(camera(self)) := append(slaves(camera(self)),$s)
 //@E 
macro rule r_removeSlave ($s in Camera) = slaves(camera(self)):= excluding(slaves(camera(self)),$s)
 //@E
macro rule r_setMaster($newMaster in Camera) = getMaster(camera(self)) := $newMaster


//Macro rules for modularization

 //@P
macro rule r_clear = 
par 
     r_clearSlaves[]
     congested(self) := false //?
     s_offer(camera(self)) := false
     state(camera(self)) := MASTER
     isAlive(camera(self)) := false
     master(camera(self)) := false
     masterWithSlaves(camera(self)) := false
     slave(camera(self)) := false
     cong(camera(self)) := false
     no_cong(camera(self)) := false
     change_master(camera(self)) := false
     masterGone(camera(self)) := false
     forall $c in Camera do 
      par  
       imAlive($c,camera(self)):= false
       imAlive(camera(self),$c):= false
       newSlave($c,camera(self)):= false
       newSlave(camera(self),$c):= false
       slaveGone($c,camera(self)):= false
       slaveGone(camera(self),$c):= false
      endpar
endpar


 //@P
 //Remove all slaves (if any) turning SLAVE2
macro rule r_removeSlavesTurningSlave = 
 par
    if (not isEmpty(slaves(camera(self)))) 
    then 
     seq 
      forall $s in Camera with contains(slaves(camera(self)),$s) do 
       //forall $s in asSet(slaves(camera(self))) do 
          change_master($s):= true            
      r_clearSlaves[]   
     endseq endif 
    slave(camera(self)):= true   
endpar

 //@P
//Remove all slaves (if any) turning MASTER2
macro rule r_removeSlavesTurningMaster = 
par
  if (not isEmpty(slaves(camera(self)))) 
  then seq 
         forall $s in Camera with contains(slaves(camera(self)),$s) do 
         //forall $s in asSet(slaves(camera(self))) do 
           masterGone($s):= true  
         r_clearSlaves[]  //o r_clear[]?
       endseq endif
  master(camera(self)):= true 
endpar    

 //@P
//Remove all slaves gone 
macro rule r_removeSlaveGone = 
 forall $s in Camera with ( contains(slaves(camera(self)),$s) and slaveGone(camera(self),$s) )  
 //forall $s in asSet(slaves(camera(self))) with slaveGo(camera(self),$s)  
 do  
        par
          r_removeSlave[$s]
          slaveGone(camera(self),$s):= false
        endpar
   
 //@P
//Add all new slaves on one shot since there could be 
//more than one SLAVE2 setting function s_offer to the 
//same value true (so without generating inconsistent update).
macro rule r_addNewSlave = 
  forall $s in Camera with newSlave(camera(self),$s) do 
      par
        r_addSlave[$s]
        newSlave(camera(self),$s):= false
      endpar
 
 
 //@P    
macro rule r_turnSlave ($master in Camera) = 
  par 
   newSlave($master,camera(self)):= true
   r_setMaster[$master]
   r_removeSlavesTurningSlave[]
   state(self) := SLAVE2
   s_offer(camera(self)) := false
  endpar

 //@P
macro rule r_turnMaster = 
   par 
     r_setMaster[camera(self)] 
     master(camera(self)):= true 
     state(self) := MASTER2 
     if masterGone(camera(self)) then masterGone(camera(self)):= false endif
   endpar

 //@P
macro rule r_turnMasterWithSlaves = 
par 
     r_addNewSlave[] 
     masterWithSlaves(camera(self)) := true
     state(self) := MASTERWITHSLAVES2
endpar

 

//@A
macro rule r_analyzeCongestion = 
 seq //Seq to avoid inconsistent updates 
     if m_offer(camera(self)) 
     then 
          r_turnMasterWithSlaves[] endif
     if (s_offer(camera(self)) and congested(self)) 
     then 
          r_turnSlave[prev(camera(self))] endif
 endseq        

//@A
macro rule r_receiveOrgSignals = 
         if change_master(camera(self)) then //Master changed!
            //@P
            par 
             r_setMaster[prev(getMaster(camera(self)))]
             newSlave(prev(getMaster(camera(self))),camera(self)) := true
             change_master(camera(self)) := false
            endpar  endif  

//@A
macro rule r_analyzeOrganization =          
if s_offer(camera(self)) 
then  
      r_turnSlave[prev(camera(self))] 
else if m_offer(camera(self)) 
     then 
          r_addNewSlave[] 
     else if isEmpty(slaves(camera(self))) //Simply turn master
          then 
               r_turnMaster[] endif endif endif 


//@M_s self_aware (no A functions)
macro rule r_selfFailureAdapt =
par
 if stopCam(camera(self)) 
 then //@P 
      state(self) := FAILED2  endif
 if (state(self) = FAILED2 and startCam(camera(self)) ) 
 then //@P  
      par 
        r_setMaster[camera(self)]
        state(self) := MASTER2  
        r_clear[]
      endpar  endif	
endpar

//@M_c context_aware (no A functions)
macro rule r_failureAdapt =
par 
 if ( state(self) = MASTERWITHSLAVES2 and slavesNotAlive(camera(self)) )
 then 
      r_removeSlaveGone[] endif
 if ( state(self) = SLAVE2 and masterGone(camera(self)) ) 
 then 
      r_turnMaster[] endif
endpar

//@M_c context_aware
macro rule r_congestionAdapt =
 par
  //MASTER2 (initial state)
 if state(self) = MASTER2
 then 
    par
     //congestion is for debugging purposes
     if ( congestion(camera(self)) and not congested(self)) //Congestion detected!
     //if ( cong(camera(self)) and not congested(self) )//Congestion detected! 
     then //@P
          par
           congested(self) := true 
           cong(camera(self)) := false 
           if isDef(next(camera(self))) then s_offer(next(camera(self))) := true endif
          endpar endif
      r_analyzeCongestion[]
    endpar
    endif 	
  //SLAVE2 
 if state(self) = SLAVE2
 then  
     if not(congestion(self)) //for debugging
     //if no_cong(camera(self)) //No longer congested! 
     then //@P
          par      
           congested(self) := false  
           slaveGone(getMaster(camera(self)),camera(self)) := true
           r_turnMaster[]
          endpar 
     else
       r_receiveOrgSignals[] endif endif       

//MASTERWITHSLAVE
 if state(self) = MASTERWITHSLAVES2 
 then if not(congestion(self)) //for debugging
      if no_cong(camera(self)) //No longer congested! 
      then 
           par r_removeSlavesTurningMaster[]  no_cong(camera(self)):= false endpar  
      else 
           r_analyzeOrganization[] endif endif        
endpar



//MAPE control loop
macro rule r_organizationControl =
 seq
   r_selfFailureAdapt[] //Adaptation due to internal failure
   r_failureAdapt[] //ROBOUSTNESS: Adaptation due to external failure (silent nodes)  
   r_congestionAdapt[] //FLEXIBILITY: Adaptation due to congestion
 endseq 
