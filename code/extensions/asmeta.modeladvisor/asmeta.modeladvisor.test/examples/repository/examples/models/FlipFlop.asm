// with parameters - parameteric FSM

asm FlipFlop

import ../../STDL/StandardLibrary

signature: 
	domain State subsetof Natural

	dynamic controlled ctl_state : State
	dynamic monitored high : Boolean
	dynamic monitored low : Boolean

    static  highState : State
    static  lowState : State
    

definitions:



	domain State = {0n, 1n}

    function  highState = 1n 
    function  lowState = 0n

	macro rule r_Fsm($ctl_state in State, $aState in State , 
			$nextState in State, $cond in Boolean/*, $rule in Rule*/) =
		if $ctl_state = $aState and $cond then  
			//par
				//$rule
				$ctl_state := $nextState
			//endpar
		endif

    macro rule r_skip = skip

	invariant inv_neverBoth over high(), low(): not(high and low)


	main rule r_Main =  
		seq
			r_Fsm[ctl_state, lowState, highState, high/*, <<r_skip>>*/]
			r_Fsm[ctl_state, highState, lowState, low/*, <<r_skip>>*/]
		endseq

default init s0:

	function ctl_state = 0n
