asm Hemodialysis01

import ../StandardLibrary

signature:
// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system


definitions:
// DOMAIN DEFINITIONS
	

// FUNCTION DEFINITIONS
	

// RULE DEFINITIONS

					
	macro rule r_preparation =
		skip

		
	macro rule r_initiation = 
		skip
		
		
	macro rule r_ending = 
		skip
		


// INVARIANTS
	


// MAIN RULE
	main rule r_main =
		par 
			if (phaseTherapy = PREPARATION) then
				par
					r_preparation[]
					phaseTherapy := INITIATION
				endpar
			endif	
			if (phaseTherapy = INITIATION) then
				par
					r_initiation[]
					phaseTherapy := ENDING
				endpar
			endif
			if (phaseTherapy = ENDING) then
				r_ending[]
			endif
		endpar
		
		
// INITIAL STATE
default init s0:
	function phaseTherapy = PREPARATION
