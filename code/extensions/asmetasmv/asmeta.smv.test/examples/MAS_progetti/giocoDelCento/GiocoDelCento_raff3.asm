asm GiocoDelCento_raff3

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLlibrary

signature:
	
	//DICHIARAZIONI DOMINI
	enum domain Players = { PLAYER1 | PLAYER2 | NULL}
	enum domain WinDomain = { WIN | LOSE | NONE}
	domain Giocata subsetof Integer
	
	//DICHIARAZIONI FUNZIONI DINAMICHE
	//dynamic controlled mance: Natural
	dynamic controlled mance: Boolean
	dynamic controlled gamePc: Giocata
	dynamic controlled ultimaGiocataPc:Players -> Giocata
	//dynamic controlled obiettivo: Integer
	//dynamic controlled distanza: Integer
	dynamic controlled obiettivo: Giocata
	dynamic controlled distanza: Giocata
	dynamic controlled turno: Players
	dynamic controlled strategiaVincente: Players
	dynamic controlled statoVincita: Players -> WinDomain
	
	//DICHIARAZIONI FUNZIONI DERIVATE
	derived giocataBuona: Prod(Giocata, Giocata) -> Boolean
	derived controllaVincita: Players -> Boolean
	derived opponent: Players -> Players
	

definitions:
	domain Giocata = {1..100}
	
	//la funzione derivata "opponent" assegna ad un giocatore il suo avversario
	function opponent($p in Players) =
		if($p = PLAYER1) then
			PLAYER2
		else
			PLAYER1
		endif
	
		
		
	//la funzione "giocataBuona" mi serve per capire se la coppia di numeri forniti rispetta i vincoli
	function giocataBuona($g in Giocata, $u in Giocata) =
	($g > 0 and ($g - ultimaGiocataPc(opponent(turno))) <= distanza and $g > ultimaGiocataPc(opponent(turno)))

			
	
	//la regola r_assegnaVincita prende in ingresso un player e cambia il suo stato di vincita da NONE a WIN
	rule r_assegnaVincita($p in Players)=
		statoVincita($p) := WIN
		

		
	//la regola "r_assegnaSconfitta" è la duale della regola precendete. Essa prende in ingresso un player
	//e commuta il suo stato di vincita da NONE a LOSE
	rule r_assegnaSconfitta($p in Players)=
		statoVincita($p) := LOSE
		
		
	
	//************************************
	//**REGOLE CHE GESTISCONO LA GIOCATA**
	//************************************
		
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

	
		
	//con questa regola faccio scegliere in modo NON deterministico al player che NON può implementare la strategia
	//vincente un numero casuale che soddisfi i vincoli imposti nella funzione "giocataBuona"
	rule r_sceltaNumero =
		choose $g in {1 .. 100} with (giocataBuona($g, ultimaGiocataPc(opponent(turno))) = true) do
			par
				ultimaGiocataPc(turno) := $g
				turno := opponent(turno)
				//mance := mance + 1n
			endpar	
			
		
			
	//Se la mance giocata è la prima chiamo questa regola, la quale verifica a quale giocatore deve essere assegnata
	//la strategia vincente, in base alla decisione gli assegno la giocata (quindi se quella appartenente all'insieme
	//delle giocata vincenti o una giocata casuale)
	rule r_controllaStrategiaVincente =
		if(gamePc <= distanza and gamePc != 0) then
			par
				ultimaGiocataPc(turno) := gamePc
				turno := opponent(turno)
				//mance := mance + 1n
				strategiaVincente := turno
			endpar
		else
			par
				strategiaVincente := opponent(turno)
				r_sceltaNumero[]
			endpar
		endif
	
		
		
	//questa regola viene chiamata se la mance non è più la prima e assegna la giocata al Player che è di turno
	//in base a chi è stata assegnata la strategia vincente
	rule r_applicaStrategia($p in Players) =
		if(strategiaVincente = $p) then
			par
				ultimaGiocataPc(turno) := ultimaGiocataPc(turno) + distanza + 1
				r_controllaVincita[ultimaGiocataPc(turno)+distanza+1,$p,opponent($p)]
				//mance := mance + 1n
			endpar
		else
			r_sceltaNumero[]
		endif
			               
		
	
	macro rule r_assegnaGiocataPc =
		//if(mance = 1n) then
		if(mance) then
			par
				mance := false
				r_controllaStrategiaVincente[]
			endpar
		else
			r_applicaStrategia[turno]
		endif
		
	
	
	rule r_giocata =
		r_assegnaGiocataPc[]
		

		                   
//	axiom over ctl: ag(statoVincita(PLAYER2)=WIN and statoVincita(PLAYER2)=LOSE)
		
		                   
		                   
	//Main rule
	main rule r_Main =
		if(statoVincita(PLAYER1) = NONE and statoVincita(PLAYER2) = NONE) then
			r_giocata[]
		endif
	
		
		
default init s0:
	function turno = PLAYER1
	//function mance = 1n
	function mance = true
	function gamePc = obiettivo mod (distanza + 1)
	function distanza = 10 		//10 - 10 - 9
	function obiettivo = 100	//99 - 60 - 97
	function ultimaGiocataPc($p in Players) = 0
	function statoVincita($p in Players) = NONE
