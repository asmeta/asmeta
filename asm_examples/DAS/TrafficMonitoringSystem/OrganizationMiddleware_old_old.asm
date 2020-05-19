//managing subsystem for flexibility
module OrganizationMiddleware
import ../../STDL/StandardLibrary
//import of the Knowledge module
import Knowledge
export *

/* 
An Organization Controller runs on each camera and it is responsible for managing organizations, based on the information it gets from the traffic monitor agent (the  signal cong) of the camera. A camera starts as MASTER2 of a single member organization. When a congestion is detected (in the role MASTER2) the organization controller sends a request_org signal to the neighboring camera in the direction of the traffic flow. 

Depending on the traffic conditions of the neighboring camera and its current role, the organizations may be restructured. If traffic is not jammed (congested = false) in the viewing range of the neighbor, organizations are not changed. 

If traffic is jammed the organizations are restructured as follows. If also the neighbor is MASTER2 of a single member organization or MASTER2 with slaves, the camera with the lowest id becomes MASTER2 with slaves of the joined organization, while the other becomes SLAVE2. 
   
When a SLAVE2 or a MASTER2 with slaves detects the traffic in its viewing range is no longer jammed based on the information it gets from the traffic monitor (the signal no_cong), it leaves the organization it belongs to and becomes MASTER2 of a single organization again. In case of a MASTER2 with slaves, all its depending slaves (whose traffic may still be jammed) will turn MASTER2 too and will also send a request_org signal to the neighboring camera in order to immediately re-organize on their own. A MASTER2 with slaves can add and remove slaves dynamically. When no SLAVE2 remains, the MASTER2 with slaves becomes MASTER2 of a single organization again. 

Whenever, the role of a camera changes, the organization controller informs the camera process to update its status via signals SLAVE2[id], MASTER2[id], or MASTERWITHSLAVES2[id] respectively. 

If the organization controller receives the stopCam signal, it will go to Failure state, which represents a silent node failure. The controller will not respond until it is recovered via the startCam signal.
*/



signature:
domain OrganizationController subsetof Agent
enum domain OrganizationState = {MASTER2 | SLAVE2 | MASTERWITHSLAVES2 | PINGMASTER | FAILED2}
controlled state:  OrganizationController -> OrganizationState     
static camera: OrganizationController -> Camera //ID of the camera  
controlled congested: OrganizationController -> Boolean //flag     

monitored congestion: Camera -> Boolean //for debugging

//shared functions with other organization controllers (declared controlled for simulation purposes)
controlled request_org : Camera -> Boolean
controlled requester: Camera -> Camera
controlled m_offer : Camera -> Boolean
controlled s_offer : Camera -> Boolean
controlled change_master : Camera -> Boolean
controlled newMaster : Camera -> Camera
controlled newSlave : Prod(Camera,Camera)-> Boolean

definitions:
 //Macro rules for adaptation actions
macro rule r_clearSlaves  = slaves(camera(self)) := []
 
macro rule r_addSlave ($s in Camera) = slaves(camera(self)) := append(slaves(camera(self)),$s)
 
macro rule r_removeSlave ($s in Camera) = 
       slaves(camera(self)):= excluding(slaves(camera(self)),$s)



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


//Remove all slaves (if any) turning MASTER2
macro rule r_removeSlavesTurningMaster = 
par
  if (not isEmpty(slaves(camera(self)))) 
  then seq 
         forall $s in Camera with contains(slaves(camera(self)),$s) do 
         //forall $s in asSet(slaves(camera(self))) do 
           masterGone($s):= true  
         r_clearSlaves[]  
       endseq endif
  master(camera(self)):= true 
endpar    

//Remove all slaves gone
macro rule r_removeSlaveGone = 
 forall $s in Camera with (contains(slaves(camera(self)),$s) and slaveGone(camera(self),$s))  
 //forall $s in asSet(slaves(camera(self))) with slaveGo(camera(self),$s)  
 do  
       // par
          r_removeSlave[$s]
          //slaveGone(camera(self),$s):= false
        //endpar
   

//Add all new slaves on one shot since there could be 
//more than one SLAVE2 setting function s_offer to the 
//same value true (so without generating inconsistent update).
macro rule r_addNewSlave = 
  forall $s in Camera with newSlave(camera(self),$s) do 
      par
        r_addSlave[$s]
        newSlave(camera(self),$s):= false
      endpar

macro rule r_setMaster($newMaster in Camera) = 
    getMaster(camera(self)) := $newMaster
    
macro rule r_turnSlave ($master in Camera) = 
  par 
   r_setMaster[$master]
   r_removeSlavesTurningSlave[]
   state(self) := SLAVE2
  endpar

macro rule r_turnMaster = 
   par 
     r_setMaster[camera(self)] 
     master(camera(self)):= true 
     state(self) := MASTER2 
   endpar

macro rule r_turnMasterWithSlaves = 
par 
          r_addNewSlave[] 
          masterWithSlaves(camera(self)) := true
          state(self) := MASTERWITHSLAVES2
endpar

 

//Macro rules for modularization

//We assume id is a monotonically increasing function on the //traffic direction and the camera with the lowest id becomes //MASTER2.   
macro rule r_orgOffer = 
   if id(requester(camera(self))) > id(camera(self)) 
   then 
     par
       s_offer(requester(camera(self))) := true
       newMaster(requester(camera(self))) := camera(self)
       r_addSlave[requester(camera(self))]
       //If MASTER2 becomes MASTER2 with slaves 
       if state(self) = MASTER2 then 
         r_turnMasterWithSlaves[] 
         //Note: r_addNewSlave[] does not have effect, the  
         //requester is added above by the rule r_addSlave[]
       endif 
     endpar
   else //turn SLAVE2
     par
       m_offer(requester(camera(self))) := true
       newSlave(requester(camera(self)),camera(self)) :=  true
       newMaster(camera(self)) := requester(camera(self))
       r_turnSlave[requester(camera(self))]  
     endpar
   endif


//@M
macro rule r_congestionDetect = 
   par
     //for debugging purposes
     if congestion(camera(self)) //Congestion detected!
     //if cong(camera(self)) //Congestion detected! 
     then par
           congested(self) := true  
           request_org(next(camera(self))) := true
           requester(next(camera(self))) := camera(self) //Necessaria?
          endpar endif
     if (congested(self) and s_offer(camera(self))) 
     then r_turnSlave[newMaster(camera(self))] endif
     if m_offer(camera(self)) then r_turnMasterWithSlaves[] endif
     if (congested(self) and request_org(camera(self)) ) 
     then r_orgOffer[] endif
   endpar        


macro rule r_receiveOrgSignals = 
     par
         if change_master(camera(self)) then //MASTER2 changed!
            par 
             r_setMaster[newMaster(camera(self))]
             newSlave(newMaster(camera(self)),camera(self)) := true
             m_offer(newMaster(camera(self))) := true //Necessaria? Forse, sovraspecifica tra newSlave e m_offer
            endpar  endif  
         if masterGone(camera(self)) then r_turnMaster[] endif
      endpar

macro rule r_organizationControl =
 par
  //MASTER2 (initial state)
 if state(self) = MASTER2
 then 
    if stopCam(camera(self)) then //context_aware
          state(self) := FAILED2  
    else r_congestionDetect[]
    endif endif    	
  //SLAVE2
 if state(self) = SLAVE2
 then  
    if stopCam(camera(self)) then 
         state(self) := FAILED2 
    else 
     if no_cong(camera(self)) //No longer congested! 
     then par      
           congested(self) := false  
           slaveGone(getMaster(camera(self)),camera(self)) := true
           //slaveGone(getMaster(camera(self))) := true //Eliminabile?
           r_turnMaster[]
          endpar 
     else r_receiveOrgSignals[] endif endif endif          
//MASTERWITHSLAVE
 if state(self) = MASTERWITHSLAVES2 
 then  
    if stopCam(camera(self)) then 
         state(self):= FAILED2 
    else 
      if no_cong(camera(self)) //No longer congested! 
      then r_removeSlavesTurningMaster[]    
      else
        par //???? Le guardie sono disgiunte?
         if slavesNotAlive(camera(self)) then r_removeSlaveGone[] endif
         if m_offer(camera(self)) then r_addNewSlave[] endif 
         if isEmpty(slaves(camera(self))) then //Simply turn MASTER2
            master(camera(self)) := true endif           
         if request_org(camera(self)) then r_orgOffer[] endif
        endpar     
 endif endif endif           
//FAILED
 if ( state(self) = FAILED2 and startCam(camera(self)) ) 
 then par 
        r_clearSlaves[]
        r_setMaster[camera(self)]
        state(self) := MASTER2  
      endpar endif	
endpar


