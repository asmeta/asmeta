asm philosophers1

import ./STDL/StandardLibrary

//declare universes and functions
signature:
	// Agents
	domain Philosophers subsetof Agent

	abstract domain Fork

	//functions for Agent Philophers
	monitored hungry: Philosophers -> Boolean
	controlled eating: Philosophers -> Boolean
	static right_fork: Philosophers -> Fork
	static left_fork: Philosophers -> Fork
    controlled owner: Fork -> Philosophers //records the current exclusive user of a given resource

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
		switch($a)
			case phil_1: fork_2
			case phil_2: fork_3
			case phil_3: fork_4
			case phil_4: fork_5
			case phil_5: fork_1
		endswitch

	function left_fork($a in Philosophers) =	
		switch($a)
			case phil_1: fork_1
			case phil_2: fork_2
			case phil_3: fork_3
			case phil_4: fork_4
			case phil_5: fork_5
		endswitch

	//Rules

	macro rule r_Eat =
		if (hungry(self)) then
			if( isUndef(owner(left_fork(self))) and isUndef(owner(right_fork(self))) ) then
				par
					owner(left_fork(self)) := self
					owner(right_fork(self)) := self
					eating(self) := true
				endpar
			endif
		endif

	macro rule r_Think =
		if ( not hungry(self)) then
			if eating(self) and (owner(left_fork(self)) = self) and (owner(right_fork(self)) = self) then
				par
					owner(left_fork(self)) := undef
					owner(right_fork(self)) := undef
					eating(self) := false
				endpar
			endif
		endif

	macro rule r_Philo = 
		par	
			r_Eat[]
			r_Think[]
		endpar

	// choose one agent and run it
	main rule r_choose_philo = 
		choose $p in Philosophers with true do
			program($p)

default init initial_state:
	function eating ($p in Philosophers)= false
	function owner ($f in Fork) = undef                      

	agent Philosophers:
		r_Philo[]
