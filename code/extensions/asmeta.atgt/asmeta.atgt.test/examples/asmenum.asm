// an example with not

asm asmenum

import StandardLibrary

signature:
	// DOMAINS
	enum domain Status = {UNASSOCIATED | OPERATING | DISASSOCIATING}
	enum domain Transition = {REQ_ASSOC_REL | REQ_ASSOC_ABORT | RX_AARQ_ACCEPTABLE_AND_KNOWN_CONFIGURATION | RX_AARE | RX_RLRQ | RX_RLRE | RX_ABRT | RX_AARQ | RX_ROIV | RX_ROER | RX_RORJ |  RX_RORS | RX_RORS_CONFIRMED_ACTION | RX_RORS_CONFIRMED_SET | RX_RORS_GET}
	enum domain Message = {MSG_NO_RESPONSE | MSG_RX_AARE | MSG_RX_ABRT | MSG_RX_RLRQ | MSG_RX_RLRE | MSG_RX_PRST}
	// FUNCTIONS
	monitored status: Status
	monitored transition: Transition //row chosen by the user
	monitored message: Message
	

definitions:
	// DOMAIN DEFINITIONS
	
	main rule r_1 = skip

// INITIAL STATE
default init s0:
