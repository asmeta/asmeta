asm SequenceDomain

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	abstract domain AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	monitored seqIntegerMonitoredFunction: Seq(Integer)
	monitored seqBooleanMonitoredFunction: Seq(Boolean)
	monitored seqStringMonitoredFunction: Seq(String)
	monitored seqCharMonitoredFunction: Seq(Char)
	monitored seqRealMonitoredFunction: Seq(Real)
	monitored seqEnumMonitoredFunction: Seq(EnumDomain)
	monitored seqAbstractMonitoredFunction: Seq(AbstractDomain)
	
	// CONTROLLED
	controlled seqIntegerControlledFunction: Seq(Integer)
	controlled seqBooleanControlledFunction: Seq(Boolean)
	controlled seqStringControlledFunction: Seq(String)
	controlled seqCharControlledFunction: Seq(Char)
	controlled seqRealControlledFunction: Seq(Real)
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
	function seqIntegerControlledFunction = [2,2]
	function seqEnumControlledFunction = [STATE1,STATE2]
	function seqAbstractControlledFunction = [value1,value2]
	function seqBooleanControlledFunction = [true,false]
	function seqStringControlledFunction = ["Hello"," ","world","!"]
	function seqCharControlledFunction = ['a','b','c']
	function seqRealControlledFunction = [5.5,2.2,7.6]
