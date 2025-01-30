asm EnumTD

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	enum domain AnotherEnumDomain = {ON | OFF}
	abstract domain AbstractDomain
	domain ConcreteDomain subsetof Integer
	
	// FUNCTIONS
	 
	// MONITORED
	monitored enumMonitoredFunction: EnumDomain // OK
	monitored anotherenumMonitoredFunction: AnotherEnumDomain // OK
	monitored enumtointegerMonitoredFunction: EnumDomain -> Integer // OK
	monitored enumtobooleanMonitoredFunction: EnumDomain -> Boolean // OK
	monitored enumtostringMonitoredFunction: EnumDomain -> String // OK
	monitored enumtocharMonitoredFunction: EnumDomain -> Char
	monitored enumtorealMonitoredFunction: EnumDomain -> Real
	monitored enumtoabstractMonitoredFunction: EnumDomain -> AbstractDomain // OK
	monitored enumtoconcreteMonitoredFunction: EnumDomain -> ConcreteDomain // OK
	monitored enumtoenumMonitoredFunction: EnumDomain -> EnumDomain // OK
	monitored anotherEnumtoenumMonitoredFunction: AnotherEnumDomain -> EnumDomain // OK
	
	// CONTROLLED
	controlled enumControlledFunction: EnumDomain // OK
	controlled anotherEnumControlledFunction: AnotherEnumDomain // OK
	controlled enumtointegerControlledFunction: EnumDomain -> Integer // OK
	controlled enumtobooleanControlledFunction: EnumDomain -> Boolean // OK
	controlled enumtostringControlledFunction: EnumDomain -> String // OK
	controlled enumtocharControlledFunction: EnumDomain -> Char
	controlled enumtorealControlledFunction: EnumDomain -> Real
	controlled enumtoabstractControlledFunction: EnumDomain -> AbstractDomain // OK
	controlled enumtoconcreteControlledFunction: EnumDomain -> ConcreteDomain // OK
	controlled enumtoenumControlledFunction: EnumDomain -> EnumDomain // OK
	controlled anotherEnumtoenumControlledFunction: EnumDomain -> AnotherEnumDomain // OK
		
	static value1: AbstractDomain
	static value2: AbstractDomain

definitions:
	// DOMAIN DEFINITIONS
	domain ConcreteDomain = {1:10}

	// FUNCTION DEFINITIONS

	// RULE DEFINITIONS

	// INVARIANTS

	// MAIN RULE
	main rule r_Main =
		skip

// INITIAL STATE
default init s0:
	function enumControlledFunction = STATE1 // OK
	/*function enumtointegerControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : 10
										case STATE2 : 20
										case STATE3 : 30
									endswitch*/ // init error
	function enumtointegerControlledFunction($c in EnumDomain) = 15 // OK
	/*function enumtobooleanControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : true
										case STATE2 : false
										case STATE3 : true
									endswitch */ // init error
	function enumtobooleanControlledFunction($c in EnumDomain) = true // OK
	/*function enumtostringControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : "hello"
										case STATE2 : " "
										case STATE3 : "world"
									endswitch */ // init error
	function enumtostringControlledFunction($c in EnumDomain) = "hello world" // OK
	/*function enumtocharControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : 'a'
										case STATE2 : 'b'
										case STATE3 : 'c'
									endswitch*/ // init error
	//function enumtocharControlledFunction($c in EnumDomain) = 'd'// init error
	/*function enumtorealControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : 1.5
										case STATE2 : 2.7
										case STATE3 : 9.2
									endswitch*/ // init error
	function enumtorealControlledFunction($c in EnumDomain) = 2.5
	function enumtoabstractControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : value1
										case STATE2 : value2
										case STATE3 : value1
									endswitch  // OK
	// function enumtoabstractControlledFunction($c in EnumDomain) = value1 // OK
	/*function enumtoconcreteControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : 2
										case STATE2 : 4
										case STATE3 : 6
									endswitch */ // init error
	function enumtoconcreteControlledFunction($c in EnumDomain) = 5 // OK
	/*function enumtoenumControlledFunction($c in EnumDomain) = switch($c)
										case STATE1 : STATE2
										case STATE2 : STATE1
										case STATE3 : STATE3
									endswitch */ // init error
	function enumtoenumControlledFunction($c in EnumDomain) = STATE2 // OK
	function anotherEnumtoenumControlledFunction($c in EnumDomain) = ON // OK
