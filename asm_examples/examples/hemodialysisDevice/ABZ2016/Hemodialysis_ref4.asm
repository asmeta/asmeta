asm Hemodialysis_ref4

import ../../../STDL/StandardLibrary

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
	enum domain BPStatus = {START | STOP}
	
	enum domain SignalLamps = {GREEN | YELLOW | RED}
	enum domain MachineState = {BYPASS | MAIN_FLOW}
	enum domain Concentrate = {BICARBONATE | ACETATE}
	enum domain ArterialBolusPhase = {WAIT_SOLUTION | SET_ARTERIAL_BOLUS_VOLUME | CONNECT_SOLUTION | RUNNING_SOLUTION }
	enum domain ErrorAlarmType = {UF_BYPASS | UF_VOLUME_ERR | UF_DIR | UF_RATE | SAD_ERR | HEPARIN_DIR | DF_PREP | FILL_BLOOD_VOL | REIN_VP_UP | REIN_AP_LOW | CONN_VP_UP | CONN_VP_LOW | CONN_AP_LOW | INIT_VP_UP | INIT_VP_LOW | INIT_AP_UP | INIT_AP_LOW | BP_NO_FLOW | BP_LESS_FLOW | BP_ROTATION_DIR | TEMP_HIGH | TEMP_LOW | ARTERIAL_BOLUS | ARTERIAL_BOLUS_END}
	enum domain SyringeType = {ST10 | ST20 | ST30}
	
	
// FUNCTIONS
	//dynamic controlled error: Boolean //True --> Error during therapy
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled mode: Mode //Modes in which the system can be
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingPhase: TubingPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	dynamic controlled patientPhase: PatientPhase //Phase during patient connection
	dynamic controlled therapyPhase: TherapyPhase //Phase during therapy
	dynamic controlled reinfusionPhase: ReinfusionPhase //Reinfusion phase
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic controlled signal_lamp: SignalLamps //Signal lamp on the monitor
	dynamic controlled arterialBolusPhase: ArterialBolusPhase //Phase during arterial bolus
	
	dynamic monitored auto_test_end: Boolean //True --> automatic test terminate succesfully False --> otherwise
	dynamic monitored conn_concentrate: Boolean //True --> concentrate is connected to machine
	dynamic monitored heparin_prepared: Boolean //True --> heparin pump is prepared
	dynamic monitored dialyzer_drained: Boolean //True --> Dialyzer is drained
	dynamic monitored cartridge_emtpy: Boolean //True --> Cartridge is emptied

	
	dynamic monitored passedMin: Integer -> Boolean //True --> time t is passed : passed(t) minutes
	dynamic monitored passedSec: Integer -> Boolean //True --> time t is passed : passed(t) seconds
	dynamic monitored passedMsec: Integer -> Boolean //True --> time t is passed : passed(t) milliseconds
	
	//Rinsing parameter
	dynamic monitored fill_bp_rate: Integer //The rate with which the blood side is filled or rinsed
	dynamic controlled fill_bp_rate_contr: Integer //The rate with which the blood side is filled or rinsed
	dynamic monitored fill_bp_volume: Integer //The BP stops after it has rinsed the blood side using the set volume
	dynamic controlled fill_bp_volume_contr: Integer //The BP stops after it has rinsed the blood side using the set volume
	dynamic monitored bp_rate_rinsing: Integer //BP rate for rinsing program
	dynamic controlled bp_rate_rinsing_contr: Integer //BP rate for rinsing program
	dynamic monitored df_flow_rinsing: Integer //DF flow rate for rinsing program
	dynamic controlled df_flow_rinsing_contr: Integer //DF flow rate for rinsing program
	dynamic monitored time_rinsing: Integer //Duration of adjusted rinsing program
	dynamic controlled time_rinsing_contr: Integer //Duration of adjusted rinsing program
	dynamic monitored uf_rate_rinsing: Integer //UF rate when rinsing with a physiological saline solution
	dynamic controlled uf_rate_rinsing_contr: Integer //UF rate when rinsing with a physiological saline solution
	dynamic monitored uf_volume_rinsing: Integer //UF volume when rinsing with a physiological saline solution
	dynamic controlled uf_volume_rinsing_contr: Integer //UF volume when rinsing with a physiological saline solution
	
	//tubing system
	dynamic monitored av_tubes_conn: Boolean // True --> A/V tubes are connected
	dynamic monitored all_comp_conn: Boolean // True --> all components are connected
	dynamic monitored saline_levels_set: Boolean // True --> saline levels are set
	dynamic monitored blood_line_insert: Boolean // True --> blood line are inserted
	dynamic controlled bp_status: BPStatus // BP status Start-->BP is running
	dynamic monitored bp_fill_fluid: Boolean // True --> BP is full with fluid
	dynamic monitored av_ends_conn: Boolean // True --> A/V ends are connected

	//treatment parameters
	dynamic monitored blood_conductivity: Integer //Blood conductivity: the rate with which the blood side is filled or rinsed
	dynamic controlled blood_conductivity_contr: Integer //Blood conductivity: the rate with which the blood side is filled or rinsed
	dynamic monitored bic_ac: Concentrate  //Selection of the concentrate used during therapy
	dynamic controlled bic_ac_contr: Concentrate  //Selection of the concentrate used during therapy
	dynamic monitored bic_conductivity: Integer //Bicarbonate conductivity
	dynamic controlled bic_conductivity_contr: Integer //Bicarbonate conductivity
	dynamic monitored df_temp: Integer //DF temperature
	dynamic controlled df_temp_contr: Integer //DF temperature
	dynamic monitored df_flow: Integer //DF flow
	dynamic controlled df_flow_contr: Integer //DF flow
	dynamic monitored uf_volume: Integer //UF volume
	dynamic controlled uf_volume_contr: Integer //UF volume
	dynamic monitored therapy_time_mins: Integer //Therapy time minutes
	dynamic controlled therapy_time_mins_contr: Integer //Therapy time minutes
	dynamic monitored therapy_time_hrs: Integer //Therapy time hours
	dynamic controlled therapy_time_hrs_contr: Integer //Therapy time hours
	dynamic monitored min_uf_rate: Integer //Minimun UF rate
	dynamic controlled min_uf_rate_contr: Integer //Minimun UF rate
	dynamic monitored max_uf_rate: Integer //Maximum UF rate
	dynamic controlled max_uf_rate_contr: Integer //Maximum UF rate
	dynamic monitored min_ap: Integer //Minimum AP
	dynamic controlled min_ap_contr: Integer //Minimum AP
	dynamic monitored max_ap: Integer //Maximum AP
	dynamic controlled max_ap_contr: Integer //Maximum AP
	dynamic monitored min_vp: Integer //Minimum VP
	dynamic controlled min_vp_contr: Integer //Minimum VP
	dynamic monitored max_vp: Integer //Maximum VP
	dynamic controlled max_vp_contr: Integer //Maximum VP
	dynamic monitored delta_ap: Integer //Limits window for arterial entry pressure AP. Distance to min and max AP
	dynamic controlled delta_ap_contr: Integer //Limits window for arterial entry pressure AP. Distance to min and max AP
	dynamic monitored perc_delta_tmp: Integer //Limits window for TMP in % of actual value
	dynamic controlled perc_delta_tmp_contr: Integer //Limits window for TMP in % of actual value
	dynamic monitored limits_tmp: Boolean //True--> monitoring the TMP at the dialyzer
	dynamic controlled limits_tmp_contr: Boolean // True--> monitoring the TMP at the dialyzer
	dynamic monitored max_tmp: Integer //Max TMP value
	dynamic controlled max_tmp_contr: Integer //Max TMP value
	dynamic monitored extended_tmp_limit: Boolean //Extended TMP limit range
	dynamic controlled extended_tmp_limit_contr: Boolean //Extended TMP limit range
	dynamic monitored max_bep: Integer // Maximum BEP pressure
	dynamic controlled max_bep_contr: Integer //Maximum BEP pressure
	dynamic monitored stop_time_mins_h: Integer //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	dynamic controlled stop_time_mins_h_contr: Integer //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	dynamic monitored stop_time_hrs_h: Integer //The heparin pump is switched off by set time prior to the end of the therapy: hours
	dynamic controlled stop_time_hrs_h_contr: Integer //The heparin pump is switched off by set time prior to the end of the therapy: hours
	dynamic monitored bolus_volume_h: Integer //Bolus volume for a bolus administration during dialysis
	dynamic controlled bolus_volume_h_contr: Integer //Bolus volume for a bolus administration during dialysis
	dynamic monitored rate_h: Integer //Continuous heparin rate over the entire duration of heparin administration
	dynamic controlled rate_h_contr: Integer //Continuous heparin rate over the entire duration of heparin administration
	dynamic monitored activation_h: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic controlled activation_h_contr: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic monitored syringe_type: SyringeType //Syringe type used during treatment
	dynamic controlled syringe_type_contr: SyringeType //Syringe type used during treatment
	
	//Rinse dialyzer
	dynamic monitored dialyzer_connected: Boolean //True--> dialyzer is connected to the machine
	dynamic monitored arterial_chamber_filled: Boolean //True--> arterial chamber is filled nearly half full
	dynamic monitored venous_chamber_fill: Boolean //True--> venous chamber is filled up to approx. 1 cm from upper edge
	dynamic monitored dialyzer_filled: Boolean //True--> dialyzer is filled
	
	//Connection patient
	dynamic monitored art_connected: Boolean //True--> patient connected arterially during patient connection
	dynamic controlled art_connected_contr: Boolean//True--> patient connected arterially during patient connection
	dynamic monitored blood_flow_conn: Integer //Blood flow set during patient connection
	dynamic controlled blood_flow_conn_contr: Integer //Blood flow set during patient connection
	dynamic monitored blood_on_VRD: Boolean //True--> blood is detected on VRD
	dynamic monitored ven_connected: Boolean //True--> patient connected venously during patient connection
	dynamic controlled ven_connected_contr: Boolean //True--> patient connected venously during patient connection
	dynamic controlled bicarbonate_status: Boolean //True--> bicarbonate is running
	dynamic monitored  current_conn_infuse_set_volume: Integer //volume transported by bp during patient connection
	
	//Therapy running
	dynamic controlled heparin_running: Boolean //True--> heparin pump is on
	dynamic controlled ap_limits_set: Boolean //True--> the ap limits are set after 10 seconds of last blood pump activation
	dynamic controlled vp_limits_set: Boolean //True--> the vp limits are set after 10 seconds of last blood pump activation
	dynamic monitored ap_limit_low: Integer //Current lower limit ap
	dynamic controlled ap_limit_low_contr: Integer //Current lower limit ap
	dynamic monitored vp_limit_low: Integer //Current lower limit vp
	dynamic controlled vp_limit_low_contr: Integer //Current lower limit vp
	dynamic monitored current_ap: Integer //Current AP
	dynamic monitored current_vp: Integer //Current VP
	dynamic monitored ap_limit_up: Integer //Current upper limit ap
	dynamic controlled ap_limit_up_contr: Integer //Current upper limit ap
	dynamic monitored vp_limit_up: Integer //Current upper limit vp
	dynamic controlled vp_limit_up_contr: Integer //Current upper limit vp
	dynamic monitored treatment_min_uf_rate: Boolean //True --> Treatment at minimum UF rate 
	dynamic controlled treatment_min_uf_rate_contr: Boolean //True --> Treatment at minimum UF rate 
	dynamic controlled current_uf_rate: Integer //Current UF rate
	dynamic monitored interrupt_dialysis: Boolean //True --> interrupt dialysis
	dynamic monitored start_arterial_bolus: Boolean //True --> start arterial bolus infusion
	dynamic monitored saline_solution_conn: Boolean //True --> saline solution for arterial bolus is connected
	dynamic monitored arterial_infusion_complete: Boolean //True -->  arterial infusio is complete
	dynamic monitored art_bolus_volume: Integer //Arterial bolus volume infusion
	dynamic controlled art_bolus_volume_contr: Integer //Arterial bolus volume infusion
	dynamic monitored current_art_bolus_volume: Integer //Current arterial bolus volume infusion

	//Reinfusion
	dynamic monitored art_removed: Boolean //True--> patient arterially is removed
	dynamic monitored saline_conn: Boolean //True--> saline solution is connected
	dynamic monitored saline_on_VRD: Boolean //True--> saline is detected on VRD 
	dynamic monitored new_saline_reinfusion: Boolean //True--> new saline reinfusion is started
	dynamic monitored current_volume_saline_inf: Integer //Volume of saline solution infused
	dynamic monitored ven_removed: Boolean //True--> patient venously is removed
	
	//Alarm Error
	derived errorExist: Boolean //True if exist at least one error
	derived alarmExist: Boolean  //True if exist at least one alarm
	derived err_patient_conn: Boolean //True if exist error during patient conn 
	derived error_bp: Boolean //True if exist blood pump error
	derived error_rein_press: Boolean //True if exist error during reinfusion phase
	derived error_therapy: Boolean //True if exist error during therapy
	
	dynamic controlled error: ErrorAlarmType -> Boolean //True--> error true
	dynamic controlled alarm: ErrorAlarmType -> Boolean //True--> alarm signal on
	dynamic monitored reset_alarm: Boolean // True--> reset alarm
	dynamic monitored reset_error_arterial_bolus: Boolean // True--> reset error arterial bolus
	dynamic monitored reset_error_bp_no_flow: Boolean // True--> reset error blood pump no flow detected for 120 sec
	dynamic monitored reset_error_bp_rot_dir: Boolean // True -->  reset error blood pump rotates backwards
	dynamic controlled bf_err_vp_low: Boolean // True --> set blood flow after VP low limit error
	dynamic controlled reset_err_pres_vp_low: Boolean // True --> set pressure flow after VP low limit error
	dynamic controlled bf_err_vp_up: Boolean // True --> set blood flow after VP up limit error
	dynamic controlled reset_err_pres_vp_up: Boolean // True --> set pressure flow after VP up limit error
	dynamic controlled bf_err_ap_up: Boolean // True --> set blood flow after AP up limit error
	dynamic controlled reset_err_pres_ap_up: Boolean // True --> set pressure flow after AP up limit error
	dynamic controlled bf_err_ap_low: Boolean // True --> set blood flow after AP low limit error
	dynamic controlled reset_err_pres_ap_low: Boolean // True --> set pressure flow after AP low limit error	
	dynamic controlled bf_err_vp_low_conn: Boolean // True --> set blood flow after VP low limit error during connection phase
	dynamic controlled reset_err_pres_vp_low_conn: Boolean // True --> set pressure flow after VP low limit error during connection phase
	dynamic controlled bf_err_ap_low_conn: Boolean // True --> set blood flow after AP low limit error during connection phase
	dynamic controlled reset_err_pres_ap_low_conn: Boolean // True --> set pressure flow after AP low limit error during connection phase
	dynamic monitored error_bicarbonate_resolve: Boolean // True --> bicarbonate error is resolved
	dynamic monitored error_heparin_resolve: Boolean // True --> heparin error is resolved
	dynamic monitored error_SAD_resolved: Boolean // True --> SAD error resolved
	dynamic monitored error_UF_rate_resolved: Boolean // True --> error due to UF rate is resolved
	dynamic monitored error_UF_dir_resolved: Boolean // True --> error due to UF direction backwards is resolved
	
	//Requirements
	dynamic monitored update_blood_flow: Boolean //True--> update blood flow
	dynamic monitored update_th_time: Boolean //True--> update therapy time
	dynamic monitored current_temp: Integer // Current temperature
	dynamic monitored detected_blood_flow: Boolean // True --> no flow dtected by blood pump
	dynamic controlled blood_flow_error_bf_less: Boolean // True --> set blood flow due to less BP flow
	dynamic controlled th_time_error_bf_less: Boolean // True --> set new therapy time due to less BP flow 
	dynamic monitored current_bp_flow: Integer //Current blood flow
	dynamic monitored bp_rotates_back: Boolean // True --> blood pump rotates backwards
	dynamic monitored current_fill_blood_vol: Integer //current blood volume 
	dynamic monitored detect_bicarbonate: Boolean // True --> bicarbonate detected False --> acetate or acid detected instead of bicarbonate
	dynamic monitored reverse_dir_heparin: Boolean // True --> reverse direction of heparin pump
	dynamic monitored currentSAD: Integer //current SAD measured
	dynamic monitored current_air_vol: Integer //current air volume measured
	dynamic monitored current_UF_rate: Integer //current UF rate
	dynamic monitored uf_dir_backwards: Boolean // True --> uf pump rotates backwards
	dynamic monitored current_UF_volume: Integer // current UF volume
		
		
	//functions
	derived time_heparin: Integer
	derived time_therapy: Integer
	
	dynamic controlled check_ap: Boolean
	
	
	derived machine_status_der: MachineState
	derived errorePerBYPASS: Boolean
	derived bp_status_der: BPStatus
	derived errorePERbpStatus: Boolean
	derived bicarbonate_status_der: Boolean
	derived errorePERbicarbonate: Boolean
	
	
	
definitions:
// DOMAIN DEFINITIONS
	
	
// FUNCTION DEFINITIONS
	
	function errorePerBYPASS = error(TEMP_HIGH) or error(TEMP_LOW) or error(DF_PREP) or error(UF_DIR) or alarm(UF_VOLUME_ERR)
	function errorePERbpStatus = error(BP_ROTATION_DIR) or error(BP_NO_FLOW) or error(INIT_VP_UP) or error(INIT_AP_UP) or error(INIT_VP_LOW) or error(INIT_AP_LOW) or error(CONN_VP_UP) or error(CONN_VP_LOW) or error(CONN_AP_LOW) or error(REIN_VP_UP) or error(REIN_AP_LOW) or error(FILL_BLOOD_VOL) or error(HEPARIN_DIR) or error(SAD_ERR) or error(ARTERIAL_BOLUS)
	function errorePERbicarbonate = error(DF_PREP) or error(UF_RATE) or alarm(UF_BYPASS)

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
		(exist $t in ErrorAlarmType with error($t))
		
	function alarmExist = 
		(exist $t in ErrorAlarmType with alarm($t))
		
	function err_patient_conn = 
		(error(CONN_VP_UP) or error(CONN_VP_LOW)= true or error(CONN_AP_LOW)= true or error(FILL_BLOOD_VOL) or error(SAD_ERR))
	
	function error_bp =
		(error(BP_NO_FLOW) or error(BP_ROTATION_DIR))
	
	function error_rein_press = 
		(error(REIN_VP_UP) or error(REIN_AP_LOW) or error(SAD_ERR))
		
	function error_therapy =
		(error(INIT_VP_UP) or error(INIT_VP_LOW) or error(INIT_AP_UP) or error(INIT_AP_LOW) or error(SAD_ERR))
	
	function time_heparin =
		((therapy_time_mins_contr + therapy_time_hrs_contr *60)-(stop_time_mins_h_contr + stop_time_hrs_h_contr*60))
	
	function time_therapy =
		 (therapy_time_mins_contr + therapy_time_hrs_contr *60)
	
// RULE DEFINITIONS

	rule r_auto_test =
		if (auto_test_end) then
			mode := CONNECT_CONCENTRATE
		endif
		
	rule r_connect_concentrate =
		if (conn_concentrate) then
			mode := SET_RINSING_PARAM
		endif
	
		rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in RinsingParam, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				rinsingParam := $nextparam 
			endpar
		endif
		
	rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in Mode, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				mode := $nextparam 
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
	
	rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in PatientPhase, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				patientPhase := $nextparam 
			endpar
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
	
	rule r_insert_param ($nextparam in Mode, $mon in SyringeType, $contr in SyringeType) = 
		par 
			$contr := $mon 
			mode := $nextparam 
		endpar
		
	rule r_insert_therapy_time ($nextparam in TreatmentParam) =
		par
			if ((therapy_time_hrs < 10) and (therapy_time_hrs > 0)) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					if ((therapy_time_mins >= 0) and (therapy_time_mins <=59)) then
						par
							therapy_time_mins_contr := therapy_time_mins
							treatmentParam := $nextparam
						endpar
					endif
				endpar
			endif
			if (therapy_time_hrs = 10) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					therapy_time_mins_contr := 0
					treatmentParam := $nextparam
				endpar
			endif
			if (therapy_time_hrs = 0) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					if ((therapy_time_mins >= 10) and (therapy_time_mins <=59)) then
						par
							therapy_time_mins_contr := therapy_time_mins
							treatmentParam := $nextparam
						endpar
					endif
				endpar
			endif
		endpar

	rule r_insert_new_therapy_time =
		par
			if ((therapy_time_hrs < 10) and (therapy_time_hrs > 0)) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					if ((therapy_time_mins >= 0) and (therapy_time_mins <=59)) then
						par
							therapy_time_mins_contr := therapy_time_mins
							th_time_error_bf_less := false
						endpar
					endif
				endpar
			endif
			if (therapy_time_hrs = 10) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					therapy_time_mins_contr := 0
					th_time_error_bf_less := false
				endpar
			endif
			if (therapy_time_hrs = 0) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					if ((therapy_time_mins >= 10) and (therapy_time_mins <=59)) then
						par
							therapy_time_mins_contr := therapy_time_mins
							th_time_error_bf_less := false
						endpar
					endif
				endpar
			endif
		endpar
		
	rule r_insert_heparin_time ($nextparam in TreatmentParam) =
		par
			if ((stop_time_hrs_h < 10) and (stop_time_hrs_h >= 0)) then
				par
					stop_time_hrs_h_contr := stop_time_hrs_h
					if ((stop_time_mins_h >= 0) and (stop_time_mins_h <=59)) then
						par
							stop_time_mins_h_contr := stop_time_mins_h
							if ((stop_time_hrs_h * 60 + stop_time_mins_h) <= (therapy_time_hrs * 60 + therapy_time_mins)) then
								treatmentParam := $nextparam
							endif
						endpar
					endif
				endpar
			endif
			if (stop_time_hrs_h = 10) then
				par
					stop_time_hrs_h_contr := stop_time_hrs_h
					stop_time_mins_h_contr := 0
					if ((stop_time_hrs_h * 60 + stop_time_mins_h) <= (therapy_time_hrs * 60 + therapy_time_mins)) then
						treatmentParam := $nextparam 
					endif
				endpar
			endif
		endpar	
		
	/*rule r_start_BP_therapy = 
		par
			bp_status := START
			ap_limits_set := false
			vp_limits_set := false	
		endpar*/
	
	rule r_check_temp_high = 
		if (error(TEMP_HIGH) = false) then
			if (current_temp > 41) then	
				par
					error(TEMP_HIGH) := true
					alarm(TEMP_HIGH) := true
					//machine_state := BYPASS
				endpar
			endif
		endif
		
	rule r_check_temp_low = 
		if (error(TEMP_LOW) = false) then
			if (current_temp < 33) then	
				par
					error(TEMP_LOW) := true
					alarm(TEMP_LOW) := true
					//machine_state := BYPASS
				endpar
			endif
		endif
		
	rule r_check_BP_rotation_dir =
		if (error(BP_ROTATION_DIR) = false) then
			if (bp_rotates_back) then
				par
					error(BP_ROTATION_DIR) := true
					alarm(BP_ROTATION_DIR) := true
					//bp_status := STOP
				endpar
			endif
		endif
		
	rule r_check_bp_less_flow = 
		if (error(BP_LESS_FLOW) = false) then
			if (machine_status_der = MAIN_FLOW) then	
				if ((current_bp_flow * 100) < (blood_flow_conn_contr * 70)) then	
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
			if ((detected_blood_flow = false) and (passedSec(120))) then	
				par
					error(BP_NO_FLOW) := true
					alarm(BP_NO_FLOW) := true
					//bp_status := STOP
				endpar
			endif
		endif

	
	rule r_check_init_press_low_limit ($press in ErrorAlarmType, $curr in Integer, $setpress in Integer, $errbf in Boolean, $errpress in Boolean)= 
		if (error($press) = false) then
			if ($curr < $setpress) then
				par
					error($press) := true
					alarm($press) := true
					//bp_status := STOP
					$errbf := true
					$errpress := true
				endpar
			endif
		endif
	
	rule r_check_init_press_up_limit ($press in ErrorAlarmType, $curr in Integer, $setpress in Integer, $errbf in Boolean, $errpress in Boolean)= 
		if (error($press) = false) then
			if ($curr > $setpress) then
				par
					error($press) := true
					alarm($press) := true
					//bp_status := STOP
					$errbf := true
					$errpress := true
				endpar
			endif
		endif
		
	macro rule r_check_init_vp_up_limit =
		r_check_init_press_up_limit[INIT_VP_UP, current_vp, vp_limit_up_contr, bf_err_vp_up, reset_err_pres_vp_up] 
	
	macro rule r_check_init_ap_up_limit =
		r_check_init_press_up_limit[INIT_AP_UP, current_ap, ap_limit_up_contr, bf_err_ap_up, reset_err_pres_ap_up] 
		
	macro rule r_check_init_vp_low_limit =
		r_check_init_press_low_limit[INIT_VP_LOW, current_vp, vp_limit_low_contr, bf_err_vp_low, reset_err_pres_vp_low] 
	
	macro rule r_check_init_ap_low_limit =
		r_check_init_press_low_limit[INIT_AP_LOW, current_ap, ap_limit_low_contr, bf_err_ap_low, reset_err_pres_ap_low] 
	
	rule r_check_conn_rein_press_up_limit ($err in ErrorAlarmType, $currentP in Integer, $uplimit in Integer, $sec in Integer)= 
		if (error($err) = false) then
			if ($currentP > $uplimit and passedSec($sec)) then
				par
					error($err) := true
					alarm($err) := true
					//bp_status := STOP
				endpar
			endif
		endif

	macro rule r_check_conn_vp_up_limit = 
		r_check_conn_rein_press_up_limit[CONN_VP_UP, current_vp, 400, 3]
			
	rule r_check_conn_rein_press_low_limit ($err in ErrorAlarmType, $currentP in Integer, $lowlimit in Integer, $sec in Integer) = 
		if (error($err) = false) then
			if ($currentP < $lowlimit and passedSec($sec)) then
				par
					error($err) := true
					alarm($err) := true
					//bp_status := STOP
				endpar
			endif
		endif

	rule r_check_conn_rein_press_low_limit ($err in ErrorAlarmType, $currentP in Integer, $lowlimit in Integer, $sec in Integer, $errbf in Boolean, $errpress in Boolean) = 
		if (error($err) = false) then
			if ($currentP < $lowlimit and passedSec($sec)) then
				par
					error($err) := true
					alarm($err) := true
					//bp_status := STOP
					$errbf := true
					$errpress := true
				endpar
			endif
		endif
		
	macro rule r_check_conn_vp_low_limit = 
		r_check_conn_rein_press_low_limit[CONN_VP_LOW, current_vp, min_vp_contr, 3, bf_err_vp_low_conn, reset_err_pres_vp_low_conn]

	macro rule r_check_conn_ap_low_limit = 
		r_check_conn_rein_press_low_limit[CONN_AP_LOW, current_ap, min_ap_contr, 1, bf_err_ap_low_conn, reset_err_pres_ap_low_conn]
	
	macro rule r_check_rein_vp_up_limit = 
		r_check_conn_rein_press_up_limit[REIN_VP_UP, current_vp, 350, 3]
			
	macro rule r_check_rein_ap_low_limit = 
		r_check_conn_rein_press_low_limit[REIN_AP_LOW, current_ap, -350, 1]
	
	rule r_check_fill_blood_vol = 
		if (error(FILL_BLOOD_VOL) = false) then
			if (current_fill_blood_vol > fill_bp_volume_contr) then
				par
					error(FILL_BLOOD_VOL) := true
					alarm(FILL_BLOOD_VOL) := true
					//bp_status := STOP
				endpar
			endif
		endif

	rule r_check_df_prep = 
		if (error(DF_PREP) = false) then
			if (detect_bicarbonate = false) then
				par
					error(DF_PREP) := true
					alarm(DF_PREP) := true
					//machine_state := BYPASS
					//bicarbonate_status := false
				endpar
			endif
		endif
		
	rule r_check_heparin = 
		if (error(HEPARIN_DIR) = false) then
			if (reverse_dir_heparin) then
				par
					error(HEPARIN_DIR) := true
					alarm(HEPARIN_DIR) := true
					heparin_running := false 
					//bp_status := STOP
				endpar
			endif
		endif
	
	rule r_set_err_SAD = 
		par
			error(SAD_ERR) := true
			alarm(SAD_ERR) := true
			//bp_status := STOP
		endpar
	
	rule r_check_SAD = 
		if (error(SAD_ERR) = false) then
			if (passedMsec(1)) then
				par
					if (currentSAD > 1200) then
					 	r_set_err_SAD[]
					 endif
					 if (currentSAD >= 0 and currentSAD < 200) then
					 	if (current_air_vol >= 200) then
					 		r_set_err_SAD[]
					 	endif
					 endif
					 if (currentSAD >= 200 and currentSAD < 400) then
					 	if (current_air_vol >= 300) then
					 		r_set_err_SAD[]
					 	endif
					 endif
					 if (currentSAD >= 400 and currentSAD <= 1200) then
					 	if (current_air_vol >= 500) then
					 		r_set_err_SAD[]
					 	endif
					 endif
				endpar
			endif
		endif	
	
	rule r_check_UF_rate = 
		if (error(UF_RATE) = false) then
			if (current_UF_rate > max_uf_rate_contr) then
				par
					error(UF_RATE) := true
					alarm(UF_RATE) := true
					//bicarbonate_status := false
				endpar
			endif
		endif

	rule r_check_UF_dir = 
		if (error(UF_DIR) = false) then
			if (uf_dir_backwards) then
				par
					error(UF_DIR) := true
					alarm(UF_DIR) := true
					//machine_state := BYPASS
				endpar
			endif
		endif

	rule r_check_UF_volume = 
		if (alarm(UF_VOLUME_ERR) = false) then
			if (current_UF_volume > uf_volume_contr) then
				//par
					alarm(UF_VOLUME_ERR) := true
					//machine_state := BYPASS
				//endpar
			endif
		endif
	
	rule r_check_UF_bypass = 
		if (alarm(UF_BYPASS) = false) then
			if (machine_status_der = BYPASS) then
				//par
					alarm(UF_BYPASS) := true
					//bicarbonate_status := false
				//endpar
			endif
		endif
							   
	rule r_error_temp_high =
		if (error (TEMP_HIGH) and alarm (TEMP_HIGH) = false) then
			if (current_temp <= 41) then	
				//par
					error (TEMP_HIGH) := false
				//	machine_state := MAIN_FLOW
				//endpar
			endif
		endif
	
	rule r_error_temp_low =
		if (error (TEMP_LOW) and alarm (TEMP_LOW) = false) then
			if (current_temp >= 33) then	
				//par
					error (TEMP_LOW) := false
				//	machine_state := MAIN_FLOW
				//endpar
			endif
		endif
	
	rule r_error_arterial_bolus =
		if (error (ARTERIAL_BOLUS) and alarm (ARTERIAL_BOLUS) = false) then
			if (reset_error_arterial_bolus) then	
				par
					error (ARTERIAL_BOLUS) := false
					arterialBolusPhase := WAIT_SOLUTION
				//	r_start_BP_therapy[] 
				endpar
			endif
		endif
	
	rule r_error_BP_no_flow = 
		if (error (BP_NO_FLOW) and alarm (BP_NO_FLOW) = false) then
			if (reset_error_bp_no_flow) then
				//par
					error (BP_NO_FLOW) := false
					/*if (mode = THERAPY_RUNNING) then
						r_start_BP_therapy[]
					else
						bp_status := START
					endif
				endpar*/
			endif
		endif
	
	rule r_error_BP_less_flow =
		if (error (BP_LESS_FLOW) and alarm (BP_LESS_FLOW) = false) then
			par
				if (blood_flow_error_bf_less) then
					if (blood_flow_conn >= 50 and blood_flow_conn <=600) then
						par
							blood_flow_conn_contr := blood_flow_conn
							blood_flow_error_bf_less := false
						endpar
					endif
				endif
				if (th_time_error_bf_less) then
					r_insert_new_therapy_time[] 
				endif
				if (blood_flow_error_bf_less = false and th_time_error_bf_less = false) then
					error (BP_LESS_FLOW) := false
				endif
			endpar
		endif

	rule r_error_BP_rotation_dir = 
		if (error (BP_ROTATION_DIR) and alarm (BP_ROTATION_DIR) = false) then
			if (reset_error_bp_rot_dir) then
				//par
					error (BP_ROTATION_DIR) := false
					/*if (mode = THERAPY_RUNNING) then
						r_start_BP_therapy[]
					else
						bp_status := START
					endif
				endpar*/
			endif
		endif
	
	rule r_error_pressure_limit ($err in ErrorAlarmType, $bfErr in Boolean, $resetP in Boolean, $inf in Integer, $sup in Integer, $monmin in Integer, $contrmin in Integer, $monmax in Integer, $contrmax in Integer)=
		if (error ($err) and alarm ($err) = false) then
			par
				if ($bfErr) then
					if (blood_flow_conn >= 50 and blood_flow_conn <=600) then
						par
							blood_flow_conn_contr := blood_flow_conn
							$bfErr := false
						endpar
					endif
				endif
				if ($resetP) then
					r_insert_param_press [$inf, $sup, $monmin, $contrmin, $monmax, $contrmax, $resetP]
				endif
				if ($bfErr = false and $resetP = false) then	
					//par
						error ($err) := false
					//	bp_status := START
					//endpar
				endif
			endpar
		endif
	
	rule r_error_vp_up_limit =
		r_error_pressure_limit[INIT_VP_UP, bf_err_vp_up, reset_err_pres_vp_up, -100, 400, min_vp, min_vp_contr, max_vp, max_vp_contr]
		
	rule r_error_vp_low_limit =
		r_error_pressure_limit[INIT_VP_LOW, bf_err_vp_low, reset_err_pres_vp_low, -100, 400, min_vp, min_vp_contr, max_vp, max_vp_contr]
	
	rule r_error_ap_up_limit =
		r_error_pressure_limit[INIT_AP_UP, bf_err_ap_up, reset_err_pres_ap_up, -400, 400, min_ap, min_ap_contr, max_ap, max_ap_contr]
	
	rule r_error_ap_low_limit =
		r_error_pressure_limit[INIT_AP_LOW, bf_err_ap_low, reset_err_pres_ap_low, -400, 400, min_ap, min_ap_contr, max_ap, max_ap_contr]
	
	rule r_error_update_blood_flow ($err in ErrorAlarmType) =
		if (error ($err) and alarm ($err) = false) then
			if (blood_flow_conn >= 50 and blood_flow_conn <=600) then
				par
					//bp_status := START
					blood_flow_conn_contr := blood_flow_conn
					error($err) := false
				endpar
			endif
		endif
	
	macro rule r_error_conn_vp_up_limit =
		r_error_update_blood_flow[CONN_VP_UP]
		
	macro rule r_error_conn_vp_low_limit =
		r_error_pressure_limit[CONN_VP_LOW, bf_err_vp_low_conn, reset_err_pres_vp_low_conn, -100, 400, min_vp, min_vp_contr, max_vp, max_vp_contr]
	
	macro rule r_error_conn_ap_low_limit =
		r_error_pressure_limit[CONN_AP_LOW, bf_err_ap_low_conn, reset_err_pres_ap_low_conn, -400, 400, min_ap, min_ap_contr, max_ap, max_ap_contr]
	
	macro rule r_error_rein_vp_up_limit =
		r_error_update_blood_flow[REIN_VP_UP] 
	
	macro rule r_error_rein_ap_low_limit =
		r_error_update_blood_flow[REIN_AP_LOW] 
	
	macro rule r_error_fill_blood_vol =
		r_error_update_blood_flow[FILL_BLOOD_VOL] 
	
	rule r_error_df_prep =
		if (error (DF_PREP) and alarm (DF_PREP) = false) then
			if (error_bicarbonate_resolve) then
				//par
					error(DF_PREP) := false
					//machine_state := MAIN_FLOW
					//bicarbonate_status := true
				//endpar
			endif
		endif
	
	rule r_error_heparin = 
		if (error (HEPARIN_DIR) and alarm (HEPARIN_DIR) = false) then
			if (error_heparin_resolve) then
				par
					error(HEPARIN_DIR) := false
					heparin_running := true 
					//r_start_BP_therapy[] 
				endpar
			endif
		endif
				
	rule r_error_SAD = 
		if (error (SAD_ERR) and alarm (SAD_ERR) = false) then
			if (error_SAD_resolved) then
				//par
					error (SAD_ERR) := false
					/*if (mode = THERAPY_RUNNING) then
						r_start_BP_therapy[]
					else
						bp_status := START
					endif
				endpar*/
			endif
		endif
	
	rule r_error_UF_rate = 
		if (error(UF_RATE) and alarm (UF_RATE) = false) then
			if (error_UF_rate_resolved) then
				//par
					error(UF_RATE) := true
				//	bicarbonate_status := true
				//endpar
			endif
		endif
	
	rule r_error_UF_dir = 
		if (error(UF_DIR) and alarm (UF_DIR) = false) then
			if (error_UF_dir_resolved) then
				//par
					error(UF_DIR) := true
				//	machine_state := MAIN_FLOW
				//endpar
			endif
		endif
													
	macro rule r_run_error =
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
			r_error_rein_vp_up_limit[] 
			r_error_rein_ap_low_limit[] 
			r_error_fill_blood_vol[] 
			r_error_df_prep[]
			r_error_heparin[] 
			r_error_SAD[] 
			r_error_UF_rate[] 
			r_error_UF_dir[]
		endpar

			
	rule r_run_alarm =
		if (reset_alarm) then
			par
				forall $alarmon in ErrorAlarmType with alarm($alarmon)= true do
					alarm($alarmon) := false
				if (alarm(UF_VOLUME_ERR)) then
					uf_volume_contr := uf_volume_contr + 200
				endif
			endpar
		endif
		
	
	macro rule r_set_filling_bp_rate =
		r_insert_param [50, 600, FILLING_BP_VOLUME, fill_bp_rate, fill_bp_rate_contr] 		
		
	macro rule r_set_filling_bp_volume =
		r_insert_param [0, 6000, BP_RATE_RINSING, fill_bp_volume, fill_bp_volume_contr] 	
	
	macro rule r_set_bp_rate_rinsing =
		r_insert_param [50, 300, DF_FLOW_RINSING, bp_rate_rinsing, bp_rate_rinsing_contr] 
	
	macro rule r_set_df_flow_rinsing =
		r_insert_param [50, 300, TIME_RINSING, df_flow_rinsing, df_flow_rinsing_contr]
		
	macro rule r_set_time_rinsing =
		r_insert_param [0, 59, UF_RATE_RINSING, time_rinsing, time_rinsing_contr]

	macro rule r_set_uf_rate_rinsing =
		r_insert_param [0, 3000, UF_VOLUME_RINSING, uf_rate_rinsing, uf_rate_rinsing_contr]
		
	macro rule r_set_uf_volume_rinsing =
		r_insert_param [0, 2950, TUBING_SYSTEM, uf_volume_rinsing, uf_volume_rinsing_contr]
		
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
		if (av_tubes_conn) then
			tubingPhase := CONNECT_ALL_COMP
		endif
	
	rule r_connect_all_comp =
		if (all_comp_conn) then
			tubingPhase := SET_SALINE_LEVELS
		endif
	
	rule r_set_saline_levels =
		if (saline_levels_set) then
			tubingPhase := INSERT_BLOODLINES
		endif
		
	rule r_insert_bloodlines =
		if (blood_line_insert) then
			tubingPhase := PRIMING
		endif
		
	rule r_priming =
		if (bp_status_der = STOP) then
			bp_status := START
		else
			par
				bp_rate_rinsing_contr := 150 
				if (bp_fill_fluid and bp_rate_rinsing_contr = 150 ) then
					par
						bp_status := STOP
						tubingPhase := CONNECT_AV_ENDS
					endpar
				endif
			endpar
		endif
	
	rule r_connect_av_ends =
		if (av_ends_conn) then
			mode := PREPARE_HEPARIN
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
		if (heparin_prepared) then
			mode := SET_TREAT_PARAM
		endif
	
	
	macro rule r_set_blood_conductivity =
		r_insert_param [12500, 16000, BIC_AC, blood_conductivity, blood_conductivity_contr] 		
		
	macro rule r_set_bic_ac =
		r_insert_param [BIC_CONDUCTIVITY, bic_ac, bic_ac_contr] 		

	macro rule r_set_bic_conductivity =
		r_insert_param [2, 4, DF_TEMP, bic_conductivity, bic_conductivity_contr] 		

	macro rule r_set_df_temp =
		r_insert_param [33, 44, DF_FLOW, df_temp, df_temp_contr] 
	
	macro rule r_set_df_flow =
		r_insert_param [300, 800, UF_VOLUME, df_flow, df_flow_contr] 
		
	macro rule r_set_uf_volume =
		r_insert_param [100, 20000, THERAPY_TIME, uf_volume, uf_volume_contr]
	
	macro rule r_set_therapy_time =
		r_insert_therapy_time [MIN_UF_RATE]
	
	macro rule r_set_min_uf_rate =
		r_insert_param [0, 500, MAX_UF_RATE, min_uf_rate, min_uf_rate_contr]
	
	macro rule r_set_max_uf_rate =
		r_insert_param [0, 4000, MIN_AP, max_uf_rate, max_uf_rate_contr]
	
	macro rule r_set_min_ap =
		r_insert_param [-400, 400, MAX_AP, min_ap, min_ap_contr]
	
	macro rule r_set_max_ap =
		r_insert_param [-400, 400, MIN_VP, max_ap, max_ap_contr]
	
	macro rule r_set_min_vp =
		r_insert_param [-100, 400, MAX_VP, min_vp, min_vp_contr]
	
	macro rule r_set_max_vp =
		r_insert_param [-100, 400, DELTA_AP, max_vp, max_vp_contr]
	
	macro rule r_set_delta_ap =
		r_insert_param [10, 100, PERC_DELTA_TMP, delta_ap, delta_ap_contr]
	
	macro rule r_set_perc_delta_tmp =
		r_insert_param [2, 99, LIMITS_TMP, perc_delta_tmp, perc_delta_tmp_contr]
	
	macro rule r_set_limits_tmp = 
		r_insert_param [MAX_TMP, limits_tmp, limits_tmp_contr]
	
	macro rule r_set_max_tmp = 
		r_insert_param [300, 700, EXTENDED_TMP, max_tmp, max_tmp_contr]
	
	macro rule r_set_extended_tmp =
		r_insert_param [MAX_BEP, extended_tmp_limit, extended_tmp_limit_contr]
	
	macro rule r_set_max_bep = 
		r_insert_param [0, 700, STOP_TIME_H, max_bep, max_bep_contr]
	
	macro rule r_set_h_stop_time =
		r_insert_heparin_time [BOLUS_VOLUME_H]
	
	macro rule r_set_h_bolus_volume =
		r_insert_param [100, 10000, RATE_H, bolus_volume_h, bolus_volume_h_contr]
	
	macro rule r_set_h_rate =
		r_insert_param [100, 10000, ACTIVATION_H, rate_h, rate_h_contr]
	
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
		if (dialyzer_connected) then 
			rinsePhase := FILL_ART_CHAMBER
		endif
	
	rule r_fill_art_chamber =
		if (bp_status_der = STOP) then
			bp_status := START
		else
			if (arterial_chamber_filled) then
				rinsePhase := FILL_VEN_CHAMBER
			endif
		endif
		
	rule r_fill_ven_chamber =
		if (venous_chamber_fill) then
			rinsePhase := FILL_DIALYZER
		endif
		
	rule r_fill_dialyzer =
		if (dialyzer_filled) then
			par
				bp_status := STOP
				mode := CONNECT_PATIENT
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
	
	
	rule r_check_initiation_phase = 
		par
			r_check_temp_high[]
			r_check_temp_low[] 		
			if (bp_status_der = START) then
				par
					r_check_BP_rotation_dir[] 
					r_check_bp_less_flow[] 
					r_check_bp_no_flow_err[] 
				endpar
			endif
		endpar
	
	rule r_conn_arterially =
		if (art_connected) then
			par
				patientPhase := START_BP
				art_connected_contr := true
			endpar
		endif
	
	rule r_start_bp =
		if (error_bp = false) then
			par
				patientPhase := BLOOD_FLOW
				bp_status := START
				ap_limits_set := false
				vp_limits_set := false
				//r_start_BP_therapy[]
			endpar
		endif
		
	macro rule r_set_blood_flow =
		if (ven_connected_contr = false) then
			r_insert_param[50, 600, FILL_TUBING, blood_flow_conn, blood_flow_conn_contr]
		else
			r_insert_param[50, 600, END_CONN, blood_flow_conn, blood_flow_conn_contr]
		endif
	
	rule r_fill_tubing =
		if (error_bp = false) then
			if (blood_on_VRD or current_conn_infuse_set_volume = fill_bp_volume_contr) then
				par
					patientPhase := CONN_VEN
					bp_status := STOP
				endpar
			endif
		endif
	
	rule r_conn_venously =
		if (ven_connected) then
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
			mode := THERAPY_RUNNING
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
		if (bp_status_der = START) then
			par
				r_check_conn_vp_up_limit[] 	
				r_check_conn_vp_low_limit[] 
				r_check_conn_ap_low_limit[]
				r_check_fill_blood_vol[]
				r_check_SAD[]
			endpar
		endif
	
	macro rule r_connect_patient =
		if (err_patient_conn = false and error_bp = false) then
			par
				r_patient_connection[]
				r_check_patient[]
			endpar
		endif
	
	
	macro rule r_start_heparin =
		if (activation_h_contr) then
			par
				therapyPhase := THERAPY_EXEC
				heparin_running := true
			endpar
		endif
	
	macro rule r_run_heparin = 
		if (heparin_running) then
			par
				r_check_heparin[]
					if (passedMin(time_heparin)) then
						heparin_running := false
					endif
			endpar
		endif
	
	rule r_update_ap_limits =
		if ((current_ap <= ap_limit_up_contr) and (current_ap >= ap_limit_low_contr))then
			if (((current_ap - delta_ap_contr) >= min_ap_contr) and ((current_ap + delta_ap_contr) <= max_ap_contr)) then
				par
					ap_limit_low_contr := current_ap - delta_ap_contr
					ap_limit_up_contr := current_ap + delta_ap_contr
				endpar
			endif
		endif
	
	rule r_update_vp_limits =
		if ((current_vp <= vp_limit_up_contr) and (current_vp >= vp_limit_low_contr)) then
			if (passedMin(5)) then
				vp_limit_low_contr := vp_limit_low_contr - 2 //update lower VP: it should be 2.5 instead of 2 but we use 2 for the model checker
			endif
		endif
		
	rule r_set_ap_vp_limits =
		par
			if (ap_limits_set = false) then
				if (((current_ap - delta_ap_contr) >= min_ap_contr) and ((current_ap + delta_ap_contr) <= max_ap_contr)) then
					par
						ap_limit_low_contr := current_ap - delta_ap_contr
						ap_limit_up_contr := current_ap + delta_ap_contr
						ap_limits_set := true
					endpar
				endif
			endif
			if (vp_limits_set = false) then
				if ((vp_limit_low >= min_vp_contr) and (vp_limit_up <= max_vp_contr)) then
					par
						vp_limit_low_contr := vp_limit_low
						vp_limit_up_contr := vp_limit_up
						vp_limits_set := true
					endpar
				endif
			endif
		endpar
	
	macro rule r_ap_vp_limits = 
		if (check_ap = true) then
			if (ap_limits_set = true and vp_limits_set = true) then
				par
					r_update_ap_limits[] 
					r_update_vp_limits[] 
				endpar
			else
				if (passedSec(10) = true) then
					r_set_ap_vp_limits[] 
				endif
			endif
		endif
		
	rule r_treatment_min_UF = 
		if (treatment_min_uf_rate_contr = false) then
			if (treatment_min_uf_rate = true) then
				par
					current_uf_rate := min_uf_rate_contr
					treatment_min_uf_rate_contr := true
				endpar
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
		if (art_bolus_volume >= 0 and art_bolus_volume <= 1000) then
			par
				art_bolus_volume_contr := art_bolus_volume
				bp_status := STOP
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
		if (current_art_bolus_volume = art_bolus_volume_contr) then
			par
				arterialBolusPhase := WAIT_SOLUTION
				alarm(ARTERIAL_BOLUS_END) := true
			endpar
		endif

	rule r_check_arterial_bolus =  
		if (current_art_bolus_volume > 400) then //ml
			par
				alarm(ARTERIAL_BOLUS) := true
				error(ARTERIAL_BOLUS) := true
				//bp_status := STOP
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
		if (passedMin(time_therapy) = true) then
			therapyPhase := THERAPY_END
		endif
		//endlet

	
	rule r_update_blood_flow =
		par
			if (update_blood_flow = true ) then
				if (blood_flow_conn >= 50 and blood_flow_conn <=600) then
					blood_flow_conn_contr := blood_flow_conn
				endif
			endif
			if (update_th_time = true) then
				r_insert_therapy_time[BLOOD_CONDUCTIVITY]
			endif
		endpar
	
	macro rule r_check_therapy_run = 
		par
			if (bp_status_der = START) then
				par
					if (ap_limits_set = true and vp_limits_set = true) then
						par
							r_check_init_vp_up_limit[] 
							r_check_init_ap_up_limit[] 
							r_check_init_vp_low_limit[] 
							r_check_init_ap_low_limit[]
						endpar
					endif 
					r_check_SAD[] 
				endpar
			endif
			if (bicarbonate_status_der = true) then
				par
					r_check_UF_rate[] 
					r_check_UF_dir[] 
					r_check_UF_volume[] 
					r_check_UF_bypass[]
					r_check_df_prep[]
				endpar
			endif
		endpar
		
	macro rule r_therapy_exec =
		par
			if (error_therapy = false) then
				par
					r_run_heparin[] 
					r_ap_vp_limits[] 
					r_treatment_min_UF[] 
					if (error_bp = false) then
						r_arterial_bolus[]
					endif
					r_therapy_end_time[] 
					r_update_blood_flow[] 
					r_check_therapy_run[]
				endpar
			endif
			r_interrupt[] 
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
			r_check_initiation_phase[]
			if (mode = CONNECT_PATIENT) then
				r_connect_patient[]
			endif
			if (mode = THERAPY_RUNNING) then
				r_running_therapy[] 
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
			bp_status := START
			reinfusionPhase := RUN_SALINE_INF
		endpar
		
	macro rule r_run_saline_inf =
		if (saline_on_VRD = true) then
			reinfusionPhase := CHOOSE_NEXT_REINF_STEP
		endif
	
	macro rule r_choose_next_reinf_step =
		if (new_saline_reinfusion = true) then
			reinfusionPhase := START_SALINE_REIN
		else
			reinfusionPhase := REMOVE_VEN
		endif
		
	macro rule r_start_saline_reinf =
		par
			bp_status := START
			reinfusionPhase := RUN_SALINE_REIN
		endpar
	
	macro rule r_run_saline_reinf = 
		if ((current_volume_saline_inf = 400) or (passedMin(5) = true)) then
			reinfusionPhase := CHOOSE_NEXT_REINF_STEP
		endif
		
	macro rule r_remove_venously =
		if (ven_removed = true) then
			par
				mode := DRAIN_DIALYZER
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
			mode := EMPTY_CARTRIDGE
		endif
		
	macro rule r_empty_cartridge =
		if (cartridge_emtpy = true) then
			mode := THERAPY_OVERVIEW
		endif
		
	macro rule r_therapy_overview =
		skip
	
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
		par
			r_run_therapy[] 
			if (errorExist = true) then
				r_run_error[] 
			endif
			if (alarmExist = true) then
				r_run_alarm[]
			endif
		endpar
			
		
		
// INITIAL STATE
default init s0:
	function phaseTherapy = PREPARATION
	function mode = AUTO_TEST
	function rinsingParam = FILLING_BP_RATE
	function tubingPhase = CONNECT_AV_TUBES
	function treatmentParam = BLOOD_CONDUCTIVITY
	function rinsePhase = CONNECT_DIALYZER
	function patientPhase = CONN_ART
	function therapyPhase = START_HEPARIN
	function reinfusionPhase = REMOVE_ART
	function machine_state = BYPASS
	function signal_lamp = YELLOW
	function arterialBolusPhase = WAIT_SOLUTION
	function fill_bp_rate_contr = 0
	function fill_bp_volume_contr = 0
	function bp_rate_rinsing_contr = 0
	function df_flow_rinsing_contr = 0
	function time_rinsing_contr = 0
	function uf_rate_rinsing_contr = 0
	function uf_volume_rinsing_contr = 0
	function bp_status = STOP
	function blood_conductivity_contr = 0
	function bic_ac_contr = BICARBONATE
	function bic_conductivity_contr = 0
	function df_temp_contr = 0
	function df_flow_contr = 0
	function uf_volume_contr = 0
	function therapy_time_mins_contr = 0
	function therapy_time_hrs_contr = 0
	function min_uf_rate_contr = 0
	function max_uf_rate_contr = 0
	function min_ap_contr = 0
	function max_ap_contr = 0
	function min_vp_contr = 0
	function max_vp_contr = 0
	function delta_ap_contr = 0
	function perc_delta_tmp_contr = 0
	function limits_tmp_contr = false
	function max_tmp_contr = 0
	function extended_tmp_limit_contr = false
	function max_bep_contr = 0	
	function stop_time_mins_h_contr = 0
	function stop_time_hrs_h_contr = 0
	function bolus_volume_h_contr = 0
	function rate_h_contr = 0
	function activation_h_contr = false
	function syringe_type_contr = ST10
	function blood_flow_conn_contr = 0
	function ven_connected_contr = false
	function art_connected_contr = false	
	function bicarbonate_status = false
	function heparin_running = false
	function ap_limits_set = false
	function vp_limits_set = false
	function treatment_min_uf_rate_contr = false
	function ap_limit_low_contr = 0	
	function vp_limit_low_contr = 0
	function ap_limit_up_contr = 0
	function vp_limit_up_contr = 0
	function current_uf_rate = 0
	function art_bolus_volume_contr = 0
	function check_ap = true
	function error($t in ErrorAlarmType) = false
	function alarm($t in ErrorAlarmType) = false
	function blood_flow_error_bf_less = false
	function th_time_error_bf_less = false
	function bf_err_vp_up = false
	function reset_err_pres_vp_up = false
	function bf_err_vp_low = false
	function reset_err_pres_vp_low = false
	function bf_err_ap_up = false
	function reset_err_pres_ap_up = false
	function bf_err_ap_low = false
	function reset_err_pres_ap_low = false	
	function bf_err_vp_low_conn = false
	function reset_err_pres_vp_low_conn = false
	function bf_err_ap_low_conn = false
	function reset_err_pres_ap_low_conn = false	
