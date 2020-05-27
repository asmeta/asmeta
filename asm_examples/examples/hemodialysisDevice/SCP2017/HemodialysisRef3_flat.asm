//applied flatteners: LR FR ChR MCR 
asm HemodialysisRef3_flat
import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary
import ../../../STDL/LTLlibrary

signature:
    enum domain Phases = {PREPARATION | INITIATION | ENDING}
    enum domain PrepPhase = {RINSE_DIALYZER | SET_TREAT_PARAM | PREPARE_HEPARIN | TUBING_SYSTEM | SET_RINSING_PARAM | CONNECT_CONCENTRATE | AUTO_TEST}
    enum domain RinsingParam = {FILLING_BP_RATE | FILLING_BP_VOLUME | BP_RATE_RINSING | DF_FLOW_RINSING | TIME_RINSING | UF_RATE_RINSING | UF_VOLUME_RINSING}
    enum domain TubingSystemPhase = {CONNECT_AV_TUBES | CONNECT_ALL_COMP | SET_SALINE_LEVELS | INSERT_BLOODLINES | PRIMING | CONNECT_AV_ENDS}
    enum domain BPStatus = {START | STOP}
    enum domain TreatmentParam = {BLOOD_CONDUCTIVITY | BIC_AC | BIC_CONDUCTIVITY | DF_TEMP | DF_FLOW | UF_VOLUME | THERAPY_TIME | MIN_UF_RATE | MAX_UF_RATE | MAX_AP | DELTA_AP | PERC_DELTA_TMP | LIMITS_TMP | MAX_TMP | EXTENDED_TMP | MAX_BEP | STOP_TIME_H | BOLUS_VOLUME_H | RATE_H | ACTIVATION_H | SYRINGE_TYPE}
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
    enum domain ArterialBolusPhase = {WAIT_SOLUTION | SET_ARTERIAL_BOLUS_VOLUME | CONNECT_SOLUTION | RUNNING_SOLUTION}
    enum domain EndingPhase = {REINFUSION | THERAPY_OVERVIEW | EMPTY_CARTRIDGE | DRAIN_DIALYZER}
    enum domain ReinfusionPhase = {REMOVE_ART | CONN_SALINE | START_SALINE_INF | RUN_SALINE_INF | CHOOSE_NEXT_REINF_STEP | START_SALINE_REIN | RUN_SALINE_REIN | REMOVE_VEN}

    controlled phase: Phases
    controlled prepPhase: PrepPhase
    controlled rinsingParam: RinsingParam
    controlled tubingSystemPhase: TubingSystemPhase
    controlled treatmentParam: TreatmentParam
    controlled rinsePhase: RinsePhase
    controlled machine_state: MachineState
    controlled initPhase: InitPhase
    controlled patientPhase: PatientPhase
    controlled signal_lamp: SignalLamps
    controlled therapyPhase: TherapyPhase
    controlled arterialBolusPhase: ArterialBolusPhase
    controlled endingPhase: EndingPhase
    controlled reinfusionPhase: ReinfusionPhase
    monitored passed1Msec: Boolean
    monitored passed120Sec: Boolean
    monitored passed3Sec: Boolean
    monitored passed10Sec: Boolean
    monitored passed1Sec: Boolean
    monitored time_heparin: Boolean
    monitored time_therapy: Boolean
    monitored passed5Min: Boolean
    monitored auto_test_end: Boolean
    monitored conn_concentrate: Boolean
    monitored fill_bp_rate: Value
    monitored fill_bp_volume: Value
    monitored bp_rate_rinsing: Value
    monitored df_flow_rinsing: Value
    monitored time_rinsing: Value
    monitored uf_rate_rinsing: Value
    monitored uf_volume_rinsing: Value
    monitored av_tubes_connected: Boolean
    monitored all_components_connected: Boolean
    monitored saline_levels_set: Boolean
    monitored blood_line_insert: Boolean
    controlled bp_status: BPStatus
    monitored bp_fill_fluid: Boolean
    monitored av_ends_connected: Boolean
    monitored bp_rate_rinsing_150: Boolean
    monitored heparin_prepared: Boolean
    monitored blood_conductivity: Value
    monitored bic_ac: Concentrate
    controlled bic_ac_contr: Concentrate
    monitored bic_conductivity: Value
    monitored df_temp: Value
    monitored df_flow: Value
    monitored uf_volume: Value
    monitored therapy_time_mins: Value
    monitored therapy_time_hrs: Value
    monitored min_uf_rate: Value
    monitored max_uf_rate: Value
    monitored max_ap: Value
    monitored delta_ap: Value
    monitored perc_delta_tmp: Value
    monitored limits_tmp: Boolean
    controlled limits_tmp_contr: Boolean
    monitored max_tmp: Value
    monitored extended_tmp_limit: Boolean
    controlled extended_tmp_limit_contr: Boolean
    monitored max_bep: Value
    monitored stop_time_mins_h: Value
    monitored stop_time_hrs_h: Value
    monitored bolus_volume_h: Value
    monitored rate_h: Value
    monitored activation_h: Boolean
    controlled activation_h_contr: Boolean
    monitored syringe_type: SyringeType
    controlled syringe_type_contr: SyringeType
    monitored dialyzer_connected: Boolean
    controlled dialyzer_connected_contr: Boolean
    monitored arterial_chamber_filled: Boolean
    monitored venous_chamber_fill: Boolean
    monitored dialyzer_filled: Boolean
    monitored stop_DF_preparation: Boolean
    controlled preparing_DF: Boolean
    monitored art_connected: Boolean
    monitored ven_connected: Boolean
    monitored start_stop_button: Boolean
    monitored blood_flow_conn: Value
    monitored blood_on_VRD: Boolean
    controlled bicarbonate_status: Boolean
    monitored conn_infuse_set_volume: Value
    controlled heparin_running: Boolean
    controlled ap_limits_set: Boolean
    controlled vp_limits_set: Boolean
    monitored ap_limit_low: Value
    monitored vp_limit_low: Value
    monitored current_ap: Value
    monitored current_vp: Value
    monitored ap_limit_up: Value
    monitored vp_limit_up: Value
    monitored treatment_min_uf_rate: Boolean
    controlled treatment_min_uf_rate_contr: Boolean
    monitored interrupt_dialysis: Boolean
    monitored start_arterial_bolus: Boolean
    monitored saline_solution_conn: Boolean
    monitored art_bolus_volume: Value
    monitored current_art_bolus_volume: Value
    monitored art_removed: Boolean
    monitored saline_conn: Boolean
    monitored saline_on_VRD: Boolean
    monitored new_saline_reinfusion: Boolean
    controlled empty_dialyzer: Boolean
    monitored volume_saline_inf_400: Boolean
    monitored ven_removed: Boolean
    monitored dialyzer_drained: Boolean
    monitored cartridge_emtpy: Boolean
    derived machine_status_der: MachineState
    derived errorForBYPASS: Boolean
    derived bp_status_der: BPStatus
    derived errorFORbpStatus: Boolean
    derived bicarbonate_status_der: Boolean
    derived errorFORbicarbonate: Boolean
    derived signal_lamp_der: SignalLamps
    derived errorExist: Boolean
    derived alarmExist: Boolean
    derived err_patient_conn: Boolean
    derived error_bp: Boolean
    derived error_rein_press: Boolean
    derived error_therapy: Boolean
    derived dialyzer_connected_status: Boolean
    controlled error: AlarmErrorType -> Boolean
    controlled alarm: AlarmErrorType -> Boolean
    monitored reset_alarm: Boolean
    controlled art_connected_contr: Boolean
    controlled ven_connected_contr: Boolean
    monitored detect_bicarbonate: Boolean
    monitored current_air_vol: Value
    monitored currentSAD: Value
    monitored error_SAD_resolved: Boolean
    monitored current_temp: Value
    monitored update_blood_flow: Boolean
    controlled update_th_time: Boolean
    monitored detected_blood_flow: Boolean
    controlled blood_flow_error_bf_less: Boolean
    controlled th_time_error_bf_less: Boolean
    monitored current_bp_flow: Value
    monitored bp_rotates_back: Boolean
    monitored current_fill_blood_vol: Value
    monitored reverse_dir_heparin: Boolean
    monitored current_UF_rate: Value
    monitored uf_dir_backwards: Boolean
    monitored current_UF_volume: Value
    monitored reset_error_arterial_bolus: Boolean
    monitored reset_error_bp_no_flow: Boolean
    monitored reset_error_bp_rot_dir: Boolean
    monitored reset_error_pression: Boolean
    monitored error_heparin_resolve: Boolean
    monitored error_UF_rate_resolved: Boolean
    monitored error_UF_dir_resolved: Boolean
    controlled bf_err_ap_low: Boolean
    controlled reset_err_pres_ap_low: Boolean
    controlled bf_err_ap_low_conn: Boolean
    controlled reset_err_pres_ap_low_conn: Boolean
    derived df_flow_state: Boolean

definitions:

    function errorForBYPASS = or(or(or(or(error(TEMP_HIGH),error(TEMP_LOW)),error(DF_PREP)),error(UF_DIR)),alarm(UF_VOLUME_ERR))
    function errorFORbpStatus = or(or(or(or(or(or(or(or(or(or(or(or(or(or(error(BP_ROTATION_DIR),error(BP_NO_FLOW)),error(INIT_VP_UP)),error(INIT_AP_UP)),error(INIT_VP_LOW)),error(INIT_AP_LOW)),error(CONN_VP_UP)),error(CONN_VP_LOW)),error(CONN_AP_LOW)),error(REIN_VP_UP)),error(REIN_AP_LOW)),error(FILL_BLOOD_VOL)),error(HEPARIN_DIR)),error(SAD_ERR)),error(ARTERIAL_BOLUS))
    function errorFORbicarbonate = or(error(UF_RATE),alarm(UF_BYPASS))
    function dialyzer_connected_status = and(and(dialyzer_connected_contr,not(error(DF_PREP))),not(error(TEMP_HIGH)))
    function machine_status_der = if errorForBYPASS then BYPASS else machine_state endif
    function bp_status_der = if errorFORbpStatus then STOP else bp_status endif
    function bicarbonate_status_der = if errorFORbicarbonate then false else bicarbonate_status endif
    function errorExist = (exist $t in AlarmErrorType with error($t))
    function alarmExist = (exist $t in AlarmErrorType with alarm($t))
    function signal_lamp_der = if errorExist then RED else signal_lamp endif
    function df_flow_state = neq(machine_status_der,BYPASS)
    function err_patient_conn = or(or(or(or(error(CONN_VP_UP),error(CONN_VP_LOW)),error(CONN_AP_LOW)),error(FILL_BLOOD_VOL)),error(SAD_ERR))
    function error_bp = or(or(error(BP_NO_FLOW),error(BP_ROTATION_DIR)),error(BP_LESS_FLOW))
    function error_rein_press = or(or(error(REIN_VP_UP),error(REIN_AP_LOW)),error(SAD_ERR))
    function error_therapy = or(or(or(or(or(error(INIT_VP_UP),error(INIT_VP_LOW)),error(INIT_AP_UP)),error(INIT_AP_LOW)),error(SAD_ERR)),error(HEPARIN_DIR))


    LTLSPEC ltlSpec_S1: g(iff(art_connected_contr,ven_connected_contr))
    LTLSPEC ltlSpec_S4: g(implies(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),eq(patientPhase,FILL_TUBING)),not(err_patient_conn)),not(error_bp)),or(blood_on_VRD,eq(conn_infuse_set_volume,PERMITTED))),x(and(eq(bp_status_der,STOP),eq(patientPhase,CONN_VEN)))))
    LTLSPEC ltlSpec_S5a: g(implies(and(not(art_connected_contr),x(art_connected_contr)),eq(phase,INITIATION)))
    LTLSPEC ltlSpec_S5b: g(implies(and(not(ven_connected_contr),x(ven_connected_contr)),eq(phase,INITIATION)))
    LTLSPEC ltlSpec_S11: g(implies(empty_dialyzer,g(eq(bp_status_der,STOP))))
    LTLSPEC ltlSpec_R1: g(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),eq(arterialBolusPhase,RUNNING_SOLUTION)),not(error_therapy)),not(error_bp)),not(error(ARTERIAL_BOLUS))),eq(current_art_bolus_volume,HIGH)),x(and(error(ARTERIAL_BOLUS),alarm(ARTERIAL_BOLUS)))))
    LTLSPEC ltlSpec_R2: g(implies(and(and(and(eq(phase,INITIATION),eq(bp_status_der,START)),not(error(BP_NO_FLOW))),and(not(detected_blood_flow),passed120Sec)),x(and(error(BP_NO_FLOW),alarm(BP_NO_FLOW)))))
    LTLSPEC ltlSpec_R3: g(implies(and(and(and(and(eq(phase,INITIATION),eq(bp_status_der,START)),eq(machine_status_der,MAIN_FLOW)),not(error(BP_LESS_FLOW))),eq(current_bp_flow,TOOLOW)),x(and(error(BP_LESS_FLOW),alarm(BP_LESS_FLOW)))))
    LTLSPEC ltlSpec_R4: g(implies(and(and(and(eq(phase,INITIATION),eq(bp_status_der,START)),not(error(BP_ROTATION_DIR))),bp_rotates_back),x(and(error(BP_ROTATION_DIR),alarm(BP_ROTATION_DIR)))))
    LTLSPEC ltlSpec_R5: g(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_VP_UP))),eq(current_vp,HIGH)),x(and(error(INIT_VP_UP),alarm(INIT_VP_UP)))))
    LTLSPEC ltlSpec_R6: g(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_AP_UP))),eq(current_ap,HIGH)),x(and(error(INIT_AP_UP),alarm(INIT_AP_UP)))))
    LTLSPEC ltlSpec_R7: g(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_VP_LOW))),eq(current_vp,TOOLOW)),x(and(error(INIT_VP_LOW),alarm(INIT_VP_LOW)))))
    LTLSPEC ltlSpec_R8: g(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_AP_LOW))),eq(current_ap,TOOLOW)),x(and(error(INIT_AP_LOW),alarm(INIT_AP_LOW)))))
    LTLSPEC ltlSpec_R9: g(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),not(err_patient_conn)),not(error_bp)),eq(bp_status_der,START)),not(error(CONN_VP_UP))),eq(current_vp,HIGH)),passed3Sec),x(and(error(CONN_VP_UP),alarm(CONN_VP_UP)))))
    LTLSPEC ltlSpec_R10: g(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),not(err_patient_conn)),not(error_bp)),eq(bp_status_der,START)),not(error(CONN_VP_LOW))),eq(current_vp,TOOLOW)),passed3Sec),x(and(error(CONN_VP_LOW),alarm(CONN_VP_LOW)))))
    LTLSPEC ltlSpec_R11: g(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),not(err_patient_conn)),not(error_bp)),eq(bp_status_der,START)),not(error(CONN_AP_LOW))),eq(current_ap,TOOLOW)),passed1Sec),x(and(error(CONN_AP_LOW),alarm(CONN_AP_LOW)))))
    LTLSPEC ltlSpec_R12: g(implies(and(and(and(and(and(and(and(eq(phase,ENDING),eq(endingPhase,REINFUSION)),not(error_rein_press)),not(error_bp)),eq(bp_status_der,START)),not(error(REIN_VP_UP))),eq(current_vp,HIGH)),passed3Sec),x(and(error(REIN_VP_UP),alarm(REIN_VP_UP)))))
    LTLSPEC ltlSpec_R13: g(implies(and(and(and(and(and(and(and(eq(phase,ENDING),eq(endingPhase,REINFUSION)),not(error_rein_press)),not(error_bp)),eq(bp_status_der,START)),not(error(REIN_AP_LOW))),eq(current_ap,TOOLOW)),passed1Sec),x(and(error(REIN_AP_LOW),alarm(REIN_AP_LOW)))))
    LTLSPEC ltlSpec_R15: g(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),eq(bp_status_der,START)),not(err_patient_conn)),not(error_bp)),not(error(FILL_BLOOD_VOL))),eq(current_fill_blood_vol,HIGH)),x(and(error(FILL_BLOOD_VOL),alarm(FILL_BLOOD_VOL)))))
    LTLSPEC ltlSpec_18and19: g(implies(and(and(and(and(and(eq(phase,PREPARATION),eq(prepPhase,RINSE_DIALYZER)),dialyzer_connected_contr),not(error(DF_PREP))),preparing_DF),not(detect_bicarbonate)),x(and(and(error(DF_PREP),alarm(DF_PREP)),not(dialyzer_connected_status)))))
    LTLSPEC ltlSpec_R20: g(implies(or(and(and(eq(phase,INITIATION),not(error(TEMP_HIGH))),eq(current_temp,HIGH)),and(and(and(and(eq(phase,PREPARATION),dialyzer_connected_contr),eq(prepPhase,RINSE_DIALYZER)),not(error(TEMP_HIGH))),eq(current_temp,HIGH))),x(and(and(error(TEMP_HIGH),alarm(TEMP_HIGH)),not(dialyzer_connected_status)))))
    LTLSPEC ltlSpec_R21: g(implies(and(and(eq(phase,INITIATION),not(error(TEMP_LOW))),eq(current_temp,TOOLOW)),x(and(error(TEMP_LOW),alarm(TEMP_LOW)))))
    LTLSPEC ltlSpec_R22: g(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),heparin_running),not(error(HEPARIN_DIR))),reverse_dir_heparin),x(and(error(HEPARIN_DIR),alarm(HEPARIN_DIR)))))
    LTLSPEC ltlSpec_R23and32: g(implies(and(or(or(and(eq(phase,PREPARATION),eq(prepPhase,TUBING_SYSTEM)),and(eq(phase,INITIATION),eq(bp_status_der,START))),and(and(and(eq(phase,ENDING),eq(endingPhase,REINFUSION)),not(error_rein_press)),eq(bp_status_der,START))),and(and(and(passed1Msec,neq(currentSAD,PERMITTED)),neq(current_air_vol,PERMITTED)),not(error(SAD_ERR)))),x(and(error(SAD_ERR),alarm(SAD_ERR)))))
    LTLSPEC ltlSpec_R33: g(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),bicarbonate_status_der),not(error(UF_RATE))),eq(current_UF_rate,HIGH)),x(and(error(UF_RATE),alarm(UF_RATE)))))
    LTLSPEC ltlSpec_R34: g(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),bicarbonate_status_der),not(error(UF_DIR))),uf_dir_backwards),x(and(error(UF_DIR),alarm(UF_DIR)))))
    LTLSPEC ltlSpec_R35: g(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),bicarbonate_status_der),not(alarm(UF_VOLUME_ERR))),eq(current_UF_volume,HIGH)),x(alarm(UF_VOLUME_ERR))))
    LTLSPEC ltlSpec_R36: g(implies(eq(machine_status_der,BYPASS),not(df_flow_state)))
    CTLSPEC ctlSpec_S11: ag(implies(empty_dialyzer,ag(eq(bp_status_der,STOP))))
    CTLSPEC ctlSpec_R1: ag(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),eq(arterialBolusPhase,RUNNING_SOLUTION)),not(error_therapy)),not(error_bp)),not(error(ARTERIAL_BOLUS))),eq(current_art_bolus_volume,HIGH)),ax(and(error(ARTERIAL_BOLUS),alarm(ARTERIAL_BOLUS)))))
    CTLSPEC ctlSpec_R2: ag(implies(and(and(and(eq(phase,INITIATION),eq(bp_status_der,START)),not(error(BP_NO_FLOW))),and(not(detected_blood_flow),passed120Sec)),ax(and(error(BP_NO_FLOW),alarm(BP_NO_FLOW)))))
    CTLSPEC ctlSpec_R3: ag(implies(and(and(and(and(eq(phase,INITIATION),eq(bp_status_der,START)),eq(machine_status_der,MAIN_FLOW)),not(error(BP_LESS_FLOW))),eq(current_bp_flow,TOOLOW)),ax(and(error(BP_LESS_FLOW),alarm(BP_LESS_FLOW)))))
    CTLSPEC ctlSpec_R4: ag(implies(and(and(and(eq(phase,INITIATION),eq(bp_status_der,START)),not(error(BP_ROTATION_DIR))),bp_rotates_back),ax(and(error(BP_ROTATION_DIR),alarm(BP_ROTATION_DIR)))))
    CTLSPEC ctlSpec_R5: ag(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_VP_UP))),eq(current_vp,HIGH)),ax(and(error(INIT_VP_UP),alarm(INIT_VP_UP)))))
    CTLSPEC ctlSpec_R6: ag(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_AP_UP))),eq(current_ap,HIGH)),ax(and(error(INIT_AP_UP),alarm(INIT_AP_UP)))))
    CTLSPEC ctlSpec_R7: ag(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_VP_LOW))),eq(current_vp,TOOLOW)),ax(and(error(INIT_VP_LOW),alarm(INIT_VP_LOW)))))
    CTLSPEC ctlSpec_R8: ag(implies(and(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),eq(bp_status_der,START)),ap_limits_set),vp_limits_set),not(error(INIT_AP_LOW))),eq(current_ap,TOOLOW)),ax(and(error(INIT_AP_LOW),alarm(INIT_AP_LOW)))))
    CTLSPEC ctlSpec_R9: ag(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),not(err_patient_conn)),not(error_bp)),eq(bp_status_der,START)),not(error(CONN_VP_UP))),eq(current_vp,HIGH)),passed3Sec),ax(and(error(CONN_VP_UP),alarm(CONN_VP_UP)))))
    CTLSPEC ctlSpec_R10: ag(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),not(err_patient_conn)),not(error_bp)),eq(bp_status_der,START)),not(error(CONN_VP_LOW))),eq(current_vp,TOOLOW)),passed3Sec),ax(and(error(CONN_VP_LOW),alarm(CONN_VP_LOW)))))
    CTLSPEC ctlSpec_R11: ag(implies(and(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),not(err_patient_conn)),not(error_bp)),eq(bp_status_der,START)),not(error(CONN_AP_LOW))),eq(current_ap,TOOLOW)),passed1Sec),ax(and(error(CONN_AP_LOW),alarm(CONN_AP_LOW)))))
    CTLSPEC ctlSpec_R12: ag(implies(and(and(and(and(and(and(and(eq(phase,ENDING),eq(endingPhase,REINFUSION)),not(error_rein_press)),not(error_bp)),eq(bp_status_der,START)),not(error(REIN_VP_UP))),eq(current_vp,HIGH)),passed3Sec),ax(and(error(REIN_VP_UP),alarm(REIN_VP_UP)))))
    CTLSPEC ctlSpec_R13: ag(implies(and(and(and(and(and(and(and(eq(phase,ENDING),eq(endingPhase,REINFUSION)),not(error_rein_press)),not(error_bp)),eq(bp_status_der,START)),not(error(REIN_AP_LOW))),eq(current_ap,TOOLOW)),passed1Sec),ax(and(error(REIN_AP_LOW),alarm(REIN_AP_LOW)))))
    CTLSPEC ctlSpec_R15: ag(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,CONNECT_PATIENT)),eq(bp_status_der,START)),not(err_patient_conn)),not(error_bp)),not(error(FILL_BLOOD_VOL))),eq(current_fill_blood_vol,HIGH)),ax(and(error(FILL_BLOOD_VOL),alarm(FILL_BLOOD_VOL)))))
    CTLSPEC ctlSpec_18and19: ag(implies(and(and(and(and(and(eq(phase,PREPARATION),eq(prepPhase,RINSE_DIALYZER)),dialyzer_connected_contr),not(error(DF_PREP))),preparing_DF),not(detect_bicarbonate)),ax(and(and(error(DF_PREP),alarm(DF_PREP)),not(dialyzer_connected_status)))))
    CTLSPEC ctlSpec_R20: ag(implies(or(and(and(eq(phase,INITIATION),not(error(TEMP_HIGH))),eq(current_temp,HIGH)),and(and(and(and(eq(phase,PREPARATION),dialyzer_connected_contr),eq(prepPhase,RINSE_DIALYZER)),not(error(TEMP_HIGH))),eq(current_temp,HIGH))),ax(and(and(error(TEMP_HIGH),alarm(TEMP_HIGH)),not(dialyzer_connected_status)))))
    CTLSPEC ctlSpec_R21: ag(implies(and(and(eq(phase,INITIATION),not(error(TEMP_LOW))),eq(current_temp,TOOLOW)),ax(and(error(TEMP_LOW),alarm(TEMP_LOW)))))
    CTLSPEC ctlSpec_R22: ag(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),heparin_running),not(error(HEPARIN_DIR))),reverse_dir_heparin),ax(and(error(HEPARIN_DIR),alarm(HEPARIN_DIR)))))
    CTLSPEC ctlSpec_R23and32: ag(implies(and(or(or(and(eq(phase,PREPARATION),eq(prepPhase,TUBING_SYSTEM)),and(eq(phase,INITIATION),eq(bp_status_der,START))),and(and(and(eq(phase,ENDING),eq(endingPhase,REINFUSION)),not(error_rein_press)),eq(bp_status_der,START))),and(and(and(passed1Msec,neq(currentSAD,PERMITTED)),neq(current_air_vol,PERMITTED)),not(error(SAD_ERR)))),ax(and(error(SAD_ERR),alarm(SAD_ERR)))))
    CTLSPEC ctlSpec_R33: ag(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),bicarbonate_status_der),not(error(UF_RATE))),eq(current_UF_rate,HIGH)),ax(and(error(UF_RATE),alarm(UF_RATE)))))
    CTLSPEC ctlSpec_R34: ag(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),bicarbonate_status_der),not(error(UF_DIR))),uf_dir_backwards),ax(and(error(UF_DIR),alarm(UF_DIR)))))
    CTLSPEC ctlSpec_R35: ag(implies(and(and(and(and(and(and(eq(phase,INITIATION),eq(initPhase,THERAPY_RUNNING)),eq(therapyPhase,THERAPY_EXEC)),not(error_therapy)),bicarbonate_status_der),not(alarm(UF_VOLUME_ERR))),eq(current_UF_volume,HIGH)),ax(alarm(UF_VOLUME_ERR))))
    CTLSPEC ctlSpec_R36: ag(implies(eq(machine_status_der,BYPASS),not(df_flow_state)))
    main rule r_Main =
        par
            par
                if eq(phase,PREPARATION) then
                    par
                        par
                            if and(not(error(DF_PREP)),preparing_DF) then
                                if not(detect_bicarbonate) then
                                    par
                                        error(DF_PREP) := true
                                        alarm(DF_PREP) := true
                                    endpar
                                endif
                            endif
                            if eq(prepPhase,TUBING_SYSTEM) then
                                if not(error(SAD_ERR)) then
                                    if passed1Msec then
                                        if and(neq(currentSAD,PERMITTED),neq(current_air_vol,PERMITTED)) then
                                            par
                                                error(SAD_ERR) := true
                                                alarm(SAD_ERR) := true
                                            endpar
                                        endif
                                    endif
                                endif
                            endif
                            if dialyzer_connected_contr then
                                if not(error(TEMP_HIGH)) then
                                    if eq(current_temp,HIGH) then
                                        par
                                            error(TEMP_HIGH) := true
                                            alarm(TEMP_HIGH) := true
                                        endpar
                                    endif
                                endif
                            endif
                        endpar
                        par
                            if eq(prepPhase,AUTO_TEST) then
                                if auto_test_end then
                                    par
                                        prepPhase := CONNECT_CONCENTRATE
                                        signal_lamp := GREEN
                                    endpar
                                endif
                            endif
                            if eq(prepPhase,CONNECT_CONCENTRATE) then
                                if conn_concentrate then
                                    par
                                        prepPhase := SET_RINSING_PARAM
                                        preparing_DF := true
                                    endpar
                                endif
                            endif
                            if eq(prepPhase,SET_RINSING_PARAM) then
                                par
                                    if eq(rinsingParam,FILLING_BP_RATE) then
                                        if eq(fill_bp_rate,PERMITTED) then
                                            rinsingParam := FILLING_BP_VOLUME
                                        endif
                                    endif
                                    if eq(rinsingParam,FILLING_BP_VOLUME) then
                                        if eq(fill_bp_volume,PERMITTED) then
                                            rinsingParam := BP_RATE_RINSING
                                        endif
                                    endif
                                    if eq(rinsingParam,BP_RATE_RINSING) then
                                        if eq(bp_rate_rinsing,PERMITTED) then
                                            rinsingParam := DF_FLOW_RINSING
                                        endif
                                    endif
                                    if eq(rinsingParam,DF_FLOW_RINSING) then
                                        if eq(df_flow_rinsing,PERMITTED) then
                                            rinsingParam := TIME_RINSING
                                        endif
                                    endif
                                    if eq(rinsingParam,TIME_RINSING) then
                                        if eq(time_rinsing,PERMITTED) then
                                            rinsingParam := UF_RATE_RINSING
                                        endif
                                    endif
                                    if eq(rinsingParam,UF_RATE_RINSING) then
                                        if eq(uf_rate_rinsing,PERMITTED) then
                                            rinsingParam := UF_VOLUME_RINSING
                                        endif
                                    endif
                                    if eq(rinsingParam,UF_VOLUME_RINSING) then
                                        if eq(uf_volume_rinsing,PERMITTED) then
                                            prepPhase := TUBING_SYSTEM
                                        endif
                                    endif
                                endpar
                            endif
                            if eq(prepPhase,TUBING_SYSTEM) then
                                par
                                    if eq(tubingSystemPhase,CONNECT_AV_TUBES) then
                                        if av_tubes_connected then
                                            tubingSystemPhase := CONNECT_ALL_COMP
                                        endif
                                    endif
                                    if eq(tubingSystemPhase,CONNECT_ALL_COMP) then
                                        if all_components_connected then
                                            tubingSystemPhase := SET_SALINE_LEVELS
                                        endif
                                    endif
                                    if eq(tubingSystemPhase,SET_SALINE_LEVELS) then
                                        if saline_levels_set then
                                            tubingSystemPhase := INSERT_BLOODLINES
                                        endif
                                    endif
                                    if eq(tubingSystemPhase,INSERT_BLOODLINES) then
                                        if blood_line_insert then
                                            tubingSystemPhase := PRIMING
                                        endif
                                    endif
                                    if eq(tubingSystemPhase,PRIMING) then
                                        if eq(bp_status_der,STOP) then
                                            bp_status := START
                                        else 
                                            if and(bp_fill_fluid,bp_rate_rinsing_150) then
                                                par
                                                    bp_status := STOP
                                                    tubingSystemPhase := CONNECT_AV_ENDS
                                                endpar
                                            endif
                                        endif
                                    endif
                                    if eq(tubingSystemPhase,CONNECT_AV_ENDS) then
                                        if av_ends_connected then
                                            prepPhase := PREPARE_HEPARIN
                                        endif
                                    endif
                                endpar
                            endif
                            if eq(prepPhase,PREPARE_HEPARIN) then
                                if heparin_prepared then
                                    prepPhase := SET_TREAT_PARAM
                                endif
                            endif
                            if eq(prepPhase,SET_TREAT_PARAM) then
                                par
                                    if eq(treatmentParam,BLOOD_CONDUCTIVITY) then
                                        if eq(blood_conductivity,PERMITTED) then
                                            treatmentParam := BIC_AC
                                        endif
                                    endif
                                    if eq(treatmentParam,BIC_AC) then
                                        par
                                            bic_ac_contr := bic_ac
                                            treatmentParam := BIC_CONDUCTIVITY
                                        endpar
                                    endif
                                    if eq(treatmentParam,BIC_CONDUCTIVITY) then
                                        if eq(bic_conductivity,PERMITTED) then
                                            treatmentParam := DF_TEMP
                                        endif
                                    endif
                                    if eq(treatmentParam,DF_TEMP) then
                                        if eq(df_temp,PERMITTED) then
                                            treatmentParam := DF_FLOW
                                        endif
                                    endif
                                    if eq(treatmentParam,DF_FLOW) then
                                        if eq(df_flow,PERMITTED) then
                                            treatmentParam := UF_VOLUME
                                        endif
                                    endif
                                    if eq(treatmentParam,UF_VOLUME) then
                                        if eq(uf_volume,PERMITTED) then
                                            treatmentParam := THERAPY_TIME
                                        endif
                                    endif
                                    if eq(treatmentParam,THERAPY_TIME) then
                                        if and(eq(therapy_time_hrs,PERMITTED),eq(therapy_time_mins,PERMITTED)) then
                                            treatmentParam := MIN_UF_RATE
                                        endif
                                    endif
                                    if eq(treatmentParam,MIN_UF_RATE) then
                                        if eq(min_uf_rate,PERMITTED) then
                                            treatmentParam := MAX_UF_RATE
                                        endif
                                    endif
                                    if eq(treatmentParam,MAX_UF_RATE) then
                                        if eq(max_uf_rate,PERMITTED) then
                                            treatmentParam := MAX_AP
                                        endif
                                    endif
                                    if eq(treatmentParam,MAX_AP) then
                                        if eq(max_ap,PERMITTED) then
                                            treatmentParam := DELTA_AP
                                        endif
                                    endif
                                    if eq(treatmentParam,DELTA_AP) then
                                        if eq(delta_ap,PERMITTED) then
                                            treatmentParam := PERC_DELTA_TMP
                                        endif
                                    endif
                                    if eq(treatmentParam,PERC_DELTA_TMP) then
                                        if eq(perc_delta_tmp,PERMITTED) then
                                            treatmentParam := LIMITS_TMP
                                        endif
                                    endif
                                    if eq(treatmentParam,LIMITS_TMP) then
                                        par
                                            limits_tmp_contr := limits_tmp
                                            treatmentParam := MAX_TMP
                                        endpar
                                    endif
                                    if eq(treatmentParam,MAX_TMP) then
                                        if eq(max_tmp,PERMITTED) then
                                            treatmentParam := EXTENDED_TMP
                                        endif
                                    endif
                                    if eq(treatmentParam,EXTENDED_TMP) then
                                        par
                                            extended_tmp_limit_contr := extended_tmp_limit
                                            treatmentParam := MAX_BEP
                                        endpar
                                    endif
                                    if eq(treatmentParam,MAX_BEP) then
                                        if eq(max_bep,PERMITTED) then
                                            treatmentParam := STOP_TIME_H
                                        endif
                                    endif
                                    if eq(treatmentParam,STOP_TIME_H) then
                                        if and(eq(stop_time_hrs_h,PERMITTED),eq(stop_time_mins_h,PERMITTED)) then
                                            treatmentParam := BOLUS_VOLUME_H
                                        endif
                                    endif
                                    if eq(treatmentParam,BOLUS_VOLUME_H) then
                                        if eq(bolus_volume_h,PERMITTED) then
                                            treatmentParam := RATE_H
                                        endif
                                    endif
                                    if eq(treatmentParam,RATE_H) then
                                        if eq(rate_h,PERMITTED) then
                                            treatmentParam := ACTIVATION_H
                                        endif
                                    endif
                                    if eq(treatmentParam,ACTIVATION_H) then
                                        par
                                            activation_h_contr := activation_h
                                            treatmentParam := SYRINGE_TYPE
                                        endpar
                                    endif
                                    if eq(treatmentParam,SYRINGE_TYPE) then
                                        par
                                            syringe_type_contr := syringe_type
                                            prepPhase := RINSE_DIALYZER
                                        endpar
                                    endif
                                endpar
                            endif
                            if eq(prepPhase,RINSE_DIALYZER) then
                                if not(error(TEMP_HIGH)) then
                                    par
                                        if eq(rinsePhase,CONNECT_DIALYZER) then
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
                                        endif
                                        if eq(rinsePhase,FILL_ART_CHAMBER) then
                                            if eq(bp_status_der,STOP) then
                                                bp_status := START
                                            else 
                                                if arterial_chamber_filled then
                                                    rinsePhase := FILL_VEN_CHAMBER
                                                endif
                                            endif
                                        endif
                                        if eq(rinsePhase,FILL_VEN_CHAMBER) then
                                            if venous_chamber_fill then
                                                rinsePhase := FILL_DIALYZER
                                            endif
                                        endif
                                        if eq(rinsePhase,FILL_DIALYZER) then
                                            if dialyzer_filled then
                                                par
                                                    bp_status := STOP
                                                    phase := INITIATION
                                                endpar
                                            endif
                                        endif
                                    endpar
                                endif
                            endif
                        endpar
                    endpar
                endif
                if eq(phase,INITIATION) then
                    par
                        par
                            if not(error(TEMP_HIGH)) then
                                if eq(current_temp,HIGH) then
                                    par
                                        error(TEMP_HIGH) := true
                                        alarm(TEMP_HIGH) := true
                                    endpar
                                endif
                            endif
                            if not(error(TEMP_LOW)) then
                                if eq(current_temp,TOOLOW) then
                                    par
                                        error(TEMP_LOW) := true
                                        alarm(TEMP_LOW) := true
                                    endpar
                                endif
                            endif
                            if eq(bp_status_der,START) then
                                par
                                    if not(error(BP_ROTATION_DIR)) then
                                        if bp_rotates_back then
                                            par
                                                error(BP_ROTATION_DIR) := true
                                                alarm(BP_ROTATION_DIR) := true
                                            endpar
                                        endif
                                    endif
                                    if not(error(BP_LESS_FLOW)) then
                                        if eq(machine_status_der,MAIN_FLOW) then
                                            if eq(current_bp_flow,TOOLOW) then
                                                par
                                                    alarm(BP_LESS_FLOW) := true
                                                    error(BP_LESS_FLOW) := true
                                                    blood_flow_error_bf_less := true
                                                    th_time_error_bf_less := true
                                                endpar
                                            endif
                                        endif
                                    endif
                                    if not(error(BP_NO_FLOW)) then
                                        if and(not(detected_blood_flow),passed120Sec) then
                                            par
                                                error(BP_NO_FLOW) := true
                                                alarm(BP_NO_FLOW) := true
                                            endpar
                                        endif
                                    endif
                                    if not(error(SAD_ERR)) then
                                        if passed1Msec then
                                            if and(neq(currentSAD,PERMITTED),neq(current_air_vol,PERMITTED)) then
                                                par
                                                    error(SAD_ERR) := true
                                                    alarm(SAD_ERR) := true
                                                endpar
                                            endif
                                        endif
                                    endif
                                endpar
                            endif
                        endpar
                        if eq(initPhase,CONNECT_PATIENT) then
                            par
                                par
                                    if eq(patientPhase,CONN_ART) then
                                        if art_connected then
                                            par
                                                patientPhase := START_BP
                                                art_connected_contr := true
                                            endpar
                                        endif
                                    endif
                                    if eq(patientPhase,START_BP) then
                                        if start_stop_button then
                                            if not(error_bp) then
                                                par
                                                    patientPhase := BLOOD_FLOW
                                                    bp_status := START
                                                endpar
                                            endif
                                        endif
                                    endif
                                    if eq(patientPhase,BLOOD_FLOW) then
                                        if not(ven_connected_contr) then
                                            if eq(blood_flow_conn,PERMITTED) then
                                                patientPhase := FILL_TUBING
                                            endif
                                        else 
                                            if eq(blood_flow_conn,PERMITTED) then
                                                patientPhase := END_CONN
                                            endif
                                        endif
                                    endif
                                    if eq(patientPhase,FILL_TUBING) then
                                        if not(error_bp) then
                                            if or(blood_on_VRD,eq(conn_infuse_set_volume,PERMITTED)) then
                                                par
                                                    patientPhase := CONN_VEN
                                                    bp_status := STOP
                                                endpar
                                            endif
                                        endif
                                    endif
                                    if eq(patientPhase,CONN_VEN) then
                                        if ven_connected then
                                            par
                                                patientPhase := START_BP
                                                ven_connected_contr := true
                                            endpar
                                        endif
                                    endif
                                    if eq(patientPhase,END_CONN) then
                                        par
                                            machine_state := MAIN_FLOW
                                            bicarbonate_status := true
                                            initPhase := THERAPY_RUNNING
                                        endpar
                                    endif
                                endpar
                                if eq(bp_status_der,START) then
                                    par
                                        if not(error(CONN_VP_UP)) then
                                            if and(eq(current_vp,HIGH),passed3Sec) then
                                                par
                                                    error(CONN_VP_UP) := true
                                                    alarm(CONN_VP_UP) := true
                                                endpar
                                            endif
                                        endif
                                        if not(error(CONN_VP_LOW)) then
                                            if and(eq(current_vp,TOOLOW),passed3Sec) then
                                                par
                                                    error(CONN_VP_LOW) := true
                                                    alarm(CONN_VP_LOW) := true
                                                endpar
                                            endif
                                        endif
                                        if not(error(CONN_AP_LOW)) then
                                            if and(eq(current_ap,TOOLOW),passed1Sec) then
                                                par
                                                    error(CONN_AP_LOW) := true
                                                    alarm(CONN_AP_LOW) := true
                                                    bf_err_ap_low_conn := true
                                                    reset_err_pres_ap_low_conn := true
                                                endpar
                                            endif
                                        endif
                                        if not(error(FILL_BLOOD_VOL)) then
                                            if eq(current_fill_blood_vol,HIGH) then
                                                par
                                                    error(FILL_BLOOD_VOL) := true
                                                    alarm(FILL_BLOOD_VOL) := true
                                                endpar
                                            endif
                                        endif
                                    endpar
                                endif
                            endpar
                        endif
                        if eq(initPhase,THERAPY_RUNNING) then
                            par
                                if eq(therapyPhase,START_HEPARIN) then
                                    if activation_h_contr then
                                        par
                                            therapyPhase := THERAPY_EXEC
                                            heparin_running := true
                                        endpar
                                    endif
                                endif
                                if eq(therapyPhase,THERAPY_EXEC) then
                                    par
                                        if not(error_therapy) then
                                            par
                                                if heparin_running then
                                                    par
                                                        if not(error(HEPARIN_DIR)) then
                                                            if reverse_dir_heparin then
                                                                par
                                                                    error(HEPARIN_DIR) := true
                                                                    alarm(HEPARIN_DIR) := true
                                                                    heparin_running := false
                                                                endpar
                                                            endif
                                                        endif
                                                        if time_heparin then
                                                            heparin_running := false
                                                        endif
                                                    endpar
                                                endif
                                                if and(ap_limits_set,vp_limits_set) then
                                                    par
                                                        if eq(current_ap,PERMITTED) then
                                                            skip
                                                        endif
                                                        if eq(current_vp,PERMITTED) then
                                                            skip
                                                        endif
                                                    endpar
                                                else 
                                                    if passed10Sec then
                                                        if neq(arterialBolusPhase,CONNECT_SOLUTION) then
                                                            par
                                                                if not(ap_limits_set) then
                                                                    if and(eq(ap_limit_low,PERMITTED),eq(ap_limit_up,PERMITTED)) then
                                                                        ap_limits_set := true
                                                                    endif
                                                                endif
                                                                if not(vp_limits_set) then
                                                                    if and(eq(vp_limit_low,PERMITTED),eq(vp_limit_up,PERMITTED)) then
                                                                        vp_limits_set := true
                                                                    endif
                                                                endif
                                                            endpar
                                                        endif
                                                    endif
                                                endif
                                                if not(treatment_min_uf_rate_contr) then
                                                    if treatment_min_uf_rate then
                                                        treatment_min_uf_rate_contr := true
                                                    endif
                                                endif
                                                if not(error_bp) then
                                                    par
                                                        if eq(arterialBolusPhase,WAIT_SOLUTION) then
                                                            if start_arterial_bolus then
                                                                arterialBolusPhase := SET_ARTERIAL_BOLUS_VOLUME
                                                            endif
                                                        endif
                                                        if eq(arterialBolusPhase,SET_ARTERIAL_BOLUS_VOLUME) then
                                                            if eq(art_bolus_volume,PERMITTED) then
                                                                par
                                                                    bp_status := STOP
                                                                    arterialBolusPhase := CONNECT_SOLUTION
                                                                endpar
                                                            endif
                                                        endif
                                                        if eq(arterialBolusPhase,CONNECT_SOLUTION) then
                                                            if saline_solution_conn then
                                                                par
                                                                    arterialBolusPhase := RUNNING_SOLUTION
                                                                    bp_status := START
                                                                    ap_limits_set := false
                                                                    vp_limits_set := false
                                                                endpar
                                                            endif
                                                        endif
                                                        if eq(arterialBolusPhase,RUNNING_SOLUTION) then
                                                            if not(error(ARTERIAL_BOLUS)) then
                                                                par
                                                                    if not(alarm(ARTERIAL_BOLUS_END)) then
                                                                        if eq(current_art_bolus_volume,PERMITTED) then
                                                                            par
                                                                                arterialBolusPhase := WAIT_SOLUTION
                                                                                alarm(ARTERIAL_BOLUS_END) := true
                                                                            endpar
                                                                        endif
                                                                    endif
                                                                    if eq(current_art_bolus_volume,HIGH) then
                                                                        par
                                                                            alarm(ARTERIAL_BOLUS) := true
                                                                            error(ARTERIAL_BOLUS) := true
                                                                        endpar
                                                                    endif
                                                                endpar
                                                            endif
                                                        endif
                                                    endpar
                                                endif
                                                if time_therapy then
                                                    therapyPhase := THERAPY_END
                                                endif
                                                par
                                                    if update_blood_flow then
                                                        if eq(blood_flow_conn,PERMITTED) then
                                                            update_th_time := true
                                                        endif
                                                    endif
                                                    if and(update_th_time,not(update_blood_flow)) then
                                                        if and(eq(therapy_time_hrs,PERMITTED),eq(therapy_time_mins,PERMITTED)) then
                                                            update_th_time := false
                                                        endif
                                                    endif
                                                endpar
                                                par
                                                    if eq(bp_status_der,START) then
                                                        if and(ap_limits_set,vp_limits_set) then
                                                            par
                                                                if not(error(INIT_VP_UP)) then
                                                                    if eq(current_vp,HIGH) then
                                                                        par
                                                                            error(INIT_VP_UP) := true
                                                                            alarm(INIT_VP_UP) := true
                                                                        endpar
                                                                    endif
                                                                endif
                                                                if not(error(INIT_AP_UP)) then
                                                                    if eq(current_ap,HIGH) then
                                                                        par
                                                                            error(INIT_AP_UP) := true
                                                                            alarm(INIT_AP_UP) := true
                                                                        endpar
                                                                    endif
                                                                endif
                                                                if not(error(INIT_VP_LOW)) then
                                                                    if eq(current_vp,TOOLOW) then
                                                                        par
                                                                            error(INIT_VP_LOW) := true
                                                                            alarm(INIT_VP_LOW) := true
                                                                        endpar
                                                                    endif
                                                                endif
                                                                if not(error(INIT_AP_LOW)) then
                                                                    if eq(current_ap,TOOLOW) then
                                                                        par
                                                                            error(INIT_AP_LOW) := true
                                                                            alarm(INIT_AP_LOW) := true
                                                                            bf_err_ap_low := true
                                                                            reset_err_pres_ap_low := true
                                                                        endpar
                                                                    endif
                                                                endif
                                                            endpar
                                                        endif
                                                    endif
                                                    if bicarbonate_status_der then
                                                        par
                                                            if not(error(UF_RATE)) then
                                                                if eq(current_UF_rate,HIGH) then
                                                                    par
                                                                        error(UF_RATE) := true
                                                                        alarm(UF_RATE) := true
                                                                    endpar
                                                                endif
                                                            endif
                                                            if not(error(UF_DIR)) then
                                                                if uf_dir_backwards then
                                                                    par
                                                                        error(UF_DIR) := true
                                                                        alarm(UF_DIR) := true
                                                                    endpar
                                                                endif
                                                            endif
                                                            if not(alarm(UF_VOLUME_ERR)) then
                                                                if eq(current_UF_volume,HIGH) then
                                                                    alarm(UF_VOLUME_ERR) := true
                                                                endif
                                                            endif
                                                            if not(alarm(UF_BYPASS)) then
                                                                if eq(machine_status_der,BYPASS) then
                                                                    alarm(UF_BYPASS) := true
                                                                endif
                                                            endif
                                                        endpar
                                                    endif
                                                endpar
                                            endpar
                                        endif
                                        if interrupt_dialysis then
                                            par
                                                therapyPhase := THERAPY_END
                                                machine_state := BYPASS
                                            endpar
                                        endif
                                    endpar
                                endif
                                if eq(therapyPhase,THERAPY_END) then
                                    phase := ENDING
                                endif
                            endpar
                        endif
                    endpar
                endif
                if eq(phase,ENDING) then
                    par
                        if eq(endingPhase,REINFUSION) then
                            if not(error_rein_press) then
                                par
                                    par
                                        if eq(reinfusionPhase,REMOVE_ART) then
                                            if art_removed then
                                                par
                                                    reinfusionPhase := CONN_SALINE
                                                    art_connected_contr := false
                                                endpar
                                            endif
                                        endif
                                        if eq(reinfusionPhase,CONN_SALINE) then
                                            if saline_conn then
                                                reinfusionPhase := START_SALINE_INF
                                            endif
                                        endif
                                        if eq(reinfusionPhase,START_SALINE_INF) then
                                            par
                                                bp_status := START
                                                reinfusionPhase := RUN_SALINE_INF
                                            endpar
                                        endif
                                        if eq(reinfusionPhase,CHOOSE_NEXT_REINF_STEP) then
                                            if new_saline_reinfusion then
                                                reinfusionPhase := START_SALINE_REIN
                                            else 
                                                reinfusionPhase := REMOVE_VEN
                                            endif
                                        endif
                                        if eq(reinfusionPhase,RUN_SALINE_INF) then
                                            if saline_on_VRD then
                                                par
                                                    reinfusionPhase := CHOOSE_NEXT_REINF_STEP
                                                    bp_status := STOP
                                                endpar
                                            endif
                                        endif
                                        if eq(reinfusionPhase,START_SALINE_REIN) then
                                            par
                                                bp_status := START
                                                reinfusionPhase := RUN_SALINE_REIN
                                            endpar
                                        endif
                                        if eq(reinfusionPhase,RUN_SALINE_REIN) then
                                            if or(volume_saline_inf_400,passed5Min) then
                                                par
                                                    reinfusionPhase := CHOOSE_NEXT_REINF_STEP
                                                    bp_status := STOP
                                                endpar
                                            endif
                                        endif
                                        if eq(reinfusionPhase,REMOVE_VEN) then
                                            if ven_removed then
                                                par
                                                    endingPhase := DRAIN_DIALYZER
                                                    ven_connected_contr := false
                                                endpar
                                            endif
                                        endif
                                    endpar
                                    if eq(bp_status_der,START) then
                                        par
                                            if not(error(REIN_VP_UP)) then
                                                if and(eq(current_vp,HIGH),passed3Sec) then
                                                    par
                                                        error(REIN_VP_UP) := true
                                                        alarm(REIN_VP_UP) := true
                                                    endpar
                                                endif
                                            endif
                                            if not(error(REIN_AP_LOW)) then
                                                if and(eq(current_ap,TOOLOW),passed1Sec) then
                                                    par
                                                        error(REIN_AP_LOW) := true
                                                        alarm(REIN_AP_LOW) := true
                                                    endpar
                                                endif
                                            endif
                                            if not(error(SAD_ERR)) then
                                                if passed1Msec then
                                                    if and(neq(currentSAD,PERMITTED),neq(current_air_vol,PERMITTED)) then
                                                        par
                                                            error(SAD_ERR) := true
                                                            alarm(SAD_ERR) := true
                                                        endpar
                                                    endif
                                                endif
                                            endif
                                        endpar
                                    endif
                                endpar
                            endif
                        endif
                        if eq(endingPhase,DRAIN_DIALYZER) then
                            if dialyzer_drained then
                                par
                                    endingPhase := EMPTY_CARTRIDGE
                                    empty_dialyzer := true
                                endpar
                            endif
                        endif
                        if eq(endingPhase,EMPTY_CARTRIDGE) then
                            if cartridge_emtpy then
                                endingPhase := THERAPY_OVERVIEW
                            endif
                        endif
                        if eq(endingPhase,THERAPY_OVERVIEW) then
                            skip
                        endif
                    endpar
                endif
            endpar
            if errorExist then
                par
                    if and(error(TEMP_HIGH),not(alarm(TEMP_HIGH))) then
                        if eq(current_temp,PERMITTED) then
                            error(TEMP_HIGH) := false
                        endif
                    endif
                    if and(error(TEMP_LOW),not(alarm(TEMP_LOW))) then
                        if eq(current_temp,TOOLOW) then
                            error(TEMP_LOW) := false
                        endif
                    endif
                    if and(error(ARTERIAL_BOLUS),not(alarm(ARTERIAL_BOLUS))) then
                        if reset_error_arterial_bolus then
                            par
                                error(ARTERIAL_BOLUS) := false
                                arterialBolusPhase := WAIT_SOLUTION
                            endpar
                        endif
                    endif
                    if and(error(BP_NO_FLOW),not(alarm(BP_NO_FLOW))) then
                        if reset_error_bp_no_flow then
                            error(BP_NO_FLOW) := false
                        endif
                    endif
                    if and(error(BP_LESS_FLOW),not(alarm(BP_LESS_FLOW))) then
                        par
                            if blood_flow_error_bf_less then
                                if eq(initPhase,CONNECT_PATIENT) then
                                    if eq(blood_flow_conn,PERMITTED) then
                                        blood_flow_error_bf_less := false
                                    endif
                                else 
                                    if eq(initPhase,THERAPY_RUNNING) then
                                        if eq(blood_conductivity,PERMITTED) then
                                            blood_flow_error_bf_less := false
                                        endif
                                    endif
                                endif
                            endif
                            if th_time_error_bf_less then
                                if and(eq(therapy_time_hrs,PERMITTED),eq(therapy_time_mins,PERMITTED)) then
                                    th_time_error_bf_less := false
                                endif
                            endif
                            if and(not(blood_flow_error_bf_less),not(th_time_error_bf_less)) then
                                error(BP_LESS_FLOW) := false
                            endif
                        endpar
                    endif
                    if and(error(BP_ROTATION_DIR),not(alarm(BP_ROTATION_DIR))) then
                        if reset_error_bp_rot_dir then
                            error(BP_ROTATION_DIR) := false
                        endif
                    endif
                    if and(error(INIT_VP_UP),not(alarm(INIT_VP_UP))) then
                        if eq(current_vp,PERMITTED) then
                            error(INIT_VP_UP) := false
                        endif
                    endif
                    if and(error(INIT_VP_LOW),not(alarm(INIT_VP_LOW))) then
                        if eq(current_vp,PERMITTED) then
                            error(INIT_VP_LOW) := false
                        endif
                    endif
                    if and(error(INIT_AP_UP),not(alarm(INIT_AP_UP))) then
                        if eq(current_ap,PERMITTED) then
                            error(INIT_AP_UP) := false
                        endif
                    endif
                    if and(error(INIT_AP_LOW),not(alarm(INIT_AP_LOW))) then
                        par
                            if reset_err_pres_ap_low then
                                if eq(initPhase,CONNECT_PATIENT) then
                                    if eq(blood_flow_conn,PERMITTED) then
                                        reset_err_pres_ap_low := false
                                    endif
                                else 
                                    if eq(initPhase,THERAPY_RUNNING) then
                                        if eq(blood_conductivity,PERMITTED) then
                                            reset_err_pres_ap_low := false
                                        endif
                                    endif
                                endif
                            endif
                            if bf_err_ap_low then
                                if and(eq(therapy_time_hrs,PERMITTED),eq(therapy_time_mins,PERMITTED)) then
                                    bf_err_ap_low := false
                                endif
                            endif
                            if and(not(reset_err_pres_ap_low),not(bf_err_ap_low)) then
                                error(INIT_AP_LOW) := false
                            endif
                        endpar
                    endif
                    if and(error(CONN_VP_UP),not(alarm(CONN_VP_UP))) then
                        if eq(blood_flow_conn,PERMITTED) then
                            error(CONN_VP_UP) := false
                        endif
                    endif
                    if and(error(CONN_VP_LOW),not(alarm(CONN_VP_LOW))) then
                        if eq(current_vp,PERMITTED) then
                            error(CONN_VP_LOW) := false
                        endif
                    endif
                    if and(error(CONN_AP_LOW),not(alarm(CONN_AP_LOW))) then
                        if eq(current_ap,PERMITTED) then
                            error(CONN_AP_LOW) := false
                        endif
                    endif
                    if and(error(FILL_BLOOD_VOL),not(alarm(FILL_BLOOD_VOL))) then
                        if eq(blood_flow_conn,PERMITTED) then
                            error(FILL_BLOOD_VOL) := false
                        endif
                    endif
                    if and(error(DF_PREP),not(alarm(DF_PREP))) then
                        if detect_bicarbonate then
                            error(DF_PREP) := false
                        endif
                    endif
                    if and(error(HEPARIN_DIR),not(alarm(HEPARIN_DIR))) then
                        if error_heparin_resolve then
                            par
                                error(HEPARIN_DIR) := false
                                heparin_running := true
                            endpar
                        endif
                    endif
                    if and(error(SAD_ERR),not(alarm(SAD_ERR))) then
                        if error_SAD_resolved then
                            error(SAD_ERR) := false
                        endif
                    endif
                    if and(error(UF_RATE),not(alarm(UF_RATE))) then
                        if error_UF_rate_resolved then
                            error(UF_RATE) := false
                        endif
                    endif
                    if and(error(UF_DIR),not(alarm(UF_DIR))) then
                        if error_UF_dir_resolved then
                            error(UF_DIR) := false
                        endif
                    endif
                endpar
            endif
            if alarmExist then
                if reset_alarm then
                    par
                        if alarm(CONN_VP_LOW) then
                            alarm(CONN_VP_LOW) := false
                        endif
                        if alarm(HEPARIN_DIR) then
                            alarm(HEPARIN_DIR) := false
                        endif
                        if alarm(CONN_AP_LOW) then
                            alarm(CONN_AP_LOW) := false
                        endif
                        if alarm(BP_NO_FLOW) then
                            alarm(BP_NO_FLOW) := false
                        endif
                        if alarm(DF_PREP) then
                            alarm(DF_PREP) := false
                        endif
                        if alarm(INIT_VP_LOW) then
                            alarm(INIT_VP_LOW) := false
                        endif
                        if alarm(INIT_AP_LOW) then
                            alarm(INIT_AP_LOW) := false
                        endif
                        if alarm(UF_VOLUME_ERR) then
                            alarm(UF_VOLUME_ERR) := false
                        endif
                        if alarm(UF_DIR) then
                            alarm(UF_DIR) := false
                        endif
                        if alarm(CONN_VP_UP) then
                            alarm(CONN_VP_UP) := false
                        endif
                        if alarm(ARTERIAL_BOLUS_END) then
                            alarm(ARTERIAL_BOLUS_END) := false
                        endif
                        if alarm(TEMP_LOW) then
                            alarm(TEMP_LOW) := false
                        endif
                        if alarm(ARTERIAL_BOLUS) then
                            alarm(ARTERIAL_BOLUS) := false
                        endif
                        if alarm(INIT_AP_UP) then
                            alarm(INIT_AP_UP) := false
                        endif
                        if alarm(BP_ROTATION_DIR) then
                            alarm(BP_ROTATION_DIR) := false
                        endif
                        if alarm(REIN_VP_UP) then
                            alarm(REIN_VP_UP) := false
                        endif
                        if alarm(FILL_BLOOD_VOL) then
                            alarm(FILL_BLOOD_VOL) := false
                        endif
                        if alarm(BP_LESS_FLOW) then
                            alarm(BP_LESS_FLOW) := false
                        endif
                        if alarm(REIN_AP_LOW) then
                            alarm(REIN_AP_LOW) := false
                        endif
                        if alarm(UF_BYPASS) then
                            alarm(UF_BYPASS) := false
                        endif
                        if alarm(TEMP_HIGH) then
                            alarm(TEMP_HIGH) := false
                        endif
                        if alarm(SAD_ERR) then
                            alarm(SAD_ERR) := false
                        endif
                        if alarm(UF_RATE) then
                            alarm(UF_RATE) := false
                        endif
                        if alarm(INIT_VP_UP) then
                            alarm(INIT_VP_UP) := false
                        endif
                    endpar
                endif
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
    function signal_lamp = YELLOW
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
