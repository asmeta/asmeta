// a simple example with a tic tac toe game

asm Ascensore

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary
import ../../STDL/LTLLibrary


signature:
	// DOMAINS
	enum domain Piano = {UNO | DUE}
	enum domain Porta = {CHIUSA | APERTA}
	// FUNCTIONS
	controlled statoPiano : Piano
	controlled statoPorta : Porta
	monitored signalPorta: Boolean
	

definitions:
	
	//Se il bottore viene premuto, nel prossimo stato la porta sar� aperta
	CTLSPEC ag(signalPorta implies ax(statoPorta = APERTA))
	
	//Prima o poi la porta si aprir�
	CTLSPEC ef(statoPorta = APERTA)
	
	//Quando la porta � aperta, rimane aperta fino a quando 
	//		signalPorta torna a false (se torna a false)
	CTLSPEC ag(statoPorta = APERTA implies aw(statoPorta = APERTA, not(signalPorta)))
	
	//Il piano nello stato successivo rimane lo stesso se 
	//se signalPorta � true 
	CTLSPEC ag((statoPiano = UNO and signalPorta implies ax(statoPiano = UNO)) or (statoPiano = DUE and signalPorta implies ax(statoPiano = DUE)))
	
	
	
	
	// MAIN RULE
	main rule r_Main =
		if signalPorta then
			if statoPiano = UNO and statoPorta = CHIUSA then
				par
					statoPiano := UNO
					statoPorta := APERTA
				endpar
			else
				if statoPiano = DUE and statoPorta = CHIUSA then
					par
						statoPiano := UNO
						statoPorta := APERTA
					endpar
				endif
			endif
		else
			if not signalPorta then
				par
				statoPorta := CHIUSA
				if statoPiano = UNO then
					statoPiano := DUE
				else 
					if statoPiano = DUE then
						statoPiano := UNO
					endif
				endif
				endpar
			endif
		endif
// INITIAL STATE
default init s0:
	function statoPiano = UNO
	function statoPorta = CHIUSA
