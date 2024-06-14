asm ArmadioCaramelle2

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:

	// DOMAINS
	
	domain Caramelle subsetof Integer
	domain Aggiunta subsetof Integer
	domain Armadio subsetof Integer
	enum domain Scelta = {PRELEVA | AGGIUNGI}
	
	// FUNCTIONS
	
	controlled quantity: Armadio -> Caramelle
	monitored scelta: Scelta
	monitored slot: Armadio

definitions:
	// DOMAIN DEFINITIONS
	domain Caramelle = {0:10}
	domain Armadio = {0:6}
	domain Aggiunta = {1 : 6}

    // propreit� falsa: se prelevo e sono a 10 rimango a 10 
    CTLSPEC  ag((scelta = PRELEVA and quantity(slot) = 10) implies ax(quantity(slot) = 10))

	// MAIN RULE
	main rule r_Main =
		if scelta = PRELEVA then
			if quantity(slot) > 0 then
				quantity(slot) := quantity(slot) - 1
			endif
		else
			// AGGIUNGI
			if quantity(slot) < 10 then
				quantity(slot) := quantity(slot) + 1
			endif
		endif
	
// INITIAL STATE
default init s0:
	function quantity($a in Armadio) = 0
