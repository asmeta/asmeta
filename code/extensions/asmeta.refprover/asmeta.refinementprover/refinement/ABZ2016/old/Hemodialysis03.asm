asm Hemodialysis03

import ../StandardLibrary

signature:

// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
	enum domain Mode = {THERAPY_OVERVIEW | EMPTY_CARTRIDGE | DRAIN_DIALYZER | REINFUSION | THERAPY_RUNNING | CONNECT_PATIENT | RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
	enum domain RinsingParam = {FILLING_BP_RATE | FILLING_BP_VOLUME | BP_RATE_RINSING | DF_FLOW_RINSING | TIME_RINSING | UF_RATE_RINSING | UF_VOLUME_RINSING}
	enum domain TubingPhase = {CONNECT_AV_TUBES | CONNECT_ALL_COMP | SET_SALINE_LEVELS | INSERT_BLOODLINES | PRIMING | CONNECT_AV_ENDS}
	enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | MIN_AP | MAX_AP | MIN_VP | MAX_VP | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
	enum domain RinsePhase = {CONNECT_DIALYZER | FILL_ART_CHAMBER | FILL_VEN_CHAMBER | FILL_DIALYZER}
	enum domain PatientPhase = {CONN_ART | START_BP | BLOOD_FLOW | FILL_TUBING | CONN_VEN | END_CONN}
	enum domain TherapyPhase = {START_HEPARIN | THERAPY_EXEC | THERAPY_END}
	enum domain ReinfusionPhase = {REMOVE_ART | CONN_SALINE | START_SALINE_INF | RUN_SALINE_INF | CHOOSE_NEXT_REINF_STEP | START_SALINE_REIN | RUN_SALINE_REIN | REMOVE_VEN}
	
// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled mode: Mode //Modes in which the system can be
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingPhase: TubingPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	dynamic controlled patientPhase: PatientPhase //Phase during patient connection
	dynamic controlled patient_conn_venously: Boolean //True --> patient is connected arterially during patient connection during therapy initiation
	dynamic controlled therapyPhase: TherapyPhase //Phase during therapy
	dynamic controlled reinfusionPhase: ReinfusionPhase //Reinfusion phase
	dynamic monitored end_therapy: Boolean //True --> therapy is finished
	
	//Reinfusion
	dynamic monitored new_saline_reinfusion: Boolean //True--> new saline reinfusion is started
	
definitions:
// DOMAIN DEFINITIONS
	

// FUNCTION DEFINITIONS
	

// RULE DEFINITIONS

	macro rule r_auto_test =
		mode := CONNECT_CONCENTRATE
		
	macro rule r_connect_concentrate =
		mode := SET_RINSING_PARAM
	
	macro rule r_set_filling_bp_rate =
		rinsingParam := FILLING_BP_VOLUME
		
	macro rule r_set_filling_bp_volume =
		rinsingParam := BP_RATE_RINSING
	
	macro rule r_set_bp_rate_rinsing =
		rinsingParam := DF_FLOW_RINSING
	
	macro rule r_set_df_flow_rinsing =
		rinsingParam := TIME_RINSING
		
	macro rule r_set_time_rinsing =
		rinsingParam := UF_RATE_RINSING
	
	macro rule r_set_uf_rate_rinsing =
		rinsingParam := UF_VOLUME_RINSING
		
	macro rule r_set_uf_volume_rinsing =
		mode := TUBING_SYSTEM
		
	macro rule r_set_rinsing_param =
		par
			if (rinsingParam = FILLING_BP_RATE) then
				r_set_filling_bp_rate[]	
			endif
			if (rinsingParam = FILLING_BP_VOLUME) then
				r_set_filling_bp_volume[]
			endif
			if (rinsingParam = BP_RATE_RINSING) then
				r_set_bp_rate_rinsing[]
			endif
			if (rinsingParam = DF_FLOW_RINSING) then
				r_set_df_flow_rinsing[]
			endif
			if (rinsingParam = TIME_RINSING) then
				r_set_time_rinsing[] 
			endif
			if (rinsingParam = UF_RATE_RINSING) then
				r_set_uf_rate_rinsing[] 
			endif
			if (rinsingParam = UF_VOLUME_RINSING) then
				r_set_uf_volume_rinsing[] 
			endif
		endpar

	macro rule r_connect_av_tubes =
		tubingPhase := CONNECT_ALL_COMP
	
	macro rule r_connect_all_comp =
		tubingPhase := SET_SALINE_LEVELS
	
	macro rule r_set_saline_levels =
		tubingPhase := INSERT_BLOODLINES
	
	macro rule r_insert_bloodlines =
		tubingPhase := PRIMING
	
	macro rule r_priming =
		tubingPhase := CONNECT_AV_ENDS
	
	macro rule r_connect_av_ends =
		mode := PREPARE_HEPARIN
		
	macro rule r_tubing_system =
		par
			if (tubingPhase = CONNECT_AV_TUBES) then
				r_connect_av_tubes[] 
			endif
			if (tubingPhase = CONNECT_ALL_COMP) then
				r_connect_all_comp[] 
			endif
			if (tubingPhase = SET_SALINE_LEVELS) then
				r_set_saline_levels[] 
			endif
			if (tubingPhase = INSERT_BLOODLINES) then
				r_insert_bloodlines[] 
			endif
			if (tubingPhase = PRIMING) then
				r_priming[] 
			endif
			if (tubingPhase = CONNECT_AV_ENDS) then
				r_connect_av_ends[] 
			endif
		endpar
				
	macro rule r_prepare_heparin =
		mode := SET_TREAT_PARAM
	
	
	macro rule r_set_blood_conductivity =
		treatmentParam := BIC_AC
		
	macro rule r_set_bic_ac =
		treatmentParam := BIC_CONDUCTIVITY
		
	macro rule r_set_bic_conductivity =
		treatmentParam := DF_TEMP
	
	macro rule r_set_df_temp =
		treatmentParam := DF_FLOW
	
	macro rule r_set_df_flow =
		treatmentParam := UF_VOLUME
		
	macro rule r_set_uf_volume =
		treatmentParam := THERAPY_TIME
	
	macro rule r_set_therapy_time =
		treatmentParam := MIN_UF_RATE
	
	macro rule r_set_min_uf_rate =
		treatmentParam := MAX_UF_RATE
	
	macro rule r_set_max_uf_rate =
		treatmentParam := MIN_AP
	
	macro rule r_set_min_ap =
		treatmentParam := MAX_AP
	
	macro rule r_set_max_ap =
		treatmentParam := MIN_VP
	
	macro rule r_set_min_vp =
		treatmentParam := MAX_VP
	
	macro rule r_set_max_vp =
		treatmentParam := DELTA_AP
	
	macro rule r_set_delta_ap =
		treatmentParam := PERC_DELTA_TMP
	
	macro rule r_set_perc_delta_tmp =
		treatmentParam := LIMITS_TMP
	
	macro rule r_set_limits_tmp = 
		treatmentParam := MAX_TMP
	
	macro rule r_set_max_tmp = 
		treatmentParam := EXTENDED_TMP
	
	macro rule r_set_extended_tmp =
		treatmentParam := MAX_BEP
	
	macro rule r_set_max_bep = 
		treatmentParam := STOP_TIME_H
	
	macro rule r_set_h_stop_time =
		treatmentParam := BOLUS_VOLUME_H
	
	macro rule r_set_h_bolus_volume =
		treatmentParam := RATE_H
	
	macro rule r_set_h_rate =
		treatmentParam := ACTIVATION_H
	
	macro rule r_set_h_activation =
		treatmentParam := SYRINGE_TYPE
	
	macro rule r_set_syringe_type =
		mode := RINSE_DIALYZER	
				
	macro rule r_set_treatment_param =
		par
			if (treatmentParam = BLOOD_CONDUCTIVITY) then
				r_set_blood_conductivity[] 
			endif
			if (treatmentParam = BIC_AC) then
				r_set_bic_ac[] 
			endif
			if (treatmentParam = BIC_CONDUCTIVITY) then
				r_set_bic_conductivity[] 
			endif
			if (treatmentParam = DF_TEMP) then
				r_set_df_temp[] 
			endif
			if (treatmentParam = DF_FLOW) then
				r_set_df_flow[] 
			endif
			if (treatmentParam = UF_VOLUME) then
				r_set_uf_volume[] 
			endif
			if (treatmentParam = THERAPY_TIME) then
				r_set_therapy_time[] 
			endif
			if (treatmentParam = MIN_UF_RATE) then
				r_set_min_uf_rate[] 
			endif
			if (treatmentParam = MAX_UF_RATE) then
				r_set_max_uf_rate[] 
			endif
			if (treatmentParam = MIN_AP) then
				r_set_min_ap[] 
			endif
			if (treatmentParam = MAX_AP) then
				r_set_max_ap[] 
			endif
			if (treatmentParam = MIN_VP) then
				r_set_min_vp[] 
			endif
			if (treatmentParam = MAX_VP) then
				r_set_max_vp[] 
			endif
			if (treatmentParam = DELTA_AP) then
				r_set_delta_ap[] 
			endif
			if (treatmentParam = PERC_DELTA_TMP) then
				r_set_perc_delta_tmp[] 
			endif
			if (treatmentParam = LIMITS_TMP) then
				r_set_limits_tmp[] 
			endif
			if (treatmentParam = MAX_TMP) then
				r_set_max_tmp[] 
			endif
			if (treatmentParam = EXTENDED_TMP) then
				r_set_extended_tmp[] 
			endif
			if (treatmentParam = MAX_BEP) then
				r_set_max_bep[] 
			endif
			if (treatmentParam = STOP_TIME_H) then
				r_set_h_stop_time[] 
			endif
			if (treatmentParam = BOLUS_VOLUME_H) then
				r_set_h_bolus_volume[] 
			endif
			if (treatmentParam = RATE_H) then
				r_set_h_rate[] 
			endif
			if (treatmentParam = ACTIVATION_H) then
				r_set_h_activation[] 
			endif
			if (treatmentParam = SYRINGE_TYPE) then
				r_set_syringe_type[] 
			endif
		endpar

	macro rule r_connect_dialyzer =
		rinsePhase := FILL_ART_CHAMBER
	
	macro rule r_fill_art_chamber =
		rinsePhase := FILL_VEN_CHAMBER
		
	macro rule r_fill_ven_chamber =
		rinsePhase := FILL_DIALYZER
		
	macro rule r_fill_dialyzer =
		par
			mode := CONNECT_PATIENT
			phaseTherapy := INITIATION
		endpar
				
	macro rule r_rinse_dialyzer =
		par
			if (rinsePhase = CONNECT_DIALYZER) then
				r_connect_dialyzer[] 
			endif
			if (rinsePhase = FILL_ART_CHAMBER) then
				r_fill_art_chamber[] 
			endif
			if (rinsePhase = FILL_VEN_CHAMBER) then
				r_fill_ven_chamber[] 
			endif
			if (rinsePhase = FILL_DIALYZER) then
				r_fill_dialyzer[] 
			endif
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
	
	macro rule r_conn_arterially =
		patientPhase := START_BP
	
	macro rule r_start_bp =
		patientPhase := BLOOD_FLOW
	
	macro rule r_set_blood_flow =
		if (patient_conn_venously = false) then
			patientPhase := FILL_TUBING
		else
			patientPhase := END_CONN
		endif
	
	macro rule r_fill_tubing =
		patientPhase := CONN_VEN
	
	macro rule r_conn_venously =
		par
			patientPhase := START_BP
			patient_conn_venously := true
		endpar
	
	macro rule r_end_connection =
		mode := THERAPY_RUNNING
		 	
	macro rule r_patient_connection =
		par
			if (patientPhase = CONN_ART) then
				r_conn_arterially[] 
			endif
			if (patientPhase = START_BP) then
				r_start_bp[] 
			endif
			if (patientPhase = BLOOD_FLOW) then
				r_set_blood_flow[] 
			endif
			if (patientPhase = FILL_TUBING) then
				r_fill_tubing[] 
			endif
			if (patientPhase = CONN_VEN) then
				r_conn_venously[] 
			endif
			if (patientPhase = END_CONN) then
				r_end_connection[] 
			endif
		endpar

	macro rule r_check_patient =
		skip
	
	macro rule r_connect_patient =
		par
			r_patient_connection[]
			r_check_patient[]
		endpar
	
	
	macro rule r_start_heparin =
		therapyPhase := THERAPY_EXEC
	
	
	macro rule r_run_heparin = 
		skip
	
	macro rule r_ap_vp_limits = 
		skip
		
	macro rule r_treatment_min_UF = 
		skip
		
	macro rule r_interrupt = 
		skip
		
	macro rule r_arterial_bolus = 
		skip
	
	macro rule r_therapy_end_time = 
		if (end_therapy = true) then
			therapyPhase := THERAPY_END
		endif
	
			
	macro rule r_therapy_exec =
		par
			r_run_heparin[]
			r_ap_vp_limits[]
			r_treatment_min_UF[]
			r_interrupt[]
			r_arterial_bolus[] 
			r_therapy_end_time[] 
		endpar
		
	macro rule r_therapy_end =
		par
			mode := REINFUSION
			phaseTherapy := ENDING
		endpar
		
	macro rule r_running_therapy =
		par
			if (therapyPhase = START_HEPARIN) then
				r_start_heparin[] 
			endif
			if (therapyPhase = THERAPY_EXEC) then
				r_therapy_exec[] 
			endif
			if (therapyPhase = THERAPY_END) then
				r_therapy_end[] 
			endif
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
	
	rule r_remove_arterially =
		reinfusionPhase := CONN_SALINE
		
	macro rule r_connect_saline =
		reinfusionPhase := START_SALINE_INF
	
	macro rule r_start_saline_inf =
		reinfusionPhase := RUN_SALINE_INF
		
	macro rule r_run_saline_inf =
		reinfusionPhase := CHOOSE_NEXT_REINF_STEP
	
	macro rule r_choose_next_reinf_step =
		if (new_saline_reinfusion = true) then
			reinfusionPhase := START_SALINE_REIN
		else
			reinfusionPhase := REMOVE_VEN
		endif
		
	macro rule r_start_saline_reinf =
		reinfusionPhase := RUN_SALINE_REIN
	
	macro rule r_run_saline_reinf = 
		reinfusionPhase := CHOOSE_NEXT_REINF_STEP
		
	macro rule r_remove_venously =
		mode := DRAIN_DIALYZER
		
	macro rule r_run_reinfusion =
		par
			if (reinfusionPhase = REMOVE_ART) then
				r_remove_arterially[] 
			endif
			if (reinfusionPhase = CONN_SALINE) then
				r_connect_saline[] 
			endif
			if (reinfusionPhase = START_SALINE_INF) then
				r_start_saline_inf[] 
			endif
			if (reinfusionPhase = CHOOSE_NEXT_REINF_STEP) then
				r_choose_next_reinf_step[] 
			endif
			if (reinfusionPhase = RUN_SALINE_INF) then
				r_run_saline_inf[] 
			endif
			if (reinfusionPhase = START_SALINE_REIN) then
				r_start_saline_reinf[] 
			endif
			if (reinfusionPhase = RUN_SALINE_REIN) then
				r_run_saline_reinf[] 
			endif
			if (reinfusionPhase = REMOVE_VEN) then
				r_remove_venously[] 
			endif
		endpar
	
	macro rule r_check_reinfusion =
		skip
	
	macro rule r_drain_dialyzer =
		mode := EMPTY_CARTRIDGE
		
	macro rule r_empty_cartridge =
		mode := THERAPY_OVERVIEW
		
	macro rule r_therapy_overview =
		skip
	
	macro rule r_reinfusion = 
		par
			r_run_reinfusion[]
			r_check_reinfusion[] 
		endpar
		
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


// MAIN RULE
	main rule r_Main =
		r_run_therapy[] 

	
		
		
		
// INITIAL STATE
default init s0:
	function phaseTherapy = PREPARATION
	function mode = AUTO_TEST
	function rinsingParam = FILLING_BP_RATE
	function tubingPhase = CONNECT_AV_TUBES
	function treatmentParam = BLOOD_CONDUCTIVITY
	function rinsePhase = CONNECT_DIALYZER
	function patientPhase = CONN_ART
	function patient_conn_venously = false
	function therapyPhase = START_HEPARIN
	function reinfusionPhase = REMOVE_ART