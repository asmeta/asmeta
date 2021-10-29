//

asm AssignAnyFromLib
import ../../../STDL/StandardLibrary
import Lib_Test
signature:
	// DOMAINS
	//domain Messagetype subsetof Any
	//anydomain MessageType
	enum domain Message = {NAK | NNK | NK}
	//controlled protocolMessage: Messagetype
definitions:
	domain Messagetype={NAK , NNK}
	
	
	// MAIN RULE
	main rule r_Main =		
			protocolMessage:= NAK
			

// INITIAL STATE
default init s0:
	
