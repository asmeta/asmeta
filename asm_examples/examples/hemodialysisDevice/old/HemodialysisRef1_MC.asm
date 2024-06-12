asm HemodialysisRef1_MC

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
	enum domain MachineState = {BYPASS}
	enum domain AlarmErrorType = {DF_PREP | SAD_ERR | TEMP_HIGH}
	enum domain SignalLamps = {GREEN | YELLOW | RED}

	dynamic controlled phase: Phases //Dialysis phase in which is currently the system
	dynamic controlled prepPhase: PrepPhase //Modes in which the system can be during preparation phase
	dynamic controlled rinsingParam: RinsingParam //Rinsing parameters
	dynamic controlled tubingSystemPhase: TubingSystemPhase //Phase during inserting, rinsing and testing tubing system
	dynamic controlled treatmentParam: TreatmentParam //Treatment parameters
	dynamic controlled rinsePhase: RinsePhase //Phase during rinsing dialyzer
	//dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	static machine_state: MachineState
	dynamic controlled signal_lamp: SignalLamps //Signal lamp on the monitor

	//Time
	dynamic monitored passed1Msec: Boolean //True --> 1 millisecond is passed

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

	derived machine_status_der: MachineState
	derived errorForBYPASS: Boolean
	derived bp_status_der: BPStatus
	derived errorFORbpStatus: Boolean
	derived signal_lamp_der: SignalLamps
	derived errorExist: Boolean //True if exist at least one error
	derived alarmExist: Boolean //True if exist at least one alarm
	derived dialyzer_connected_status: Boolean //True if dialyzer is connected and no errors occur

	dynamic controlled error: AlarmErrorType -> Boolean //True--> error true
	dynamic controlled alarm: AlarmErrorType -> Boolean //True--> alarm signal on
	dynamic monitored reset_alarm: Boolean // True--> reset alarm

	//Requirements
	dynamic monitored detect_bicarbonate: Boolean // True --> bicarbonate detected False --> acetate or acid detected instead of bicarbonate
	dynamic monitored current_air_vol: Value //current air volume
	dynamic monitored currentSAD: Value //current SAD measured
	dynamic monitored error_SAD_resolved: Boolean // True --> SAD error resolved
	dynamic monitored current_temp: Value // Current temperature


definitions:
	function machine_state = BYPASS

	function errorForBYPASS = error(TEMP_HIGH) or error(DF_PREP)
	function errorFORbpStatus = error(SAD_ERR)
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

	macro rule r_error =
		par
			r_error_df_prep[]
			r_error_SAD[]
			r_error_temp_high[]
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

	rule r_insert_param($nextParam in RinsingParam, $mon in Value) =
		if $mon = PERMITTED then
			rinsingParam := $nextParam
		endif

	rule r_insert_param($nextParam in PrepPhase, $mon in Value) =
		if $mon = PERMITTED then
			prepPhase := $nextParam
		endif

	rule r_insert_param($nextParam in TreatmentParam, $mon in Value) =
		if $mon = PERMITTED then
			treatmentParam := $nextParam
		endif

	rule r_insert_time($nextParam in TreatmentParam, $mon1 in Value, $mon2 in Value) =
		if $mon1 = PERMITTED and $mon2 = PERMITTED then
			treatmentParam := $nextParam
		endif

	rule r_insert_param($nextParam in TreatmentParam, $mon in Concentrate, $contr in Concentrate) =
		par
			$contr := $mon
			treatmentParam := $nextParam
		endpar

	rule r_insert_param($nextParam in TreatmentParam, $mon in Boolean, $contr in Boolean) =
		par
			$contr := $mon
			treatmentParam := $nextParam
		endpar

	rule r_insert_param($nextParam in PrepPhase, $mon in SyringeType, $contr in SyringeType) =
		par
			$contr := $mon
			prepPhase := $nextParam
		endpar

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

	macro rule r_run_initiation =
		phase := ENDING

	macro rule r_run_ending=
		skip

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
	//R18-R19
	CTLSPEC ctlSpec_R18and19: ag((phase = PREPARATION and prepPhase = RINSE_DIALYZER and dialyzer_connected_contr and not(error(DF_PREP)) and preparing_DF and not(detect_bicarbonate)) implies ax(error(DF_PREP) and alarm(DF_PREP) and not(dialyzer_connected_status)))
	LTLSPEC ltlSpec_R18and19: g((phase = PREPARATION and prepPhase = RINSE_DIALYZER and dialyzer_connected_contr and not(error(DF_PREP)) and preparing_DF and not(detect_bicarbonate)) implies x(error(DF_PREP) and alarm(DF_PREP) and not(dialyzer_connected_status)))

	//R23-R32
	CTLSPEC ctlSpec_R23and32: ag((phase = PREPARATION and prepPhase = TUBING_SYSTEM and passed1Msec and currentSAD != PERMITTED and current_air_vol != PERMITTED and not(error(SAD_ERR))) implies ax(error(SAD_ERR) and alarm(SAD_ERR)))
	LTLSPEC ltlSpec_R23and32: g((phase = PREPARATION and prepPhase = TUBING_SYSTEM and passed1Msec and currentSAD != PERMITTED and current_air_vol != PERMITTED and not(error(SAD_ERR))) implies x(error(SAD_ERR) and alarm(SAD_ERR)))

	//R20
	CTLSPEC ctlSpec_R20: ag((phase = PREPARATION and dialyzer_connected_contr and prepPhase = RINSE_DIALYZER and not(error(TEMP_HIGH)) and current_temp = HIGH) implies ax(error(TEMP_HIGH) and alarm(TEMP_HIGH) and not(dialyzer_connected_status)))
	LTLSPEC ltlSpec_R20: g((phase = PREPARATION and dialyzer_connected_contr and prepPhase = RINSE_DIALYZER and not(error(TEMP_HIGH)) and current_temp = HIGH) implies x(error(TEMP_HIGH) and alarm(TEMP_HIGH) and not(dialyzer_connected_status)))

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
	//function machine_state = BYPASS //MP7. never updated: could be static
	function preparing_DF = false
	function bic_ac_contr = BICARBONATE
	function error($t in AlarmErrorType) = false
	function alarm($t in AlarmErrorType) = false
	function signal_lamp = YELLOW
	function limits_tmp_contr = false
	function extended_tmp_limit_contr = false
	function activation_h_contr = false
	function syringe_type_contr = ST10
