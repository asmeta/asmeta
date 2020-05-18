asm FApplicSelf

import ../../../STDL/StandardLibrary

//declare universes and functions

	
signature:
		
    enum domain Phasetransport = {NORMALRUN | CRITICALRUN | STOPPED | STOPPEDINLOADPOS}  
		
// Agent "TRANSPORT"
	domain TransportAgent subsetof Agent 
	
	controlled currphaseTra:  TransportAgent -> Phasetransport
	controlled f: Prod(Integer,TransportAgent)->Boolean
	
// THE AGENTS

// Agent "TRANSPORT"
	static transport : TransportAgent  
	
// Another generic agent 
   static a:Agent	
  

definitions:

//Rules for Agent TRANSPORT

//Rule that defines the NormalRun

	rule r_FbNormal =
		if currphaseTra (self) = NORMALRUN then skip //OK! also f(1,self) is OK!
		//else if currphaseTra (a) = NORMALRUN then skip //WRONG! The parser should not accept "currphaseTra(a)".
		else if f(1,a) then skip //WRONG! The parser should not accept "f(1,a)".
		endif endif //endif

default init initial_state:

//AGENTS initialization

agent TransportAgent : 	r_FbNormal []
		