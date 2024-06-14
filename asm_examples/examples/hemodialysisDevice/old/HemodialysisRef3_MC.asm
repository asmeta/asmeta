asm HemodialysisRef3_MC

/*
The Hemodialysis Machine Case Study
http://www.cdcc.faw.jku.at/ABZ2016/HD-CaseStudy.pdf
*/

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLLibrary
import ../../../STDL/LTLLibrary

signature:
	enum domain Phases = {PREPARATION | INITIATION | ENDING}
	enum domain PrepPhase = {RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
	enum domain RinsingParam = {FILLING_BP_RATE | FILLING_BP_VOLUME | BP_RATE_RINSING | DF_FLOW_RINSING | TIME_RINSING | UF_RATE_RINSING | UF_VOLUME_RINSING}
	enum domain TubingSystemPhase = {CONNECT_AV_TUBES | CONNECT_ALL_COMP | SET_SALINE_LEVELS | INSERT_BLOODLINES | PRIMING | CONNECT_AV_ENDS}
	enum domain BPStatus = {START | STOP}
	enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE |/* MIN_AP |*/ MAX_AP |/* MIN_VP | MAX_VP |*/ DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
	enum domain Concentrate = {BICARBONATE | ACETATE}
	enum domain SyringeType = {ST10 | ST20 | ST30}
	enum domain RinsePhase = {CONNECT_DIALYZER | FILL_ART_CHAMBER | FILL_VEN_CHAMBER | FILL_DIALYZER}
	enum domain Value = {TOOLOW | PERMITTED | HIGH}
	enum domain MachineState = {BYPASS | MAIN_FLOW}
	enum domain AlarmErrorType = {REIN_VP_UP | REIN_AP_LOW | UF_BYPASS | UF_VOLUME_ERR | UF_DIR | UF_RATE | SAD_ERR | HEPARIN_DIR | DF_PREP | FILL_BLOOD_VOL | CONN_VP_UP | CONN_VP_LOW | CONN_AP_LOW | INIT_VP_UP | INIT_VP_LOW | INIT_AP_UP | INIT_AP_LOW | BP_NO_FLOW | BP_LESS_FLOW | BP_ROTATION_DIR | TEMP_HIGH | TEMP_LOW | ARTERIAL_BOLUS | ARTERIAL_BOLUS_END}
	enum domain InitPhase = {CONNECT_PATIENT | THERAPY_RUNNING}
	enum domain PatientPhase = {CONN_ART | START_BP | BLOOD_FLOW | FILL_TUBING | CONN_VEN | END_CONN}
	enum domain SignalLamps = {GREEN | YELLOW | RED}
	enum domain TherapyPhase = {START_HEPARIN | THERAPY_EXEC | THERAPY_END}
	enum domain ArterialBolusPhase = {WAIT_SOLUTION | SET_ARTERIAL_BOLUS_VOLUME | CONNECT_SOLUTION | RUNNING_SOLUTION }
	enum domain EndingPhase = {REINFUSION | THERAPY_OVERVIEW | EMPTY_CARTRIDGE | DRAIN_DIALYZER}
	enum domain ReinfusionPhase = {REMOVE_ART | CONN_SALINE | START_SALINE_INF | RUN_SALINE_INF | CHOOSE_NEXT_REINF_STEP | START_SALINE_REIN | RUN_SALINE_REIN | REMOVE_VEN}

	dynamic controlled phase: Phases //Dialysis phase in which is currently the system
	dynamic controlled prepPhase: PrepPhase //Modes in which the system can be during preparation phase
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingSystemPhase: TubingSystemPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic controlled initPhase: InitPhase //Modes in which the system can be
	dynamic controlled patientPhase: PatientPhase //Phase during patient connection
	dynamic controlled signal_lamp: SignalLamps //Signal lamp on the monitor
	dynamic controlled therapyPhase: TherapyPhase //Phase during therapy
	dynamic controlled arterialBolusPhase: ArterialBolusPhase //Phase during arterial bolus
	dynamic controlled endingPhase: EndingPhase //Modes in which the system can be
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
	dynamic monitored fill_bp_volume: Value //The BP stops after it has rinsed the blood side using the set volume
	dynamic monitored bp_rate_rinsing: Value //BP rate for rinsing program
	dynamic monitored df_flow_rinsing: Value //DF flow rate for rinsing program
	dynamic monitored time_rinsing: Value //Duration of adjusted rinsing program
	dynamic monitored uf_rate_rinsing: Value //UF rate when rinsing with a physiological saline solution
	dynamic monitored uf_volume_rinsing: Value //UF volume when rinsing with a physiological saline solution

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
	dynamic monitored bic_ac: Concentrate //Selection of the concentrate used during therapy
	dynamic controlled bic_ac_contr: Concentrate //Selection of the concentrate used during therapy
	dynamic monitored bic_conductivity: Value //Bicarbonate conductivity
	dynamic monitored df_temp: Value //DF temperature
	dynamic monitored df_flow: Value //DF flow
	dynamic monitored uf_volume: Value //UF volume
	dynamic monitored therapy_time_mins: Value //Therapy time minutes
	dynamic monitored therapy_time_hrs: Value //Therapy time hours
	dynamic monitored min_uf_rate: Value //Minimun UF rate
	dynamic monitored max_uf_rate: Value //Maximum UF rate
	dynamic monitored max_ap: Value //Maximum AP
	dynamic monitored delta_ap: Value //Limits window for arterial entry pressure AP. Distance to min and max AP
	dynamic monitored perc_delta_tmp: Value //Limits window for TMP in % of actual value
	dynamic monitored limits_tmp: Boolean //True--> monitoring the TMP at the dialyzer
	dynamic controlled limits_tmp_contr: Boolean // True--> monitoring the TMP at the dialyzer
	dynamic monitored max_tmp: Value //Max TMP value
	dynamic monitored extended_tmp_limit: Boolean //Extended TMP limit range
	dynamic controlled extended_tmp_limit_contr: Boolean //Extended TMP limit range
	dynamic monitored max_bep: Value // Maximum BEP pressure
	dynamic monitored stop_time_mins_h: Value //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	dynamic monitored stop_time_hrs_h: Value //The heparin pump is switched off by set time prior to the end of the therapy: hours
	dynamic monitored bolus_volume_h: Value //Bolus volume for a bolus administration during dialysis
	dynamic monitored rate_h: Value //Continuous heparin rate over the entire duration of heparin administration
	dynamic monitored activation_h: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic controlled activation_h_contr: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic monitored syringe_type: SyringeType //Syringe type used during treatment
	dynamic controlled syringe_type_contr: SyringeType //Syringe type used during treatment

	//Rinse dialyzer
	dynamic monitored dialyzer_connected: Boolean //True--> dialyzer is connected to the machine
	dynamic controlled dialyzer_connected_contr: Boolean //True--> dialyzer is connected to the machine
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
	dynamic monitored vp_limit_low: Value //Current lower limit vp
	dynamic monitored current_ap: Value //Current AP
	dynamic monitored current_vp: Value //Current VP
	dynamic monitored ap_limit_up: Value //Current upper limit ap
	dynamic monitored vp_limit_up: Value //Current upper limit vp
	dynamic monitored treatment_min_uf_rate: Boolean //True --> Treatment at minimum UF rate
	dynamic controlled treatment_min_uf_rate_contr: Boolean //True --> Treatment at minimum UF rate
	dynamic monitored interrupt_dialysis: Boolean //True --> interrupt dialysis
	dynamic monitored start_arterial_bolus: Boolean //True --> start arterial bolus infusion
	dynamic monitored saline_solution_conn: Boolean //True --> saline solution for arterial bolus is connected
	dynamic monitored art_bolus_volume: Value //Arterial bolus volume infusion
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
	derived errorForBYPASS: Boolean
	derived bp_status_der: BPStatus
	derived errorFORbpStatus: Boolean
	derived bicarbonate_status_der: Boolean
	derived errorFORbicarbonate: Boolean
	derived signal_lamp_der: SignalLamps
	derived errorExist: Boolean //True if exist at least one error
	derived alarmExist: Boolean //True if exist at least one alarm
	derived err_patient_conn: Boolean //True if exist error during patient conn
	derived error_bp: Boolean //True if exist blood pump error
	derived error_rein_press: Boolean //True if exist error during reinfusion phase
	derived error_therapy: Boolean //True if exist error during therapy
	derived dialyzer_connected_status: Boolean //True if dialyzer is connected and no errors occur

	dynamic controlled error: AlarmErrorType -> Boolean //True--> error true
	dynamic controlled alarm: AlarmErrorType -> Boolean //True--> alarm signal on
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
	dynamic monitored reset_error_bp_rot_dir: Boolean // True --> reset error blood pump rotates backwards
	dynamic monitored reset_error_pression: Boolean // True --> reset error pression AP-VP
	dynamic monitored error_heparin_resolve: Boolean // True --> heparin error is resolved
	dynamic monitored error_UF_rate_resolved: Boolean // True --> error due to UF rate is resolved
	dynamic monitored error_UF_dir_resolved: Boolean // True --> error due to UF direction backwards is resolved
	dynamic controlled bf_err_ap_low: Boolean // True --> set blood flow after AP low limit error during therapy
	dynamic controlled reset_err_pres_ap_low: Boolean // True --> set pressure flow after AP low limit error during therapy
	dynamic controlled bf_err_ap_low_conn: Boolean // True --> set blood flow after AP low limit error during connection phase
	dynamic controlled reset_err_pres_ap_low_conn: Boolean // True --> set pressure flow after AP low limit error during connection phase
	derived df_flow_state: Boolean //True --> DF is on, False --> DF is off

definitions:
	function errorForBYPASS = error(TEMP_HIGH) or error(TEMP_LOW) or error(DF_PREP) or error(UF_DIR) or alarm(UF_VOLUME_ERR)

	function errorFORbpStatus = error(BP_ROTATION_DIR) or error(BP_NO_FLOW) or error(INIT_VP_UP) or error(INIT_AP_UP) or error(INIT_VP_LOW) or error(INIT_AP_LOW) or error(CONN_VP_UP) or error(CONN_VP_LOW) or error(CONN_AP_LOW) or error(REIN_VP_UP) or error(REIN_AP_LOW) or error(FILL_BLOOD_VOL) or error(HEPARIN_DIR) or error(SAD_ERR) or error(ARTERIAL_BOLUS)

	function errorFORbicarbonate = error(UF_RATE) or alarm(UF_BYPASS)

	function dialyzer_connected_status = dialyzer_connected_contr and not(error(DF_PREP)) and not(error(TEMP_HIGH))

	function machine_status_der =
		if errorForBYPASS then
			BYPASS
		else
			machine_state
		endif

	function bp_status_der =
		if errorFORbpStatus then
			STOP
		else
			bp_status
		endif

	function bicarbonate_status_der =
		if errorFORbicarbonate then
			false
		else
			bicarbonate_status
		endif

	function errorExist =
		(exist $t in AlarmErrorType with error($t))

	function alarmExist =
		(exist $t in AlarmErrorType with alarm($t))

	function signal_lamp_der =
		if errorExist then
			RED
		else
			signal_lamp
		endif

	function df_flow_state =
		/*if machine_status_der = BYPASS then
			false
		else
			true
		endif*/
		machine_status_der != BYPASS

	function err_patient_conn =
		(error(CONN_VP_UP) or error(CONN_VP_LOW) or error(CONN_AP_LOW) or error(FILL_BLOOD_VOL) or error(SAD_ERR))

	function error_bp =
		(error(BP_NO_FLOW) or error(BP_ROTATION_DIR) or error(BP_LESS_FLOW))

	function error_rein_press =
		(error(REIN_VP_UP) or error(REIN_AP_LOW) or error(SAD_ERR))

	function error_therapy =
		(error(INIT_VP_UP) or error(INIT_VP_LOW) or error(INIT_AP_UP) or error(INIT_AP_LOW) or error(SAD_ERR) or error(HEPARIN_DIR))

	rule r_check_df_prep =
		if not(error(DF_PREP)) and preparing_DF then
			if not(detect_bicarbonate) then
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
		if not(error(SAD_ERR)) then
			if passed1Msec then
				if currentSAD != PERMITTED and current_air_vol != PERMITTED then
					r_set_err_SAD[]
				endif
			endif
		endif

	rule r_check_temp_high =
		if not(error(TEMP_HIGH)) then
			if current_temp = HIGH then
				par
					error(TEMP_HIGH) := true
					alarm(TEMP_HIGH) := true
				endpar
			endif
		endif

	rule r_error_df_prep =
		if error(DF_PREP) and not(alarm(DF_PREP)) then
			if detect_bicarbonate then
				error(DF_PREP) := false
			endif
		endif

	rule r_error_SAD =
		if error(SAD_ERR) and not(alarm(SAD_ERR)) then
			if error_SAD_resolved then
				error(SAD_ERR) := false
			endif
		endif

	rule r_error_temp_high =
		if error(TEMP_HIGH) and not(alarm(TEMP_HIGH)) then
			if current_temp = PERMITTED then
				error(TEMP_HIGH) := false
			endif
		endif

	rule r_error_temp_low =
		if error(TEMP_LOW) and not(alarm(TEMP_LOW)) then
			if current_temp = TOOLOW then
				error(TEMP_LOW) := false
			endif
		endif

	rule r_error_arterial_bolus =
		if error(ARTERIAL_BOLUS) and not(alarm(ARTERIAL_BOLUS)) then
			if reset_error_arterial_bolus then
				par
					error(ARTERIAL_BOLUS) := false
					arterialBolusPhase := WAIT_SOLUTION
				endpar
			endif
		endif

	rule r_error_BP_no_flow =
		if error(BP_NO_FLOW) and not(alarm(BP_NO_FLOW)) then
			if reset_error_bp_no_flow then
				error(BP_NO_FLOW) := false
			endif
		endif

	rule r_insert_new_therapy_time ($errTime in Boolean)=
		if therapy_time_hrs = PERMITTED and therapy_time_mins = PERMITTED then
			$errTime := false
		endif

	rule r_error_AP_low_BPflow_time ($err in AlarmErrorType, $errTime in Boolean, $errBPFlow in Boolean) =
		if error($err) and not(alarm($err)) then
			par
				if $errBPFlow then
					//*********
					if (initPhase=CONNECT_PATIENT) then
						if blood_flow_conn = PERMITTED then
							$errBPFlow := false
						endif
					else
						if (initPhase=THERAPY_RUNNING) then
							if blood_conductivity = PERMITTED then
								$errBPFlow := false
							endif
						endif
					endif
					//*********
				endif
				if $errTime then
					r_insert_new_therapy_time[$errTime]
				endif
				if not($errBPFlow) and not($errTime) then
					error($err) := false
				endif
			endpar
		endif

	rule r_error_BP_less_flow =
		r_error_AP_low_BPflow_time[BP_LESS_FLOW, th_time_error_bf_less, blood_flow_error_bf_less]

	rule r_error_BP_rotation_dir =
		if error(BP_ROTATION_DIR) and not(alarm(BP_ROTATION_DIR)) then
			if reset_error_bp_rot_dir then
				error(BP_ROTATION_DIR) := false
			endif
		endif

	rule r_error_pressure_limit ($err in AlarmErrorType, $curr in Value)=
		if error($err) and not(alarm($err)) then
			if $curr = PERMITTED then
				error($err) := false
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

	rule r_error_update_blood_flow ($err in AlarmErrorType) =
		if error($err) and not(alarm($err)) then
			if blood_flow_conn = PERMITTED then
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
		if error(HEPARIN_DIR) and not(alarm(HEPARIN_DIR)) then
			if error_heparin_resolve then
				par
					error(HEPARIN_DIR) := false
					heparin_running := true
				endpar
			endif
		endif

	rule r_error_UF_rate =
		if error(UF_RATE) and not(alarm(UF_RATE)) then
			if error_UF_rate_resolved then
				error(UF_RATE) := false
			endif
		endif

	rule r_error_UF_dir =
		if error(UF_DIR) and not(alarm(UF_DIR)) then
			if error_UF_dir_resolved then
				error(UF_DIR) := false
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
		if reset_alarm then
			forall $alarmon in AlarmErrorType with alarm($alarmon) do
				alarm($alarmon) := false
		endif

	macro rule r_check_preparation =
		par
			r_check_df_prep[]
			if prepPhase = TUBING_SYSTEM then
				r_check_SAD[]
			endif
			if dialyzer_connected_contr then
				r_check_temp_high[]
			endif
		endpar

	macro rule r_run_automatic_test =
		if auto_test_end then
			par
				prepPhase := CONNECT_CONCENTRATE
				signal_lamp := GREEN
			endpar
		endif

	macro rule r_connect_concentrate =
		if conn_concentrate then
			par
				prepPhase := SET_RINSING_PARAM
				preparing_DF := true
			endpar
		endif

	rule r_insert_param ($nextParam in RinsingParam, $mon in Value) =
		if $mon = PERMITTED then
			rinsingParam := $nextParam
		endif

	rule r_insert_param ($nextParam in PrepPhase, $mon in Value) =
		if $mon = PERMITTED then
			prepPhase := $nextParam
		endif

	rule r_insert_param ($nextParam in TreatmentParam, $mon in Value) =
		if $mon = PERMITTED then
			treatmentParam := $nextParam
		endif

	rule r_insert_time ($nextParam in TreatmentParam, $mon1 in Value, $mon2 in Value) =
		if $mon1 = PERMITTED and $mon2 = PERMITTED then
			treatmentParam := $nextParam
		endif

	rule r_insert_param ($nextParam in TreatmentParam, $mon in Concentrate, $contr in Concentrate) =
		par
			$contr := $mon
			treatmentParam := $nextParam
		endpar

	rule r_insert_param ($nextParam in TreatmentParam, $mon in Boolean, $contr in Boolean) =
		par
			$contr := $mon
			treatmentParam := $nextParam
		endpar

	rule r_insert_param ($nextParam in PrepPhase, $mon in SyringeType, $contr in SyringeType) =
		par
			$contr := $mon
			prepPhase := $nextParam
		endpar

	rule r_insert_param ($nextParam in PatientPhase, $mon in Value) =
		if $mon = PERMITTED then
			patientPhase := $nextParam
		endif

	macro rule r_set_filling_bp_rate =
		r_insert_param[FILLING_BP_VOLUME, fill_bp_rate]

	macro rule r_set_filling_bp_volume =
		r_insert_param[BP_RATE_RINSING, fill_bp_volume]

	macro rule r_set_bp_rate_rinsing =
		r_insert_param[DF_FLOW_RINSING, bp_rate_rinsing]

	macro rule r_set_df_flow_rinsing =
		r_insert_param[TIME_RINSING, df_flow_rinsing]

	macro rule r_set_time_rinsing =
		r_insert_param[UF_RATE_RINSING, time_rinsing]

	macro rule r_set_uf_rate_rinsing =
		r_insert_param[UF_VOLUME_RINSING, uf_rate_rinsing]

	macro rule r_set_uf_volume_rinsing =
		r_insert_param[TUBING_SYSTEM, uf_volume_rinsing]

	macro rule r_set_rinsing_param =
		par
			if rinsingParam = FILLING_BP_RATE then
				r_set_filling_bp_rate[]
			endif
			if rinsingParam = FILLING_BP_VOLUME then
				r_set_filling_bp_volume[]
			endif
			if rinsingParam = BP_RATE_RINSING then
				r_set_bp_rate_rinsing[]
			endif
			if rinsingParam = DF_FLOW_RINSING then
				r_set_df_flow_rinsing[]
			endif
			if rinsingParam = TIME_RINSING then
				r_set_time_rinsing[]
			endif
			if rinsingParam = UF_RATE_RINSING then
				r_set_uf_rate_rinsing[]
			endif
			if rinsingParam = UF_VOLUME_RINSING then
				r_set_uf_volume_rinsing[]
			endif
		endpar

	rule r_connect_av_tubes =
		if av_tubes_connected then
			tubingSystemPhase := CONNECT_ALL_COMP
		endif

	rule r_connect_all_comp =
		if all_components_connected then
			tubingSystemPhase := SET_SALINE_LEVELS
		endif

	rule r_set_saline_levels =
		if saline_levels_set then
			tubingSystemPhase := INSERT_BLOODLINES
		endif

	rule r_insert_bloodlines =
		if blood_line_insert then
			tubingSystemPhase := PRIMING
		endif

	rule r_priming =
		if bp_status_der = STOP then
			bp_status := START
		else
			if bp_fill_fluid and bp_rate_rinsing_150 then
				par
					bp_status := STOP
					tubingSystemPhase := CONNECT_AV_ENDS
				endpar
			endif
		endif

	rule r_connect_av_ends =
		if av_ends_connected then
			prepPhase := PREPARE_HEPARIN
		endif

	macro rule r_install_tubing_system =
		par
			if tubingSystemPhase = CONNECT_AV_TUBES then
				r_connect_av_tubes[]
			endif
			if tubingSystemPhase = CONNECT_ALL_COMP then
				r_connect_all_comp[]
			endif
			if tubingSystemPhase = SET_SALINE_LEVELS then
				r_set_saline_levels[]
			endif
			if tubingSystemPhase = INSERT_BLOODLINES then
				r_insert_bloodlines[]
			endif
			if tubingSystemPhase = PRIMING then
				r_priming[]
			endif
			if tubingSystemPhase = CONNECT_AV_ENDS then
				r_connect_av_ends[]
			endif
		endpar

	macro rule r_prepare_heparin =
		if heparin_prepared then
			prepPhase := SET_TREAT_PARAM
		endif

	macro rule r_set_blood_conductivity =
		r_insert_param[BIC_AC, blood_conductivity]

	macro rule r_set_bic_ac =
		r_insert_param[BIC_CONDUCTIVITY, bic_ac, bic_ac_contr]

	macro rule r_set_bic_conductivity =
		r_insert_param[DF_TEMP, bic_conductivity]

	macro rule r_set_df_temp =
		r_insert_param[DF_FLOW, df_temp]

	macro rule r_set_df_flow =
		r_insert_param[UF_VOLUME, df_flow]

	macro rule r_set_uf_volume =
		r_insert_param[THERAPY_TIME, uf_volume]

	macro rule r_set_therapy_time =
		r_insert_time[MIN_UF_RATE, therapy_time_hrs, therapy_time_mins]

	macro rule r_set_min_uf_rate =
		r_insert_param[MAX_UF_RATE, min_uf_rate]

	macro rule r_set_max_uf_rate =
		r_insert_param[MAX_AP, max_uf_rate]

	macro rule r_set_max_ap =
		r_insert_param[DELTA_AP, max_ap]

	macro rule r_set_delta_ap =
		r_insert_param[PERC_DELTA_TMP, delta_ap]

	macro rule r_set_perc_delta_tmp =
		r_insert_param[LIMITS_TMP, perc_delta_tmp]

	macro rule r_set_limits_tmp =
		r_insert_param[MAX_TMP, limits_tmp, limits_tmp_contr]

	macro rule r_set_max_tmp =
		r_insert_param[EXTENDED_TMP, max_tmp]

	macro rule r_set_extended_tmp =
		r_insert_param[MAX_BEP, extended_tmp_limit, extended_tmp_limit_contr]

	macro rule r_set_max_bep =
		r_insert_param[STOP_TIME_H, max_bep]

	macro rule r_set_h_stop_time =
		r_insert_time[BOLUS_VOLUME_H, stop_time_hrs_h, stop_time_mins_h]

	macro rule r_set_h_bolus_volume =
		r_insert_param[RATE_H, bolus_volume_h]

	macro rule r_set_h_rate =
		r_insert_param[ACTIVATION_H, rate_h]

	macro rule r_set_h_activation =
		r_insert_param[SYRINGE_TYPE, activation_h, activation_h_contr]

	macro rule r_set_syringe_type =
		r_insert_param[RINSE_DIALYZER, syringe_type, syringe_type_contr]

	macro rule r_set_treatment_param =
		par
			if treatmentParam = BLOOD_CONDUCTIVITY then
				r_set_blood_conductivity[]
			endif
			if treatmentParam = BIC_AC then
				r_set_bic_ac[]
			endif
			if treatmentParam = BIC_CONDUCTIVITY then
				r_set_bic_conductivity[]
			endif
			if treatmentParam = DF_TEMP then
				r_set_df_temp[]
			endif
			if treatmentParam = DF_FLOW then
				r_set_df_flow[]
			endif
			if treatmentParam = UF_VOLUME then
				r_set_uf_volume[]
			endif
			if treatmentParam = THERAPY_TIME then
				r_set_therapy_time[]
			endif
			if treatmentParam = MIN_UF_RATE then
				r_set_min_uf_rate[]
			endif
			if treatmentParam = MAX_UF_RATE then
				r_set_max_uf_rate[]
			endif
			if treatmentParam = MAX_AP then
				r_set_max_ap[]
			endif
			if treatmentParam = DELTA_AP then
				r_set_delta_ap[]
			endif
			if treatmentParam = PERC_DELTA_TMP then
				r_set_perc_delta_tmp[]
			endif
			if treatmentParam = LIMITS_TMP then
				r_set_limits_tmp[]
			endif
			if treatmentParam = MAX_TMP then
				r_set_max_tmp[]
			endif
			if treatmentParam = EXTENDED_TMP then
				r_set_extended_tmp[]
			endif
			if treatmentParam = MAX_BEP then
				r_set_max_bep[]
			endif
			if treatmentParam = STOP_TIME_H then
				r_set_h_stop_time[]
			endif
			if treatmentParam = BOLUS_VOLUME_H then
				r_set_h_bolus_volume[]
			endif
			if treatmentParam = RATE_H then
				r_set_h_rate[]
			endif
			if treatmentParam = ACTIVATION_H then
				r_set_h_activation[]
			endif
			if treatmentParam = SYRINGE_TYPE then
				r_set_syringe_type[]
			endif
		endpar

	rule r_connect_dialyzer =
		if not(error(DF_PREP)) then
			if preparing_DF then
				if stop_DF_preparation then
					preparing_DF := false
				endif
			else
				if dialyzer_connected then
					par
						rinsePhase := FILL_ART_CHAMBER
						dialyzer_connected_contr := true
					endpar
				endif
			endif
		endif

	rule r_fill_art_chamber =
		if bp_status_der = STOP then
			bp_status := START
		else
			if arterial_chamber_filled then
				rinsePhase := FILL_VEN_CHAMBER
			endif
		endif

	rule r_fill_ven_chamber =
		if venous_chamber_fill then
			rinsePhase := FILL_DIALYZER
		endif

	rule r_fill_dialyzer =
		if dialyzer_filled then
			par
				bp_status := STOP
				phase := INITIATION
			endpar
		endif

	macro rule r_rinse_dialyzer =
		if not(error(TEMP_HIGH)) then
			par
				if rinsePhase = CONNECT_DIALYZER then
					r_connect_dialyzer[]
				endif
				if rinsePhase = FILL_ART_CHAMBER then
					r_fill_art_chamber[]
				endif
				if rinsePhase = FILL_VEN_CHAMBER then
					r_fill_ven_chamber[]
				endif
				if rinsePhase = FILL_DIALYZER then
					r_fill_dialyzer[]
				endif
			endpar
		endif

	macro rule r_exec_preparation =
		par
			if prepPhase = AUTO_TEST then
				r_run_automatic_test[]
			endif
			if prepPhase = CONNECT_CONCENTRATE then
				r_connect_concentrate[]
			endif
			if prepPhase = SET_RINSING_PARAM then
				r_set_rinsing_param[]
			endif
			if prepPhase = TUBING_SYSTEM then
				r_install_tubing_system[]
			endif
			if prepPhase = PREPARE_HEPARIN then
				r_prepare_heparin[]
			endif
			if prepPhase = SET_TREAT_PARAM then
				r_set_treatment_param[]
			endif
			if prepPhase = RINSE_DIALYZER then
				r_rinse_dialyzer[]
			endif
		endpar

	macro rule r_run_preparation =
		par
			r_check_preparation[]
			r_exec_preparation[]
		endpar

	rule r_check_temp_low =
		if not(error(TEMP_LOW)) then
			if current_temp = TOOLOW then
				par
					error(TEMP_LOW) := true
					alarm(TEMP_LOW) := true
				endpar
			endif
		endif

	rule r_check_BP_rotation_dir =
		if not(error(BP_ROTATION_DIR)) then
			if bp_rotates_back then
				par
					error(BP_ROTATION_DIR) := true
					alarm(BP_ROTATION_DIR) := true
				endpar
			endif
		endif

	rule r_check_bp_less_flow =
		if not(error(BP_LESS_FLOW)) then
			if machine_status_der = MAIN_FLOW then
				if current_bp_flow = TOOLOW then//IS LESS THAN 70% OF THE SET FLOW
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
		if not(error(BP_NO_FLOW)) then
			if not(detected_blood_flow) and passed120Sec then
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
			if bp_status_der = START then
				par
					r_check_BP_rotation_dir[]
					r_check_bp_less_flow[]
					r_check_bp_no_flow_err[]
					r_check_SAD[]
				endpar
			endif
		endpar

	rule r_conn_arterially =
		if art_connected then
			par
				patientPhase := START_BP
				art_connected_contr := true
			endpar
		endif

	rule r_start_bp =
		if start_stop_button then
			if not(error_bp) then
				par
					patientPhase := BLOOD_FLOW
					bp_status := START
				endpar
			endif
		endif

	macro rule r_set_blood_flow =
		if not(ven_connected_contr) then
			r_insert_param[FILL_TUBING, blood_flow_conn]
		else
			r_insert_param[END_CONN, blood_flow_conn]
		endif

	rule r_fill_tubing =
		if not(error_bp) then
			if blood_on_VRD or conn_infuse_set_volume = PERMITTED then//S4
				par
					patientPhase := CONN_VEN
					bp_status := STOP
				endpar
			endif
		endif

	rule r_conn_venously =
		if ven_connected then
			par
				patientPhase := START_BP
				ven_connected_contr := true
			endpar
		endif

	rule r_end_connection =
		par
			machine_state := MAIN_FLOW
			bicarbonate_status := true //is running
			//signal_lamp := GREEN
			initPhase := THERAPY_RUNNING
		endpar

	rule r_patient_connection =
		par
			if patientPhase = CONN_ART then
				r_conn_arterially[]
			endif
			if patientPhase = START_BP then
				r_start_bp[]
			endif
			if patientPhase = BLOOD_FLOW then
				r_set_blood_flow[]
			endif
			if patientPhase = FILL_TUBING then
				r_fill_tubing[]
			endif
			if patientPhase = CONN_VEN then
				r_conn_venously[]
			endif
			if patientPhase = END_CONN then
				r_end_connection[]
			endif
		endpar

	rule r_check_conn_rein_press_up_limit ($err in AlarmErrorType, $currentP in Value, $passedSec in Boolean)=
		if not(error($err)) then
			if $currentP = HIGH and $passedSec then
				par
					error($err) := true
					alarm($err) := true
				endpar
			endif
		endif

	macro rule r_check_conn_vp_up_limit =
		r_check_conn_rein_press_up_limit[CONN_VP_UP, current_vp, passed3Sec]

	rule r_check_conn_rein_press_low_limit ($err in AlarmErrorType, $currentP in Value, $passedSec in Boolean) =
		if not(error($err)) then
			if $currentP = TOOLOW and $passedSec then
				par
					error($err) := true
					alarm($err) := true
				endpar
			endif
		endif

	rule r_check_conn_rein_press_low_limit ($err in AlarmErrorType, $currentP in Value, $passedSec in Boolean, $errbf in Boolean, $errpress in Boolean) =
		if not(error($err)) then
			if $currentP = TOOLOW and $passedSec then
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
		if not(error(FILL_BLOOD_VOL)) then
			if current_fill_blood_vol = HIGH then
				par
					error(FILL_BLOOD_VOL) := true
					alarm(FILL_BLOOD_VOL) := true
				endpar
			endif
		endif

	rule r_check_patient =
		if bp_status_der = START then
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
		if activation_h_contr then
			par
				therapyPhase := THERAPY_EXEC
				heparin_running := true
			endpar
		endif

	rule r_check_heparin =
		if not(error(HEPARIN_DIR)) then
			if reverse_dir_heparin then
				par
					error(HEPARIN_DIR) := true
					alarm(HEPARIN_DIR) := true
					heparin_running := false
				endpar
			endif
		endif

	macro rule r_run_heparin =
		if heparin_running then
			par
				r_check_heparin[]
				if time_heparin then
					heparin_running := false
				endif
			endpar
		endif

	rule r_treatment_min_UF =
		if not(treatment_min_uf_rate_contr) then
			if treatment_min_uf_rate then
				treatment_min_uf_rate_contr := true
			endif
		endif

	rule r_interrupt =
		if interrupt_dialysis then
			par
				therapyPhase := THERAPY_END
				machine_state := BYPASS // action of the machine
			endpar
		endif

	rule r_start_arterial_bolus =
		if start_arterial_bolus then
			arterialBolusPhase := SET_ARTERIAL_BOLUS_VOLUME
		endif

	rule r_set_arterial_bolus_volume =
		if art_bolus_volume = PERMITTED then
			par
				bp_status := STOP
				arterialBolusPhase := CONNECT_SOLUTION
			endpar
		endif

	rule r_connect_solution =
		if saline_solution_conn then
			par
				arterialBolusPhase := RUNNING_SOLUTION
				bp_status := START
				ap_limits_set := false
				vp_limits_set := false
			endpar
		endif

	rule r_running_solution =
		if not(alarm(ARTERIAL_BOLUS_END)) then
			if current_art_bolus_volume = PERMITTED then
				par
					arterialBolusPhase := WAIT_SOLUTION
					alarm(ARTERIAL_BOLUS_END) := true
				endpar
			endif
		endif

	rule r_check_arterial_bolus =
		if current_art_bolus_volume = HIGH then//ml
			par
				alarm(ARTERIAL_BOLUS) := true
				error(ARTERIAL_BOLUS) := true
			endpar
		endif

	rule r_run_arterial_bolus =
		if not(error(ARTERIAL_BOLUS)) then
			par
				r_running_solution[]
				r_check_arterial_bolus[]
			endpar
		endif

	macro rule r_arterial_bolus =
		par
			if arterialBolusPhase = WAIT_SOLUTION then
				r_start_arterial_bolus[]
			endif
			if arterialBolusPhase = SET_ARTERIAL_BOLUS_VOLUME then
				r_set_arterial_bolus_volume[]
			endif
			if arterialBolusPhase = CONNECT_SOLUTION then
				r_connect_solution[]
			endif
			if arterialBolusPhase = RUNNING_SOLUTION then
				r_run_arterial_bolus[]
			endif
		endpar

	rule r_therapy_end_time =
		if time_therapy then
			therapyPhase := THERAPY_END
		endif

	rule r_update_therapy_time =
		if therapy_time_hrs = PERMITTED and therapy_time_mins = PERMITTED then
			update_th_time := false
		endif

	rule r_update_blood_flow =
		par
			if update_blood_flow then
				if blood_flow_conn = PERMITTED then
					update_th_time := true
				endif
			endif
			if update_th_time and not(update_blood_flow) then
				r_update_therapy_time[]
			endif
		endpar

	rule r_set_ap_vp_limits =
		if arterialBolusPhase != CONNECT_SOLUTION then
			par
				if not(ap_limits_set) then
					if ap_limit_low = PERMITTED and ap_limit_up = PERMITTED then
						ap_limits_set := true
					endif
				endif
				if not(vp_limits_set) then
					if vp_limit_low = PERMITTED and vp_limit_up = PERMITTED then
						vp_limits_set := true
					endif
				endif
			endpar
		endif

	rule r_update_ap_limits =
		if current_ap = PERMITTED then
			skip
		endif

	rule r_update_vp_limits =
		if current_vp = PERMITTED then
			skip
		endif

	macro rule r_monitor_ap_vp_limits =
		if ap_limits_set and vp_limits_set then
			par
				r_update_ap_limits[]
				r_update_vp_limits[]
			endpar
		else
			if passed10Sec then
				r_set_ap_vp_limits[]
			endif
		endif

	rule r_check_init_press_low_limit ($press in AlarmErrorType, $curr in Value)=
		if not(error($press)) then
			if $curr = TOOLOW then
				par
					error($press) := true
					alarm($press) := true
				endpar
			endif
		endif

	rule r_check_init_press_up_limit ($press in AlarmErrorType, $curr in Value)=
		if not(error($press)) then
			if $curr = HIGH then
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
		if not(error(INIT_AP_LOW)) then
			if current_ap = TOOLOW then
				par
					error(INIT_AP_LOW) := true
					alarm(INIT_AP_LOW) := true
					bf_err_ap_low := true
					reset_err_pres_ap_low := true
				endpar
			endif
		endif

	rule r_check_UF_rate =
		if not(error(UF_RATE)) then
			if current_UF_rate = HIGH then
				par
					error(UF_RATE) := true
					alarm(UF_RATE) := true
				endpar
			endif
		endif

	rule r_check_UF_dir =
		if not(error(UF_DIR)) then
			if uf_dir_backwards then
				par
					error(UF_DIR) := true
					alarm(UF_DIR) := true
				endpar
			endif
		endif

	rule r_check_UF_volume =
		if not(alarm(UF_VOLUME_ERR)) then
			if current_UF_volume = HIGH then
				alarm(UF_VOLUME_ERR) := true
			endif
		endif

	rule r_check_UF_bypass =
		if not(alarm(UF_BYPASS)) then
			if machine_status_der = BYPASS then
				alarm(UF_BYPASS) := true
			endif
		endif

	macro rule r_check_therapy_exec =
		par
			if bp_status_der = START then
				if ap_limits_set and vp_limits_set then
					par
						r_check_init_vp_up_limit[]
						r_check_init_ap_up_limit[]
						r_check_init_vp_low_limit[]
						r_check_init_ap_low_limit[]
					endpar
				endif
			endif
			if bicarbonate_status_der then
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
			if not(error_therapy) then
				par
					r_run_heparin[]
					r_monitor_ap_vp_limits[]
					r_treatment_min_UF[]
					if not(error_bp) then
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
			if therapyPhase = START_HEPARIN then
				r_start_heparin[]
			endif
			if therapyPhase = THERAPY_EXEC then
				r_therapy_exec[]
			endif
			if therapyPhase = THERAPY_END then
				r_therapy_end[]
			endif
		endpar

	macro rule r_run_initiation =
		par
			r_check_initiation_phase[]
			if initPhase = CONNECT_PATIENT then
				r_initiate_patient[]
			endif
			if initPhase = THERAPY_RUNNING then
				r_run_therapy[]
			endif
		endpar

	rule r_remove_arterially =
		if art_removed then
			par
				reinfusionPhase := CONN_SALINE
				art_connected_contr := false
			endpar
		endif

	macro rule r_connect_saline =
		if saline_conn then
			reinfusionPhase := START_SALINE_INF
		endif

	macro rule r_start_saline_inf =
		par
			bp_status := START // action of the machine
			reinfusionPhase := RUN_SALINE_INF
		endpar

	macro rule r_run_saline_inf =
		if saline_on_VRD then
			par
				reinfusionPhase := CHOOSE_NEXT_REINF_STEP
				bp_status := STOP
			endpar
		endif

	macro rule r_choose_next_reinf_step =
		if new_saline_reinfusion then
			reinfusionPhase := START_SALINE_REIN
		else
			reinfusionPhase := REMOVE_VEN
		endif

	macro rule r_start_saline_reinf =
		par
			bp_status := START // action of the machine
			reinfusionPhase := RUN_SALINE_REIN
		endpar

	macro rule r_run_saline_reinf =
		if volume_saline_inf_400 or passed5Min then
			par
				reinfusionPhase := CHOOSE_NEXT_REINF_STEP
				bp_status := STOP
			endpar
		endif

	macro rule r_remove_venously =
		if ven_removed then
			par
				endingPhase := DRAIN_DIALYZER
				ven_connected_contr := false
			endpar
		endif

	macro rule r_run_reinfusion =
		par
			if reinfusionPhase = REMOVE_ART then
				r_remove_arterially[]
			endif
			if reinfusionPhase = CONN_SALINE then
				r_connect_saline[]
			endif
			if reinfusionPhase = START_SALINE_INF then
				r_start_saline_inf[]
			endif
			if reinfusionPhase = CHOOSE_NEXT_REINF_STEP then
				r_choose_next_reinf_step[]
			endif
			if reinfusionPhase = RUN_SALINE_INF then
				r_run_saline_inf[]
			endif
			if reinfusionPhase = START_SALINE_REIN then
				r_start_saline_reinf[]
			endif
			if reinfusionPhase = RUN_SALINE_REIN then
				r_run_saline_reinf[]
			endif
			if reinfusionPhase = REMOVE_VEN then
				r_remove_venously[]
			endif
		endpar

	macro rule r_check_rein_vp_up_limit =
		r_check_conn_rein_press_up_limit[REIN_VP_UP, current_vp, passed3Sec]//>+350

	macro rule r_check_rein_ap_low_limit =
		r_check_conn_rein_press_low_limit[REIN_AP_LOW, current_ap, passed1Sec]//< -350

	macro rule r_check_reinfusion =
		if bp_status_der = START then
			par
				r_check_rein_vp_up_limit[]
				r_check_rein_ap_low_limit[]
				r_check_SAD[]
			endpar
		endif

	macro rule r_reinfusion =
		if not(error_rein_press) then
			par
				r_run_reinfusion[]
				r_check_reinfusion[]
			endpar
		endif

	macro rule r_drain_dialyzer =
		if dialyzer_drained then
			par
				endingPhase := EMPTY_CARTRIDGE
				empty_dialyzer := true
			endpar
		endif

	macro rule r_empty_cartridge =
		if cartridge_emtpy then
			endingPhase := THERAPY_OVERVIEW
		endif

	macro rule r_therapy_overview =
		skip

	macro rule r_run_ending=
		par
			if endingPhase = REINFUSION then
				r_reinfusion[]
			endif
			if endingPhase = DRAIN_DIALYZER then
				r_drain_dialyzer[]
			endif
			if endingPhase = EMPTY_CARTRIDGE then
				r_empty_cartridge[]
			endif
			if endingPhase = THERAPY_OVERVIEW then
				r_therapy_overview[]
			endif
		endpar

	macro rule r_run_dialysis =
		par
			if phase = PREPARATION then
				r_run_preparation[]
			endif
			if phase = INITIATION then
				r_run_initiation[]
			endif
			if phase = ENDING then
				r_run_ending[]
			endif
		endpar

	//PROPERTIES
	//S1 (already specified in HemodialysisRef2_MC)
	LTLSPEC ltlSpec_S1: g(art_connected_contr iff ven_connected_contr) //false

	//S4 (already specified in HemodialysisRef2_MC)
	LTLSPEC ltlSpec_S4: g((phase = INITIATION and initPhase = CONNECT_PATIENT and patientPhase = FILL_TUBING and not(err_patient_conn) and not(error_bp) and (blood_on_VRD or conn_infuse_set_volume = PERMITTED)) implies x(bp_status_der = STOP and patientPhase = CONN_VEN)) //true

	//S5 (already specified in HemodialysisRef2_MC)
	//LTLSPEC g((art_connected_contr or ven_connected_contr) implies (phase = INITIATION)) //FALSE
	LTLSPEC ltlSpec_S5a: g((not art_connected_contr and x(art_connected_contr)) implies (phase = INITIATION))
	LTLSPEC ltlSpec_S5b: g((not ven_connected_contr and x(ven_connected_contr)) implies (phase = INITIATION))

	//S11
	CTLSPEC ctlSpec_S11: ag(empty_dialyzer implies ag(bp_status_der = STOP)) //true
	LTLSPEC ltlSpec_S11: g(empty_dialyzer implies g(bp_status_der = STOP)) //true


	//R1 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R1: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and arterialBolusPhase = RUNNING_SOLUTION and not(error_therapy) and not(error_bp) and not(error(ARTERIAL_BOLUS)) and current_art_bolus_volume = HIGH) implies ax(error(ARTERIAL_BOLUS) and alarm(ARTERIAL_BOLUS)))
	LTLSPEC ltlSpec_R1: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and arterialBolusPhase = RUNNING_SOLUTION and not(error_therapy) and not(error_bp) and not(error(ARTERIAL_BOLUS)) and current_art_bolus_volume = HIGH) implies x(error(ARTERIAL_BOLUS) and alarm(ARTERIAL_BOLUS)))

	//R2 and R14 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R2: ag((phase = INITIATION and bp_status_der = START and not(error(BP_NO_FLOW)) and (not(detected_blood_flow) and passed120Sec)) implies ax(error(BP_NO_FLOW) and alarm(BP_NO_FLOW)))
	LTLSPEC ltlSpec_R2: g((phase = INITIATION and bp_status_der = START and not(error(BP_NO_FLOW)) and (not(detected_blood_flow) and passed120Sec)) implies x(error(BP_NO_FLOW) and alarm(BP_NO_FLOW)))

	//R3 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R3: ag((phase = INITIATION and bp_status_der = START and machine_status_der = MAIN_FLOW and not(error(BP_LESS_FLOW)) and current_bp_flow = TOOLOW) implies ax(error(BP_LESS_FLOW) and alarm(BP_LESS_FLOW)))
	LTLSPEC ltlSpec_R3: g((phase = INITIATION and bp_status_der = START and machine_status_der = MAIN_FLOW and not(error(BP_LESS_FLOW)) and current_bp_flow = TOOLOW) implies x(error(BP_LESS_FLOW) and alarm(BP_LESS_FLOW)))

	//R4 and R17 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R4: ag((phase = INITIATION and bp_status_der = START and not(error(BP_ROTATION_DIR)) and bp_rotates_back) implies ax(error(BP_ROTATION_DIR) and alarm(BP_ROTATION_DIR)))
	LTLSPEC ltlSpec_R4: g((phase = INITIATION and bp_status_der = START and not(error(BP_ROTATION_DIR)) and bp_rotates_back) implies x(error(BP_ROTATION_DIR) and alarm(BP_ROTATION_DIR)))

	//R5 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R5: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_VP_UP)) and current_vp = HIGH) implies ax(error(INIT_VP_UP) and alarm(INIT_VP_UP)))
	LTLSPEC ltlSpec_R5: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_VP_UP)) and current_vp = HIGH) implies x(error(INIT_VP_UP) and alarm(INIT_VP_UP)))

	//R6 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R6: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_AP_UP)) and current_ap = HIGH) implies ax(error(INIT_AP_UP) and alarm(INIT_AP_UP)))
	LTLSPEC ltlSpec_R6: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_AP_UP)) and current_ap = HIGH) implies x(error(INIT_AP_UP) and alarm(INIT_AP_UP)))

	//R7 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R7: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_VP_LOW)) and current_vp = TOOLOW) implies ax(error(INIT_VP_LOW) and alarm(INIT_VP_LOW)))
	LTLSPEC ltlSpec_R7: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_VP_LOW)) and current_vp = TOOLOW) implies x(error(INIT_VP_LOW) and alarm(INIT_VP_LOW)))

	//R8 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R8: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_AP_LOW)) and current_ap = TOOLOW) implies ax(error(INIT_AP_LOW) and alarm(INIT_AP_LOW)))
	LTLSPEC ltlSpec_R8: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bp_status_der = START and ap_limits_set and vp_limits_set and not(error(INIT_AP_LOW)) and current_ap = TOOLOW) implies x(error(INIT_AP_LOW) and alarm(INIT_AP_LOW)))

	//R9 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R9: ag((phase = INITIATION and initPhase = CONNECT_PATIENT and not(err_patient_conn) and not(error_bp) and bp_status_der = START and not(error(CONN_VP_UP)) and current_vp = HIGH and passed3Sec) implies ax(error(CONN_VP_UP) and alarm(CONN_VP_UP)))
	LTLSPEC ltlSpec_R9: g((phase = INITIATION and initPhase = CONNECT_PATIENT and not(err_patient_conn) and not(error_bp) and bp_status_der = START and not(error(CONN_VP_UP)) and current_vp = HIGH and passed3Sec) implies x(error(CONN_VP_UP) and alarm(CONN_VP_UP)))

	//R10 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R10: ag((phase = INITIATION and initPhase = CONNECT_PATIENT and not(err_patient_conn) and not(error_bp) and bp_status_der = START and not(error(CONN_VP_LOW)) and current_vp = TOOLOW and passed3Sec) implies ax(error(CONN_VP_LOW) and alarm(CONN_VP_LOW)))
	LTLSPEC ltlSpec_R10: g((phase = INITIATION and initPhase = CONNECT_PATIENT and not(err_patient_conn) and not(error_bp) and bp_status_der = START and not(error(CONN_VP_LOW)) and current_vp = TOOLOW and passed3Sec) implies x(error(CONN_VP_LOW) and alarm(CONN_VP_LOW)))

	//R11 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R11: ag((phase = INITIATION and initPhase = CONNECT_PATIENT and not(err_patient_conn) and not(error_bp) and bp_status_der = START and not(error(CONN_AP_LOW)) and current_ap = TOOLOW and passed1Sec) implies ax(error(CONN_AP_LOW) and alarm(CONN_AP_LOW)))
	LTLSPEC ltlSpec_R11: g((phase = INITIATION and initPhase = CONNECT_PATIENT and not(err_patient_conn) and not(error_bp) and bp_status_der = START and not(error(CONN_AP_LOW)) and current_ap = TOOLOW and passed1Sec) implies x(error(CONN_AP_LOW) and alarm(CONN_AP_LOW)))

	//R12
	CTLSPEC ctlSpec_R12: ag((phase = ENDING and endingPhase = REINFUSION and not(error_rein_press) and not(error_bp) and bp_status_der = START and not(error(REIN_VP_UP)) and current_vp = HIGH and passed3Sec) implies ax(error(REIN_VP_UP) and alarm(REIN_VP_UP)))
	LTLSPEC ltlSpec_R12: g((phase = ENDING and endingPhase = REINFUSION and not(error_rein_press) and not(error_bp) and bp_status_der = START and not(error(REIN_VP_UP)) and current_vp = HIGH and passed3Sec) implies x(error(REIN_VP_UP) and alarm(REIN_VP_UP)))

	//R13
	CTLSPEC ctlSpec_R13: ag((phase = ENDING and endingPhase = REINFUSION and not(error_rein_press) and not(error_bp) and bp_status_der = START and not(error(REIN_AP_LOW)) and current_ap = TOOLOW and passed1Sec) implies ax(error(REIN_AP_LOW) and alarm(REIN_AP_LOW)))
	LTLSPEC ltlSpec_R13: g((phase = ENDING and endingPhase = REINFUSION and not(error_rein_press) and not(error_bp) and bp_status_der = START and not(error(REIN_AP_LOW)) and current_ap = TOOLOW and passed1Sec) implies x(error(REIN_AP_LOW) and alarm(REIN_AP_LOW)))

	//R15 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R15: ag((phase = INITIATION and initPhase = CONNECT_PATIENT and bp_status_der = START and not(err_patient_conn) and not(error_bp) and not(error(FILL_BLOOD_VOL)) and current_fill_blood_vol = HIGH) implies ax(error(FILL_BLOOD_VOL) and alarm(FILL_BLOOD_VOL)))
	LTLSPEC ltlSpec_R15: g((phase = INITIATION and initPhase = CONNECT_PATIENT and bp_status_der = START and not(err_patient_conn) and not(error_bp) and not(error(FILL_BLOOD_VOL)) and current_fill_blood_vol = HIGH) implies x(error(FILL_BLOOD_VOL) and alarm(FILL_BLOOD_VOL)))

	//R18-R19 (already specified in HemodialysisRef1_MC)
	CTLSPEC ctlSpec_18and19: ag((phase = PREPARATION and prepPhase = RINSE_DIALYZER and dialyzer_connected_contr and not(error(DF_PREP)) and preparing_DF and not(detect_bicarbonate)) implies ax(error(DF_PREP) and alarm(DF_PREP) and not(dialyzer_connected_status)))
	LTLSPEC ltlSpec_18and19: g((phase = PREPARATION and prepPhase = RINSE_DIALYZER and dialyzer_connected_contr and not(error(DF_PREP)) and preparing_DF and not(detect_bicarbonate)) implies x(error(DF_PREP) and alarm(DF_PREP) and not(dialyzer_connected_status)))

	//R20 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R20: ag(((phase = INITIATION and not(error(TEMP_HIGH)) and current_temp = HIGH) or (phase = PREPARATION and dialyzer_connected_contr and prepPhase = RINSE_DIALYZER and not(error(TEMP_HIGH)) and current_temp = HIGH)) implies ax(error(TEMP_HIGH) and alarm(TEMP_HIGH) and not(dialyzer_connected_status)))
	LTLSPEC ltlSpec_R20: g(((phase = INITIATION and not(error(TEMP_HIGH)) and current_temp = HIGH) or (phase = PREPARATION and dialyzer_connected_contr and prepPhase = RINSE_DIALYZER and not(error(TEMP_HIGH)) and current_temp = HIGH)) implies x(error(TEMP_HIGH) and alarm(TEMP_HIGH) and not(dialyzer_connected_status)))

	//R21 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R21: ag((phase = INITIATION and not(error(TEMP_LOW)) and current_temp = TOOLOW) implies ax(error(TEMP_LOW) and alarm(TEMP_LOW)))
	LTLSPEC ltlSpec_R21: g((phase = INITIATION and not(error(TEMP_LOW)) and current_temp = TOOLOW) implies x(error(TEMP_LOW) and alarm(TEMP_LOW)))

	//R22 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R22: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and heparin_running and not(error(HEPARIN_DIR)) and reverse_dir_heparin) implies ax(error(HEPARIN_DIR) and alarm(HEPARIN_DIR)))
	LTLSPEC ltlSpec_R22: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and heparin_running and not(error(HEPARIN_DIR)) and reverse_dir_heparin) implies x(error(HEPARIN_DIR) and alarm(HEPARIN_DIR)))

	//R23-R32 (modified w.r.t. the version in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R23and32: ag((((phase = PREPARATION and prepPhase = TUBING_SYSTEM) or (phase = INITIATION and bp_status_der = START) or (phase = ENDING and endingPhase = REINFUSION and not(error_rein_press) and bp_status_der = START)) and (passed1Msec and currentSAD != PERMITTED and current_air_vol != PERMITTED and not(error(SAD_ERR)))) implies ax(error(SAD_ERR) and alarm(SAD_ERR)))
	LTLSPEC ltlSpec_R23and32: g((((phase = PREPARATION and prepPhase = TUBING_SYSTEM) or (phase = INITIATION and bp_status_der = START) or (phase = ENDING and endingPhase = REINFUSION and not(error_rein_press) and bp_status_der = START)) and (passed1Msec and currentSAD != PERMITTED and current_air_vol != PERMITTED and not(error(SAD_ERR)))) implies x(error(SAD_ERR) and alarm(SAD_ERR)))

	//R33 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R33: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bicarbonate_status_der and not(error(UF_RATE)) and current_UF_rate = HIGH) implies ax(error(UF_RATE) and alarm(UF_RATE)))
	LTLSPEC ltlSpec_R33: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bicarbonate_status_der and not(error(UF_RATE)) and current_UF_rate = HIGH) implies x(error(UF_RATE) and alarm(UF_RATE)))

	//R34 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R34: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bicarbonate_status_der and not(error(UF_DIR)) and uf_dir_backwards) implies ax(error(UF_DIR) and alarm(UF_DIR)))
	LTLSPEC ltlSpec_R34: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bicarbonate_status_der and not(error(UF_DIR)) and uf_dir_backwards) implies x(error(UF_DIR) and alarm(UF_DIR)))

	//R35 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R35: ag((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bicarbonate_status_der and not(alarm(UF_VOLUME_ERR)) and current_UF_volume = HIGH) implies ax(alarm(UF_VOLUME_ERR)))
	LTLSPEC ltlSpec_R35: g((phase = INITIATION and initPhase = THERAPY_RUNNING and therapyPhase = THERAPY_EXEC and not(error_therapy) and bicarbonate_status_der and not(alarm(UF_VOLUME_ERR)) and current_UF_volume = HIGH) implies x(alarm(UF_VOLUME_ERR)))

	//R36 (already specified in HemodialysisRef2_MC)
	CTLSPEC ctlSpec_R36: ag((machine_status_der = BYPASS) implies not(df_flow_state))
	LTLSPEC ltlSpec_R36: g((machine_status_der = BYPASS) implies not(df_flow_state))

	main rule r_Main =
		par
			r_run_dialysis[]
			if errorExist then
				r_error[]
			endif
			if alarmExist then
				r_alarm[]
			endif
		endpar

default init s0:
	function phase = PREPARATION
	function prepPhase = AUTO_TEST
	function rinsingParam = FILLING_BP_RATE
	function treatmentParam = BLOOD_CONDUCTIVITY
	function rinsePhase = CONNECT_DIALYZER
	function tubingSystemPhase = CONNECT_AV_TUBES
	function bp_status = STOP
	function dialyzer_connected_contr = false	
	function machine_state = BYPASS	
	function preparing_DF = false	
	function bic_ac_contr = BICARBONATE
	function error($t in AlarmErrorType) = false
	function alarm($t in AlarmErrorType) = false
	function signal_lamp= YELLOW
	function limits_tmp_contr = false
	function extended_tmp_limit_contr = false
	function activation_h_contr = false
	function syringe_type_contr = ST10	
	function patientPhase = CONN_ART
	function arterialBolusPhase = WAIT_SOLUTION
	function therapyPhase = START_HEPARIN
	function ven_connected_contr = false
	function art_connected_contr = false
	function initPhase = CONNECT_PATIENT
	function bicarbonate_status = false
	function heparin_running = false
	function ap_limits_set = false
	function vp_limits_set = false
	function treatment_min_uf_rate_contr = false
	function blood_flow_error_bf_less = false
	function th_time_error_bf_less = false
	function update_th_time = false	
	function bf_err_ap_low = false
	function bf_err_ap_low_conn = false
	function reset_err_pres_ap_low = false
	function reset_err_pres_ap_low_conn = false	
	function endingPhase = REINFUSION
	function reinfusionPhase = REMOVE_ART
	function empty_dialyzer = false
