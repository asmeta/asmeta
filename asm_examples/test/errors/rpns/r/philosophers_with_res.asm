// with resource (unique instead of left,right fork it does not work !!!

// simplified version of philosophers
// something wrong beacuase no inconsiste update is detected
// come libro di Egon, forse sbagliato il libro !!!!!
// o da aggiungere invarianti

asm philosophers_with_res

import ../../../../STDL/StandardLibrary

//declare universes and functions
	
signature:

		
// Agents

	domain Philosophers subsetof Agent  
	
	abstract domain Fork
	
//functions for Agent Philophers
	
	static right_fork : Philosophers -> Fork

	static left_fork : Philosophers -> Fork
	
	static resource: Philosophers -> Prod(Fork,Fork)

    controlled owner : Prod(Fork,Fork) -> Philosophers //records the current exclusive user of a given resource


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


	function resource($a in Philosophers) = (left_fork($a),right_fork($a))

//Rules 

	macro rule r_Eat =
		if ( isUndef(owner(resource(self)))) then
							owner(left_fork(self),right_fork(self)) := self
		endif

	macro rule r_Think =
		if owner(resource(self)) = self then
					owner(left_fork(self),right_fork(self)) := undef
		endif
        
                                                  
// choose one agent and run it
main rule r_choose_philo = 
	choose $p in Philosophers with true do program($p)

//define the initial states 

default init initial_state:
	
function owner ($f1 in Fork, $f2 in Fork) = undef                      

//AGENTS initialization (secondo modo: funzionante!)
//permette anche un'inizializzazione parziale della funzione program, 
// quando invece di Agent c'e' un concrete subset di //Agent):

agent Philosophers : 
             par	
                r_Eat[] 
				r_Think[]
	    	endpar                           
		
