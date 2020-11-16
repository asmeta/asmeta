// Hemodialysis_ref4_forMC.cpp automatically generated from ASM2CODE

#include "Hemodialysis_ref4_forMC.h"
using namespace Hemodialysis_ref4_forMCnamespace;

// Conversion of ASM rules in C++ methods
void Hemodialysis_ref4_forMC::r_auto_test() {
	if ((auto_test_end == true)) {
		modePreparation[1] = CONNECT_CONCENTRATE;
	}
}
void Hemodialysis_ref4_forMC::r_connect_concentrate() {
	if ((conn_concentrate == true)) {
		modePreparation[1] = SET_RINSING_PARAM;
	}
}
void Hemodialysis_ref4_forMC::r_insert_param(int _inf, int _sup,
		ModePreparation _nextparam, int _mon, int _contr) {
	if ((_mon >= _inf) & (_mon <= _sup)) {
		{ //par
			_contr = _mon;
			modePreparation[1] = _nextparam;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_insert_param_press(int _inf, int _sup,
		int _monmin, int _contrmin, int _monmax, int _contrmax, bool _succ) {
	if ((_monmin >= _inf) & (_monmin <= _sup) & (_monmax >= _inf)
			& (_monmax <= _sup)) {
		{ //par
			_contrmin = _monmin;
			_contrmax = _monmax;
			_succ = false;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_insert_param(int _inf, int _sup,
		TreatmentParam _nextparam, int _mon, int _contr) {
	if ((_mon >= _inf) & (_mon <= _sup)) {
		{ //par
			_contr = _mon;
			treatmentParam[1] = _nextparam;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_insert_param(int _inf, int _sup,
		PatientPhase _nextparam, int _mon, int _contr) {
	if ((_mon >= _inf) & (_mon <= _sup)) {
		{ //par
			_contr = _mon;
			patientPhase[1] = _nextparam;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_insert_param(TreatmentParam _nextparam,
		bool _mon, bool _contr) {
	{ //par
		_contr = _mon;
		treatmentParam[1] = _nextparam;
	} //endpar
}
void Hemodialysis_ref4_forMC::r_insert_param(PatientPhase _phase,
		bool _setvalue) {
	if ((_setvalue == true)) {
		patientPhase[1] = _phase;
	}
}
void Hemodialysis_ref4_forMC::r_check_temp_high() {
	if ((error[0][TEMP_HIGH] == false)) {
		if ((current_temp == 2)) {
			{ //par
				error[1][TEMP_HIGH] = true;
				alarm[1][TEMP_HIGH] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_temp_low() {
	if ((error[0][TEMP_LOW] == false)) {
		if ((current_temp == 0)) {
			{ //par
				error[1][TEMP_LOW] = true;
				alarm[1][TEMP_LOW] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_BP_rotation_dir() {
	if ((error[0][BP_ROTATION_DIR] == false)) {
		if ((bp_rotates_back == true)) {
			{ //par
				error[1][BP_ROTATION_DIR] = true;
				alarm[1][BP_ROTATION_DIR] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_bp_less_flow() {
	if ((error[0][BP_LESS_FLOW] == false)) {
		if ((machine_status_der() == MAIN_FLOW)) {
			if ((current_bp_flow_less_70 == true)) {
				{ //par
					alarm[1][BP_LESS_FLOW] = true;
					error[1][BP_LESS_FLOW] = true;
				} //endpar
			}
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_bp_no_flow_err() {
	if ((error[0][BP_NO_FLOW] == false)) {
		if ((detected_blood_flow == false) & (passed120Sec == true)) {
			{ //par
				error[1][BP_NO_FLOW] = true;
				alarm[1][BP_NO_FLOW] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_init_press_limit(ErrorAlarmType _err,
		LMHlimit _limit, bool _errbf, bool _errpress) {
	if ((error[0][_err] == false)) {
		if ((_limit == 0) | (_limit == 2)) {
			{ //par
				error[1][_err] = true;
				alarm[1][_err] = true;
				_errbf = true;
				_errpress = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_init_vp_up_limit() {
	r_check_init_press_limit(INIT_VP_UP, current_vp, bf_err_vp_up[0],
			reset_err_pres_vp_up[0]);
}
void Hemodialysis_ref4_forMC::r_check_init_ap_up_limit() {
	r_check_init_press_limit(INIT_AP_UP, current_ap, bf_err_ap_up[0],
			reset_err_pres_ap_up[0]);
}
void Hemodialysis_ref4_forMC::r_check_init_vp_low_limit() {
	r_check_init_press_limit(INIT_VP_LOW, current_vp, bf_err_vp_low[0],
			reset_err_pres_vp_low[0]);
}
void Hemodialysis_ref4_forMC::r_check_init_ap_low_limit() {
	r_check_init_press_limit(INIT_AP_LOW, current_ap, bf_err_ap_low[0],
			reset_err_pres_ap_low[0]);
}
void Hemodialysis_ref4_forMC::r_check_conn_rein_press_limit(ErrorAlarmType _err,
		LMHlimit _limit, bool _passedT) {
	if ((error[0][_err] == false)) {
		if ((_limit == 0) | (_limit == 2) & (_passedT == true)) {
			{ //par
				error[1][_err] = true;
				alarm[1][_err] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_conn_rein_press_limit(ErrorAlarmType _err,
		LMHlimit _limit, bool _passedT, bool _errbf, bool _errpress) {
	if ((error[0][_err] == false)) {
		if ((_limit == 0) | (_limit == 2) & (_passedT == true)) {
			{ //par
				error[1][_err] = true;
				alarm[1][_err] = true;
				_errbf = true;
				_errpress = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_conn_vp_up_limit() {
	r_check_conn_rein_press_limit(CONN_VP_UP, current_vp, passed3Sec);
}
void Hemodialysis_ref4_forMC::r_check_conn_vp_low_limit() {
	r_check_conn_rein_press_limit(CONN_VP_LOW, current_vp, passed3Sec,
			bf_err_vp_low_conn[0], reset_err_pres_vp_low_conn[0]);
}
void Hemodialysis_ref4_forMC::r_check_conn_ap_low_limit() {
	r_check_conn_rein_press_limit(CONN_AP_LOW, current_ap, passed1Sec,
			bf_err_ap_low_conn[0], reset_err_pres_ap_low_conn[0]);
}
void Hemodialysis_ref4_forMC::r_check_rein_vp_up_limit() {
	r_check_conn_rein_press_limit(REIN_VP_UP, current_vp, passed3Sec);
}
void Hemodialysis_ref4_forMC::r_check_rein_ap_low_limit() {
	r_check_conn_rein_press_limit(REIN_AP_LOW, current_ap, passed1Sec);
}
void Hemodialysis_ref4_forMC::r_check_fill_blood_vol() {
	if ((error[0][FILL_BLOOD_VOL] == false)) {
		if ((fill_blood_vol_passed_up_limit == true)) {
			{ //par
				error[1][FILL_BLOOD_VOL] = true;
				alarm[1][FILL_BLOOD_VOL] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_df_prep() {
	if ((error[0][DF_PREP] == false)) {
		if ((detect_bicarbonate == false)) {
			{ //par
				error[1][DF_PREP] = true;
				alarm[1][DF_PREP] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_heparin() {
	if ((error[0][HEPARIN_DIR] == false)) {
		if ((reverse_dir_heparin == true)) {
			{ //par
				error[1][HEPARIN_DIR] = true;
				alarm[1][HEPARIN_DIR] = true;
				heparin_running[1] = false;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_set_err_SAD() {
	{ //par
		error[1][SAD_ERR] = true;
		alarm[1][SAD_ERR] = true;
	} //endpar
}
void Hemodialysis_ref4_forMC::r_check_SAD() {
	if ((error[0][SAD_ERR] == false)) {
		if ((passed1Msec == true)) {
			{ //par
				if ((currentSAD == 4)) {
					r_set_err_SAD();
				}
				if ((currentSAD == 1)) {
					if ((current_air_vol > 0)) {
						r_set_err_SAD();
					}
				}
				if ((currentSAD == 2)) {
					if ((current_air_vol > 1)) {
						r_set_err_SAD();
					}
				}
				if ((currentSAD == 3)) {
					if ((current_air_vol > 2)) {
						r_set_err_SAD();
					}
				}
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_UF_rate() {
	if ((error[0][UF_RATE] == false)) {
		if ((uf_rate_passed_max_uf_rate == true)) {
			{ //par
				error[1][UF_RATE] = true;
				alarm[1][UF_RATE] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_UF_dir() {
	if ((error[0][UF_DIR] == false)) {
		if ((uf_dir_backwards == true)) {
			{ //par
				error[1][UF_DIR] = true;
				alarm[1][UF_DIR] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_UF_volume() {
	if ((alarm[0][UF_VOLUME_ERR] == false)) {
		if ((uf_volume_passed_max_uf_volume == true)) {
			alarm[1][UF_VOLUME_ERR] = true;
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_UF_bypass() {
	if ((alarm[0][UF_BYPASS] == false)) {
		if ((machine_status_der() == BYPASS)) {
			alarm[1][UF_BYPASS] = true;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_temp_high() {
	if ((error[0][TEMP_HIGH] == true) & (alarm[0][TEMP_HIGH] == false)) {
		if ((current_temp == 1)) {
			error[1][TEMP_HIGH] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_temp_low() {
	if ((error[0][TEMP_LOW] == true) & (alarm[0][TEMP_LOW] == false)) {
		if ((current_temp == 1)) {
			error[1][TEMP_LOW] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_arterial_bolus() {
	if ((error[0][ARTERIAL_BOLUS] == true)
			& (alarm[0][ARTERIAL_BOLUS] == false)) {
		if ((current_art_bolus_volume == 1)) {
			{ //par
				error[1][ARTERIAL_BOLUS] = false;
				arterialBolusPhase[1] = WAIT_SOLUTION;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_BP_no_flow() {
	if ((error[0][BP_NO_FLOW] == true) & (alarm[0][BP_NO_FLOW] == false)) {
		if ((detected_blood_flow == true)) {
			error[1][BP_NO_FLOW] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_BP_less_flow() {
	if ((error[0][BP_LESS_FLOW] == true) & (alarm[0][BP_LESS_FLOW] == false)) {
		if ((current_bp_flow_less_70 == false)) {
			error[1][BP_LESS_FLOW] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_BP_rotation_dir() {
	if ((error[0][BP_ROTATION_DIR] == true)
			& (alarm[0][BP_ROTATION_DIR] == false)) {
		if ((bp_rotates_back == false)) {
			error[1][BP_ROTATION_DIR] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_pressure_limit(ErrorAlarmType _err,
		bool _bfErr, bool _resetP) {
	if ((error[0][_err] == true) & (alarm[0][_err] == false)) {
		{ //par
			if ((_bfErr == true)) {
				if ((blood_flow_conn_reset == true)) {
					_bfErr = false;
				}
			}
			if ((_resetP == true)) {
				if ((param_press_reset == true)) {
					_resetP = false;
				}
			}
			if ((_bfErr == false) & (_resetP == false)) {
				error[1][_err] = false;
			}
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_error_vp_up_limit() {
	r_error_pressure_limit(INIT_VP_UP, bf_err_vp_up[0],
			reset_err_pres_vp_up[0]);
}
void Hemodialysis_ref4_forMC::r_error_vp_low_limit() {
	r_error_pressure_limit(INIT_VP_LOW, bf_err_vp_low[0],
			reset_err_pres_vp_low[0]);
}
void Hemodialysis_ref4_forMC::r_error_ap_up_limit() {
	r_error_pressure_limit(INIT_AP_UP, bf_err_ap_up[0],
			reset_err_pres_ap_up[0]);
}
void Hemodialysis_ref4_forMC::r_error_ap_low_limit() {
	r_error_pressure_limit(INIT_AP_LOW, bf_err_ap_low[0],
			reset_err_pres_ap_low[0]);
}
void Hemodialysis_ref4_forMC::r_error_update_blood_flow(ErrorAlarmType _err) {
	if ((error[0][_err] == true) & (alarm[0][_err] == false)) {
		if ((blood_flow_conn_reset == true)) {
			error[1][_err] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_conn_vp_up_limit() {
	r_error_update_blood_flow (CONN_VP_UP);
}
void Hemodialysis_ref4_forMC::r_error_conn_vp_low_limit() {
	r_error_pressure_limit(CONN_VP_LOW, bf_err_vp_low_conn[0],
			reset_err_pres_vp_low_conn[0]);
}
void Hemodialysis_ref4_forMC::r_error_conn_ap_low_limit() {
	r_error_pressure_limit(CONN_AP_LOW, bf_err_ap_low_conn[0],
			reset_err_pres_ap_low_conn[0]);
}
void Hemodialysis_ref4_forMC::r_error_rein_vp_up_limit() {
	r_error_update_blood_flow (REIN_VP_UP);
}
void Hemodialysis_ref4_forMC::r_error_rein_ap_low_limit() {
	r_error_update_blood_flow (REIN_AP_LOW);
}
void Hemodialysis_ref4_forMC::r_error_fill_blood_vol() {
	r_error_update_blood_flow (FILL_BLOOD_VOL);
}
void Hemodialysis_ref4_forMC::r_error_df_prep() {
	if ((error[0][DF_PREP] == true) & (alarm[0][DF_PREP] == false)) {
		if ((detect_bicarbonate == true)) {
			error[1][DF_PREP] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_heparin() {
	if ((error[0][HEPARIN_DIR] == true) & (alarm[0][HEPARIN_DIR] == false)) {
		if ((reverse_dir_heparin == false)) {
			{ //par
				error[1][HEPARIN_DIR] = false;
				heparin_running[1] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_SAD() {
	if ((error[0][SAD_ERR] == true) & (alarm[0][SAD_ERR] == false)) {
		if ((currentSAD == 1) & (current_air_vol == 0)
				| (currentSAD == 2) & (current_air_vol == 1)
				| (currentSAD == 3) & (current_air_vol == 2)) {
			error[1][SAD_ERR] = false;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_UF_rate() {
	if ((error[0][UF_RATE] == true) & (alarm[0][UF_RATE] == false)) {
		if ((uf_rate_passed_max_uf_rate == false)) {
			error[1][UF_RATE] = true;
		}
	}
}
void Hemodialysis_ref4_forMC::r_error_UF_dir() {
	if ((error[0][UF_DIR] == true) & (alarm[0][UF_DIR] == false)) {
		if ((uf_dir_backwards == false)) {
			error[1][UF_DIR] = true;
		}
	}
}
void Hemodialysis_ref4_forMC::r_run_error() {
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
void Hemodialysis_ref4_forMC::r_run_alarm() {
	if ((reset_alarm == true)) {
		for (auto _alarmon : ErrorAlarmType_elems)
			if ((alarm[0][_alarmon] == true)) {
				alarm[1][_alarmon] = false;
			}
	}
}
void Hemodialysis_ref4_forMC::r_set_filling_bp_rate() {
	rinsingParam[1] = FILLING_BP_VOLUME;
}
void Hemodialysis_ref4_forMC::r_set_filling_bp_volume() {
	rinsingParam[1] = BP_RATE_RINSING;
}
void Hemodialysis_ref4_forMC::r_set_bp_rate_rinsing() {
	rinsingParam[1] = DF_FLOW_RINSING;
}
void Hemodialysis_ref4_forMC::r_set_df_flow_rinsing() {
	rinsingParam[1] = TIME_RINSING;
}
void Hemodialysis_ref4_forMC::r_set_time_rinsing() {
	rinsingParam[1] = UF_RATE_RINSING;
}
void Hemodialysis_ref4_forMC::r_set_uf_rate_rinsing() {
	rinsingParam[1] = UF_VOLUME_RINSING;
}
void Hemodialysis_ref4_forMC::r_set_uf_volume_rinsing() {
	modePreparation[1] = TUBING_SYSTEM;
}
void Hemodialysis_ref4_forMC::r_set_rinsing_param() {
	{ //par
		if ((rinsingParam[0] == FILLING_BP_RATE)) {
			r_set_filling_bp_rate();
		}
		if ((rinsingParam[0] == FILLING_BP_VOLUME)) {
			r_set_filling_bp_volume();
		}
		if ((rinsingParam[0] == BP_RATE_RINSING)) {
			r_set_bp_rate_rinsing();
		}
		if ((rinsingParam[0] == DF_FLOW_RINSING)) {
			r_set_df_flow_rinsing();
		}
		if ((rinsingParam[0] == TIME_RINSING)) {
			r_set_time_rinsing();
		}
		if ((rinsingParam[0] == UF_RATE_RINSING)) {
			r_set_uf_rate_rinsing();
		}
		if ((rinsingParam[0] == UF_VOLUME_RINSING)) {
			r_set_uf_volume_rinsing();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_connect_av_tubes() {
	if ((av_tubes_conn == true)) {
		tubingPhase[1] = CONNECT_ALL_COMP;
	}
}
void Hemodialysis_ref4_forMC::r_connect_all_comp() {
	if ((all_comp_conn == true)) {
		tubingPhase[1] = SET_SALINE_LEVELS;
	}
}
void Hemodialysis_ref4_forMC::r_set_saline_levels() {
	if ((saline_levels_set == true)) {
		tubingPhase[1] = INSERT_BLOODLINES;
	}
}
void Hemodialysis_ref4_forMC::r_insert_bloodlines() {
	if ((blood_line_insert == true)) {
		tubingPhase[1] = PRIMING;
	}
}
void Hemodialysis_ref4_forMC::r_priming() {
	if ((bp_status_der() == STOP)) {
		bp_status[1] = START;
	} else if ((bp_fill_fluid == true) & (bp_rate_rinsing_150 == true)) {
		{ //par
			bp_status[1] = STOP;
			tubingPhase[1] = CONNECT_AV_ENDS;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_connect_av_ends() {
	if ((av_ends_conn == true)) {
		modePreparation[1] = PREPARE_HEPARIN;
	}
}
void Hemodialysis_ref4_forMC::r_tubing_system() {
	{ //par
		if ((tubingPhase[0] == CONNECT_AV_TUBES)) {
			r_connect_av_tubes();
		}
		if ((tubingPhase[0] == CONNECT_ALL_COMP)) {
			r_connect_all_comp();
		}
		if ((tubingPhase[0] == SET_SALINE_LEVELS)) {
			r_set_saline_levels();
		}
		if ((tubingPhase[0] == INSERT_BLOODLINES)) {
			r_insert_bloodlines();
		}
		if ((tubingPhase[0] == PRIMING)) {
			r_priming();
		}
		if ((tubingPhase[0] == CONNECT_AV_ENDS)) {
			r_connect_av_ends();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_prepare_heparin() {
	if ((heparin_prepared == true)) {
		modePreparation[1] = SET_TREAT_PARAM;
	}
}
void Hemodialysis_ref4_forMC::r_set_blood_conductivity() {
	treatmentParam[1] = BIC_AC;
}
void Hemodialysis_ref4_forMC::r_set_bic_ac() {
	treatmentParam[1] = BIC_CONDUCTIVITY;
}
void Hemodialysis_ref4_forMC::r_set_bic_conductivity() {
	treatmentParam[1] = DF_TEMP;
}
void Hemodialysis_ref4_forMC::r_set_df_temp() {
	treatmentParam[1] = DF_FLOW;
}
void Hemodialysis_ref4_forMC::r_set_df_flow() {
	treatmentParam[1] = UF_VOLUME;
}
void Hemodialysis_ref4_forMC::r_set_uf_volume() {
	treatmentParam[1] = THERAPY_TIME;
}
void Hemodialysis_ref4_forMC::r_set_therapy_time() {
	treatmentParam[1] = MIN_UF_RATE;
}
void Hemodialysis_ref4_forMC::r_set_min_uf_rate() {
	treatmentParam[1] = MAX_UF_RATE;
}
void Hemodialysis_ref4_forMC::r_set_max_uf_rate() {
	treatmentParam[1] = MIN_AP;
}
void Hemodialysis_ref4_forMC::r_set_min_ap() {
	treatmentParam[1] = MAX_AP;
}
void Hemodialysis_ref4_forMC::r_set_max_ap() {
	treatmentParam[1] = MIN_VP;
}
void Hemodialysis_ref4_forMC::r_set_min_vp() {
	treatmentParam[1] = MAX_VP;
}
void Hemodialysis_ref4_forMC::r_set_max_vp() {
	treatmentParam[1] = DELTA_AP;
}
void Hemodialysis_ref4_forMC::r_set_delta_ap() {
	treatmentParam[1] = PERC_DELTA_TMP;
}
void Hemodialysis_ref4_forMC::r_set_perc_delta_tmp() {
	treatmentParam[1] = LIMITS_TMP;
}
void Hemodialysis_ref4_forMC::r_set_limits_tmp() {
	treatmentParam[1] = MAX_TMP;
}
void Hemodialysis_ref4_forMC::r_set_max_tmp() {
	treatmentParam[1] = EXTENDED_TMP;
}
void Hemodialysis_ref4_forMC::r_set_extended_tmp() {
	treatmentParam[1] = MAX_BEP;
}
void Hemodialysis_ref4_forMC::r_set_max_bep() {
	treatmentParam[1] = STOP_TIME_H;
}
void Hemodialysis_ref4_forMC::r_set_h_stop_time() {
	treatmentParam[1] = BOLUS_VOLUME_H;
}
void Hemodialysis_ref4_forMC::r_set_h_bolus_volume() {
	treatmentParam[1] = RATE_H;
}
void Hemodialysis_ref4_forMC::r_set_h_rate() {
	treatmentParam[1] = ACTIVATION_H;
}
void Hemodialysis_ref4_forMC::r_set_h_activation() {
	r_insert_param(SYRINGE_TYPE, activation_h, activation_h_contr[0]);
}
void Hemodialysis_ref4_forMC::r_set_syringe_type() {
	modePreparation[1] = RINSE_DIALYZER;
}
void Hemodialysis_ref4_forMC::r_set_treatment_param() {
	{ //par
		if ((treatmentParam[0] == BLOOD_CONDUCTIVITY)) {
			r_set_blood_conductivity();
		}
		if ((treatmentParam[0] == BIC_AC)) {
			r_set_bic_ac();
		}
		if ((treatmentParam[0] == BIC_CONDUCTIVITY)) {
			r_set_bic_conductivity();
		}
		if ((treatmentParam[0] == DF_TEMP)) {
			r_set_df_temp();
		}
		if ((treatmentParam[0] == DF_FLOW)) {
			r_set_df_flow();
		}
		if ((treatmentParam[0] == UF_VOLUME)) {
			r_set_uf_volume();
		}
		if ((treatmentParam[0] == THERAPY_TIME)) {
			r_set_therapy_time();
		}
		if ((treatmentParam[0] == MIN_UF_RATE)) {
			r_set_min_uf_rate();
		}
		if ((treatmentParam[0] == MAX_UF_RATE)) {
			r_set_max_uf_rate();
		}
		if ((treatmentParam[0] == MIN_AP)) {
			r_set_min_ap();
		}
		if ((treatmentParam[0] == MAX_AP)) {
			r_set_max_ap();
		}
		if ((treatmentParam[0] == MIN_VP)) {
			r_set_min_vp();
		}
		if ((treatmentParam[0] == MAX_VP)) {
			r_set_max_vp();
		}
		if ((treatmentParam[0] == DELTA_AP)) {
			r_set_delta_ap();
		}
		if ((treatmentParam[0] == PERC_DELTA_TMP)) {
			r_set_perc_delta_tmp();
		}
		if ((treatmentParam[0] == LIMITS_TMP)) {
			r_set_limits_tmp();
		}
		if ((treatmentParam[0] == MAX_TMP)) {
			r_set_max_tmp();
		}
		if ((treatmentParam[0] == EXTENDED_TMP)) {
			r_set_extended_tmp();
		}
		if ((treatmentParam[0] == MAX_BEP)) {
			r_set_max_bep();
		}
		if ((treatmentParam[0] == STOP_TIME_H)) {
			r_set_h_stop_time();
		}
		if ((treatmentParam[0] == BOLUS_VOLUME_H)) {
			r_set_h_bolus_volume();
		}
		if ((treatmentParam[0] == RATE_H)) {
			r_set_h_rate();
		}
		if ((treatmentParam[0] == ACTIVATION_H)) {
			r_set_h_activation();
		}
		if ((treatmentParam[0] == SYRINGE_TYPE)) {
			r_set_syringe_type();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_connect_dialyzer() {
	if ((dialyzer_connected == true)) {
		rinsePhase[1] = FILL_ART_CHAMBER;
	}
}
void Hemodialysis_ref4_forMC::r_fill_art_chamber() {
	if ((bp_status_der() == STOP)) {
		bp_status[1] = START;
	} else if ((arterial_chamber_filled == true)) {
		rinsePhase[1] = FILL_VEN_CHAMBER;
	}
}
void Hemodialysis_ref4_forMC::r_fill_ven_chamber() {
	if ((venous_chamber_fill == true)) {
		rinsePhase[1] = FILL_DIALYZER;
	}
}
void Hemodialysis_ref4_forMC::r_fill_dialyzer() {
	if ((dialyzer_filled == true)) {
		{ //par
			bp_status[1] = STOP;
			phaseTherapy[1] = INITIATION;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_rinse_dialyzer() {
	{ //par
		if ((rinsePhase[0] == CONNECT_DIALYZER)) {
			r_connect_dialyzer();
		}
		if ((rinsePhase[0] == FILL_ART_CHAMBER)) {
			r_fill_art_chamber();
		}
		if ((rinsePhase[0] == FILL_VEN_CHAMBER)) {
			r_fill_ven_chamber();
		}
		if ((rinsePhase[0] == FILL_DIALYZER)) {
			r_fill_dialyzer();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_preparation() {
	{ //par
		if ((modePreparation[0] == AUTO_TEST)) {
			r_auto_test();
		}
		if ((modePreparation[0] == CONNECT_CONCENTRATE)) {
			r_connect_concentrate();
		}
		if ((modePreparation[0] == SET_RINSING_PARAM)) {
			r_set_rinsing_param();
		}
		if ((modePreparation[0] == TUBING_SYSTEM)) {
			r_tubing_system();
		}
		if ((modePreparation[0] == PREPARE_HEPARIN)) {
			r_prepare_heparin();
		}
		if ((modePreparation[0] == SET_TREAT_PARAM)) {
			r_set_treatment_param();
		}
		if ((modePreparation[0] == RINSE_DIALYZER)) {
			r_rinse_dialyzer();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_check_initiation_phase() {
	{ //par
		r_check_temp_high();
		r_check_temp_low();
		if ((bp_status_der() == START)) {
			{ //par
				r_check_BP_rotation_dir();
				r_check_bp_less_flow();
				r_check_bp_no_flow_err();
			} //endpar
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_conn_arterially() {
	if ((art_connected == true)) {
		{ //par
			patientPhase[1] = START_BP;
			art_connected_contr[1] = true;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_start_bp() {
	if ((error_bp() == false)) {
		{ //par
			patientPhase[1] = BLOOD_FLOW;
			bp_status[1] = START;
			ap_limits_set[1] = false;
			vp_limits_set[1] = false;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_set_blood_flow() {
	if ((ven_connected_contr[0] == false)) {
		r_insert_param(FILL_TUBING, blood_flow_conn_set);
	} else {
		r_insert_param(END_CONN, blood_flow_conn_set);
	}
}
void Hemodialysis_ref4_forMC::r_fill_tubing() {
	if ((error_bp() == false)) {
		if ((blood_on_VRD == true) | (conn_infuse_set_volume == true)) {
			{ //par
				patientPhase[1] = CONN_VEN;
				bp_status[1] = STOP;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_conn_venously() {
	if ((ven_connected == true)) {
		{ //par
			patientPhase[1] = START_BP;
			ven_connected_contr[1] = true;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_end_connection() {
	{ //par
		machine_state[1] = MAIN_FLOW;
		bicarbonate_status[1] = true;
		signal_lamp[1] = GREEN;
		modeInitiation[1] = THERAPY_RUNNING;
	} //endpar
}
void Hemodialysis_ref4_forMC::r_patient_connection() {
	{ //par
		if ((patientPhase[0] == CONN_ART)) {
			r_conn_arterially();
		}
		if ((patientPhase[0] == START_BP)) {
			r_start_bp();
		}
		if ((patientPhase[0] == BLOOD_FLOW)) {
			r_set_blood_flow();
		}
		if ((patientPhase[0] == FILL_TUBING)) {
			r_fill_tubing();
		}
		if ((patientPhase[0] == CONN_VEN)) {
			r_conn_venously();
		}
		if ((patientPhase[0] == END_CONN)) {
			r_end_connection();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_check_patient() {
	if ((bp_status_der() == START)) {
		{ //par
			r_check_conn_vp_up_limit();
			r_check_conn_vp_low_limit();
			r_check_conn_ap_low_limit();
			r_check_fill_blood_vol();
			r_check_SAD();
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_connect_patient() {
	if ((err_patient_conn() == false) & (error_bp() == false)) {
		{ //par
			r_patient_connection();
			r_check_patient();
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_start_heparin() {
	if ((activation_h_contr[0] == true)) {
		{ //par
			therapyPhase[1] = THERAPY_EXEC;
			heparin_running[1] = true;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_run_heparin() {
	if ((heparin_running[0] == true)) {
		{ //par
			r_check_heparin();
			if ((passedHeparinTime == true)) {
				heparin_running[1] = false;
			}
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_set_ap_vp_limits() {
	{ //par
		if ((ap_limits_set[0] == false)) {
			ap_limits_set[1] = true;
		}
		if ((vp_limits_set[0] == false)) {
			vp_limits_set[1] = true;
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_ap_vp_limits() {
	if ((check_ap[0] == true)) {
		if ((ap_limits_set[0] == false) | (vp_limits_set[0] == false)) {
			if ((passed10Sec == true)) {
				r_set_ap_vp_limits();
			}
		}
	}
}
void Hemodialysis_ref4_forMC::r_treatment_min_UF() {
	if ((treatment_min_uf_rate_contr[0] == false)) {
		if ((treatment_min_uf_rate == true)) {
			treatment_min_uf_rate_contr[1] = true;
		}
	}
}
void Hemodialysis_ref4_forMC::r_interrupt() {
	if ((interrupt_dialysis == true)) {
		{ //par
			therapyPhase[1] = THERAPY_END;
			machine_state[1] = BYPASS;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_start_arterial_bolus() {
	if ((start_arterial_bolus == true)) {
		arterialBolusPhase[1] = SET_ARTERIAL_BOLUS_VOLUME;
	}
}
void Hemodialysis_ref4_forMC::r_set_arterial_bolus_volume() {
	if ((art_bolus_volume_set == true)) {
		{ //par
			bp_status[1] = STOP;
			arterialBolusPhase[1] = CONNECT_SOLUTION;
			check_ap[1] = false;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_connect_solution() {
	if ((saline_solution_conn == true)) {
		{ //par
			arterialBolusPhase[1] = RUNNING_SOLUTION;
			bp_status[1] = START;
			ap_limits_set[1] = false;
			vp_limits_set[1] = false;
			check_ap[1] = true;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_running_solution() {
	if ((alarm[0][ARTERIAL_BOLUS_END] == false)) {
		if ((current_art_bolus_volume == 0)) {
			{ //par
				arterialBolusPhase[1] = WAIT_SOLUTION;
				alarm[1][ARTERIAL_BOLUS_END] = true;
			} //endpar
		}
	}
}
void Hemodialysis_ref4_forMC::r_check_arterial_bolus() {
	if ((current_art_bolus_volume == 2)) {
		{ //par
			alarm[1][ARTERIAL_BOLUS] = true;
			error[1][ARTERIAL_BOLUS] = true;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_run_arterial_bolus() {
	{ //par
		r_running_solution();
		r_check_arterial_bolus();
	} //endpar
}
void Hemodialysis_ref4_forMC::r_arterial_bolus() {
	if ((error[0][ARTERIAL_BOLUS] == false)) {
		{ //par
			if ((arterialBolusPhase[0] == WAIT_SOLUTION)) {
				r_start_arterial_bolus();
			}
			if ((arterialBolusPhase[0] == SET_ARTERIAL_BOLUS_VOLUME)) {
				r_set_arterial_bolus_volume();
			}
			if ((arterialBolusPhase[0] == CONNECT_SOLUTION)) {
				r_connect_solution();
			}
			if ((arterialBolusPhase[0] == RUNNING_SOLUTION)) {
				r_run_arterial_bolus();
			}
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_therapy_end_time() {
	if ((passedTherapyTime == true)) {
		therapyPhase[1] = THERAPY_END;
	}
}
void Hemodialysis_ref4_forMC::r_update_blood_flow() {
	if ((update_blood_flow == true)) {
		;
	}
}
void Hemodialysis_ref4_forMC::r_check_therapy_run() {
	{ //par
		if ((bp_status_der() == START)) {
			{ //par
				if ((ap_limits_set[0] == true) & (vp_limits_set[0] == true)) {
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
void Hemodialysis_ref4_forMC::r_therapy_exec() {
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
void Hemodialysis_ref4_forMC::r_therapy_end() {
	phaseTherapy[1] = ENDING;
}
void Hemodialysis_ref4_forMC::r_running_therapy() {
	{ //par
		if ((therapyPhase[0] == START_HEPARIN)) {
			r_start_heparin();
		}
		if ((therapyPhase[0] == THERAPY_EXEC)) {
			r_therapy_exec();
		}
		if ((therapyPhase[0] == THERAPY_END)) {
			r_therapy_end();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_initiation() {
	{ //par
		r_check_initiation_phase();
		if ((modeInitiation[0] == CONNECT_PATIENT)) {
			r_connect_patient();
		}
		if ((modeInitiation[0] == THERAPY_RUNNING)) {
			r_running_therapy();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_remove_arterially() {
	if ((art_removed == true)) {
		{ //par
			reinfusionPhase[1] = CONN_SALINE;
			art_connected_contr[1] = false;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_connect_saline() {
	if ((saline_conn == true)) {
		reinfusionPhase[1] = START_SALINE_INF;
	}
}
void Hemodialysis_ref4_forMC::r_start_saline_inf() {
	{ //par
		bp_status[1] = START;
		reinfusionPhase[1] = RUN_SALINE_INF;
	} //endpar
}
void Hemodialysis_ref4_forMC::r_run_saline_inf() {
	if ((saline_on_VRD == true)) {
		{ //par
			reinfusionPhase[1] = CHOOSE_NEXT_REINF_STEP;
			bp_status[1] = STOP;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_choose_next_reinf_step() {
	if ((new_saline_reinfusion == true)) {
		reinfusionPhase[1] = START_SALINE_REIN;
	} else {
		reinfusionPhase[1] = REMOVE_VEN;
	}
}
void Hemodialysis_ref4_forMC::r_start_saline_reinf() {
	{ //par
		bp_status[1] = START;
		reinfusionPhase[1] = RUN_SALINE_REIN;
	} //endpar
}
void Hemodialysis_ref4_forMC::r_run_saline_reinf() {
	if ((volume_saline_inf_400 == true) | (passed5Min == true)) {
		{ //par
			reinfusionPhase[1] = CHOOSE_NEXT_REINF_STEP;
			bp_status[1] = STOP;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_remove_venously() {
	if ((ven_removed == true)) {
		{ //par
			modeEnding[1] = DRAIN_DIALYZER;
			ven_connected_contr[1] = false;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_run_reinfusion() {
	{ //par
		if ((reinfusionPhase[0] == REMOVE_ART)) {
			r_remove_arterially();
		}
		if ((reinfusionPhase[0] == CONN_SALINE)) {
			r_connect_saline();
		}
		if ((reinfusionPhase[0] == START_SALINE_INF)) {
			r_start_saline_inf();
		}
		if ((reinfusionPhase[0] == CHOOSE_NEXT_REINF_STEP)) {
			r_choose_next_reinf_step();
		}
		if ((reinfusionPhase[0] == RUN_SALINE_INF)) {
			r_run_saline_inf();
		}
		if ((reinfusionPhase[0] == START_SALINE_REIN)) {
			r_start_saline_reinf();
		}
		if ((reinfusionPhase[0] == RUN_SALINE_REIN)) {
			r_run_saline_reinf();
		}
		if ((reinfusionPhase[0] == REMOVE_VEN)) {
			r_remove_venously();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_check_reinfusion() {
	if ((bp_status_der() == START)) {
		{ //par
			r_check_rein_vp_up_limit();
			r_check_rein_ap_low_limit();
			r_check_SAD();
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_reinfusion() {
	if ((error_rein_press() == false)) {
		{ //par
			r_run_reinfusion();
			r_check_reinfusion();
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_drain_dialyzer() {
	if ((dialyzer_drained == true)) {
		{ //par
			modeEnding[1] = EMPTY_CARTRIDGE;
			empty_dialyzer[1] = true;
		} //endpar
	}
}
void Hemodialysis_ref4_forMC::r_empty_cartridge() {
	if ((cartridge_emtpy == true)) {
		modeEnding[1] = THERAPY_OVERVIEW;
	}
}
void Hemodialysis_ref4_forMC::r_therapy_overview() {
	;
}
void Hemodialysis_ref4_forMC::r_ending() {
	{ //par
		if ((modeEnding[0] == REINFUSION)) {
			r_reinfusion();
		}
		if ((modeEnding[0] == DRAIN_DIALYZER)) {
			r_drain_dialyzer();
		}
		if ((modeEnding[0] == EMPTY_CARTRIDGE)) {
			r_empty_cartridge();
		}
		if ((modeEnding[0] == THERAPY_OVERVIEW)) {
			r_therapy_overview();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_run_therapy() {
	{ //par
		if ((phaseTherapy[0] == PREPARATION)) {
			r_preparation();
		}
		if ((phaseTherapy[0] == INITIATION)) {
			r_initiation();
		}
		if ((phaseTherapy[0] == ENDING)) {
			r_ending();
		}
	} //endpar
}
void Hemodialysis_ref4_forMC::r_Main() {
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

// Static function definition
bool Hemodialysis_ref4_forMC::errorePerBYPASS() {
	return error[0][TEMP_HIGH] | error[0][TEMP_LOW] | error[0][DF_PREP]
			| error[0][UF_DIR] | alarm[0][UF_VOLUME_ERR];
}
bool Hemodialysis_ref4_forMC::errorePERbpStatus() {
	return error[0][BP_ROTATION_DIR] | error[0][BP_NO_FLOW]
			| error[0][INIT_VP_UP] | error[0][INIT_AP_UP]
			| error[0][INIT_VP_LOW] | error[0][INIT_AP_LOW]
			| error[0][CONN_VP_UP] | error[0][CONN_VP_LOW]
			| error[0][CONN_AP_LOW] | error[0][REIN_VP_UP]
			| error[0][REIN_AP_LOW] | error[0][FILL_BLOOD_VOL]
			| error[0][HEPARIN_DIR] | error[0][SAD_ERR]
			| error[0][ARTERIAL_BOLUS];
}
bool Hemodialysis_ref4_forMC::errorePERbicarbonate() {
	return error[0][DF_PREP] | error[0][UF_RATE] | alarm[0][UF_BYPASS];
}
MachineState Hemodialysis_ref4_forMC::machine_status_der() {
	return /*conditionalTerm*/
	(errorePerBYPASS()) ? BYPASS : machine_state[0];
}
BPStatus Hemodialysis_ref4_forMC::bp_status_der() {
	return /*conditionalTerm*/
	(errorePERbpStatus()) ? STOP : bp_status[0];
}
bool Hemodialysis_ref4_forMC::bicarbonate_status_der() {
	return /*conditionalTerm*/
	(errorePERbicarbonate()) ? false : bicarbonate_status[0];
}
bool Hemodialysis_ref4_forMC::errorExist() {
	return [&]()->bool { /*<--- ExistsTerm*/
		for(auto _t : ErrorAlarmType_elems)
		if((error[0][_t] == true))
		return true;
		return false;
	}();
}
bool Hemodialysis_ref4_forMC::alarmExist() {
	return [&]()->bool { /*<--- ExistsTerm*/
		for(auto _t : ErrorAlarmType_elems)
		if((alarm[0][_t] == true))
		return true;
		return false;
	}();
}
bool Hemodialysis_ref4_forMC::err_patient_conn() {
	return (error[0][CONN_VP_UP] == true) | (error[0][CONN_VP_LOW] == true)
			| (error[0][CONN_AP_LOW] == true)
			| (error[0][FILL_BLOOD_VOL] == true) | (error[0][SAD_ERR] == true);
}
bool Hemodialysis_ref4_forMC::error_bp() {
	return (error[0][BP_NO_FLOW] == true) | (error[0][BP_ROTATION_DIR] == true)
			| (error[0][BP_LESS_FLOW] == true);
}
bool Hemodialysis_ref4_forMC::error_rein_press() {
	return (error[0][REIN_VP_UP] == true) | (error[0][REIN_AP_LOW] == true)
			| (error[0][SAD_ERR] == true);
}
bool Hemodialysis_ref4_forMC::error_therapy() {
	return (error[0][INIT_VP_UP] == true) | (error[0][INIT_VP_LOW] == true)
			| (error[0][INIT_AP_UP] == true) | (error[0][INIT_AP_LOW] == true)
			| (error[0][SAD_ERR] == true) | (error[0][HEPARIN_DIR] == true);
}

// Function and domain initialization
Hemodialysis_ref4_forMC::Hemodialysis_ref4_forMC() :
		// Static domain initialization 
		SADLimit_elems(set<int> { 1, 2, 3, 4 }), LMHlimit_elems(set<int> { 0, 1,
				2 }), Airlimit_elems(set<int> { 0, 1, 2, 3 }), PhasesTherapy_elems(
				{ PREPARATION, INITIATION, ENDING }), ModePreparation_elems( {
				RINSE_DIALYZER, SET_TREAT_PARAM, PREPARE_HEPARIN, TUBING_SYSTEM,
				SET_RINSING_PARAM, CONNECT_CONCENTRATE, AUTO_TEST }), ModeInitiation_elems(
				{ CONNECT_PATIENT, THERAPY_RUNNING }), ModeEnding_elems(
				{ REINFUSION, THERAPY_OVERVIEW, EMPTY_CARTRIDGE, DRAIN_DIALYZER }), RinsingParam_elems(
				{ FILLING_BP_RATE, FILLING_BP_VOLUME, BP_RATE_RINSING,
						DF_FLOW_RINSING, TIME_RINSING, UF_RATE_RINSING,
						UF_VOLUME_RINSING }), TubingPhase_elems( {
				CONNECT_AV_TUBES, CONNECT_ALL_COMP, SET_SALINE_LEVELS,
				INSERT_BLOODLINES, PRIMING, CONNECT_AV_ENDS }), TreatmentParam_elems(
				{ BLOOD_CONDUCTIVITY, BIC_AC, BIC_CONDUCTIVITY, DF_TEMP,
						DF_FLOW, UF_VOLUME, THERAPY_TIME, MIN_UF_RATE,
						MAX_UF_RATE, MIN_AP, MAX_AP, MIN_VP, MAX_VP, DELTA_AP,
						PERC_DELTA_TMP, LIMITS_TMP, MAX_TMP, EXTENDED_TMP,
						MAX_BEP, STOP_TIME_H, BOLUS_VOLUME_H, RATE_H,
						ACTIVATION_H, SYRINGE_TYPE }), RinsePhase_elems( {
				CONNECT_DIALYZER, FILL_ART_CHAMBER, FILL_VEN_CHAMBER,
				FILL_DIALYZER }), PatientPhase_elems( { CONN_ART, START_BP,
				BLOOD_FLOW, FILL_TUBING, CONN_VEN, END_CONN }), TherapyPhase_elems(
				{ START_HEPARIN, THERAPY_EXEC, THERAPY_END }), ReinfusionPhase_elems(
				{ REMOVE_ART, CONN_SALINE, START_SALINE_INF, RUN_SALINE_INF,
						CHOOSE_NEXT_REINF_STEP, START_SALINE_REIN,
						RUN_SALINE_REIN, REMOVE_VEN }), BPStatus_elems( { START,
				STOP }), SignalLamps_elems( { GREEN, YELLOW }), MachineState_elems(
				{ BYPASS, MAIN_FLOW }), ArterialBolusPhase_elems( {
				WAIT_SOLUTION, SET_ARTERIAL_BOLUS_VOLUME, CONNECT_SOLUTION,
				RUNNING_SOLUTION }), ErrorAlarmType_elems( { UF_BYPASS,
				UF_VOLUME_ERR, UF_DIR, UF_RATE, SAD_ERR, HEPARIN_DIR, DF_PREP,
				FILL_BLOOD_VOL, REIN_VP_UP, REIN_AP_LOW, CONN_VP_UP,
				CONN_VP_LOW, CONN_AP_LOW, INIT_VP_UP, INIT_VP_LOW, INIT_AP_UP,
				INIT_AP_LOW, BP_NO_FLOW, BP_LESS_FLOW, BP_ROTATION_DIR,
				TEMP_HIGH, TEMP_LOW, ARTERIAL_BOLUS, ARTERIAL_BOLUS_END }) {
	//Init static functions Abstract domain
	//Function initialization
	phaseTherapy[0] = phaseTherapy[1] = PREPARATION;
	modePreparation[0] = modePreparation[1] = AUTO_TEST;
	modeInitiation[0] = modeInitiation[1] = CONNECT_PATIENT;
	modeEnding[0] = modeEnding[1] = REINFUSION;
	rinsingParam[0] = rinsingParam[1] = FILLING_BP_RATE;
	tubingPhase[0] = tubingPhase[1] = CONNECT_AV_TUBES;
	treatmentParam[0] = treatmentParam[1] = BLOOD_CONDUCTIVITY;
	rinsePhase[0] = rinsePhase[1] = CONNECT_DIALYZER;
	patientPhase[0] = patientPhase[1] = CONN_ART;
	therapyPhase[0] = therapyPhase[1] = START_HEPARIN;
	reinfusionPhase[0] = reinfusionPhase[1] = REMOVE_ART;
	machine_state[0] = machine_state[1] = BYPASS;
	signal_lamp[0] = signal_lamp[1] = YELLOW;
	arterialBolusPhase[0] = arterialBolusPhase[1] = WAIT_SOLUTION;
	bp_status[0] = bp_status[1] = STOP;
	activation_h_contr[0] = activation_h_contr[1] = false;
	ven_connected_contr[0] = ven_connected_contr[1] = false;
	art_connected_contr[0] = art_connected_contr[1] = false;
	bicarbonate_status[0] = bicarbonate_status[1] = false;
	heparin_running[0] = heparin_running[1] = false;
	ap_limits_set[0] = ap_limits_set[1] = false;
	vp_limits_set[0] = vp_limits_set[1] = false;
	treatment_min_uf_rate_contr[0] = treatment_min_uf_rate_contr[1] = false;
	empty_dialyzer[0] = empty_dialyzer[1] = false;
	check_ap[0] = check_ap[1] = true;
	for (auto const& _t : ErrorAlarmType_elems) {
		error[0].insert( { _t, false });
		error[1].insert( { _t, false });
	}
	for (auto const& _t : ErrorAlarmType_elems) {
		alarm[0].insert( { _t, false });
		alarm[1].insert( { _t, false });
	}
	bf_err_vp_up[0] = bf_err_vp_up[1] = false;
	reset_err_pres_vp_up[0] = reset_err_pres_vp_up[1] = false;
	bf_err_vp_low[0] = bf_err_vp_low[1] = false;
	reset_err_pres_vp_low[0] = reset_err_pres_vp_low[1] = false;
	bf_err_ap_up[0] = bf_err_ap_up[1] = false;
	reset_err_pres_ap_up[0] = reset_err_pres_ap_up[1] = false;
	bf_err_ap_low[0] = bf_err_ap_low[1] = false;
	reset_err_pres_ap_low[0] = reset_err_pres_ap_low[1] = false;
	bf_err_vp_low_conn[0] = bf_err_vp_low_conn[1] = false;
	reset_err_pres_vp_low_conn[0] = reset_err_pres_vp_low_conn[1] = false;
	bf_err_ap_low_conn[0] = bf_err_ap_low_conn[1] = false;
	reset_err_pres_ap_low_conn[0] = reset_err_pres_ap_low_conn[1] = false;
}

// initialize controlled functions that contains monitored functions in the init term
void Hemodialysis_ref4_forMC::initControlledWithMonitored() {
}

// Apply the update set
void Hemodialysis_ref4_forMC::fireUpdateSet() {
	phaseTherapy[0] = phaseTherapy[1];
	modePreparation[0] = modePreparation[1];
	modeInitiation[0] = modeInitiation[1];
	modeEnding[0] = modeEnding[1];
	rinsingParam[0] = rinsingParam[1];
	tubingPhase[0] = tubingPhase[1];
	treatmentParam[0] = treatmentParam[1];
	rinsePhase[0] = rinsePhase[1];
	patientPhase[0] = patientPhase[1];
	therapyPhase[0] = therapyPhase[1];
	reinfusionPhase[0] = reinfusionPhase[1];
	machine_state[0] = machine_state[1];
	signal_lamp[0] = signal_lamp[1];
	arterialBolusPhase[0] = arterialBolusPhase[1];
	bp_status[0] = bp_status[1];
	activation_h_contr[0] = activation_h_contr[1];
	art_connected_contr[0] = art_connected_contr[1];
	ven_connected_contr[0] = ven_connected_contr[1];
	bicarbonate_status[0] = bicarbonate_status[1];
	heparin_running[0] = heparin_running[1];
	ap_limits_set[0] = ap_limits_set[1];
	vp_limits_set[0] = vp_limits_set[1];
	treatment_min_uf_rate_contr[0] = treatment_min_uf_rate_contr[1];
	empty_dialyzer[0] = empty_dialyzer[1];
	error[0] = error[1];
	alarm[0] = alarm[1];
	bf_err_vp_low[0] = bf_err_vp_low[1];
	reset_err_pres_vp_low[0] = reset_err_pres_vp_low[1];
	bf_err_vp_up[0] = bf_err_vp_up[1];
	reset_err_pres_vp_up[0] = reset_err_pres_vp_up[1];
	bf_err_ap_up[0] = bf_err_ap_up[1];
	reset_err_pres_ap_up[0] = reset_err_pres_ap_up[1];
	bf_err_ap_low[0] = bf_err_ap_low[1];
	reset_err_pres_ap_low[0] = reset_err_pres_ap_low[1];
	bf_err_vp_low_conn[0] = bf_err_vp_low_conn[1];
	reset_err_pres_vp_low_conn[0] = reset_err_pres_vp_low_conn[1];
	bf_err_ap_low_conn[0] = bf_err_ap_low_conn[1];
	reset_err_pres_ap_low_conn[0] = reset_err_pres_ap_low_conn[1];
	check_ap[0] = check_ap[1];
}

//init static functions and elements of abstract domains


