asm arrayLedSimple

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain Led subsetof Integer
	enum domain Stato = {ACCESO | SPENTO}
	
	controlled stato : Led -> Stato
	monitored scelta: Led

definitions:
	domain Led = {1..2}

	rule r_cambiaStato($l in Led)  =
		if $l > 1 then
		    if stato($l-1) = ACCESO then 
				stato($l) := ACCESO
			endif
		endif
			
	main rule r_Main =
		r_cambiaStato[scelta]

default init s0:
	function stato($l in Led) = SPENTO
