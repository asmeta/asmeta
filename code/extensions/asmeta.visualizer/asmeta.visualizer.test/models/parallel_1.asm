// parallel rules 
asm parallel_1

import StandardLibrary

signature:
// DOMAINS

// FUNCTIONS

definitions:
// DOMAIN DEFINITIONS

// FUNCTION DEFINITIONS

// RULE DEFINITIONS


	// il test � terminato senza aver certificato un livello

	macro rule r_goOut1 = skip

	macro rule r_goOut2 = skip

	macro rule r_goOut = 
			par 
			r_goOut1[]
			r_goOut2[]
		endpar 
	

	macro rule r_goIn = skip
		

// MAIN RULE
	main rule r_Main =
		par 
			par 
			r_goOut1[]
			r_goOut2[]
		endpar 
			r_goOut[]
			r_goIn[]
		endpar 
			// 
// INITIAL STATE

default init s0:

