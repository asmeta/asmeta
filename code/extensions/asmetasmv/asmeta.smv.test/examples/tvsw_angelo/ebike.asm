asm ebike

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
    	// definisce gli stati possibili della bicicletta
    enum domain Bicicletta = {DISPONIBILE, USO, MANUTENZIONE}
    // definisce gli stati possibili del led
    	enum domain Led = {VERDE, ROSSO, SPENTO}
    	enum domain SceltaUtente = {UTILIZZO, RESTITUZIONE}
    	domain Autonomia subsetof Integer
    	domain NumeroRastrelliere subsetof Integer

    	controlled autonomiaBicicletta: NumeroRastrelliere -> Autonomia
    	controlled led: NumeroRastrelliere -> Led
    	controlled stato: NumeroRastrelliere -> Bicicletta
    	controlled inRiparazione: NumeroRastrelliere -> Boolean
    	monitored rastrellieraScelta: NumeroRastrelliere
    	monitored segnalaErrore: Boolean
    	monitored scelta: SceltaUtente
    	monitored distanzaPercorsa: Autonomia

definitions:
    	domain Autonomia = {0:100}
    	domain NumeroRastrelliere = {0:5}
	
    	macro rule r_set_manutenzione =
    	    forall $r in NumeroRastrelliere do
    	        if (autonomiaBicicletta($r) < 10 and not inRiparazione($r)) then
    	            par
    	                led($r) := ROSSO
    	                stato($r) := MANUTENZIONE
    	                inRiparazione($r) := true
    	            endpar
endif

    	macro rule r_ripristina_bici =
    	    forall $r in NumeroRastrelliere do
    	        if (inRiparazione($r)) then
    	            par
    	                autonomiaBicicletta($r) := 100
    	                stato($r) := DISPONIBILE
    	                led($r) := VERDE
    	                inRiparazione($r) := false
    	            endpar
endif

    	macro rule r_set_uso($r in NumeroRastrelliere) =
    	    if (stato($r) = DISPONIBILE) then
    	        par
    	            stato($r) := USO
    	            led($r) := SPENTO
    	        endpar
endif

    	macro rule r_assegna_bici($d in Autonomia) =
    	    choose $r in NumeroRastrelliere with (stato($r) = DISPONIBILE and autonomiaBicicletta($r) >= $d) do
    	        r_set_uso[$r]
	
	//verifica che quando la bicicletta è in manutenzione, il led è rosso.
	CTLSPEC ag(stato(0) = MANUTENZIONE implies led(0) = ROSSO)
	CTLSPEC ag(stato(1) = MANUTENZIONE implies led(1) = ROSSO)
	CTLSPEC ag(stato(2) = MANUTENZIONE implies led(2) = ROSSO)
	CTLSPEC ag(stato(3) = MANUTENZIONE implies led(3) = ROSSO)
	CTLSPEC ag(stato(4) = MANUTENZIONE implies led(4) = ROSSO)
	CTLSPEC ag(stato(5) = MANUTENZIONE implies led(5) = ROSSO)
	
	//verifica che quando la bicicletta è in manutenzione, non può essere in uso.
	CTLSPEC ag(stato(0) = MANUTENZIONE implies not(stato(0) = USO))
	CTLSPEC ag(stato(1) = MANUTENZIONE implies not(stato(1) = USO))
	CTLSPEC ag(stato(2) = MANUTENZIONE implies not(stato(2) = USO))
	CTLSPEC ag(stato(3) = MANUTENZIONE implies not(stato(3) = USO))
	CTLSPEC ag(stato(4) = MANUTENZIONE implies not(stato(4) = USO))
	CTLSPEC ag(stato(5) = MANUTENZIONE implies not(stato(5) = USO))
	
    	main rule r_main =
    	    par
    	        r_ripristina_bici[]
    	        if (scelta != RESTITUZIONE) then
    	            r_set_manutenzione[]
    	        endif
    	        if (scelta = UTILIZZO) then
    	            r_assegna_bici[distanzaPercorsa]
    	        else if (scelta = RESTITUZIONE and stato(rastrellieraScelta) = USO) then
    	            if (segnalaErrore) then
    	                par
    	                    stato(rastrellieraScelta) := MANUTENZIONE
    	                    led(rastrellieraScelta) := ROSSO
    	                    inRiparazione(rastrellieraScelta) := true
    	                endpar
	else
            	        par
            	            	autonomiaBicicletta(rastrellieraScelta) := 
            if (autonomiaBicicletta(rastrellieraScelta) - distanzaPercorsa > 0) then
                autonomiaBicicletta(rastrellieraScelta) - distanzaPercorsa
            else
                0
            endif
            	            	stato(rastrellieraScelta) := DISPONIBILE
            	            	led(rastrellieraScelta) := VERDE
            	        	endpar
	endif
            	endif
            	endif
        	endpar
default init s0:
    	function stato($r in NumeroRastrelliere) = DISPONIBILE
    	function autonomiaBicicletta($r in NumeroRastrelliere) = 100
    	function led($r in NumeroRastrelliere) = VERDE
    	function inRiparazione($r in NumeroRastrelliere) = false