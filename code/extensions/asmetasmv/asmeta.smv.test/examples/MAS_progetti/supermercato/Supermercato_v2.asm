asm Supermercato_v2 // A.FINA M.GUZZETTI 

import ../../../../../../../asm_examples/STDL/StandardLibrary

signature:

abstract domain Cliente
abstract domain Prodotto
abstract domain SuperCassa

enum domain Attivita = {ACQUISTA | FINESPESA}
enum domain ProdottoScelto = {PASTAA | PASTAB | VERDURAA | VERDURAB | CARNEA | CARNEB}        //scelta acquisto del cliente
enum domain Numero = {ZERO | UNO | DUE} 													//numero prodotti in sconto acquistati
enum domain ScontoScelta = {PASTAA_SCELTA | PASTAB_SCELTA | VERDURAA_SCELTA | VERDURAB_SCELTA | CARNEA_SCELTA | CARNEB_SCELTA | NONE}      //sconto per controllo sul numero
enum domain Sconto = {PASTAA_S | PASTAB_S | VERDURAA_S | VERDURAB_S | CARNEA_S | CARNEB_S}     //sconto generato
enum domain Messaggio = {SPESA_FINITA_DIGITARE_ACQUISTA_PER_NUOVO_CLIENTE | AGGIUNTO_NO_SCONTI | PRODOTTO_AGGIUNTO_SI_SCONTO |
						 PRODOTTO_NON_SELEZIONABILE_CAMBIARE | PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO | AGGIUNTO_NO_SCONTI_DUE | 
						 PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE | AGGIUNTO_SI_SCONTO}

dynamic controlled decisione: Cliente -> Boolean

// Prodotto
dynamic controlled disponibilita: Prodotto -> Boolean //da implementare
dynamic controlled sconto: Prodotto -> Boolean //da implementare

//Cassa
dynamic controlled scUno: Sconto
dynamic controlled scDue: Sconto
dynamic controlled control: Boolean
dynamic controlled vialibera: Boolean
dynamic controlled gen: Boolean
dynamic controlled pezzo: ProdottoScelto
dynamic controlled outMess: String
dynamic controlled selectAttivita: Attivita
dynamic controlled s_Scelta: ScontoScelta
dynamic controlled n_Numero: Numero
//dynamic monitored act: Attivita//versione non funzionante (le monitorate sono settate dall'ambiente, non dalla macchina)
dynamic controlled act: Attivita
//dynamic monitored mess: Messaggio//versione non funzionante (le monitorate sono settate dall'ambiente, non dalla macchina)
dynamic controlled mess: Messaggio

//RIMOSSE COME DA SUGGERIMENTO 
//derived variabile_uno: Sconto -> Any 				
//derived variabile_due: Sconto -> Any
//derived variabile_act: Attivita -> Any
//derived variabile_pezzo: ProdottoScelto -> Any


definitions:
//AGGIUNTE PER CORREGGERE L_ERRORE COMUNICATO associato da noi alla choose sotto 
//function variabile_uno($sc1 in Sconto) = NONE				 
//function variabile_due($sc2 in Sconto) = NONE
//function variabile_act($act in Attivita) = NONE
//function variabile_pezzo($pezzo in ProdottoScelto) = NONE


	//regola per la selezione casuale dei prodotti da scontare
	macro rule r_sconti =	
	
	seq	

		choose $sc1 in Sconto with true do	
			scUno:= $sc1
	
		choose $sc2 in Sconto with true do
			scDue:= $sc2
			
			
				if ((scUno = PASTAA_S or scUno = PASTAB_S) and (scDue = PASTAA_S or scDue = PASTAB_S)) then
				control := false 
			else if ((scUno = VERDURAA_S or scUno = VERDURAB_S) and (scDue = VERDURAA_S or scDue = VERDURAB_S)) then
				control := false  
			else if ((scUno = CARNEA_S or scUno = CARNEB_S) and (scDue = CARNEA_S or scDue = CARNEB_S)) then
				control := false
			else control := true
				endif endif endif
		
		
			if (control = true) then
			par
				vialibera:= true
				gen:= true
			endpar
			endif
	endseq	
			
	
	
	
	//regole per cliente
	macro rule r_scelta =

		if (act = ACQUISTA) then
			selectAttivita := ACQUISTA
			else if (act = FINESPESA) then
				 selectAttivita := FINESPESA
			     endif
		endif


	macro rule r_buy =
		if (selectAttivita = ACQUISTA) then
			choose $pezzo in ProdottoScelto with true do
				pezzo:= $pezzo	
		endif
	
		
	macro rule r_end = 
		if (selectAttivita = FINESPESA) then
		    par
		    mess := SPESA_FINITA_DIGITARE_ACQUISTA_PER_NUOVO_CLIENTE
		    //outMess := "Il cliente ha terminato i suoi acquisti. Digitare ACQUISTA per un nuovo cliente"
		    n_Numero := ZERO
			s_Scelta := NONE
			endpar
		endif
		
	
	macro rule r_verifica=
	par
	if ((pezzo = PASTAA) and ((scUno != PASTAA_S) and (scDue != PASTAA_S))) then
			mess := AGGIUNTO_NO_SCONTI
			//outMess := "aggiunto no sconti"
	endif
		
	if ((pezzo = PASTAA) and ((scUno = PASTAA_S) or (scDue = PASTAA_S))) then
		par
			
		if (n_Numero = ZERO) then
				par
					s_Scelta := PASTAA_SCELTA
					n_Numero := UNO
					mess := PRODOTTO_AGGIUNTO_SI_SCONTO
					//outMess := "prodotto aggiunto al carrello"
				endpar
			endif
			
		if ((n_Numero = UNO) and (s_Scelta = PASTAA_SCELTA) and (pezzo = PASTAA)) then
				mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE
				//outMess:= "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != PASTAA_SCELTA) and (pezzo = PASTAA)) then
				par
					mess := PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
					//outMess:= "Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
					s_Scelta := PASTAA_SCELTA
					n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != PASTAA_S) and (scDue != PASTAA_S))) then
					mess := AGGIUNTO_NO_SCONTI_DUE
					//outMess:= "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = PASTAA_S) or (scDue = PASTAA_S))) then
					mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
					//outMess:= "Prodotto non aggiunto -2¡ controllo-"
			endif  
		endif 
		endpar
	endif
			
				
		
	if ((pezzo = PASTAB) and ((scUno != PASTAB_S) and (scDue != PASTAB_S))) then
			mess := AGGIUNTO_NO_SCONTI
			//outMess := "aggiunto no sconti"
	endif
			
	if ((pezzo = PASTAB) and ((scUno = PASTAB_S) or (scDue = PASTAB_S))) then
		par
				
		if (n_Numero = ZERO) then
			par
				s_Scelta := PASTAB_SCELTA
				n_Numero := UNO
				mess := AGGIUNTO_SI_SCONTO
				//outMess := "prodotto aggiunto al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = PASTAB_SCELTA) and (pezzo = PASTAB)) then
				mess :=  PRODOTTO_NON_SELEZIONABILE_CAMBIARE
				//outMess:= "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != PASTAB_SCELTA) and (pezzo = PASTAB)) then
					par
					mess := PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
					//outMess:= "Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
					s_Scelta := PASTAB_SCELTA
					n_Numero := DUE
					endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != PASTAB_S) and (scDue != PASTAB_S))) then
					mess := AGGIUNTO_NO_SCONTI_DUE
					//outMess:= "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = PASTAB_S) or (scDue = PASTAB_S))) then
					mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
					//outMess:= "Prodotto non aggiunto -2¡ controllo-"
			endif
		endif 
		endpar
	endif
		
		
		
	if ((pezzo = VERDURAA) and ((scUno != VERDURAA_S) and (scDue != VERDURAA_S))) then
			mess := AGGIUNTO_NO_SCONTI
			//outMess := "aggiunto no sconti"
	endif
			
	if ((pezzo = VERDURAA) and ((scUno = VERDURAA_S) or (scDue = VERDURAA_S))) then
		par
		if (n_Numero = ZERO) then
			par
				s_Scelta := VERDURAA_SCELTA
				n_Numero := UNO
				mess := AGGIUNTO_SI_SCONTO
				//outMess := "prodotto aggiungo al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = VERDURAA_SCELTA) and (pezzo = VERDURAA)) then
			mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE
			//outMess:= "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
		else if ((n_Numero = UNO) and (s_Scelta != VERDURAA_SCELTA) and (pezzo = VERDURAA)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//outMess:= "Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
				s_Scelta := VERDURAA_SCELTA
				n_Numero := DUE
				endpar
		endif
	endif
				
		if ((n_Numero = DUE) and ((scUno != VERDURAA_S) and (scDue != VERDURAA_S))) then
					mess:= AGGIUNTO_NO_SCONTI_DUE
					//outMess:= "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = VERDURAA_S) or (scDue = VERDURAA_S))) then
					mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
					//outMess:= "Prodotto non aggiunto -2¡ controllo-"
			endif 
		endif 
		endpar
	endif
			
		
	if ((pezzo = VERDURAB) and ((scUno != VERDURAB_S) and (scDue != VERDURAB_S))) then
		mess:= AGGIUNTO_NO_SCONTI
		//outMess := "aggiunto no sconti"
	endif
		
	if ((pezzo = VERDURAB) and ((scUno = VERDURAB_S) or (scDue = VERDURAB_S))) then
		par
			
		if (n_Numero = ZERO) then
			par
				s_Scelta := VERDURAB_SCELTA
				n_Numero := UNO
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO
				//outMess := "prodotto aggiungo al carrello"
			endpar
		endif	
		
		if ((n_Numero = UNO) and (s_Scelta = VERDURAB_SCELTA) and (pezzo = VERDURAB)) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE
				//outMess:= "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta = VERDURAB_SCELTA) and (pezzo = VERDURAB)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//outMess:= "Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
				s_Scelta := VERDURAB_SCELTA
				n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != VERDURAB_S) and (scDue != VERDURAB_S))) then
				mess:= AGGIUNTO_NO_SCONTI_DUE
				//outMess:= "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = VERDURAB_S) or (scDue = VERDURAB_S))) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
				//outMess:= "Prodotto non aggiunto -2¡ controllo-"
			endif 
		endif 
		endpar
	endif

		
	if ((pezzo = CARNEA) and ((scUno != CARNEA_S) and (scDue != CARNEA_S))) then
		mess:= AGGIUNTO_NO_SCONTI
		//outMess := "aggiunto no sconti"
	endif
		
	if ((pezzo = CARNEA) and ((scUno = CARNEA_S) or (scDue = CARNEA_S))) then
		par
			
		if (n_Numero = ZERO) then
			par
				s_Scelta := CARNEA_SCELTA
				n_Numero := UNO
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO
				//outMess := "prodotto aggiungo al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = CARNEA_SCELTA) and (pezzo = CARNEA)) then
			mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE
			//outMess:= "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != CARNEA_SCELTA) and (pezzo = CARNEA)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//outMess:= "Prodotto aggiunto //con prodotto diverso da scontati con contr=UNO" 
				s_Scelta := CARNEA_SCELTA
				n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != CARNEA_S) and (scDue != CARNEA_S))) then
				mess:= AGGIUNTO_NO_SCONTI_DUE
				//outMess:= "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = CARNEA_S) or (scDue = CARNEA_S))) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
				//outMess:= "Prodotto non aggiunto -2¡ controllo-"
			endif
		endif
		endpar
	endif
	
		
	if ((pezzo = CARNEB) and ((scUno != CARNEB_S) and (scDue != CARNEB_S))) then
		mess:= AGGIUNTO_NO_SCONTI
		//outMess := "aggiunto no sconti"
	endif
		
	if ((pezzo = CARNEB) and ((scUno = CARNEB_S) or (scDue = CARNEB_S))) then
		par
			
		if (n_Numero = ZERO) then
			par
				s_Scelta := CARNEB_SCELTA
				n_Numero := UNO
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO
				//outMess := "prodotto aggiungo al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = CARNEB_SCELTA) and (pezzo = CARNEB)) then
			mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE
			//outMess:= "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != CARNEB_SCELTA) and (pezzo = CARNEB)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//outMess:= "Prodotto aggiunto //con prodotto diverso da scontati con contr=UNO" 
				s_Scelta := CARNEB_SCELTA
				n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != CARNEB_S) and (scDue != CARNEB_S))) then
				mess:= AGGIUNTO_NO_SCONTI_DUE
				//outMess:= "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = CARNEB_S) or (scDue = CARNEB_S))) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
				//outMess:= "Prodotto non aggiunto -2¡ controllo-"
			endif 
		endif 
		endpar 
	endif
	endpar


//Definizione della regola principale
	main rule r_main =
			
		if (gen= false) then
			r_sconti[]
			
			else if (gen= true and vialibera = true) then 
				seq		
				r_scelta[]
				if (selectAttivita =ACQUISTA) then
					seq
					r_buy[]
					r_verifica[]
					endseq
					else
					r_end[] 
				endif
				endseq
			endif
		endif		
			
			
//Definizione dello stato iniziale
default init s0:
	function n_Numero = ZERO
	function vialibera = false
	function gen = false