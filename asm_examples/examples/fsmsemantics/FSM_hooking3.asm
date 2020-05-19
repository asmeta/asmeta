// versione per FSM semantic hooking 
// ma in modo che sia un po' diversa dalla gamma del meta hooking

asm FSM_hooking3

import ../../STDL/StandardLibrary

signature:

//Gamma, defined by the user  

	  abstract domain Fsm
	  domain State subsetof String
	  abstract domain Transition
	  domain Event subsetof Integer

      //Functions on Fsm
      controlled initialState: Fsm -> State
      controlled currentState: Fsm -> State
      monitored input: 		   Fsm -> Event
      out output: 			   Fsm -> Event
      
            
      //Functions on Transition
      controlled source: Transition -> State 
      controlled target: Transition -> State
      controlled inputEvent: Transition -> Event
      controlled outputEvent: Transition -> Event
     
	
//** For a specific Fsm terminal model in the example

static myFsm : Fsm 

static t1:Transition
static t2:Transition
static t3:Transition
static t4:Transition

// Again Gamma

definitions:

    domain State = {"s1","s2"}
    domain Event = {0,1}

    rule r_doTransition($m in Fsm, $t in Transition) =
             par
	          output($m) := outputEvent($t)
	          currentState($m):= target($t)
	         endpar
    
	
	rule r_fsmReact($m in Fsm, $e in Event) = 
        let ($s = currentState($m)) in        
        choose $pt in Transition with source($pt)= $s and inputEvent($pt)= $e do r_doTransition[$m,$pt]          
        endlet
		
            
		
// run all the fsm 

	main rule r_Main = forall $fsm in Fsm do r_fsmReact[$fsm,input($fsm)]
		
		
//** For a specific Fsm terminal model 

default init s0:

      //Functions on Fsm 
      function initialState($m in Fsm) = at({myFsm -> "s1"},$m)
	  function currentState($m in Fsm) = at({myFsm -> "s1"},$m) 
      
      //Functions on Transition
      function source($t in Transition) = at({t1->"s1",t2->"s1",t3->"s2",t4->"s2"},$t)
      function target($t in Transition) = at({t1->"s1",t2->"s2",t3->"s2",t4->"s1"},$t)
      function inputEvent($t in Transition) = at({t1->1,t2->0,t3->0,t4->1},$t)
      function outputEvent($t in Transition) = at({t1->0,t2->1,t3->0,t4->1},$t)

