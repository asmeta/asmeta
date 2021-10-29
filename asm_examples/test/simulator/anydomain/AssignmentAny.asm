// a simple example with an enum derived fron an AnyDomain
// it fails - bug reported by MarioLilli oct 2021
asm AssignmentAny
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
