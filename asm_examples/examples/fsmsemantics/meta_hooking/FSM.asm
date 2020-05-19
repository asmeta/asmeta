asm FSM

import ../../../STDL/StandardLibrary

signature:

//Signature derived automatically from the Fsm metamodel: 
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
     
//Added signature:        

      controlled currentState: Fsm -> State
      monitored input: Fsm -> Event
      out output: Fsm -> Event       //(Mealy Fsm)
	

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
            
		
	main rule r_Main = forall $m in Fsm do
                                  r_run[$m]