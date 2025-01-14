asm ConcreteD

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	domain ConcreteDomain subsetof Integer
	domain ConcreteDomain2 subsetof Integer	
	
	// FUNCTIONS
	 
	// MONITORED
	 monitored monitoredConcreteDomainFunction: ConcreteDomain
	 monitored monitoredConcreteDomain2Function: ConcreteDomain2
	 
	
	// CONTROLLED
	 controlled controlledConcreteDomainFunction: ConcreteDomain 
	 controlled controlledConcreteDomain2Function: ConcreteDomain2


definitions:
	// DOMAIN DEFINITIONS
	domain ConcreteDomain = {1:10}
	domain ConcreteDomain2 = {1,5,10}
	// FUNCTION DEFINITIONS

	// RULE DEFINITIONS

	// INVARIANTS

	// MAIN RULE
	main rule r_Main =
		skip

// INITIAL STATE
default init s0:
	 function controlledConcreteDomainFunction = 5
	 function controlledConcreteDomain2Function = 5
	 