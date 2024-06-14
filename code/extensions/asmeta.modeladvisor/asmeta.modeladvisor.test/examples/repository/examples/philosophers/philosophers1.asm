// EXAMPLE OF USE OF ASYNCR MULTI AGENT ASM 
// with multiple instances of the same agent  
// (all the gents have the same state, functions and rules)
// first version: choose one philo and run it
// if the philo is hungry and it can eat, it will start eating
// if the philo is not hungry, it will not start eating or it will stop eating

asm philosophers1

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

//declare universes and functions
	
signature:

		
// Agents

	domain Philosophers subsetof Agent  
	
	abstract domain Fork
	
//functions for Agent Philophers
	
	monitored hungry : Philosophers -> Boolean

	controlled eating : Philosophers -> Boolean
	
	static right_fork : Philosophers -> Fork

	static left_fork : Philosophers -> Fork
	

    controlled owner : Fork -> Philosophers //records the current exclusive user of a given resource


   	// initial philosofers (at least 5)
   	static phil_1: Philosophers
	static phil_2: Philosophers
	static phil_3: Philosophers
    static phil_4: Philosophers
	static phil_5: Philosophers

   	static fork_1: Fork
   	static fork_2: Fork
   	static fork_3: Fork
    static fork_4: Fork
   	static fork_5: Fork
	
definitions:

		
	function right_fork($a in Philosophers) =	
		if $a = phil_1 then fork_2
		else if $a = phil_2 then fork_3
		else if $a = phil_3 then fork_4
		else if $a = phil_4 then fork_5
		else if $a = phil_5 then fork_1
		
                endif endif endif endif endif	

	function left_fork($a in Philosophers) =	
		if $a = phil_1 then fork_1
		else if $a = phil_2 then fork_2
		else if $a = phil_3 then fork_3
                else if $a = phil_4 then fork_4
		else if $a = phil_5 then fork_5
		endif endif endif endif endif 	

//Rules 

	macro rule r_Eat =
		if (hungry(self)) then
			forall $l in Fork, $r in Fork with $l=left_fork(self) and $r=right_fork(self) do
				if ( isUndef(owner($l)) and 
				     isUndef(owner($r)) ) then
					par
						owner($l) := self
						owner($r) := self
						eating(self) := true
					endpar
			endif
		endif

	macro rule r_Think =
		if(not hungry(self)) then
		forall $l in Fork, $r in Fork with $l=left_fork(self) and $r=right_fork(self) do
			if eating(self) and (owner($l) = self) and (owner($r) = self) then
						par
                        owner($l) := undef
                        owner($r) := undef 
						eating(self) := false
						endpar
			endif
		endif

	

	macro rule r_Philo = 
             par	
                r_Eat[] 
				r_Think[]
	    	endpar                           
        
    CTLSPEC ef(eating(phil_1))
	CTLSPEC ef(eating(phil_2))
	CTLSPEC ef(eating(phil_3))
	CTLSPEC ef(eating(phil_4))
	CTLSPEC ef(eating(phil_5))
	
	CTLSPEC ag(not(eating(phil_1)) implies ef(eating(phil_1)))
	CTLSPEC ag(not(eating(phil_2)) implies ef(eating(phil_2)))
	CTLSPEC ag(not(eating(phil_3)) implies ef(eating(phil_3)))
	CTLSPEC ag(not(eating(phil_4)) implies ef(eating(phil_4)))
	CTLSPEC ag(not(eating(phil_5)) implies ef(eating(phil_5)))
	                                             
// choose one agent and run it
main rule r_choose_philo = 
	choose $p in Philosophers with true do program($p)

//define the initial states 

default init initial_state:
	
function eating ($p in Philosophers)= false

function owner ($f in Fork) = undef                      

//AGENTS initialization (secondo modo: funzionante!)
//permette anche un'inizializzazione parziale della funzione program, 
// quando invece di Agent c'� un concrete subset di //Agent):

agent Philosophers : r_Philo[]
		