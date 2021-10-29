// a simple example with an enum derived fron an AnyDomain
// it parses and simulates - it is simulated

asm AssignmentAny3
import ../../../STDL/StandardLibrary

signature:
	// DOMAINS
	domain MessageType subsetof Any
	enum domain Message = {NAK | NNK | NK}
	controlled protocolMessage: MessageType

definitions:
    domain MessageType =  {NAK, NNK, NK}


	// MAIN RULE
	main rule r_Main =
			protocolMessage:= NAK


// INITIAL STATE
default init s0:
