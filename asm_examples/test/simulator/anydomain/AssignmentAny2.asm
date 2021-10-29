// a simple example with an enum derived fron an AnyDomain
// it parses and simulates (use of AnyDomain as type)

asm AssignmentAny2
import ../../../STDL/StandardLibrary

signature:
	// DOMAINS
	anydomain MessageType
	enum domain Message = {NAK | NNK | NK}
	controlled protocolMessage: MessageType

definitions:

	// MAIN RULE
	main rule r_Main =
			protocolMessage:= NAK


// INITIAL STATE
default init s0:
