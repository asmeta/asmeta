// a simple example with a dice game

asm DiceGame // Dado

import STDL/StandardLibrary

signature:
	// DOMAINS
	enum domain Giocatore = {USER | PC}
	enum domain Risultato = {WINUSER | WINPC | PATTA}
	enum domain StatoGioco = {FINE | INCORSO}
	domain NumSoldi subsetof Integer 
	domain FacceDado subsetof Integer
	
	monitored lancioUser: FacceDado
	monitored lancioPC: FacceDado
	
	controlled soldi: Giocatore -> NumSoldi
	controlled risultatoGiocata: Risultato
	controlled statoGioco: StatoGioco
	controlled vincitore: Giocatore
	
	static risultato: Prod(FacceDado,FacceDado) -> Risultato
	
	derived inRange: Giocatore -> Boolean
	
definitions:
	domain NumSoldi = {0 : 20}
	domain FacceDado = {1 : 6}
	
	function risultato($u in FacceDado, $p in FacceDado) =
		if($u > $p) then WINUSER
		else if($u < $p) then WINPC
			else PATTA
			endif
		endif
		
	function inRange($g in Giocatore) =
		0 <= soldi($g) and soldi($g) <= 20	

	
	// MAIN RULE
	main rule r_Main = 
		if(soldi(USER) = 0 or soldi(PC) = 0) then
			par
				statoGioco := FINE
				if(soldi(USER) = 0) then
					vincitore := PC
				else
					vincitore := USER
				endif
			endpar
		else
			par
				statoGioco := INCORSO
				risultatoGiocata := risultato(lancioUser,lancioPC)
				if(risultato(lancioUser,lancioPC) = WINUSER) then
					par
						soldi(USER) := soldi(USER) + 1
						soldi(PC) := soldi(PC) - 1
					endpar
				else if(risultato(lancioUser,lancioPC) = WINPC) then
					par
						soldi(USER) := soldi(USER)  -1
						soldi(PC) := soldi(PC) + 1
					endpar
				endif
			endif
		endpar
	endif
// INITIAL STATE
default init s0:
	function soldi($g in Giocatore) = 10
	function statoGioco = INCORSO
