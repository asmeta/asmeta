// DOPO DUE SKIP SI CONSIDERA ERRORE
// IL PROGRAMMA NON VA ALL'INFINITO, MA PRIMA O POI TERMINA SEMPRE
// OTTIMIZZATO DAL PUNTO DI VISTA DELLE REGOLE

//modified version of certifierRaff5 for doing refinement proof
//domains "Answers" and "OutMessage" have been renamed as "AnswersRaff5" and "OutMessageRaff5"
//also functions "currentAnswer" and "outMessage" have been renamed as "currentAnswerRaff5" and "outMessageRaff5"
asm certifierRaff5ForSMTproof

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary


signature:
// DOMAINS
	enum domain AnswersRaff5={UNDEFA | SKIP_RAFF5 | EXIT_RAFF5 | AA_RAFF5 | BB_RAFF5 | CC_RAFF5 | DD_RAFF5}  //risoste che pue' dare l'utente: salta, esci, figura AA, BB, CC, DD
	enum domain Shapes = {AAA | BBB | CCC | DDD}  //risposte che puo' dare l'utente: salta, esci, figura AA, BB, CC, DD
	enum domain OutMessageRaff5={UNDEFM | CERTIFICATERAFF5 | NOTCERTIFICATERAFF5}
	domain Level subsetof Integer //livello che sta certificando
	domain Certificate subsetof Integer // livello certificato
	domain RightAnswer subsetof Integer


// FUNCTIONS
	dynamic controlled test: Boolean //true:esegui il test false:ferma il test
	dynamic controlled outMessageRaff5: OutMessageRaff5 //messaggio a video
	dynamic controlled currentAnswerRaff5: AnswersRaff5 //oggetto da indovinare
	dynamic monitored choosenShape : Shapes
	dynamic monitored getAnswer: AnswersRaff5 //risposta dell'utente
	dynamic controlled levelTest: Level //livello che si sta certificando
	dynamic controlled levelCertificate: Certificate //livello certificato
	dynamic controlled rightAnswer: RightAnswer //per certificare un livello testalo 3 volte
	dynamic controlled answerError: Boolean //risposte sbagliate durante la certificazione del livello se =2 salgo di un livello
	dynamic controlled answerSkip: Boolean // non puo' fare piu' di uno skip per ogni livello, se fa piu' di uno skip esce con NON CERTIFICATO
	dynamic controlled loop: Boolean /*serve per evitare un ciclo infinito.
									Esempio (senza var loop): se certifico il livello 6 passo al 5
									dopo provo a certificare il 4 se non lo certifico ritorno al 5
									certifico il 5 e passo al 4 se non lo certifico ritorno al 5 e cos� via
									Esempio (con var loop): se certifico il livello 6 passo al 5
									dopo provo a certificare il livello 4 se non lo certifico la variabile loop diventa true
									certifico il 5 la variabile loop e' a true quindi mi fermo e visualizzo che ho
									//certificato il 5 in quanto il 4 ho gia' provato e non riesco a certificarlo*/


definitions:
// DOMAIN DEFINITIONS
	domain Level={1 : 6}
	domain Certificate={1 : 7} //7 non certificato 1..6 livello certificato
	domain RightAnswer={0 : 2}


// FUNCTION DEFINITIONS


// RULE DEFINITIONS


	// sceglie un'immagine che deve indovinare l'utente a caso tra quelle del dominio Answers eccetto SKIP ed EXIT
	// l'immagine scelta nello stato N dovr� essere indovinata dall'utente al passo N+1
	macro rule r_chooseshape=
	   switch choosenShape 
	   		case AAA : currentAnswerRaff5 := AA_RAFF5
	  		case BBB : currentAnswerRaff5 := BB_RAFF5
	  		case CCC : currentAnswerRaff5 := CC_RAFF5
	   		case DDD : currentAnswerRaff5 := DD_RAFF5
	   endswitch


	// il test � terminato senza aver certificato un livello
	macro rule r_goOut=
		par
			test:=false
			levelCertificate:=7
		endpar
		
	
	// l'utente ha sbagliato due volte un livello, quindi risale di uno e indica come certificato il livello precedente
	// a quello che si sta certificando
	macro rule r_goUp=
		par
			levelTest:=levelTest+1
			answerError:=false
			answerSkip:=false	
			rightAnswer:=0
			levelCertificate:=levelCertificate+1
		endpar

	
	macro rule r_goUpCheckLevel=
		// se l'utente sbaglia due volte ma non � a livello 6  esegui la regola r_2ErrorGoBack
		if levelTest<6 then
			r_goUp[]
		else
			// se l'utente � a livello 6 e sbaglia due volte termina il test con NONCERTIFICATO
			test:=false
		endif
	

	// se l'utente risponde in modo sbagliato o digita SKIP due volte nello stesso livello
	macro rule r_wrong=
		// se e' il primo errore memorizza che sta risalendo (loop=true) e verifica a quale livello � con la regola r_wrongCheckLevel
		par
			loop:=true
			r_goUpCheckLevel[]
		endpar
		

	
	// se l'utente da la risposta sbagliata
	macro rule r_wrongAnswer=
		// se e' il primo errore incrementa il contatore
		if (answerError) then
		// se e' il secondo errore esegui r_wrong
			r_wrong[]
		else
			answerError:=true
		endif


	// se l'utente da la risposta corretta e non � mai tornato indietro di un livello passa al livello successivo
	// e certifica il livello appena testato
	macro rule r_rightAnswNoLoop=
		par
			answerError:=false
			answerSkip:=false
			levelTest:=levelTest-1
			levelCertificate:=levelCertificate-1
		endpar


	// se � al livello 1 e la risposta data � corretta, si deve certificare il livello 1
	// per certificarlo l'utente deve indovinare per 3 volte le immagini scelte dall'asm
	// certificato memorizza quante volte l'utente ha indovinato le immagini per certificare il livello
	// una volta certificato pone test=false e al ciclo successivo viene esguita la regola r_exit che uscir�
	// con risultato CERTIFICATO
	macro rule r_certificate=
		if rightAnswer<=1 then
			rightAnswer:=rightAnswer+1
		else
			par
				levelCertificate:=levelCertificate-1
				test:=false
			endpar
		endif
		
		

	// se non � alivello 1 e la risposta data � corretta vengono gestite due situazioni
	macro rule r_goDown=
		if loop=false then
			// caso in cui si parte dal livello 6 e si sta scendendo
			r_rightAnswNoLoop[]
		else
			// caso in cui si � sbagliato un livello e quindi si sale di uno per certificare quello precedente
			r_certificate[]
		endif
	
	
	// se la risposta � giusta e non � al livello 1 esegui r_levelno1[] altrimenti r_level1[]
	macro rule r_rightAnswer=
		if levelTest>1 then
			r_goDown[]
		else 
			r_certificate[]
		endif


	// confronta la risposta dell'utente con quella scelta dall'asm
	macro rule r_checkanswer($a in AnswersRaff5)=
		if ($a = currentAnswerRaff5) then
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
			if (answerSkip) then
				r_wrong[]
			else
				answerSkip:=true
			endif
			skip
		endpar


	macro rule r_test=	
		par
			r_chooseshape[]
			if (currentAnswerRaff5!=UNDEFA) then
				// se l'utente digita EXIT si esegue la regola per uscire
				if (getAnswer=EXIT_RAFF5) then
					r_goOut[]
				else
					// se l'utente digita SKIP si esegue la regola di skip
					if (getAnswer=SKIP_RAFF5) then
						r_skip[]
					else
						// altrimenti se l'utente da una risposta di confronta con quella scelta dall'asm
						r_checkanswer[getAnswer]
					endif
				endif
			endif
		endpar



	// esce dal test con CERTIFICATO se e' stato certificato un livello
	// NONCERTIFICATO se l'utente ha digitato EXIT oppure non ha certificato nessun livello (non indovina il livello 6) 
	macro rule r_exit=
		if outMessageRaff5 = UNDEFM then 
			if levelCertificate=7 then
				outMessageRaff5:=NOTCERTIFICATERAFF5
			else
		   		outMessageRaff5:=CERTIFICATERAFF5
			endif
    	endif


//1
CTLSPEC af(not test)
//2
CTLSPEC af(outMessageRaff5=CERTIFICATERAFF5) or ef (outMessageRaff5=NOTCERTIFICATERAFF5)
//3
//CTLSPEC ef (not test and levelTest=1)
CTLSPEC not(ef (not test and levelTest=1)) 
//4
//CTLSPEC ag(answerError<2)
//5
// genera uno scenario in cui l'utente digita EXIT e test diventa falso
CTLSPEC ag(test)
//6 --> non esiste uno stato in cui test=falso e outMessageRaff5=CERTIFICATO
// genera uno scenario in cui test= falso e outMessageRaff5=CERTIFICATO
CTLSPEC not ef (not test and outMessageRaff5=CERTIFICATERAFF5)


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
// il test e' iniziato, ma non ho certificato nessun livello
// inizio a certificare dal livello 6
default init s0:
	function test = true
	function levelTest=6
	function levelCertificate=7
	function rightAnswer=0
	function answerError=false
	function answerSkip=false
	function loop=false
	function currentAnswerRaff5=UNDEFA
	function outMessageRaff5 = UNDEFM

