asm AbstractTD

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	abstract domain AbstractDomain
	abstract domain AnotherAbstractDomain
	enum domain EnumDomain = {STATE1 | STATE2 | STATE3}
	domain ConcreteDomain subsetof Integer
	
	// FUNCTIONS
	 
	// MONITORED
	monitored abstractMonitoredFunction: AbstractDomain // OK
	monitored enumtoabstractMonitoredFunction: EnumDomain -> AbstractDomain // OK
	monitored stringMonitoredFunction: String // OK
	monitored abstracttointegerMonitoredFunction: AbstractDomain -> Integer // OK
	monitored abstracttostringMonitoredFunction: AbstractDomain -> String // OK
	monitored abstracttobooleanMonitoredFunction: AbstractDomain -> Boolean // OK
	monitored abstracttorealMonitoredFunction: AbstractDomain -> Real // OK
	monitored abstracttocharMonitoredFunction: AbstractDomain -> Char // OK
	monitored abstracttoenumMonitoredFunction: AbstractDomain -> EnumDomain // OK
	monitored abstracttoabstractMonitoredFunction: AbstractDomain -> AnotherAbstractDomain // OK
	monitored abstracttoconcreteMonitoredFunction: AbstractDomain -> ConcreteDomain // OK
	

	// CONTROLLED
	controlled abstractControlledFunction: AbstractDomain // OK
	controlled enumtoabstractControlledFunction: EnumDomain -> AbstractDomain // OK
	controlled stringControlledFunction: String // OK
	controlled abstracttointegerControlledFunction: AbstractDomain -> Integer // OK
	controlled abstracttostringControlledFunction: AbstractDomain -> String // OK
	controlled abstracttobooleanControlledFunction: AbstractDomain -> Boolean // OK
	controlled abstracttorealControlledFunction: AbstractDomain -> Real // OK
	controlled abstracttocharControlledFunction: AbstractDomain -> Char // OK
	controlled abstracttoenumControlledFunction: AbstractDomain -> EnumDomain // OK
	controlled abstracttoabstractControlledFunction: AbstractDomain -> AnotherAbstractDomain // OK
	controlled abstracttoconcreteControlledFunction: AbstractDomain -> ConcreteDomain // OK
	
	
	static value1: AbstractDomain
	static value2: AbstractDomain
	static another1: AnotherAbstractDomain
	static another2: AnotherAbstractDomain

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
	function abstractControlledFunction = value1 // init error
	function stringControlledFunction = "Hello world" // OK
	function enumtoabstractControlledFunction($c in EnumDomain) = switch($c)
									case STATE1 : value1
									case STATE2 : value2
									case STATE3 : value1
								endswitch //OK
	function abstracttointegerControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : 10
									case value2 : 20
								endswitch //OK
	// function abstracttointegerControlledFunction($c in AbstractDomain) = 10 // OK
	/*function abstracttorealControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : 5.5
									case value2 : 3.3
								endswitch */ // OK
	function abstracttorealControlledFunction($c in AbstractDomain) = 2.2 // OK						
	/* function abstracttocharControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : 'a'
									case value2 : 'b'
								endswitch*/ // init error
	// function abstracttocharControlledFunction($c in AbstractDomain) = 'c' // init error
	/* function abstracttostringControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : "hello"
									case value2 : "world"
								endswitch */ //OK
	function abstracttostringControlledFunction($c in AbstractDomain) = "hello world" // OK
	function abstracttobooleanControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : true
									case value2 : false
								endswitch // OK
	// function abstracttobooleanControlledFunction($c in AbstractDomain) = true // OK
	function abstracttoenumControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : STATE1
									case value2 : STATE2
								endswitch // OK
	// function abstracttoenumControlledFunction($c in AbstractDomain) = STATE1 // OK
	function abstracttoabstractControlledFunction($c in AbstractDomain) = switch($c)
									case value1 : another1
									case value2 : another2
								endswitch // OK
	//function abstracttoabstractControlledFunction($c in AbstractDomain) = another1 // OK
	/*function abstracttoconcreteControlledFunction($c in AbstractDomain) = switch($c)
										case value1 : 2
										case value2 : 4
									endswitch */ // init error
	function abstracttoconcreteControlledFunction($c in AbstractDomain) = 5 // OK
	