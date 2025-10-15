asm trackerID2
// N.B. VERSIONE di TrackerID per fare model checking ASMETASMV
import ../../../../../../asm_examples/STDL/StandardLibrary

signature:


// DOMINI
	enum domain UserRole = {UTENTE|OPERATORE}
	enum domain OperazioniOperatore = {CREARE_UN_ORDINE | AGGIORNA_ORDINE}
	enum domain OperazioniUtente= {MONITORA_ORDINE}
	enum domain Stato = {CREATO|PARTITO|IN_TRANSITO|IN_CONSEGNA|CONSEGNATO}

	domain OrdineID subsetof Integer 

// FUNZIONI
	dynamic monitored ruoloCorrente: UserRole
	dynamic monitored sceltaOperazioneOperatore:OperazioniOperatore
	dynamic monitored sceltaOperazioneUtente:OperazioniUtente
	dynamic monitored scegliId:OrdineID
	
	dynamic controlled nuovoOrdine: OrdineID
	dynamic controlled ordineMonitoratoID: OrdineID
	dynamic controlled ordineMonitoratoStato: Stato
	dynamic controlled statoOrdine: OrdineID -> Stato
	
	
	derived existOrdine: OrdineID -> Boolean

definitions:

	domain OrdineID ={1:3} // LIMITE 4 altrimenti crush
	
	function existOrdine($id in OrdineID) =
		isDef(statoOrdine($id))

	rule r_monitoraOrdine($id in OrdineID) =
		if(existOrdine($id)) then
			par
				ordineMonitoratoStato:= statoOrdine($id)
				ordineMonitoratoID:= $id
			endpar	
		endif
		
	rule r_aggiungiOrdine($id in OrdineID)=
//		if isDef($id) then 
			if (not existOrdine($id)) then 
				// crea l'ordine
//				par
//					ordineMonitoratoID:= undef
//					ordineMonitoratoStato:= undef
					statoOrdine($id):=CREATO
//				endpar	
			endif
//		endif 

	rule r_aggiornaOrdine($id in OrdineID) =
//		if isDef($id) then
			if existOrdine($id) then
				if(statoOrdine($id)= CREATO) then
					statoOrdine($id) := PARTITO
				else if((statoOrdine($id)= PARTITO)) then
					statoOrdine($id) := IN_TRANSITO
				else if((statoOrdine($id)= IN_TRANSITO)) then
					statoOrdine($id) := IN_CONSEGNA
				else if((statoOrdine($id)= IN_CONSEGNA)) then
					statoOrdine($id) := CONSEGNATO
				endif				
				endif				
				endif				
				endif				
			endif
//		endif
/*			
		// PROPRIETA' 
		// 1. Se esiste un ordine con id = 2 prima o poi il suo stato di consegna è IN_TRANSITO.
		CTLSPEC existOrdine(2) implies af(statoOrdine(2) = IN_TRANSITO)
		// 2. In ogni stato del sistema, un ordine con stato IN_TRANSITO non può avere come stato successivo CREATO.
		CTLSPEC ag(statoOrdine(1)= IN_TRANSITO implies not(af(statoOrdine(1)=CREATO)))
		// 3. Non si salta mai uno stato. Se in CREATO, il prossimo non può essere IN_TRANSITO ma PARTITO
		CTLSPEC ag(statoOrdine(1) =CREATO implies (not(ax(statoOrdine(1) = IN_TRANSITO) and ax(statoOrdine(1)=PARTITO))))
		// 4. Se un ordine è monitorato, allora esiste davvero
		CTLSPEC ag(ordineMonitoratoID = 1 implies existOrdine(1))
		// 5. Se un ordine è in uno stato diverso da CREATO in futuro non potrà mai essere nello stato CREATO
		CTLSPEC ag(existOrdine(1) and statoOrdine(1) != CREATO implies not (af(statoOrdine(1)=CREATO)))
		// 7. Se un ordine NON esiste, il suo stato rimane undef
		CTLSPEC ag((forall $o in OrdineID with not existOrdine($o)) implies statoOrdine($o) = undef)
*/		
		
	main rule r_Main = 
		
//		if(ruoloCorrente = UTENTE) then
//			if(sceltaOperazioneUtente=MONITORA_ORDINE) then 
//				r_monitoraOrdine[scegliId]			
//			endif
//			
//		else 
			if(ruoloCorrente = OPERATORE) then 
				if(sceltaOperazioneOperatore = CREARE_UN_ORDINE) then 
					r_aggiungiOrdine[scegliId]
				else
					r_aggiornaOrdine[scegliId]	
				endif
			endif
//		endif

default init s0:

