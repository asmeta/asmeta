//5 ottobre 2010
asm supermercatov2  

import ../../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../../asm_examples/STDL/CTLLibrary

signature:

//abstract domain Cliente
//abstract domain Prodotto
//abstract domain SuperCassa

enum domain Attivita = {ACQUISTA | FINESPESA}
enum domain ProdottoScelto = {PASTAA | PASTAB | VERDURAA | VERDURAB | CARNEA | CARNEB}        //scelta acquisto del cliente
enum domain Numero = {ZERO | UNO | DUE} 													//numero prodotti in sconto acquistati
enum domain ScontoScelta = {PASTAA_SCELTA | PASTAB_SCELTA | VERDURAA_SCELTA | VERDURAB_SCELTA | CARNEA_SCELTA | CARNEB_SCELTA | NONE}      //sconto per controllo sul numero
enum domain Sconto = {PASTAA_S | PASTAB_S | VERDURAA_S | VERDURAB_S | CARNEA_S | CARNEB_S}     //sconto generato
enum domain Messaggio = {SPESA_FINITA_DIGITARE_ACQUISTA_PER_NUOVO_CLIENTE | AGGIUNTO_NO_SCONTI | PRODOTTO_AGGIUNTO_SI_SCONTO | PRODOTTO_NON_SELEZIONABILE_CAMBIARE | PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO | AGGIUNTO_NO_SCONTI_DUE | PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE | AGGIUNTO_SI_SCONTO}

//dynamic controlled decisione: Cliente -> Boolean

// Prodotto
//dynamic controlled disponibilita: Prodotto -> Boolean //da implementare
//dynamic controlled sconto: Prodotto -> Boolean //da implementare

//Cassa
dynamic controlled scUno: Sconto

dynamic controlled scDue: Sconto
dynamic controlled vialibera: Boolean
dynamic controlled pezzo: ProdottoScelto
dynamic controlled mess: Messaggio
dynamic controlled selectAttivita: Attivita
dynamic controlled s_Scelta: ScontoScelta
dynamic controlled n_Numero: Numero
dynamic controlled outMess: Sconto
dynamic controlled passaggio: Boolean
dynamic controlled sceltaverifica: Boolean
dynamic controlled verificaver: Boolean
dynamic monitored act: Attivita


definitions:

	//regole per la selezione casuale dei prodotti da scontare
	 
	 rule r_sconto1 =	
	
		choose $sc1 in Sconto with true do	
			scUno:= $sc1
	 
	 rule r_sconto2 =
	
		choose $sc2 in Sconto with true do
		par
			scDue:= $sc2
			passaggio := true
		endpar
	
	rule r_check =
	
			if ((scUno = PASTAA_S or scUno = PASTAB_S) and (scDue = PASTAA_S or scDue = PASTAB_S)) then
				passaggio := false 
			else if ((scUno = VERDURAA_S or scUno = VERDURAB_S) and (scDue = VERDURAA_S or scDue = VERDURAB_S)) then
				passaggio := false  
			else if ((scUno = CARNEA_S or scUno = CARNEB_S) and (scDue = CARNEA_S or scDue = CARNEB_S)) then
				passaggio := false
			else 
				
		
				vialibera:= true
	
			endif endif endif
			
		
	
	
	
	//regole per cliente
	 rule r_scelta =
		
		if (act = ACQUISTA) then
			par
			selectAttivita := ACQUISTA
			sceltaverifica := true
			endpar
			else if (act = FINESPESA) then
				 selectAttivita := FINESPESA
			     endif
		endif
		

	 rule r_buy =
	par
		if (selectAttivita = ACQUISTA) then
			choose $pezzo in ProdottoScelto with true do
				pezzo:= $pezzo	
		endif
		verificaver := true
	endpar	
		
	macro rule r_end = 
		if (selectAttivita = FINESPESA) then
		    par
		    mess := SPESA_FINITA_DIGITARE_ACQUISTA_PER_NUOVO_CLIENTE
		    //"Il cliente ha terminato i suoi acquisti. Digitare ACQUISTA per un nuovo cliente"
		    n_Numero := ZERO
			s_Scelta := NONE
			endpar
		endif
		
	
	 rule r_verifica=
	par
	if ((pezzo = PASTAA) and ((scUno != PASTAA_S) and (scDue != PASTAA_S))) then
			mess := AGGIUNTO_NO_SCONTI
			//"aggiunto no sconti"
	endif
		
	if ((pezzo = PASTAA) and ((scUno = PASTAA_S) or (scDue = PASTAA_S))) then
		par
			
		if (n_Numero = ZERO) then
				par
					s_Scelta := PASTAA_SCELTA
					n_Numero := UNO
					mess := PRODOTTO_AGGIUNTO_SI_SCONTO
					//"prodotto aggiunto al carrello"
				endpar
			endif
			
		if ((n_Numero = UNO) and (s_Scelta = PASTAA_SCELTA) and (pezzo = PASTAA)) then
				mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE
				//"Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != PASTAA_SCELTA) and (pezzo = PASTAA)) then
				par
					mess := PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
					//"Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
					s_Scelta := PASTAA_SCELTA
					n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != PASTAA_S) and (scDue != PASTAA_S))) then
					mess := AGGIUNTO_NO_SCONTI_DUE
					//"Prodotto aggiunto -2o controllo-"
			else if ((n_Numero = DUE) and ((scUno = PASTAA_S) or (scDue = PASTAA_S))) then
					mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
					//"Prodotto non aggiunto -2o controllo-"
			endif  
		endif 
		endpar
	endif
			
				
		
	if ((pezzo = PASTAB) and ((scUno != PASTAB_S) and (scDue != PASTAB_S))) then
			mess := AGGIUNTO_NO_SCONTI
			//"aggiunto no sconti"
	endif
			
	if ((pezzo = PASTAB) and ((scUno = PASTAB_S) or (scDue = PASTAB_S))) then
		par
				
		if (n_Numero = ZERO) then
			par
				s_Scelta := PASTAB_SCELTA
				n_Numero := UNO
				mess := PRODOTTO_AGGIUNTO_SI_SCONTO
				//"prodotto aggiunto al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = PASTAB_SCELTA) and (pezzo = PASTAB)) then
				mess :=  PRODOTTO_NON_SELEZIONABILE_CAMBIARE
				//"Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != PASTAB_SCELTA) and (pezzo = PASTAB)) then
					par
					mess := PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
					//"Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
					s_Scelta := PASTAB_SCELTA
					n_Numero := DUE
					endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != PASTAB_S) and (scDue != PASTAB_S))) then
					mess := AGGIUNTO_NO_SCONTI_DUE
					//"Prodotto aggiunto -2o controllo-"
			else if ((n_Numero = DUE) and ((scUno = PASTAB_S) or (scDue = PASTAB_S))) then
					mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
					//"Prodotto non aggiunto -2o controllo-"
			endif
		endif 
		endpar
	endif
		
		
		
	if ((pezzo = VERDURAA) and ((scUno != VERDURAA_S) and (scDue != VERDURAA_S))) then
			mess := AGGIUNTO_NO_SCONTI
			//"aggiunto no sconti"
	endif
			
	if ((pezzo = VERDURAA) and ((scUno = VERDURAA_S) or (scDue = VERDURAA_S))) then
		par
		if (n_Numero = ZERO) then
			par
				s_Scelta := VERDURAA_SCELTA
				n_Numero := UNO
				mess := PRODOTTO_AGGIUNTO_SI_SCONTO
				//"prodotto aggiungo al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = VERDURAA_SCELTA) and (pezzo = VERDURAA)) then
			mess := PRODOTTO_NON_SELEZIONABILE_CAMBIARE
			//"Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
		else if ((n_Numero = UNO) and (s_Scelta != VERDURAA_SCELTA) and (pezzo = VERDURAA)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//"Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
				s_Scelta := VERDURAA_SCELTA
				n_Numero := DUE
				endpar
		endif
	endif
				
		if ((n_Numero = DUE) and ((scUno != VERDURAA_S) and (scDue != VERDURAA_S))) then
					mess:= AGGIUNTO_NO_SCONTI_DUE
					//"Prodotto aggiunto -2o controllo-"
			else if ((n_Numero = DUE) and ((scUno = VERDURAA_S) or (scDue = VERDURAA_S))) then
					mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
					//"Prodotto non aggiunto -2o controllo-"
			endif 
		endif 
		endpar
	endif
			
		
	if ((pezzo = VERDURAB) and ((scUno != VERDURAB_S) and (scDue != VERDURAB_S))) then
		mess:= AGGIUNTO_NO_SCONTI
		//"aggiunto no sconti"
	endif
		
	if ((pezzo = VERDURAB) and ((scUno = VERDURAB_S) or (scDue = VERDURAB_S))) then
		par
			
		if (n_Numero = ZERO) then
			par
				s_Scelta := VERDURAB_SCELTA
				n_Numero := UNO
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO
				//"prodotto aggiungo al carrello"
			endpar
		endif	
		
		if ((n_Numero = UNO) and (s_Scelta = VERDURAB_SCELTA) and (pezzo = VERDURAB)) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE
				//"Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta = VERDURAB_SCELTA) and (pezzo = VERDURAB)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//"Prodotto aggiunto //con prodotto diverso da scontato scelta con contr=UNO" 
				s_Scelta := VERDURAB_SCELTA
				n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != VERDURAB_S) and (scDue != VERDURAB_S))) then
				mess:= AGGIUNTO_NO_SCONTI_DUE
				//"Prodotto aggiunto -2o controllo-"
			else if ((n_Numero = DUE) and ((scUno = VERDURAB_S) or (scDue = VERDURAB_S))) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
				//"Prodotto non aggiunto -2o controllo-"
			endif 
		endif 
		endpar
	endif

		
	if ((pezzo = CARNEA) and ((scUno != CARNEA_S) and (scDue != CARNEA_S))) then
		mess:= AGGIUNTO_NO_SCONTI
		//"aggiunto no sconti"
	endif
		
	if ((pezzo = CARNEA) and ((scUno = CARNEA_S) or (scDue = CARNEA_S))) then
		par
			
		if (n_Numero = ZERO) then
			par
				s_Scelta := CARNEA_SCELTA
				n_Numero := UNO
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO
				//"prodotto aggiungo al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = CARNEA_SCELTA) and (pezzo = CARNEA)) then
			mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE
			//"Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != CARNEA_SCELTA) and (pezzo = CARNEA)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//"Prodotto aggiunto //con prodotto diverso da scontati con contr=UNO" 
				s_Scelta := CARNEA_SCELTA
				n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != CARNEA_S) and (scDue != CARNEA_S))) then
				mess:= AGGIUNTO_NO_SCONTI_DUE
				//"Prodotto aggiunto -2o controllo-"
			else if ((n_Numero = DUE) and ((scUno = CARNEA_S) or (scDue = CARNEA_S))) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
				//"Prodotto non aggiunto -2o controllo-"
			endif
		endif
		endpar
	endif
	
		
	if ((pezzo = CARNEB) and ((scUno != CARNEB_S) and (scDue != CARNEB_S))) then
		mess:= AGGIUNTO_NO_SCONTI
		// "aggiunto no sconti"
	endif
		
	if ((pezzo = CARNEB) and ((scUno = CARNEB_S) or (scDue = CARNEB_S))) then
		par
			
		if (n_Numero = ZERO) then
			par
				s_Scelta := CARNEB_SCELTA
				n_Numero := UNO
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO
				//"prodotto aggiungo al carrello"
			endpar
		endif
			
		if ((n_Numero = UNO) and (s_Scelta = CARNEB_SCELTA) and (pezzo = CARNEB)) then
			mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE
			// "Il prodotto scelto non  selezionabile, cambiare prodotto effettuando un nuovo acquisto" 
			else if ((n_Numero = UNO) and (s_Scelta != CARNEB_SCELTA) and (pezzo = CARNEB)) then
				par
				mess:= PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO
				//"Prodotto aggiunto //con prodotto diverso da scontati con contr=UNO" 
				s_Scelta := CARNEB_SCELTA
				n_Numero := DUE
				endpar
			endif
		endif
		
		if ((n_Numero = DUE) and ((scUno != CARNEB_S) and (scDue != CARNEB_S))) then
				mess:= AGGIUNTO_NO_SCONTI_DUE
				// "Prodotto aggiunto -2¡ controllo-"
			else if ((n_Numero = DUE) and ((scUno = CARNEB_S) or (scDue = CARNEB_S))) then
				mess:= PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE
				// "Prodotto non aggiunto -2o controllo-"
			endif 
		endif 
		endpar 
	endif
	verificaver := false
	sceltaverifica := false
	endpar


//dato s0 con condizioni false per "vialibera", "passaggio", "sceltaverifica", "verificaver" esiste almeno uno stato futuro con condizioni true
invariant over vialibera: ag(vialibera=false) implies ef(vialibera=true) 
invariant over passaggio: ag(passaggio=false) implies ef(passaggio=true) 
invariant over sceltaverifica: ag(sceltaverifica=false) implies ef(sceltaverifica=true) 
invariant over verificaver: ag(verificaver=false) implies ef(verificaver=true)

//nel futuro la scelta del cliente ricadra' sull'acquistare un prodotto o terminare la spesa
invariant over selectAttivita: eg(selectAttivita = ACQUISTA) implies eg(selectAttivita = ACQUISTA)
invariant over selectAttivita: eg(selectAttivita = FINESPESA) implies eg((selectAttivita = FINESPESA) and (mess= SPESA_FINITA_DIGITARE_ACQUISTA_PER_NUOVO_CLIENTE))
//Generiche
//invariant over selectAttivita: ag(ef(selectAttivita = FINESPESA))
//invariant over selectAttivita: ag(ef(selectAttivita = ACQUISTA))


//esiste uno stato in cui il numero di prodotti acquistati in sconto equivale a ZERO/UNO
//dove in futuro il contatore passera' ad UNO/DUE per via dell'acquisto di un qualsiasi prodotto scontato
//Generiche per n_Numero
invariant over n_Numero: ag(n_Numero = ZERO) implies ef(n_Numero = UNO)
invariant over n_Numero: ag(n_Numero = UNO) implies ef(n_Numero = DUE)
//Specifiche per il prodotto CARNEB, valido anche per tutti gli altri prodotti selezionabili
invariant over n_Numero: ag((n_Numero= ZERO) and (pezzo = CARNEB) and ((scUno = CARNEB_S) or (scDue = CARNEB_S))) implies ef((n_Numero = UNO) and (mess= PRODOTTO_AGGIUNTO_SI_SCONTO) and (s_Scelta = CARNEB_SCELTA))
invariant over n_Numero: ag((n_Numero= UNO) and (pezzo = CARNEB) and ((scUno = CARNEB_S) or (scDue = CARNEB_S))) implies ef((n_Numero = DUE) and (mess= PRODOTTO_AGGIUNTO_SI_SCONTO) and (s_Scelta = CARNEB_SCELTA))
invariant over n_Numero: ag((n_Numero= UNO) and (pezzo = CARNEB) and (s_Scelta = CARNEB_SCELTA)) implies ef(mess = PRODOTTO_NON_SELEZIONABILE_CAMBIARE)
invariant over n_Numero: ag((n_Numero= UNO) and (pezzo = CARNEB) and (s_Scelta != CARNEB_SCELTA)) implies ef(mess = AGGIUNTO_NO_SCONTI)
invariant over n_Numero: ag((n_Numero= DUE) and (pezzo = CARNEB) and ((scUno = CARNEB_S) or (scDue = CARNEB_S)) implies ef(mess = PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE))
invariant over n_Numero: af((n_Numero= DUE) and (pezzo = CARNEB) and ((scUno != CARNEB_S) and (scDue != CARNEB_S)) implies ef(mess = AGGIUNTO_NO_SCONTI_DUE))


//assioma variabili "scUno" e "scDue"
invariant over scUno: ag(ef((scUno = PASTAA_S) or (scUno = PASTAB_S) or (scUno = CARNEA_S) or (scUno = CARNEB_S) or (scUno = VERDURAA_S) or (scUno = VERDURAB_S)))
invariant over scDue: ag(ef((scDue = PASTAA_S) or (scDue = PASTAB_S) or (scDue = CARNEA_S) or (scDue = CARNEB_S) or (scDue = VERDURAA_S) or (scDue = VERDURAB_S)))
invariant over scUno: ag(ef(scUno != scDue))

//assioma variabile "pezzo"
invariant over pezzo: ag(ef((pezzo = PASTAA) or (pezzo = PASTAB) or (pezzo = CARNEA) or (pezzo = CARNEB) or (pezzo = VERDURAA) or (pezzo = VERDURAB)))

//assioma variabile "s_Scelta"
invariant over s_Scelta: ag(ef((s_Scelta = PASTAA_SCELTA) or (s_Scelta = PASTAB_SCELTA) or (s_Scelta = CARNEA_SCELTA) or (s_Scelta = CARNEB_SCELTA) or (s_Scelta = VERDURAA_SCELTA) or (s_Scelta = VERDURAB_SCELTA)))

//assioma variabile "mess"
invariant over mess: ag(ef((mess = SPESA_FINITA_DIGITARE_ACQUISTA_PER_NUOVO_CLIENTE) or (mess = AGGIUNTO_NO_SCONTI) or (mess = PRODOTTO_AGGIUNTO_SI_SCONTO) or (mess = PRODOTTO_NON_SELEZIONABILE_CAMBIARE) or (mess = PRODOTTO_AGGIUNTO_SI_SCONTO_DIVERSO) or (mess = AGGIUNTO_NO_SCONTI_DUE) or (mess = PRODOTTO_NON_SELEZIONABILE_CAMBIARE_DUE) or (mess = AGGIUNTO_SI_SCONTO)))

//Definizione della regola principale
	main rule r_main =
			
	if (vialibera = false and passaggio = false) then
			par
				r_sconto1[] 
				r_sconto2[]
			endpar
		
			
		else if (vialibera = false and passaggio = true) then
				r_check[]
		
		else if (vialibera = true and passaggio= true and sceltaverifica = false) then 
				r_scelta[]
		
		else if (sceltaverifica = true and selectAttivita = ACQUISTA and verificaver = false) then
				r_buy[]
					
		else if (verificaver = true) then
				r_verifica[]
		else
				r_end[] 
				
		endif		
		endif	
		endif
		endif	
	endif	
			
			
//Definizione dello stato iniziale
default init s0:
	function n_Numero = ZERO
	function vialibera = false
	function passaggio = false
	function sceltaverifica = false
	function verificaver = false
	

