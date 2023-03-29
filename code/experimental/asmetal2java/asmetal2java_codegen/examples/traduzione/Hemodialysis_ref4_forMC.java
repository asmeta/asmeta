// Hemodialysis_ref4_forMC.java automatically generated from ASM2CODE

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.Bag;
import java.util.concurrent.ThreadLocalRandom;
import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;

abstract class Hemodialysis_ref4_forMC_sig {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	//Variabile di tipo Concreto o Enumerativo
	static enum PhasesTherapy {
		PREPARATION, INITIATION, ENDING
	}

	List<PhasesTherapy> PhasesTherapy_lista = new ArrayList<PhasesTherapy>();

	//Variabile di tipo Concreto o Enumerativo

	static enum ModePreparation {
		RINSE_DIALYZER,
		SET_TREAT_PARAM,
		PREPARE_HEPARIN,
		TUBING_SYSTEM,
		SET_RINSING_PARAM,
		CONNECT_CONCENTRATE,
		AUTO_TEST
	}

	List<ModePreparation> ModePreparation_lista =
			new ArrayList<ModePreparation>();

	//Variabile di tipo Concreto o Enumerativo

	static enum ModeInitiation {
		CONNECT_PATIENT, THERAPY_RUNNING
	}

	List<ModeInitiation> ModeInitiation_lista = new ArrayList<ModeInitiation>();

	//Variabile di tipo Concreto o Enumerativo

	static enum ModeEnding {
		REINFUSION, THERAPY_OVERVIEW, EMPTY_CARTRIDGE, DRAIN_DIALYZER
	}

	List<ModeEnding> ModeEnding_lista = new ArrayList<ModeEnding>();

	//Variabile di tipo Concreto o Enumerativo

	static enum RinsingParam {
		FILLING_BP_RATE,
		FILLING_BP_VOLUME,
		BP_RATE_RINSING,
		DF_FLOW_RINSING,
		TIME_RINSING,
		UF_RATE_RINSING,
		UF_VOLUME_RINSING
	}

	List<RinsingParam> RinsingParam_lista = new ArrayList<RinsingParam>();

	//Variabile di tipo Concreto o Enumerativo

	static enum TubingPhase {
		CONNECT_AV_TUBES,
		CONNECT_ALL_COMP,
		SET_SALINE_LEVELS,
		INSERT_BLOODLINES,
		PRIMING,
		CONNECT_AV_ENDS
	}

	List<TubingPhase> TubingPhase_lista = new ArrayList<TubingPhase>();

	//Variabile di tipo Concreto o Enumerativo

	static enum TreatmentParam {
		BLOOD_CONDUCTIVITY,
		BIC_AC,
		BIC_CONDUCTIVITY,
		DF_TEMP,
		DF_FLOW,
		UF_VOLUME,
		THERAPY_TIME,
		MIN_UF_RATE,
		MAX_UF_RATE,
		MIN_AP,
		MAX_AP,
		MIN_VP,
		MAX_VP,
		DELTA_AP,
		PERC_DELTA_TMP,
		LIMITS_TMP,
		MAX_TMP,
		EXTENDED_TMP,
		MAX_BEP,
		STOP_TIME_H,
		BOLUS_VOLUME_H,
		RATE_H,
		ACTIVATION_H,
		SYRINGE_TYPE
	}

	List<TreatmentParam> TreatmentParam_lista = new ArrayList<TreatmentParam>();

	//Variabile di tipo Concreto o Enumerativo

	static enum RinsePhase {
		CONNECT_DIALYZER, FILL_ART_CHAMBER, FILL_VEN_CHAMBER, FILL_DIALYZER
	}

	List<RinsePhase> RinsePhase_lista = new ArrayList<RinsePhase>();

	//Variabile di tipo Concreto o Enumerativo

	static enum PatientPhase {
		CONN_ART, START_BP, BLOOD_FLOW, FILL_TUBING, CONN_VEN, END_CONN
	}

	List<PatientPhase> PatientPhase_lista = new ArrayList<PatientPhase>();

	//Variabile di tipo Concreto o Enumerativo

	static enum TherapyPhase {
		START_HEPARIN, THERAPY_EXEC, THERAPY_END
	}

	List<TherapyPhase> TherapyPhase_lista = new ArrayList<TherapyPhase>();

	//Variabile di tipo Concreto o Enumerativo

	static enum ReinfusionPhase {
		REMOVE_ART,
		CONN_SALINE,
		START_SALINE_INF,
		RUN_SALINE_INF,
		CHOOSE_NEXT_REINF_STEP,
		START_SALINE_REIN,
		RUN_SALINE_REIN,
		REMOVE_VEN
	}

	List<ReinfusionPhase> ReinfusionPhase_lista =
			new ArrayList<ReinfusionPhase>();

	//Variabile di tipo Concreto o Enumerativo

	static enum BPStatus {
		START, STOP
	}

	List<BPStatus> BPStatus_lista = new ArrayList<BPStatus>();

	//Variabile di tipo Concreto o Enumerativo

	static enum SignalLamps {
		GREEN, YELLOW
	}

	List<SignalLamps> SignalLamps_lista = new ArrayList<SignalLamps>();

	//Variabile di tipo Concreto o Enumerativo

	static enum MachineState {
		BYPASS, MAIN_FLOW
	}

	List<MachineState> MachineState_lista = new ArrayList<MachineState>();

	//Variabile di tipo Concreto o Enumerativo

	static enum ArterialBolusPhase {
		WAIT_SOLUTION,
		SET_ARTERIAL_BOLUS_VOLUME,
		CONNECT_SOLUTION,
		RUNNING_SOLUTION
	}

	List<ArterialBolusPhase> ArterialBolusPhase_lista = new ArrayList<
			ArterialBolusPhase>();

	//Variabile di tipo Concreto o Enumerativo

	static enum ErrorAlarmType {
		UF_BYPASS,
		UF_VOLUME_ERR,
		UF_DIR,
		UF_RATE,
		SAD_ERR,
		HEPARIN_DIR,
		DF_PREP,
		FILL_BLOOD_VOL,
		REIN_VP_UP,
		REIN_AP_LOW,
		CONN_VP_UP,
		CONN_VP_LOW,
		CONN_AP_LOW,
		INIT_VP_UP,
		INIT_VP_LOW,
		INIT_AP_UP,
		INIT_AP_LOW,
		BP_NO_FLOW,
		BP_LESS_FLOW,
		BP_ROTATION_DIR,
		TEMP_HIGH,
		TEMP_LOW,
		ARTERIAL_BOLUS,
		ARTERIAL_BOLUS_END
	}

	List<ErrorAlarmType> ErrorAlarmType_lista = new ArrayList<ErrorAlarmType>();

	//Variabile di tipo Concreto o Enumerativo

	static class SADLimit {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	SADLimit SADLimit_elem = new SADLimit();

	List<Integer> SADLimit_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class LMHlimit {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	LMHlimit LMHlimit_elem = new LMHlimit();

	List<Integer> LMHlimit_elems = new ArrayList<Integer>();

	//Variabile di tipo Concreto o Enumerativo

	static class Airlimit {

		static List<Integer> elems = new ArrayList<Integer>();

		Integer value;
	}

	Airlimit Airlimit_elem = new Airlimit();

	List<Integer> Airlimit_elems = new ArrayList<Integer>();

	//Metodi di supporto per l'implementazione delle funzioni controlled

	class zeroC<Domain> {

		Domain oldValue;
		Domain newValue;

		void set(Domain d) {

			newValue = d;
		}

		Domain get() {

			return oldValue;
		}
	}

	static class nC<Domain, Codomain> {

		Map<Domain, Codomain> oldValues = new HashMap<>();
		Map<Domain, Codomain> newValues = new HashMap<>();

		void set(Domain d, Codomain c) {

			newValues.put(d, c);
		}

		Codomain get(Domain d) {

			return oldValues.get(d);
		}
	}

	//Metodi di supporto per l'implementazione delle funzioni non controlled

	class zero<Domain> {

		Domain Value;

		void set(Domain d) {

			Value = d;
		}

		Domain get() {

			return Value;
		}
	}

	class n<Domain, Codomain> {

		Map<Domain, Codomain> Values = new HashMap<>();

		void set(Domain d, Codomain c) {

			Values.put(d, c);
		}

		Codomain get(Domain d) {

			return Values.get(d);
		}
	}

	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	//Funzione di tipo Controlled
	zeroC<PhasesTherapy> phaseTherapy = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<ModePreparation> modePreparation = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<ModeInitiation> modeInitiation = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<ModeEnding> modeEnding = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<RinsingParam> rinsingParam = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<TubingPhase> tubingPhase = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<TreatmentParam> treatmentParam = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<RinsePhase> rinsePhase = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<PatientPhase> patientPhase = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<TherapyPhase> therapyPhase = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<ReinfusionPhase> reinfusionPhase = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<MachineState> machine_state = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<SignalLamps> signal_lamp = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<ArterialBolusPhase> arterialBolusPhase = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> auto_test_end = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> conn_concentrate = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> heparin_prepared = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> dialyzer_drained = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> cartridge_emtpy = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passedHeparinTime = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passed5Min = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passedTherapyTime = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passed120Sec = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passed10Sec = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passed3Sec = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passed1Sec = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> passed1Msec = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> bp_rate_rinsing_150 = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> av_tubes_conn = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> all_comp_conn = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> saline_levels_set = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> blood_line_insert = new zero<>();

	//Funzione di tipo Controlled
	zeroC<BPStatus> bp_status = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> bp_fill_fluid = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> av_ends_conn = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> activation_h = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> activation_h_contr = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> dialyzer_connected = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> arterial_chamber_filled = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> venous_chamber_fill = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> dialyzer_filled = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> art_connected = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> art_connected_contr = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> blood_flow_conn_set = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> blood_flow_conn_reset = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> param_press_reset = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> blood_on_VRD = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> conn_infuse_set_volume = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> ven_connected = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> ven_connected_contr = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> bicarbonate_status = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> heparin_running = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> ap_limits_set = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> vp_limits_set = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> treatment_min_uf_rate = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> treatment_min_uf_rate_contr = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> interrupt_dialysis = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> start_arterial_bolus = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> saline_solution_conn = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> art_bolus_volume_set = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> art_removed = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> saline_conn = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> saline_on_VRD = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> new_saline_reinfusion = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> empty_dialyzer = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> volume_saline_inf_400 = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> ven_removed = new zero<>();

	//Funzione di tipo derived
	abstract
	Boolean errorExist();

	//Funzione di tipo derived
	abstract
	Boolean alarmExist();

	//Funzione di tipo derived
	abstract
	Boolean err_patient_conn();

	//Funzione di tipo derived
	abstract
	Boolean error_bp();

	//Funzione di tipo derived
	abstract
	Boolean error_rein_press();

	//Funzione di tipo derived
	abstract
	Boolean error_therapy();

	//Funzione di tipo Controlled
	nC<ErrorAlarmType, Boolean> error = new nC<>();

	//Funzione di tipo Controlled
	nC<ErrorAlarmType, Boolean> alarm = new nC<>();

	//Funzione di tipo monitored
	zero<Boolean> reset_alarm = new zero<>();

	//Funzione di tipo monitored
	zero<LMHlimit> current_art_bolus_volume = new zero<>();

	LMHlimit current_art_bolus_volume_supporto = new LMHlimit();

	//Funzione di tipo Controlled
	zeroC<Boolean> bf_err_vp_low = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> reset_err_pres_vp_low = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> bf_err_vp_up = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> reset_err_pres_vp_up = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> bf_err_ap_up = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> reset_err_pres_ap_up = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> bf_err_ap_low = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> reset_err_pres_ap_low = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> bf_err_vp_low_conn = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> reset_err_pres_vp_low_conn = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> bf_err_ap_low_conn = new zeroC<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> reset_err_pres_ap_low_conn = new zeroC<>();

	//Funzione di tipo monitored
	zero<Boolean> update_blood_flow = new zero<>();

	//Funzione di tipo monitored
	zero<LMHlimit> current_temp = new zero<>();

	LMHlimit current_temp_supporto = new LMHlimit();

	//Funzione di tipo monitored
	zero<Boolean> current_bp_flow_less_70 = new zero<>();

	//Funzione di tipo monitored
	zero<LMHlimit> current_vp = new zero<>();

	LMHlimit current_vp_supporto = new LMHlimit();

	//Funzione di tipo monitored
	zero<LMHlimit> current_ap = new zero<>();

	LMHlimit current_ap_supporto = new LMHlimit();

	//Funzione di tipo monitored
	zero<Boolean> fill_blood_vol_passed_up_limit = new zero<>();

	//Funzione di tipo monitored
	zero<Airlimit> current_air_vol = new zero<>();

	Airlimit current_air_vol_supporto = new Airlimit();

	//Funzione di tipo monitored
	zero<SADLimit> currentSAD = new zero<>();

	SADLimit currentSAD_supporto = new SADLimit();

	//Funzione di tipo monitored
	zero<Boolean> uf_rate_passed_max_uf_rate = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> uf_volume_passed_max_uf_volume = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> detected_blood_flow = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> bp_rotates_back = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> detect_bicarbonate = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> reverse_dir_heparin = new zero<>();

	//Funzione di tipo monitored
	zero<Boolean> uf_dir_backwards = new zero<>();

	//Funzione di tipo Controlled
	zeroC<Boolean> check_ap = new zeroC<>();

	//Funzione di tipo derived
	abstract
	MachineState machine_status_der();

	//Funzione di tipo derived
	abstract
	Boolean errorePerBYPASS();

	//Funzione di tipo derived
	abstract
	BPStatus bp_status_der();

	//Funzione di tipo derived
	abstract
	Boolean errorePERbpStatus();

	//Funzione di tipo derived
	abstract
	Boolean bicarbonate_status_der();

	//Funzione di tipo derived
	abstract
	Boolean errorePERbicarbonate();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */

	abstract
	void r_auto_test();

	abstract
	void r_connect_concentrate();

	abstract
	void r_insert_param(Integer _inf, Integer _sup, ModePreparation _nextparam,
			Integer _mon, Integer _contr);

	abstract
	void r_insert_param_press(Integer _inf, Integer _sup, Integer _monmin,
			Integer _contrmin, Integer _monmax, Integer _contrmax,
			Boolean _succ);

	abstract
	void r_insert_param(Integer _inf, Integer _sup, TreatmentParam _nextparam,
			Integer _mon, Integer _contr);

	abstract
	void r_insert_param(Integer _inf, Integer _sup, PatientPhase _nextparam,
			Integer _mon, Integer _contr);

	abstract
	void r_insert_param(TreatmentParam _nextparam, Boolean _mon,
			Boolean _contr);

	abstract
	void r_insert_param(PatientPhase _phase, Boolean _setvalue);

	abstract
	void r_check_temp_high();

	abstract
	void r_check_temp_low();

	abstract
	void r_check_BP_rotation_dir();

	abstract
	void r_check_bp_less_flow();

	abstract
	void r_check_bp_no_flow_err();

	abstract
	void r_check_init_press_limit(ErrorAlarmType _err, LMHlimit _limit,
			Boolean _errbf, Boolean _errpress);

	abstract
	void r_check_init_vp_up_limit();

	abstract
	void r_check_init_ap_up_limit();

	abstract
	void r_check_init_vp_low_limit();

	abstract
	void r_check_init_ap_low_limit();

	abstract
	void r_check_conn_rein_press_limit(ErrorAlarmType _err, LMHlimit _limit,
			Boolean _passedT);

	abstract
	void r_check_conn_rein_press_limit(ErrorAlarmType _err, LMHlimit _limit,
			Boolean _passedT, Boolean _errbf, Boolean _errpress);

	abstract
	void r_check_conn_vp_up_limit();

	abstract
	void r_check_conn_vp_low_limit();

	abstract
	void r_check_conn_ap_low_limit();

	abstract
	void r_check_rein_vp_up_limit();

	abstract
	void r_check_rein_ap_low_limit();

	abstract
	void r_check_fill_blood_vol();

	abstract
	void r_check_df_prep();

	abstract
	void r_check_heparin();

	abstract
	void r_set_err_SAD();

	abstract
	void r_check_SAD();

	abstract
	void r_check_UF_rate();

	abstract
	void r_check_UF_dir();

	abstract
	void r_check_UF_volume();

	abstract
	void r_check_UF_bypass();

	abstract
	void r_error_temp_high();

	abstract
	void r_error_temp_low();

	abstract
	void r_error_arterial_bolus();

	abstract
	void r_error_BP_no_flow();

	abstract
	void r_error_BP_less_flow();

	abstract
	void r_error_BP_rotation_dir();

	abstract
	void r_error_pressure_limit(ErrorAlarmType _err, Boolean _bfErr,
			Boolean _resetP);

	abstract
	void r_error_vp_up_limit();

	abstract
	void r_error_vp_low_limit();

	abstract
	void r_error_ap_up_limit();

	abstract
	void r_error_ap_low_limit();

	abstract
	void r_error_update_blood_flow(ErrorAlarmType _err);

	abstract
	void r_error_conn_vp_up_limit();

	abstract
	void r_error_conn_vp_low_limit();

	abstract
	void r_error_conn_ap_low_limit();

	abstract
	void r_error_rein_vp_up_limit();

	abstract
	void r_error_rein_ap_low_limit();

	abstract
	void r_error_fill_blood_vol();

	abstract
	void r_error_df_prep();

	abstract
	void r_error_heparin();

	abstract
	void r_error_SAD();

	abstract
	void r_error_UF_rate();

	abstract
	void r_error_UF_dir();

	abstract
	void r_run_error();

	abstract
	void r_run_alarm();

	abstract
	void r_set_filling_bp_rate();

	abstract
	void r_set_filling_bp_volume();

	abstract
	void r_set_bp_rate_rinsing();

	abstract
	void r_set_df_flow_rinsing();

	abstract
	void r_set_time_rinsing();

	abstract
	void r_set_uf_rate_rinsing();

	abstract
	void r_set_uf_volume_rinsing();

	abstract
	void r_set_rinsing_param();

	abstract
	void r_connect_av_tubes();

	abstract
	void r_connect_all_comp();

	abstract
	void r_set_saline_levels();

	abstract
	void r_insert_bloodlines();

	abstract
	void r_priming();

	abstract
	void r_connect_av_ends();

	abstract
	void r_tubing_system();

	abstract
	void r_prepare_heparin();

	abstract
	void r_set_blood_conductivity();

	abstract
	void r_set_bic_ac();

	abstract
	void r_set_bic_conductivity();

	abstract
	void r_set_df_temp();

	abstract
	void r_set_df_flow();

	abstract
	void r_set_uf_volume();

	abstract
	void r_set_therapy_time();

	abstract
	void r_set_min_uf_rate();

	abstract
	void r_set_max_uf_rate();

	abstract
	void r_set_min_ap();

	abstract
	void r_set_max_ap();

	abstract
	void r_set_min_vp();

	abstract
	void r_set_max_vp();

	abstract
	void r_set_delta_ap();

	abstract
	void r_set_perc_delta_tmp();

	abstract
	void r_set_limits_tmp();

	abstract
	void r_set_max_tmp();

	abstract
	void r_set_extended_tmp();

	abstract
	void r_set_max_bep();

	abstract
	void r_set_h_stop_time();

	abstract
	void r_set_h_bolus_volume();

	abstract
	void r_set_h_rate();

	abstract
	void r_set_h_activation();

	abstract
	void r_set_syringe_type();

	abstract
	void r_set_treatment_param();

	abstract
	void r_connect_dialyzer();

	abstract
	void r_fill_art_chamber();

	abstract
	void r_fill_ven_chamber();

	abstract
	void r_fill_dialyzer();

	abstract
	void r_rinse_dialyzer();

	abstract
	void r_preparation();

	abstract
	void r_check_initiation_phase();

	abstract
	void r_conn_arterially();

	abstract
	void r_start_bp();

	abstract
	void r_set_blood_flow();

	abstract
	void r_fill_tubing();

	abstract
	void r_conn_venously();

	abstract
	void r_end_connection();

	abstract
	void r_patient_connection();

	abstract
	void r_check_patient();

	abstract
	void r_connect_patient();

	abstract
	void r_start_heparin();

	abstract
	void r_run_heparin();

	abstract
	void r_set_ap_vp_limits();

	abstract
	void r_ap_vp_limits();

	abstract
	void r_treatment_min_UF();

	abstract
	void r_interrupt();

	abstract
	void r_start_arterial_bolus();

	abstract
	void r_set_arterial_bolus_volume();

	abstract
	void r_connect_solution();

	abstract
	void r_running_solution();

	abstract
	void r_check_arterial_bolus();

	abstract
	void r_run_arterial_bolus();

	abstract
	void r_arterial_bolus();

	abstract
	void r_therapy_end_time();

	abstract
	void r_update_blood_flow();

	abstract
	void r_check_therapy_run();

	abstract
	void r_therapy_exec();

	abstract
	void r_therapy_end();

	abstract
	void r_running_therapy();

	abstract
	void r_initiation();

	abstract
	void r_remove_arterially();

	abstract
	void r_connect_saline();

	abstract
	void r_start_saline_inf();

	abstract
	void r_run_saline_inf();

	abstract
	void r_choose_next_reinf_step();

	abstract
	void r_start_saline_reinf();

	abstract
	void r_run_saline_reinf();

	abstract
	void r_remove_venously();

	abstract
	void r_run_reinfusion();

	abstract
	void r_check_reinfusion();

	abstract
	void r_reinfusion();

	abstract
	void r_drain_dialyzer();

	abstract
	void r_empty_cartridge();

	abstract
	void r_therapy_overview();

	abstract
	void r_ending();

	abstract
	void r_run_therapy();

	abstract
	void r_Main();

}

class Hemodialysis_ref4_forMC extends Hemodialysis_ref4_forMC_sig {

	// Inizializzazione di funzioni e domini

	Hemodialysis_ref4_forMC() {

		//Definizione iniziale dei domini statici

		SADLimit.elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
		SADLimit_elems = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));
		LMHlimit.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2));
		LMHlimit_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2));
		Airlimit.elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3));
		Airlimit_elems = Collections.unmodifiableList(Arrays.asList(0, 1, 2, 3));
		//setto la lista di elementi di supporto della classe enumerativa
		for(PhasesTherapy i : PhasesTherapy.values())
		PhasesTherapy_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(ModePreparation i : ModePreparation.values())
		ModePreparation_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(ModeInitiation i : ModeInitiation.values())
		ModeInitiation_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(ModeEnding i : ModeEnding.values())
		ModeEnding_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(RinsingParam i : RinsingParam.values())
		RinsingParam_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(TubingPhase i : TubingPhase.values())
		TubingPhase_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(TreatmentParam i : TreatmentParam.values())
		TreatmentParam_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(RinsePhase i : RinsePhase.values())
		RinsePhase_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(PatientPhase i : PatientPhase.values())
		PatientPhase_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(TherapyPhase i : TherapyPhase.values())
		TherapyPhase_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(ReinfusionPhase i : ReinfusionPhase.values())
		ReinfusionPhase_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(BPStatus i : BPStatus.values())
		BPStatus_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(SignalLamps i : SignalLamps.values())
		SignalLamps_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(MachineState i : MachineState.values())
		MachineState_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(ArterialBolusPhase i : ArterialBolusPhase.values())
		ArterialBolusPhase_lista.add(i);
		//setto la lista di elementi di supporto della classe enumerativa
		for(ErrorAlarmType i : ErrorAlarmType.values())
		ErrorAlarmType_lista.add(i);

		//Definizione iniziale dei domini dinamici

		//Definizione iniziale dei domini astratti con funzini statiche

		//Inizializzazione delle funzioni

		phaseTherapy.oldValue = phaseTherapy.newValue = PhasesTherapy.PREPARATION;
		modePreparation.oldValue = modePreparation.newValue = ModePreparation.AUTO_TEST;
		modeInitiation.oldValue = modeInitiation.newValue = ModeInitiation.CONNECT_PATIENT;
		modeEnding.oldValue = modeEnding.newValue = ModeEnding.REINFUSION;
		rinsingParam.oldValue = rinsingParam.newValue = RinsingParam.FILLING_BP_RATE;
		tubingPhase.oldValue = tubingPhase.newValue = TubingPhase.CONNECT_AV_TUBES;
		treatmentParam.oldValue = treatmentParam.newValue = TreatmentParam.BLOOD_CONDUCTIVITY;
		rinsePhase.oldValue = rinsePhase.newValue = RinsePhase.CONNECT_DIALYZER;
		patientPhase.oldValue = patientPhase.newValue = PatientPhase.CONN_ART;
		therapyPhase.oldValue = therapyPhase.newValue = TherapyPhase.START_HEPARIN;
		reinfusionPhase.oldValue = reinfusionPhase.newValue = ReinfusionPhase.REMOVE_ART;
		machine_state.oldValue = machine_state.newValue = MachineState.BYPASS;
		signal_lamp.oldValue = signal_lamp.newValue = SignalLamps.YELLOW;
		arterialBolusPhase.oldValue = arterialBolusPhase.newValue = ArterialBolusPhase.WAIT_SOLUTION;
		bp_status.oldValue = bp_status.newValue = BPStatus.STOP;
		activation_h_contr.oldValue = activation_h_contr.newValue = false;
		ven_connected_contr.oldValue = ven_connected_contr.newValue = false;
		art_connected_contr.oldValue = art_connected_contr.newValue = false;
		bicarbonate_status.oldValue = bicarbonate_status.newValue = false;
		heparin_running.oldValue = heparin_running.newValue = false;
		ap_limits_set.oldValue = ap_limits_set.newValue = false;
		vp_limits_set.oldValue = vp_limits_set.newValue = false;
		treatment_min_uf_rate_contr.oldValue = treatment_min_uf_rate_contr.newValue = false;
		empty_dialyzer.oldValue = empty_dialyzer.newValue = false;
		check_ap.oldValue = check_ap.newValue = true;
		for(ErrorAlarmType _t: ErrorAlarmType.values()) {
			Boolean a = false;

			error.oldValues.put(_t,a);
			error.newValues.put(_t,a);
		}
		for(ErrorAlarmType _t: ErrorAlarmType.values()) {
			Boolean a = false;

			alarm.oldValues.put(_t,a);
			alarm.newValues.put(_t,a);
		}
		bf_err_vp_up.oldValue = bf_err_vp_up.newValue = false;
		reset_err_pres_vp_up.oldValue = reset_err_pres_vp_up.newValue = false;
		bf_err_vp_low.oldValue = bf_err_vp_low.newValue = false;
		reset_err_pres_vp_low.oldValue = reset_err_pres_vp_low.newValue = false;
		bf_err_ap_up.oldValue = bf_err_ap_up.newValue = false;
		reset_err_pres_ap_up.oldValue = reset_err_pres_ap_up.newValue = false;
		bf_err_ap_low.oldValue = bf_err_ap_low.newValue = false;
		reset_err_pres_ap_low.oldValue = reset_err_pres_ap_low.newValue = false;
		bf_err_vp_low_conn.oldValue = bf_err_vp_low_conn.newValue = false;
		reset_err_pres_vp_low_conn.oldValue = reset_err_pres_vp_low_conn.newValue = false;
		bf_err_ap_low_conn.oldValue = bf_err_ap_low_conn.newValue = false;
		reset_err_pres_ap_low_conn.oldValue = reset_err_pres_ap_low_conn.newValue = false;

	}

	// Definizione delle funzioni statiche
	Boolean errorePerBYPASS() {return error.get(ErrorAlarmType.TEMP_HIGH) || error.get(ErrorAlarmType.TEMP_LOW) || error.get(ErrorAlarmType.DF_PREP) || error.get(ErrorAlarmType.UF_DIR) || alarm.get(ErrorAlarmType.UF_VOLUME_ERR);}
	Boolean errorePERbpStatus() {return error.get(ErrorAlarmType.BP_ROTATION_DIR) || error.get(ErrorAlarmType.BP_NO_FLOW) || error.get(ErrorAlarmType.INIT_VP_UP) || error.get(ErrorAlarmType.INIT_AP_UP) || error.get(ErrorAlarmType.INIT_VP_LOW) || error.get(ErrorAlarmType.INIT_AP_LOW) || error.get(ErrorAlarmType.CONN_VP_UP) || error.get(ErrorAlarmType.CONN_VP_LOW) || error.get(ErrorAlarmType.CONN_AP_LOW) || error.get(ErrorAlarmType.REIN_VP_UP) || error.get(ErrorAlarmType.REIN_AP_LOW) || error.get(ErrorAlarmType.FILL_BLOOD_VOL) || error.get(ErrorAlarmType.HEPARIN_DIR) || error.get(ErrorAlarmType.SAD_ERR) || error.get(ErrorAlarmType.ARTERIAL_BOLUS);}
	Boolean errorePERbicarbonate() {return error.get(ErrorAlarmType.DF_PREP) || error.get(ErrorAlarmType.UF_RATE) || alarm.get(ErrorAlarmType.UF_BYPASS);}
	MachineState machine_status_der() {return /*conditionalTerm*/
		(errorePerBYPASS())
		?
		MachineState.BYPASS
		:
		machine_state.get()
		;}
	BPStatus bp_status_der() {return /*conditionalTerm*/
		(errorePERbpStatus())
		?
		BPStatus.STOP
		:
		bp_status.get()
		;}
	Boolean bicarbonate_status_der() {return /*conditionalTerm*/
		(errorePERbicarbonate())
		?
		false
		:
		bicarbonate_status.get()
		;}
	Boolean errorExist() {return ErrorAlarmType_lista.stream().anyMatch(c -> error.get(c))
		;}
	Boolean alarmExist() {return ErrorAlarmType_lista.stream().anyMatch(c -> alarm.get(c))
		;}
	Boolean err_patient_conn() {return (error.get(ErrorAlarmType.CONN_VP_UP) == true) || (error.get(ErrorAlarmType.CONN_VP_LOW) == true) || (error.get(ErrorAlarmType.CONN_AP_LOW) == true) || (error.get(ErrorAlarmType.FILL_BLOOD_VOL) == true) || (error.get(ErrorAlarmType.SAD_ERR) == true);}
	Boolean error_bp() {return (error.get(ErrorAlarmType.BP_NO_FLOW) == true) || (error.get(ErrorAlarmType.BP_ROTATION_DIR) == true) || (error.get(ErrorAlarmType.BP_LESS_FLOW) == true);}
	Boolean error_rein_press() {return (error.get(ErrorAlarmType.REIN_VP_UP) == true) || (error.get(ErrorAlarmType.REIN_AP_LOW) == true) || (error.get(ErrorAlarmType.SAD_ERR) == true);}
	Boolean error_therapy() {return (error.get(ErrorAlarmType.INIT_VP_UP) == true) || (error.get(ErrorAlarmType.INIT_VP_LOW) == true) || (error.get(ErrorAlarmType.INIT_AP_UP) == true) || (error.get(ErrorAlarmType.INIT_AP_LOW) == true) || (error.get(ErrorAlarmType.SAD_ERR) == true) || (error.get(ErrorAlarmType.HEPARIN_DIR) == true);}

	// Conversione delle regole ASM in metodi java

	@Override
	void r_auto_test() {
		if ((auto_test_end.get() == true)) {
			modePreparation.set(ModePreparation.CONNECT_CONCENTRATE);
		}
	}

	@Override
	void r_connect_concentrate() {
		if ((conn_concentrate.get() == true)) {
			modePreparation.set(ModePreparation.SET_RINSING_PARAM);
		}
	}

	@Override
	void r_insert_param (Integer _inf, Integer _sup, ModePreparation _nextparam, Integer _mon, Integer _contr) {
		if ((_mon >= _inf) && (_mon <= _sup)) {
			{ //par
				_contr = (_mon);

				modePreparation.set(_nextparam);
			} //endpar
		}
	}

	@Override
	void r_insert_param_press (Integer _inf, Integer _sup, Integer _monmin, Integer _contrmin, Integer _monmax, Integer _contrmax, Boolean _succ) {
		if ((_monmin >= _inf) && (_monmin <= _sup) && (_monmax >= _inf) && (_monmax <= _sup)) {
			{ //par
				_contrmin = (_monmin);

				_contrmax = (_monmax);

				_succ = (false);

			} //endpar
		}
	}

	@Override
	void r_insert_param (Integer _inf, Integer _sup, TreatmentParam _nextparam, Integer _mon, Integer _contr) {
		if ((_mon >= _inf) && (_mon <= _sup)) {
			{ //par
				_contr = (_mon);

				treatmentParam.set(_nextparam);
			} //endpar
		}
	}

	@Override
	void r_insert_param (Integer _inf, Integer _sup, PatientPhase _nextparam, Integer _mon, Integer _contr) {
		if ((_mon >= _inf) && (_mon <= _sup)) {
			{ //par
				_contr = (_mon);

				patientPhase.set(_nextparam);
			} //endpar
		}
	}

	@Override
	void r_insert_param (TreatmentParam _nextparam, Boolean _mon, Boolean _contr) {
		{ //par
			_contr = (_mon);

			treatmentParam.set(_nextparam);
		} //endpar
	}

	@Override
	void r_insert_param (PatientPhase _phase, Boolean _setvalue) {
		if ((_setvalue == true)) {
			patientPhase.set(_phase);
		}
	}

	@Override
	void r_check_temp_high() {
		if ((error.get(ErrorAlarmType.TEMP_HIGH) == false)) {
			if ((current_temp.get().value == 2)) {
				{ //par
					error.set(ErrorAlarmType.TEMP_HIGH, true);
					alarm.set(ErrorAlarmType.TEMP_HIGH, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_temp_low() {
		if ((error.get(ErrorAlarmType.TEMP_LOW) == false)) {
			if ((current_temp.get().value == 0)) {
				{ //par
					error.set(ErrorAlarmType.TEMP_LOW, true);
					alarm.set(ErrorAlarmType.TEMP_LOW, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_BP_rotation_dir() {
		if ((error.get(ErrorAlarmType.BP_ROTATION_DIR) == false)) {
			if ((bp_rotates_back.get() == true)) {
				{ //par
					error.set(ErrorAlarmType.BP_ROTATION_DIR, true);
					alarm.set(ErrorAlarmType.BP_ROTATION_DIR, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_bp_less_flow() {
		if ((error.get(ErrorAlarmType.BP_LESS_FLOW) == false)) {
			if ((machine_status_der() == MachineState.MAIN_FLOW)) {
				if ((current_bp_flow_less_70.get() == true)) {
					{ //par
						alarm.set(ErrorAlarmType.BP_LESS_FLOW, true);
						error.set(ErrorAlarmType.BP_LESS_FLOW, true);
					} //endpar
				}
			}
		}
	}

	@Override
	void r_check_bp_no_flow_err() {
		if ((error.get(ErrorAlarmType.BP_NO_FLOW) == false)) {
			if ((detected_blood_flow.get() == false) && (passed120Sec.get() == true)) {
				{ //par
					error.set(ErrorAlarmType.BP_NO_FLOW, true);
					alarm.set(ErrorAlarmType.BP_NO_FLOW, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_init_press_limit (ErrorAlarmType _err, LMHlimit _limit, Boolean _errbf, Boolean _errpress) {
		if ((error.get(_err) == false)) {
			if ((_limit.value == 0) || (_limit.value == 2)) {
				{ //par
					error.set(_err, true);
					alarm.set(_err, true);
					_errbf = (true);

					_errpress = (true);

				} //endpar
			}
		}
	}

	@Override
	void r_check_init_vp_up_limit() {
		r_check_init_press_limit(ErrorAlarmType.INIT_VP_UP, current_vp.get(), bf_err_vp_up.get(), reset_err_pres_vp_up.get());
	}

	@Override
	void r_check_init_ap_up_limit() {
		r_check_init_press_limit(ErrorAlarmType.INIT_AP_UP, current_ap.get(), bf_err_ap_up.get(), reset_err_pres_ap_up.get());
	}

	@Override
	void r_check_init_vp_low_limit() {
		r_check_init_press_limit(ErrorAlarmType.INIT_VP_LOW, current_vp.get(), bf_err_vp_low.get(), reset_err_pres_vp_low.get());
	}

	@Override
	void r_check_init_ap_low_limit() {
		r_check_init_press_limit(ErrorAlarmType.INIT_AP_LOW, current_ap.get(), bf_err_ap_low.get(), reset_err_pres_ap_low.get());
	}

	@Override
	void r_check_conn_rein_press_limit (ErrorAlarmType _err, LMHlimit _limit, Boolean _passedT) {
		if ((error.get(_err) == false)) {
			if ((_limit.value == 0) || (_limit.value == 2) && (_passedT == true)) {
				{ //par
					error.set(_err, true);
					alarm.set(_err, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_conn_rein_press_limit (ErrorAlarmType _err, LMHlimit _limit, Boolean _passedT, Boolean _errbf, Boolean _errpress) {
		if ((error.get(_err) == false)) {
			if ((_limit.value == 0) || (_limit.value == 2) && (_passedT == true)) {
				{ //par
					error.set(_err, true);
					alarm.set(_err, true);
					_errbf = (true);

					_errpress = (true);

				} //endpar
			}
		}
	}

	@Override
	void r_check_conn_vp_up_limit() {
		r_check_conn_rein_press_limit(ErrorAlarmType.CONN_VP_UP, current_vp.get(), passed3Sec.get());
	}

	@Override
	void r_check_conn_vp_low_limit() {
		r_check_conn_rein_press_limit(ErrorAlarmType.CONN_VP_LOW, current_vp.get(), passed3Sec.get(), bf_err_vp_low_conn.get(), reset_err_pres_vp_low_conn.get());
	}

	@Override
	void r_check_conn_ap_low_limit() {
		r_check_conn_rein_press_limit(ErrorAlarmType.CONN_AP_LOW, current_ap.get(), passed1Sec.get(), bf_err_ap_low_conn.get(), reset_err_pres_ap_low_conn.get());
	}

	@Override
	void r_check_rein_vp_up_limit() {
		r_check_conn_rein_press_limit(ErrorAlarmType.REIN_VP_UP, current_vp.get(), passed3Sec.get());
	}

	@Override
	void r_check_rein_ap_low_limit() {
		r_check_conn_rein_press_limit(ErrorAlarmType.REIN_AP_LOW, current_ap.get(), passed1Sec.get());
	}

	@Override
	void r_check_fill_blood_vol() {
		if ((error.get(ErrorAlarmType.FILL_BLOOD_VOL) == false)) {
			if ((fill_blood_vol_passed_up_limit.get() == true)) {
				{ //par
					error.set(ErrorAlarmType.FILL_BLOOD_VOL, true);
					alarm.set(ErrorAlarmType.FILL_BLOOD_VOL, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_df_prep() {
		if ((error.get(ErrorAlarmType.DF_PREP) == false)) {
			if ((detect_bicarbonate.get() == false)) {
				{ //par
					error.set(ErrorAlarmType.DF_PREP, true);
					alarm.set(ErrorAlarmType.DF_PREP, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_heparin() {
		if ((error.get(ErrorAlarmType.HEPARIN_DIR) == false)) {
			if ((reverse_dir_heparin.get() == true)) {
				{ //par
					error.set(ErrorAlarmType.HEPARIN_DIR, true);
					alarm.set(ErrorAlarmType.HEPARIN_DIR, true);
					heparin_running.set(false);
				} //endpar
			}
		}
	}

	@Override
	void r_set_err_SAD() {
		{ //par
			error.set(ErrorAlarmType.SAD_ERR, true);
			alarm.set(ErrorAlarmType.SAD_ERR, true);
		} //endpar
	}

	@Override
	void r_check_SAD() {
		if ((error.get(ErrorAlarmType.SAD_ERR) == false)) {
			if ((passed1Msec.get() == true)) {
				{ //par
					if ((currentSAD.get().value == 4)) {
						r_set_err_SAD();
					}
					if ((currentSAD.get().value == 1)) {
						if ((current_air_vol.get().value > 0)) {
							r_set_err_SAD();
						}
					}
					if ((currentSAD.get().value == 2)) {
						if ((current_air_vol.get().value > 1)) {
							r_set_err_SAD();
						}
					}
					if ((currentSAD.get().value == 3)) {
						if ((current_air_vol.get().value > 2)) {
							r_set_err_SAD();
						}
					}
				} //endpar
			}
		}
	}

	@Override
	void r_check_UF_rate() {
		if ((error.get(ErrorAlarmType.UF_RATE) == false)) {
			if ((uf_rate_passed_max_uf_rate.get() == true)) {
				{ //par
					error.set(ErrorAlarmType.UF_RATE, true);
					alarm.set(ErrorAlarmType.UF_RATE, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_UF_dir() {
		if ((error.get(ErrorAlarmType.UF_DIR) == false)) {
			if ((uf_dir_backwards.get() == true)) {
				{ //par
					error.set(ErrorAlarmType.UF_DIR, true);
					alarm.set(ErrorAlarmType.UF_DIR, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_UF_volume() {
		if ((alarm.get(ErrorAlarmType.UF_VOLUME_ERR) == false)) {
			if ((uf_volume_passed_max_uf_volume.get() == true)) {
				alarm.set(ErrorAlarmType.UF_VOLUME_ERR, true);
			}
		}
	}

	@Override
	void r_check_UF_bypass() {
		if ((alarm.get(ErrorAlarmType.UF_BYPASS) == false)) {
			if ((machine_status_der() == MachineState.BYPASS)) {
				alarm.set(ErrorAlarmType.UF_BYPASS, true);
			}
		}
	}

	@Override
	void r_error_temp_high() {
		if ((error.get(ErrorAlarmType.TEMP_HIGH) == true) && (alarm.get(ErrorAlarmType.TEMP_HIGH) == false)) {
			if ((current_temp.get().value == 1)) {
				error.set(ErrorAlarmType.TEMP_HIGH, false);
			}
		}
	}

	@Override
	void r_error_temp_low() {
		if ((error.get(ErrorAlarmType.TEMP_LOW) == true) && (alarm.get(ErrorAlarmType.TEMP_LOW) == false)) {
			if ((current_temp.get().value == 1)) {
				error.set(ErrorAlarmType.TEMP_LOW, false);
			}
		}
	}

	@Override
	void r_error_arterial_bolus() {
		if ((error.get(ErrorAlarmType.ARTERIAL_BOLUS) == true) && (alarm.get(ErrorAlarmType.ARTERIAL_BOLUS) == false)) {
			if ((current_art_bolus_volume.get().value == 1)) {
				{ //par
					error.set(ErrorAlarmType.ARTERIAL_BOLUS, false);
					arterialBolusPhase.set(ArterialBolusPhase.WAIT_SOLUTION);
				} //endpar
			}
		}
	}

	@Override
	void r_error_BP_no_flow() {
		if ((error.get(ErrorAlarmType.BP_NO_FLOW) == true) && (alarm.get(ErrorAlarmType.BP_NO_FLOW) == false)) {
			if ((detected_blood_flow.get() == true)) {
				error.set(ErrorAlarmType.BP_NO_FLOW, false);
			}
		}
	}

	@Override
	void r_error_BP_less_flow() {
		if ((error.get(ErrorAlarmType.BP_LESS_FLOW) == true) && (alarm.get(ErrorAlarmType.BP_LESS_FLOW) == false)) {
			if ((current_bp_flow_less_70.get() == false)) {
				error.set(ErrorAlarmType.BP_LESS_FLOW, false);
			}
		}
	}

	@Override
	void r_error_BP_rotation_dir() {
		if ((error.get(ErrorAlarmType.BP_ROTATION_DIR) == true) && (alarm.get(ErrorAlarmType.BP_ROTATION_DIR) == false)) {
			if ((bp_rotates_back.get() == false)) {
				error.set(ErrorAlarmType.BP_ROTATION_DIR, false);
			}
		}
	}

	@Override
	void r_error_pressure_limit (ErrorAlarmType _err, Boolean _bfErr, Boolean _resetP) {
		if ((error.get(_err) == true) && (alarm.get(_err) == false)) {
			{ //par
				if ((_bfErr == true)) {
					if ((blood_flow_conn_reset.get() == true)) {
						_bfErr = (false);

					}
				}
				if ((_resetP == true)) {
					if ((param_press_reset.get() == true)) {
						_resetP = (false);

					}
				}
				if ((_bfErr == false) && (_resetP == false)) {
					error.set(_err, false);
				}
			} //endpar
		}
	}

	@Override
	void r_error_vp_up_limit() {
		r_error_pressure_limit(ErrorAlarmType.INIT_VP_UP, bf_err_vp_up.get(), reset_err_pres_vp_up.get());
	}

	@Override
	void r_error_vp_low_limit() {
		r_error_pressure_limit(ErrorAlarmType.INIT_VP_LOW, bf_err_vp_low.get(), reset_err_pres_vp_low.get());
	}

	@Override
	void r_error_ap_up_limit() {
		r_error_pressure_limit(ErrorAlarmType.INIT_AP_UP, bf_err_ap_up.get(), reset_err_pres_ap_up.get());
	}

	@Override
	void r_error_ap_low_limit() {
		r_error_pressure_limit(ErrorAlarmType.INIT_AP_LOW, bf_err_ap_low.get(), reset_err_pres_ap_low.get());
	}

	@Override
	void r_error_update_blood_flow (ErrorAlarmType _err) {
		if ((error.get(_err) == true) && (alarm.get(_err) == false)) {
			if ((blood_flow_conn_reset.get() == true)) {
				error.set(_err, false);
			}
		}
	}

	@Override
	void r_error_conn_vp_up_limit() {
		r_error_update_blood_flow(ErrorAlarmType.CONN_VP_UP);
	}

	@Override
	void r_error_conn_vp_low_limit() {
		r_error_pressure_limit(ErrorAlarmType.CONN_VP_LOW, bf_err_vp_low_conn.get(), reset_err_pres_vp_low_conn.get());
	}

	@Override
	void r_error_conn_ap_low_limit() {
		r_error_pressure_limit(ErrorAlarmType.CONN_AP_LOW, bf_err_ap_low_conn.get(), reset_err_pres_ap_low_conn.get());
	}

	@Override
	void r_error_rein_vp_up_limit() {
		r_error_update_blood_flow(ErrorAlarmType.REIN_VP_UP);
	}

	@Override
	void r_error_rein_ap_low_limit() {
		r_error_update_blood_flow(ErrorAlarmType.REIN_AP_LOW);
	}

	@Override
	void r_error_fill_blood_vol() {
		r_error_update_blood_flow(ErrorAlarmType.FILL_BLOOD_VOL);
	}

	@Override
	void r_error_df_prep() {
		if ((error.get(ErrorAlarmType.DF_PREP) == true) && (alarm.get(ErrorAlarmType.DF_PREP) == false)) {
			if ((detect_bicarbonate.get() == true)) {
				error.set(ErrorAlarmType.DF_PREP, false);
			}
		}
	}

	@Override
	void r_error_heparin() {
		if ((error.get(ErrorAlarmType.HEPARIN_DIR) == true) && (alarm.get(ErrorAlarmType.HEPARIN_DIR) == false)) {
			if ((reverse_dir_heparin.get() == false)) {
				{ //par
					error.set(ErrorAlarmType.HEPARIN_DIR, false);
					heparin_running.set(true);
				} //endpar
			}
		}
	}

	@Override
	void r_error_SAD() {
		if ((error.get(ErrorAlarmType.SAD_ERR) == true) && (alarm.get(ErrorAlarmType.SAD_ERR) == false)) {
			if ((currentSAD.get().value == 1) && (current_air_vol.get().value == 0) || (currentSAD.get().value == 2) && (current_air_vol.get().value == 1) || (currentSAD.get().value == 3) && (current_air_vol.get().value == 2)) {
				error.set(ErrorAlarmType.SAD_ERR, false);
			}
		}
	}

	@Override
	void r_error_UF_rate() {
		if ((error.get(ErrorAlarmType.UF_RATE) == true) && (alarm.get(ErrorAlarmType.UF_RATE) == false)) {
			if ((uf_rate_passed_max_uf_rate.get() == false)) {
				error.set(ErrorAlarmType.UF_RATE, true);
			}
		}
	}

	@Override
	void r_error_UF_dir() {
		if ((error.get(ErrorAlarmType.UF_DIR) == true) && (alarm.get(ErrorAlarmType.UF_DIR) == false)) {
			if ((uf_dir_backwards.get() == false)) {
				error.set(ErrorAlarmType.UF_DIR, true);
			}
		}
	}

	@Override
	void r_run_error() {
		{ //par
			r_error_temp_high();
			r_error_temp_low();
			r_error_arterial_bolus();
			r_error_BP_no_flow();
			r_error_BP_less_flow();
			r_error_BP_rotation_dir();
			r_error_vp_up_limit();
			r_error_vp_low_limit();
			r_error_ap_up_limit();
			r_error_ap_low_limit();
			r_error_conn_vp_up_limit();
			r_error_conn_vp_low_limit();
			r_error_conn_ap_low_limit();
			r_error_rein_vp_up_limit();
			r_error_rein_ap_low_limit();
			r_error_fill_blood_vol();
			r_error_df_prep();
			r_error_heparin();
			r_error_SAD();
			r_error_UF_rate();
			r_error_UF_dir();
		} //endpar
	}

	@Override
	void r_run_alarm() {
		if ((reset_alarm.get() == true)) {
			for(ErrorAlarmType _alarmon : ErrorAlarmType_lista)
			if((alarm.get(_alarmon) == true)) {
				alarm.set(_alarmon, false);
			}
		}
	}

	@Override
	void r_set_filling_bp_rate() {
		rinsingParam.set(RinsingParam.FILLING_BP_VOLUME);
	}

	@Override
	void r_set_filling_bp_volume() {
		rinsingParam.set(RinsingParam.BP_RATE_RINSING);
	}

	@Override
	void r_set_bp_rate_rinsing() {
		rinsingParam.set(RinsingParam.DF_FLOW_RINSING);
	}

	@Override
	void r_set_df_flow_rinsing() {
		rinsingParam.set(RinsingParam.TIME_RINSING);
	}

	@Override
	void r_set_time_rinsing() {
		rinsingParam.set(RinsingParam.UF_RATE_RINSING);
	}

	@Override
	void r_set_uf_rate_rinsing() {
		rinsingParam.set(RinsingParam.UF_VOLUME_RINSING);
	}

	@Override
	void r_set_uf_volume_rinsing() {
		modePreparation.set(ModePreparation.TUBING_SYSTEM);
	}

	@Override
	void r_set_rinsing_param() {
		{ //par
			if ((rinsingParam.get() == RinsingParam.FILLING_BP_RATE)) {
				r_set_filling_bp_rate();
			}
			if ((rinsingParam.get() == RinsingParam.FILLING_BP_VOLUME)) {
				r_set_filling_bp_volume();
			}
			if ((rinsingParam.get() == RinsingParam.BP_RATE_RINSING)) {
				r_set_bp_rate_rinsing();
			}
			if ((rinsingParam.get() == RinsingParam.DF_FLOW_RINSING)) {
				r_set_df_flow_rinsing();
			}
			if ((rinsingParam.get() == RinsingParam.TIME_RINSING)) {
				r_set_time_rinsing();
			}
			if ((rinsingParam.get() == RinsingParam.UF_RATE_RINSING)) {
				r_set_uf_rate_rinsing();
			}
			if ((rinsingParam.get() == RinsingParam.UF_VOLUME_RINSING)) {
				r_set_uf_volume_rinsing();
			}
		} //endpar
	}

	@Override
	void r_connect_av_tubes() {
		if ((av_tubes_conn.get() == true)) {
			tubingPhase.set(TubingPhase.CONNECT_ALL_COMP);
		}
	}

	@Override
	void r_connect_all_comp() {
		if ((all_comp_conn.get() == true)) {
			tubingPhase.set(TubingPhase.SET_SALINE_LEVELS);
		}
	}

	@Override
	void r_set_saline_levels() {
		if ((saline_levels_set.get() == true)) {
			tubingPhase.set(TubingPhase.INSERT_BLOODLINES);
		}
	}

	@Override
	void r_insert_bloodlines() {
		if ((blood_line_insert.get() == true)) {
			tubingPhase.set(TubingPhase.PRIMING);
		}
	}

	@Override
	void r_priming() {
		if ((bp_status_der() == BPStatus.STOP)) {
			bp_status.set(BPStatus.START);
		} else if ((bp_fill_fluid.get() == true) && (bp_rate_rinsing_150.get() == true)) {
			{ //par
				bp_status.set(BPStatus.STOP);
				tubingPhase.set(TubingPhase.CONNECT_AV_ENDS);
			} //endpar
		}
	}

	@Override
	void r_connect_av_ends() {
		if ((av_ends_conn.get() == true)) {
			modePreparation.set(ModePreparation.PREPARE_HEPARIN);
		}
	}

	@Override
	void r_tubing_system() {
		{ //par
			if ((tubingPhase.get() == TubingPhase.CONNECT_AV_TUBES)) {
				r_connect_av_tubes();
			}
			if ((tubingPhase.get() == TubingPhase.CONNECT_ALL_COMP)) {
				r_connect_all_comp();
			}
			if ((tubingPhase.get() == TubingPhase.SET_SALINE_LEVELS)) {
				r_set_saline_levels();
			}
			if ((tubingPhase.get() == TubingPhase.INSERT_BLOODLINES)) {
				r_insert_bloodlines();
			}
			if ((tubingPhase.get() == TubingPhase.PRIMING)) {
				r_priming();
			}
			if ((tubingPhase.get() == TubingPhase.CONNECT_AV_ENDS)) {
				r_connect_av_ends();
			}
		} //endpar
	}

	@Override
	void r_prepare_heparin() {
		if ((heparin_prepared.get() == true)) {
			modePreparation.set(ModePreparation.SET_TREAT_PARAM);
		}
	}

	@Override
	void r_set_blood_conductivity() {
		treatmentParam.set(TreatmentParam.BIC_AC);
	}

	@Override
	void r_set_bic_ac() {
		treatmentParam.set(TreatmentParam.BIC_CONDUCTIVITY);
	}

	@Override
	void r_set_bic_conductivity() {
		treatmentParam.set(TreatmentParam.DF_TEMP);
	}

	@Override
	void r_set_df_temp() {
		treatmentParam.set(TreatmentParam.DF_FLOW);
	}

	@Override
	void r_set_df_flow() {
		treatmentParam.set(TreatmentParam.UF_VOLUME);
	}

	@Override
	void r_set_uf_volume() {
		treatmentParam.set(TreatmentParam.THERAPY_TIME);
	}

	@Override
	void r_set_therapy_time() {
		treatmentParam.set(TreatmentParam.MIN_UF_RATE);
	}

	@Override
	void r_set_min_uf_rate() {
		treatmentParam.set(TreatmentParam.MAX_UF_RATE);
	}

	@Override
	void r_set_max_uf_rate() {
		treatmentParam.set(TreatmentParam.MIN_AP);
	}

	@Override
	void r_set_min_ap() {
		treatmentParam.set(TreatmentParam.MAX_AP);
	}

	@Override
	void r_set_max_ap() {
		treatmentParam.set(TreatmentParam.MIN_VP);
	}

	@Override
	void r_set_min_vp() {
		treatmentParam.set(TreatmentParam.MAX_VP);
	}

	@Override
	void r_set_max_vp() {
		treatmentParam.set(TreatmentParam.DELTA_AP);
	}

	@Override
	void r_set_delta_ap() {
		treatmentParam.set(TreatmentParam.PERC_DELTA_TMP);
	}

	@Override
	void r_set_perc_delta_tmp() {
		treatmentParam.set(TreatmentParam.LIMITS_TMP);
	}

	@Override
	void r_set_limits_tmp() {
		treatmentParam.set(TreatmentParam.MAX_TMP);
	}

	@Override
	void r_set_max_tmp() {
		treatmentParam.set(TreatmentParam.EXTENDED_TMP);
	}

	@Override
	void r_set_extended_tmp() {
		treatmentParam.set(TreatmentParam.MAX_BEP);
	}

	@Override
	void r_set_max_bep() {
		treatmentParam.set(TreatmentParam.STOP_TIME_H);
	}

	@Override
	void r_set_h_stop_time() {
		treatmentParam.set(TreatmentParam.BOLUS_VOLUME_H);
	}

	@Override
	void r_set_h_bolus_volume() {
		treatmentParam.set(TreatmentParam.RATE_H);
	}

	@Override
	void r_set_h_rate() {
		treatmentParam.set(TreatmentParam.ACTIVATION_H);
	}

	@Override
	void r_set_h_activation() {
		r_insert_param(TreatmentParam.SYRINGE_TYPE, activation_h.get(), activation_h_contr.get());
	}

	@Override
	void r_set_syringe_type() {
		modePreparation.set(ModePreparation.RINSE_DIALYZER);
	}

	@Override
	void r_set_treatment_param() {
		{ //par
			if ((treatmentParam.get() == TreatmentParam.BLOOD_CONDUCTIVITY)) {
				r_set_blood_conductivity();
			}
			if ((treatmentParam.get() == TreatmentParam.BIC_AC)) {
				r_set_bic_ac();
			}
			if ((treatmentParam.get() == TreatmentParam.BIC_CONDUCTIVITY)) {
				r_set_bic_conductivity();
			}
			if ((treatmentParam.get() == TreatmentParam.DF_TEMP)) {
				r_set_df_temp();
			}
			if ((treatmentParam.get() == TreatmentParam.DF_FLOW)) {
				r_set_df_flow();
			}
			if ((treatmentParam.get() == TreatmentParam.UF_VOLUME)) {
				r_set_uf_volume();
			}
			if ((treatmentParam.get() == TreatmentParam.THERAPY_TIME)) {
				r_set_therapy_time();
			}
			if ((treatmentParam.get() == TreatmentParam.MIN_UF_RATE)) {
				r_set_min_uf_rate();
			}
			if ((treatmentParam.get() == TreatmentParam.MAX_UF_RATE)) {
				r_set_max_uf_rate();
			}
			if ((treatmentParam.get() == TreatmentParam.MIN_AP)) {
				r_set_min_ap();
			}
			if ((treatmentParam.get() == TreatmentParam.MAX_AP)) {
				r_set_max_ap();
			}
			if ((treatmentParam.get() == TreatmentParam.MIN_VP)) {
				r_set_min_vp();
			}
			if ((treatmentParam.get() == TreatmentParam.MAX_VP)) {
				r_set_max_vp();
			}
			if ((treatmentParam.get() == TreatmentParam.DELTA_AP)) {
				r_set_delta_ap();
			}
			if ((treatmentParam.get() == TreatmentParam.PERC_DELTA_TMP)) {
				r_set_perc_delta_tmp();
			}
			if ((treatmentParam.get() == TreatmentParam.LIMITS_TMP)) {
				r_set_limits_tmp();
			}
			if ((treatmentParam.get() == TreatmentParam.MAX_TMP)) {
				r_set_max_tmp();
			}
			if ((treatmentParam.get() == TreatmentParam.EXTENDED_TMP)) {
				r_set_extended_tmp();
			}
			if ((treatmentParam.get() == TreatmentParam.MAX_BEP)) {
				r_set_max_bep();
			}
			if ((treatmentParam.get() == TreatmentParam.STOP_TIME_H)) {
				r_set_h_stop_time();
			}
			if ((treatmentParam.get() == TreatmentParam.BOLUS_VOLUME_H)) {
				r_set_h_bolus_volume();
			}
			if ((treatmentParam.get() == TreatmentParam.RATE_H)) {
				r_set_h_rate();
			}
			if ((treatmentParam.get() == TreatmentParam.ACTIVATION_H)) {
				r_set_h_activation();
			}
			if ((treatmentParam.get() == TreatmentParam.SYRINGE_TYPE)) {
				r_set_syringe_type();
			}
		} //endpar
	}

	@Override
	void r_connect_dialyzer() {
		if ((dialyzer_connected.get() == true)) {
			rinsePhase.set(RinsePhase.FILL_ART_CHAMBER);
		}
	}

	@Override
	void r_fill_art_chamber() {
		if ((bp_status_der() == BPStatus.STOP)) {
			bp_status.set(BPStatus.START);
		} else if ((arterial_chamber_filled.get() == true)) {
			rinsePhase.set(RinsePhase.FILL_VEN_CHAMBER);
		}
	}

	@Override
	void r_fill_ven_chamber() {
		if ((venous_chamber_fill.get() == true)) {
			rinsePhase.set(RinsePhase.FILL_DIALYZER);
		}
	}

	@Override
	void r_fill_dialyzer() {
		if ((dialyzer_filled.get() == true)) {
			{ //par
				bp_status.set(BPStatus.STOP);
				phaseTherapy.set(PhasesTherapy.INITIATION);
			} //endpar
		}
	}

	@Override
	void r_rinse_dialyzer() {
		{ //par
			if ((rinsePhase.get() == RinsePhase.CONNECT_DIALYZER)) {
				r_connect_dialyzer();
			}
			if ((rinsePhase.get() == RinsePhase.FILL_ART_CHAMBER)) {
				r_fill_art_chamber();
			}
			if ((rinsePhase.get() == RinsePhase.FILL_VEN_CHAMBER)) {
				r_fill_ven_chamber();
			}
			if ((rinsePhase.get() == RinsePhase.FILL_DIALYZER)) {
				r_fill_dialyzer();
			}
		} //endpar
	}

	@Override
	void r_preparation() {
		{ //par
			if ((modePreparation.get() == ModePreparation.AUTO_TEST)) {
				r_auto_test();
			}
			if ((modePreparation.get() == ModePreparation.CONNECT_CONCENTRATE)) {
				r_connect_concentrate();
			}
			if ((modePreparation.get() == ModePreparation.SET_RINSING_PARAM)) {
				r_set_rinsing_param();
			}
			if ((modePreparation.get() == ModePreparation.TUBING_SYSTEM)) {
				r_tubing_system();
			}
			if ((modePreparation.get() == ModePreparation.PREPARE_HEPARIN)) {
				r_prepare_heparin();
			}
			if ((modePreparation.get() == ModePreparation.SET_TREAT_PARAM)) {
				r_set_treatment_param();
			}
			if ((modePreparation.get() == ModePreparation.RINSE_DIALYZER)) {
				r_rinse_dialyzer();
			}
		} //endpar
	}

	@Override
	void r_check_initiation_phase() {
		{ //par
			r_check_temp_high();
			r_check_temp_low();
			if ((bp_status_der() == BPStatus.START)) {
				{ //par
					r_check_BP_rotation_dir();
					r_check_bp_less_flow();
					r_check_bp_no_flow_err();
				} //endpar
			}
		} //endpar
	}

	@Override
	void r_conn_arterially() {
		if ((art_connected.get() == true)) {
			{ //par
				patientPhase.set(PatientPhase.START_BP);
				art_connected_contr.set(true);
			} //endpar
		}
	}

	@Override
	void r_start_bp() {
		if ((error_bp() == false)) {
			{ //par
				patientPhase.set(PatientPhase.BLOOD_FLOW);
				bp_status.set(BPStatus.START);
				ap_limits_set.set(false);
				vp_limits_set.set(false);
			} //endpar
		}
	}

	@Override
	void r_set_blood_flow() {
		if ((ven_connected_contr.get() == false)) {
			r_insert_param(PatientPhase.FILL_TUBING, blood_flow_conn_set.get());
		} else {
			r_insert_param(PatientPhase.END_CONN, blood_flow_conn_set.get());
		}
	}

	@Override
	void r_fill_tubing() {
		if ((error_bp() == false)) {
			if ((blood_on_VRD.get() == true) || (conn_infuse_set_volume.get() == true)) {
				{ //par
					patientPhase.set(PatientPhase.CONN_VEN);
					bp_status.set(BPStatus.STOP);
				} //endpar
			}
		}
	}

	@Override
	void r_conn_venously() {
		if ((ven_connected.get() == true)) {
			{ //par
				patientPhase.set(PatientPhase.START_BP);
				ven_connected_contr.set(true);
			} //endpar
		}
	}

	@Override
	void r_end_connection() {
		{ //par
			machine_state.set(MachineState.MAIN_FLOW);
			bicarbonate_status.set(true);
			signal_lamp.set(SignalLamps.GREEN);
			modeInitiation.set(ModeInitiation.THERAPY_RUNNING);
		} //endpar
	}

	@Override
	void r_patient_connection() {
		{ //par
			if ((patientPhase.get() == PatientPhase.CONN_ART)) {
				r_conn_arterially();
			}
			if ((patientPhase.get() == PatientPhase.START_BP)) {
				r_start_bp();
			}
			if ((patientPhase.get() == PatientPhase.BLOOD_FLOW)) {
				r_set_blood_flow();
			}
			if ((patientPhase.get() == PatientPhase.FILL_TUBING)) {
				r_fill_tubing();
			}
			if ((patientPhase.get() == PatientPhase.CONN_VEN)) {
				r_conn_venously();
			}
			if ((patientPhase.get() == PatientPhase.END_CONN)) {
				r_end_connection();
			}
		} //endpar
	}

	@Override
	void r_check_patient() {
		if ((bp_status_der() == BPStatus.START)) {
			{ //par
				r_check_conn_vp_up_limit();
				r_check_conn_vp_low_limit();
				r_check_conn_ap_low_limit();
				r_check_fill_blood_vol();
				r_check_SAD();
			} //endpar
		}
	}

	@Override
	void r_connect_patient() {
		if ((err_patient_conn() == false) && (error_bp() == false)) {
			{ //par
				r_patient_connection();
				r_check_patient();
			} //endpar
		}
	}

	@Override
	void r_start_heparin() {
		if ((activation_h_contr.get() == true)) {
			{ //par
				therapyPhase.set(TherapyPhase.THERAPY_EXEC);
				heparin_running.set(true);
			} //endpar
		}
	}

	@Override
	void r_run_heparin() {
		if ((heparin_running.get() == true)) {
			{ //par
				r_check_heparin();
				if ((passedHeparinTime.get() == true)) {
					heparin_running.set(false);
				}
			} //endpar
		}
	}

	@Override
	void r_set_ap_vp_limits() {
		{ //par
			if ((ap_limits_set.get() == false)) {
				ap_limits_set.set(true);
			}
			if ((vp_limits_set.get() == false)) {
				vp_limits_set.set(true);
			}
		} //endpar
	}

	@Override
	void r_ap_vp_limits() {
		if ((check_ap.get() == true)) {
			if ((ap_limits_set.get() == false) || (vp_limits_set.get() == false)) {
				if ((passed10Sec.get() == true)) {
					r_set_ap_vp_limits();
				}
			}
		}
	}

	@Override
	void r_treatment_min_UF() {
		if ((treatment_min_uf_rate_contr.get() == false)) {
			if ((treatment_min_uf_rate.get() == true)) {
				treatment_min_uf_rate_contr.set(true);
			}
		}
	}

	@Override
	void r_interrupt() {
		if ((interrupt_dialysis.get() == true)) {
			{ //par
				therapyPhase.set(TherapyPhase.THERAPY_END);
				machine_state.set(MachineState.BYPASS);
			} //endpar
		}
	}

	@Override
	void r_start_arterial_bolus() {
		if ((start_arterial_bolus.get() == true)) {
			arterialBolusPhase.set(ArterialBolusPhase.SET_ARTERIAL_BOLUS_VOLUME);
		}
	}

	@Override
	void r_set_arterial_bolus_volume() {
		if ((art_bolus_volume_set.get() == true)) {
			{ //par
				bp_status.set(BPStatus.STOP);
				arterialBolusPhase.set(ArterialBolusPhase.CONNECT_SOLUTION);
				check_ap.set(false);
			} //endpar
		}
	}

	@Override
	void r_connect_solution() {
		if ((saline_solution_conn.get() == true)) {
			{ //par
				arterialBolusPhase.set(ArterialBolusPhase.RUNNING_SOLUTION);
				bp_status.set(BPStatus.START);
				ap_limits_set.set(false);
				vp_limits_set.set(false);
				check_ap.set(true);
			} //endpar
		}
	}

	@Override
	void r_running_solution() {
		if ((alarm.get(ErrorAlarmType.ARTERIAL_BOLUS_END) == false)) {
			if ((current_art_bolus_volume.get().value == 0)) {
				{ //par
					arterialBolusPhase.set(ArterialBolusPhase.WAIT_SOLUTION);
					alarm.set(ErrorAlarmType.ARTERIAL_BOLUS_END, true);
				} //endpar
			}
		}
	}

	@Override
	void r_check_arterial_bolus() {
		if ((current_art_bolus_volume.get().value == 2)) {
			{ //par
				alarm.set(ErrorAlarmType.ARTERIAL_BOLUS, true);
				error.set(ErrorAlarmType.ARTERIAL_BOLUS, true);
			} //endpar
		}
	}

	@Override
	void r_run_arterial_bolus() {
		{ //par
			r_running_solution();
			r_check_arterial_bolus();
		} //endpar
	}

	@Override
	void r_arterial_bolus() {
		if ((error.get(ErrorAlarmType.ARTERIAL_BOLUS) == false)) {
			{ //par
				if ((arterialBolusPhase.get() == ArterialBolusPhase.WAIT_SOLUTION)) {
					r_start_arterial_bolus();
				}
				if ((arterialBolusPhase.get() == ArterialBolusPhase.SET_ARTERIAL_BOLUS_VOLUME)) {
					r_set_arterial_bolus_volume();
				}
				if ((arterialBolusPhase.get() == ArterialBolusPhase.CONNECT_SOLUTION)) {
					r_connect_solution();
				}
				if ((arterialBolusPhase.get() == ArterialBolusPhase.RUNNING_SOLUTION)) {
					r_run_arterial_bolus();
				}
			} //endpar
		}
	}

	@Override
	void r_therapy_end_time() {
		if ((passedTherapyTime.get() == true)) {
			therapyPhase.set(TherapyPhase.THERAPY_END);
		}
	}

	@Override
	void r_update_blood_flow() {
		if ((update_blood_flow.get() == true)) {
			;
		}
	}

	@Override
	void r_check_therapy_run() {
		{ //par
			if ((bp_status_der() == BPStatus.START)) {
				{ //par
					if ((ap_limits_set.get() == true) && (vp_limits_set.get() == true)) {
						{ //par
							r_check_init_vp_up_limit();
							r_check_init_ap_up_limit();
							r_check_init_vp_low_limit();
							r_check_init_ap_low_limit();
						} //endpar
					}
					r_check_SAD();
				} //endpar
			}
			if ((bicarbonate_status_der() == true)) {
				{ //par
					r_check_UF_rate();
					r_check_UF_dir();
					r_check_UF_volume();
					r_check_UF_bypass();
					r_check_df_prep();
				} //endpar
			}
		} //endpar
	}

	@Override
	void r_therapy_exec() {
		{ //par
			if ((error_therapy() == false)) {
				{ //par
					r_run_heparin();
					r_ap_vp_limits();
					r_treatment_min_UF();
					if ((error_bp() == false)) {
						r_arterial_bolus();
					}
					r_therapy_end_time();
					r_update_blood_flow();
					r_check_therapy_run();
				} //endpar
			}
			r_interrupt();
		} //endpar
	}

	@Override
	void r_therapy_end() {
		phaseTherapy.set(PhasesTherapy.ENDING);
	}

	@Override
	void r_running_therapy() {
		{ //par
			if ((therapyPhase.get() == TherapyPhase.START_HEPARIN)) {
				r_start_heparin();
			}
			if ((therapyPhase.get() == TherapyPhase.THERAPY_EXEC)) {
				r_therapy_exec();
			}
			if ((therapyPhase.get() == TherapyPhase.THERAPY_END)) {
				r_therapy_end();
			}
		} //endpar
	}

	@Override
	void r_initiation() {
		{ //par
			r_check_initiation_phase();
			if ((modeInitiation.get() == ModeInitiation.CONNECT_PATIENT)) {
				r_connect_patient();
			}
			if ((modeInitiation.get() == ModeInitiation.THERAPY_RUNNING)) {
				r_running_therapy();
			}
		} //endpar
	}

	@Override
	void r_remove_arterially() {
		if ((art_removed.get() == true)) {
			{ //par
				reinfusionPhase.set(ReinfusionPhase.CONN_SALINE);
				art_connected_contr.set(false);
			} //endpar
		}
	}

	@Override
	void r_connect_saline() {
		if ((saline_conn.get() == true)) {
			reinfusionPhase.set(ReinfusionPhase.START_SALINE_INF);
		}
	}

	@Override
	void r_start_saline_inf() {
		{ //par
			bp_status.set(BPStatus.START);
			reinfusionPhase.set(ReinfusionPhase.RUN_SALINE_INF);
		} //endpar
	}

	@Override
	void r_run_saline_inf() {
		if ((saline_on_VRD.get() == true)) {
			{ //par
				reinfusionPhase.set(ReinfusionPhase.CHOOSE_NEXT_REINF_STEP);
				bp_status.set(BPStatus.STOP);
			} //endpar
		}
	}

	@Override
	void r_choose_next_reinf_step() {
		if ((new_saline_reinfusion.get() == true)) {
			reinfusionPhase.set(ReinfusionPhase.START_SALINE_REIN);
		} else {
			reinfusionPhase.set(ReinfusionPhase.REMOVE_VEN);
		}
	}

	@Override
	void r_start_saline_reinf() {
		{ //par
			bp_status.set(BPStatus.START);
			reinfusionPhase.set(ReinfusionPhase.RUN_SALINE_REIN);
		} //endpar
	}

	@Override
	void r_run_saline_reinf() {
		if ((volume_saline_inf_400.get() == true) || (passed5Min.get() == true)) {
			{ //par
				reinfusionPhase.set(ReinfusionPhase.CHOOSE_NEXT_REINF_STEP);
				bp_status.set(BPStatus.STOP);
			} //endpar
		}
	}

	@Override
	void r_remove_venously() {
		if ((ven_removed.get() == true)) {
			{ //par
				modeEnding.set(ModeEnding.DRAIN_DIALYZER);
				ven_connected_contr.set(false);
			} //endpar
		}
	}

	@Override
	void r_run_reinfusion() {
		{ //par
			if ((reinfusionPhase.get() == ReinfusionPhase.REMOVE_ART)) {
				r_remove_arterially();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.CONN_SALINE)) {
				r_connect_saline();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.START_SALINE_INF)) {
				r_start_saline_inf();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.CHOOSE_NEXT_REINF_STEP)) {
				r_choose_next_reinf_step();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.RUN_SALINE_INF)) {
				r_run_saline_inf();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.START_SALINE_REIN)) {
				r_start_saline_reinf();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.RUN_SALINE_REIN)) {
				r_run_saline_reinf();
			}
			if ((reinfusionPhase.get() == ReinfusionPhase.REMOVE_VEN)) {
				r_remove_venously();
			}
		} //endpar
	}

	@Override
	void r_check_reinfusion() {
		if ((bp_status_der() == BPStatus.START)) {
			{ //par
				r_check_rein_vp_up_limit();
				r_check_rein_ap_low_limit();
				r_check_SAD();
			} //endpar
		}
	}

	@Override
	void r_reinfusion() {
		if ((error_rein_press() == false)) {
			{ //par
				r_run_reinfusion();
				r_check_reinfusion();
			} //endpar
		}
	}

	@Override
	void r_drain_dialyzer() {
		if ((dialyzer_drained.get() == true)) {
			{ //par
				modeEnding.set(ModeEnding.EMPTY_CARTRIDGE);
				empty_dialyzer.set(true);
			} //endpar
		}
	}

	@Override
	void r_empty_cartridge() {
		if ((cartridge_emtpy.get() == true)) {
			modeEnding.set(ModeEnding.THERAPY_OVERVIEW);
		}
	}

	@Override
	void r_therapy_overview() {
		;
	}

	@Override
	void r_ending() {
		{ //par
			if ((modeEnding.get() == ModeEnding.REINFUSION)) {
				r_reinfusion();
			}
			if ((modeEnding.get() == ModeEnding.DRAIN_DIALYZER)) {
				r_drain_dialyzer();
			}
			if ((modeEnding.get() == ModeEnding.EMPTY_CARTRIDGE)) {
				r_empty_cartridge();
			}
			if ((modeEnding.get() == ModeEnding.THERAPY_OVERVIEW)) {
				r_therapy_overview();
			}
		} //endpar
	}

	@Override
	void r_run_therapy() {
		{ //par
			if ((phaseTherapy.get() == PhasesTherapy.PREPARATION)) {
				r_preparation();
			}
			if ((phaseTherapy.get() == PhasesTherapy.INITIATION)) {
				r_initiation();
			}
			if ((phaseTherapy.get() == PhasesTherapy.ENDING)) {
				r_ending();
			}
		} //endpar
	}

	@Override
	void r_Main() {
		{ //par
			r_run_therapy();
			if ((errorExist() == true)) {
				r_run_error();
			}
			if ((alarmExist() == true)) {
				r_run_alarm();
			}
		} //endpar
	}

	// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
	void initControlledWithMonitored() {
	}

	// applicazione dell'aggiornamento del set
	void fireUpdateSet() {

		phaseTherapy.oldValue = phaseTherapy.newValue;
		modePreparation.oldValue = modePreparation.newValue;
		modeInitiation.oldValue = modeInitiation.newValue;
		modeEnding.oldValue = modeEnding.newValue;
		rinsingParam.oldValue = rinsingParam.newValue;
		tubingPhase.oldValue = tubingPhase.newValue;
		treatmentParam.oldValue = treatmentParam.newValue;
		rinsePhase.oldValue = rinsePhase.newValue;
		patientPhase.oldValue = patientPhase.newValue;
		therapyPhase.oldValue = therapyPhase.newValue;
		reinfusionPhase.oldValue = reinfusionPhase.newValue;
		machine_state.oldValue = machine_state.newValue;
		signal_lamp.oldValue = signal_lamp.newValue;
		arterialBolusPhase.oldValue = arterialBolusPhase.newValue;
		bp_status.oldValue = bp_status.newValue;
		activation_h_contr.oldValue = activation_h_contr.newValue;
		art_connected_contr.oldValue = art_connected_contr.newValue;
		ven_connected_contr.oldValue = ven_connected_contr.newValue;
		bicarbonate_status.oldValue = bicarbonate_status.newValue;
		heparin_running.oldValue = heparin_running.newValue;
		ap_limits_set.oldValue = ap_limits_set.newValue;
		vp_limits_set.oldValue = vp_limits_set.newValue;
		treatment_min_uf_rate_contr.oldValue = treatment_min_uf_rate_contr.newValue;
		empty_dialyzer.oldValue = empty_dialyzer.newValue;
		error.oldValues = error.newValues;
		alarm.oldValues = alarm.newValues;
		bf_err_vp_low.oldValue = bf_err_vp_low.newValue;
		reset_err_pres_vp_low.oldValue = reset_err_pres_vp_low.newValue;
		bf_err_vp_up.oldValue = bf_err_vp_up.newValue;
		reset_err_pres_vp_up.oldValue = reset_err_pres_vp_up.newValue;
		bf_err_ap_up.oldValue = bf_err_ap_up.newValue;
		reset_err_pres_ap_up.oldValue = reset_err_pres_ap_up.newValue;
		bf_err_ap_low.oldValue = bf_err_ap_low.newValue;
		reset_err_pres_ap_low.oldValue = reset_err_pres_ap_low.newValue;
		bf_err_vp_low_conn.oldValue = bf_err_vp_low_conn.newValue;
		reset_err_pres_vp_low_conn.oldValue = reset_err_pres_vp_low_conn.newValue;
		bf_err_ap_low_conn.oldValue = bf_err_ap_low_conn.newValue;
		reset_err_pres_ap_low_conn.oldValue = reset_err_pres_ap_low_conn.newValue;
		check_ap.oldValue = check_ap.newValue;
	}

	//Metodo per l'aggiornamento dell'asm
	void UpdateASM()
	{
		r_Main();
		fireUpdateSet();
		initControlledWithMonitored();
	}

public static void main(String[] args) {
	}

}


