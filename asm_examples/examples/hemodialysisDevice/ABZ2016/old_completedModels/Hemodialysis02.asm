asm Hemodialysis02

import ../../../../STDL/StandardLibrary

signature:

// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
	enum domain Mode = {THERAPY_OVERVIEW | EMPTY_CARTRIDGE | DRAIN_DIALYZER | REINFUSION | THERAPY_RUNNING | CONNECT_PATIENT | RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
	
// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled mode: Mode //Modes in which the system can be
		

definitions:
// DOMAIN DEFINITIONS
	

// FUNCTION DEFINITIONS
	

// RULE DEFINITIONS

	macro rule r_auto_test =
		mode := CONNECT_CONCENTRATE
		
	macro rule r_connect_concentrate =
		mode := SET_RINSING_PARAM
		
	macro rule r_set_rinsing_param =
		mode := TUBING_SYSTEM
		
	macro rule r_tubing_system =
		mode := PREPARE_HEPARIN
		
	macro rule r_prepare_heparin =
		mode := SET_TREAT_PARAM
		
	macro rule r_set_treatment_param =
		mode := RINSE_DIALYZER
		
	macro rule r_rinse_dialyzer =
		par
			mode := CONNECT_PATIENT
			phaseTherapy := INITIATION
		endpar
		
	macro rule r_preparation =
		par
			if (mode = AUTO_TEST) then 
				r_auto_test[] 
			endif
			if (mode = CONNECT_CONCENTRATE) then 
				r_connect_concentrate[]
			endif
			if (mode = SET_RINSING_PARAM) then 
				r_set_rinsing_param[] 
			endif
			if (mode = TUBING_SYSTEM) then 
				r_tubing_system[] 
			endif
			if (mode = PREPARE_HEPARIN) then
				r_prepare_heparin[]
			endif
			if (mode = SET_TREAT_PARAM) then 
				r_set_treatment_param[]
			endif
			if (mode = RINSE_DIALYZER) then
				r_rinse_dialyzer[]
			endif
		endpar

	macro rule r_connect_patient =
		mode := THERAPY_RUNNING
		
	macro rule r_running_therapy =
		par
			mode := REINFUSION
			phaseTherapy := ENDING
		endpar
		
	macro rule r_initiation =
		par
			if (mode = CONNECT_PATIENT) then
				r_connect_patient[]
			endif
			if (mode = THERAPY_RUNNING) then
				r_running_therapy[] 
			endif
		endpar
	
	macro rule r_drain_dialyzer =
		mode := EMPTY_CARTRIDGE
		
	macro rule r_empty_cartridge =
		mode := THERAPY_OVERVIEW
		
	macro rule r_therapy_overview =
		skip
	
	macro rule r_reinfusion = 
		mode := DRAIN_DIALYZER
	
	macro rule r_ending=
		par
			if (mode = REINFUSION) then
				r_reinfusion[]
			endif
			if (mode = DRAIN_DIALYZER) then
				r_drain_dialyzer[]
			endif
			if (mode = EMPTY_CARTRIDGE) then
				r_empty_cartridge[]
			endif
			if (mode = THERAPY_OVERVIEW) then
				r_therapy_overview[]
			endif
		endpar
		
		
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
	function mode = AUTO_TEST
