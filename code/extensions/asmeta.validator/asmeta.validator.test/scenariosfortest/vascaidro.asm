asm vascaidro

import StandardLibrary

signature:
	// DOMAINS
	domain Livello subsetof Integer
	// FUNCTIONS
	controlled statoLivello: Livello
	
	monitored riempi_completamente: Boolean
	monitored svuota_completamente: Boolean
	monitored riempi_25_percento: Boolean
	monitored svuota_25_percento: Boolean
	
definitions:
	// DOMAIN DEFINITIONS
	domain Livello = {0:100, 25}

	rule r_IncDec($b in Boolean) = if ($b) then statoLivello := statoLivello + 25 else statoLivello := statoLivello - 25 endif

	// MAIN RULE
	main rule r_Main =
		if(riempi_completamente)
		then statoLivello := 100
		else 	if(svuota_completamente)
				then statoLivello := 0
				else 	if(riempi_25_percento)
						then 	if(statoLivello + 25 > 100) 
								then skip
								else r_IncDec[true]
								endif
						else 	if(svuota_25_percento)
								then 	if(statoLivello - 25 < 0) 
										then skip
										else r_IncDec[false]
										endif
								else skip
								endif
						endif
				endif
		endif


// INITIAL STATE
default init s0:
	function statoLivello = 0

