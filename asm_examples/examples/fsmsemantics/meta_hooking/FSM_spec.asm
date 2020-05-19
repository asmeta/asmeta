asm FSM_spec

import ../../../STDL/StandardLibrary

signature:

//Signature derived automatically from the Fsm metamodel: 
	dynamic abstract domain Fsm
	dynamic abstract domain State
	dynamic abstract domain Transition
	dynamic abstract domain Event

      //Functions on Fsm
      controlled ownedState: Fsm -> Powerset(State)
      controlled initialState: Fsm -> State
      controlled finalState: Fsm -> Powerset(State)

      //Functions on State
      controlled name: State -> String
	  controlled owningFsm: State -> Fsm 
      controlled incomingTransition: State -> Powerset(Transition)
      controlled outgoingTransition: State -> Powerset(Transition)
      
      //Functions on Transition
      controlled source: Transition -> State 
      controlled target: Transition -> State
      controlled inputEvent: Transition -> Event
      controlled outputEvent: Transition -> Event

      //Functions on Event
      controlled name: Event -> String
     
//Added signature:        

      controlled currentState: Fsm -> State
	monitored input: Fsm -> Event
      out output: Fsm -> Event       //(Mealy Fsm)
	
//** For a specific Fsm terminal model
static myFsm0 : Fsm
static myFsm1 : Fsm
static myFsm : Fsm //the composition of the two Fsms: myFsm0, myFsm1.

static e0:Event
static e1:Event
static epsylon:Event

static s1:State
static s2:State
static s3:State
static s4:State
static s0:State

static t1:Transition
static t2:Transition
static t3:Transition
static t4:Transition
static t5:Transition
static t6:Transition
static t7:Transition
static t8:Transition
static t01:Transition
static t02:Transition

definitions:
	
	rule r_fire ($t in Transition) = 
        let ($m = owningFsm(source($t))) in 
         par
	     output($m) := outputEvent($t)
	     currentState($m):= target($t)
	   endpar
        endlet

			
	rule r_run ($m in Fsm)=
		choose $t in outgoingTransition(currentState($m)) with inputEvent($t)=input($m)
              do r_fire[$t]
            
		
//** For a specific Fsm terminal model 

	main rule r_Main = r_run[myFsm]
		
	
		
//** For a specific Fsm terminal model 
default init s0:
	function currentState($m in Fsm) = initialState($m) 

      //Functions on Fsm 
      function ownedState($m in Fsm) = at({myFsm1->{s1,s2},myFsm0->{s3,s4}},$m)
      function initialState($m in Fsm) = at({myFsm1 -> s1,myFsm0->s3,myFsm->s0},$m)
      function finalState($m in Fsm) = at({myFsm1 -> {s1}, myFsm0->{s3}, myFsm->{s1,s3}},$m)

      //Functions on State
      function name($s in State)= at({s1->"s1",s2->"s2",s3->"s3",s4->"s4",s0->"s0"},$s)
	  function owningFsm($s in State)= at({s1->myFsm1,s2->myFsm1,s3->myFsm0,s4->myFsm0,s0->myFsm},$s)
      function incomingTransition($s in State)= at({s1->{t1,t4,t01},s2->{t2,t3},s3->{t5,t8,t02},s4->{t6,t7}},$s)
      function outgoingTransition($s in State)= at({s1->{t1,t2},s2->{t3,t4},s3->{t5,t6},s4->{t7,t8},s0->{t01,t02}},$s)
      
      //Functions on Transition
      function source($t in Transition) = at({t1->s1,t2->s1,t3->s2,t4->s2,t5->s3,t6->s3,t7->s4,t8->s4,t01->s0,t02->s0},$t)
      function target($t in Transition) = at({t1->s1,t2->s2,t3->s2,t4->s1,t5->s3,t6->s4,t7->s4,t8->s3,t01->s1,t02->s3},$t)
      function inputEvent($t in Transition) = at({t1->e1,t2->e0,t3->e1,t4->e0,t5->e0,t6->e1,t7->e0,t8->e1,t01->epsylon,t02->epsylon},$t)
      function outputEvent($t in Transition) = at({t1->e1,t2->e0,t3->e1,t4->e0,t5->e0,t6->e1,t7->e0,t8->e1,t01->epsylon,t02->epsylon},$t)

      //Functions on Event
      function name($e in Event)= at({e1->"1",e0->"0",epsylon->"epsylon"},$e)







