// Hemodialysis_ref4_forMC_Exe.java automatically generated from ASM2CODE
//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM

import java.util.Scanner;

class Hemodialysis_ref4_forMC_Exe {

	static void printControlled(Hemodialysis_ref4_forMC esecuzione) {

		System.out.println(
				"phaseTherapy = " + esecuzione.phaseTherapy.oldValue.name());
		System.out.println(
				"modePreparation = "
						+ esecuzione.modePreparation.oldValue.name());
		System.out.println(
				"modeInitiation = "
						+ esecuzione.modeInitiation.oldValue.name());
		System.out.println(
				"modeEnding = " + esecuzione.modeEnding.oldValue.name());
		System.out.println(
				"rinsingParam = " + esecuzione.rinsingParam.oldValue.name());
		System.out.println(
				"tubingPhase = " + esecuzione.tubingPhase.oldValue.name());
		System.out.println(
				"treatmentParam = "
						+ esecuzione.treatmentParam.oldValue.name());
		System.out.println(
				"rinsePhase = " + esecuzione.rinsePhase.oldValue.name());
		System.out.println(
				"patientPhase = " + esecuzione.patientPhase.oldValue.name());
		System.out.println(
				"therapyPhase = " + esecuzione.therapyPhase.oldValue.name());
		System.out.println(
				"reinfusionPhase = "
						+ esecuzione.reinfusionPhase.oldValue.name());
		System.out.println(
				"machine_state = " + esecuzione.machine_state.oldValue.name());
		System.out.println(
				"signal_lamp = " + esecuzione.signal_lamp.oldValue.name());
		System.out.println(
				"arterialBolusPhase = "
						+ esecuzione.arterialBolusPhase.oldValue.name());
		System.out.println(
				"bp_status = " + esecuzione.bp_status.oldValue.name());
		System.out.println(
				"activation_h_contr = " + esecuzione.activation_h_contr.get());

		System.out.println(
				"art_connected_contr = "
						+ esecuzione.art_connected_contr.get());

		System.out.println(
				"ven_connected_contr = "
						+ esecuzione.ven_connected_contr.get());

		System.out.println(
				"bicarbonate_status = " + esecuzione.bicarbonate_status.get());

		System.out.println(
				"heparin_running = " + esecuzione.heparin_running.get());

		System.out.println("ap_limits_set = " + esecuzione.ap_limits_set.get());

		System.out.println("vp_limits_set = " + esecuzione.vp_limits_set.get());

		System.out.println(
				"treatment_min_uf_rate_contr = "
						+ esecuzione.treatment_min_uf_rate_contr.get());

		System.out.println(
				"empty_dialyzer = " + esecuzione.empty_dialyzer.get());

		System.out.println("bf_err_vp_low = " + esecuzione.bf_err_vp_low.get());

		System.out.println(
				"reset_err_pres_vp_low = "
						+ esecuzione.reset_err_pres_vp_low.get());

		System.out.println("bf_err_vp_up = " + esecuzione.bf_err_vp_up.get());

		System.out.println(
				"reset_err_pres_vp_up = "
						+ esecuzione.reset_err_pres_vp_up.get());

		System.out.println("bf_err_ap_up = " + esecuzione.bf_err_ap_up.get());

		System.out.println(
				"reset_err_pres_ap_up = "
						+ esecuzione.reset_err_pres_ap_up.get());

		System.out.println("bf_err_ap_low = " + esecuzione.bf_err_ap_low.get());

		System.out.println(
				"reset_err_pres_ap_low = "
						+ esecuzione.reset_err_pres_ap_low.get());

		System.out.println(
				"bf_err_vp_low_conn = " + esecuzione.bf_err_vp_low_conn.get());

		System.out.println(
				"reset_err_pres_vp_low_conn = "
						+ esecuzione.reset_err_pres_vp_low_conn.get());

		System.out.println(
				"bf_err_ap_low_conn = " + esecuzione.bf_err_ap_low_conn.get());

		System.out.println(
				"reset_err_pres_ap_low_conn = "
						+ esecuzione.reset_err_pres_ap_low_conn.get());

		System.out.println("check_ap = " + esecuzione.check_ap.get());

	}

	static void askMonitored(Hemodialysis_ref4_forMC esecuzione) {

		System.out.print(
				"Inserire un valore booleano per auto_test_end (true/false):  ");
		Scanner auto_test_endinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String auto_test_endControllo = auto_test_endinput.nextLine();
			if (auto_test_endControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(auto_test_endControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.auto_test_end.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per conn_concentrate (true/false):  ");
		Scanner conn_concentrateinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String conn_concentrateControllo = conn_concentrateinput.nextLine();
			if (conn_concentrateControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(conn_concentrateControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.conn_concentrate.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per heparin_prepared (true/false):  ");
		Scanner heparin_preparedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String heparin_preparedControllo = heparin_preparedinput.nextLine();
			if (heparin_preparedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(heparin_preparedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.heparin_prepared.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per dialyzer_drained (true/false):  ");
		Scanner dialyzer_drainedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String dialyzer_drainedControllo = dialyzer_drainedinput.nextLine();
			if (dialyzer_drainedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(dialyzer_drainedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.dialyzer_drained.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per cartridge_emtpy (true/false):  ");
		Scanner cartridge_emtpyinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String cartridge_emtpyControllo = cartridge_emtpyinput.nextLine();
			if (cartridge_emtpyControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(cartridge_emtpyControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.cartridge_emtpy.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passedHeparinTime (true/false):  ");
		Scanner passedHeparinTimeinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passedHeparinTimeControllo =
					passedHeparinTimeinput.nextLine();
			if (passedHeparinTimeControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passedHeparinTimeControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passedHeparinTime.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passed5Min (true/false):  ");
		Scanner passed5Mininput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passed5MinControllo = passed5Mininput.nextLine();
			if (passed5MinControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passed5MinControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passed5Min.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passedTherapyTime (true/false):  ");
		Scanner passedTherapyTimeinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passedTherapyTimeControllo =
					passedTherapyTimeinput.nextLine();
			if (passedTherapyTimeControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passedTherapyTimeControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passedTherapyTime.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passed120Sec (true/false):  ");
		Scanner passed120Secinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passed120SecControllo = passed120Secinput.nextLine();
			if (passed120SecControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passed120SecControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passed120Sec.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passed10Sec (true/false):  ");
		Scanner passed10Secinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passed10SecControllo = passed10Secinput.nextLine();
			if (passed10SecControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passed10SecControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passed10Sec.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passed3Sec (true/false):  ");
		Scanner passed3Secinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passed3SecControllo = passed3Secinput.nextLine();
			if (passed3SecControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passed3SecControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passed3Sec.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passed1Sec (true/false):  ");
		Scanner passed1Secinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passed1SecControllo = passed1Secinput.nextLine();
			if (passed1SecControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passed1SecControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passed1Sec.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per passed1Msec (true/false):  ");
		Scanner passed1Msecinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String passed1MsecControllo = passed1Msecinput.nextLine();
			if (passed1MsecControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(passed1MsecControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.passed1Msec.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per bp_rate_rinsing_150 (true/false):  ");
		Scanner bp_rate_rinsing_150input = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String bp_rate_rinsing_150Controllo =
					bp_rate_rinsing_150input.nextLine();
			if (bp_rate_rinsing_150Controllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(bp_rate_rinsing_150Controllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.bp_rate_rinsing_150.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per av_tubes_conn (true/false):  ");
		Scanner av_tubes_conninput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String av_tubes_connControllo = av_tubes_conninput.nextLine();
			if (av_tubes_connControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(av_tubes_connControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.av_tubes_conn.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per all_comp_conn (true/false):  ");
		Scanner all_comp_conninput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String all_comp_connControllo = all_comp_conninput.nextLine();
			if (all_comp_connControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(all_comp_connControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.all_comp_conn.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per saline_levels_set (true/false):  ");
		Scanner saline_levels_setinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String saline_levels_setControllo =
					saline_levels_setinput.nextLine();
			if (saline_levels_setControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(saline_levels_setControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.saline_levels_set.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per blood_line_insert (true/false):  ");
		Scanner blood_line_insertinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String blood_line_insertControllo =
					blood_line_insertinput.nextLine();
			if (blood_line_insertControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(blood_line_insertControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.blood_line_insert.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per bp_fill_fluid (true/false):  ");
		Scanner bp_fill_fluidinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String bp_fill_fluidControllo = bp_fill_fluidinput.nextLine();
			if (bp_fill_fluidControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(bp_fill_fluidControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.bp_fill_fluid.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per av_ends_conn (true/false):  ");
		Scanner av_ends_conninput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String av_ends_connControllo = av_ends_conninput.nextLine();
			if (av_ends_connControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(av_ends_connControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.av_ends_conn.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per activation_h (true/false):  ");
		Scanner activation_hinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String activation_hControllo = activation_hinput.nextLine();
			if (activation_hControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(activation_hControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.activation_h.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per dialyzer_connected (true/false):  ");
		Scanner dialyzer_connectedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String dialyzer_connectedControllo =
					dialyzer_connectedinput.nextLine();
			if (dialyzer_connectedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(dialyzer_connectedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.dialyzer_connected.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per arterial_chamber_filled (true/false):  ");
		Scanner arterial_chamber_filledinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String arterial_chamber_filledControllo =
					arterial_chamber_filledinput.nextLine();
			if (arterial_chamber_filledControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(arterial_chamber_filledControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.arterial_chamber_filled.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per venous_chamber_fill (true/false):  ");
		Scanner venous_chamber_fillinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String venous_chamber_fillControllo =
					venous_chamber_fillinput.nextLine();
			if (venous_chamber_fillControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(venous_chamber_fillControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.venous_chamber_fill.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per dialyzer_filled (true/false):  ");
		Scanner dialyzer_filledinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String dialyzer_filledControllo = dialyzer_filledinput.nextLine();
			if (dialyzer_filledControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(dialyzer_filledControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.dialyzer_filled.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per art_connected (true/false):  ");
		Scanner art_connectedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String art_connectedControllo = art_connectedinput.nextLine();
			if (art_connectedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(art_connectedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.art_connected.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per blood_flow_conn_set (true/false):  ");
		Scanner blood_flow_conn_setinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String blood_flow_conn_setControllo =
					blood_flow_conn_setinput.nextLine();
			if (blood_flow_conn_setControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(blood_flow_conn_setControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.blood_flow_conn_set.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per blood_flow_conn_reset (true/false):  ");
		Scanner blood_flow_conn_resetinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String blood_flow_conn_resetControllo =
					blood_flow_conn_resetinput.nextLine();
			if (blood_flow_conn_resetControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(blood_flow_conn_resetControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.blood_flow_conn_reset.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per param_press_reset (true/false):  ");
		Scanner param_press_resetinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String param_press_resetControllo =
					param_press_resetinput.nextLine();
			if (param_press_resetControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(param_press_resetControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.param_press_reset.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per blood_on_VRD (true/false):  ");
		Scanner blood_on_VRDinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String blood_on_VRDControllo = blood_on_VRDinput.nextLine();
			if (blood_on_VRDControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(blood_on_VRDControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.blood_on_VRD.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per conn_infuse_set_volume (true/false):  ");
		Scanner conn_infuse_set_volumeinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String conn_infuse_set_volumeControllo =
					conn_infuse_set_volumeinput.nextLine();
			if (conn_infuse_set_volumeControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(conn_infuse_set_volumeControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.conn_infuse_set_volume.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per ven_connected (true/false):  ");
		Scanner ven_connectedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String ven_connectedControllo = ven_connectedinput.nextLine();
			if (ven_connectedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(ven_connectedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.ven_connected.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per treatment_min_uf_rate (true/false):  ");
		Scanner treatment_min_uf_rateinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String treatment_min_uf_rateControllo =
					treatment_min_uf_rateinput.nextLine();
			if (treatment_min_uf_rateControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(treatment_min_uf_rateControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.treatment_min_uf_rate.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per interrupt_dialysis (true/false):  ");
		Scanner interrupt_dialysisinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String interrupt_dialysisControllo =
					interrupt_dialysisinput.nextLine();
			if (interrupt_dialysisControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(interrupt_dialysisControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.interrupt_dialysis.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per start_arterial_bolus (true/false):  ");
		Scanner start_arterial_bolusinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String start_arterial_bolusControllo =
					start_arterial_bolusinput.nextLine();
			if (start_arterial_bolusControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(start_arterial_bolusControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.start_arterial_bolus.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per saline_solution_conn (true/false):  ");
		Scanner saline_solution_conninput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String saline_solution_connControllo =
					saline_solution_conninput.nextLine();
			if (saline_solution_connControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(saline_solution_connControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.saline_solution_conn.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per art_bolus_volume_set (true/false):  ");
		Scanner art_bolus_volume_setinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String art_bolus_volume_setControllo =
					art_bolus_volume_setinput.nextLine();
			if (art_bolus_volume_setControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(art_bolus_volume_setControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.art_bolus_volume_set.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per art_removed (true/false):  ");
		Scanner art_removedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String art_removedControllo = art_removedinput.nextLine();
			if (art_removedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(art_removedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.art_removed.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per saline_conn (true/false):  ");
		Scanner saline_conninput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String saline_connControllo = saline_conninput.nextLine();
			if (saline_connControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(saline_connControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.saline_conn.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per saline_on_VRD (true/false):  ");
		Scanner saline_on_VRDinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String saline_on_VRDControllo = saline_on_VRDinput.nextLine();
			if (saline_on_VRDControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(saline_on_VRDControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.saline_on_VRD.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per new_saline_reinfusion (true/false):  ");
		Scanner new_saline_reinfusioninput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String new_saline_reinfusionControllo =
					new_saline_reinfusioninput.nextLine();
			if (new_saline_reinfusionControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(new_saline_reinfusionControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.new_saline_reinfusion.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per volume_saline_inf_400 (true/false):  ");
		Scanner volume_saline_inf_400input = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String volume_saline_inf_400Controllo =
					volume_saline_inf_400input.nextLine();
			if (volume_saline_inf_400Controllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(volume_saline_inf_400Controllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.volume_saline_inf_400.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per ven_removed (true/false):  ");
		Scanner ven_removedinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String ven_removedControllo = ven_removedinput.nextLine();
			if (ven_removedControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(ven_removedControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.ven_removed.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per reset_alarm (true/false):  ");
		Scanner reset_alarminput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String reset_alarmControllo = reset_alarminput.nextLine();
			if (reset_alarmControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(reset_alarmControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.reset_alarm.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore Intero per current_art_bolus_volume (Integer):  ");
		Scanner current_art_bolus_volumeinput = new Scanner(System.in);

		for (;;) {
			int x;
			String current_art_bolus_volumeControllo =
					current_art_bolus_volumeinput.nextLine();
			if (current_art_bolus_volumeControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(current_art_bolus_volumeControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.current_art_bolus_volume_supporto.value = x;
			esecuzione.current_art_bolus_volume.set(
					esecuzione.current_art_bolus_volume_supporto);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per update_blood_flow (true/false):  ");
		Scanner update_blood_flowinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String update_blood_flowControllo =
					update_blood_flowinput.nextLine();
			if (update_blood_flowControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(update_blood_flowControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.update_blood_flow.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore Intero per current_temp (Integer):  ");
		Scanner current_tempinput = new Scanner(System.in);

		for (;;) {
			int x;
			String current_tempControllo = current_tempinput.nextLine();
			if (current_tempControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(current_tempControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.current_temp_supporto.value = x;
			esecuzione.current_temp.set(esecuzione.current_temp_supporto);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per current_bp_flow_less_70 (true/false):  ");
		Scanner current_bp_flow_less_70input = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String current_bp_flow_less_70Controllo =
					current_bp_flow_less_70input.nextLine();
			if (current_bp_flow_less_70Controllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(current_bp_flow_less_70Controllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.current_bp_flow_less_70.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore Intero per current_vp (Integer):  ");
		Scanner current_vpinput = new Scanner(System.in);

		for (;;) {
			int x;
			String current_vpControllo = current_vpinput.nextLine();
			if (current_vpControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(current_vpControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.current_vp_supporto.value = x;
			esecuzione.current_vp.set(esecuzione.current_vp_supporto);
			break;
		}

		System.out.print(
				"Inserire un valore Intero per current_ap (Integer):  ");
		Scanner current_apinput = new Scanner(System.in);

		for (;;) {
			int x;
			String current_apControllo = current_apinput.nextLine();
			if (current_apControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(current_apControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.current_ap_supporto.value = x;
			esecuzione.current_ap.set(esecuzione.current_ap_supporto);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per fill_blood_vol_passed_up_limit (true/false):  ");
		Scanner fill_blood_vol_passed_up_limitinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String fill_blood_vol_passed_up_limitControllo =
					fill_blood_vol_passed_up_limitinput.nextLine();
			if (fill_blood_vol_passed_up_limitControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(
						fill_blood_vol_passed_up_limitControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.fill_blood_vol_passed_up_limit.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore Intero per current_air_vol (Integer):  ");
		Scanner current_air_volinput = new Scanner(System.in);

		for (;;) {
			int x;
			String current_air_volControllo = current_air_volinput.nextLine();
			if (current_air_volControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(current_air_volControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.current_air_vol_supporto.value = x;
			esecuzione.current_air_vol.set(esecuzione.current_air_vol_supporto);
			break;
		}

		System.out.print(
				"Inserire un valore Intero per currentSAD (Integer):  ");
		Scanner currentSADinput = new Scanner(System.in);

		for (;;) {
			int x;
			String currentSADControllo = currentSADinput.nextLine();
			if (currentSADControllo.isEmpty())
				break;
			try {
				x = Integer.parseInt(currentSADControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}

			esecuzione.currentSAD_supporto.value = x;
			esecuzione.currentSAD.set(esecuzione.currentSAD_supporto);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per uf_rate_passed_max_uf_rate (true/false):  ");
		Scanner uf_rate_passed_max_uf_rateinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String uf_rate_passed_max_uf_rateControllo =
					uf_rate_passed_max_uf_rateinput.nextLine();
			if (uf_rate_passed_max_uf_rateControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(uf_rate_passed_max_uf_rateControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.uf_rate_passed_max_uf_rate.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per uf_volume_passed_max_uf_volume (true/false):  ");
		Scanner uf_volume_passed_max_uf_volumeinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String uf_volume_passed_max_uf_volumeControllo =
					uf_volume_passed_max_uf_volumeinput.nextLine();
			if (uf_volume_passed_max_uf_volumeControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(
						uf_volume_passed_max_uf_volumeControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.uf_volume_passed_max_uf_volume.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per detected_blood_flow (true/false):  ");
		Scanner detected_blood_flowinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String detected_blood_flowControllo =
					detected_blood_flowinput.nextLine();
			if (detected_blood_flowControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(detected_blood_flowControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.detected_blood_flow.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per bp_rotates_back (true/false):  ");
		Scanner bp_rotates_backinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String bp_rotates_backControllo = bp_rotates_backinput.nextLine();
			if (bp_rotates_backControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(bp_rotates_backControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.bp_rotates_back.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per detect_bicarbonate (true/false):  ");
		Scanner detect_bicarbonateinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String detect_bicarbonateControllo =
					detect_bicarbonateinput.nextLine();
			if (detect_bicarbonateControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(detect_bicarbonateControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.detect_bicarbonate.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per reverse_dir_heparin (true/false):  ");
		Scanner reverse_dir_heparininput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String reverse_dir_heparinControllo =
					reverse_dir_heparininput.nextLine();
			if (reverse_dir_heparinControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(reverse_dir_heparinControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.reverse_dir_heparin.set(y);
			break;
		}

		System.out.print(
				"Inserire un valore booleano per uf_dir_backwards (true/false):  ");
		Scanner uf_dir_backwardsinput = new Scanner(System.in);

		for (;;) {
			Boolean y;
			String uf_dir_backwardsControllo = uf_dir_backwardsinput.nextLine();
			if (uf_dir_backwardsControllo.isEmpty())
				break;
			try {
				y = Boolean.parseBoolean(uf_dir_backwardsControllo);
			} catch (Exception e) {
				System.out.println("hai inserito un valore sbagliato, riprova");
				continue;
			}
			// setti la variabile
			esecuzione.uf_dir_backwards.set(y);
			break;
		}

	}

public static void main(String[] args) {

		System.out.println("INFO - file java creto e tradotto dal file originale Hemodialysis_ref4_forMC.asm");
		System.out.println("Inizio esecuzione del file Hemodialysis_ref4_forMC.java\n\n");

		Hemodialysis_ref4_forMC esecuzione = new Hemodialysis_ref4_forMC();

		String continuare = "no";
		int stato =0;
		stato++;

		System.out.println("INITIAL STATE: ");

		do {

			System.out.println("<State "+ stato +" (controlled)>");

			//Aggiornamento valori dell'ASM e inserimento dati monitorati

			printControlled(esecuzione);
			askMonitored(esecuzione);
			esecuzione.UpdateASM();

			System.out.println("</State "+ stato +" (controlled)>");

			System.out.println("\n<Stato attuale>");
			printControlled(esecuzione);

			System.out.print("Vuoi continuare? (yes/no)  ");
			Scanner input = new Scanner(System.in);
			continuare = input.next();

			stato++;
		}

		while(continuare.contentEquals("yes") || continuare.contentEquals("Yes") || continuare.contentEquals("YES") );

		System.out.println("FINAL STATE:");

		//Valori finale delle variabili
		printControlled(esecuzione);

		System.out.println("esecuzione terminata");

	}

}


