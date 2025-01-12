asm BagDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	 monitored monitoredBagDomainFunction: Bag(Integer)
	 monitored monitoredBagDomainEnumFunction: Bag(EnumDomain)
	 monitored monitoredBagDomainAbstractFunction: Bag(AbstractDomain)
	
	// CONTROLLED
	 controlled controlledBagDomainFunction: Bag(Integer) 
	 controlled controlledBagDomainEnumFunction: Bag(EnumDomain)
	 monitored controlledBagDomainAbstractFunction: Bag(AbstractDomain)

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
	 //function controlledBagDomainFunction = <5> // Initialization error
	 //function controlledBagDomainEnumFunction = <STATE1> // Initialization error
	 //function controlledBagDomainAbstractFunction = <value1> // Initialization error
