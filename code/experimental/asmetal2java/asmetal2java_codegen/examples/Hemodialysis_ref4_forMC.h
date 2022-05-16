// Hemodialysis_ref4_forMC.h automatically generated from ASMETA2CODE
#ifndef HEMODIALYSIS_REF4_FORMC_H
#define HEMODIALYSIS_REF4_FORMC_H

//Arduino.h uses WString instead...
#include<string>
typedef std::string String;
#include<iostream> 
#include<vector> 
using namespace std;
#include <set>
#include <map>
#include <list>
#include <bits/stl_tree.h>
#include <boost/tuple/tuple.hpp>
#define ANY String

/////////////////////////////////////////////////
/// DOMAIN DEFINITIONS
/////////////////////////////////////////////////
/* Domain definitions here */
namespace Hemodialysis_ref4_forMCnamespace {
enum PhasesTherapy {
	PREPARATION, INITIATION, ENDING
};

enum ModePreparation {
	RINSE_DIALYZER,
	SET_TREAT_PARAM,
	PREPARE_HEPARIN,
	TUBING_SYSTEM,
	SET_RINSING_PARAM,
	CONNECT_CONCENTRATE,
	AUTO_TEST
};

enum ModeInitiation {
	CONNECT_PATIENT, THERAPY_RUNNING
};

enum ModeEnding {
	REINFUSION, THERAPY_OVERVIEW, EMPTY_CARTRIDGE, DRAIN_DIALYZER
};

enum RinsingParam {
	FILLING_BP_RATE,
	FILLING_BP_VOLUME,
	BP_RATE_RINSING,
	DF_FLOW_RINSING,
	TIME_RINSING,
	UF_RATE_RINSING,
	UF_VOLUME_RINSING
};

enum TubingPhase {
	CONNECT_AV_TUBES,
	CONNECT_ALL_COMP,
	SET_SALINE_LEVELS,
	INSERT_BLOODLINES,
	PRIMING,
	CONNECT_AV_ENDS
};

enum TreatmentParam {
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
};

enum RinsePhase {
	CONNECT_DIALYZER, FILL_ART_CHAMBER, FILL_VEN_CHAMBER, FILL_DIALYZER
};

enum PatientPhase {
	CONN_ART, START_BP, BLOOD_FLOW, FILL_TUBING, CONN_VEN, END_CONN
};

enum TherapyPhase {
	START_HEPARIN, THERAPY_EXEC, THERAPY_END
};

enum ReinfusionPhase {
	REMOVE_ART,
	CONN_SALINE,
	START_SALINE_INF,
	RUN_SALINE_INF,
	CHOOSE_NEXT_REINF_STEP,
	START_SALINE_REIN,
	RUN_SALINE_REIN,
	REMOVE_VEN
};

enum BPStatus {
	START, STOP
};

enum SignalLamps {
	GREEN, YELLOW
};

enum MachineState {
	BYPASS, MAIN_FLOW
};

enum ArterialBolusPhase {
	WAIT_SOLUTION, SET_ARTERIAL_BOLUS_VOLUME, CONNECT_SOLUTION, RUNNING_SOLUTION
};

enum ErrorAlarmType {
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
};

typedef int SADLimit;

typedef int LMHlimit;

typedef int Airlimit;

}

using namespace Hemodialysis_ref4_forMCnamespace;

class Hemodialysis_ref4_forMC {

	/////////////////////////////////////////////////
	/// DOMAIN CONTAINERS
	/////////////////////////////////////////////////
	/* Domain containers here */
	const set<PhasesTherapy> PhasesTherapy_elems;

	const set<ModePreparation> ModePreparation_elems;

	const set<ModeInitiation> ModeInitiation_elems;

	const set<ModeEnding> ModeEnding_elems;

	const set<RinsingParam> RinsingParam_elems;

	const set<TubingPhase> TubingPhase_elems;

	const set<TreatmentParam> TreatmentParam_elems;

	const set<RinsePhase> RinsePhase_elems;

	const set<PatientPhase> PatientPhase_elems;

	const set<TherapyPhase> TherapyPhase_elems;

	const set<ReinfusionPhase> ReinfusionPhase_elems;

	const set<BPStatus> BPStatus_elems;

	const set<SignalLamps> SignalLamps_elems;

	const set<MachineState> MachineState_elems;

	const set<ArterialBolusPhase> ArterialBolusPhase_elems;

	const set<ErrorAlarmType> ErrorAlarmType_elems;

	const set<SADLimit> SADLimit_elems;

	const set<LMHlimit> LMHlimit_elems;

	const set<Airlimit> Airlimit_elems;

public:
	/////////////////////////////////////////////////
	/// FUNCTIONS
	/////////////////////////////////////////////////
	PhasesTherapy phaseTherapy[2];
	ModePreparation modePreparation[2];
	ModeInitiation modeInitiation[2];
	ModeEnding modeEnding[2];
	RinsingParam rinsingParam[2];
	TubingPhase tubingPhase[2];
	TreatmentParam treatmentParam[2];
	RinsePhase rinsePhase[2];
	PatientPhase patientPhase[2];
	TherapyPhase therapyPhase[2];
	ReinfusionPhase reinfusionPhase[2];
	MachineState machine_state[2];
	SignalLamps signal_lamp[2];
	ArterialBolusPhase arterialBolusPhase[2];
	bool auto_test_end;
	bool conn_concentrate;
	bool heparin_prepared;
	bool dialyzer_drained;
	bool cartridge_emtpy;
	bool passedHeparinTime;
	bool passed5Min;
	bool passedTherapyTime;
	bool passed120Sec;
	bool passed10Sec;
	bool passed3Sec;
	bool passed1Sec;
	bool passed1Msec;
	bool bp_rate_rinsing_150;
	bool av_tubes_conn;
	bool all_comp_conn;
	bool saline_levels_set;
	bool blood_line_insert;
	BPStatus bp_status[2];
	bool bp_fill_fluid;
	bool av_ends_conn;
	bool activation_h;
	bool activation_h_contr[2];
	bool dialyzer_connected;
	bool arterial_chamber_filled;
	bool venous_chamber_fill;
	bool dialyzer_filled;
	bool art_connected;
	bool art_connected_contr[2];
	bool blood_flow_conn_set;
	bool blood_flow_conn_reset;
	bool param_press_reset;
	bool blood_on_VRD;
	bool conn_infuse_set_volume;
	bool ven_connected;
	bool ven_connected_contr[2];
	bool bicarbonate_status[2];
	bool heparin_running[2];
	bool ap_limits_set[2];
	bool vp_limits_set[2];
	bool treatment_min_uf_rate;
	bool treatment_min_uf_rate_contr[2];
	bool interrupt_dialysis;
	bool start_arterial_bolus;
	bool saline_solution_conn;
	bool art_bolus_volume_set;
	bool art_removed;
	bool saline_conn;
	bool saline_on_VRD;
	bool new_saline_reinfusion;
	bool empty_dialyzer[2];
	bool volume_saline_inf_400;
	bool ven_removed;
	bool errorExist();

	bool alarmExist();

	bool err_patient_conn();

	bool error_bp();

	bool error_rein_press();

	bool error_therapy();

	map<ErrorAlarmType, bool> error[2];
	map<ErrorAlarmType, bool> alarm[2];
	bool reset_alarm;
	LMHlimit current_art_bolus_volume;
	bool bf_err_vp_low[2];
	bool reset_err_pres_vp_low[2];
	bool bf_err_vp_up[2];
	bool reset_err_pres_vp_up[2];
	bool bf_err_ap_up[2];
	bool reset_err_pres_ap_up[2];
	bool bf_err_ap_low[2];
	bool reset_err_pres_ap_low[2];
	bool bf_err_vp_low_conn[2];
	bool reset_err_pres_vp_low_conn[2];
	bool bf_err_ap_low_conn[2];
	bool reset_err_pres_ap_low_conn[2];
	bool update_blood_flow;
	LMHlimit current_temp;
	bool current_bp_flow_less_70;
	LMHlimit current_vp;
	LMHlimit current_ap;
	bool fill_blood_vol_passed_up_limit;
	Airlimit current_air_vol;
	SADLimit currentSAD;
	bool uf_rate_passed_max_uf_rate;
	bool uf_volume_passed_max_uf_volume;
	bool detected_blood_flow;
	bool bp_rotates_back;
	bool detect_bicarbonate;
	bool reverse_dir_heparin;
	bool uf_dir_backwards;
	bool check_ap[2];
	MachineState machine_status_der();

	bool errorePerBYPASS();

	BPStatus bp_status_der();

	bool errorePERbpStatus();

	bool bicarbonate_status_der();

	bool errorePERbicarbonate();

	////////////////////////////////////////////////
	/// RULE DEFINITION
	/////////////////////////////////////////////////
	/* Rule definition here */
	void r_auto_test();
	void r_connect_concentrate();
	void r_insert_param(int _inf, int _sup, ModePreparation _nextparam,
			int _mon, int _contr);
	void r_insert_param_press(int _inf, int _sup, int _monmin, int _contrmin,
			int _monmax, int _contrmax, bool _succ);
	void r_insert_param(int _inf, int _sup, TreatmentParam _nextparam, int _mon,
			int _contr);
	void r_insert_param(int _inf, int _sup, PatientPhase _nextparam, int _mon,
			int _contr);
	void r_insert_param(TreatmentParam _nextparam, bool _mon, bool _contr);
	void r_insert_param(PatientPhase _phase, bool _setvalue);
	void r_check_temp_high();
	void r_check_temp_low();
	void r_check_BP_rotation_dir();
	void r_check_bp_less_flow();
	void r_check_bp_no_flow_err();
	void r_check_init_press_limit(ErrorAlarmType _err, LMHlimit _limit,
			bool _errbf, bool _errpress);
	void r_check_init_vp_up_limit();
	void r_check_init_ap_up_limit();
	void r_check_init_vp_low_limit();
	void r_check_init_ap_low_limit();
	void r_check_conn_rein_press_limit(ErrorAlarmType _err, LMHlimit _limit,
			bool _passedT);
	void r_check_conn_rein_press_limit(ErrorAlarmType _err, LMHlimit _limit,
			bool _passedT, bool _errbf, bool _errpress);
	void r_check_conn_vp_up_limit();
	void r_check_conn_vp_low_limit();
	void r_check_conn_ap_low_limit();
	void r_check_rein_vp_up_limit();
	void r_check_rein_ap_low_limit();
	void r_check_fill_blood_vol();
	void r_check_df_prep();
	void r_check_heparin();
	void r_set_err_SAD();
	void r_check_SAD();
	void r_check_UF_rate();
	void r_check_UF_dir();
	void r_check_UF_volume();
	void r_check_UF_bypass();
	void r_error_temp_high();
	void r_error_temp_low();
	void r_error_arterial_bolus();
	void r_error_BP_no_flow();
	void r_error_BP_less_flow();
	void r_error_BP_rotation_dir();
	void r_error_pressure_limit(ErrorAlarmType _err, bool _bfErr, bool _resetP);
	void r_error_vp_up_limit();
	void r_error_vp_low_limit();
	void r_error_ap_up_limit();
	void r_error_ap_low_limit();
	void r_error_update_blood_flow(ErrorAlarmType _err);
	void r_error_conn_vp_up_limit();
	void r_error_conn_vp_low_limit();
	void r_error_conn_ap_low_limit();
	void r_error_rein_vp_up_limit();
	void r_error_rein_ap_low_limit();
	void r_error_fill_blood_vol();
	void r_error_df_prep();
	void r_error_heparin();
	void r_error_SAD();
	void r_error_UF_rate();
	void r_error_UF_dir();
	void r_run_error();
	void r_run_alarm();
	void r_set_filling_bp_rate();
	void r_set_filling_bp_volume();
	void r_set_bp_rate_rinsing();
	void r_set_df_flow_rinsing();
	void r_set_time_rinsing();
	void r_set_uf_rate_rinsing();
	void r_set_uf_volume_rinsing();
	void r_set_rinsing_param();
	void r_connect_av_tubes();
	void r_connect_all_comp();
	void r_set_saline_levels();
	void r_insert_bloodlines();
	void r_priming();
	void r_connect_av_ends();
	void r_tubing_system();
	void r_prepare_heparin();
	void r_set_blood_conductivity();
	void r_set_bic_ac();
	void r_set_bic_conductivity();
	void r_set_df_temp();
	void r_set_df_flow();
	void r_set_uf_volume();
	void r_set_therapy_time();
	void r_set_min_uf_rate();
	void r_set_max_uf_rate();
	void r_set_min_ap();
	void r_set_max_ap();
	void r_set_min_vp();
	void r_set_max_vp();
	void r_set_delta_ap();
	void r_set_perc_delta_tmp();
	void r_set_limits_tmp();
	void r_set_max_tmp();
	void r_set_extended_tmp();
	void r_set_max_bep();
	void r_set_h_stop_time();
	void r_set_h_bolus_volume();
	void r_set_h_rate();
	void r_set_h_activation();
	void r_set_syringe_type();
	void r_set_treatment_param();
	void r_connect_dialyzer();
	void r_fill_art_chamber();
	void r_fill_ven_chamber();
	void r_fill_dialyzer();
	void r_rinse_dialyzer();
	void r_preparation();
	void r_check_initiation_phase();
	void r_conn_arterially();
	void r_start_bp();
	void r_set_blood_flow();
	void r_fill_tubing();
	void r_conn_venously();
	void r_end_connection();
	void r_patient_connection();
	void r_check_patient();
	void r_connect_patient();
	void r_start_heparin();
	void r_run_heparin();
	void r_set_ap_vp_limits();
	void r_ap_vp_limits();
	void r_treatment_min_UF();
	void r_interrupt();
	void r_start_arterial_bolus();
	void r_set_arterial_bolus_volume();
	void r_connect_solution();
	void r_running_solution();
	void r_check_arterial_bolus();
	void r_run_arterial_bolus();
	void r_arterial_bolus();
	void r_therapy_end_time();
	void r_update_blood_flow();
	void r_check_therapy_run();
	void r_therapy_exec();
	void r_therapy_end();
	void r_running_therapy();
	void r_initiation();
	void r_remove_arterially();
	void r_connect_saline();
	void r_start_saline_inf();
	void r_run_saline_inf();
	void r_choose_next_reinf_step();
	void r_start_saline_reinf();
	void r_run_saline_reinf();
	void r_remove_venously();
	void r_run_reinfusion();
	void r_check_reinfusion();
	void r_reinfusion();
	void r_drain_dialyzer();
	void r_empty_cartridge();
	void r_therapy_overview();
	void r_ending();
	void r_run_therapy();
	void r_Main();

	Hemodialysis_ref4_forMC();

	void initControlledWithMonitored();

	void getInputs();

	void setOutputs();

	void fireUpdateSet();

};
#endif

