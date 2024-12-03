asm HemodialysisRaff3_MC

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary
import ../../STDL/LTLLibrary

signature:

// DOMAINS
	enum domain Phases = {PREPARATION | INITIATION | ENDING}
	enum domain PrepPhaseMode = {RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
	enum domain RinsingParam = {FILLING_BP_RATE | FILLING_BP_VOLUME | BP_RATE_RINSING | DF_FLOW_RINSING | TIME_RINSING | UF_RATE_RINSING | UF_VOLUME_RINSING}
	enum domain TubingSystemPhase = {CONNECT_AV_TUBES | CONNECT_ALL_COMP | SET_SALINE_LEVELS | INSERT_BLOODLINES | PRIMING | CONNECT_AV_ENDS}
	enum domain BPStatus = {START | STOP}
	//enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | MIN_AP | MAX_AP | MIN_VP | MAX_VP | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
	enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE |/* MIN_AP |*/ MAX_AP |/* MIN_VP | MAX_VP |*/ DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
	enum domain Concentrate = {BICARBONATE | ACETATE}
	enum domain SyringeType = {ST10 | ST20 | ST30}
	enum domain RinsePhase = {CONNECT_DIALYZER | FILL_ART_CHAMBER | FILL_VEN_CHAMBER | FILL_DIALYZER}
	enum domain Value = {TOOLOW | PERMITTED | HIGH}
	enum domain MachineState = {BYPASS | MAIN_FLOW}
	enum domain ErrorAlarmType = {REIN_VP_UP | REIN_AP_LOW | UF_BYPASS | UF_VOLUME_ERR | UF_DIR | UF_RATE | SAD_ERR | HEPARIN_DIR | DF_PREP | FILL_BLOOD_VOL | CONN_VP_UP | CONN_VP_LOW | CONN_AP_LOW | INIT_VP_UP | INIT_VP_LOW | INIT_AP_UP | INIT_AP_LOW | BP_NO_FLOW | BP_LESS_FLOW | BP_ROTATION_DIR | TEMP_HIGH | TEMP_LOW | ARTERIAL_BOLUS | ARTERIAL_BOLUS_END}
	enum domain InitPhaseMode = {CONNECT_PATIENT | THERAPY_RUNNING}
	enum domain PatientPhase = {CONN_ART | START_BP | BLOOD_FLOW | FILL_TUBING | CONN_VEN | END_CONN}
	enum domain SignalLamps = {GREEN | YELLOW | RED}
	enum domain TherapyPhase = {START_HEPARIN | THERAPY_EXEC | THERAPY_END}
	enum domain ArterialBolusPhase = {WAIT_SOLUTION | SET_ARTERIAL_BOLUS_VOLUME | CONNECT_SOLUTION | RUNNING_SOLUTION }
	enum domain EndingPhaseMode = {REINFUSION | THERAPY_OVERVIEW | EMPTY_CARTRIDGE | DRAIN_DIALYZER}
	enum domain ReinfusionPhase = {REMOVE_ART | CONN_SALINE | START_SALINE_INF | RUN_SALINE_INF | CHOOSE_NEXT_REINF_STEP | START_SALINE_REIN | RUN_SALINE_REIN | REMOVE_VEN}
	
// FUNCTIONS
	dynamic controlled phase: Phases //Dialysis phase in which is currently the system
	dynamic controlled prepPhaseMode: PrepPhaseMode //Modes in which the system can be during preparation phase
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingSystemPhase: TubingSystemPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic controlled initPhaseMode: InitPhaseMode //Modes in which the system can be
	dynamic controlled patientPhase: PatientPhase //Phase during patient connection
	dynamic controlled signal_lamp: SignalLamps //Signal lamp on the monitor
	dynamic controlled therapyPhase: TherapyPhase //Phase during therapy
	dynamic controlled arterialBolusPhase: ArterialBolusPhase //Phase during arterial bolus
	dynamic controlled endingPhaseMode: EndingPhaseMode //Modes in which the system can be
	dynamic controlled reinfusionPhase: ReinfusionPhase //Reinfusion phase
	
	//Time
	dynamic monitored passed1Msec: Boolean //True --> 1 millisecond is passed
	dynamic monitored passed120Sec: Boolean //True --> 120 secondS are passed
	dynamic monitored passed3Sec: Boolean //True --> 3 secondS are passed
	dynamic monitored passed10Sec: Boolean //True --> 10 secondS are passed
	dynamic monitored passed1Sec: Boolean //True --> 1 second is passed
	dynamic monitored time_heparin: Boolean //True --> heparin time is passed
	dynamic monitored time_therapy: Boolean //True --> therapy time is passed
	dynamic monitored passed5Min: Boolean //True --> 5 minutes are passed
	
	
	//preparation phase
	dynamic monitored auto_test_end: Boolean //True --> automatic test terminate succesfully False --> otherwise
	dynamic monitored conn_concentrate: Boolean //True --> concentrate is connected to machine

	
	//Rinsing parameter
	dynamic monitored fill_bp_rate: Value //The rate with which the blood side is filled or rinsed
	//dynamic controlled fill_bp_rate_contr: Integer //The rate with which the blood side is filled or rinsed
	dynamic monitored fill_bp_volume: Value //The BP stops after it has rinsed the blood side using the set volume
	//dynamic controlled fill_bp_volume_contr: Integer //The BP stops after it has rinsed the blood side using the set volume
	dynamic monitored bp_rate_rinsing: Value //BP rate for rinsing program
	//dynamic controlled bp_rate_rinsing_contr: Integer //BP rate for rinsing program
	dynamic monitored df_flow_rinsing: Value //DF flow rate for rinsing program
	//dynamic controlled df_flow_rinsing_contr: Integer //DF flow rate for rinsing program
	dynamic monitored time_rinsing: Value //Duration of adjusted rinsing program
	//dynamic controlled time_rinsing_contr: Integer //Duration of adjusted rinsing program
	dynamic monitored uf_rate_rinsing: Value //UF rate when rinsing with a physiological saline solution
	//dynamic controlled uf_rate_rinsing_contr: Integer //UF rate when rinsing with a physiological saline solution
	dynamic monitored uf_volume_rinsing: Value //UF volume when rinsing with a physiological saline solution
	//dynamic controlled uf_volume_rinsing_contr: Integer //UF volume when rinsing with a physiological saline solution
	
	//tubing system
	dynamic monitored av_tubes_connected: Boolean // True --> A/V tubes are connected
	dynamic monitored all_components_connected: Boolean // True --> all components are connected
	dynamic monitored saline_levels_set: Boolean // True --> saline levels are set
	dynamic monitored blood_line_insert: Boolean // True --> blood line are inserted
	dynamic controlled bp_status: BPStatus // BP status Start-->BP is running
	dynamic monitored bp_fill_fluid: Boolean // True --> BP is full with fluid
	dynamic monitored av_ends_connected: Boolean // True --> A/V ends are connected
	dynamic monitored bp_rate_rinsing_150: Boolean //True --> BP rate during rinsing is 150
	
	//heparin
	dynamic monitored heparin_prepared: Boolean //True --> heparin pump is prepared
	
	//treatment param
	dynamic monitored blood_conductivity: Value //Blood conductivity: the rate with which the blood side is filled or rinsed
	//dynamic controlled blood_conductivity_contr: Integer //Blood conductivity: the rate with which the blood side is filled or rinsed
	dynamic monitored bic_ac: Concentrate  //Selection of the concentrate used during therapy
	dynamic controlled bic_ac_contr: Concentrate  //Selection of the concentrate used during therapy
	dynamic monitored bic_conductivity: Value //Bicarbonate conductivity
	//dynamic controlled bic_conductivity_contr: Integer //Bicarbonate conductivity
	dynamic monitored df_temp: Value //DF temperature
	//dynamic controlled df_temp_contr: Integer //DF temperature
	dynamic monitored df_flow: Value //DF flow
	//dynamic controlled df_flow_contr: Integer //DF flow
	dynamic monitored uf_volume: Value //UF volume
	//dynamic controlled uf_volume_contr: Integer //UF volume
	dynamic monitored therapy_time_mins: Value //Therapy time minutes
	//dynamic controlled therapy_time_mins_contr: Integer //Therapy time minutes
	dynamic monitored therapy_time_hrs: Value //Therapy time hours
	//dynamic controlled therapy_time_hrs_contr: Integer //Therapy time hours
	dynamic monitored min_uf_rate: Value //Minimun UF rate
	//dynamic controlled min_uf_rate_contr: Integer //Minimun UF rate
	dynamic monitored max_uf_rate: Value //Maximum UF rate
	//dynamic controlled max_uf_rate_contr: Integer //Maximum UF rate
	dynamic monitored max_ap: Value //Maximum AP
	//dynamic controlled max_ap_contr: Integer //Maximum AP
	dynamic monitored delta_ap: Value //Limits window for arterial entry pressure AP. Distance to min and max AP
	//dynamic controlled delta_ap_contr: Integer //Limits window for arterial entry pressure AP. Distance to min and max AP
	dynamic monitored perc_delta_tmp: Value //Limits window for TMP in % of actual value
	//dynamic controlled perc_delta_tmp_contr: Integer //Limits window for TMP in % of actual value
	dynamic monitored limits_tmp: Boolean //True--> monitoring the TMP at the dialyzer
	dynamic controlled limits_tmp_contr: Boolean // True--> monitoring the TMP at the dialyzer
	dynamic monitored max_tmp: Value //Max TMP value
	//dynamic controlled max_tmp_contr: Integer //Max TMP value
	dynamic monitored extended_tmp_limit: Boolean //Extended TMP limit range
	dynamic controlled extended_tmp_limit_contr: Boolean //Extended TMP limit range
	dynamic monitored max_bep: Value // Maximum BEP pressure
	//dynamic controlled max_bep_contr: Integer //Maximum BEP pressure
	dynamic monitored stop_time_mins_h: Value //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	//dynamic controlled stop_time_mins_h_contr: Integer //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	dynamic monitored stop_time_hrs_h: Value //The heparin pump is switched off by set time prior to the end of the therapy: hours
	//dynamic controlled stop_time_hrs_h_contr: Integer //The heparin pump is switched off by set time prior to the end of the therapy: hours
	dynamic monitored bolus_volume_h: Value //Bolus volume for a bolus administration during dialysis
	//dynamic controlled bolus_volume_h_contr: Integer //Bolus volume for a bolus administration during dialysis
	dynamic monitored rate_h: Value //Continuous heparin rate over the entire duration of heparin administration
	//dynamic controlled rate_h_contr: Integer //Continuous heparin rate over the entire duration of heparin administration
	dynamic monitored activation_h: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic controlled activation_h_contr: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic monitored syringe_type: SyringeType //Syringe type used during treatment
	dynamic controlled syringe_type_contr: SyringeType //Syringe type used during treatment
	
	//static min_ap: Integer //mmHg
	//static max_ap: Integer //mmHg
	//static min_vp: Integer //mmHg
	//static max_vp: Integer //mmHg
	
	//dynamic monitored min_ap: Integer //Minimum AP
	//dynamic controlled min_ap_contr: Integer //Minimum AP

	//dynamic monitored min_vp: Integer //Minimum VP
	//dynamic controlled min_vp_contr: Integer //Minimum VP
	//dynamic monitored max_vp: Integer //Maximum VP
	//dynamic controlled max_vp_contr: Integer //Maximum VP
	
	//Rinse dialyzer
	dynamic monitored dialyzer_connected: Boolean //True--> dialyzer is connected to the machine
	dynamic monitored arterial_chamber_filled: Boolean //True--> arterial chamber is filled nearly half full
	dynamic monitored venous_chamber_fill: Boolean //True--> venous chamber is filled up to approx. 1 cm from upper edge
	dynamic monitored dialyzer_filled: Boolean //True--> dialyzer is filled
	dynamic monitored stop_DF_preparation: Boolean //True--> stop DF preparation
	dynamic controlled preparing_DF: Boolean //True--> preparing DF
		
	//Patient connection
	dynamic monitored art_connected: Boolean//True--> patient connected arterially during patient connection
	dynamic monitored ven_connected: Boolean //True--> patient connected venously during patient connection
	dynamic monitored start_stop_button: Boolean //True--> the start stop button is pushed
	dynamic monitored blood_flow_conn: Value //Blood flow set during patient connection
	dynamic monitored blood_on_VRD: Boolean //True--> blood is detected on VRD
	dynamic controlled bicarbonate_status: Boolean //True--> bicarbonate is running
	dynamic monitored conn_infuse_set_volume: Value //volume transported by bp during patient connection
	
	//Therapy running
	dynamic controlled heparin_running: Boolean //True--> heparin pump is on
	dynamic controlled ap_limits_set: Boolean //True--> the ap limits are set after 10 seconds of last blood pump activation
	dynamic controlled vp_limits_set: Boolean //True--> the vp limits are set after 10 seconds of last blood pump activation
	dynamic monitored ap_limit_low: Value //Current lower limit ap
	//dynamic controlled ap_limit_low_contr:  //Current lower limit ap
	dynamic monitored vp_limit_low: Value //Current lower limit vp
	//dynamic controlled vp_limit_low_contr: Integer //Current lower limit vp
	dynamic monitored current_ap: Value //Current AP
	dynamic monitored current_vp: Value //Current VP
	dynamic monitored ap_limit_up: Value //Current upper limit ap
	//dynamic controlled ap_limit_up_contr: Integer //Current upper limit ap
	dynamic monitored vp_limit_up: Value //Current upper limit vp
	//dynamic controlled vp_limit_up_contr: Integer //Current upper limit vp
	dynamic monitored treatment_min_uf_rate: Boolean //True --> Treatment at minimum UF rate 
	dynamic controlled treatment_min_uf_rate_contr: Boolean //True --> Treatment at minimum UF rate 
	//dynamic controlled current_uf_rate: Integer //Current UF rate
	dynamic monitored interrupt_dialysis: Boolean //True --> interrupt dialysis
	dynamic monitored start_arterial_bolus: Boolean //True --> start arterial bolus infusion
	dynamic monitored saline_solution_conn: Boolean //True --> saline solution for arterial bolus is connected
	dynamic monitored art_bolus_volume: Value //Arterial bolus volume infusion
	//dynamic controlled art_bolus_volume_contr: Integer //Arterial bolus volume infusion
	dynamic monitored current_art_bolus_volume: Value //Current arterial bolus volume infusion

	//Reinfusion
	dynamic monitored art_removed: Boolean //True--> patient arterially is removed
	dynamic monitored saline_conn: Boolean //True--> saline solution is connected
	dynamic monitored saline_on_VRD: Boolean //True--> saline is detected on VRD 
	dynamic monitored new_saline_reinfusion: Boolean //True--> new saline reinfusion is started
	dynamic controlled empty_dialyzer: Boolean //True--> dialyzer is emptied
	dynamic monitored volume_saline_inf_400: Boolean //Volume of saline solution infused is equal to 400
	dynamic monitored ven_removed: Boolean //True--> patient venously is removed
	dynamic monitored dialyzer_drained: Boolean //True --> Dialyzer is drained
	dynamic monitored cartridge_emtpy: Boolean //True --> Cartridge is emptied
	
	derived machine_status_der: MachineState
	derived errorePerBYPASS: Boolean
	derived bp_status_der: BPStatus
	derived errorePERbpStatus: Boolean
	derived bicarbonate_status_der: Boolean
	derived errorePERbicarbonate: Boolean
	derived signal_lamp_der: SignalLamps
	derived errorExist: Boolean //True if exist at least one error
	derived alarmExist: Boolean  //True if exist at least one alarm
	derived err_patient_conn: Boolean //True if exist error during patient conn 
	derived error_bp: Boolean //True if exist blood pump error
	derived error_rein_press: Boolean //True if exist error during reinfusion phase
	derived error_therapy: Boolean //True if exist error during therapy
	
	dynamic controlled error: ErrorAlarmType -> Boolean //True--> error true
	dynamic controlled alarm: ErrorAlarmType -> Boolean //True--> alarm signal on
	dynamic monitored reset_alarm: Boolean // True--> reset alarm
	
	
	//Requirements
	dynamic controlled art_connected_contr: Boolean //True--> patient connected arterially during patient connection
	dynamic controlled ven_connected_contr: Boolean //True--> patient connected venously during patient connection
	dynamic monitored detect_bicarbonate: Boolean // True --> bicarbonate detected False --> acetate or acid detected instead of bicarbonate
	dynamic monitored current_air_vol: Value //current air volume
	dynamic monitored currentSAD: Value //current SAD measured
	dynamic monitored error_SAD_resolved: Boolean // True --> SAD error resolved
	dynamic monitored current_temp: Value // Current temperature
	dynamic monitored update_blood_flow: Boolean //True--> update blood flow
	dynamic controlled update_th_time: Boolean //True--> update therapy time
	dynamic monitored detected_blood_flow: Boolean // True --> no flow dtected by blood pump
	dynamic controlled blood_flow_error_bf_less: Boolean // True --> set blood flow due to less BP flow
	dynamic controlled th_time_error_bf_less: Boolean // True --> set new therapy time due to less BP flow 
	dynamic monitored current_bp_flow: Value //Current blood flow
	dynamic monitored bp_rotates_back: Boolean // True --> blood pump rotates backwards
	dynamic monitored current_fill_blood_vol: Value //current blood volume 
	dynamic monitored reverse_dir_heparin: Boolean // True --> reverse direction of heparin pump
	dynamic monitored current_UF_rate: Value //current UF rate
	dynamic monitored uf_dir_backwards: Boolean // True --> uf pump rotates backwards
	dynamic monitored current_UF_volume: Value // current UF volume
	dynamic monitored reset_error_arterial_bolus: Boolean // True--> reset error arterial bolus
	dynamic monitored reset_error_bp_no_flow: Boolean // True--> reset error blood pump no flow detected for 120 sec
	dynamic monitored reset_error_bp_rot_dir: Boolean // True -->  reset error blood pump rotates backwards
	dynamic monitored reset_error_pression: Boolean // True -->  reset error pression AP-VP
	dynamic monitored error_heparin_resolve: Boolean // True --> heparin error is resolved
	dynamic monitored error_UF_rate_resolved: Boolean // True --> error due to UF rate is resolved
	dynamic monitored error_UF_dir_resolved: Boolean // True --> error due to UF direction backwards is resolved
	dynamic controlled bf_err_ap_low: Boolean // True --> set blood flow after AP low limit error during therapy
	dynamic controlled reset_err_pres_ap_low: Boolean // True --> set pressure flow after AP low limit error during therapy
	dynamic controlled bf_err_ap_low_conn: Boolean // True --> set blood flow after AP low limit error during connection phase
	dynamic controlled reset_err_pres_ap_low_conn: Boolean // True --> set pressure flow after AP low limit error during connection phase
	derived df_flow_state: Boolean //True --> DF is on, False --> DF is off
		
	
definitions:
// DOMAIN DEFINITIONS

// FUNCTION DEFINITIONS

	//function min_ap = -400
	//function max_ap = 400
	//function min_vp = -100
	//function max_vp = 400
		
	function errorePerBYPASS = error(TEMP_HIGH) or error(TEMP_LOW) or error(DF_PREP) or error(UF_DIR) or alarm(UF_VOLUME_ERR)
	function errorePERbpStatus = error(BP_ROTATION_DIR) or error(BP_NO_FLOW) or error(INIT_VP_UP) or error(INIT_AP_UP) or error(INIT_VP_LOW) or error(INIT_AP_LOW) or error(CONN_VP_UP) or error(CONN_VP_LOW) or error(CONN_AP_LOW) or error(REIN_VP_UP) or error(REIN_AP_LOW) or error(FILL_BLOOD_VOL) or error(HEPARIN_DIR) or error(SAD_ERR) or error(ARTERIAL_BOLUS)
	function errorePERbicarbonate = error(UF_RATE) or alarm(UF_BYPASS)

	function machine_status_der =
		if errorePerBYPASS then
			BYPASS
		else
			machine_state
		endif
		
	function bp_status_der =
		if errorePERbpStatus then
			STOP
		else
			bp_status
		endif
	
	function bicarbonate_status_der =
		if errorePERbicarbonate then
			false
		else
			bicarbonate_status
		endif
		
	function errorExist = 
		(exist $t in ErrorAlarmType with error($t) = true)
		
	function alarmExist = 
		(exist $t in ErrorAlarmType with alarm($t) = true)
	
	function signal_lamp_der =
		if errorExist then
			RED
		else
			signal_lamp
		endif	
	
	function df_flow_state =
		if machine_status_der = BYPASS then
			false
		else
			true
		endif
			
	function err_patient_conn = 
		(error(CONN_VP_UP) = true or error(CONN_VP_LOW)= true or error(CONN_AP_LOW)= true or error(FILL_BLOOD_VOL) = true or error(SAD_ERR) = true)
	
	function error_bp =
		(error(BP_NO_FLOW) = true or error(BP_ROTATION_DIR) = true or error(BP_LESS_FLOW) = true)
	
	function error_rein_press = 
		(error(REIN_VP_UP) = true or error(REIN_AP_LOW) = true or error(SAD_ERR) = true)
		
	function error_therapy =
		(error(INIT_VP_UP) = true or error(INIT_VP_LOW) = true or error(INIT_AP_UP) = true or error(INIT_AP_LOW) = true or error(SAD_ERR) = true or error(HEPARIN_DIR) = true)


// RULE DEFINITIONS
	rule r_check_df_prep = 
		if (error(DF_PREP) = false and preparing_DF = true) then
			if (detect_bicarbonate = false) then
				par
					error(DF_PREP) := true
					alarm(DF_PREP) := true
				endpar
			endif
		endif
	
	rule r_set_err_SAD = 
		par
			error(SAD_ERR) := true
			alarm(SAD_ERR) := true
		endpar
	
	rule r_check_SAD = 
		if (error(SAD_ERR) = false) then
			if (passed1Msec = true) then
				if (currentSAD != PERMITTED and current_air_vol != PERMITTED) then
				 	r_set_err_SAD[]
				 endif
			endif
		endif		
	
	rule r_check_temp_high = 
		if (error(TEMP_HIGH) = false) then
			if (current_temp = HIGH) then	
				par
					error(TEMP_HIGH) := true
					alarm(TEMP_HIGH) := true
				endpar
			endif
		endif	
			
	rule r_error_df_prep =
		if (error (DF_PREP) = true and alarm (DF_PREP) = false) then
			if (detect_bicarbonate = true) then
				error(DF_PREP) := false
			endif
		endif
	
	rule r_error_SAD = 
		if (error (SAD_ERR) = true and alarm (SAD_ERR) = false) then
			if (error_SAD_resolved = true) then
				error (SAD_ERR) := false
			endif
		endif
	
	rule r_error_temp_high =
		if (error (TEMP_HIGH) = true and alarm (TEMP_HIGH) = false) then
			if (current_temp = PERMITTED) then	
				error (TEMP_HIGH) := false
			endif
		endif
	
	rule r_error_temp_low =
		if (error (TEMP_LOW) = true and alarm (TEMP_LOW) = false) then
			if (current_temp = TOOLOW) then	
				error (TEMP_LOW) := false
			endif
		endif
	
	rule r_error_arterial_bolus =
		if (error (ARTERIAL_BOLUS) = true and alarm (ARTERIAL_BOLUS) = false) then
			if (reset_error_arterial_bolus = true) then	
				par
					error (ARTERIAL_BOLUS) := false
					arterialBolusPhase := WAIT_SOLUTION 
				endpar
			endif
		endif
	
	rule r_error_BP_no_flow = 
		if (error (BP_NO_FLOW) = true and alarm (BP_NO_FLOW) = false) then
			if (reset_error_bp_no_flow = true) then
				error (BP_NO_FLOW) := false
			endif
		endif
	
	rule r_insert_new_therapy_time ($errTime in Boolean)=
		if ((therapy_time_hrs = PERMITTED) and (therapy_time_mins = PERMITTED)) then
			$errTime := false
		endif
	
	rule r_error_AP_low_BPflow_time ($err in ErrorAlarmType, $errTime in Boolean, $errBPFlow in Boolean) =
		if (error ($err) = true and alarm ($err) = false) then
			par
				if ($errBPFlow = true) then
					if (blood_flow_conn = PERMITTED) then
						$errBPFlow := false
					endif
				endif
				if ($errTime = true) then
					r_insert_new_therapy_time[$errTime] 
				endif
				if ($errTime = false and $errTime = false) then
					error ($err) := false
				endif
			endpar
		endif
	
	rule r_error_BP_less_flow =
		r_error_AP_low_BPflow_time[BP_LESS_FLOW, th_time_error_bf_less, blood_flow_error_bf_less]

	rule r_error_BP_rotation_dir = 
		if (error (BP_ROTATION_DIR) = true and alarm (BP_ROTATION_DIR) = false) then
			if (reset_error_bp_rot_dir = true) then
				error (BP_ROTATION_DIR) := false
			endif
		endif
	
	rule r_error_pressure_limit ($err in ErrorAlarmType, $curr in Value)=
		if (error ($err) = true and alarm ($err) = false) then
			if ($curr = PERMITTED) then
				error ($err) := false
			endif
		endif
	
	rule r_error_vp_up_limit =
		r_error_pressure_limit[INIT_VP_UP, current_vp]
		
	rule r_error_vp_low_limit =
		r_error_pressure_limit[INIT_VP_LOW, current_vp]
	
	rule r_error_ap_up_limit =
		r_error_pressure_limit[INIT_AP_UP, current_ap]
			
	rule r_error_ap_low_limit =
		r_error_AP_low_BPflow_time[INIT_AP_LOW, bf_err_ap_low, reset_err_pres_ap_low]
	
	rule r_error_update_blood_flow ($err in ErrorAlarmType) =
		if (error ($err) = true and alarm ($err) = false) then
			if (blood_flow_conn = PERMITTED) then
				error($err) := false
			endif
		endif
	
	macro rule r_error_conn_vp_up_limit =
		r_error_update_blood_flow[CONN_VP_UP]
		
	macro rule r_error_conn_vp_low_limit =
		r_error_pressure_limit[CONN_VP_LOW, current_vp]
	
	macro rule r_error_conn_ap_low_limit =
		r_error_pressure_limit[CONN_AP_LOW, current_ap]
	
	macro rule r_error_fill_blood_vol =
		r_error_update_blood_flow[FILL_BLOOD_VOL] 
	
	rule r_error_heparin = 
		if (error (HEPARIN_DIR) = true and alarm (HEPARIN_DIR) = false) then
			if (error_heparin_resolve = true) then
				par
					error(HEPARIN_DIR) := false
					heparin_running := true 
				endpar
			endif
		endif
		
	rule r_error_UF_rate = 
		if (error(UF_RATE) = true and alarm (UF_RATE) = false) then
			if (error_UF_rate_resolved = true) then
				error(UF_RATE) := true
			endif
		endif
	
	rule r_error_UF_dir = 
		if (error(UF_DIR) = true and alarm (UF_DIR) = false) then
			if (error_UF_dir_resolved = true) then
				error(UF_DIR) := true
			endif
		endif
											
	macro rule r_error =
		par
			r_error_temp_high[]
			r_error_temp_low[]
			r_error_arterial_bolus[]
			r_error_BP_no_flow[]
			r_error_BP_less_flow[] 
			r_error_BP_rotation_dir[]
			r_error_vp_up_limit[] 
			r_error_vp_low_limit[] 
			r_error_ap_up_limit[] 
			r_error_ap_low_limit[] 
			r_error_conn_vp_up_limit[] 
			r_error_conn_vp_low_limit[] 
			r_error_conn_ap_low_limit[]
			r_error_fill_blood_vol[] 
			r_error_df_prep[]
			r_error_heparin[] 
			r_error_SAD[] 
			r_error_UF_rate[] 
			r_error_UF_dir[]
		endpar
		
	macro rule r_alarm =
		if (reset_alarm = true) then
			forall $alarmon in ErrorAlarmType with alarm($alarmon)= true do
				alarm($alarmon) := false
		endif
	
	
	macro rule r_check_preparation = 
		par
			r_check_df_prep[]
			if (prepPhaseMode = TUBING_SYSTEM) then
				r_check_SAD[]
			endif
			if (prepPhaseMode = TUBING_SYSTEM and prepPhaseMode = RINSE_DIALYZER) then
				r_check_temp_high[]
			endif
		endpar
		
	macro rule r_run_automatic_test =
		if (auto_test_end = true) then
			par	
				prepPhaseMode := CONNECT_CONCENTRATE
				signal_lamp := GREEN
			endpar
		endif
		
	macro rule r_connect_concentrate =
		if (conn_concentrate = true) then
			par
				prepPhaseMode := SET_RINSING_PARAM
				preparing_DF := true
			endpar
		endif
	
	rule r_insert_param ($nextparam in RinsingParam, $mon in Value) = 
		if ($mon = PERMITTED) then
			rinsingParam := $nextparam 
		endif
	
	rule r_insert_param ($nextparam in PrepPhaseMode, $mon in Value) = 
		if ($mon = PERMITTED) then
			prepPhaseMode := $nextparam 
		endif
	
	rule r_insert_param ($nextparam in TreatmentParam, $mon in Value) = 
		if ($mon = PERMITTED) then
			treatmentParam := $nextparam 
		endif
	
	rule r_insert_time ($nextparam in TreatmentParam, $mon1 in Value, $mon2 in Value) = 
		if ($mon1 = PERMITTED and $mon2 = PERMITTED) then
			treatmentParam := $nextparam 
		endif
		
	rule r_insert_param ($nextparam in TreatmentParam, $mon in Concentrate, $contr in Concentrate) = 
		par 
			$contr := $mon 
			treatmentParam := $nextparam 
		endpar

	rule r_insert_param ($nextparam in TreatmentParam, $mon in Boolean, $contr in Boolean) = 
		par 
			$contr := $mon 
			treatmentParam := $nextparam 
		endpar
	
	rule r_insert_param ($nextparam in PrepPhaseMode, $mon in SyringeType, $contr in SyringeType) = 
		par 
			$contr := $mon 
			prepPhaseMode := $nextparam 
		endpar
	
	rule r_insert_param ($nextparam in PatientPhase, $mon in Value) = 
		if ($mon = PERMITTED) then
			patientPhase := $nextparam 
		endif
		
	macro rule r_set_filling_bp_rate =
		r_insert_param [FILLING_BP_VOLUME, fill_bp_rate] 		
		
	macro rule r_set_filling_bp_volume =
		r_insert_param [BP_RATE_RINSING, fill_bp_volume] 	
	
	macro rule r_set_bp_rate_rinsing =
		r_insert_param [DF_FLOW_RINSING, bp_rate_rinsing] 
	
	macro rule r_set_df_flow_rinsing =
		r_insert_param [TIME_RINSING, df_flow_rinsing]
		
	macro rule r_set_time_rinsing =
		r_insert_param [UF_RATE_RINSING, time_rinsing]

	macro rule r_set_uf_rate_rinsing =
		r_insert_param [UF_VOLUME_RINSING, uf_rate_rinsing]
		
	macro rule r_set_uf_volume_rinsing =
		r_insert_param [TUBING_SYSTEM, uf_volume_rinsing]
		
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
		if (av_tubes_connected = true) then
			tubingSystemPhase := CONNECT_ALL_COMP
		endif
		
	rule r_connect_all_comp =
		if (all_components_connected = true) then
			tubingSystemPhase := SET_SALINE_LEVELS
		endif
		
	rule r_set_saline_levels =
		if (saline_levels_set = true) then
			tubingSystemPhase := INSERT_BLOODLINES
		endif
	
	rule r_insert_bloodlines =
		if (blood_line_insert = true) then
			tubingSystemPhase := PRIMING
		endif
	
	rule r_priming =
		if (bp_status_der = STOP) then
			bp_status := START 
		else
			if (bp_fill_fluid = true and bp_rate_rinsing_150 = true ) then
				par
					bp_status := STOP 
					tubingSystemPhase := CONNECT_AV_ENDS
				endpar
			endif
		endif
	
	rule r_connect_av_ends =
		if (av_ends_connected = true) then
			prepPhaseMode := PREPARE_HEPARIN
		endif
	
	macro rule r_install_tubing_system =
		par
			if (tubingSystemPhase = CONNECT_AV_TUBES) then
				r_connect_av_tubes[] 
			endif
			if (tubingSystemPhase = CONNECT_ALL_COMP) then
				r_connect_all_comp[] 
			endif
			if (tubingSystemPhase = SET_SALINE_LEVELS) then
				r_set_saline_levels[] 
			endif
			if (tubingSystemPhase = INSERT_BLOODLINES) then
				r_insert_bloodlines[] 
			endif
			if (tubingSystemPhase = PRIMING) then
				r_priming[] 
			endif
			if (tubingSystemPhase = CONNECT_AV_ENDS) then
				r_connect_av_ends[] 
			endif
		endpar

		
	macro rule r_prepare_heparin =
		if (heparin_prepared = true) then
			prepPhaseMode := SET_TREAT_PARAM
		endif
		
	macro rule r_set_blood_conductivity =
		r_insert_param [BIC_AC, blood_conductivity] 		
		
	macro rule r_set_bic_ac =
		r_insert_param [BIC_CONDUCTIVITY, bic_ac, bic_ac_contr] 		

	macro rule r_set_bic_conductivity =
		r_insert_param [DF_TEMP, bic_conductivity] 		

	macro rule r_set_df_temp =
		r_insert_param [DF_FLOW, df_temp] 
	
	macro rule r_set_df_flow =
		r_insert_param [UF_VOLUME, df_flow] 
		
	macro rule r_set_uf_volume =
		r_insert_param [THERAPY_TIME, uf_volume]
	
	macro rule r_set_therapy_time =
		r_insert_time [MIN_UF_RATE, therapy_time_hrs, therapy_time_mins]
	
	macro rule r_set_min_uf_rate =
		r_insert_param [MAX_UF_RATE, min_uf_rate]
	
	macro rule r_set_max_uf_rate =
		r_insert_param [MAX_AP, max_uf_rate]
	
	//macro rule r_set_min_ap =
	//	r_insert_param [-400, 400, MAX_AP, min_ap, min_ap_contr]
	
	macro rule r_set_max_ap =
		r_insert_param [DELTA_AP, max_ap]
	
	//macro rule r_set_min_vp =
	//	r_insert_param [-100, 400, MAX_VP, min_vp, min_vp_contr]
	
	//macro rule r_set_max_vp =
	//	r_insert_param [-100, 400, DELTA_AP, max_vp, max_vp_contr]
	
	macro rule r_set_delta_ap =
		r_insert_param [PERC_DELTA_TMP, delta_ap]
	
	macro rule r_set_perc_delta_tmp =
		r_insert_param [LIMITS_TMP, perc_delta_tmp]
	
	macro rule r_set_limits_tmp = 
		r_insert_param [MAX_TMP, limits_tmp, limits_tmp_contr]
	
	macro rule r_set_max_tmp = 
		r_insert_param [EXTENDED_TMP, max_tmp]
	
	macro rule r_set_extended_tmp =
		r_insert_param [MAX_BEP, extended_tmp_limit, extended_tmp_limit_contr]
	
	macro rule r_set_max_bep = 
		r_insert_param [STOP_TIME_H, max_bep]
	
	macro rule r_set_h_stop_time =
		r_insert_time [BOLUS_VOLUME_H, stop_time_hrs_h, stop_time_mins_h]
	
	macro rule r_set_h_bolus_volume =
		r_insert_param [RATE_H, bolus_volume_h]
	
	macro rule r_set_h_rate =
		r_insert_param [ACTIVATION_H, rate_h]
	
	macro rule r_set_h_activation =
		r_insert_param [SYRINGE_TYPE, activation_h, activation_h_contr]
	
	macro rule r_set_syringe_type =
		r_insert_param [RINSE_DIALYZER, syringe_type, syringe_type_contr] 	
				
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
			//if (treatmentParam = MIN_AP) then
			//	r_set_min_ap[] 
			//endif
			if (treatmentParam = MAX_AP) then
				r_set_max_ap[] 
			endif
			//if (treatmentParam = MIN_VP) then
			//	r_set_min_vp[] 
			//endif
			//if (treatmentParam = MAX_VP) then
			//	r_set_max_vp[] 
			//endif
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
		if (error(DF_PREP) = false) then
			if (preparing_DF = true) then
				if (stop_DF_preparation = true) then
					preparing_DF := false
				endif
			else
				if (dialyzer_connected = true) then 
					rinsePhase := FILL_ART_CHAMBER
				endif
			endif
		endif
	
	rule r_fill_art_chamber =
		if (bp_status_der = STOP) then
			bp_status := START
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
				bp_status := STOP
				phase := INITIATION
				//initPhaseMode := CONNECT_PATIENT
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

	macro rule r_run_preparation =
		par
			r_check_preparation[]
			if (prepPhaseMode = AUTO_TEST) then 
				r_run_automatic_test[] 
			endif
			if (prepPhaseMode = CONNECT_CONCENTRATE) then 
				r_connect_concentrate[]
			endif
			if (prepPhaseMode = SET_RINSING_PARAM) then 
				r_set_rinsing_param[] 
			endif
			if (prepPhaseMode = TUBING_SYSTEM) then 
				r_install_tubing_system[] 
			endif
			if (prepPhaseMode = PREPARE_HEPARIN) then
				r_prepare_heparin[]
			endif
			if (prepPhaseMode = SET_TREAT_PARAM) then 
				r_set_treatment_param[]
			endif
			if (prepPhaseMode = RINSE_DIALYZER) then
				r_rinse_dialyzer[]
			endif
		endpar
	
	rule r_check_temp_low = 
		if (error(TEMP_LOW) = false) then
			if (current_temp = TOOLOW) then	
				par
					error(TEMP_LOW) := true
					alarm(TEMP_LOW) := true
				endpar
			endif
		endif
	
	rule r_check_BP_rotation_dir =
		if (error(BP_ROTATION_DIR) = false) then
			if (bp_rotates_back = true) then
				par
					error(BP_ROTATION_DIR) := true
					alarm(BP_ROTATION_DIR) := true
				endpar
			endif
		endif
	
	rule r_check_bp_less_flow = 
		if (error(BP_LESS_FLOW) = false) then
			if (machine_status_der = MAIN_FLOW) then	
				if (current_bp_flow = TOOLOW) then	//IS LESS THAN 70% OF THE SET FLOW
					par
						alarm(BP_LESS_FLOW) := true
						error(BP_LESS_FLOW) := true
						blood_flow_error_bf_less := true
						th_time_error_bf_less := true
					endpar
				endif
			endif
		endif
	
	rule r_check_bp_no_flow_err = 
		if (error(BP_NO_FLOW) = false) then
			if ((detected_blood_flow = false) and (passed120Sec = true)) then	
				par
					error(BP_NO_FLOW) := true
					alarm(BP_NO_FLOW) := true
				endpar
			endif
		endif
	
	rule r_check_initiation_phase = 
		par
			r_check_temp_high[]
			r_check_temp_low[] 		
			if (bp_status_der = START) then
				par
					r_check_BP_rotation_dir[] 
					r_check_bp_less_flow[] 
					r_check_bp_no_flow_err[] 
					r_check_SAD[]
				endpar
			endif
		endpar
	
	rule r_conn_arterially =
		if (art_connected = true) then
			par
				patientPhase := START_BP
				art_connected_contr := true
			endpar
		endif
	
	rule r_start_bp =
		if (start_stop_button = true) then
			if (error_bp = false) then
				par
					patientPhase := BLOOD_FLOW
					bp_status := START
					//r_start_BP_therapy[]
				endpar
			endif
		endif
		
	macro rule r_set_blood_flow =
		if (ven_connected_contr = false) then
			r_insert_param[FILL_TUBING, blood_flow_conn]
		else
			r_insert_param [END_CONN, blood_flow_conn]
		endif	
		
	rule r_fill_tubing =
		if (error_bp = false) then
			if (blood_on_VRD = true or conn_infuse_set_volume = PERMITTED) then //S-4
				par
					patientPhase := CONN_VEN
					bp_status := STOP
				endpar
			endif
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
			machine_state := MAIN_FLOW
			bicarbonate_status := true //is running
			signal_lamp := GREEN
			initPhaseMode := THERAPY_RUNNING
		endpar
		
	rule r_patient_connection = 
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
		
	rule r_check_conn_rein_press_up_limit ($err in ErrorAlarmType, $currentP in Value, $passedSec in Boolean)= 
		if (error($err) = false) then
			if ($currentP = HIGH and ($passedSec) = true) then
				par
					error($err) := true
					alarm($err) := true
				endpar
			endif
		endif	
		
	macro rule r_check_conn_vp_up_limit = 
		r_check_conn_rein_press_up_limit[CONN_VP_UP, current_vp, passed3Sec]
		
	rule r_check_conn_rein_press_low_limit ($err in ErrorAlarmType, $currentP in Value, $passedSec in Boolean) = 
		if (error($err) = false) then
			if ($currentP = TOOLOW and ($passedSec) = true) then
				par
					error($err) := true
					alarm($err) := true
				endpar
			endif
		endif

	rule r_check_conn_rein_press_low_limit ($err in ErrorAlarmType, $currentP in Value, $passedSec in Boolean, $errbf in Boolean, $errpress in Boolean) = 
		if (error($err) = false) then
			if ($currentP = TOOLOW and ($passedSec) = true) then
				par
					error($err) := true
					alarm($err) := true
					$errbf := true
					$errpress := true
				endpar
			endif
		endif
		
	macro rule r_check_conn_vp_low_limit = 
		r_check_conn_rein_press_low_limit[CONN_VP_LOW, current_vp, passed3Sec]

	macro rule r_check_conn_ap_low_limit = 
		r_check_conn_rein_press_low_limit[CONN_AP_LOW, current_ap, passed1Sec, bf_err_ap_low_conn, reset_err_pres_ap_low_conn]
		
	rule r_check_fill_blood_vol = 
		if (error(FILL_BLOOD_VOL) = false) then
			if (current_fill_blood_vol = HIGH) then
				par
					error(FILL_BLOOD_VOL) := true
					alarm(FILL_BLOOD_VOL) := true
					//bp_status := STOP
				endpar
			endif
		endif	
		
	rule r_check_patient = 
		if (bp_status_der = START) then
			par
				r_check_conn_vp_up_limit[] 	
				r_check_conn_vp_low_limit[] 
				r_check_conn_ap_low_limit[]
				r_check_fill_blood_vol[]
			endpar
		endif
		
	rule r_initiate_patient = 
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
	
	rule r_check_heparin = 
		if (error(HEPARIN_DIR) = false) then
			if (reverse_dir_heparin = true) then
				par
					error(HEPARIN_DIR) := true
					alarm(HEPARIN_DIR) := true
					heparin_running := false 
					//bp_status := STOP
				endpar
			endif
		endif
		
	macro rule r_run_heparin = 
		if (heparin_running = true) then
			par
				r_check_heparin[]
				if (time_heparin = true) then
					heparin_running := false
				endif
			endpar
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
		if (art_bolus_volume = PERMITTED) then
			par
				bp_status := STOP
				arterialBolusPhase := CONNECT_SOLUTION
				//check_ap := false
			endpar		
		endif	
			
	rule r_connect_solution =
		if (saline_solution_conn = true) then
			par
				arterialBolusPhase := RUNNING_SOLUTION
				bp_status := START
				ap_limits_set := false
				vp_limits_set := false
				//check_ap := true	
				//r_start_BP_therapy[] 
			endpar
		endif		
	
	rule r_running_solution =
		if (alarm(ARTERIAL_BOLUS_END) = false) then
			if (current_art_bolus_volume = PERMITTED) then
				par
					arterialBolusPhase := WAIT_SOLUTION
					alarm(ARTERIAL_BOLUS_END) := true
				endpar
			endif
		endif
	
	rule r_check_arterial_bolus =  
		if (current_art_bolus_volume = HIGH) then //ml
			par
				alarm(ARTERIAL_BOLUS) := true
				error(ARTERIAL_BOLUS) := true
			endpar
		endif
	
	rule r_run_arterial_bolus = 
		if (error(ARTERIAL_BOLUS) = false) then
			par
				r_running_solution[] 
				r_check_arterial_bolus[] 
			endpar
		endif
		
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
	//	let ($time2 = time_therapy) in
		if (time_therapy = true) then
			therapyPhase := THERAPY_END
		endif
		//endlet
	
	rule r_update_therapy_time =
		//par
			if ((therapy_time_hrs = PERMITTED) and (therapy_time_mins = PERMITTED)) then
				update_th_time := false
			endif
		//endpar
			
	rule r_update_blood_flow =
		par
			if (update_blood_flow = true) then
				if (blood_flow_conn = PERMITTED) then
					update_th_time := true
				endif
			endif
			if (update_th_time = true and update_blood_flow = false) then
				r_update_therapy_time[]
			endif
		endpar	
	
	rule r_set_ap_vp_limits =
	if (arterialBolusPhase != CONNECT_SOLUTION) then
		par
			if (ap_limits_set = false) then
				if ((ap_limit_low = PERMITTED) and (ap_limit_up = PERMITTED)) then
					ap_limits_set := true
				endif
			endif
			if (vp_limits_set = false) then
				if ((vp_limit_low = PERMITTED) and (vp_limit_up = PERMITTED)) then
					vp_limits_set := true
				endif
			endif
		endpar
	endif
	
	rule r_update_ap_limits =
		if (current_ap = PERMITTED)then
			skip
		endif
		
	rule r_update_vp_limits =
		if (current_vp = PERMITTED) then
			skip
		endif
	
	macro rule r_monitor_ap_vp_limits = 
		if (ap_limits_set = true and vp_limits_set = true) then
			par
				r_update_ap_limits[] 
				r_update_vp_limits[] 
			endpar
		else
			if (passed10Sec = true) then
				r_set_ap_vp_limits[] 
			endif
		endif
					
	rule r_check_init_press_low_limit ($press in ErrorAlarmType, $curr in Value)= 
		if (error($press) = false) then
			if ($curr = TOOLOW) then
				par
					error($press) := true
					alarm($press) := true
				endpar
			endif
		endif				
					
	rule r_check_init_press_up_limit ($press in ErrorAlarmType, $curr in Value)= 
		if (error($press) = false) then
			if ($curr = HIGH) then
				par
					error($press) := true
					alarm($press) := true
				endpar
			endif
		endif
	
	macro rule r_check_init_vp_up_limit =
		r_check_init_press_up_limit[INIT_VP_UP, current_vp] 
	
	macro rule r_check_init_ap_up_limit =
		r_check_init_press_up_limit[INIT_AP_UP, current_ap] 
		
	macro rule r_check_init_vp_low_limit =
		r_check_init_press_low_limit[INIT_VP_LOW, current_vp] 
	
	macro rule r_check_init_ap_low_limit =
		r_check_init_press_low_limit[INIT_AP_LOW, current_ap] 
	
	rule r_check_UF_rate = 
		if (error(UF_RATE) = false) then
			if (current_UF_rate = HIGH) then
				par
					error(UF_RATE) := true
					alarm(UF_RATE) := true
				endpar
			endif
		endif
	
	rule r_check_UF_dir = 
		if (error(UF_DIR) = false) then
			if (uf_dir_backwards = true) then
				par
					error(UF_DIR) := true
					alarm(UF_DIR) := true
				endpar
			endif
		endif

	rule r_check_UF_volume = 
		if (alarm(UF_VOLUME_ERR) = false) then
			if (current_UF_volume = HIGH) then
				alarm(UF_VOLUME_ERR) := true
			endif
		endif
		
	rule r_check_UF_bypass = 
		if (alarm(UF_BYPASS) = false) then
			if (machine_status_der = BYPASS) then
				alarm(UF_BYPASS) := true
			endif
		endif	
		
		macro rule r_check_therapy_exec = 
		par
			if (bp_status_der = START) then
				if (ap_limits_set = true and vp_limits_set = true) then
					par
						r_check_init_vp_up_limit[] 
						r_check_init_ap_up_limit[] 
						r_check_init_vp_low_limit[] 
						r_check_init_ap_low_limit[]
					endpar
				endif 
			endif
			if (bicarbonate_status_der = true) then
				par
					r_check_UF_rate[] 
					r_check_UF_dir[] 
					r_check_UF_volume[] 
					r_check_UF_bypass[]
				endpar
			endif
		endpar
		
	macro rule r_therapy_exec =
		par
			if (error_therapy = false) then
				par
					r_run_heparin[] 
					r_monitor_ap_vp_limits[] 
					r_treatment_min_UF[] 
					if (error_bp = false) then
						r_arterial_bolus[]
					endif
					r_therapy_end_time[] 
					r_update_blood_flow[] 
					r_check_therapy_exec[]
				endpar
			endif
			r_interrupt[] 
		endpar	
													
	macro rule r_therapy_end =
		phase := ENDING

		
	macro rule r_run_therapy =
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
		
	macro rule r_run_initiation =
		par
			r_check_initiation_phase[]
			if (initPhaseMode = CONNECT_PATIENT) then
				r_initiate_patient[]
			endif
			if (initPhaseMode = THERAPY_RUNNING) then
				r_run_therapy[] 
			endif
		endpar	
	
	
	


	rule r_remove_arterially =
		if (art_removed = true) then
			par
				reinfusionPhase := CONN_SALINE
				art_connected_contr := false
			endpar
		endif
		
	macro rule r_connect_saline =
		if (saline_conn = true) then
			reinfusionPhase := START_SALINE_INF
		endif
	
	macro rule r_start_saline_inf =
		par
			//if (bp_status_update = false) then
				bp_status := START // azione della macchina
			//endif
			reinfusionPhase := RUN_SALINE_INF
		endpar
		
	macro rule r_run_saline_inf =
		if (saline_on_VRD = true) then
			par
				reinfusionPhase := CHOOSE_NEXT_REINF_STEP
				bp_status := STOP
			endpar
		endif
	
	macro rule r_choose_next_reinf_step =
		if (new_saline_reinfusion = true) then
			reinfusionPhase := START_SALINE_REIN
		else
			reinfusionPhase := REMOVE_VEN
		endif
		
	macro rule r_start_saline_reinf =
		par
			//if (bp_status_update = false) then
				bp_status := START // azione della macchina
			//endif
			reinfusionPhase := RUN_SALINE_REIN
		endpar
	
	macro rule r_run_saline_reinf = 
		if ((volume_saline_inf_400 = true) or (passed5Min = true)) then
			par
				reinfusionPhase := CHOOSE_NEXT_REINF_STEP
				bp_status := STOP
			endpar
		endif
		
	macro rule r_remove_venously =
		if (ven_removed = true) then
			par
				endingPhaseMode := DRAIN_DIALYZER
				ven_connected_contr := false
			endpar
		endif
		
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
	
	macro rule r_check_rein_vp_up_limit = 
		r_check_conn_rein_press_up_limit[REIN_VP_UP, current_vp, passed3Sec] //>+350
			
	macro rule r_check_rein_ap_low_limit = 
		r_check_conn_rein_press_low_limit[REIN_AP_LOW, current_ap, passed1Sec] //< -350
		
	macro rule r_check_reinfusion =
		if (bp_status_der = START) then
			par
				r_check_rein_vp_up_limit[]
				r_check_rein_ap_low_limit[]
				r_check_SAD[]
			endpar
		endif
		
	macro rule r_reinfusion = 
		if (error_rein_press = false) then
			par
				r_run_reinfusion[]
				r_check_reinfusion[]
			endpar
		endif
	
	macro rule r_drain_dialyzer =
		if (dialyzer_drained = true) then
			par
				endingPhaseMode := EMPTY_CARTRIDGE
				empty_dialyzer := true
			endpar
		endif
		
	macro rule r_empty_cartridge =
		if (cartridge_emtpy = true) then
			endingPhaseMode := THERAPY_OVERVIEW
		endif
		
	macro rule r_therapy_overview =
		skip
		
	macro rule r_run_ending=
		par
			if (endingPhaseMode = REINFUSION) then
				r_reinfusion[] 
			endif
			if (endingPhaseMode = DRAIN_DIALYZER) then
				r_drain_dialyzer[]
			endif
			if (endingPhaseMode = EMPTY_CARTRIDGE) then
				r_empty_cartridge[]
			endif
			if (endingPhaseMode = THERAPY_OVERVIEW) then
				r_therapy_overview[]
			endif
		endpar
		
		
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
		par
			r_run_dialysis[] 
			if (errorExist = true) then
				r_error[] 
			endif
			if (alarmExist = true) then
				r_alarm[]
			endif
		endpar
		
		
		
// INITIAL STATE
default init s0:
	function phase = PREPARATION
	function prepPhaseMode = AUTO_TEST
	function rinsingParam = FILLING_BP_RATE
	function treatmentParam = BLOOD_CONDUCTIVITY
	function rinsePhase = CONNECT_DIALYZER
	function tubingSystemPhase = CONNECT_AV_TUBES
	function bp_status = STOP
	function signal_lamp= YELLOW
	function patientPhase = CONN_ART
	function arterialBolusPhase = WAIT_SOLUTION
	function therapyPhase = START_HEPARIN
	function ven_connected_contr = false
	function art_connected_contr = false	
	function machine_state = BYPASS
	function preparing_DF = false
	function error($t in ErrorAlarmType) = false
	function alarm($t in ErrorAlarmType) = false
	function initPhaseMode = CONNECT_PATIENT
	function limits_tmp_contr = false
	function extended_tmp_limit_contr = false
	function activation_h_contr = false
	function syringe_type_contr = ST10
	function bicarbonate_status = false
	function heparin_running = false
	function ap_limits_set = false
	function vp_limits_set = false
	function treatment_min_uf_rate_contr = false
	function blood_flow_error_bf_less = false
	function th_time_error_bf_less = false
	function endingPhaseMode = REINFUSION
	function reinfusionPhase = REMOVE_ART