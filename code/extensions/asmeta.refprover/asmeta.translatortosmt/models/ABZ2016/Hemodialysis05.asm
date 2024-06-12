asm Hemodialysis05

import ../StandardLibrary
import ../CTLLibrary

signature:
// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
	enum domain Mode = {FILL_ART_CHAMBER | FILL_VEN_CHAMBER | PRIMING | CONNECT_AV_ENDS | OVERVIEW | CARTRIDGE | DRAIN_DIALYZER | REMOVE_VEN | WAIT_BP_STOP | RESTART_BP | WAIT_VRD_SIGNAL | REMOVE_ART | AUTO_TEST | CONNECT_CONCENTRATE | SET_RINSING_PARAM | CONNECT_AV_TUBES | SET_COMPONENTS | SET_SALINE_LEVELS | INSERT_BLOODLINES | RINSE_TEST_TUBES | PREPARE_HEPARIN | SET_TREAT_PARAM | RINSE_DIALYZER | DF_PREPARATION | CONNECT_ART | START_BP | BLOOD_FLOW | BLOOD_VRD | START_STOP_BP | CONNECT_VEN | CHANGE_STATE | BICARBONATE_RUN | THERAPY_RUNNING}
	enum domain Parameter = {FILL_BP_RATE | FILL_BP_VOLUME | RINSE_BP_RATE | DF_FLOW_RINSE | RINSE_TIME | RINSE_UF_RATE | RINSE_UF_VOLUME | BLOOD_COND | BIC_AC | BIC_COND | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | MIN_AP | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | TREATMENT_H | SYRINGE_TYPE | ENDPARAM}
	//enum domain Parameter = {FILL_BP_RATE | FILL_BP_VOLUME | RINSE_BP_RATE | DF_FLOW_RINSE | RINSE_TIME | RINSE_UF_RATE | RINSE_UF_VOLUME | BLOOD_FLOW_CONN | BLOOD_COND | BIC_AC | BIC_COND | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTEND_TMP_LIMIT | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | TREATMENT_H | SYRINGE_TYPE }
	enum domain SignalLamps = {GREEN | YELLOW}
	enum domain StartStop = {START | STOP}
	enum domain MachineState= {BYPASS | MAINFLOW}
	enum domain SyringeType = {ST10 | ST20 | ST30}
	enum domain Concentrate ={BICARBONATE | ACETATE}
	
// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled mode: Mode //Modes in which the system can be
	dynamic controlled signalLamp: SignalLamps //Signal lamp on the monitor
	dynamic monitored passed_auto_test: Boolean //True --> the machine automated self test is succesfully completed
	dynamic monitored concentrate_connected: Boolean //True --> the concentrate is connected to the HD machine
	//dynamic monitored rinsing_param_set: Boolean //True --> the rinsing parameters are set
	dynamic monitored av_tubes_connected: Boolean //True --> arterial and venous tubes are connected
	dynamic monitored components_connected: Boolean //True --> components (Fig. 2) are connected
	dynamic monitored saline_levels_set: Boolean //True --> saline levels for preparation are set
	dynamic monitored bloodlines_inserted: Boolean //True --> bloodlines are inserted
	dynamic monitored heparin_prepared: Boolean //True --> heparin pump prepared
	//dynamic monitored treat_param_set: Boolean //True --> treatment parameters are set
	dynamic controlled blood_pump: StartStop //Blood pump --> start and stop
	dynamic controlled bicarbonate: StartStop //Bicarbonate --> start and stop 
	dynamic monitored connected_art: Boolean //True --> the patient is connected arterially
	dynamic controlled connected_art_contr: Boolean //True --> the patient is connected arterially
	dynamic monitored connected_ven: Boolean //True --> the patient is connected venously
	dynamic controlled connected_ven_contr: Boolean //True --> the patient is connected venously
	dynamic monitored removed_art: Boolean //True --> the patient is removed arterially
	dynamic monitored removed_ven: Boolean //True --> the patient is removed venously	
	
	dynamic monitored blood_flow_therapy: Integer //Value of blood flow during therapy
	dynamic controlled blood_flow_therapy_contr: Integer //Value of blood flow during therapy
	dynamic controlled first_blood_flow: Boolean //True --> first call of set blood flow
	
	//dynamic controlled blood_flow_set: Boolean //True --> the blood flow of BP is set
	
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic monitored restart_reinfusion: Boolean //True --> restart reinfusion after automatic stop	
	dynamic monitored button_BP_pressed: Boolean //True --> button of BP pressed on UI 
	dynamic controlled saline_solution: Boolean //True --> physiological saline solution connected to arterial line
	dynamic controlled button_dialyzer_pressed: Boolean //True --> button of dialyzer pressed on UI 
	dynamic controlled end_therapy: Boolean //True --> therapy is finished
	dynamic controlled reinfusion_repeated: Boolean //True --> the reinfusion of physiological saline solution is done twice
	dynamic controlled empty_dialyzer: Boolean //True --> the dialyzer is emptied	
	//dynamic monitored bp_volume_reaches: Boolean //True --> BP reaches the volume set during SET_RINSING_PARAM mode
	dynamic monitored current_bp_volume_reaches: Integer //BP volume reaches, if it is equals to the value insert before stop, otherwise continue
	
	dynamic monitored av_ends_conn: Boolean //True --> the arterial and venous ends are connected	
	dynamic monitored dialyzer_balabceC_conn: Boolean //True --> the dialyzer and balance chamber are connected	
	dynamic controlled dialyzer_balanceC_conn_contr: Boolean //True --> the dialyzer and balance chamber are connected
	dynamic monitored art_chamber_filled: Boolean //True --> the arterial chamber is filled nearly half full
	dynamic monitored ven_chamber_filled: Boolean //True --> the venous chamber is filled up to approx 1cm from the upper edge
	
	dynamic controlled parameters: Parameter //state to insert parameters
	dynamic monitored syringe_type: SyringeType //Syringe type used during treatment
	dynamic controlled syringe_type_contr: SyringeType //Syringe type used during treatment
	dynamic monitored fill_bp_rate: Integer //The rate with which the blood side is filled or rinsed
	dynamic controlled fill_bp_rate_contr: Integer //The rate with which the blood side is filled or rinsed
	dynamic monitored fill_bp_volume: Integer //The BP stops after it has rinsed the blood side using the set volume
	dynamic controlled fill_bp_volume_contr: Integer //The BP stops after it has rinsed the blood side using the set volume
	dynamic monitored rinse_bp_rate: Integer //BP rate for rinsing program
	dynamic controlled rinse_bp_rate_contr: Integer //BP rate for rinsing program
	dynamic monitored df_flow_rinse: Integer //DF flow rate for rinsing program
	dynamic controlled df_flow_rinse_contr: Integer //DF flow rate for rinsing program
	dynamic monitored rinse_time: Integer //Duration of adjusted rinsing program
	dynamic controlled rinse_time_contr: Integer //Duration of adjusted rinsing program
	dynamic monitored rinse_UF_rate: Integer //UF rate when rinsing with a physiological saline solution
	dynamic controlled rinse_UF_rate_contr: Integer //UF rate when rinsing with a physiological saline solution
	dynamic monitored rinse_UF_volume: Integer //UF volume when rinsing with a physiological saline solution
	dynamic controlled rinse_UF_volume_contr: Integer //UF volume when rinsing with a physiological saline solution
	dynamic monitored blood_flow_conn: Integer //Blood flow for connecting patient
	dynamic controlled blood_flow_conn_contr: Integer //Blood flow for connecting patient
	dynamic monitored blood_cond: Real //Blood conductivity: the rate with which the blood side is filled or rinsed
	dynamic controlled blood_cond_contr: Real //Blood conductivity: the rate with which the blood side is filled or rinsed
	//dynamic monitored blood_cond: Integer //Blood conductivity: the rate with which the blood side is filled or rinsed
	//dynamic controlled blood_flow_cond_contr: Integer //Blood conductivity: the rate with which the blood side is filled or rinsed
	dynamic monitored bic_ac: Concentrate //Selection of the concentrate used during therapy
	dynamic controlled bic_ac_contr: Concentrate //Selection of the concentrate used during therapy
	dynamic monitored bic_cond: Integer //Bicarbonate conductivity
	dynamic controlled bic_cond_contr: Integer //Bicarbonate conductivity
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
	dynamic monitored min_ap: Integer //Minimum arterial pressure: lower limit
	dynamic controlled min_ap_contr: Integer //Minimum arterial pressure: lower limit
	
	dynamic monitored delta_ap: Integer //Limits window for arterial entry pressure AP. Distance to min and max AP
	dynamic controlled delta_ap_contr: Integer //Limits window for arterial entry pressure AP. Distance to min and max AP
	dynamic monitored perc_delta_tmp: Integer //Limits window for TMP in % of actual value
	dynamic controlled perc_delta_tmp_contr: Integer //Limits window for TMP in % of actual value
	dynamic monitored limits_tmp: Boolean //True--> monitoring the TMP at the dialyzer
	dynamic controlled limits_tmp_contr: Boolean //True--> monitoring the TMP at the dialyzer
	dynamic monitored max_tmp: Integer //Max TMP value
	dynamic controlled max_tmp_contr: Integer //Max TMP value
	//dynamic monitored extend_tmp_limit: Boolean //Extended TMP limit range
	//dynamic controlled extend_tmp_limit_contr: Boolean //Extended TMP limit range
	dynamic monitored stop_time_mins_h: Integer //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	dynamic controlled stop_time_mins_h_contr: Integer //The heparin pump is switched off by set time prior to the end of the therapy: minutes
	dynamic monitored stop_time_hrs_h: Integer //The heparin pump is switched off by set time prior to the end of the therapy: hours
	dynamic controlled stop_time_hrs_h_contr: Integer //The heparin pump is switched off by set time prior to the end of the therapy: hours
	dynamic monitored bolus_volume_h: Real //Bolus volume for a bolus administration during dialysis
	dynamic controlled bolus_volume_h_contr: Real //Bolus volume for a bolus administration during dialysis
	//dynamic monitored bolus_volume_h: Integer //Bolus volume for a bolus administration during dialysis
	//dynamic controlled bolus_volume_h_contr: Integer //Bolus volume for a bolus administration during dialysis
	dynamic monitored rate_h: Real //Continuous heparin rate over the entire duration of heparin administration
	dynamic controlled rate_h_contr: Real //Continuous heparin rate over the entire duration of heparin administration
	//dynamic monitored rate_h: Integer //Continuous heparin rate over the entire duration of heparin administration
	//dynamic controlled rate_h_contr: Integer //Continuous heparin rate over the entire duration of heparin administration
	dynamic monitored treatment_h: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	dynamic controlled treatment_h_contr: Boolean //True--> the heparin monitoring function is on, if it's off the tratment is without heparin
	
	dynamic monitored sensor_bicarbonate: Boolean //True--> the sensor detects bicarbonate False--> the sensor detects acid/acetate
	dynamic monitored df_preparation_end: Boolean //True--> df preparation is finished False--> df preparation is not finished
	
	
	dynamic monitored speed_sensor_BP: Integer //BP speed sensor
	dynamic controlled alarm_signal: Boolean //True--> alarm signal generated
	dynamic monitored direction_sensor_BP: Boolean //True--> the BP has the right direction
	
	dynamic monitored flow_detect: Boolean //True --> flow is detected in the EBC
	dynamic monitored actual_blood_flow: Integer //Actual blood flow detected
	
	dynamic monitored passed: Integer -> Boolean

definitions:
// DOMAIN DEFINITIONS
	

// FUNCTION DEFINITIONS
	

// RULE DEFINITIONS
	
	macro rule r_set_lamp ($l in SignalLamps) =
		signalLamp := $l

	macro rule r_auto_test =
		if (mode = AUTO_TEST) then 
			if (passed_auto_test = true) then
				par
					r_set_lamp[GREEN]
					mode := CONNECT_CONCENTRATE
				endpar
			endif
		endif
	
	macro rule r_connect_concentrate =
		if (mode = CONNECT_CONCENTRATE) then 
			if (concentrate_connected = true) then
				mode := SET_RINSING_PARAM
			endif
		endif
	
	macro rule r_insert_integer ($inf in Integer, $sup in Integer, $nextparam in Parameter, $mon in Integer, $contr in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				parameters := $nextparam
			endpar
		endif
		
	macro rule r_insert_integer_max ($inf in Integer, $sup in Integer, $nextparam in Parameter, $mon in Integer, $contr in Integer, $contrmin in Integer) = 
		if (($mon >= $inf ) and ($mon <= $sup ) and ($mon >= $contrmin )) then
			par 
				$contr := $mon 
				parameters := $nextparam
			endpar
		endif	
			
	macro rule r_insert_real ($inf in Real, $sup in Real, $nextparam in Parameter, $mon in Real, $contr in Real) = 
		if (($mon >= $inf ) and ($mon <= $sup )) then
			par 
				$contr := $mon 
				parameters := $nextparam
			endpar
		endif
	
	macro rule r_insert_concentrate ($nextparam in Parameter, $mon in Concentrate, $contr in Concentrate) = 
		par 
			$contr := $mon 
			parameters := $nextparam
		endpar
	
	macro rule r_insert_bool ($nextparam in Parameter, $mon in Boolean, $contr in Boolean) = 
		par 
			$contr := $mon 
			parameters := $nextparam
		endpar
			
	macro rule r_set_rinsing_param =
		if (mode = SET_RINSING_PARAM) then 
			par
				if (parameters = FILL_BP_RATE) then
					r_insert_integer [50, 600, FILL_BP_VOLUME, fill_bp_rate, fill_bp_rate_contr]
				endif
				if (parameters = FILL_BP_VOLUME) then
					r_insert_integer [0, 6000, RINSE_BP_RATE, fill_bp_volume, fill_bp_volume_contr]
				endif
				if (parameters = RINSE_BP_RATE) then
					r_insert_integer [50, 300, DF_FLOW_RINSE, rinse_bp_rate, rinse_bp_rate_contr]
				endif	
				if (parameters = DF_FLOW_RINSE) then
					r_insert_integer [50, 300, RINSE_TIME, df_flow_rinse, df_flow_rinse_contr]
				endif
				if (parameters = RINSE_TIME) then
					r_insert_integer [0, 59, RINSE_UF_RATE, rinse_time, rinse_time_contr]
				endif
				if (parameters = RINSE_UF_RATE) then
					r_insert_integer [0, 3000, RINSE_UF_VOLUME, rinse_UF_rate, rinse_UF_rate_contr]
				endif
				if (parameters = RINSE_UF_VOLUME) then
					r_insert_integer [0, 2950, BLOOD_COND, rinse_UF_volume, rinse_UF_volume_contr]
				endif	
				//if (parameters = BLOOD_FLOW_CONN) then
				//	r_insert_integer [50, 600, BLOOD_COND, blood_flow_conn, blood_flow_conn_contr]
				//endif	
				if (parameters = BLOOD_COND) then
					mode := CONNECT_AV_TUBES
				endif
			endpar
		endif
		
	macro rule r_connect_av_tubes =
		if (mode = CONNECT_AV_TUBES) then 
			if (av_tubes_connected = true) then
				mode := SET_COMPONENTS
			endif
		endif
		
	macro rule r_connect_all_components =
		if (mode = SET_COMPONENTS) then 
			if (components_connected = true) then
				mode := SET_SALINE_LEVELS
			endif
		endif
		
	macro rule r_set_saline_levels =
		if (mode = SET_SALINE_LEVELS) then 
			if (saline_levels_set = true) then
				mode := INSERT_BLOODLINES
			endif
		endif
		
	macro rule r_insert_bloodlines =
		if (mode = INSERT_BLOODLINES) then 
			if (bloodlines_inserted = true) then
				mode := RINSE_TEST_TUBES
			endif
		endif
			
	macro rule r_set_components =
		par
			r_connect_av_tubes[]
			r_connect_all_components[]
			r_set_saline_levels[]
			r_insert_bloodlines[]
		endpar
	
	macro rule r_start_stop_BP ($nextmode in Mode, $s in StartStop) =
		par 
			blood_pump := $s
			mode := $nextmode
		endpar
			
	macro rule r_BP_rinsing = 
		if (mode = RINSE_TEST_TUBES) then
			r_start_stop_BP[PRIMING, START]
		endif
	
	macro rule r_priming = 
		if (mode = PRIMING) then
			if (current_bp_volume_reaches = fill_bp_volume_contr) then
				r_start_stop_BP[CONNECT_AV_ENDS, STOP]
			endif
		endif
	
	macro rule r_connect_AV_ends = 
		if (mode = CONNECT_AV_ENDS) then
			if (av_ends_conn = true) then
				r_start_stop_BP[PREPARE_HEPARIN, START]
			endif
		endif
		
	macro rule r_rinse_test_tubes = 
		par
			r_BP_rinsing[]
			r_priming[]
			r_connect_AV_ends[]
		endpar
		
	macro rule r_prepare_heparin =
		if (mode = PREPARE_HEPARIN) then
			if (heparin_prepared = true) then
				mode := SET_TREAT_PARAM
			endif
		endif
	
	macro rule r_insert_therapy_time ($nextparam2 in Parameter) =
		par
			if ((therapy_time_hrs < 10) and (therapy_time_hrs > 0)) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					if ((therapy_time_mins >= 0) and (therapy_time_mins <=59)) then
						par
							therapy_time_mins_contr := therapy_time_mins
							parameters := $nextparam2
						endpar
					endif
				endpar
			endif
			if (therapy_time_hrs = 10) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					therapy_time_mins_contr := 0
					parameters := $nextparam2
				endpar
			endif
			if (therapy_time_hrs = 0) then
				par
					therapy_time_hrs_contr := therapy_time_hrs
					if ((therapy_time_mins >= 10) and (therapy_time_mins <=59)) then
						par
							therapy_time_mins_contr := therapy_time_mins
							parameters := $nextparam2
						endpar
					endif
				endpar
			endif
		endpar
		
	macro rule r_insert_stop_time_h ($nextparam3 in Parameter) =
		par
			if ((stop_time_hrs_h < 10) and (stop_time_hrs_h >= 0)) then
				par
					stop_time_hrs_h_contr := stop_time_hrs_h
					if ((stop_time_mins_h >= 0) and (stop_time_mins_h <=59)) then
						par
							stop_time_mins_h_contr := stop_time_mins_h
							parameters := $nextparam3
						endpar
					endif
				endpar
			endif
			if (stop_time_hrs_h = 10) then
				par
					stop_time_hrs_h_contr := stop_time_hrs_h
					stop_time_mins_h_contr := 0
					parameters := $nextparam3
				endpar
			endif
		endpar
	
	macro rule r_insert_syringe ($nextparam4 in Parameter) = 
		par
			syringe_type_contr := syringe_type
			parameters := $nextparam4
		endpar
		
	macro rule r_set_treatment_param =
		if (mode = SET_TREAT_PARAM) then 
			par
				if (parameters = BLOOD_COND) then
					r_insert_real [12.5, 16.0, BIC_AC, blood_cond, blood_cond_contr]
				endif				
				if (parameters = BIC_AC) then
					r_insert_concentrate [BIC_COND, bic_ac, bic_ac_contr]
				endif
				if (parameters = BIC_COND) then
					r_insert_integer [2, 4, DF_TEMP, bic_cond, bic_cond_contr]
				endif
				if (parameters = DF_TEMP) then
					r_insert_integer [33, 40, DF_FLOW, df_temp, df_temp_contr]
				endif
				if (parameters = DF_FLOW) then
					r_insert_integer [300, 800, UF_VOLUME, df_flow, df_flow_contr]
				endif
				if (parameters = UF_VOLUME) then
					r_insert_integer [100, 20000, THERAPY_TIME, uf_volume, uf_volume_contr]
				endif
				if (parameters = THERAPY_TIME) then
					r_insert_therapy_time [MIN_UF_RATE]
				endif
				if (parameters = MIN_UF_RATE) then
					r_insert_integer [0, 500, MAX_UF_RATE, min_uf_rate, min_uf_rate_contr]
				endif
				if (parameters = MAX_UF_RATE) then
					r_insert_integer_max [0, 4000, MIN_AP, max_uf_rate, max_uf_rate_contr, min_uf_rate_contr]
				endif
				if (parameters = MIN_AP) then
					r_insert_integer [-400, 0, DELTA_AP, min_ap, min_ap_contr]
				endif
				if (parameters = DELTA_AP) then
					r_insert_integer [10, 100, PERC_DELTA_TMP, delta_ap, delta_ap_contr]
				endif
				if (parameters = PERC_DELTA_TMP) then
					r_insert_integer [2, 99, LIMITS_TMP, perc_delta_tmp, perc_delta_tmp_contr]
				endif
				if (parameters = LIMITS_TMP) then
					r_insert_bool [MAX_TMP, limits_tmp, limits_tmp_contr]
				endif
				if (parameters = MAX_TMP) then
					r_insert_integer [300, 700, STOP_TIME_H, max_tmp, max_tmp_contr]
					//r_insert_integer [300, 700, EXTEND_TMP_LIMIT, max_tmp, max_tmp_contr]
				endif
				//if (parameters = EXTEND_TMP_LIMIT) then
				//	r_insert_bool [STOP_TIME_H, extend_tmp_limit, extend_tmp_limit_contr]
				//endif
				if (parameters = STOP_TIME_H) then
					r_insert_stop_time_h [BOLUS_VOLUME_H]
				endif
				if (parameters = BOLUS_VOLUME_H) then
					r_insert_real [0.1, 10.0, RATE_H, bolus_volume_h, bolus_volume_h_contr]
				endif	
				if (parameters = RATE_H) then
					r_insert_real [0.1, 10.0, TREATMENT_H, rate_h, rate_h_contr]
				endif
				if (parameters = TREATMENT_H) then
					r_insert_bool [SYRINGE_TYPE, treatment_h, treatment_h_contr]
				endif
				if (parameters = SYRINGE_TYPE) then
					r_insert_syringe [ENDPARAM]
				endif
				if (parameters = ENDPARAM) then
					mode := DF_PREPARATION
				endif
			endpar
		endif
	
	macro rule r_df_prepare = 
		if (mode = DF_PREPARATION) then
			if (df_preparation_end = true) then
				mode := RINSE_DIALYZER
			endif
		endif
	
	macro rule r_check_df_preparation =
		if (mode = DF_PREPARATION) then
			if (sensor_bicarbonate = false) then //no bicarbonate is detected. Acid/acetate is detected instead of bicarbonate
				par
					alarm_signal := true
					machine_state := BYPASS
				endpar
			endif				
		endif
	
	macro rule r_df_preparation = 
		par
			r_df_prepare[]
			//R-18 R-19
			r_check_df_preparation[]
		endpar
		
	macro rule r_dialyzer_balanceC_conn =
		if (mode = RINSE_DIALYZER) then
			if (dialyzer_balabceC_conn = true) then
				par
					dialyzer_balanceC_conn_contr := true
					mode := FILL_ART_CHAMBER
				endpar
			endif
		endif
	
	macro rule r_fill_art_chambre =
		if (mode = FILL_ART_CHAMBER) then
			if (art_chamber_filled = true) then
				mode := FILL_VEN_CHAMBER
			endif
		endif
	
	macro rule r_fill_ven_chambre =
		if (mode = FILL_VEN_CHAMBER) then
			if (ven_chamber_filled = true) then
				par
					r_start_stop_BP[CONNECT_ART, STOP]
					phaseTherapy := INITIATION
				endpar
			endif
		endif
		
	macro rule r_rinse_dialyzer = 
		par
			r_dialyzer_balanceC_conn[]
			r_fill_art_chambre[]
			r_fill_ven_chambre[]
		endpar
					
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
			r_df_preparation[]
		endpar

	macro rule r_start_stop_BP ($currentmode in Mode, $nextmode in Mode, $s in StartStop) =
		if ($currentmode = START_STOP_BP) then
			par 
				blood_pump := $s
				mode := $nextmode
			endpar
		endif
	
	macro rule r_start_bicarbonate =
		if (mode = BICARBONATE_RUN) then
			par
				bicarbonate := START
				mode := THERAPY_RUNNING
			endpar
		endif
	
	macro rule r_connect_arterially =
		if (mode = CONNECT_ART) then 
			if (connected_art = true) then
				par
					mode := START_STOP_BP
					connected_art_contr := true
				endpar
			endif
		endif
	
	macro rule r_insert_blood_flow_therapy ($nextmode in Mode, $mon in Integer, $contr in Integer) = 
		if (($mon >= 50 ) and ($mon <= 600 )) then
			par 
				$contr := $mon 
				mode := $nextmode
				if (first_blood_flow = true) then
					first_blood_flow := false
				endif
			endpar
		endif
		
	macro rule r_set_blood_flow =
		if (mode = BLOOD_FLOW) then 
			if (first_blood_flow = true) then //Blood flow set function is used twice, once goes to BLOOD_VRD mode and the other one goes to CHANGE_STATE mode
				r_insert_blood_flow_therapy [BLOOD_VRD, blood_flow_conn, blood_flow_conn_contr]
			else	
				r_insert_blood_flow_therapy [CHANGE_STATE, blood_flow_conn, blood_flow_conn_contr]
			endif
		endif
		
	macro rule r_blood_on_VRD = //SENSOR
		if (mode = BLOOD_VRD) then
			r_start_stop_BP[START_STOP_BP, CONNECT_VEN, STOP]
		endif
		
	macro rule r_connect_venously =
		if (mode = CONNECT_VEN) then 
			if (connected_ven = true) then	
				par
					mode := START_STOP_BP
					connected_ven_contr := true
				endpar
			endif
		endif
	
	macro rule  r_change_machine_state ($next in Mode, $color in SignalLamps, $sm in MachineState)= 
		if (mode = CHANGE_STATE) then
			par
				machine_state := $sm
				r_set_lamp[$color]
				mode := $next
			endpar
		endif
	
	macro rule r_alarm_BP = 
		par	
			blood_pump := STOP
			alarm_signal := true
		endpar
				
	macro rule r_check_during_connection =
		if (blood_pump = START) then
			//par
				if (speed_sensor_BP = 0) then
					//R-14
					r_alarm_BP[]
				endif
				
			//endpar
		endif	
				
	macro rule r_patient_connection =
		par
			r_connect_arterially[]
			r_start_stop_BP[mode, BLOOD_FLOW, START]
			r_set_blood_flow[]
			r_blood_on_VRD[]
			r_connect_venously[]
			r_change_machine_state[BICARBONATE_RUN, GREEN, MAINFLOW]
			r_start_bicarbonate[]
		endpar
		
	macro rule r_connect_patient =
		par
			r_patient_connection[]
			r_check_during_connection[]
		endpar
		
		
	macro rule r_during_therapy =
		if (mode = THERAPY_RUNNING) then
			par
				phaseTherapy := ENDING
				mode := REMOVE_ART
			endpar
		endif
	
	macro rule r_sensor_direction_BP =
		if (direction_sensor_BP = false) then
			//R-17
			r_alarm_BP[]
		endif
			
	macro rule r_check_BP =
	if (blood_pump = START) then
		par
			//R-2
			if (passed(120)=true and flow_detect = false) then
				r_alarm_BP[]
			endif
			//R-3
			if (machine_state != BYPASS) then
				if ((actual_blood_flow*100) < (blood_flow_conn_contr*70)) then
					alarm_signal := true
				endif
			endif
			//R-4		R-17
			r_sensor_direction_BP[]
		endpar
	endif
	
	macro rule r_check_initiation_phase =
		r_check_BP[]
			
	macro rule r_initiation = 
		par	
			r_check_initiation_phase[]
			r_connect_patient[]
			r_during_therapy[]
		endpar
		
	macro rule r_remove_art =	
		if (mode = REMOVE_ART) then
			if (removed_art = true) then
				par
					saline_solution := true
					connected_art_contr := false
					r_start_stop_BP[WAIT_VRD_SIGNAL, START]
				endpar
			endif
		endif	
	
	macro rule r_saline_solution =		
		if (mode = WAIT_VRD_SIGNAL) then
			r_start_stop_BP[RESTART_BP, STOP]
		endif
	
	macro rule r_restart_BP = 
		if (mode = RESTART_BP) then
			if (button_BP_pressed = true) then
				r_start_stop_BP[WAIT_BP_STOP, START]
			endif
		endif
	
	macro rule r_wait_BP_stop = 
		if (mode = WAIT_BP_STOP) then
			if (reinfusion_repeated = false) then
				if (restart_reinfusion = true) then
					par
						r_start_stop_BP[RESTART_BP, STOP]
						reinfusion_repeated := true
					endpar
				else
					r_start_stop_BP[REMOVE_VEN, STOP]
				endif
			else
				r_start_stop_BP[REMOVE_VEN, STOP]
			endif
		endif
	
	macro rule r_remove_ven = 
		if (mode = REMOVE_VEN) then
			if (removed_ven = true) then
				par
					mode := DRAIN_DIALYZER
					connected_ven_contr := false
				endpar
			endif
		endif	
			
	macro rule r_reinfusion =
		par
			r_remove_art[]
			r_saline_solution[]
			r_restart_BP[]
			r_wait_BP_stop[]
			r_remove_ven[]
		endpar		


	macro rule r_drain_dialyzer =
		if (mode = DRAIN_DIALYZER) then
			if (button_dialyzer_pressed = true) then
				par
					mode := CARTRIDGE
					empty_dialyzer := true
				endpar
			endif
		endif
		
	macro rule r_empty_cartridge =
		if (mode = CARTRIDGE) then
			mode := OVERVIEW
		endif
		
	macro rule r_therapy_overview =
		if (mode = OVERVIEW) then
			end_therapy := true
		endif
			
	macro rule r_ending = 
		par
			r_reinfusion[]
			r_drain_dialyzer[]
			r_empty_cartridge[]
			r_therapy_overview[]
		endpar


// INVARIANTS

//PROPERTIES
//S-1	
CTLSPEC ag(connected_art_contr and connected_ven_contr)
//S-5	
CTLSPEC ef((connected_art or connected_ven) and (phaseTherapy = INITIATION))
//S-11
CTLSPEC  ef(empty_dialyzer and blood_pump = START) //generate a counterexample


// MAIN RULE
	main rule r_Main =
		if (end_therapy = false ) then
			if (alarm_signal = false) then
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
			endif
		else
			skip
		endif
				
		
// INITIAL STATE
default init s0:
	function phaseTherapy = PREPARATION
	function mode = AUTO_TEST
	function signalLamp = YELLOW
	function machine_state = BYPASS
	function first_blood_flow = true
	function saline_solution = false
	function end_therapy = false
	function reinfusion_repeated = false
	function connected_art_contr = false
	function connected_ven_contr = false
	function empty_dialyzer = false
	function blood_pump = STOP
	function dialyzer_balanceC_conn_contr = false
	//
	function parameters = FILL_BP_RATE
	function alarm_signal = false