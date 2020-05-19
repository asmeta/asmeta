//Knowledge
module Knowledge
import ../../STDL/StandardLibrary
import CameraAgentSubsystem //Import of the managed subsystem module
export * 

signature:
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
controlled slaves: Camera -> Seq(Camera)

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

definitions:



function isMaster($c in Camera) = ( state($c) = MASTER or state($c) = MASTERWITHSLAVES ) 
function isMasterWithSlaves($c in Camera) =  state($c) = MASTERWITHSLAVES 
function isSlave($c in Camera) =  state($c) = SLAVE 
function hasNext($c in Camera) = isDef(next($c))

function next($c in Camera) = 
     if isUndef(nextNeighbor($c)) then undef
     else if state(nextNeighbor($c)) = FAILED then next(nextNeighbor($c))
     else nextNeighbor($c) endif endif

function prev($c in Camera) = 
     if isUndef(prevNeighbor($c)) then undef
     else if state(prevNeighbor($c)) = FAILED then prev(prevNeighbor($c))
     else prevNeighbor($c) endif endif

   
function  slavesNotAlive ($c in Camera) =    
   (exist $s in Camera with ( contains(slaves($c),$s) and  slaveGone($c,$s) ))
   
function  allSlavesAlive ($c in Camera) =    
  ( forall $s in Camera with ( contains(slaves($c),$s) and  imAlive($c,$s) ))   
  
function  m_offer ($c in Camera) =    
   (exist $s in Camera with newSlave($c,$s) )
  