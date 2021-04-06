
asm telecamere2

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:

	domain Numtelecamere subsetof Integer

	// FUNCTIONS
	controlled telecamere: Numtelecamere -> Boolean
	monitored teleChoice: Numtelecamere 

definitions:
	// DOMAIN DEFINITIONS
	domain Numtelecamere = {1:3}
	
	// MAIN RULE
	main rule r_Main =
		// questo da errore java.lang.AssertionError: telecamere_0 does not have an update map
		if(teleChoice>1) then
			telecamere(teleChoice-1):=false
		endif

// INITIAL STATE
default init s0:
	function telecamere($n in Numtelecamere) = true
