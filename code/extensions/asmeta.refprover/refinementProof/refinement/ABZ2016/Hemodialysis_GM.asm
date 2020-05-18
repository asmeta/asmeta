asm Hemodialysis_GM

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
		phaseTherapy := INITIATION
	
		
	macro rule r_initiation =
		phaseTherapy := ENDING
	
	
	macro rule r_ending=
		skip
		
		
	macro rule r_run_therapy =
		par 
			if (phaseTherapy = PREPARATION) then
				r_preparation[]
			endif	
			if (phaseTherapy = INITIATION) then
				r_initiation[]
			endif
			if (phaseTherapy = ENDING) then
				r_ending[]
			endif
		endpar	

	
// INVARIANTS
	


// MAIN RULE
	main rule r_Main =
		r_run_therapy[] 

	
		
		
		
// INITIAL STATE
default init s0:
	function phaseTherapy = PREPARATION