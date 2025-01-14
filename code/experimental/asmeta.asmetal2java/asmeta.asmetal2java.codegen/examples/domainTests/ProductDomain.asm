asm ProductDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	 monitored monitoredProductFunction: Prod(Integer, Integer)
	 monitored monitoredProductEnumFunction: Prod(EnumDomain, EnumDomain)
	 monitored monitoredProductAbstractFunction: Prod(AbstractDomain, AbstractDomain)
	
	// CONTROLLED
	 controlled controlledProductFunction: Prod(Integer, Integer) 
	 controlled controlledProductEnumFunction: Prod(EnumDomain, EnumDomain)
	 monitored controlledProductAbstractFunction: Prod(AbstractDomain, AbstractDomain)

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
	// function controlledProductFunction = (5,5) // Initialization error
	// function controlledProductEnumFunction = (STATE1,STATE1) // Initialization error
	// function controlledProductAbstractFunction = (value1,value2) // Initialization error
