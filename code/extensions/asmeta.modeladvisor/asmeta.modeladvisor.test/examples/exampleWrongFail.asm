// a simple example with a tic tac toe game

asm exampleWrongFail

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain Status = {UNASSOCIATED | OPERATING | DISASSOCIATING}
	enum domain Transition = {REQ_ASSOC_ABORT | RX_RLRE | RX_ABRT }
	enum domain Message = {MSG_NO_RESPONSE | MSG_RX_ABRT }
	// FUNCTIONS
	controlled status: Status
	monitored transition: Transition //row chosen by the user
	controlled message: Message
	derived endOfGame: Boolean

definitions:
	// DOMAIN DEFINITIONS
	
	function endOfGame = (status = OPERATING)

	rule r_8 =
		if status = UNASSOCIATED then
			par
				status := UNASSOCIATED
				message := MSG_NO_RESPONSE
			endpar
		endif

	rule r_45 =
		if status = DISASSOCIATING then
			par
				status := UNASSOCIATED
				message := MSG_RX_ABRT
			endpar
		endif

	main rule r_Main =
		par
			r_8[]
			r_45[]
		endpar

// INITIAL STATE
default init s0:
	function status = UNASSOCIATED
	function transition = RX_ABRT
