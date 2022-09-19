asm SiGistica
import StandardLibrary
import CTLlibrary
import LTLlibrary

signature:

	//Domini
	enum domain Service = {SPEDIZIONE|GESTIONE_CLIENTE}
	enum domain UffLog = {OFFICINA|RICH_INFO}
	enum domain PagDip = {YES|NO}
	enum domain Mercato = {AUTISTA|GASOLIO}
	
	domain QuantityAut subsetof Integer 	//Integer per indicare la quantità totale di Mezzi
	domain QuantityGas subsetof Integer 	//Integer per indicare la quantità totale di Gasolio
	domain Stipendio subsetof Integer		//Stipendio
	domain PersInfo subsetof Integer		//Personale dedicato a dare info
	//domain Offic subsetof Boolean 			//Definisce se un mezzo è stato revisionato o no dopo un viaggio
	
	
	dynamic monitored selService: Service  //Scelta Utente
	dynamic monitored selPag: PagDip 	   //Scelta Utente
	dynamic controlled qtAutGas: Mercato -> QuantityGas	 //Funzione che data un'istanza di Mercato restituisce la quantità
	dynamic controlled checkMezzo: UffLog -> Boolean	 //Funzione che data un'istanza di UffLog restituisce un Boolean
	dynamic controlled pagDomain: Stipendio	 //Controlla la variabile Stipendio
	dynamic controlled perInfo: PersInfo	 //Controlla la variabile PersInfo
	


definitions:

	domain QuantityAut = {0:10}  //Inizializzazione
	domain QuantityGas = {0:10}	 //Inizializzazione
	domain Stipendio = {0:10}	 //Inizializzazione
	domain PersInfo = {0:10}	 //Inizializzazione 

	
	//Funzione che decrementa la disponibilità di gasolio per un viaggio	
	macro rule r_rifornimento = 
		choose $g in Mercato with ($g = GASOLIO and qtAutGas($g) > 0) do 
			qtAutGas(GASOLIO) := qtAutGas(GASOLIO) - 1
	
	//Funzione che verifica se il mezzo è stato revisionato oppure no
	macro rule r_officina($a in UffLog)=
	 	if($a = RICH_INFO) then skip
	 	else if($a = OFFICINA and  checkMezzo($a) = false) then
				checkMezzo($a) := true
		else if($a = OFFICINA and checkMezzo($a) = true) then
				checkMezzo($a) := false
			endif
	 	endif
	 	endif
	
	//POSSIBILE SOLUZIONE
	
	//Funzione che permette di pagare un autista a seguito di un viaggio
	macro rule r_pagamento =  
		if(pagDomain < 10) then 
			pagDomain := pagDomain + 1
			endif
	
	//Funzione che tramite la scelta dell'Utente richiama la funzione per pagare un autista oppure pagarlo in un secondo momento
	macro rule r_choosePag = 
		switch(selPag)
			case (YES):
				r_pagamento[]
			case (NO):
				skip
			endswitch	
	
	//Funzione autista che decrementa la quantità di autisti presenti in azienda
	//r_officina richiama la funzione sopra per verificare la corretta revisione del mezzo
	//r_choosePag richiama la funzione sopra per decidere se pagare o meno un autista
	macro rule r_autista  =  
		choose $a in Mercato with ($a = AUTISTA and qtAutGas($a) > 0) do
			par
				qtAutGas(AUTISTA) := qtAutGas(AUTISTA) - 1
				r_officina[OFFICINA]
				r_choosePag[]			
			endpar
	
	//Funzione che decrementa il personale dedicato alle informazioni 
	macro rule r_info  = 
		if(perInfo > 0) then
			perInfo := perInfo - 1
		endif
	
	//------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	// 1) Check mezzo = true implica nel prossimo stato mezzo = false
	CTLSPEC ef(checkMezzo(OFFICINA) = true implies ax(checkMezzo(OFFICINA) = false))
	
	// 2) Esiste un percorso in cui lo stipendio non viene erogato 
    //	  ma il gasolio decrementato
	CTLSPEC ef(selPag = NO and qtAutGas(GASOLIO) = 9)
	
	// 3) Esiste, in futuro, un percorso in cui viene selezionato il servizio di Spezione e non erogato 
	//    lo stipendio al dipendente ed implica che la quantità di Gasolio nei serbatoio è uguale a 9
	CTLSPEC ef(selService = SPEDIZIONE and selPag = NO implies ef(qtAutGas(GASOLIO) = 9))
	
	// 4) Esiste, in futuro, un percorso in cui è selezionata la spedizione e i dipendenti 
	//    da pagare sono ancora 9 implica che i dipendenti da pagare rimangono invariati 
	//    fino a quando non viene selezionato dall'utente selPag = YES
	CTLSPEC ef(selService = SPEDIZIONE and pagDomain = 9 implies aw(selPag = YES,pagDomain = 8)) 
	
	// 5) Globalmente se viene selezionato un servizio di spedizione senza il pagamento 
	//    dello stipendio implica sempre qta di Gasolio = 9
	//    Mi aspetto sia falsa
	//CTLSPEC ag(selService = SPEDIZIONE and selPag = NO implies ag(qtAutGas(GASOLIO) = 9))
	
	/*
	- specification AG ((selPag = NO & selService = SPEDIZIONE) -> AG qtAutGas(GASOLIO) = 9)  is false
	-- as demonstrated by the following execution sequence
	Trace Description: CTL Counterexample 
	Trace Type: Counterexample 
	  -> State: 1.1 <-
	    selPag = NO
	    selService = GESTIONE_CLIENTE
	    qtAutGas(GASOLIO) = 10
	    var_$g64_0 = GASOLIO
	  -> State: 1.2 <-
	    selService = SPEDIZIONE
	 */
	//------------------------------------------------------------------------------------------------------------------------------------------------------------
	

	//Funzione main che richiama in parallelo r_rifornimento e r_autista nel caso in cui l'utente seleziona SPEDIZIONE
	//Richiama r_info nel caso in cui l'utente seleziona GESTIONE_CLIENTE
	//La gestione delle scelte viene regolata attraverso uno switch-case
	main rule r_Main = 
		switch(selService)
			case (SPEDIZIONE):
				par
					r_rifornimento[]
					r_autista[]
				endpar
			case (GESTIONE_CLIENTE):
				r_info[]
			endswitch

//Stato iniziale
default init s0:
	function qtAutGas($a in Mercato) = 10 //Quantità totale di AUTISTA e GASOLIO pari a 0
	function checkMezzo($c in UffLog) = false	//Inizialemnte false
	function pagDomain = 0 //Inizialmente non viene erogato nessun stipendio
	function perInfo = 10	//Inizialmente sono 10 i dipendenti che danno informazioni
