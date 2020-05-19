// a simple example with a tic tac toe game

asm phd_master_flat2_v2

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain Status = {UNASSOCIATED | WAITING_FOR_CONFIG | CHECKING_CONFIG | OPERATING | DISASSOCIATING}
	enum domain Transition = {REQ_ASSOC_REL | REQ_ASSOC_ABORT | RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION | RX_AARQ_ACCEPTABLE_AND_UNKNOWN_CONFIGURATION | RX_AARQ_UNACCEPTABLE_CONFIGURATION | RX_AARE | RX_RLRQ | RX_RLRE | RX_ABRT | RX_ROIV_CONFIRMED_EVENT_REPORT | RX_AARQ | RX_ROIV | RX_ROER | RX_RORJ | REQ_AGENT_SUPPLIED_UNKNOWN_CONFIGURATION | REQ_AGENT_SUPPLIED_KNOWN_CONFIGURATION | RX_RORS | RX_RORS_CONFIRMED_ACTION | RX_RORS_CONFIRMED_SET | RX_RORS_GET}
	enum domain Message = {MSG_NO_RESPONSE | MSG_RX_AARE | MSG_RX_ABRT | MSG_RX_RLRQ | MSG_RX_RLRE | MSG_RX_PRST}
	// FUNCTIONS
	controlled status: Status
	monitored transition: Transition //row chosen by the user
	controlled message: Message
	
	// to forse a second call of event ??? 
	controlled ext_configurations: Boolean
	controlled new_measurement: Boolean
	
definitions:
	// DOMAIN DEFINITIONS
	
	rule r_1 = if status = UNASSOCIATED and transition = REQ_ASSOC_REL then par status := UNASSOCIATED message := MSG_NO_RESPONSE	 endpar endif
rule r_2 = if status = UNASSOCIATED and transition = REQ_ASSOC_ABORT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_3 = if status = UNASSOCIATED and transition = RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION then par status := OPERATING message := MSG_RX_AARE endpar endif
rule r_4 = if status = UNASSOCIATED and transition = RX_AARQ_ACCEPTABLE_AND_UNKNOWN_CONFIGURATION then par status := WAITING_FOR_CONFIG message := MSG_RX_AARE ext_configurations:= false new_measurement:= false endpar endif
rule r_5 = if status = UNASSOCIATED and transition = RX_AARQ_UNACCEPTABLE_CONFIGURATION then par status := UNASSOCIATED message := MSG_RX_AARE endpar endif
rule r_6 = if status = UNASSOCIATED and transition = RX_AARE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_7 = if status = UNASSOCIATED and transition = RX_RLRQ then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_8 = if status = UNASSOCIATED and transition = RX_RLRE then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_9 = if status = UNASSOCIATED and transition = RX_ABRT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_10 = if status = UNASSOCIATED and transition = RX_ROIV_CONFIRMED_EVENT_REPORT then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_11 = if status = WAITING_FOR_CONFIG and transition = REQ_ASSOC_REL then par status := DISASSOCIATING message := MSG_RX_RLRQ endpar endif
rule r_12 = if status = WAITING_FOR_CONFIG and transition = REQ_ASSOC_ABORT then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_13 = if status = WAITING_FOR_CONFIG and transition = RX_AARQ then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_14 = if status = WAITING_FOR_CONFIG and transition = RX_AARE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_15 = if status = WAITING_FOR_CONFIG and transition = RX_RLRQ then par status := UNASSOCIATED message := MSG_RX_RLRE endpar endif
rule r_16 = if status = WAITING_FOR_CONFIG and transition = RX_RLRE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_17 = if status = WAITING_FOR_CONFIG and transition = RX_ABRT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_18 = if status = WAITING_FOR_CONFIG and transition = RX_ROIV_CONFIRMED_EVENT_REPORT then par status := CHECKING_CONFIG message := MSG_NO_RESPONSE if ext_configurations=true then ext_configurations := false endif if new_measurement=false then new_measurement:= true endif endpar endif
rule r_19 = if status = WAITING_FOR_CONFIG and transition = RX_ROIV then par status := WAITING_FOR_CONFIG message := MSG_RX_PRST endpar endif
rule r_20 = if status = WAITING_FOR_CONFIG and transition = RX_ROER then par status := WAITING_FOR_CONFIG message := MSG_NO_RESPONSE endpar endif
rule r_21 = if status = WAITING_FOR_CONFIG and transition = RX_RORJ then par status := WAITING_FOR_CONFIG message := MSG_NO_RESPONSE endpar endif
rule r_22 = if status = CHECKING_CONFIG and transition = REQ_ASSOC_REL then par status := DISASSOCIATING message := MSG_RX_RLRQ new_measurement:=false endpar endif
rule r_23 = if status = CHECKING_CONFIG and transition = REQ_ASSOC_ABORT then par status := UNASSOCIATED message := MSG_RX_ABRT new_measurement:=false endpar endif
rule r_24 = if status = CHECKING_CONFIG and transition = RX_AARQ then par status := UNASSOCIATED message := MSG_RX_ABRT new_measurement:=false endpar endif
rule r_25 = if status = CHECKING_CONFIG and transition = RX_AARE then par status := UNASSOCIATED message := MSG_RX_ABRT new_measurement:=false endpar endif
rule r_26 = if status = CHECKING_CONFIG and transition = RX_RLRQ then par status := UNASSOCIATED message := MSG_RX_RLRE new_measurement:=false endpar endif
rule r_27 = if status = CHECKING_CONFIG and transition = RX_RLRE then par status := UNASSOCIATED message := MSG_RX_ABRT new_measurement:=false endpar endif
rule r_28 = if status = CHECKING_CONFIG and transition = RX_ABRT then par status := UNASSOCIATED message := MSG_NO_RESPONSE new_measurement:=false endpar endif
rule r_29 = if status = CHECKING_CONFIG and transition = RX_ROIV_CONFIRMED_EVENT_REPORT then par status := CHECKING_CONFIG message := MSG_RX_ABRT if new_measurement=true then new_measurement := false endif endpar endif
rule r_30 = if status = CHECKING_CONFIG and transition = RX_ROIV then par status := UNASSOCIATED message := MSG_RX_PRST new_measurement:=false endpar endif
rule r_31 = if status = CHECKING_CONFIG and transition = RX_ROER then par status := CHECKING_CONFIG message := MSG_NO_RESPONSE new_measurement:=false endpar endif
rule r_32 = if status = CHECKING_CONFIG and transition = RX_RORJ then par status := CHECKING_CONFIG message := MSG_NO_RESPONSE new_measurement:=false endpar endif
rule r_33 = if status = CHECKING_CONFIG and transition = REQ_AGENT_SUPPLIED_UNKNOWN_CONFIGURATION then par status := WAITING_FOR_CONFIG message := MSG_RX_PRST if ext_configurations=false then ext_configurations := true endif new_measurement:=false endpar endif
rule r_34 = if status = CHECKING_CONFIG and transition = REQ_AGENT_SUPPLIED_KNOWN_CONFIGURATION then par status := OPERATING message := MSG_RX_PRST new_measurement:=false endpar endif
rule r_35 = if status = OPERATING and transition = REQ_ASSOC_REL then par status := DISASSOCIATING message := MSG_RX_RLRQ endpar endif
rule r_36 = if status = OPERATING and transition = REQ_ASSOC_ABORT then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_37 = if status = OPERATING and transition = RX_AARQ then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_38 = if status = OPERATING and transition = RX_AARE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_39 = if status = OPERATING and transition = RX_RLRQ then par status := UNASSOCIATED message := MSG_RX_RLRE endpar endif
rule r_40 = if status = OPERATING and transition = RX_RLRE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_41 = if status = OPERATING and transition = RX_ABRT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_42 = if status = OPERATING and transition = RX_ROER then par status := OPERATING message := MSG_NO_RESPONSE endpar endif
rule r_43 = if status = OPERATING and transition = RX_RORJ then par status := OPERATING message := MSG_NO_RESPONSE endpar endif
rule r_44 = if status = DISASSOCIATING and transition = REQ_ASSOC_REL then par status := DISASSOCIATING message := MSG_NO_RESPONSE endpar endif
rule r_45 = if status = DISASSOCIATING and transition = REQ_ASSOC_ABORT then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_46 = if status = DISASSOCIATING and transition = RX_AARQ then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_47 = if status = DISASSOCIATING and transition = RX_AARE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_48 = if status = DISASSOCIATING and transition = RX_RLRQ then par status := DISASSOCIATING message := MSG_RX_RLRE endpar endif
rule r_49 = if status = DISASSOCIATING and transition = RX_RLRE then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_50 = if status = DISASSOCIATING and transition = RX_ABRT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_51 = if status = DISASSOCIATING and transition = RX_ROIV then par status := DISASSOCIATING message := MSG_NO_RESPONSE endpar endif
rule r_52 = if status = DISASSOCIATING and transition = RX_ROER then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_53 = if status = DISASSOCIATING and transition = RX_RORJ then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif

rule r_54 = if status = WAITING_FOR_CONFIG and transition = RX_RORS then par status := WAITING_FOR_CONFIG message := MSG_RX_ABRT endpar endif
rule r_55 = if status = CHECKING_CONFIG and transition = RX_RORS then par status := CHECKING_CONFIG message := MSG_NO_RESPONSE endpar endif
rule r_56 = if status = CHECKING_CONFIG and transition = RX_RORS_CONFIRMED_ACTION then par status := CHECKING_CONFIG message := MSG_NO_RESPONSE endpar endif
rule r_57 = if status = CHECKING_CONFIG and transition = RX_RORS_CONFIRMED_SET then par status := CHECKING_CONFIG message := MSG_NO_RESPONSE endpar endif
rule r_58 = if status = CHECKING_CONFIG and transition = RX_RORS_GET then par status := CHECKING_CONFIG message := MSG_RX_ABRT endpar endif
rule r_59 = if status = OPERATING and transition = RX_RORS then par status := OPERATING message := MSG_RX_ABRT endpar endif
rule r_60 = if status = OPERATING and transition = RX_RORS_CONFIRMED_ACTION then par status := OPERATING message := MSG_RX_ABRT endpar endif
rule r_61 = if status = OPERATING and transition = RX_RORS_CONFIRMED_SET then par status := OPERATING message := MSG_RX_ABRT endpar endif
rule r_62 = if status = OPERATING and transition = RX_RORS_GET then par status := OPERATING message := MSG_RX_ABRT endpar endif
rule r_63 = if status = DISASSOCIATING and transition = RX_RORS then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif


	main rule r_Main = par r_1[] r_2[] r_3[] r_4[] r_5[] r_6[] r_7[] r_8[] r_9[] r_10[] r_11[] r_12[] r_13[] r_14[] r_15[] r_16[] r_17[] r_18[] r_19[] r_20[] r_21[] r_22[] r_23[] r_24[] r_25[] r_26[] r_27[] r_28[] r_29[] r_30[] r_31[] r_32[] r_33[] r_34[] r_35[] r_36[] r_37[] r_38[] r_39[] r_40[] r_41[] r_42[] r_43[] r_44[] r_45[] r_46[] r_47[] r_48[] r_49[] r_50[] r_51[] r_52[] r_53[] r_54[] r_55[] r_56[] r_57[] r_58[] r_59[] r_60[] r_61[] r_62[] r_63[] endpar

// INITIAL STATE
default init s0:
	function status = UNASSOCIATED
	function transition = RX_ABRT
	function ext_configurations = false
	function new_measurement = false
