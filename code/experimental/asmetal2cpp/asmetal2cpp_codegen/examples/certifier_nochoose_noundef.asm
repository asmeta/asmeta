// come l'originale ma senza choose che
// altrimenti non riesco valutare copertura

asm certifier_nochoose_noundef

import STDL/StandardLibrary


signature:
// DOMAINS
	enum domain Answers= { UNDEFA | SKIP | EXIT | AA | BB | CC | DD}  //risoste che pu� dare l'utente: salta, esci, figura AA, BB, CC, DD
	enum domain Shapes = {AAA | BBB | CCC | DDD}  //risoste che pu� dare l'utente: salta, esci, figura AA, BB, CC, DD
	enum domain OutMessage={UNDEFM | CERTIFICATE | NOTCERTIFICATE}
	domain Level subsetof Integer //livello che sta certificando
	domain Certifier subsetof Integer // livello certificato
	domain AnswerError subsetof Integer // numero risposte errate
	domain Certificato subsetof Integer
	domain MaxSkip subsetof Integer // non pu� fare pi� di uno skip per ogni livello, se fa pi� di uno skip esce con NON CERTIFICATO


// FUNCTIONS
	dynamic controlled test: Boolean //true:esegui il test false:ferma il test
	dynamic controlled outMessage: OutMessage //messaggio a video
	dynamic controlled currentAnswer: Answers //oggetto da indovinare
	dynamic monitored getAnswer: Answers //risposta dell'utente
	dynamic monitored choosenShape : Shapes
	dynamic controlled level: Level //livello che si sta certificando
	dynamic controlled certifier: Certifier //livello certificato
	dynamic controlled certificato: Certificato //per certificare un livello testalo 3 volte
	dynamic controlled answerError: AnswerError //risposte sbagliate durante la certificazione del livello se =2 salgo di un livello
	dynamic controlled maxSkip: MaxSkip 
	dynamic controlled loop: Boolean /*serve per evitare un ciclo infinito.
									Esempio (senza var loop): se certifico il livello 6 passo al 5
									dopo provo a certificare il 4 se non lo certifico ritorno al 5
									certifico il 5 e passo al 4 se non lo certifico ritorno al 5 e cos� via
									Esempio (con var loop): se certifico il livello 6 passo al 5
									dopo provo a certificare il livello 4 se non lo certifico la variabile loop diventa true
									certifico il 5 la variabile loop � a true quindi mi fermo e visualizzo che ho
									//certificato il 5 in quanto il 4 ho gi� provato e non riesco a certificarlo*/


definitions:
// DOMAIN DEFINITIONS
	domain Level={1..6}
	domain Certifier={0 :6} //0 non certificato 1..6 livello certificato
	domain AnswerError={0 :1}
	domain Certificato={0 :2}
	domain MaxSkip={0 :1}


// FUNCTION DEFINITIONS


// RULE DEFINITIONS


	// sceglie un'immagine che deve indovinare l'utente a caso tra quelle del dominio Answers eccetto SKIP ed EXIT
	// l'immagine scelta nello stato N dovr� essere indovinata dall'utente al passo N+1
	macro rule r_chooseshape=
	   switch choosenShape 
	   case AAA : currentAnswer := AA 
	   case BBB : currentAnswer := BB
	   case CCC : currentAnswer := CC
	   case DDD : currentAnswer := DD
	   endswitch


	// il test � terminato senza aver certificato un livello
	macro rule r_goOut=
		par
			test:=false
			certifier:=0
		endpar
		
	
	// l'utente ha sbagliato due volte un livello, quindi risale di uno e indica come certificato il livello precedente
	// a quello che si sta certificando
	macro rule r_2ErrorGoBack=
		par
			level:=level+1
			answerError:=0
			maxSkip:=0
			if certifier=6 then
				certifier:=0
			else
				certifier:=certifier+1
			endif
		endpar

	
	macro rule r_wrongCheckLevel=
		// se l'utente sbaglia due volte ma non � a livello 6  esegui la regola r_2ErrorGoBack
		if level<6 then
			r_2ErrorGoBack[]
		else
			// se l'utente � a livello 6 e sbaglia due volte termina il test con NONCERTIFICATO
			test:=false
			//r_goOut[]	//mettere solo test=false senza certifier=0
		endif
	

	// se l'utente risponde in modo sbagliato o digita SKIP due volte nello stesso livello
	macro rule r_wrong=
		// se � il primo errore memorizza che sta risalendo (loop=true) e verifica a quale livello � con la regola r_wrongCheckLevel
		if loop=false then
			par
				loop:=true
				r_wrongCheckLevel[]
			endpar
		else
			// se � gi� risalito di un livello allora azzera la variabile certificato perch� potrebbe avere avviato 
			// la procedura di certificazione senza successo e verifica a quale livello � con la regola r_wrongCheckLevel
			par
				certificato:=0
				r_wrongCheckLevel[]
			endpar
		endif

	
	// se l'utente da la risposta sbagliata
	macro rule r_wrongAnswer=
		// se � il primo errore incrementa il contatore
		if answerError=0 then
			answerError:=answerError+1
		else
			// se � il secondo errore esegui r_wrong
			r_wrong[]
		endif


	// se l'utente ha risposto correttamente ad un livello certificalo
	// alternativa level:=certifier, ma essendo di due domini diversi non funziona
	macro rule r_setCertifier=
		if level=6 then
			certifier:=6
		else
			certifier:=certifier-1
		endif


	// se l'utente da la risposta corretta e non � mai tornato indietro di un livello passa al livello successivo
	// e certifica il livello appena testato
	macro rule r_rightAnswNoLoop=
		par
			answerError:=0
			maxSkip:=0
			level:=level-1
			//if certifier<=0 then
			r_setCertifier[]
		endpar


	// se l'utente ha fatto due errori ad un livello deve certificare il livello precedente
	macro rule r_rightAnswLoop=
		if certificato<=1 then
			// per poter certificare un livello l'utente deve indovinare tre volte
			certificato:=certificato+1
		else
			// dopo che ha indovinato 3 volte determina il livello certificato e termina il test
			par
				test:=false
				r_setCertifier[]
			endpar
		endif


	// se non � alivello 1 e la risposta data � corretta vengono gestite due situazioni
	macro rule r_levelno1=
		if loop=false then
			// caso in cui si parte dal livello 6 e si sta scendendo
			r_rightAnswNoLoop[]
		else
			// caso in cui si � sbagliato un livello e quindi si sale di uno per certificare quello precedente
			r_rightAnswLoop[]
		endif

	
	// se � al livello 1 e la risposta data � corretta, si deve certificare il livello 1
	// per certificarlo l'utente deve indovinare per 3 volte le immagini scelte dall'asm
	// certificato memorizza quante volte l'utente ha indovinato le immagini per certificare il livello
	// una volta certificato pone test=false e al ciclo successivo viene esguita la regola r_exit che uscir�
	// con risultato CERTIFICATO
	macro rule r_level1=
		if certificato<=1 then
			par
				certifier:=1
				certificato:=certificato+1
			endpar
		else
			test:=false
		endif

	
	// se la risposta � giusta e non � al livello 1 esegui r_levelno1[] altrimenti r_level1[]
	macro rule r_rightAnswer=
		if level>1 then
			r_levelno1[]
		else 
			r_level1[]
		endif


	// confronta la risposta dell'utente con quella scelta dall'asm
	// AG: MODIFIVIATO SENXZA PARAMETRI
	macro rule r_checkanswer =
		if (currentAnswer = getAnswer) then
			// se � corretta esegui la regola r_rightAnswer[]
			r_rightAnswer[]
		else
			// se � sbagliata esegui la regola r_wrongAnswer[]
			r_wrongAnswer[]
		endif


	// all'utente � permesso digitare SKIP solo due volte
	// la seconda volta viene considerato come l'errore, quindi torna al livello precedente (regola: r_wrong[])
	macro rule r_skip=
		par
			if maxSkip=0 then
				maxSkip:=1
			else
				r_wrong[]
			endif
			skip
		endpar


	macro rule r_test=
		par
			// sceglie casualmente un'immagine da mostrare all'utente
			r_chooseshape[]
			// usare isUndef() o isDef()
			// se sono nel primo stato scelgo solo l'immagine da indovinare 
			// che verr� confrontata con la risposta dell'utente nello stato successivo
			// se invece sono negli stati successivi si aspetta la risposta dell'utente 
			// per poterla confrontare con l'immagine scelta al passo precedente
			// e scelgo l'immagine che l'utente dovr� indovinare al passo successivo
			// S1: currentAnswer = undef
			// S2: currentAnswer = DD
			// S2: getAnswer = risposta utente
			if currentAnswer != UNDEFA then
				// se l'utente digita EXIT si esegue la regola per uscire
				if (getAnswer=EXIT) then
					r_goOut[]
				else
					// se l'utente digita SKIP si esegue la regola di skip
					if (getAnswer=SKIP) then
						r_skip[]
					else
						// altrimenti se l'utente da una risposta di confronta con quella scelta dall'asm
						r_checkanswer[]
					endif
				endif
			endif
		endpar


	// esce dal test con CERTIFICATO se � stato certificato un livello
	// NONCERTIFICATO se l'utente ha digitato EXIT oppure non ha certificato nessun livello (non indovina il livello 6) 
	macro rule r_exit=
		if outMessage != UNDEFM then 
			if certifier=0 then
				outMessage:=NOTCERTIFICATE
			else
		   		outMessage:=CERTIFICATE
			endif
    	endif



// MAIN RULE
	main rule r_Main =
		if (test) then
			// fase di test attiva
			r_test[]	
		else
			// fase di test terminata
			r_exit[]
		endif
		
		
// INITIAL STATE
// il test � iniziato, ma non ho certificato nessun livello
// inizio a certificare dal livello 6
default init s0:
	function test = true
	function level=6
	function certifier=0
	function certificato=0
	function answerError=0
	function loop=false
	function maxSkip=0
	function outMessage = UNDEFM
	function currentAnswer = UNDEFA 

