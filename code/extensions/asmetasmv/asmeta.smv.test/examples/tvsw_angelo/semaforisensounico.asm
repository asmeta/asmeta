// ANAS

asm semaforisensounico

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
	// DOMAINS
	domain Semaforo subsetof Integer
	domain StatoSemaforo subsetof Integer
	// FUNCTIONS
	//controlled stampa : Integer
    controlled sem0: Semaforo
	controlled sem1: Semaforo
	//controlled statoSem0: StatoSemaforo
	//controlled statoSem1: StatoSemaforo
	controlled statoSem: Semaforo -> StatoSemaforo
	monitored userSceglieSemaforo: Semaforo //row chosen by the user
	monitored userSceglieStatoSemaforo: StatoSemaforo //column chosen by the user
	static statiUguali : Prod(StatoSemaforo , StatoSemaforo) -> Boolean
	derived ritornaStato : Semaforo -> StatoSemaforo
	//derived mettiVerde : Semaforo -> Boolean
	derived mettiVerde :  Boolean
	derived mettiGiallo : Semaforo -> Boolean
	derived mettiRosso : Semaforo -> Boolean
definitions:
	// DOMAIN DEFINITIONS
	domain Semaforo = {0:1}
    domain StatoSemaforo = {0:2}
    
	// FUNCTION DEFINITIONS
	function statiUguali($s in StatoSemaforo, $s2 in StatoSemaforo) = (if $s = $s2 then true else false endif)
    //function ritornaStato($r in Semaforo) = (if $r = sem0 then statoSem0 else statoSem1 endif)
    //function mettiVerde = if statoSem0 = 2 and statoSem1 = 2 then true else endif
    function mettiVerde = (statoSem(sem0) = 2 and statoSem(sem1) = 2)
    
    
    /*function mettiGiallo($p in Semaforo) = ($p = sem0 and statoSem0 = 0) or
                                           ($p = sem1 and statoSem1 = 0)
    function mettiRosso($p in Semaforo) = if ($p = sem0 and statoSem0 = 1) then true else
                                          if ($p = sem1 and statoSem1 = 1) then true else false endif endif*/
                                          
	// RULE DEFINITIONS
    rule r_mettiVerde = if(mettiVerde) then   statoSem(userSceglieSemaforo) := 0 endif
    rule r_mettiGiallo = if statoSem(userSceglieSemaforo) = 0 then statoSem(userSceglieSemaforo) := 1 endif
    rule r_mettiRosso = if statoSem(userSceglieSemaforo) = 1 then statoSem(userSceglieSemaforo) := 2 endif
    //rule r_cambia =  statoSem0 := 0 
	// INVARIANTS
//	invariant inv_win over winner:  not(winner(CROSS) and winner(NOUGHT))
    invariant inv_arianteSem0 over sem0 : sem0 = 0 
    invariant inv_arianteSem1 over sem1 : sem1 = 1
	// MAIN RULE
//	main rule r_Main =
//		if not(endOfGame) then
//			if status = TURN_USER then
//				r_moveUser[]
//			else
//				r_movePC[]
//			endif
//		endif
CTLSPEC  ag(statoSem(sem0) = 0 implies statoSem(sem1) = 2)
//
    main rule r_Main = 
    //if (userSceglieSemaforo = 0  and userSceglieStatoSemaforo = 1) then r_cambia[] else 
    //if userSceglieStatoSemaforo = 0 then r_mettiVerde[] endif
    par
    	r_mettiVerde[]
    	r_mettiGiallo[]
    	r_mettiRosso[]
    endpar
   
   // if statiUguali(userSceglieStatoSemaforo,ritornaStato(userSceglieSemaforo)) then stampa:=print("true") else stampa:=print("false") endif endif

// INITIAL STATE
default init s0:
	function sem0 = 0
	function sem1 = 1
	//function statoSem0 = 2
	//function statoSem1 = 2
	function statoSem($p in Semaforo) = 2
