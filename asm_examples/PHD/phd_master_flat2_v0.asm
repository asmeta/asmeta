// a simple example with a tic tac toe game

asm phd_master_flat2_v0

import ../STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain Status = {UNASSOCIATED | OPERATING | DISASSOCIATING}
	enum domain Transition = {REQ_ASSOC_REL | REQ_ASSOC_ABORT | RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION | RX_AARE | RX_RLRQ | RX_RLRE | RX_ABRT | RX_AARQ | RX_ROIV | RX_ROER | RX_RORJ |  RX_RORS | RX_RORS_CONFIRMED_ACTION | RX_RORS_CONFIRMED_SET | RX_RORS_GET}
	enum domain Message = {MSG_NO_RESPONSE | MSG_RX_AARE | MSG_RX_ABRT | MSG_RX_RLRQ | MSG_RX_RLRE | MSG_RX_PRST}
	// FUNCTIONS
	controlled status: Status
	monitored transition: Transition //row chosen by the user
	controlled message: Message
	

definitions:
	// DOMAIN DEFINITIONS
	
	rule r_1 = if status = UNASSOCIATED and transition = REQ_ASSOC_REL then par status := UNASSOCIATED message := MSG_NO_RESPONSE	 endpar endif
rule r_2 = if status = UNASSOCIATED and transition = REQ_ASSOC_ABORT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_3 = if status = UNASSOCIATED and transition = RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION then par status := OPERATING message := MSG_RX_AARE endpar endif
rule r_6 = if status = UNASSOCIATED and transition = RX_AARE then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_7 = if status = UNASSOCIATED and transition = RX_RLRQ then par status := UNASSOCIATED message := MSG_RX_ABRT endpar endif
rule r_8 = if status = UNASSOCIATED and transition = RX_RLRE then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
rule r_9 = if status = UNASSOCIATED and transition = RX_ABRT then par status := UNASSOCIATED message := MSG_NO_RESPONSE endpar endif
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

	main rule r_Main = par r_1[] r_2[] r_3[] r_6[] r_7[] r_8[] r_9[] r_35[] r_36[] r_37[] r_38[] r_39[] r_40[] r_41[] r_42[] r_43[] r_44[] r_45[] r_46[] r_47[] r_48[] r_49[] r_50[] r_51[] r_52[] r_53[] endpar

// INITIAL STATE
default init s0:
	function status = UNASSOCIATED
	//function transition = RX_ABRT
