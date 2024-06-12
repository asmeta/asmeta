asm SMV_GiocoDelCento_raff3_mod_Paolo

import ../../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../../asm_examples/STDL/CTLLibrary

signature:
	
	//DICHIARAZIONI DOMINI
	enum domain Players = { PLAYER1 | PLAYER2 | NULL}
	enum domain WinDomain = { WIN | LOSE | NONE}
	domain Giocata subsetof Integer
	domain Obiettivo subsetof Integer
	domain Distanza subsetof Integer
	
	//DICHIARAZIONI FUNZIONI DINAMICHE
	dynamic controlled firstMance: Boolean
	dynamic controlled gamePc: Giocata
	dynamic controlled ultimaGiocataPc1: Giocata //colui che implementa la SV
	dynamic controlled ultimaGiocataPc2: Giocata //colui che NON implementa la SV
	dynamic controlled turno: Players
	dynamic controlled strategiaVincente: Players
	dynamic controlled statoVincita: Players -> WinDomain
	
	//DICHIARAZIONI FUNZIONI STATICHE
	dynamic controlled obiettivo: Obiettivo
	dynamic controlled distanza: Distanza
	
	//DICHIARAZIONI FUNZIONI DERIVATE
//	derived giocataBuona: Boolean
	//derived opponent: Players -> Players
	
	

definitions:
	
	domain Giocata = {0 : 100}
	domain Obiettivo = {100}
	domain Distanza = {10}
	
	
	
	
	//la funzione derivata "opponent" assegna ad un giocatore il suo avversario
	/*function opponent($p in Players) =
		if($p = PLAYER1) then
			PLAYER2
		else
			PLAYER1
		endif*/
	
		
		
	//la funzione "giocataBuona" mi serve per capire se la coppia di numeri forniti rispetta i vincoli
//	function giocataBuona($g in Giocata, $u in Giocata) =
//	($g > 0 and ($g - ultimaGiocataPc1) <= distanza and $g > ultimaGiocataPc1)

			
	
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
			//turno := opponent(turno) //ponendo qui il "cambio turno", nel caso ci fosse un vincitore, lo stato finale di turno rimarrà 
			turno := $q //ponendo qui il "cambio turno", nel caso ci fosse un vincitore, lo stato finale di turno rimarrà
		endif			//su colui che ha fatto la giocata vincente.

	
		
	//con questa regola faccio scegliere in modo NON deterministico al player che NON può implementare la strategia
	//vincente un numero casuale che soddisfi i vincoli imposti nella funzione "giocataBuona"
	rule r_sceltaNumero =
		choose $g in Giocata with ($g > 0 and ($g - ultimaGiocataPc1) <= distanza and $g > ultimaGiocataPc1) do
			par
				ultimaGiocataPc2 := $g
				//turno := opponent(turno)
				if(turno = PLAYER1) then
					turno := PLAYER2
				else
					turno := PLAYER1
				endif
				firstMance := false
			endpar	
			
		
			
	//Se la mance giocata è la prima chiamo questa regola, la quale verifica a quale giocatore deve essere assegnata
	//la strategia vincente, in base alla decisione gli assegno la giocata (quindi se quella appartenente all'insieme
	//delle giocata vincenti o una giocata casuale)
	rule r_controllaStrategiaVincente =
		//if(gamePc <= distanza and gamePc != 0) then
		if((obiettivo mod (distanza + 1)) <= distanza and (obiettivo mod (distanza + 1)) != 0) then
			par
				//ultimaGiocataPc1 := gamePc
				ultimaGiocataPc1 := obiettivo mod (distanza + 1)
				//turno := opponent(turno)
				if(turno = PLAYER1) then
					turno := PLAYER2
				else
					turno := PLAYER1
				endif
				firstMance := false
				strategiaVincente := turno
			endpar
		else
			par
				//strategiaVincente := opponent(turno)
				if(turno = PLAYER1) then
					strategiaVincente := PLAYER2
				else
					strategiaVincente := PLAYER1
				endif
				r_sceltaNumero[]
			endpar
		endif
	
		
		
	//questa regola viene chiamata se la mance non è più la prima e assegna la giocata al Player che è di turno
	//in base a chi è stata assegnata la strategia vincente
	rule r_applicaStrategia($p in Players, $q in Players) =
		if(strategiaVincente = $p) then
			par
				ultimaGiocataPc1 := ultimaGiocataPc1 + distanza + 1
				r_controllaVincita[ultimaGiocataPc1+distanza+1,$p,$q]
				firstMance := false
			endpar
		else
			r_sceltaNumero[]
		endif
			               
		
	
	macro rule r_assegnaGiocataPc($p in Players, $q in Players) =
		if(firstMance) then
			r_controllaStrategiaVincente[]
		else
			r_applicaStrategia[$p,$q] //TOGLIERE FUNZIONE COME PARAMETRO PER LA REGOLA
		endif
		
	
	
	rule r_giocata =
		if(turno = PLAYER1) then
			r_assegnaGiocataPc[PLAYER1,PLAYER2]
		else
			r_assegnaGiocataPc[PLAYER2,PLAYER1] 
		endif

	
	//LE PROPRIETA' CTL SONO VERIFICATE SOLO PER QUEI TIPI DI DATI CHE PERMETTONO AL PLAYER1
	//DI ATTUARE UNA STRATEGIA VINCENTE, OVVERO QUANDO "distanza" E "obiettivo" ASSUMONO UN VALORE
	//TALE PER CUI "strategiaVincente = PLAYER1"

	//PROPRIETA' DI LIVENESS
	//questa proprietà dice la strategia vincente sarà assegnata SOLO al PLAYER1	
	//invariant over statoVincita: ag(strategiaVincente = PLAYER1 implies ag(gamePc <= distanza and gamePc != 0))

	//questa proprietà dice che vincerà sempre e solo il PLAYER1
	invariant over statoVincita: ag(statoVincita(PLAYER1)=WIN and statoVincita(PLAYER2)=LOSE)

	invariant over statoVincita: ag(statoVincita(PLAYER2)!=WIN)
	invariant over statoVincita: ef(ag(statoVincita(PLAYER1)=WIN  and statoVincita(PLAYER2)=LOSE))
	invariant over statoVincita: ag(ef(ag(statoVincita(PLAYER1)=WIN  and statoVincita(PLAYER2)=LOSE)))

	invariant over statoVincita: ag(statoVincita(PLAYER1)=WIN implies ag(statoVincita(PLAYER1)=WIN))
	invariant over statoVincita: ag(statoVincita(PLAYER2)=LOSE implies ag(statoVincita(PLAYER2)=LOSE))

	//Main rule
	main rule r_Main =
		if(statoVincita(PLAYER1) = NONE and statoVincita(PLAYER2) = NONE) then
			r_giocata[]
		endif

default init s0:
	function turno = PLAYER1
	function firstMance = true
	//function gamePc = obiettivo mod (distanza + 1)
	function distanza = 10 		//10 - 10 - 9
	function obiettivo = 100	//99 - 60 - 97
	function ultimaGiocataPc1 = 0
	function ultimaGiocataPc2 = 0
	function statoVincita($p in Players) = NONE
