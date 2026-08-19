// an example with infinite integers

asm inf_integers

import ../StandardLibrary

signature:
	// FUNCTIONS
	monitored input: Integer
	controlled output: Integer
	

definitions:
	// DOMAIN DEFINITIONS
	
	// RULES
	main rule r_1 = if input > 10 then output := 230 else output := 250 endif

// INITIAL STATE
default init s0:
