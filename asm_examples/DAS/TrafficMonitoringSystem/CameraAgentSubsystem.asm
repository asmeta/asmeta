//managed subsystem
module CameraAgentSubsystem
import ../../STDL/StandardLibrary
export *

/* A camera agent subsystem is modeled as two ASM agents: Camera and Traffic Monitor. 

Each Camera has four basic states. In normal operation, the camera can be master with no slaves, master of an organization with slaves, or it can be slave. Additionally, the camera can be in the failed state, representing the status of the camera after a silent node failure. There is an instance with a unique id for each camera. Cameras in the master status are responsible for communicating the traffic conditions to clients, but this functionality is not modeled here. 

Traffic Monitor is a sensor of the managed subsystem. It keeps track of the actual traffic conditions based on the signals it receives from the cars and determines traffic congestion. It interacts with the local organizational manager to handle organization management. For each camera, one traffic monitoring agent instance is running all the time. Whenever a car enters into the viewing range of a camera, the traffic monitor detects the car via the monitored function camEnter. Similarly when a car goes out of the range
of a camera, the traffic monitor detects this through the camLeave monitored function. The traffic monitors determines a traffic jam by comparing the total number of cars in its viewing range with the capacity. Based on this, the monitor may interact with the organization controller to adapt the organizations.
*/



signature:
domain Camera subsetof Agent
enum domain CameraState = {MASTER | SLAVE |   MASTERWITHSLAVES | FAILED}
domain TrafficMonitor subsetof Agent //sensor
enum domain TrafficMonitorState = {CONGESTION | NOCONGESTION}

static id : Camera -> Integer
controlled state: Camera -> CameraState  
//Functions shared with OrganizationController
/* controlled master: Camera -> Boolean   
controlled slave: Camera -> Boolean
controlled masterWithSlaves: Camera -> Boolean */

controlled state2: TrafficMonitor -> TrafficMonitorState 
controlled cars: TrafficMonitor -> Integer 
static capacity: Integer
//ID of the monitored camera     
static camera: TrafficMonitor -> Camera 
monitored camEnter: Camera -> Boolean
monitored camLeave: Camera -> Boolean

//Functions shared with OrganizationController
controlled cong: Camera -> Boolean
controlled no_cong: Camera -> Boolean



definitions:


/* 
We prefer for control state ASMs the parallel synchronous understanding of ASMs as firing in each step every rule (by the par rule). We control possible conflicts, e.g. by taking care that the rule guards of rules fireable in a control state are disjoint. In case of possible conflicts we sequencialize the transitions giving priority to certain events (e.g., those coming from the environment) that cannot be delayed.

Here for the camera behavior, we give priority to the event stopCam from the environment (a monitored function). We assume the other guards are disjoint since related to actions executed by the same agent (the organization controller) in two separate modes. 
*/

macro rule r_cameraBehavior =
/*seq  //Gives rise to inconsistent updates do to par rules and separate msg master, slave, masterWithSlaves
 if stopCam(self) then state(self):= FAILED endif
 par
  //MASTER (initial state)
  if (state(self) = MASTER) 
  then  par 
          if masterWithSlaves(self) 
          then par state(self):= MASTERWITHSLAVES masterWithSlaves(self):= false endpar endif	       
          if slave(self) then par state(self):= SLAVE slave(self):= false endpar endif 
         endpar	endif    	
  //SLAVE
  if ( state(self) = SLAVE and master(self) )
  then 
   par state(self):= MASTER master(self):= false endpar  endif	
  //MASTERWITHSLAVES
  if state(self) = MASTERWITHSLAVES 
  then  
     par 
      if master(self) then 
         par state(self):= MASTER master(self):= false endpar endif	       
      if slave(self) then 
         par state(self):= SLAVE slave(self):= false endpar endif 
     endpar	endif           
  //FAILED
  if (state(self) = FAILED and startCam(self)) 
  then state(self):= MASTER  endif	 
 endpar
endseq
*/
//if ( state(self) = FAILED and startCam(self) ) then  state(self):= MASTER  
//else if ( not(state(self) = FAILED) and stopCam(self) ) then  state(self):= FAILED endif endif
skip

macro rule r_observeCars = 
par 
  if (camEnter(camera(self)) and not (camLeave(camera(self)) ))
  then  cars(self):= cars(self) + 1  endif
  if (not (camEnter(camera(self))) and camLeave(camera(self)) )
  then  cars(self):= cars(self) - 1  endif
endpar

//We give priority to the guards related to the CAPACITY
//because it drives the transitions from state CONGESTION
//to NOCONGESTION and viceversa. The other guards are assumed //disjoint for the same reasons above for the camera behavior.
macro rule r_trafficMonitor =
par
  //NOCONGESTION (initial state)
 if state2(self) = NOCONGESTION 
 then 
    if cars(self) >= capacity then 
        par 
            cong(camera(self)):= true
            state2(self) := CONGESTION
        endpar  
    //else r_observeCars[] 
    endif 
    endif
       	
 //CONGESTION
  if state2(self) = CONGESTION 
  then 
    if cars(self) < capacity then 
        par 
            no_cong(camera(self)):= true
            state2(self) := NOCONGESTION
        endpar    
    //else r_observeCars[] 
    endif 
    endif
  r_observeCars[]       
endpar


