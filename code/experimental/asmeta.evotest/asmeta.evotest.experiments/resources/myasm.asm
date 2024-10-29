asm myasm

import StandardLibrary
import importedAsm

signature:
	// DOMAINS
	domain Livello subsetof Integer
	// FUNCTIONS
	controlled statoLivello: Livello

	monitored impostaDefault: Boolean
	monitored quantita: Livello

definitions:
	// DOMAIN DEFINITIONS
	domain Livello = {0:100}
	
	rule r_RuleChiamata($i in Integer, $s in String) =
		skip
		
	rule r_RuleChiamata($i in String, $s in String) =
		skip

	rule r_RuleChiamata($l in Livello) =
		par
			if ($l >= 50)
			then
				par
					statoLivello := 50
					r_RuleChiamata[true]
				endpar
			else
				par
					statoLivello := $l
					r_RuleChiamata[false]
				endpar
			endif
			skip
		endpar

	// MAIN RULE
	main rule r_Main =
		if(impostaDefault)
		then r_RuleChiamata[5]
		else r_RuleChiamata[quantita]
		endif

// INITIAL STATE
default init s0:
	function statoLivello = 0
