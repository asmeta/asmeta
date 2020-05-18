// ATM
// versione di maggio 2007 PA e AG
//

asm ATM

import ../../STDL/StandardLibrary

signature:

	abstract domain NumCard
	enum domain State = { AWAITCARD | AWAITPIN | CHOOSE | OUTOFSERVICE | CHOOSEAMOUNT | STANDARDAMOUNTSELECTION | OTHERAMOUNTSELECTION}
	domain MoneySize subsetof Integer //tagli selezionabili
	enum domain Service = {BALANCE | WITHDRAWAL | EXIT}
	enum domain MoneySizeSelection = {STANDARD | OTHER}
	enum domain PinDomain = {AA | BB | CC | DD}
	
	domain Money subsetof Integer

	dynamic controlled currCard: NumCard//numero della carta presente nel bancomat
	dynamic controlled atmState: State
	//dynamic controlled outMess: Any//messaggio stampato sul video
	static pin: NumCard -> PinDomain
	dynamic controlled accessible: NumCard -> Boolean
	dynamic controlled moneyLeft: Money
	dynamic controlled balance: NumCard -> Money

	dynamic monitored insertedCard: NumCard //numero della carta inserita
	dynamic monitored insertedPin: PinDomain //numero di pin inserito
	dynamic monitored selectedService: Service //l'utente puo' scegliere di visualizzare il conto o effettuare un prelievo
	dynamic monitored insertMoneySize: Money
	dynamic monitored standardOrOther: MoneySizeSelection //

	derived allowed: Prod(NumCard, Money) -> Boolean

	static card1: NumCard
	static card2: NumCard
	static card3: NumCard


	static minMoney: Money
	static maxPrelievo: Money
	
	
	dynamic controlled execGoOutOfService: Boolean

definitions:
	domain MoneySize = {10, 20, 40, 50, 100, 150, 200} //tagli prestabiliti per il prelievo
	
	domain Money = {0..6200}
	
	function minMoney = 200 //quantita' minima di soldi necessaria per il funzionamento del bancomat
	function maxPrelievo = 1000

	function pin($c in NumCard) =
		switch($c)
			case card1: AA
			case card2: BB
			case card3: CC
		endswitch



	//il prelievo e' possibile se la cifra richiesta e' inferiore al saldo
	function allowed($c in NumCard, $m in Money) =
		balance($c) >= $m


	macro rule r_subtractFrom ($c in NumCard, $m in Money) =
		balance($c) := balance($c) - $m


	//controlla che ci sia una quantita' minima di soldi
	macro rule r_goOutOfService =
		if (moneyLeft < minMoney) then
			//par
				atmState := OUTOFSERVICE
				//outMess := "Out of Service"
			//endpar
		endif


	macro rule r_insertcard =
		if(atmState=AWAITCARD) then
			if(exist $c in NumCard with $c=insertedCard) then
				par
					currCard := insertedCard
					atmState := AWAITPIN
					//outMess := "Enter pin"
				endpar
			endif
		endif


	macro rule r_enterPin =
		forall $c in NumCard with $c=currCard do
			if(atmState=AWAITPIN) then
				//il pin deve essere corretto e l'account non deve essere bloccato su precedenti operazioni pendenti
				if(insertedPin=pin($c) and accessible($c)) then
					//par
						//outMess := "Choose service"
						atmState := CHOOSE
					//endpar
				else
					//par
						atmState := AWAITCARD//viene restituita la carta e il bancomat attende una nuova carta
						//if(insertedPin!=pin(currCard)) then
							//outMess := "Wrong pin"
						//endif
						//if(not(accessible((currCard))) and insertedPin=pin(currCard)) then
							//outMess := "Account non accessible"
						//endif
					//endpar
				endif
			endif


	macro rule r_chooseService =
		if(atmState=CHOOSE) then
			par
				//if(selectedService=BALANCE) then
				//	outMess := balance(currCard) //stampa a video del saldo
				//endif
				if(selectedService=WITHDRAWAL) then
					//par
						atmState := CHOOSEAMOUNT //l'utente deve selezionare una cifra standard o inserire la cifra
						//outMess := "Choose Standard or Other"
					//endpar
				endif
				if(selectedService=EXIT) then
					//par
						atmState := AWAITCARD //l'utente ha scelto di uscire
						//outMess := "Goodbye"
					//endpar
				endif
			endpar
		endif


	rule r_chooseAmount =
		if(atmState=CHOOSEAMOUNT) then
			par
				if(standardOrOther=STANDARD) then
					//par
						atmState := STANDARDAMOUNTSELECTION
						//outMess := "Select a money size"
					//endpar
				endif
				if(standardOrOther=OTHER) then
					//par
						atmState := OTHERAMOUNTSELECTION
						//outMess := "Enter money size"
					//endpar
				endif
			endpar
		endif


	rule r_grantMoney($m in Money) =
		forall $c in NumCard with $c=currCard do
			par
				r_subtractFrom[$c, $m]//sottrae dal conto la cifra richiesta
				moneyLeft := moneyLeft - insertMoneySize //i soldi nel bancomat diminuiscono
				seq
					accessible($c) := false //l'account non e' accessibile fino a quando non viene sbloccato dal sistema centrale
					accessible($c) := true //il sistema sblocca l'account (dovrebbe essere un altro agente)
				endseq
				atmState := AWAITCARD //viene espulsa la carta e il bancomat aspetta un'altra carta
				//outMess := "Goodbye"
			endpar

	macro rule r_processMoneyRequest ($m in Integer) =
		forall $c in NumCard with $c=currCard do
			if(allowed($c, $m)) then
				r_grantMoney[$m]
			//else
				//outMess := "Not enough money in your count"
			endif

	macro rule r_prelievo =
	forall $n in MoneySize with $n=insertMoneySize do
		par 
			if(atmState=STANDARDAMOUNTSELECTION) then
				if(exist $m in MoneySize with $m=$n) then
					if(insertMoneySize<=moneyLeft) then
						r_processMoneyRequest [$m]
					//else
						//outMess := "Il bancomat non ha abbastanza soldi"
					endif
				endif
			endif
			if(atmState=OTHERAMOUNTSELECTION) then
				if(mod($n, 10)=0) then
					if($n<=moneyLeft) then
						r_processMoneyRequest [$n]
					//else
						//outMess := "Il bancomat non ha abbastanza soldi"
					endif
				//else
					//outMess := "Tagli non compatibili"
				endif
			endif
		endpar


	main rule r_Main =
		/*seq
			r_goOutOfService[]
			par
				r_insertcard[]
				r_enterPin[]
				r_chooseService[]
				r_chooseAmount[]
				r_prelievo[]
			endpar
		endseq*/
		par
			execGoOutOfService := not(execGoOutOfService)
			if(execGoOutOfService) then
				r_goOutOfService[]
			else
				par
					r_insertcard[]
					r_enterPin[]
					r_chooseService[]
					r_chooseAmount[]
					r_prelievo[]
				endpar
			endif
		endpar


// define the initial states
default init s0:
	function atmState = AWAITCARD //all'inizio il bancomat e' in attesa di una carta
	function moneyLeft = 1000 //soldi disponibili all'inizio nel bancomat
	function balance($c in NumCard) = switch($c)
										case card1 : 3000
										case card2 : 1652
										case card3 : 548
									endswitch
	function accessible($c in NumCard) = true
	
	function execGoOutOfService = true 
