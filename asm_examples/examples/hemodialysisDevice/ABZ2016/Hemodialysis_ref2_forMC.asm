asm Hemodialysis_ref2_forMC

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary
import ../../../STDL/LTLlibrary

signature:

// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
	enum domain ModePreparation = {RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
	enum domain ModeInitiation = {CONNECT_PATIENT | THERAPY_RUNNING}
	enum domain RinsingParam = {FILLING_BP_RATE | FILLING_BP_VOLUME | BP_RATE_RINSING | DF_FLOW_RINSING | TIME_RINSING | UF_RATE_RINSING | UF_VOLUME_RINSING}
	enum domain PatientPhase = {CONN_ART | START_BP | BLOOD_FLOW | FILL_TUBING | CONN_VEN | END_CONN}
	enum domain TherapyPhase = {START_HEPARIN | THERAPY_EXEC | THERAPY_END}
	enum domain TubingPhase = {CONNECT_AV_TUBES | CONNECT_ALL_COMP | SET_SALINE_LEVELS | INSERT_BLOODLINES | PRIMING | CONNECT_AV_ENDS}
	enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | MIN_AP | MAX_AP | MIN_VP | MAX_VP | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
	enum domain RinsePhase = {CONNECT_DIALYZER | FILL_ART_CHAMBER | FILL_VEN_CHAMBER | FILL_DIALYZER}
	enum domain BPStatus = {START | STOP}
	enum domain MachineState = {BYPASS | MAIN_FLOW}
	enum domain SignalLamps = {GREEN | YELLOW}
	enum domain ArterialBolusPhase = {WAIT_SOLUTION | SET_ARTERIAL_BOLUS_VOLUME | CONNECT_SOLUTION | RUNNING_SOLUTION }
	
	domain LMHlimit subsetof Integer
		
// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled modePreparation: ModePreparation //Modes in which the system can be
	dynamic controlled modeInitiation: ModeInitiation //Modes in which the system can be
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingPhase: TubingPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	dynamic controlled patientPhase: PatientPhase //Phase during patient connection
	dynamic controlled therapyPhase: TherapyPhase //Phase during therapy
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic controlled signal_lamp: SignalLamps //Signal lamp on the monitor
	dynamic controlled arterialBolusPhase: ArterialBolusPhase //Phase during arterial bolus
	
	dynamic monitored auto_test_end: Boolean //True --> automatic test terminate succesfully False --> otherwise
	dynamic monitored conn_concentrate: Boolean //True --> concentrate is connected to machine
	dynamic monitored heparin_prepared: Boolean //True --> heparin pump is prepared

	//Passed Time
	dynamic monitored passedHeparinTime: Boolean //Heparin Time passed
	dynamic monitored passed5Min: Boolean // 5 minutes passed
	dynamic monitored passedTherapyTime: Boolean //Therapy Time passed
	dynamic monitored passed120Sec: Boolean //120 secods passed
	dynamic monitored passed10Sec: Boolean //10 seconds passed
	
	
	//Rinsing parameter
	dynamic monitored bp_rate_rinsing_150: Boolean //True --> BP rate during rinsing is 150
	
	//tubing system
	dynamic monitored av_tubes_conn: Boolean // True --> A/V tubes are connected
	dynamic monitored all_comp_conn: Boolean // True --> all components are connected
	dynamic monitored saline_levels_set: Boolean // True --> saline levels are set
	dynamic monitored blood_line_insert: Boolean // True --> blood line are inserted
	dynamic controlled bp_status: BPStatus // BP status Start-->BP is running
	dynamic monitored bp_fill_fluid: Boolean // True --> BP is full with fluid
	dynamic monitored av_ends_conn: Boolean // True --> A/V ends are connected

	dynamic monitored activation_h: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic controlled activation_h_contr: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	
	//Rinse dialyzer
	dynamic monitored dialyzer_connected: Boolean //True--> dialyzer is connected to the machine
	dynamic monitored arterial_chamber_filled: Boolean //True--> arterial chamber is filled nearly half full
	dynamic monitored venous_chamber_fill: Boolean //True--> venous chamber is filled up to approx. 1 cm from upper edge
	dynamic monitored dialyzer_filled: Boolean //True--> dialyzer is filled

	//Connection patient
	dynamic monitored art_connected: Boolean //True--> patient connected arterially during patient connection
	dynamic controlled art_connected_contr: Boolean//True--> patient connected arterially during patient connection
	dynamic monitored blood_flow_conn_set: Boolean//True--> blood flow set
	dynamic monitored blood_on_VRD: Boolean //True--> blood is detected on VRD
	//*******da aggiungere negli altri raff
	dynamic monitored conn_infuse_set_volume: Boolean //True--> set volume infused 
	//******************
	dynamic monitored ven_connected: Boolean //True--> patient connected venously during patient connection
	dynamic controlled ven_connected_contr: Boolean //True--> patient connected venously during patient connection
	dynamic controlled bicarbonate_status: Boolean //True--> bicarbonate is running

	//Therapy running
	dynamic controlled heparin_running: Boolean //True--> heparin pump is on
	dynamic controlled ap_limits_set: Boolean //True--> the ap limits are set after 10 seconds of last blood pump activation
	dynamic controlled vp_limits_set: Boolean //True--> the vp limits are set after 10 seconds of last blood pump activation
	dynamic monitored treatment_min_uf_rate: Boolean //True --> Treatment at minimum UF rate 
	dynamic controlled treatment_min_uf_rate_contr: Boolean //True --> Treatment at minimum UF rate 
	dynamic monitored interrupt_dialysis: Boolean //True --> interrupt dialysis
	dynamic monitored start_arterial_bolus: Boolean //True --> start arterial bolus infusion
	dynamic monitored saline_solution_conn: Boolean //True --> saline solution for arterial bolus is connected
	dynamic monitored art_bolus_volume_set: Boolean //True --> arterial bolus is set
	dynamic controlled check_ap: Boolean
	dynamic monitored current_art_bolus_volume: LMHlimit //1--> nei limiti 2-->fuori limiti 0-->valore settato raggiunto
	
	
	dynamic monitored update_blood_flow: Boolean //True--> update blood flow
	
	derived machine_status_der: MachineState
	derived bp_status_der: BPStatus
	derived bicarbonate_status_der: Boolean


	
definitions:
// DOMAIN DEFINITIONS
	domain LMHlimit = {0 : 2} //L low 0 limit M into limit 1 H high limit 2
	
// FUNCTION DEFINITIONS
	
	function machine_status_der =
		machine_state

	
	function bp_status_der =
		bp_status

	function bicarbonate_status_der =
		bicarbonate_status
	
// RULE DEFINITIONS

	rule r_auto_test =
		if (auto_test_end = true) then
			modePreparation := CONNECT_CONCENTRATE
		endif
		
	rule r_connect_concentrate =
		if (conn_concentrate = true) then
			modePreparation := SET_RINSING_PARAM
		endif
	
		rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in RinsingParam, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				rinsingParam := $nextparam 
			endpar
		endif
		
	rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in ModePreparation, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				modePreparation := $nextparam 
			endpar
		endif

	rule r_insert_param_press ($inf in Integer, $sup in Integer, $monmin in Integer, $contrmin in Integer, $monmax in Integer, $contrmax in Integer, $succ in Boolean) = 
		if (($monmin >= $inf) and ($monmin <= $sup) and ($monmax >= $inf) and ($monmax <= $sup)) then
			par 
				$contrmin := $monmin
				$contrmax := $monmax
				$succ := false 
			endpar
		endif
			
	rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in TreatmentParam, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				treatmentParam := $nextparam 
			endpar
		endif

	rule r_insert_param ($nextparam in TreatmentParam, $mon in Boolean, $contr in Boolean) = 
		par 
			$contr := $mon 
			treatmentParam := $nextparam 
		endpar

	rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in PatientPhase, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				patientPhase := $nextparam 
			endpar
		endif
	
	macro rule r_insert_param ($phase in PatientPhase, $setvalue in Boolean) =
		if ($setvalue = true) then
			patientPhase := $phase
		endif
		
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
		modePreparation := TUBING_SYSTEM
		
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

	rule r_connect_av_tubes =
		if (av_tubes_conn = true) then
			tubingPhase := CONNECT_ALL_COMP
		endif
	
	rule r_connect_all_comp =
		if (all_comp_conn = true) then
			tubingPhase := SET_SALINE_LEVELS
		endif
	
	rule r_set_saline_levels =
		if (saline_levels_set = true) then
			tubingPhase := INSERT_BLOODLINES
		endif
		
	rule r_insert_bloodlines =
		if (blood_line_insert = true) then
			tubingPhase := PRIMING
		endif
		
	rule r_priming =
		if (bp_status_der = STOP) then
			//if (bp_status_update = false) then
				bp_status := START // azione della macchina
			//endif
		else
			if (bp_fill_fluid = true and bp_rate_rinsing_150 = true ) then
				par
					bp_status := STOP // azione della macchina
					tubingPhase := CONNECT_AV_ENDS
				endpar
			endif
		endif
	
	rule r_connect_av_ends =
		if (av_ends_conn = true) then
			modePreparation := PREPARE_HEPARIN
		endif
		
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
	
				
	rule r_prepare_heparin =
		if (heparin_prepared = true) then
			modePreparation := SET_TREAT_PARAM
		endif
	
	
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
		r_insert_param [SYRINGE_TYPE, activation_h, activation_h_contr]
	
	macro rule r_set_syringe_type =
		modePreparation := RINSE_DIALYZER	
	
	
	
	//macro rule r_set_syringe_type =
	//	r_insert_param [RINSE_DIALYZER, syringe_type, syringe_type_contr] 	
				
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
	
	rule r_connect_dialyzer =
		if (dialyzer_connected = true) then 
			rinsePhase := FILL_ART_CHAMBER
		endif
	
	rule r_fill_art_chamber =
		if (bp_status_der = STOP) then
			//if (bp_status_update = false) then
				bp_status := START // azione della macchina
			//endif
		else
			if (arterial_chamber_filled = true) then
				rinsePhase := FILL_VEN_CHAMBER
			endif
		endif
		
	rule r_fill_ven_chamber =
		if (venous_chamber_fill = true) then
			rinsePhase := FILL_DIALYZER
		endif
		
	rule r_fill_dialyzer =
		if (dialyzer_filled = true) then
			par
				bp_status := STOP // azione della macchina
				phaseTherapy := INITIATION
			endpar
		endif
			
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
			if (modePreparation = AUTO_TEST) then 
				r_auto_test[] 
			endif
			if (modePreparation = CONNECT_CONCENTRATE) then 
				r_connect_concentrate[]
			endif
			if (modePreparation = SET_RINSING_PARAM) then 
				r_set_rinsing_param[] 
			endif
			if (modePreparation = TUBING_SYSTEM) then 
				r_tubing_system[] 
			endif
			if (modePreparation = PREPARE_HEPARIN) then
				r_prepare_heparin[]
			endif
			if (modePreparation = SET_TREAT_PARAM) then 
				r_set_treatment_param[]
			endif
			if (modePreparation = RINSE_DIALYZER) then
				r_rinse_dialyzer[]
			endif
		endpar
		
	
		
	rule r_check_initiation_phase = 
		skip
	
	rule r_conn_arterially =
		if (art_connected = true) then
			par
				patientPhase := START_BP
				art_connected_contr := true
			endpar
		endif
	
	rule r_start_bp =
		par
			patientPhase := BLOOD_FLOW
			bp_status := START
			ap_limits_set := false
			vp_limits_set := false	
			//r_start_BP_therapy[]
		endpar
	

	macro rule r_set_blood_flow =
		if (ven_connected_contr = false) then
			r_insert_param[FILL_TUBING, blood_flow_conn_set]
		else
			r_insert_param[END_CONN, blood_flow_conn_set]
		endif
	
	rule r_fill_tubing =
		if (blood_on_VRD = true or conn_infuse_set_volume = true) then
			par
				patientPhase := CONN_VEN
				bp_status := STOP // azione della macchina
			endpar
		endif
	
	rule r_conn_venously =
		if (ven_connected = true) then
			par
				patientPhase := START_BP
				ven_connected_contr := true
			endpar
		endif
	
	rule r_end_connection =
		par
			//if (machine_state_status = false) then
				machine_state := MAIN_FLOW //azione della macchina (non gestione errore)
			//endif
			bicarbonate_status := true //is running (azione della macchina)
			signal_lamp := GREEN
			modeInitiation := THERAPY_RUNNING
		endpar
		 	
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
		if (activation_h_contr = true) then
			par
				therapyPhase := THERAPY_EXEC
				heparin_running := true
			endpar
		endif
	
	macro rule r_run_heparin = 
		if (heparin_running = true) then
			if (passedHeparinTime = true) then
				heparin_running := false
			endif
		endif
	
	rule r_set_ap_vp_limits =
		par
			if (ap_limits_set = false) then
				ap_limits_set := true
			endif
			if (vp_limits_set = false) then
				vp_limits_set := true
			endif
		endpar
		
	macro rule r_ap_vp_limits = 
		if (check_ap = true) then
			if (ap_limits_set = false or vp_limits_set = false) then
				if (passed10Sec = true) then
					r_set_ap_vp_limits[] 
				endif
			endif
		endif
		
	rule r_treatment_min_UF = 
		if (treatment_min_uf_rate_contr = false) then
			if (treatment_min_uf_rate = true) then
				treatment_min_uf_rate_contr := true
			endif
		endif
		
	rule r_interrupt = 
		if (interrupt_dialysis = true) then
			par
				therapyPhase := THERAPY_END
				machine_state := BYPASS // azione della macchina
			endpar
		endif
		
	rule r_start_arterial_bolus =
		if (start_arterial_bolus = true) then
			arterialBolusPhase := SET_ARTERIAL_BOLUS_VOLUME
		endif
	
	rule r_set_arterial_bolus_volume =
		if (art_bolus_volume_set = true) then
			par
				bp_status := STOP // azione della macchina
				arterialBolusPhase := CONNECT_SOLUTION
				check_ap := false
			endpar		
		endif	
				
	rule r_connect_solution =
		if (saline_solution_conn = true) then
			par
				arterialBolusPhase := RUNNING_SOLUTION
				bp_status := START
				ap_limits_set := false
				vp_limits_set := false
				check_ap := true	
				//r_start_BP_therapy[] 
			endpar
		endif
	
	rule r_running_solution =
		if (current_art_bolus_volume = 0) then
			arterialBolusPhase := WAIT_SOLUTION
		endif


	rule r_check_arterial_bolus =  
		skip
	
	rule r_run_arterial_bolus = 
		par
			r_running_solution[] 
			r_check_arterial_bolus[] 
		endpar
				
	macro rule r_arterial_bolus = 
		par
			if (arterialBolusPhase = WAIT_SOLUTION) then
				r_start_arterial_bolus[] 
			endif
			if (arterialBolusPhase = SET_ARTERIAL_BOLUS_VOLUME) then
				r_set_arterial_bolus_volume[] 
			endif
			if (arterialBolusPhase = CONNECT_SOLUTION) then
				r_connect_solution[] 
			endif
			if (arterialBolusPhase = RUNNING_SOLUTION) then
				r_run_arterial_bolus[]
			endif
		endpar
	
	rule r_therapy_end_time = 
		if (passedTherapyTime = true) then
			therapyPhase := THERAPY_END
		endif
	
	rule r_update_blood_flow =
		if (update_blood_flow = true ) then
			skip //update bloodflow and therapy time
		endif
	
	macro rule r_check_therapy_run = 
		skip
		
	macro rule r_therapy_exec =
		par
			r_run_heparin[] 
			r_ap_vp_limits[] 
			r_treatment_min_UF[] 
			r_arterial_bolus[]
			r_therapy_end_time[]
			r_update_blood_flow[]  
			r_check_therapy_run[]
			r_interrupt[] 
		endpar	
		
	macro rule r_therapy_end =
		phaseTherapy := ENDING
		
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
			r_check_initiation_phase[]
			if (modeInitiation = CONNECT_PATIENT) then
				r_connect_patient[]
			endif
			if (modeInitiation = THERAPY_RUNNING) then
				r_running_therapy[] 
			endif
		endpar

	
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
		
	//PROPERTIES
	//S-1	
	//CTLSPEC ag(art_connected_contr iff ven_connected_contr) //false
	LTLSPEC g(art_connected_contr iff ven_connected_contr) //false
	//S-4
	//CTLSPEC ag((phaseTherapy = INITIATION and modeInitiation = CONNECT_PATIENT and patientPhase = FILL_TUBING and (blood_on_VRD = true or conn_infuse_set_volume = true)) implies ax(bp_status_der = STOP and patientPhase = CONN_VEN)) //true
	LTLSPEC g((phaseTherapy = INITIATION and modeInitiation = CONNECT_PATIENT and patientPhase = FILL_TUBING and (blood_on_VRD = true or conn_infuse_set_volume = true)) implies x(bp_status_der = STOP and patientPhase = CONN_VEN)) //true


	main rule r_Main =
		r_run_therapy[] 

			
		
		
// INITIAL STATE
default init s0:
	function phaseTherapy = PREPARATION
	function modePreparation = AUTO_TEST
	function modeInitiation = CONNECT_PATIENT
	function rinsingParam = FILLING_BP_RATE
	function tubingPhase = CONNECT_AV_TUBES
	function treatmentParam = BLOOD_CONDUCTIVITY
	function rinsePhase = CONNECT_DIALYZER
	function machine_state = BYPASS
	function bp_status = STOP
	function activation_h_contr = false
	function patientPhase = CONN_ART
	function therapyPhase = START_HEPARIN
	function signal_lamp = YELLOW
	function arterialBolusPhase = WAIT_SOLUTION
	function ven_connected_contr = false
	function art_connected_contr = false	
	function bicarbonate_status = false
	function heparin_running = false
	function ap_limits_set = false
	function vp_limits_set = false
	function treatment_min_uf_rate_contr = false
	function check_ap = true
