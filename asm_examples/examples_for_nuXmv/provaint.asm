
asm provaint

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
// DOMAINS

domain Num subsetof Integer
// FUNCTIONS
	controlled intero: Num
	

definitions:
// DOMAIN DEFINITIONS
 domain Num={1 : 10}

// FUNCTION DEFINITIONS
	
// RULE DEFINITIONS

// INVARIANTS
CTLSPEC ag(intero>0)

// MAIN RULE
	main rule r_Main =
		intero:=intero+1

		
// INITIAL STATE
default init s0:
	function intero=1
