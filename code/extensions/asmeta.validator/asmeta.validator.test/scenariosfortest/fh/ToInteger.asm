
asm ToInteger

import StandardLibrary

signature:
	// DOMAINS
	
	// FUNCTION
	
	controlled n: Integer

definitions:
	
	// MAIN RULE
	main rule r_Main=
	     n:=toInteger("1")
	     
// INITIAL STATE
default init s0:

function n=undef
