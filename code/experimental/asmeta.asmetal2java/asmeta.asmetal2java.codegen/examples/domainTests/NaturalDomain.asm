asm NaturalDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	domain ConcreteDomain subsetof Natural

	// FUNCTIONS
	 
	// MONITORED
	monitored naturalMonitoredFunction: Natural
	monitored enumtonaturalMonitoredFunction: EnumDomain -> Natural
	monitored abstracttonaturalMonitoredFunction: AbstractDomain -> Natural
	monitored concretetonaturalMonitoredFunction: ConcreteDomain -> Natural
	
	// CONTROLLED
	controlled naturalControlledFunction: Natural
	controlled enumtonaturalControlledFunction: EnumDomain -> Natural
	controlled abstracttonaturalControlledFunction: AbstractDomain -> Natural
	controlled concretetonaturalControlledFunction: ConcreteDomain -> Natural
	
	
	static value1: AbstractDomain
	static value2: AbstractDomain
	

definitions:
	// DOMAIN DEFINITIONS
	domain ConcreteDomain = {1n:5n}

	// FUNCTION DEFINITIONS

	// RULE DEFINITIONS

	// INVARIANTS

	// MAIN RULE
	main rule r_Main =
		skip

// INITIAL STATE
default init s0:
	// function naturalControlledFunction = 1n // init error
	// function enumtonaturalControlledFunction($c in EnumDomain) = 2n // init error
	// function abstracttonaturalControlledFunction($c in AbstractDomain) = 3n // init error
	// function concretetonaturalControlledFunction($c in ConcreteDomain) = 4n // init error
