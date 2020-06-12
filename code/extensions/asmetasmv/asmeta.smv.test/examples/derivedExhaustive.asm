/*
La seguente specifica derivedExhaustive.asm, invece, essendo completamente specificata
non genera nessun errore in esecuzione di NuSMV.
Negli esempi asm riportati era relativamente semplice scoprire l'errore anche senza la traduzione in
NuSMV.  
*/

asm derivedExhaustive

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo : MyDomain
	dynamic monitored mon1: Boolean
	dynamic monitored mon2: Boolean
	derived der_func: Boolean

definitions:
	domain MyDomain = {1..4}

	function der_func =
			if(mon1) then
				if(mon2) then
					true
				else
					false
				endif
			else
				true
			endif

	//Proprieta' CTL
	CTLSPEC ag((not(mon1) or (mon1 and mon2)) iff der_func)
	CTLSPEC ag((mon1 and not(mon2)) iff not(der_func))
	
	main rule r_Main =
		if(der_func) then
			foo := 1
		endif
