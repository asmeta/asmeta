// given a number which is 0 or 1, update it to 1 if originally 0, 0 otherwise

asm FLIP_FLOP_0
import ../../STDL/StandardLibrary

signature: 
	domain State subsetof Natural

	dynamic controlled ctl_state : State   //we can also write "controlled ctl_state : State"
	dynamic monitored high : Boolean       //we can also write "monitored high : Boolean"
	dynamic monitored low : Boolean        //we can also write "monitored low : Boolean"

definitions:

	domain State = {0n,1n}

	macro rule r_Fsm =
		if ctl_state = 0n then  //if function is 0
			ctl_state := 1n //assign 1
		else 
			ctl_state := 0n //otherwise 0
		endif

	main rule r_flip_flop_1 =  
		seq //execute rules sequentially
			r_Fsm[]
			r_Fsm[]
		endseq

default init initial_state:

	function ctl_state = 0n
	function high = false
	function low = false