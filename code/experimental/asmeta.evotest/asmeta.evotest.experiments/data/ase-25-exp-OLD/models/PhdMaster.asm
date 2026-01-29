asm PhdMaster //Phd_master_flat2_v1

import STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain Status = {UNASSOCIATED | OPERATING | DISASSOCIATING}
	enum domain Transition = {REQ_ASSOC_REL | REQ_ASSOC_ABORT | RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION | RX_AARE | RX_RLRQ | RX_RLRE | RX_ABRT | RX_AARQ | RX_ROIV | RX_ROER | RX_RORJ} //new -> RX_ROIV | RX_ROER | RX_RORJ
	enum domain Message = {MSG_NO_RESPONSE | MSG_RX_AARE | MSG_RX_ABRT | MSG_RX_RLRQ | MSG_RX_RLRE}
	// FUNCTIONS
	controlled status: Status
	monitored transition: Transition //row chosen by the user
	controlled message: Message
	

definitions:
	// DOMAIN DEFINITIONS
	
	rule r_Unassociated =
		switch transition
			case REQ_ASSOC_REL:
				par
					status := UNASSOCIATED 
					message := MSG_NO_RESPONSE
				endpar
			case REQ_ASSOC_ABORT:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION:
				par
					status := OPERATING 
					message := MSG_RX_AARE
				endpar
			case RX_AARE:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_RLRQ:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_RLRE:
				par
					status := UNASSOCIATED 
					message := MSG_NO_RESPONSE
				endpar
			case RX_ABRT:
				par
					status := UNASSOCIATED 
					message := MSG_NO_RESPONSE
				endpar
		endswitch
	
	rule r_Operating =
		switch transition
			case REQ_ASSOC_REL:
				par
					status := DISASSOCIATING 
					message := MSG_RX_RLRQ
				endpar
			case REQ_ASSOC_ABORT:
				par
					status := UNASSOCIATED
					message := MSG_RX_ABRT
				endpar
			case RX_AARQ:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_AARE:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_RLRQ:
				par
					status := UNASSOCIATED
					message := MSG_RX_RLRE
				endpar
			case RX_RLRE:
				par
					status := UNASSOCIATED
					message := MSG_RX_ABRT
				endpar
			case RX_ABRT:
				par
					status := UNASSOCIATED
					message := MSG_NO_RESPONSE
				endpar
			case RX_ROER:
				par
					status := OPERATING
					message := MSG_NO_RESPONSE
				endpar
			case RX_RORJ:
				par
					status := OPERATING 
					message := MSG_NO_RESPONSE
				endpar
		endswitch
		
	rule r_Disassociating =
		switch transition
			case REQ_ASSOC_REL:
				par
					status := DISASSOCIATING 
					message := MSG_NO_RESPONSE
				endpar
			case REQ_ASSOC_ABORT:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_AARQ:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_AARE:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_RLRQ:
				par
					status := DISASSOCIATING 
					message := MSG_RX_RLRE
				endpar
			case RX_RLRE:
				par
					status := UNASSOCIATED 
					message := MSG_NO_RESPONSE
				endpar
			case RX_ABRT:
				par
					status := UNASSOCIATED 
					message := MSG_NO_RESPONSE
				endpar
			case RX_ROIV:
				par
					status := DISASSOCIATING 
					message := MSG_NO_RESPONSE
				endpar
			case RX_ROER:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
			case RX_RORJ:
				par
					status := UNASSOCIATED 
					message := MSG_RX_ABRT
				endpar
		endswitch

	INVAR (status = DISASSOCIATING) implies (transition = REQ_ASSOC_REL or transition = REQ_ASSOC_ABORT
		or transition = RX_AARQ or transition = RX_AARE or transition = RX_RLRQ or transition = RX_RLRE
		or transition = RX_ABRT or transition = RX_ROIV or transition = RX_ROER or transition = RX_RORJ)

	INVAR (status = OPERATING) implies (transition = REQ_ASSOC_REL or transition = REQ_ASSOC_ABORT
		or transition = RX_AARQ or transition = RX_AARE or transition = RX_RLRQ or transition = RX_RLRE
		or transition = RX_ABRT or transition = RX_ROER or transition = RX_RORJ)

	INVAR (status = UNASSOCIATED) implies (transition = REQ_ASSOC_REL or transition = REQ_ASSOC_ABORT
		or transition = RX_AARE or transition = RX_RLRQ or transition = RX_RLRE
		or transition = RX_ABRT or transition = RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION)


	main rule r_Main = 
		par
			if (status = UNASSOCIATED) then
				r_Unassociated[]
			endif 
			if (status = OPERATING) then
				r_Operating[] 
			endif
			if (status = DISASSOCIATING) then
				r_Disassociating[] 
			endif
		endpar
		
		
		
// INITIAL STATE
default init s0:
	function status = UNASSOCIATED
	//function transition = RX_ABRT
