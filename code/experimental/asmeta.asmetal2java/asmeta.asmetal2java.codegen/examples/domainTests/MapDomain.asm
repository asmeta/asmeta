asm MapDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	 monitored monitoredMapDomainFunction: Map(String,Integer)
	 monitored monitoredMapDomainEnumFunction: Map(EnumDomain,Integer)
	 monitored monitoredMapDomainAbstractFunction: Map(AbstractDomain,Integer)
	
	// CONTROLLED
	 controlled controlledMapDomainFunction: Map(String,Integer) 
	 controlled controlledMapDomainEnumFunction: Map(EnumDomain,Integer)
	 monitored controlledMapDomainAbstractFunction: Map(AbstractDomain,Integer)

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
	 //function controlledMapDomainFunction = {"key1"->1,"key2"->2} // Initialization error
	 //function controlledMapDomainEnumFunction = {STATE1->3,STATE2->6} // Initialization error
	 //function controlledMapDomainAbstractFunction = {value1->2,value2->4} // Initialization error
