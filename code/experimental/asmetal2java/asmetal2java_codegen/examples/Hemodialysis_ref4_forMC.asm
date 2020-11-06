asm Hemodialysis_ref4_forMC

import STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
	enum domain ModePreparation = {RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
	enum domain ModeInitiation = {CONNECT_PATIENT | THERAPY_RUNNING}
	enum domain ModeEnding = {REINFUSION | THERAPY_OVERVIEW | EMPTY_CARTRIDGE | DRAIN_DIALYZER}
	enum domain RinsingParam = {FILLING_BP_RATE | FILLING_BP_VOLUME | BP_RATE_RINSING | DF_FLOW_RINSING | TIME_RINSING | UF_RATE_RINSING | UF_VOLUME_RINSING}
	enum domain TubingPhase = {CONNECT_AV_TUBES | CONNECT_ALL_COMP | SET_SALINE_LEVELS | INSERT_BLOODLINES | PRIMING | CONNECT_AV_ENDS}
	enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | MIN_AP | MAX_AP | MIN_VP | MAX_VP | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
	enum domain RinsePhase = {CONNECT_DIALYZER | FILL_ART_CHAMBER | FILL_VEN_CHAMBER | FILL_DIALYZER}
	enum domain PatientPhase = {CONN_ART | START_BP | BLOOD_FLOW | FILL_TUBING | CONN_VEN | END_CONN}
	enum domain TherapyPhase = {START_HEPARIN | THERAPY_EXEC | THERAPY_END}
	enum domain ReinfusionPhase = {REMOVE_ART | CONN_SALINE | START_SALINE_INF | RUN_SALINE_INF | CHOOSE_NEXT_REINF_STEP | START_SALINE_REIN | RUN_SALINE_REIN | REMOVE_VEN}
	enum domain BPStatus = {START | STOP}
	enum domain SignalLamps = {GREEN | YELLOW}
	enum domain MachineState = {BYPASS | MAIN_FLOW}
	enum domain ArterialBolusPhase = {WAIT_SOLUTION | SET_ARTERIAL_BOLUS_VOLUME | CONNECT_SOLUTION | RUNNING_SOLUTION }
	enum domain ErrorAlarmType = {UF_BYPASS | UF_VOLUME_ERR | UF_DIR | UF_RATE | SAD_ERR | HEPARIN_DIR | DF_PREP | FILL_BLOOD_VOL | REIN_VP_UP | REIN_AP_LOW | CONN_VP_UP | CONN_VP_LOW | CONN_AP_LOW | INIT_VP_UP | INIT_VP_LOW | INIT_AP_UP | INIT_AP_LOW | BP_NO_FLOW | BP_LESS_FLOW | BP_ROTATION_DIR | TEMP_HIGH | TEMP_LOW | ARTERIAL_BOLUS | ARTERIAL_BOLUS_END}
	

	domain SADLimit subsetof Integer // SAD limits
	domain LMHlimit subsetof Integer
	domain Airlimit subsetof Integer 
	

// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled modePreparation: ModePreparation //Modes in which the system can be
	dynamic controlled modeInitiation: ModeInitiation //Modes in which the system can be
	dynamic controlled modeEnding: ModeEnding //Modes in which the system can be
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

	//Passed Time
	dynamic monitored passedHeparinTime: Boolean //Heparin Time passed
	dynamic monitored passed5Min: Boolean // 5 minutes passed
	dynamic monitored passedTherapyTime: Boolean //Therapy Time passed
	dynamic monitored passed120Sec: Boolean //120 secods passed
	dynamic monitored passed10Sec: Boolean //10 seconds passed
	dynamic monitored passed3Sec: Boolean //3 seconds passed
	dynamic monitored passed1Sec: Boolean //1 second passed
	dynamic monitored passed1Msec: Boolean //1 millisecond passed
	
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
	dynamic monitored blood_flow_conn_reset: Boolean //True --> Blood flow reset into limits
	dynamic monitored param_press_reset: Boolean //Pressure parameters reset
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
	
	//Reinfusion
	dynamic monitored art_removed: Boolean //True--> patient arterially is removed
	dynamic monitored saline_conn: Boolean //True--> saline solution is connected
	dynamic monitored saline_on_VRD: Boolean //True--> saline is detected on VRD 
	dynamic monitored new_saline_reinfusion: Boolean //True--> new saline reinfusion is started
	dynamic controlled empty_dialyzer: Boolean //True--> dialyzer is emptied
	
	//dynamic monitored current_volume_saline_inf: SalineVolume //Volume of saline solution infused
	dynamic monitored volume_saline_inf_400: Boolean //Volume of saline solution infused is equal to 400
	dynamic monitored ven_removed: Boolean //True--> patient venously is removed
	
	//Alarm Error
	derived errorExist: Boolean //True if exist at least one error
	derived alarmExist: Boolean  //True if exist at least one alarm
	derived err_patient_conn: Boolean //True if exist error during patient conn 
	derived error_bp: Boolean //True if exist blood pump error
	derived error_rein_press: Boolean //True if exist error during reinfusion phase
	derived error_therapy: Boolean //True if exist error during therapy
	
	/*derived error_bicarbonate_status: Boolean 
	derived machine_state_status: Boolean 
	derived bp_status_update: Boolean*/
	
	dynamic controlled error: ErrorAlarmType -> Boolean //True--> error true
	dynamic controlled alarm: ErrorAlarmType -> Boolean //True--> alarm signal on
	dynamic monitored reset_alarm: Boolean // True--> reset alarm
	dynamic monitored current_art_bolus_volume: LMHlimit //1--> nei limiti 2-->fuori limiti 0-->valore settato raggiunto
	
	//dynamic monitored reset_error_arterial_bolus: Boolean // True--> reset error arterial bolus
	//dynamic monitored reset_error_bp_no_flow: Boolean // True--> reset error blood pump no flow detected for 120 sec
	//dynamic monitored reset_error_bp_rot_dir: Boolean // True -->  reset error blood pump rotates backwards
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
	
	//Requirements
	dynamic monitored update_blood_flow: Boolean //True--> update blood flow
	dynamic monitored current_temp: LMHlimit //Current temperature
	
	
	dynamic monitored current_bp_flow_less_70: Boolean //True--> current blood flow is less than set blood flow
	dynamic monitored current_vp: LMHlimit //Current VP during initiation
	dynamic monitored current_ap: LMHlimit //Current AP during initiation
	
	dynamic monitored fill_blood_vol_passed_up_limit: Boolean //True--> fill blood volume exceed upper limit
	dynamic monitored current_air_vol: Airlimit //current air volume
	dynamic monitored currentSAD: SADLimit //current SAD measured
	dynamic monitored uf_rate_passed_max_uf_rate: Boolean //True --> UF rate exceed upper limit
	dynamic monitored uf_volume_passed_max_uf_volume: Boolean //True --> UF volume exceed upper limit
	
	dynamic monitored detected_blood_flow: Boolean // True --> no flow dtected by blood pump
	dynamic monitored bp_rotates_back: Boolean // True --> blood pump rotates backwards
	dynamic monitored detect_bicarbonate: Boolean // True --> bicarbonate detected False --> acetate or acid detected instead of bicarbonate
	dynamic monitored reverse_dir_heparin: Boolean // True --> reverse direction of heparin pump
	dynamic monitored uf_dir_backwards: Boolean // True --> uf pump rotates backwards

	dynamic controlled check_ap: Boolean
	
	
	derived machine_status_der: MachineState
	derived errorePerBYPASS: Boolean
	derived bp_status_der: BPStatus
	derived errorePERbpStatus: Boolean
	derived bicarbonate_status_der: Boolean
	derived errorePERbicarbonate: Boolean
	
definitions:
// DOMAIN DEFINITIONS
	domain SADLimit = {1 .. 4}
	domain LMHlimit = {0 .. 2} //L low 0 limit M into limit 1 H high limit 2
	domain Airlimit = {0 .. 3}

	
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
		(exist $t in ErrorAlarmType with error($t) = true)
		
	function alarmExist = 
		(exist $t in ErrorAlarmType with alarm($t) = true)
		
	function err_patient_conn = 
		(error(CONN_VP_UP) = true or error(CONN_VP_LOW)= true or error(CONN_AP_LOW)= true or error(FILL_BLOOD_VOL) = true or error(SAD_ERR) = true)
	
	function error_bp =
		(error(BP_NO_FLOW) = true or error(BP_ROTATION_DIR) = true or error(BP_LESS_FLOW) = true)
	
	function error_rein_press = 
		(error(REIN_VP_UP) = true or error(REIN_AP_LOW) = true or error(SAD_ERR) = true)
		
	function error_therapy =
		(error(INIT_VP_UP) = true or error(INIT_VP_LOW) = true or error(INIT_AP_UP) = true or error(INIT_AP_LOW) = true or error(SAD_ERR) = true or error(HEPARIN_DIR) = true)

// RULE DEFINITIONS

	rule r_auto_test =
		if (auto_test_end = true) then
			modePreparation := CONNECT_CONCENTRATE
		endif
		
	rule r_connect_concentrate =
		if (conn_concentrate = true) then
			modePreparation := SET_RINSING_PARAM
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
	
	rule r_insert_param ($inf in Integer, $sup in Integer, $nextparam in PatientPhase, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				patientPhase := $nextparam 
			endpar
		endif

	rule r_insert_param ($nextparam in TreatmentParam, $mon in Boolean, $contr in Boolean) = 
		par 
			$contr := $mon 
			treatmentParam := $nextparam 
		endpar
		
		macro rule r_insert_param ($phase in PatientPhase, $setvalue in Boolean) =
		if ($setvalue = true) then
			patientPhase := $phase
		endif
		
	/*rule r_start_BP_therapy = 
		par
			//if (bp_status_update = false) then
				bp_status := START // azione della macchina
			//endif
			ap_limits_set := false
			vp_limits_set := false	
		endpar*/
	
	rule r_check_temp_high = 
		if (error(TEMP_HIGH) = false) then
			if (current_temp = 2) then	
				par
					error(TEMP_HIGH) := true
					alarm(TEMP_HIGH) := true
					//machine_state := BYPASS
				endpar
			endif
		endif
		
	rule r_check_temp_low = 
		if (error(TEMP_LOW) = false) then
			if (current_temp = 0) then		
				par
					error(TEMP_LOW) := true
					alarm(TEMP_LOW) := true
					//machine_state := BYPASS
				endpar
			endif
		endif
		
	rule r_check_BP_rotation_dir =
		if (error(BP_ROTATION_DIR) = false) then
			if (bp_rotates_back = true) then
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
				if (current_bp_flow_less_70 = true) then
					par
						alarm(BP_LESS_FLOW) := true
						error(BP_LESS_FLOW) := true
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
					//bp_status := STOP
				endpar
			endif
		endif

	rule r_check_init_press_limit  ($err in ErrorAlarmType, $limit in LMHlimit, $errbf in Boolean, $errpress in Boolean) =
		if (error($err) = false) then
			if ($limit = 0 or $limit = 2) then
				par
					error($err) := true
					alarm($err) := true
					//bp_status := STOP
					$errbf := true
					$errpress := true
				endpar
			endif
		endif
		
	macro rule r_check_init_vp_up_limit =
		r_check_init_press_limit[INIT_VP_UP, current_vp, bf_err_vp_up, reset_err_pres_vp_up] 
	
	macro rule r_check_init_ap_up_limit =
		r_check_init_press_limit[INIT_AP_UP, current_ap, bf_err_ap_up, reset_err_pres_ap_up] 
		
	macro rule r_check_init_vp_low_limit =
		r_check_init_press_limit[INIT_VP_LOW, current_vp, bf_err_vp_low, reset_err_pres_vp_low] 
	
	macro rule r_check_init_ap_low_limit =
		r_check_init_press_limit[INIT_AP_LOW, current_ap, bf_err_ap_low, reset_err_pres_ap_low] 
	
	rule r_check_conn_rein_press_limit ($err in ErrorAlarmType, $limit in LMHlimit, $passedT in Boolean)= 
		if (error($err) = false) then
			if (($limit = 0 or $limit = 2) and $passedT = true) then
				par
					error($err) := true
					alarm($err) := true
					//bp_status := STOP
				endpar
			endif
		endif

	rule r_check_conn_rein_press_limit ($err in ErrorAlarmType, $limit in LMHlimit, $passedT in Boolean, $errbf in Boolean, $errpress in Boolean)= 
		if (error($err) = false) then
			if (($limit = 0 or $limit = 2) and $passedT = true) then
				par
					error($err) := true
					alarm($err) := true
					$errbf := true
					$errpress := true
					//bp_status := STOP
				endpar
			endif
		endif
		
	macro rule r_check_conn_vp_up_limit = 
		r_check_conn_rein_press_limit[CONN_VP_UP, current_vp, passed3Sec] //>+450
		
	macro rule r_check_conn_vp_low_limit = 
		r_check_conn_rein_press_limit[CONN_VP_LOW, current_vp, passed3Sec, bf_err_vp_low_conn, reset_err_pres_vp_low_conn] //<low limit
		 
	macro rule r_check_conn_ap_low_limit = 
		r_check_conn_rein_press_limit[CONN_AP_LOW, current_ap, passed1Sec, bf_err_ap_low_conn, reset_err_pres_ap_low_conn] //<low limi
	
	macro rule r_check_rein_vp_up_limit = 
		r_check_conn_rein_press_limit[REIN_VP_UP, current_vp, passed3Sec] //>+350
			
	macro rule r_check_rein_ap_low_limit = 
		r_check_conn_rein_press_limit[REIN_AP_LOW, current_ap, passed1Sec] //< -350
		
	rule r_check_fill_blood_vol = 
		if (error(FILL_BLOOD_VOL) = false) then
			if (fill_blood_vol_passed_up_limit = true) then
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
			if (reverse_dir_heparin = true) then
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
			if (passed1Msec = true) then
				par
					if (currentSAD = 4) then //=4
					 	r_set_err_SAD[]
					 endif
					if (currentSAD = 1) then //=1
					 	if (current_air_vol > 0) then
					 		r_set_err_SAD[]
					 	endif
					 endif
					 if (currentSAD = 2) then //=2
					 	if (current_air_vol > 1) then
					 		r_set_err_SAD[]
					 	endif
					 endif
					 if (currentSAD = 3) then //=3
					 	if (current_air_vol > 2) then
					 		r_set_err_SAD[]
					 	endif
					 endif
				endpar
			endif
		endif	
	
	rule r_check_UF_rate = 
		if (error(UF_RATE) = false) then
			if (uf_rate_passed_max_uf_rate = true) then
				par
					error(UF_RATE) := true
					alarm(UF_RATE) := true
					//bicarbonate_status := false
				endpar
			endif
		endif

	rule r_check_UF_dir = 
		if (error(UF_DIR) = false) then
			if (uf_dir_backwards = true) then
				par
					error(UF_DIR) := true
					alarm(UF_DIR) := true
					//machine_state := BYPASS
				endpar
			endif
		endif


	rule r_check_UF_volume = 
		if (alarm(UF_VOLUME_ERR) = false) then
			if (uf_volume_passed_max_uf_volume = true) then	
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
		if (error (TEMP_HIGH) = true and alarm (TEMP_HIGH) = false) then
			if (current_temp = 1) then
				//par
					error (TEMP_HIGH) := false
					/*if (machine_state_status = false) then
						machine_state := MAIN_FLOW
					endif*/
				//endpar
			endif
		endif
	
	rule r_error_temp_low =
		if (error (TEMP_LOW) = true and alarm (TEMP_LOW) = false) then
			if (current_temp = 1) then	
				//par
					error (TEMP_LOW) := false
					/*if (machine_state_status = false) then
						machine_state := MAIN_FLOW
					endif*/
				//endpar
			endif
		endif
	
	rule r_error_arterial_bolus =
		if (error (ARTERIAL_BOLUS) = true and alarm (ARTERIAL_BOLUS) = false) then
			if (current_art_bolus_volume = 1) then	
				par
					error (ARTERIAL_BOLUS) := false
					arterialBolusPhase := WAIT_SOLUTION
					//r_start_BP_therapy[] 
				endpar
			endif
		endif
	
	rule r_error_BP_no_flow = 
		if (error (BP_NO_FLOW) = true and alarm (BP_NO_FLOW) = false) then
			if (detected_blood_flow = true) then
				//par
					error (BP_NO_FLOW) := false
					//if (mode = THERAPY_RUNNING) then
					//	r_start_BP_therapy[]
					//else
					//	if (bp_status_update = false) then
					//	bp_status := START
						//endif
					//endif
				//endpar
			endif
		endif
	
	rule r_error_BP_less_flow =
		if (error (BP_LESS_FLOW) = true and alarm (BP_LESS_FLOW) = false) then
			if (current_bp_flow_less_70 = false) then
				error (BP_LESS_FLOW) := false
			endif
		endif

	rule r_error_BP_rotation_dir = 
		if (error (BP_ROTATION_DIR) = true and alarm (BP_ROTATION_DIR) = false) then
			if (bp_rotates_back = false) then
				//par
					error (BP_ROTATION_DIR) := false
					/*if (mode = THERAPY_RUNNING) then
						r_start_BP_therapy[]*/
					/*else
						if (bp_status_update = false) then
							bp_status := START
						endif*/
					//endif
				//endpar
			endif
		endif
		
	rule r_error_pressure_limit ($err in ErrorAlarmType, $bfErr in Boolean, $resetP in Boolean)=
		if (error ($err) = true and alarm ($err) = false) then
			par
				if ($bfErr = true) then
					if (blood_flow_conn_reset = true) then
						$bfErr := false
					endif
				endif
				if ($resetP = true) then
					if (param_press_reset = true) then
						$resetP := false
					endif
				endif
				if ($bfErr = false and $resetP = false) then	
					//par
						error ($err) := false
						/*if (bp_status_update = false) then
							bp_status := START
						endif*/
					//endpar
				endif
			endpar
		endif
	
	rule r_error_vp_up_limit =
		r_error_pressure_limit[INIT_VP_UP, bf_err_vp_up, reset_err_pres_vp_up]
		
	rule r_error_vp_low_limit =
		r_error_pressure_limit[INIT_VP_LOW, bf_err_vp_low, reset_err_pres_vp_low]
	
	rule r_error_ap_up_limit =
		r_error_pressure_limit[INIT_AP_UP, bf_err_ap_up, reset_err_pres_ap_up]
	
	rule r_error_ap_low_limit =
		r_error_pressure_limit[INIT_AP_LOW, bf_err_ap_low, reset_err_pres_ap_low]
	
	rule r_error_update_blood_flow ($err in ErrorAlarmType) =
		if (error ($err) = true and alarm ($err) = false) then
			if (blood_flow_conn_reset = true) then
				//par
					/*if (bp_status_update = false) then
						bp_status := START
					endif*/
					error($err) := false
				//endpar
			endif
		endif
	
	macro rule r_error_conn_vp_up_limit =
		r_error_update_blood_flow[CONN_VP_UP]
		
	macro rule r_error_conn_vp_low_limit =
		r_error_pressure_limit[CONN_VP_LOW, bf_err_vp_low_conn, reset_err_pres_vp_low_conn]
	
	macro rule r_error_conn_ap_low_limit =
		r_error_pressure_limit[CONN_AP_LOW, bf_err_ap_low_conn, reset_err_pres_ap_low_conn]
	
	macro rule r_error_rein_vp_up_limit =
		r_error_update_blood_flow[REIN_VP_UP] 
	
	macro rule r_error_rein_ap_low_limit =
		r_error_update_blood_flow[REIN_AP_LOW] 
	
	macro rule r_error_fill_blood_vol =
		r_error_update_blood_flow[FILL_BLOOD_VOL] 
	
	rule r_error_df_prep =
		if (error (DF_PREP) = true and alarm (DF_PREP) = false) then
			if (detect_bicarbonate = true) then
				//par
					error(DF_PREP) := false
					/*if (machine_state_status = false) then
						machine_state := MAIN_FLOW
					endif*/
					/*if (error_bicarbonate_status = false) then
						bicarbonate_status := true
					endif*/
				//endpar
			endif
		endif
	
	rule r_error_heparin = 
		if (error (HEPARIN_DIR) = true and alarm (HEPARIN_DIR) = false) then
			if (reverse_dir_heparin = false) then
				par
					error(HEPARIN_DIR) := false
					heparin_running := true 
					//r_start_BP_therapy[] 
				endpar
			endif
		endif
				
	rule r_error_SAD = 
		if (error (SAD_ERR) = true and alarm (SAD_ERR) = false) then
			if ((currentSAD = 1 and current_air_vol = 0) or (currentSAD = 2 and current_air_vol = 1) or (currentSAD = 3 and current_air_vol = 2)) then
				//par
					error (SAD_ERR) := false
					/*if (mode = THERAPY_RUNNING) then
						r_start_BP_therapy[]*/
					/*else
						if (bp_status_update = false) then
							bp_status := START
						endif*/
					//endif
				//endpar
			endif
		endif
	
	rule r_error_UF_rate = 
		if (error(UF_RATE) = true and alarm (UF_RATE) = false) then
			if (uf_rate_passed_max_uf_rate = false) then
				//par
					error(UF_RATE) := true
					/*if (error_bicarbonate_status = false) then 
						bicarbonate_status := true
					endif*/
				//endpar
			endif
		endif
	
	rule r_error_UF_dir = 
		if (error(UF_DIR) = true and alarm (UF_DIR) = false) then
			if (uf_dir_backwards = false) then
				//par
					error(UF_DIR) := true
					/*if (machine_state_status = false) then
						machine_state := MAIN_FLOW
					endif*/
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
		if (reset_alarm = true) then
			forall $alarmon in ErrorAlarmType with alarm($alarmon)= true do
				alarm($alarmon) := false
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
		if (art_connected = true) then
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
			r_insert_param[FILL_TUBING, blood_flow_conn_set]
		else
			r_insert_param[END_CONN, blood_flow_conn_set]
		endif
	
	rule r_fill_tubing =
		if (error_bp = false) then
			if (blood_on_VRD = true or conn_infuse_set_volume = true) then
				par
					patientPhase := CONN_VEN
					bp_status := STOP // azione della macchina
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
		if (activation_h_contr = true) then
			par
				therapyPhase := THERAPY_EXEC
				heparin_running := true
			endpar
		endif
	
	macro rule r_run_heparin = 
		if (heparin_running = true) then
			par
				r_check_heparin[]
				if (passedHeparinTime = true) then
					heparin_running := false
				endif
			endpar
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
		if (alarm(ARTERIAL_BOLUS_END) = false) then
			if (current_art_bolus_volume = 0) then
				par
					arterialBolusPhase := WAIT_SOLUTION
					alarm(ARTERIAL_BOLUS_END) := true
				endpar
			endif
		endif

	rule r_check_arterial_bolus =  
		if (current_art_bolus_volume = 2) then //ml
			par
				alarm(ARTERIAL_BOLUS) := true
				error(ARTERIAL_BOLUS) := true
				//bp_status := STOP // azione della macchina
			endpar
		endif
	
	rule r_run_arterial_bolus = 
	//	if (error(ARTERIAL_BOLUS) = false) then
			par
				r_running_solution[] 
				r_check_arterial_bolus[] 
			endpar
	//	endif
				
	macro rule r_arterial_bolus = 
		if (error(ARTERIAL_BOLUS) = false) then
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
		endif
	
	rule r_therapy_end_time = 
		if (passedTherapyTime = true) then
			therapyPhase := THERAPY_END
		endif
	
	rule r_update_blood_flow =
		if (update_blood_flow = true ) then
			skip //update bloodflow and therapy time
		endif
	
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
				modeEnding := DRAIN_DIALYZER
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
			par
				modeEnding := EMPTY_CARTRIDGE
				empty_dialyzer := true
			endpar
		endif
		
	macro rule r_empty_cartridge =
		if (cartridge_emtpy = true) then
			modeEnding := THERAPY_OVERVIEW
		endif
		
	macro rule r_therapy_overview =
		skip
	
	macro rule r_ending=
		par
			if (modeEnding = REINFUSION) then
				r_reinfusion[] 
			endif
			if (modeEnding = DRAIN_DIALYZER) then
				r_drain_dialyzer[]
			endif
			if (modeEnding = EMPTY_CARTRIDGE) then
				r_empty_cartridge[]
			endif
			if (modeEnding = THERAPY_OVERVIEW) then
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
	function modePreparation = AUTO_TEST
	function modeInitiation = CONNECT_PATIENT
	function modeEnding = REINFUSION
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
	function bp_status = STOP
	function activation_h_contr = false
	function ven_connected_contr = false
	function art_connected_contr = false	
	function bicarbonate_status = false
	function heparin_running = false
	function ap_limits_set = false
	function vp_limits_set = false
	function treatment_min_uf_rate_contr = false
	function empty_dialyzer = false
	function check_ap = true
	function error($t in ErrorAlarmType) = false
	function alarm($t in ErrorAlarmType) = false
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
