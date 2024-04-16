asm HemodialysisRaff1_MCReq

import ../../StandardLibrary


//NOTE IMPORTANTI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//PER IL RAFFINAMENTO INIZIALIZZARE TUTTE LE FUNZIONI CONTROLLATE!!!!!!!!!!!!!!!!!!
//AGGIUNTA: dialyzer_connected_contr per sapere se il dializatore è connesso o no

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
	enum domain ErrorAlarmType = {DF_PREP | SAD_ERR | TEMP_HIGH}
	enum domain SignalLamps = {GREEN | YELLOW | RED}
		
// FUNCTIONS
	dynamic controlled phase: Phases //Dialysis phase in which is currently the system
	dynamic controlled prepPhaseMode: PrepPhaseMode //Modes in which the system can be during preparation phase
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingSystemPhase: TubingSystemPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic controlled signal_lamp: SignalLamps //Signal lamp on the monitor
	
	//Time
	dynamic monitored passed1Msec: Boolean //True --> 1 millisecond is passed
	
	
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
	//NEW
	dynamic controlled dialyzer_connected_contr: Boolean //True--> dialyzer is connected to the machine
	dynamic monitored arterial_chamber_filled: Boolean //True--> arterial chamber is filled nearly half full
	dynamic monitored venous_chamber_fill: Boolean //True--> venous chamber is filled up to approx. 1 cm from upper edge
	dynamic monitored dialyzer_filled: Boolean //True--> dialyzer is filled
	dynamic monitored stop_DF_preparation: Boolean //True--> stop DF preparation
	dynamic controlled preparing_DF: Boolean //True--> preparing DF
		
	
	derived machine_status_der: MachineState
	derived errorePerBYPASS: Boolean
	derived bp_status_der: BPStatus
	derived errorePERbpStatus: Boolean
	derived signal_lamp_der: SignalLamps
	derived errorExist: Boolean //True if exist at least one error
	derived alarmExist: Boolean  //True if exist at least one alarm
	
	dynamic controlled error: ErrorAlarmType -> Boolean //True--> error true
	dynamic controlled alarm: ErrorAlarmType -> Boolean //True--> alarm signal on
	dynamic monitored reset_alarm: Boolean // True--> reset alarm
	
	
	//Requirements
	//dynamic controlled art_connected_contr: Boolean //True--> patient connected arterially during patient connection
	//dynamic controlled ven_connected_contr: Boolean //True--> patient connected venously during patient connection
	dynamic monitored detect_bicarbonate: Boolean // True --> bicarbonate detected False --> acetate or acid detected instead of bicarbonate
	dynamic monitored current_air_vol: Value //current air volume
	dynamic monitored currentSAD: Value //current SAD measured
	dynamic monitored error_SAD_resolved: Boolean // True --> SAD error resolved
	dynamic monitored current_temp: Value // Current temperature
	
		
	
definitions:
// DOMAIN DEFINITIONS
	
	function errorePerBYPASS = error(TEMP_HIGH) or /*error(TEMP_LOW) or*/ error(DF_PREP) /*or error(UF_DIR) or alarm(UF_VOLUME_ERR)*/
	function errorePERbpStatus =/*error(BP_ROTATION_DIR) or error(BP_NO_FLOW) or error(INIT_VP_UP) or error(INIT_AP_UP) or error(INIT_VP_LOW) or error(INIT_AP_LOW) or error(CONN_VP_UP) or error(CONN_VP_LOW) or error(CONN_AP_LOW) or error(REIN_VP_UP) or error(REIN_AP_LOW) or error(FILL_BLOOD_VOL) or error(HEPARIN_DIR) or*/ error(SAD_ERR) /*or error(ARTERIAL_BOLUS)*/
	
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
		
// FUNCTION DEFINITIONS

	//function min_ap = -400
	//function max_ap = 400
	//function min_vp = -100
	//function max_vp = 400

// RULE DEFINITIONS
	rule r_check_df_prep = 
		if (error(DF_PREP) = false and preparing_DF = true) then
			if (detect_bicarbonate = false) then
				par
					error(DF_PREP) := true
					alarm(DF_PREP) := true
					//NEW
					if (dialyzer_connected_contr = true) then
						dialyzer_connected_contr := false
					endif
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
				par
					error(DF_PREP) := false
					//NEW
					if (rinsePhase = FILL_ART_CHAMBER or rinsePhase = FILL_VEN_CHAMBER or rinsePhase = FILL_DIALYZER) then
						dialyzer_connected_contr := true
					endif
				endpar
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
					
	macro rule r_error =
		par
			r_error_df_prep[]
			r_error_SAD[]
			r_error_temp_high[]
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
					par
						rinsePhase := FILL_ART_CHAMBER
						//NEW
						dialyzer_connected_contr := true
					endpar
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
	function dialyzer_connected_contr = false
	//function ven_connected_contr = false
	//function art_connected_contr = false
	function machine_state = BYPASS
	function preparing_DF = false
	function bic_ac_contr = BICARBONATE
	function error($t in ErrorAlarmType) = false
	function alarm($t in ErrorAlarmType) = false
	function signal_lamp = YELLOW
	function limits_tmp_contr = false
	function extended_tmp_limit_contr = false
	function activation_h_contr = false
	function syringe_type_contr = ST10
	