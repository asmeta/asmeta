asm GiocoDelCento

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
	derived giocataBuona: Boolean
	derived controllaVincita: Players -> Boolean
	derived opponent: Players -> Players
	dynamic controlled outMess: String	
	

definitions:
	
	//la funzione derivata "opponent" assegna ad un giocatore il suo avversario
	function opponent($p in Players) =
		if($p = PLAYER1) then
			PLAYER2
		else
			PLAYER1
		endif
	
	//la funzione "giocataBuona" mi serve per capire se la coppia di numeri forniti rispetta i vincoli
	function giocataBuona =
		if(game > 0) then
			((game - ultimaGiocata) <= distanza and game > ultimaGiocata)
		endif
			
	
	//la regola r_assegnaVincita prende in ingresso un player e cambia il suo stato di vincita da NONE a WIN
	rule r_assegnaVincita($p in Players)=
		statoVincita($p) := WIN
		
			
	//la regola r_assegnaSconfitta è la duale della regola precendete. Essa prende in ingresso un player
	//e commuta il suo stato di vincita da NONE a LOSE
	rule r_assegnaSconfitta($p in Players)=
		statoVincita($p) := LOSE
		
		
	//con questa regola verifico se il PLAYER raggiunge l'obiettivo e nel caso assegno la vittoria a quest'ultimo.
	//Tale regola prende in ingresso tutti e due i player, così facendo se fosse verificata la condizione "$g = obiettivo"
	//riuscirei ad assegnare la sconfitta all'altro Player. 
	rule r_controllaVincita($g in Giocata, $p in Players, $q in Players) =
		if($g = obiettivo) then
			par
				r_assegnaVincita[$p]
				r_assegnaSconfitta[$q]
			endpar
		else
			turno := opponent(turno) //ponendo qui il "cambio turno", nel caso ci fosse un vincitore, lo stato finale di turno rimarrà 
		endif			//su colui che ha fatto la giocata vincente.

	
	
	macro rule r_assegnaGiocata($p in Players, $q in Players) =
		if(giocataBuona = true) then
			par
				ultimaGiocata := game
				r_controllaVincita[game,$p,$q]
			endpar
		else
			outMess:= "Hai inserito un numero non valido"
		endif

	rule r_giocata =
		r_assegnaGiocata[turno,opponent(turno)]

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