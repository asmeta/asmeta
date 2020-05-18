// DOPO DUE SKIP SI CONSIDERA ERRORE
// IL PROGRAMMA NON VA ALL'INFINITO, MA PRIMA O POI TERMINA SEMPRE
// OTTIMIZZATO DAL PUNTO DI VISTA DELLE REGOLE

asm certifierRaff1

import StandardLibrary
import CTLlibrary


signature:
// DOMAINS
	enum domain Answers={SKIP| EXIT | WRONG | RIGHT}  //user answers: SKIP, EXIT, RIGHT, WRONG
	enum domain OutMessage={CERTIFICATE | NOTCERTIFICATE} // Certificate --> level certified, NotCertificate --> level not certified
	domain Level subsetof Integer //level the user is certifying
	domain Certificate subsetof Integer // level certified


// FUNCTIONS
	dynamic controlled test2: Boolean
	dynamic controlled test: Boolean //true:test is running false:test is finished
	dynamic controlled outMessage: OutMessage //out message: Certificate --> level certified, NotCertificate --> level not certified
	dynamic monitored getAnswer: Answers //user answer --> it is monitored
	dynamic controlled levelTest: Level //level the user is certifying
	dynamic controlled levelCertificate: Certificate // level certified
	dynamic controlled loop: Boolean /* Memorized if the user gone up of at least one level during certification process*/
									//serve per evitare un ciclo infinito.
									//Esempio (senza var loop): se certifico il livello 6 passo al 5
									//dopo provo a certificare il 4 se non lo certifico ritorno al 5
									//certifico il 5 e passo al 4 se non lo certifico ritorno al 5 e cos� via
									//Esempio (con var loop): se certifico il livello 6 passo al 5
									//dopo provo a certificare il livello 4 se non lo certifico la variabile loop diventa true
									//certifico il 5 la variabile loop � a true quindi mi fermo e visualizzo che ho
									//certificato il 5 in quanto il 4 ho gi� provato e non riesco a certificarlo*/
	dynamic controlled answerSkip: Boolean // it is allowed only one skip for each level
										// if the user skips twice the test goes up of one level



definitions:
// DOMAIN DEFINITIONS
	domain Level={1..6}
	domain Certificate={1..7} //7 not certified 1..6 level certified


// FUNCTION DEFINITIONS


// RULE DEFINITIONS


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
			answerSkip:=false	
			levelCertificate:=levelCertificate+1
		endpar

	
	macro rule r_goUpCheckLevel=
		// se l'utente sbaglia ma non � a livello 6  esegui la regola r_goUp (sali di un livello)
		if levelTest<6 then
			r_goUp[]
		else
			// se l'utente � a livello 6 e sbaglia termina il test con NONCERTIFICATO
			test:=false
		endif
	

	// se l'utente risponde in modo sbagliato o digita SKIP due volte nello stesso livello
	macro rule r_wrongAnswer=
		// se sbaglia/fa due skip nello stesso livello memorizza che sta risalendo (loop=true) e verifica a quale livello � con la regola r_goUpCheckLevel
		par
			loop:=true
			r_goUpCheckLevel[]
		endpar
		

	// se l'utente da la risposta corretta e non � mai tornato indietro di un livello passa al livello successivo
	// e certifica il livello appena testato
	macro rule r_rightAnswNoLoop=
		par
			answerSkip:=false
			levelTest:=levelTest-1
			levelCertificate:=levelCertificate-1
		endpar


	// se � al livello 1 e la risposta data � corretta, certifica il livello 1
	//e termina il test
	//se � a livello n e la risposta data � corretta, certifica il livello n
	//e termina il test
	macro rule r_certificate=
		par
			levelCertificate:=levelCertificate-1
			test:=false
		endpar


	// se non � a livello 1 e la risposta data � corretta vengono gestite due situazioni
	macro rule r_goDown=
		if loop=false then
			// caso in cui si parte dal livello 6 e si sta scendendo senza aver commesso un errore
			r_rightAnswNoLoop[]
		else
			// caso in cui si � sbagliato un livello (salendo di uno) e si certifica il livello corrente
			r_certificate[]
		endif

	
	
	// se la risposta � giusta e non � al livello 1 esegui r_goDown[] altrimenti r_certificate[]
	macro rule r_rightAnswer=
		if levelTest>1 then
			r_goDown[]
		else 
			r_certificate[]
		endif


	// verifica la risposta data
	macro rule r_checkanswer($a in Answers)=
		if ($a = RIGHT) then
			// se � corretta esegui la regola r_rightAnswer[]
			r_rightAnswer[]
		else
			// se � sbagliata esegui la regola r_wrongAnswer[]
			r_wrongAnswer[]
		endif


	// all'utente � permesso digitare SKIP solo due volte
	// la seconda volta viene considerato come l'errore, quindi torna al livello precedente (regola: r_wrongAnswer[])
	macro rule r_skip=
		if (answerSkip) then
			r_wrongAnswer[]
		else
			answerSkip:=true
		endif


	macro rule r_test=	
		// se l'utente digita EXIT si esegue la regola per uscire
		if (getAnswer=EXIT) then
			r_goOut[]
		else
			// se l'utente digita SKIP si esegue la regola di skip
			if (getAnswer=SKIP) then
				r_skip[]
			else
				// altrimenti se l'utente da una risposta verifica la risposta data
			r_checkanswer[getAnswer]
			endif
		endif



	// esce dal test con CERTIFICATO se � stato certificato un livello
	// NONCERTIFICATO se l'utente ha digitato EXIT oppure non ha certificato nessun livello (non indovina il livello 6) 
	macro rule r_exit=
		if levelCertificate=7 then
			outMessage:=NOTCERTIFICATE
		else
	   		outMessage:=CERTIFICATE
		endif


//1
CTLSPEC af(not test)
//2
CTLSPEC af(outMessage=CERTIFICATE) or ef (outMessage=NOTCERTIFICATE)
//3
//CTLSPEC ef (not test and levelTest=1)
CTLSPEC not(ef (not test and levelTest=1)) 
//4
//CTLSPEC ag(answerError<2)
//5
// genera uno scenario in cui l'utente digita EXIT e test diventa falso
CTLSPEC ag(test)
//6 --> non esiste uno stato in cui test=falso e outMessage=CERTIFICATO
// genera uno scenario in cui test= falso e outMessage=CERTIFICATO
CTLSPEC not ef (not test and outMessage=CERTIFICATE)


//livello certificato � maggiore o uguale al livello che si sta certificando
invariant over levelTest: levelCertificate>=levelTest

// MAIN RULE
	main rule r_Main =
		if (test2) then
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
	function levelTest=6
	function levelCertificate=7
	function answerSkip=false
	function loop=false

