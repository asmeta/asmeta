asm ConcreteD

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	domain ConcreteDomain subsetof Integer
	domain ConcreteDomain2 subsetof Integer
	domain ConcreteEnumDomain subsetof EnumDomain
	abstract domain AbstractDomain
	domain ConcreteAbstractDomain subsetof AbstractDomain
	
	// FUNCTIONS
	 
	// MONITORED
	monitored concreteDomainMonitoredFunction: ConcreteDomain
	monitored concreteDomain2MonitoredFunction: ConcreteDomain2
	monitored concreteEnumDomainMonitoredFunction: ConcreteEnumDomain
	monitored contreteToEnumMonitoredFunction: ConcreteDomain -> EnumDomain
	monitored enumContreteToEnumMonitoredFunction: ConcreteEnumDomain -> EnumDomain
	monitored enumContreteToIntegerMonitoredFunction: ConcreteEnumDomain -> Integer
	monitored enumContreteToAbstractMonitoredFunction: ConcreteEnumDomain -> AbstractDomain
	monitored concreteToConcrete2MonitoredFunction: ConcreteDomain -> ConcreteDomain2
	monitored concretetoAbstractMonitoredFunction: ConcreteDomain -> AbstractDomain
	//monitored concreteAbstractToIntegerMonitoredFunction: ConcreteAbstractDomain -> Integer // error
	monitored concrete2toBooleanMonitoredFunction: ConcreteDomain2 -> Boolean
	
	// CONTROLLED
	controlled concreteDomainControlledFunction: ConcreteDomain 
	controlled concreteDomain2ControlledFunction: ConcreteDomain2
	controlled concreteEnumDomainControlledFunction: ConcreteEnumDomain
	controlled concreteToEnumControlledFunction: ConcreteDomain -> EnumDomain
	controlled enumConcreteToEnumControlledFunction: ConcreteEnumDomain -> EnumDomain
	controlled enumConcreteToIntegerControlledFunction: ConcreteEnumDomain -> Integer
	controlled enumContreteToAbstractControlledFunction: ConcreteEnumDomain -> AbstractDomain
	controlled concreteToConcrete2ControlledFunction: ConcreteDomain -> ConcreteDomain2
	controlled concretetoAbstractControlledFunction: ConcreteDomain -> AbstractDomain
	//controlled concreteAbstractToIntegerControlledFunction: ConcreteAbstractDomain -> Integer // error
	controlled concrete2toBooleanControlledFunction: ConcreteDomain2 -> Boolean
	
	static value1: AbstractDomain
	static value2: AbstractDomain
	
	//static concreteValue1: ConcreteAbstractDomain
	//static concreteValue2: ConcreteAbstractDomain
	
definitions:
	// DOMAIN DEFINITIONS
	domain ConcreteDomain = {1:5}
	domain ConcreteDomain2 = {1,5,10}
	domain ConcreteEnumDomain = {STATE1, STATE2}
	//domain ConcreteAbstractDomain = {concreteValue1, concreteValue2}
	// FUNCTION DEFINITIONS

	// RULE DEFINITIONS

	// INVARIANTS

	// MAIN RULE
	main rule r_Main =
		skip

// INITIAL STATE
default init s0:
	 function concreteDomainControlledFunction = 5 // OK
	 function concreteDomain2ControlledFunction = 5	// OK 
	 function concreteEnumDomainControlledFunction = STATE1 // OK
	 /*function concreteToEnumControlledFunction($c in ConcreteDomain) = switch($c)
										case 1 : STATE1
										case 2 : STATE2
										case 3 : STATE3
										case 4 : STATE1
										case 5 : STATE2
									endswitch*/ // init error
	// function concreteToEnumControlledFunction($c in ConcreteDomain) = STATE1 // init error
	/*function enumConcreteToEnumControlledFunction($c in ConcreteEnumDomain) = switch($c)
										case STATE1 : STATE1
										case STATE2 : STATE2
										case STATE3 : STATE3
									endswitch*/ // init error
	// function enumConcreteToEnumControlledFunction($c in ConcreteEnumDomain) = STATE1 // init error
	function enumConcreteToIntegerControlledFunction($c in ConcreteEnumDomain) = switch($c)
										case STATE1 : 1
										case STATE2 : 5
										case STATE3 : 10
									endswitch // OK
	 //function enumConcreteToIntegerControlledFunction($c in ConcreteEnumDomain) = 5 // OK
	 /*function enumContreteToAbstractControlledFunction($c in ConcreteEnumDomain) = switch($c)
	 									case STATE1 : value1
										case STATE2 : value2
										case STATE3 : value1
									endswitch */ // init error
	 // function enumContreteToAbstractControlledFunction($c in ConcreteEnumDomain) =value1 // init error
	 /*function concreteToConcrete2ControlledFunction($c in ConcreteDomain) = switch($c)
										case 1 : 1
										case 2 : 2
										case 3 : 3
										case 4 : 1
										case 5 : 2
									endswitch */ // init error
	//function concreteToConcrete2ControlledFunction($c in ConcreteDomain) = 2 // init error
	/* function concretetoAbstractControlledFunction($c in ConcreteDomain) = switch($c)
										case 1 : value1
										case 2 : value2
										case 3 : value1
										case 4 : value2
										case 5 : value1
									endswitch */ // init error
	//function concretetoAbstractControlledFunction($c in ConcreteDomain) = value1 // init error
	function concrete2toBooleanControlledFunction($c in ConcreteDomain2) = switch($c)
										case 1 : true
										case 5 : false
										case 10 : true
									endswitch // OK
	// function concrete2toBooleanControlledFunction($c in ConcreteDomain2) = true // OK
	