// come FSM_hooking ma un po' semplificata
// senza outTransitions

asm FSM_hooking2

import ../../STDL/StandardLibrary

signature:

//Gamma, defined by the user  

	  dynamic abstract domain Fsm
	  dynamic abstract domain State
	  dynamic abstract domain Transition
	  dynamic abstract domain Event

      //Functions on Fsm
      controlled initialState: Fsm -> State
      controlled currentState: Fsm -> State
      monitored input: 		   Fsm -> Event
      out output: 			   Fsm -> Event       //(Mealy Fsm)
      
            
      //Functions on Transition
      controlled source: Transition -> State 
      controlled target: Transition -> State
      controlled inputEvent: Transition -> Event
      controlled outputEvent: Transition -> Event
     
	
//** For a specific Fsm terminal model in the example

static myFsm : Fsm 
static e0:Event
static e1:Event
static s1:State
static s2:State
static t1:Transition
static t2:Transition
static t3:Transition
static t4:Transition

// Again Gamma

definitions:

    rule r_doTransition($m in Fsm, $t in Transition) =
             par
	          output($m) := outputEvent($t)
	          currentState($m):= target($t)
	         endpar
    
	
	rule r_fsmReact($m in Fsm, $e in Event) = 
        let ($s = currentState($m)) in        
        choose $pt in Transition with source($pt)= $s and inputEvent($pt)= $e do r_doTransition[$m,$pt]          
        endlet
		
            
		
//** For a specific Fsm terminal model 

	main rule r_Main = r_fsmReact[myFsm,input(myFsm)]
		
		
//** For a specific Fsm terminal model 

default init s0:

      //Functions on Fsm 
      function initialState($m in Fsm) = at({myFsm -> s1},$m)
	  function currentState($m in Fsm) = at({myFsm -> s1},$m) 
      
      //Functions on Transition
      function source($t in Transition) = at({t1->s1,t2->s1,t3->s2,t4->s2},$t)
      function target($t in Transition) = at({t1->s1,t2->s2,t3->s2,t4->s1},$t)
      function inputEvent($t in Transition) = at({t1->e1,t2->e0,t3->e0,t4->e1},$t)
      function outputEvent($t in Transition) = at({t1->e0,t2->e1,t3->e0,t4->e1},$t)

