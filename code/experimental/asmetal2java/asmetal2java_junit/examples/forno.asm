// a simple example with a tic tac toe game

asm forno

import StandardLibrary



signature:
	// DOMAINS
	enum domain Stato = {ACCESO | SPENTO}
	enum domain StatoPorta = {APERTA | CHIUSA}
	
	monitored segnalePorta: Boolean
	monitored accendiForno: Boolean
	controlled statoForno: Stato
	controlled statoPorta: StatoPorta
	

definitions:


	// MAIN RULE
	main rule r_Main =
	par	
		if statoForno = SPENTO and statoPorta = CHIUSA and segnalePorta then
			statoPorta := APERTA
		endif
		if statoForno = SPENTO and segnalePorta = false then
			statoPorta := CHIUSA
		endif
		if statoForno = SPENTO and statoPorta = CHIUSA and accendiForno and segnalePorta = false then
			statoForno := ACCESO
		endif
		if statoForno = ACCESO and accendiForno = false then
			statoForno := SPENTO
		endif
	endpar
		 
// INITIAL STATE
default init s0:
	function statoForno = SPENTO
	function statoPorta = CHIUSA
