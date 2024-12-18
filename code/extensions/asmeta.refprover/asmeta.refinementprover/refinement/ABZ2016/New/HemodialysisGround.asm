asm HemodialysisGround

import ../../StandardLibrary

signature:

// DOMAINS
	enum domain Phases = {PREPARATION | INITIATION | ENDING}

// FUNCTIONS
	dynamic controlled phase: Phases //Phase terapy in which is currently the system
		

definitions:
// DOMAIN DEFINITIONS
	

// FUNCTION DEFINITIONS
	

// RULE DEFINITIONS

	macro rule r_run_preparation =
		phase := INITIATION
	
		
	macro rule r_run_initiation =
		phase := ENDING
	
	
	macro rule r_run_ending=
		skip
		
		
	macro rule r_run_dialysis =
		par 
			if (phase = PREPARATION) then
				r_run_preparation[]
			endif	
			if (phase = INITIATION) then
				r_run_initiation[]
			endif
			if (phase = ENDING) then
				r_run_ending[]
			endif
		endpar	

	
// INVARIANTS
	


// MAIN RULE
	main rule r_Main =
		r_run_dialysis[] 
		
		
		
// INITIAL STATE
default init s0:
	function phase = PREPARATION