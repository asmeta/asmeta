asm GiocoDelCento_3

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain Players = { PLAYER1 | PLAYER2 }
	enum domain WinDomain = { WIN | LOSE | NONE}
	domain Giocata subsetof Integer
	dynamic monitored game: Giocata
	dynamic controlled ultimaGiocata: Giocata
	dynamic controlled obiettivo: Integer
	dynamic controlled distanza: Integer
	dynamic controlled turno: Players
	dynamic controlled statoVincita: Players -> WinDomain
	derived giocataBuona: Prod(Giocata, Giocata) -> Boolean
	derived controllaVincita: Players -> Boolean
	dynamic controlled outMess: String	
	

definitions:
	//la funzione "giocataBuona" mi serve per capire se la coppia di numeri forniti rispetta i vincoli
	function giocataBuona($p1_i in Giocata, $p1_ii in Giocata) =
		($p1_ii > 0 implies (($p1_ii - $p1_i) <= distanza and $p1_ii > $p1_i))
	
	
	//la regola r_assegnaVincita prende in ingresso un player e cambia il suo stato di vincita da NONE a WIN
	rule r_assegnaVincita($p in Players)=
		if(statoVincita($p) = NONE) then
			statoVincita($p) := WIN
		endif
			
	//la regola r_assegnaSconfitta è la duale della regola precendete. Essa prende in ingresso un player
	//e commuta il suo stato di vincita da NONE a LOSE
	rule r_assegnaSconfitta($p in Players)=
		if(statoVincita($p) = NONE) then
			statoVincita($p) := LOSE
		endif
		
	//con questa regola verifico se il PLAYER1 raggiunge l'obiettivo e nel caso assegno la vittoria a quest'ultimo
	rule r_controllaVincitaP1 =
		if(ultimaGiocata = obiettivo) then
			par
				outMess := "Player1 hai vinto"
				r_assegnaVincita[PLAYER1]
				r_assegnaSconfitta[PLAYER2]
			endpar
		endif

	//con questa regola verifico se il PLAYER2 raggiunge l'obiettivo e nel caso assegno la vittoria a quest'ultimo
	rule r_controllaVincitaP2 =
		if(ultimaGiocata = obiettivo) then
			par
				outMess := "Player2 hai vinto"
				r_assegnaVincita[PLAYER2]
				r_assegnaSconfitta[PLAYER1]
			endpar
		endif
	
	//PER ORA LE DUE REGOLE CHE CONTROLLANO LA VINCITA RIMANGONO SEPARATE. UNO DEI RAFFINAMENTI POSSIBILI
	//DI QUESTO MODELLO POTREBBE PREVEDERE L'UNIONE DI QUESTE DUE REGOLE PER OTTENERNE UNA SOLA CHE CONTROLLA LE GIOCATE.		
	
	rule r_giocata =
		if (game > 0) then
			if(turno = PLAYER1) then
				if(giocataBuona(ultimaGiocata, game) = true) then
					seq
						ultimaGiocata := game
						r_controllaVincitaP1[]
						turno := PLAYER2
					endseq
				else
					outMess:= "Hai inserito un numero maggiore della distanza consentita"
				endif
			else
				if(giocataBuona(ultimaGiocata, game) = true) then
					seq
						ultimaGiocata := game
						r_controllaVincitaP2[]
						turno := PLAYER1
					endseq
				else
					outMess:= "Hai inserito un numero maggiore della distanza consentita"    
				endif
			endif
		else 
			outMess := " Non puoi inserire lo ZERO, prego inserire un altro numero:"
		endif
	
	
	//Gli assiomi devono essere rivisti e approfonditi	
	invariant over statoVincita: ef(statoVincita(PLAYER1)=WIN and statoVincita(PLAYER2)=LOSE)
	invariant over statoVincita: ef(statoVincita(PLAYER2)=WIN and statoVincita(PLAYER1)=LOSE)
		
		
	main rule r_Main =
		if(statoVincita(PLAYER1) = NONE and statoVincita(PLAYER2) = NONE) then
			r_giocata[]
		endif
	
default init s0:
	function turno = PLAYER1
	function distanza = 10
	function obiettivo = 100
	function ultimaGiocata = 0
	function statoVincita($p in Players) = NONE
	function outMess = " "
