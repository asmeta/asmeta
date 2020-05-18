asm ASM_even //Semantic hooking: gamma for the FSM metamodel
import ../../../STDL/StandardLibrary
signature:
  //Signature derived automatically from the FSM metamodel:
      abstract domain NamedElement
      dynamic domain Fsm subsetof NamedElement
      dynamic domain State subsetof NamedElement
      dynamic domain Transition subsetof NamedElement
      dynamic domain Event subsetof NamedElement

      //Functions on NamedElement
      controlled name : NamedElement->String

      //Functions on Fsm
      controlled inputAlphabet: Fsm -> String
      controlled outputAlphabet: Fsm -> String
      controlled states: Fsm -> Powerset(State)
      controlled transitions: Fsm -> Powerset(Transition)

      //Functions on State
      controlled isStart: State -> Boolean

      //Functions on Transition
      controlled input: Transition -> String
      controlled output: Transition -> String
      controlled from: Transition -> State //source state
      controlled to: Transition -> State //target state

  //Added signature:
      controlled currentState: Fsm -> State
      monitored currentInput: Fsm -> String  //the input to feed the Fsm
      out currentOutput: Fsm -> String

  //For a specific Fsm terminal model:
      static evenFsm : Fsm
      static even:State
      static odd:State
      static t1:Transition //from even to even
      static t2:Transition //from even to odd
      static t3:Transition //from odd to even
      static t4:Transition //from odd to odd

definitions:

	rule r_fire ($m in Fsm, $t in Transition) =
         par
	     currentOutput($m) := output($t)
	     currentState($m):= to($t)
         endpar

	rule r_run ($m in Fsm)=
		choose $t in transitions($m)
                  with input($t)=currentInput($m) and from($t) = currentState($m) 
              do r_fire[$m,$t]


	main rule r_Main = forall $m in Fsm do
                                  r_run[$m]

  //For a specific Fsm terminal model
default init s0:

      //Functions on All
      function name($e in NamedElement) = at({evenFsm->"evenFsm",even->"even",odd->"odd",t1->"t1",t2->"t2",t3->"t3",t4->"t4"}, $e)

      //Functions on Fsm
      function inputAlphabet($m in Fsm) = at({evenFsm -> "01"},$m)
      function outputAlphabet($m in Fsm) = at({evenFsm -> "oe"},$m)
      function states($m in Fsm) = at({evenFsm->{even,odd}},$m)
      function transitions($s in Fsm)= at({evenFsm->{t1,t2,t3,t4}},$s)

      //Functions on State
      function isStart($s in State) = at({even->true,odd->false},$s)

      //Functions on Transition
      function input($t in Transition) = at({t2->"1",t1->"0",t3->"0",t4->"1"},$t)
      function output($t in Transition) = at({t2->"e",t1->"o",t3->"e",t4->"o"},$t)
      function from($t in Transition) = at({t2->even,t1->even,t3->odd,t4->odd},$t)
      function to($t in Transition) = at({t2->even,t1->odd,t3->even,t4->odd},$t)
      function currentState($m in Fsm) = chooseone({$s in states($m) | isStart($s) : $s}) //even state
