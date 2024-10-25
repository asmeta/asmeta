asm myasm

import StandardLibrary

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

	rule r_RuleChiamata($l in Livello) =
		par
			if ($l >= 50)
			then statoLivello := 50
			else statoLivello := $l
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
