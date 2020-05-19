asm certifierGround

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary


signature:
// DOMAINS
	enum domain Answers={EXIT | WRONG | RIGHT}  //user answers: EXIT, RIGHT, WRONG
	enum domain OutMessage={CERTIFICATE | NOTCERTIFICATE} // Certificate --> level certified, NotCertificate --> level not certified
	domain Level subsetof Integer //level the user is certifying
	domain Certificate subsetof Integer // level certified


// FUNCTIONS
	dynamic controlled test: Boolean //true:test is running false:test is finished
	dynamic controlled outMessage: OutMessage //out message: Certificate --> level certified, NotCertificate --> level not certified
	dynamic monitored getAnswer: Answers //user answer --> it is monitored
	dynamic controlled levelTest: Level //level the user is certifying
	dynamic controlled levelCertificate: Certificate // level certified
	dynamic controlled loop: Boolean /* Memorized if the user gone up of at least one level during certification process*/
									//serve per evitare un ciclo infinito.
									//Esempio (senza var loop): se certifico il livello 6 passo al 5
									//dopo provo a certificare il 4 se non lo certifico ritorno al 5
									//certifico il 5 e passo al 4 se non lo certifico ritorno al 5 e così via
									//Esempio (con var loop): se certifico il livello 6 passo al 5
									//dopo provo a certificare il livello 4 se non lo certifico la variabile loop diventa true
									//certifico il 5 la variabile loop è a true quindi mi fermo e visualizzo che ho
									//certificato il 5 in quanto il 4 ho già provato e non riesco a certificarlo*/


definitions:
// DOMAIN DEFINITIONS
	domain Level={1 : 6}
	domain Certificate={1 : 7} //7 not certified 1..6 level certified


// FUNCTION DEFINITIONS


// RULE DEFINITIONS

	// il test è terminato senza aver certificato un livello
	macro rule r_goOut=
		par
			test:=false
			levelCertificate:=7
		endpar
		
	
	// l'utente ha sbagliato un livello, quindi risale di uno e indica come certificato il livello precedente
	// a quello che si sta certificando
	macro rule r_goUp=
		par
			levelTest:=levelTest+1
			levelCertificate:=levelCertificate+1
		endpar

	
	macro rule r_goUpCheckLevel=
		// se l'utente sbaglia ma non è a livello 6  esegui la regola r_goUp (sali di un livello)
		if levelTest<6 then
			r_goUp[]
		else
			// se l'utente è a livello 6 e sbaglia termina il test con NONCERTIFICATO
			test:=false
		endif
	

	// se l'utente risponde in modo sbagliato 
	macro rule r_wrongAnswer=
		// se sbaglia memorizza che sta risalendo (loop=true) e verifica a quale livello è con la regola r_goUpCheckLevel
		par
			loop:=true
			r_goUpCheckLevel[]
		endpar


	// se l'utente da la risposta corretta e non è mai tornato indietro di un livello passa al livello successivo
	// e certifica il livello appena testato
	macro rule r_rightAnswNoLoop=
		par
			levelTest:=levelTest-1
			levelCertificate:=levelCertificate-1
		endpar

	// se è al livello 1 e la risposta data è corretta, certifica il livello 1
	//e termina il test
	//se è a livello n e la risposta data è corretta, certifica il livello n
	//e termina il test
	macro rule r_certificate=
		par
			levelCertificate:=levelCertificate-1
			test:=false
		endpar

	// se non è a livello 1 e la risposta data è corretta vengono gestite due situazioni
	macro rule r_goDown=
		if loop=false then
			// caso in cui si parte dal livello 6 e si sta scendendo senza aver commesso un errore
			r_rightAnswNoLoop[]
		else
			// caso in cui si è sbagliato un livello (salendo di uno) e si certifica il livello corrente
			r_certificate[]
		endif

	
	// se la risposta è giusta e non è al livello 1 esegui r_goDown[] altrimenti r_certificate[]
	macro rule r_rightAnswer=
		if levelTest>1 then
			r_goDown[]
		else 
			r_certificate[]
		endif


	// verifica la risposta data
	macro rule r_checkanswer($a in Answers)=
		if ($a = RIGHT) then
			// se è corretta esegui la regola r_rightAnswer[]
			r_rightAnswer[]
		else
			// se è sbagliata esegui la regola r_wrongAnswer[]
			r_wrongAnswer[]
		endif


	macro rule r_test=
		// se l'utente digita EXIT si esegue la regola per uscire
		if (getAnswer=EXIT) then
			r_goOut[]
		else
			// altrimenti se l'utente da una risposta verifica la risposta data
			r_checkanswer[getAnswer]
		endif


	// esce dal test con CERTIFICATO se è stato certificato un livello
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
//CTLSPEC ef (not test and level=1)
//EF--> verifica che a partire da TUTTI gli stati iniziali si raggiunge test=false e level=1
//per dimostrare che esiste uno stato in cui test=False e level=1 a partire da almeno uno stato iniziale
// si dimostra la negata -->non esiste uno stato in cui test=False e level=1 a partire da almeno uno stato iniziale
// il model checker come controesempio mostrerà uno stato in cui test=false e level=1
CTLSPEC not(ef (not test and levelTest=1)) 
//4
//CTLSPEC ag(answerError<2)
//5
// genera uno scenario in cui l'utente digita EXIT e test diventa falso
CTLSPEC ag(test)
//6 --> non esiste uno stato in cui test=falso e outMessage=CERTIFICATO
// genera uno scenario in cui test= falso e outMessage=CERTIFICATO
CTLSPEC not ef (not test and outMessage=CERTIFICATE)


//livello certificato è maggiore o uguale al livello che si sta certificando
invariant over levelTest: levelCertificate>=levelTest
//se outMessage è CERTIFICATE  e loop = false (non è mai risalito) allora il livello certificato è 1
invariant over levelCertificate: ((outMessage=CERTIFICATE) and (loop=false)) implies levelCertificate=1
//se outMessage è CERTIFICATE  e loop = true (è risalito almeno una volta) allora il livello certificato è >1
invariant over levelCertificate: ((outMessage=CERTIFICATE) and (loop=true)) implies levelCertificate>1

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
// il test è iniziato, ma non ho certificato nessun livello
// inizio a certificare dal livello 6
default init s0:
	function test = true
	function levelTest=6
	function levelCertificate=7
	function loop=false

