/* BibliotecaASM
   Sebastiano Rota 57166 Agosto 2007 */

asm BibliotecaASM

import ../../STDL/StandardLibrary

//declare universes and functions
		
signature:
 
//Dichiarazione dei domini		
    abstract domain Utenti
    abstract domain Libri
    enum domain Stati = { SCELTAOPERAZIONE | PRESTITOINSERIMENTO | RESTITUZIONEINSERIMENTO | LIBRIDISPONIBILI | UTENTELIBRO} 
	enum domain Servizi = { PRESTITO | RESTITUZIONE | CONSULTAZIONE | DISPONIBILITA | USCITA }

//Variabili controllate (leggibili e scrivibili) dal sistema
	dynamic controlled bibliotecaStato: Stati
	dynamic controlled stampaMessaggio: Any 
	dynamic controlled libroCorrente: Libri
	dynamic controlled dispLibri: Libri -> Integer
	dynamic controlled associa: Utenti -> Libri

//Variabili monitorate (leggibili) dal sistema	
	dynamic monitored utenteRiconosciuto: Utenti
	dynamic monitored libroRiconosciuto: Libri
	dynamic monitored servizioSelezionato: Servizi

//Funzioni derivate	
	derived dispOk: Libri -> Boolean
	derived associaOk: Prod(Utenti,Libri) -> Boolean
	derived libroOk: Libri -> Boolean

//Variabili statiche	 
	static utente1: Utenti
	static utente2: Utenti
	static utente3: Utenti
	
	static libro1: Libri
	static libro2: Libri
	static libro3: Libri
	static libro4: Libri

definitions:

//Funzione che si occupa di verificare l'effettiva disponibilità di un libro
	function dispOk($l in Libri) = 
		dispLibri($l) > 0

//Funzione che si occupa di verificare che un utente possegga o meno un libro		
	function associaOk($u in Utenti, $l in Libri) =
		associa($u) = $l

//Regola che permette all'utente di scegliere il servizio desiderato					
	rule r_sceltaServizio =
		if(bibliotecaStato=SCELTAOPERAZIONE) then
			par
				if(servizioSelezionato=PRESTITO) then
					par
						bibliotecaStato := PRESTITOINSERIMENTO
						stampaMessaggio := "Prestito libro - Inserire utente e libro"	
					endpar				
				endif
				if(servizioSelezionato=RESTITUZIONE) then
					par
						bibliotecaStato := RESTITUZIONEINSERIMENTO 
						stampaMessaggio := "Restituzione libro - Inserire utente e libro"
					endpar
				endif
				if(servizioSelezionato=DISPONIBILITA) then
					par
						bibliotecaStato := LIBRIDISPONIBILI 
						stampaMessaggio := "Consultazione biblioteca - Inserire libro"
					endpar
				endif
				if(servizioSelezionato=CONSULTAZIONE) then
					par
						bibliotecaStato := UTENTELIBRO 
						stampaMessaggio := "Consultazione biblioteca - Inserire libro"
					endpar
				endif
				if(servizioSelezionato=USCITA) then
					par
						bibliotecaStato := SCELTAOPERAZIONE
						stampaMessaggio := "Alla prossima"
					endpar
				endif
			endpar
		endif

//Regola che si occupa di incrementare la disponibilità del libro restituito		
	macro rule r_aggiungiLibro($u in Utenti,$l in Libri) =
		//if((isUndef($l))=false) then
			if(associaOk($u,$l)) then
		     	par
			 		associa($u) := undef
			 		dispLibri($l) := dispLibri($l) + 1
			 		stampaMessaggio := "Copia restituita"
			 	endpar
			else
				stampaMessaggio := "L'utente non possiede il libro"
			endif		
		//endif 
			
//Regola che si occupa di decrementare la disponibilità del libro prestato		
	macro rule r_sottraiLibro($u in Utenti,$l in Libri) =
		if(dispOk($l)) then
			if(associaOk($u,$l)=false) then
				par
					associa($u) := $l
					dispLibri($l) := dispLibri($l) - 1	
					stampaMessaggio := "Copia prestata"
				endpar
			endif
		else
			stampaMessaggio := "Non ci sono copie del libro voluto"
		endif	

//Questa regola permette ad un utente di prendere in prestito un libro (restituendo quello che eventualmente già possiede)		
	rule r_prestitoLibro =
		if(bibliotecaStato=PRESTITOINSERIMENTO) then
			if(exist $u in Utenti with $u=utenteRiconosciuto) then
				if(exist $l in Libri with $l=libroRiconosciuto) then
					par
						r_aggiungiLibro[utenteRiconosciuto,associa(utenteRiconosciuto)]
						r_sottraiLibro[utenteRiconosciuto,libroRiconosciuto]
						bibliotecaStato := SCELTAOPERAZIONE
					endpar	
				endif
			endif
		endif

//Questa regola permette ad un utente di restituire il libro che possiede		
	rule r_restituzioneLibro =
		if(bibliotecaStato=RESTITUZIONEINSERIMENTO) then
			if(exist $u in Utenti with $u=utenteRiconosciuto) then
				if(exist $l in Libri with $l=libroRiconosciuto) then
					par
						r_aggiungiLibro[utenteRiconosciuto,libroRiconosciuto]
						bibliotecaStato := SCELTAOPERAZIONE
					endpar
				endif
			endif
		endif
		
//Questa regola dato un libro, permette di verificarne la disponibilità 		
	rule r_disponibilita =
		if(bibliotecaStato=LIBRIDISPONIBILI) then
			if(exist $l in Libri with $l=libroRiconosciuto) then
				par
					bibliotecaStato := SCELTAOPERAZIONE
					stampaMessaggio := dispLibri(libroRiconosciuto)
				endpar
			endif
		endif
	
//Questa regola dato un utente, permette di verificare quale libro della biblioteca possiede
	rule r_consultazione =
		if(bibliotecaStato=UTENTELIBRO) then
			if(exist $u in Utenti with $u=utenteRiconosciuto) then
				par
					bibliotecaStato := SCELTAOPERAZIONE
					stampaMessaggio := associa(utenteRiconosciuto)
				endpar
			endif
		endif
						
//Definizione della regola principale
	main rule r_main =
			par
				r_sceltaServizio[]
				r_prestitoLibro[]
				r_restituzioneLibro[]
				r_disponibilita[]
				r_consultazione[]
			endpar
 
//Definizione dello stato iniziale 
default init s0:
	
	//Stato iniziale
	function bibliotecaStato  = SCELTAOPERAZIONE
	
	//Gli utenti all'inizio possono avere un libro della biblioteca già in prestito
	function associa($u in Utenti) = 
		switch($u)
			case utente1 : libro1
			case utente2 : undef
			case utente3 : undef
		endswitch
									
	//Disponibilità dei libri (numero copie disponibili)
	function dispLibri($l in Libri) = 
		switch($l) 
			case libro1 : 1
			case libro2 : 2
			case libro3 : 2
			case libro4 : 3
		endswitch

		
