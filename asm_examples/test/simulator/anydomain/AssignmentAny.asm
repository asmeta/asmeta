// a simple example with an enum derived fron an AnyDomain
// pareses but it fails because   MessageType as subset is not defined

asm AssignmentAny
import ../../../STDL/StandardLibrary

signature:
	// DOMAINS
	domain MessageType subsetof Any
	enum domain Message = {NAK | NNK | NK}
	controlled protocolMessage: MessageType

definitions:


	// MAIN RULE
	main rule r_Main =
			protocolMessage := NAK


// INITIAL STATE
default init s0:
