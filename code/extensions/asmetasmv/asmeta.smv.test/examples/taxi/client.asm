asm client

import ../../../../../../asm_examples/STDL/StandardLibrary
export *

signature:
	domain ClientAgent subsetof Agent
	enum domain State = {WAITING| TRAVELLING| IDLE| CALLTAXI}
	domain Durate subsetof Integer
	dynamic controlled state: ClientAgent -> State
	dynamic controlled durata_viaggio: ClientAgent -> Durate
	dynamic monitored calltaxi: ClientAgent -> Boolean
	static cl1: ClientAgent
	static cl2: ClientAgent
	static cl3: ClientAgent
	static gr2: ClientAgent
	static gr3: ClientAgent

definitions:
	domain Durate = {1..3}
	
	rule r_travel =
		if state(self)=TRAVELLING then
			if(durata_viaggio(self) = 1) then
				//par
					state(self) :=  IDLE
					//keepTaxi(self) := 0
				//endpar
			else
				durata_viaggio(self) := durata_viaggio(self) - 1
			endif
		endif
	
	rule r_idle = 
		if state(self)=IDLE then
			if(calltaxi(self)) then
				state(self) := CALLTAXI
			endif
		endif
	
	rule r_callTaxi = 
		if state(self)=CALLTAXI then
			state(self) := WAITING
		endif
	
	rule r_ClientAgent =
		par
			r_idle[]
			r_travel[]
			r_callTaxi[]
		endpar

default init s0:
