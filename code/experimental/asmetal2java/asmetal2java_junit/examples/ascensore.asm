// a simple example with a tic tac toe game

asm ascensore

import StandardLibrary


signature:
	// DOMAINS
	domain Piano subsetof Integer
	enum domain Porta = {CHIUSA | APERTA}
	controlled statoPiano: Piano
	controlled statoPorta: Porta
	monitored signalPorta: Boolean
	
	
	
definitions:
	
	domain Piano = {1 : 2}
	
	// MAIN RULE
	main rule r_Main =
		if statoPiano = 1 then 
			if signalPorta then
				statoPorta := APERTA
			else 
				par
					statoPorta := CHIUSA
					statoPiano := 2
				endpar
			endif
		else
			if signalPorta then
				statoPorta := APERTA
				else
					par
						statoPorta := CHIUSA
						statoPiano := 1
					endpar
					endif
			endif
			

// INITIAL STATE
default init s0:
	function statoPiano = 1
	function statoPorta = CHIUSA
