asm PowersetDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	 monitored monitoredPowersetFunction: Powerset(Integer)
	 monitored monitoredPowersetEnumFunction: Powerset(EnumDomain)
	 monitored monitoredPowersetAbstractFunction: Powerset(AbstractDomain)
	
	// CONTROLLED
	 controlled controlledPowersetFunction: Powerset(Integer) 
	 controlled controlledPowersetEnumFunction: Powerset(EnumDomain)
	 monitored controlledPowersetAbstractFunction: Powerset(AbstractDomain)

	 static value1: AbstractDomain
	 static value2: AbstractDomain

definitions:
	// DOMAIN DEFINITIONS

	// FUNCTION DEFINITIONS

	// RULE DEFINITIONS

	// INVARIANTS

	// MAIN RULE
	main rule r_Main =
		skip

// INITIAL STATE
default init s0:
	 //function controlledPowersetFunction = {5} // Initialization error
	 //function controlledPowersetEnumFunction = {STATE1} // Initialization error
	 //function controlledPowersetAbstractFunction = {value1} // Initialization error
