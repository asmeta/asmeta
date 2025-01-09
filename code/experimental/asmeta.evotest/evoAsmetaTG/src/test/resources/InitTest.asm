// a simple example to test the initialization of functions.
// scenario under test: the initialization value of two functions that share the same domain
// check that after the initialization of the second, the first is not updated

asm InitTest

import STDL/StandardLibrary

signature:
	// DOMAINS
	domain Time subsetof Integer
	enum domain State = {OFF | ON | RESTARTED}
	
	// FUNCTIONS
	controlled seconds: Time
	controlled minutes: Time
	monitored signal : Boolean
	controlled status : State

definitions:
	// DOMAIN DEFINITIONS
	domain Time = {0 : 60}

	// RULE DEFINITIONS
	rule r_timeRule =
		if seconds < 5 then
			seconds := seconds + 1
		else
			par
				seconds := 0
				minutes := minutes + 1
			endpar
		endif
	
	rule r_restartRule = 
		if minutes < 6 then 
			skip
		else
			par
				minutes := 0
				status := RESTARTED
			endpar
		endif
	
	
	// MAIN RULE
	main rule r_Main = 
		par
			if signal then 
				par
					r_timeRule[]
					status := ON
				endpar
			endif
			r_restartRule[]
		endpar

// INITIAL STATE
default init s0:
	function seconds = 3
	function minutes = 0
	function status = OFF