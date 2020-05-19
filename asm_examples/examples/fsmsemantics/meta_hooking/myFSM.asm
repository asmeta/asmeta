asm myFSM
import ../../../STDL/StandardLibrary
signature:

     //Signature derived automatically from the Fsm metamodel (Mealy Fsm) 
     abstract domain NamedElement
     dynamic domain Fsm subsetof NamedElement
     dynamic domain State subsetof NamedElement
     dynamic domain Transition subsetof NamedElement
     dynamic domain Event subsetof NamedElement
    
     //Functions on NamedElement
     controlled name:NamedElement->String
     
     //Functions on Fsm
      controlled ownedState: Fsm -> Powerset(State)
      controlled initialState: Fsm -> State
      
      //Functions on State
      controlled owningFsm: State -> Fsm 
      controlled incomingTransition: State -> Powerset(Transition)
      controlled outgoingTransition: State -> Powerset(Transition)
      
      //Functions on Transition
      controlled source: Transition -> State 
      controlled target: Transition -> State
      controlled inputEvent: Transition -> Event
      controlled outputEvent: Transition -> Event
     
      //Added signature to back-annotate in the FSM metamodel:        
      controlled currentState: Fsm -> State
      monitored input: Fsm -> Event
      controlled output: Fsm -> Event      
	
     //** For a specific Fsm terminal model
      static myFsm : Fsm
      static e0:Event
      static e1:Event
      static s1:State
      static s2:State
      static t1:Transition
      static t2:Transition
      static t3:Transition
      static t4:Transition
      


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
             
 main rule r_Main = r_run[myFsm]
			
//** For a specific Fsm terminal model 
default init s0:
    function currentState($m in Fsm) = initialState($m) 

     //Function on NamedElement      
      function name($e in NamedElement)=
 	at({myFsm->"myFsm",s1->"s1",s2->"s2",t1->"t1",t2->"t2",t3->"t3",t4->"t4",e1->"1",e0->"0"}, $e) 

      //Functions on Fsm     
      function ownedState($m in Fsm) = at({myFsm->{s1,s2}}, $m)
      function initialState($m in Fsm) = at({myFsm -> s1}, $m)

      //Functions on State
      function owningFsm($s in State)= at({s1->myFsm,s2->myFsm}, $s)
      function incomingTransition($s in State)= at({s1->{t1,t4},s2->{t2,t3}}, $s)
      function outgoingTransition($s in State)= at({s1->{t1,t2},s2->{t3,t4}}, $s)
      
      //Functions on Transition
      function source($t in Transition) = at({t1->s1,t2->s1,t3->s2,t4->s2}, $t)
      function target($t in Transition) = at({t1->s1,t2->s2,t3->s2,t4->s1}, $t)
      function inputEvent($t in Transition) = at({t1->e1,t2->e0,t3->e0,t4->e1}, $t)
      function outputEvent($t in Transition) = at({t1->e0,t2->e1,t3->e0,t4->e1}, $t)
   
      



