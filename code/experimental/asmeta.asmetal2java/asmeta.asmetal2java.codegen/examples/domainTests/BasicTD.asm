asm BasicTD

import ../STDL/StandardLibrary

signature:
	// DOMAINS

	// FUNCTIONS
	 
	// MONITORED
	// monitored complexMonitoredFunction: Complex // Not supported by the translator
	monitored realMonitoredFunction: Real //  OK
	monitored integerMonitoredFunction: Integer // OK
	// monitored naturalMonitoredFunction: Natural // Not supported by the testGen
	monitored stringMonitoredFunction: String // OK
	monitored charMonitoredFunction: Char //  OK
	monitored booleanMonitoredFunction: Boolean // OK
	// monitored undefMonitoredFunction: Undef // Not supported by the translator
	
	// CONTROLLED
	// controlled complexControlledFunction: Complex // Not supported by the translator
	controlled realControlledFunction: Real // Initialization error
	controlled integerControlledFunction: Integer // OK
	// controlled naturalControlledFunction: Natural // Not supported by the testGen
	controlled stringControlledFunction: String // OK
	controlled charControlledFunction: Char // Initialization error
	controlled booleanControlledFunction: Boolean // OK
	// controlled undefControlledFunction: Undef // Not supported by the translator

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
	// function complexControlledFunction = -2-i3 // Not supported by the translator
	function realControlledFunction = 3.4 // OK (problems with negative values) 
	function integerControlledFunction = -5 // OK
	// function naturalControlledFunction = 3n // Not supported by the testGen
	function stringControlledFunction = "hello world" // OK
	// function charControlledFunction = 'a' // init error
	function booleanControlledFunction = true // OK
	// function undefControlledFunction = undef // Not supported by the translator
