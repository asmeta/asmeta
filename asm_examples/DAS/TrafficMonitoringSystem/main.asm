asm main
//main ASM: Camera system of systems

import ../../STDL/StandardLibrary
//import of ASM modules for subcomponents
import SelfHealingSubsystem
import CameraAgentSubsystem
import OrganizationMiddleware

signature:
	
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

definitions:

function capacity = 3    

/* function camera(trafficMonitor1)= c1
function camera(trafficMonitor2)= c2
function camera(selfHealingController1)= c1
function camera(selfHealingController2)= c2
function camera(organizationController1)= c1
function camera(organizationController2)= c2 */

function camera($a in TrafficMonitor) =
	   switch($a)
			case trafficMonitor1: c1
			case trafficMonitor2: c2
			case trafficMonitor3: c3
			case trafficMonitor4: c4
		endswitch

function camera($a in SelfHealingController) =
	   switch($a)
			case selfHealingController1: c1
			case selfHealingController2: c2
			case selfHealingController3: c3
			case selfHealingController4: c4
	  endswitch		
			
function camera($a in OrganizationController) =
	   switch($a)		
			case organizationController1: c1
			case organizationController2: c2
			case organizationController3: c3
			case organizationController4: c4
     endswitch

function id($c in Camera) =  
        switch($c)
			case c1: 1
			case c2: 2
			case c3: 3
			case c4: 4
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

	macro rule r_skip = skip  //for debugging
			
main rule r_main =
	forall $a in Agent do program($a)

default init s0:

   function state($c in Camera) = MASTER
   function imAlive($c in Camera,$s in Camera) = false
   function isAlive($c in Camera) = false
   function slaves($c in Camera) = []
   //function master($c in Camera) = false   
   //function masterWithSlaves($c in Camera) = false  
   //function slave($c in Camera) = false   
   function cong($c in Camera) = false   
   function no_cong($c in Camera) = false   
   function s_offer($c in Camera) = false
   function change_master($c in Camera) = false
   function newSlave($c in Camera,$s in Camera) = false 
   function slaveGone($c in Camera,$s in Camera) = false
   function getMaster($c in Camera) = $c
   function masterGone($c in Camera) = false
   
   function state2($t in TrafficMonitor) = NOCONGESTION 
   function cars($t in TrafficMonitor) = 0 
   function state($s in SelfHealingController) = START
  
   
   //function state($o in OrganizationController) = MASTER2
   function congested($o in OrganizationController) = false


	
   agent Camera:
		r_cameraBehavior[]

   agent TrafficMonitor: r_skip[] //for debugging
		//r_trafficMonitor[] 

   agent SelfHealingController:
		r_selfHeal[]

   agent OrganizationController:
		r_organizationControl[]
 
     
    
    
