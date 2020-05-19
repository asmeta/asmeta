asm certifierGroundWrongGuard

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary


signature:
// DOMAINS
	enum domain Answers={EXIT | WRONG | RIGHT}  //risoste che pu� dare l'utente: salta, esci, figura AA, BB, CC, DD
	enum domain OutMessage={CERTIFICATE | NOTCERTIFICATE}
	domain Level subsetof Integer //livello che sta certificando
	domain Certificate subsetof Integer // livello certificato


// FUNCTIONS
	dynamic controlled test: Boolean //true:esegui il test false:ferma il test
	dynamic controlled outMessage: OutMessage //messaggio a video
	dynamic monitored getAnswer: Answers //risposta dell'utente
	dynamic controlled levelTest: Level //livello che si sta certificando
	dynamic controlled levelCertificate: Certificate //livello certificato
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
	domain Level={1 : 6}
	domain Certificate={1 : 7} //7 non certificato 1..6 livello certificato



// FUNCTION DEFINITIONS


// RULE DEFINITIONS

	// il test � terminato senza aver certificato un livello
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
		// se l'utente sbaglia ma non � a livello 6  esegui la regola r_GoBack
		//if levelTest<6 then //correct version
		if levelTest<7 then //wrong version. Violation of metaproperty MP3
			r_goUp[]
		else
			// se l'utente � a livello 6 e sbaglia termina il test con NONCERTIFICATO
			test:=false
		endif
	

	// se l'utente risponde in modo sbagliato 
	macro rule r_wrongAnswer=
		// se sbaglia memorizza che sta risalendo (loop=true) e verifica a quale livello � con la regola r_wrongCheckLevel
		par
			loop:=true
			r_goUpCheckLevel[]
		endpar


	// se l'utente da la risposta corretta e non � mai tornato indietro di un livello passa al livello successivo
	// e certifica il livello appena testato
	macro rule r_rightAnswNoLoop=
		par
			levelTest:=levelTest-1
			levelCertificate:=levelCertificate-1
		endpar

	// se � al livello 1 e la risposta data � corretta, si deve certificare il livello 1
	// per certificarlo l'utente deve indovinare per 3 volte le immagini scelte dall'asm
	// certificato memorizza quante volte l'utente ha indovinato le immagini per certificare il livello
	// una volta certificato pone test=false e al ciclo successivo viene esguita la regola r_exit che uscir�
	// con risultato CERTIFICATO
	macro rule r_certificate=
		par
			levelCertificate:=levelCertificate-1
			test:=false
		endpar

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
	macro rule r_checkanswer($a in Answers)=
		if ($a = RIGHT) then
			// se � corretta esegui la regola r_rightAnswer[]
			r_rightAnswer[]
		else
			// se � sbagliata esegui la regola r_wrongAnswer[]
			r_wrongAnswer[]
		endif


	macro rule r_test=
		// se l'utente digita EXIT si esegue la regola per uscire
		if (getAnswer=EXIT) then
			r_goOut[]
		else
			// altrimenti se l'utente da una risposta di confronta con quella scelta dall'asm
			r_checkanswer[getAnswer]
		endif


	// esce dal test con CERTIFICATO se � stato certificato un livello
	// NONCERTIFICATO se l'utente ha digitato EXIT oppure non ha certificato nessun livello (non indovina il livello 6) 
	macro rule r_exit=
		if isUndef(outMessage) then 
			if levelCertificate=7 then
				outMessage:=NOTCERTIFICATE
			else
		   		outMessage:=CERTIFICATE
			endif
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
// il model checker come controesempio mostrer� uno stato in cui test=false e level=1
CTLSPEC not(ef (not test and levelTest=1)) 
//4
//CTLSPEC ag(answerError<2)
//5
// genera uno scenario in cui l'utente digita EXIT e test diventa falso
CTLSPEC ag(test)
//6 --> non esiste uno stato in cui test=falso e outMessage=CERTIFICATO
// genera uno scenario in cui test= falso e outMessage=CERTIFICATO
CTLSPEC not ef (not test and outMessage=CERTIFICATE)


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
	function levelTest=6
	function levelCertificate=7
	function loop=false

