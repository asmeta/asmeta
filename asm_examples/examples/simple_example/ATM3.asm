// ATM
// versione gennaio 2011. rimossa la seq nella main rule
//
//Requirements (Abstract State Machines - A Method for High-Level System Design and Analysis, page 92)
//"Design the control for an ATM, and show it to be well functioning,
//where via a GUI the customer can perform the following operations:
//Op1. Enter the ID. Only one attempt is allowed per session; upon
//failure the card is withdrawn.
//Op2. Ask for the balance of the account. This operation is allowed
//only once and only before attempting to withdraw money.
//Op3. Withdraw money from the account. Only one attempt is allowed
//per session. A warning is issued if the amount required
//exceeds the balance of the account."

asm ATM3

import ../../STDL/StandardLibrary

signature:
	abstract domain NumCard
	enum domain State = { AWAITCARD | AWAITPIN | CHOOSE | OUTOFSERVICE | CHOOSEAMOUNT | STANDARDAMOUNTSELECTION | OTHERAMOUNTSELECTION}
	domain MoneySize subsetof Integer //tagli selezionabili
	enum domain Service = {BALANCE | WITHDRAWAL | EXIT}
	enum domain MoneySizeSelection = {STANDARD | OTHER}

	dynamic controlled currCard: NumCard//numero della carta presente nel bancomat
	dynamic controlled atmState: State
	dynamic controlled outMess: Any//messaggio stampato sul video
	static pin: NumCard -> Integer
	dynamic controlled accessible: NumCard -> Boolean
	dynamic controlled moneyLeft: Integer
	dynamic controlled balance: NumCard -> Integer
	
	dynamic controlled numOfBalanceChecks: Integer //new

	dynamic monitored insertedCard: NumCard //numero della carta inserita
	dynamic monitored insertedPin: Integer //numero di pin inserito
	dynamic monitored selectedService: Service //l'utente puo' scegliere di visualizzare il conto o effettuare un prelievo
	dynamic monitored insertMoneySizeStandard: MoneySize
	dynamic monitored insertMoneySizeOther: Integer
	dynamic monitored standardOrOther: MoneySizeSelection

	derived allowed: Prod(NumCard, Integer) -> Boolean

	static card1: NumCard
	static card2: NumCard
	static card3: NumCard

	static minMoney: Integer
	static maxPrelievo: Integer

definitions:
	domain MoneySize = {10, 20, 40, 50, 100, 150, 200} //tagli prestabiliti per il prelievo
	function minMoney = 200 //quantita' minima di soldi necessaria per il funzionamento del bancomat
	function maxPrelievo = 1000

	function pin($c in NumCard) =
		switch($c)
			case card1 : 1
			case card2 : 2
			case card3 : 3
		endswitch

	//il prelievo e' possibile se la cifra richiesta e' inferiore al saldo
	function allowed($c in NumCard, $m in Integer) =
		balance($c) >= $m


	macro rule r_subtractFrom ($c in NumCard, $m in Integer) =
		balance($c) := balance($c) - $m


	//controlla che ci sia una quantita' minima di soldi
	macro rule r_goOutOfService =
		par
			atmState := OUTOFSERVICE
			outMess := "Out of Service"
		endpar

	macro rule r_insertcard =
		if(atmState=AWAITCARD) then
			if(exist $c in NumCard with $c=insertedCard) then
				par
					currCard := insertedCard
					atmState := AWAITPIN
					outMess := "Enter pin"
				endpar
			endif
		endif

	macro rule r_enterPin =
		if(atmState=AWAITPIN) then
			//il pin deve essere corretto e l'account non deve essere bloccato su precedenti operazioni pendenti
			if(insertedPin=pin(currCard) and accessible(currCard)) then
				par
					outMess := "Choose service"
					atmState := CHOOSE
					numOfBalanceChecks := 0 //new
				endpar
			else
				par
					atmState := AWAITCARD//viene restituita la carta e il bancomat attende una nuova carta
					if(insertedPin!=pin(currCard)) then
						outMess := "Wrong pin"
					endif
					if(not(accessible((currCard))) and insertedPin=pin(currCard)) then
						outMess := "Account non accessible"
					endif
				endpar
			endif
		endif

	macro rule r_chooseService =
		if(atmState=CHOOSE) then
			par
				if(selectedService=BALANCE) then
					//start of new version
					if(numOfBalanceChecks = 0) then
						par
							numOfBalanceChecks := numOfBalanceChecks + 1
							outMess := balance(currCard) //stampa a video del saldo
						endpar
					else
						par
							atmState := AWAITCARD //in attesa di un nuovo utente
							outMess := "You can check only once your balance. Goodbye."
						endpar
					endif
					//end of new version
				endif
				if(selectedService=WITHDRAWAL) then
					par
						atmState := CHOOSEAMOUNT //l'utente deve selezionare una cifra standard o inserire la cifra
						outMess := "Choose Standard or Other"
					endpar
				endif
				if(selectedService=EXIT) then
					par
						atmState := AWAITCARD //l'utente ha scelto di uscire
						outMess := "Goodbye"
					endpar
				endif
			endpar
		endif

	rule r_chooseAmount =
		if(atmState=CHOOSEAMOUNT) then
			par
				if(standardOrOther=STANDARD) then
					par
						atmState := STANDARDAMOUNTSELECTION
						outMess := "Select a money size"
					endpar
				endif
				if(standardOrOther = OTHER) then
					par
						atmState := OTHERAMOUNTSELECTION
						outMess := "Enter money size"
					endpar
				endif
			endpar
		endif

	rule r_grantMoney($m in Integer) =
		par
			accessible(currCard) := false //l'account non e' accessibile fino a quando non viene sbloccato dal sistema centrale
			r_subtractFrom[currCard, $m] //sottrae dal conto la cifra richiesta
			moneyLeft := moneyLeft - $m //i soldi nel bancomat diminuiscono
			atmState := AWAITCARD //viene espulsa la carta e il bancomat aspetta un'altra carta
			outMess := "Goodbye"
		endpar

	macro rule r_processMoneyRequest ($m in Integer) =
		if(allowed(currCard, $m)) then
			r_grantMoney[$m]
		else
			outMess := "Not enough money in your account"
		endif

	macro rule r_prelievo =
		par 
			if(atmState=STANDARDAMOUNTSELECTION) then
				if(exist $m in MoneySize with $m=insertMoneySizeStandard) then
					if(insertMoneySizeStandard <= moneyLeft) then
						r_processMoneyRequest[insertMoneySizeStandard]
					else
						outMess := "Il bancomat non ha abbastanza soldi"
					endif
				endif
			endif
			if(atmState=OTHERAMOUNTSELECTION) then
				if(mod(insertMoneySizeOther, 10) = 0) then
					if(insertMoneySizeOther <= moneyLeft) then
						r_processMoneyRequest[insertMoneySizeOther]
					else
						outMess := "Il bancomat non ha abbastanza soldi"
					endif
				else
					outMess := "Tagli non compatibili"
				endif
			endif
		endpar

	main rule r_Main =
		if (moneyLeft < minMoney) then
			r_goOutOfService[]
		else
			par
				r_insertcard[]
				r_enterPin[]
				r_chooseService[]
				r_chooseAmount[]
				seq
					r_prelievo[]
					//se la carta corrente non è accessibile,
					//vuol dire che c'è stato un prelievo
					if isDef(currCard) and not(accessible(currCard)) then
						accessible(currCard) := true //il sistema centrale sblocca l'account della carta corrente
					endif
				endseq
			endpar
		endif

default init s0:
	function atmState = AWAITCARD //all'inizio il bancomat e' in attesa di una carta
	function moneyLeft = 1000 //soldi disponibili all'inizio nel bancomat
	function balance($c in NumCard) = switch($c)
										case card1 : 3000
										case card2 : 1652
										case card3 : 548
									endswitch
	function accessible($c in NumCard) = true	