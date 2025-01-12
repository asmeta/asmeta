asm SequenceDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	monitored seqIntegerMonitoredFunction: Seq(Integer)
	monitored seqEnumMonitoredFunction: Seq(EnumDomain)
	monitored seqAbstractMonitoredFunction: Seq(AbstractDomain)
	

	// CONTROLLED
	controlled seqIntegerControlledFunction: Seq(Integer)
	controlled seqEnumControlledFunction: Seq(EnumDomain)
	controlled seqAbstractControlledFunction: Seq(AbstractDomain)
	
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
	function seqIntegerControlledFunction = [5,5]
	function seqEnumControlledFunction = [STATE1,STATE2]
	function seqAbstractControlledFunction = [value1,value2]
	