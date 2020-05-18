asm Hemodialysis02

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

	macro rule r_auto_test =
		skip
	macro rule r_connect_concentrate =
		skip
	macro rule r_set_rinsing_param =
		skip
	macro rule r_set_components =
		skip
	macro rule r_rinse_test_tubes =
		skip
	macro rule r_prepare_heparin =
		skip
	macro rule r_set_treatment_param =
		skip
	macro rule r_rinse_dialyzer =
		skip
					
	macro rule r_preparation =
		par
			r_auto_test[]
			r_connect_concentrate[]
			r_set_rinsing_param[]
			r_set_components[]
			r_rinse_test_tubes[]
			r_prepare_heparin[]
			r_set_treatment_param[]
			r_rinse_dialyzer[]
		endpar

		
	macro rule r_connect_patient =
		skip
	macro rule r_during_therapy =
		skip
		
	macro rule r_initiation = 
		par
			r_connect_patient[]
			r_during_therapy[]
		endpar
		
		
	macro rule r_reinfusion =
		skip
	macro rule r_empty_dialyzer =
		skip
	macro rule r_empty_cartridge =
		skip
	macro rule r_therapy_overview =
		skip
			
	macro rule r_ending = 
		par
			r_reinfusion[]
			r_empty_dialyzer[]
			r_empty_cartridge[]
			r_therapy_overview[]
		endpar


// INVARIANTS
	


// MAIN RULE
	main rule r_Main =
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
