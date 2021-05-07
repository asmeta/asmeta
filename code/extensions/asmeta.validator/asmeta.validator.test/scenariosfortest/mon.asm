asm mon

import StandardLibrary

export *

signature:

        dynamic controlled con:  Boolean
		dynamic monitored mon: Boolean
		

definitions:

		// main rule
	main rule r_Main = con := not mon

// define the initial states 
default init s0:
	        
        