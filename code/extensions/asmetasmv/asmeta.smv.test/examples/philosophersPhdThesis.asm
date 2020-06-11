// EXAMPLE OF USE OF ASYNCR MULTI AGENT ASM 
// with multiple instances of the same agent  
// (all the agents have the same state, functions and rules)
// first version: choose one philo and run it
// if the philo is hungry and it can eat, it will start eating
// if the philo is not hungry, it will not start eating or it will stop eating

asm philosophersPhdThesis

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Philosophers subsetof Agent  	
	abstract domain Fork
	
	monitored hungry : Philosophers -> Boolean
	controlled eating : Philosophers -> Boolean

	static right_fork : Philosophers -> Fork
	static left_fork : Philosophers -> Fork

    controlled owner : Fork -> Philosophers //records the current exclusive user of a given resource

   	// initial philosophers (at least 5)
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

	macro rule r_Eat =
		if hungry(self) then
			forall $l in Fork, $r in Fork with $l=left_fork(self) and $r=right_fork(self) do
				if isUndef(owner($l)) and 
				     isUndef(owner($r)) then
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

    CTLSPEC ef(eating(phil_1) and eating(phil_3))
    CTLSPEC not(ef(eating(phil_1) and eating(phil_3)))

	CTLSPEC (forall $p in Philosophers with ag(ef(eating($p))) )
	CTLSPEC (forall $p in Philosophers with ag(ef(not(eating($p)))))

	main rule r_choose_philo = 
		choose $p in Philosophers with true do
			program($p)

default init initial_state:
	function eating ($p in Philosophers)= false
	function owner ($f in Fork) = undef                      

	agent Philosophers:
		r_Philo[]