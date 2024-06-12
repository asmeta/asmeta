asm Hemodialysis04

import ../StandardLibrary
import ../CTLLibrary

signature:
// DOMAINS
	enum domain PhasesTherapy = {PREPARATION | INITIATION | ENDING}
	enum domain Mode = {FILL_ART_CHAMBER | FILL_VEN_CHAMBER | PRIMING | CONNECT_AV_ENDS | OVERVIEW | CARTRIDGE | DRAIN_DIALYZER | REMOVE_VEN | WAIT_BP_STOP | RESTART_BP | WAIT_VRD_SIGNAL | REMOVE_ART | AUTO_TEST | CONNECT_CONCENTRATE | SET_RINSING_PARAM | CONNECT_AV_TUBES | SET_COMPONENTS | SET_SALINE_LEVELS | INSERT_BLOODLINES | RINSE_TEST_TUBES | PREPARE_HEPARIN | SET_TREAT_PARAM | RINSE_DIALYZER | CONNECT_ART | START_BP | BLOOD_FLOW | BLOOD_VRD | START_STOP_BP | CONNECT_VEN | CHANGE_STATE | BICARBONATE_RUN | THERAPY_RUNNING}
	enum domain SignalLamps = {GREEN | YELLOW}
	enum domain StartStop = {START | STOP}
	enum domain MachineState= {BYPASS | MAINFLOW}
	
// FUNCTIONS
	dynamic controlled phaseTherapy: PhasesTherapy //Phase terapy in which is currently the system
	dynamic controlled mode: Mode //Modes in which the system can be
	dynamic controlled signalLamp: SignalLamps //Signal lamp on the monitor
	dynamic monitored passed_auto_test: Boolean //True --> the machine automated self test is succesfully completed
	dynamic monitored concentrate_connected: Boolean //True --> the concentrate is connected to the HD machine
	dynamic monitored rinsing_param_set: Boolean //True --> the rinsing parameters are set
	dynamic monitored av_tubes_connected: Boolean //True --> arterial and venous tubes are connected
	dynamic monitored components_connected: Boolean //True --> components (Fig. 2) are connected
	dynamic monitored saline_levels_set: Boolean //True --> saline levels for preparation are set
	dynamic monitored bloodlines_inserted: Boolean //True --> bloodlines are inserted
	dynamic monitored heparin_prepared: Boolean //True --> heparin pump prepared
	dynamic monitored treat_param_set: Boolean //True --> treatment parameters are set
	dynamic controlled blood_pump: StartStop //Blood pump --> start and stop
	dynamic controlled bicarbonate: StartStop //Bicarbonate --> start and stop 
	dynamic monitored connected_art: Boolean //True --> the patient is connected arterially
	dynamic monitored blood_flow_set: Boolean //True --> the blood flow of BP is set
	dynamic controlled machine_state: MachineState //Current state of hemodialysis machine
	dynamic controlled first_blood_flow: Boolean //True --> first call of set blood flow
	dynamic monitored connected_ven: Boolean //True --> the patient is connected venously
	dynamic monitored removed_art: Boolean //True --> the patient is removed arterially
	dynamic monitored removed_ven: Boolean //True --> the patient is removed venously	
	dynamic monitored restart_reinfusion: Boolean //True --> restart reinfusion after automatic stop	
	dynamic monitored button_BP_pressed: Boolean //True --> button of BP pressed on UI 
	dynamic controlled saline_solution: Boolean //True --> physiological saline solution connected to arterial line
	dynamic monitored button_dialyzer_pressed: Boolean //True --> button of dialyzer pressed on UI 
	dynamic controlled end_therapy: Boolean //True --> therapy is finished
	dynamic controlled reinfusion_repeated: Boolean //True --> the reinfusion of physiological saline solution is done twice
	dynamic controlled connected_art_contr: Boolean //True --> the patient is connected arterially
	dynamic controlled connected_ven_contr: Boolean //True --> the patient is connected venously
	dynamic controlled empty_dialyzer: Boolean //True --> the dialyzer is emptied	
	dynamic monitored bp_volume_reaches: Boolean //True --> BP reaches the volume set during SET_RINSING_PARAM mode
	dynamic monitored av_ends_conn: Boolean //True --> the arterial and venous ends are connected	
	dynamic monitored dialyzer_balabceC_conn: Boolean //True --> the dialyzer and balance chamber are connected	
	dynamic controlled dialyzer_balanceC_conn_contr: Boolean //True --> the dialyzer and balance chamber are connected
	dynamic monitored art_chamber_filled: Boolean //True --> the arterial chamber is filled nearly half full
	dynamic monitored ven_chamber_filled: Boolean //True --> the venous chamber is filled up to approx 1cm from the upper edge




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
		
		
	macro rule r_set_rinsing_param =
		if (mode = SET_RINSING_PARAM) then 
			if (rinsing_param_set = true) then
				mode := CONNECT_AV_TUBES
			endif
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
			if (bp_volume_reaches = true) then
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
		
	macro rule r_set_treatment_param =
		if (mode = SET_TREAT_PARAM) then 
			if (treat_param_set = true) then
				mode := RINSE_DIALYZER
			endif
		endif
		
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
		
	macro rule r_set_blood_flow =
		if (mode = BLOOD_FLOW) then 
			if (blood_flow_set = true) then
				if (first_blood_flow = true) then //Blood flow set function is used twice, once goes to BLOOD_VRD mode and the other one goes to CHANGE_STATE mode
					par
						mode := BLOOD_VRD
						first_blood_flow := false
					endpar
				else
					mode := CHANGE_STATE
				endif
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
	
	macro rule r_check_during_connection =
		skip		
					
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
		
	macro rule r_check_initiation_phase =
		skip
			
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
	function bicarbonate = STOP
