asm monProblem

//il problema e' stato risolto
//lascio per documentazione

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain Dom subsetof Integer
	dynamic monitored mon1: Dom
	dynamic monitored mon2: Dom

definitions:
	domain Dom ={1:10}

	main rule r_Main =
		if(mon1=3 and mon2=4) then //NullPointerException su mon2
			skip
		endif
		
default init s0:
