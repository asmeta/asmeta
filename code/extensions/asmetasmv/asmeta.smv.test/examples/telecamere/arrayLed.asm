// a simple example with a tic tac toe game

asm arrayLed

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:
	// DOMAINS
	domain Led subsetof Integer
	enum domain Stato = {ACCESO | SPENTO}
	
	
	// FUNCTIONS
	controlled stato : Led -> Stato // Mi d� lo stato di ogni led
	monitored scelta: Led
	
	derived acceso : Boolean 
		

definitions:
	// DOMAIN DEFINITIONS
	domain Led = {1..4}

	// FUNCTION DEFINITIONS
	function acceso = (forall $l in Led with stato($l) = ACCESO )
	

	// RULE DEFINITIONS
	rule r_cambiaStato($l in Led)  =
		if $l = 1 and stato(2) = SPENTO then 
			if stato($l) = SPENTO then
				stato($l) := ACCESO
			else
				stato($l) := SPENTO
			endif
		else
			if $l = 4 and stato(3) = ACCESO then
				if stato($l) = SPENTO then
					stato($l) := ACCESO
				else
					stato($l) := SPENTO
				endif
			else
				if $l > 1 and $l < 4 then
				    if stato($l-1) = ACCESO and stato($l+1) = SPENTO then 
						if stato($l) = SPENTO then
							stato($l) := ACCESO
						else
							stato($l) := SPENTO
						endif
					endif
				endif
			endif
		endif
			
	//CTL PROPERTIES
		CTLSPEC not ef(stato(1)=SPENTO)
		
		
		 
	// MAIN RULE
	main rule r_Main =
			r_cambiaStato[scelta]
			//choose $l in Led with true do r_cambiaStato[$l]
				

// INITIAL STATE
default init s0:
	function stato($l in Led) = SPENTO
