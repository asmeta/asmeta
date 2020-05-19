// ZANCHI DELLA VALLE

/* VideotecaASM*/

asm VideotecaASM

import ../STDL/StandardLibrary

//declare universes and functions

signature:

//Dichiarazione dei domini
    abstract domain Utenti
    abstract domain Film
    enum domain Stati = { SCELTAOPERAZIONE | NOLEGGIOINSERIMENTO | RESTITUZIONEINSERIMENTO | FILMDISPONIBILI | UTENTEFILM}
	enum domain Servizi = { NOLEGGIO | RESTITUZIONE | CONSULTAZIONE | DISPONIBILITA | USCITA }

//Variabili e funzioni controllate (leggibili e scrivibili) dal sistema
	dynamic controlled videotecaStato: Stati
	dynamic controlled stampaMessaggio: Any
	dynamic controlled dispFilm: Film -> Integer
	dynamic controlled associa: Utenti -> Film

//Variabili monitorate (leggibili) dal sistema
	dynamic monitored utenteRiconosciuto: Utenti
	dynamic monitored filmRiconosciuto: Film
	dynamic monitored servizioSelezionato: Servizi

//Funzioni derivate
	derived dispOk: Film -> Boolean
	derived associaOk: Prod(Utenti,Film) -> Boolean

//Variabili statiche
	static utente1: Utenti
	static utente2: Utenti
	static utente3: Utenti

	static film1: Film
	static film2: Film
	static film3: Film
	static film4: Film

definitions:

//Funzione che si occupa di verificare l'effettiva disponibilità di un film
	function dispOk($f in Film) =
		dispFilm($f) > 0

//Funzione che si occupa di verificare che un utente possegga o meno un film
	function associaOk($u in Utenti, $f in Film) =
		associa($u) = $f

//Regola che permette all'utente di scegliere il servizio desiderato
	rule r_sceltaServizio =
		if(videotecaStato=SCELTAOPERAZIONE) then
			par
				if(servizioSelezionato=NOLEGGIO) then
					par
						videotecaStato := NOLEGGIOINSERIMENTO
						stampaMessaggio := "Noleggio film - Inserire utente e film"
					endpar
				endif
				if(servizioSelezionato=RESTITUZIONE) then
					par
						videotecaStato := RESTITUZIONEINSERIMENTO
						stampaMessaggio := "Restituzione film - Inserire utente e film"
					endpar
				endif
				if(servizioSelezionato=DISPONIBILITA) then
					par
						videotecaStato := FILMDISPONIBILI
						stampaMessaggio := "Consultazione videoteca - Inserire film"
					endpar
				endif
				if(servizioSelezionato=CONSULTAZIONE) then
					par
						videotecaStato := UTENTEFILM
						stampaMessaggio := "Consultazione videoteca - Inserire utenteCONSU"
					endpar
				endif
				if(servizioSelezionato=USCITA) then
					par
						videotecaStato := SCELTAOPERAZIONE
						stampaMessaggio := "Alla prossima"
					endpar
				endif
			endpar
		endif

//Regola che si occupa di incrementare la disponibilità del film restituito
	macro rule r_aggiungiFilm($u in Utenti,$f in Film) =
		if(associaOk($u,$f)) then
		   	par
		 		associa($u) := undef
		 		dispFilm($f) := dispFilm($f) + 1
		 		stampaMessaggio := "Copia restituita"
		 	endpar
		else
			stampaMessaggio := "L'utente non possiede il film"
		endif


//Regola che si occupa di decrementare la disponibilità del film prestato
	macro rule r_sottraiFilm($u in Utenti,$f in Film) =
		if(dispOk($f)) then
			if(associaOk($u,$f)=false) then
				par
					associa($u) := $f
					dispFilm($f) := dispFilm($f) - 1
					stampaMessaggio := "Copia prestata"
				endpar
			endif
		else
			stampaMessaggio := "Non ci sono copie del film voluto"
		endif

//Questa regola permette ad un utente di noleggiare un film (restituendo quello che eventualmente già possiede)
	rule r_noleggioFilm =
		if(videotecaStato=NOLEGGIOINSERIMENTO) then
			if(exist $u in Utenti with $u=utenteRiconosciuto) then
				if(exist $f in Film with $f=filmRiconosciuto) then
					par
						seq
							if(isDef(associa(utenteRiconosciuto))) then
								r_aggiungiFilm[utenteRiconosciuto,associa(utenteRiconosciuto)]
							endif
							r_sottraiFilm[utenteRiconosciuto,filmRiconosciuto]
						endseq
						videotecaStato := SCELTAOPERAZIONE
					endpar
				endif
			endif
		endif

//Questa regola permette ad un utente di restituire il film che possiede
	rule r_restituzioneFilm =
		if(videotecaStato=RESTITUZIONEINSERIMENTO) then
			if(exist $u in Utenti with $u=utenteRiconosciuto) then
				if(exist $f in Film with $f=filmRiconosciuto) then
					par
						r_aggiungiFilm[utenteRiconosciuto,filmRiconosciuto]
						videotecaStato := SCELTAOPERAZIONE
					endpar
				endif
			endif
		endif

//Questa regola, dato un film, permette di verificarne la disponibilità
	rule r_disponibilita =
		if(videotecaStato=FILMDISPONIBILI) then
			if(exist $f in Film with $f=filmRiconosciuto) then
				par
					videotecaStato := SCELTAOPERAZIONE
					stampaMessaggio := dispFilm(filmRiconosciuto)
				endpar
			endif
		endif

//Questa regola, dato un utente, permette di verificare quale film della videoteca possiede
	rule r_consultazione =
		if(videotecaStato=UTENTEFILM) then
			if(exist $u in Utenti with $u=utenteRiconosciuto) then
				par
					videotecaStato := SCELTAOPERAZIONE
					stampaMessaggio := associa(utenteRiconosciuto)
				endpar
			endif
		endif

//Definizione della regola principale
	main rule r_main =
			par
				r_sceltaServizio[]
				r_noleggioFilm[]
				r_restituzioneFilm[]
				r_disponibilita[]
				r_consultazione[]
			endpar

//Definizione dello stato iniziale
default init s0:

	//Stato iniziale
	function videotecaStato  = SCELTAOPERAZIONE

	//Gli utenti all'inizio possono avere gia' noleggiato un film della videoteca
	function associa($u in Utenti) =
		switch($u)
			case utente1 : film1
			case utente2 : undef
			case utente3 : undef
		endswitch

	//Disponibilità dei film (numero copie disponibili)
	function dispFilm($f in Film) =
		switch($f)
			case film1 : 1
			case film2 : 2
			case film3 : 2
			case film4 : 3
		endswitch


