asm till

import ../STDL/StandardLibrary

signature:
	agent domain Till
	abstract domain Card
	abstract domain Account

	enum domain State = { AWAITCARD | AWAITPIN | CHOOSE |  VIEWBALANCE |CHOOSESIZEMONEY | WITHDRAWAL 
						| STATEMENT | FINISH}
	enum domain Service = {BALANCE | WITHDRAWAL | STATEMENT}

	dynamic controlled currCard: Till -> Card
	dynamic controlled tillState: Till -> State
	
	static pin: Card -> Natural
	derived isLegal: Card -> Boolean

	dynamic controlled outMess: String //for debugging

	dynamic controlled moneyLeft: Till -> Natural
	dynamic controlled balance: Account -> Natural //this belongs to the database
	static cardAccount: Card -> Account
	
	controlled accountCards: Account -> Powerset(Card) //for multiplicity 0..n 

	dynamic monitored insertedCard: Till -> Card //inserted card
	dynamic monitored insertedPin: Till -> Natural //inserted pin
	dynamic monitored selectedMoney: Till -> Natural //selected amount of money
	
	dynamic monitored unavailable: Card -> Boolean
	
	derived isInHierState: Till -> Boolean

	static till1: Till

	static card1: Card
	static card2: Card
	static card3: Card
	
	static account1: Account
	static account2: Account
	static account3: Account

	monitored databaseAvailable: Boolean

	derived ranCurrCard: Powerset(Card)
	
definitions:
	function ranCurrCard =
		{$card in Card | (exist $till in Till with currCard($till)=$card)}

	function isLegal($c in Card) = true //all cards are legal

	function isInHierState($t in till) = (tillState($t) = CHOOSE | 
										tillState($t) = VIEWBALANCE |
								tillState($t) = WITHDRAWAL | tillState($t) = STATEMENT) 

	function pin($c in Card) =
		switch($c)
			case card1 : 1n
			case card2 : 2n
			case card3 : 3n
		endswitch

	function cardAccount($c in Card) =
		switch($c)
			case card1 : account1
			case card2 : account2
			case card3 : account3
		endswitch

	macro rule r_subtractFrom($c in Account, $m in Natural) =
		balance($c) := balance($c) - $m

	macro rule r_goOutOfService =
		if unavailable(self) then
			tillState(self) := OUTOFSERVICE
		endif

	macro rule r_insertcard =
		if(tillState(self)=AWAITCARD) then
			if not(exist $till in Till with $till != $self and
						                currCard($till)=insertedCard(self))
				par
					currCard(self) := insertedCard(self)
					tillState(self) := AWAITPIN
					outMess(self) := "Enter pin"//debugging
				endpar
			endif
		endif

	macro rule r_enterPinOK =
		if(tillState(self)=AWAITPIN and insertedPin=pin(currCard(self))) then
			par
				outMess(self) := "Choose service" //debugging
				tillState(self) := CHOOSE
			endpar
		endif
	
	macro rule r_enterPinWRONG =
		if(tillState(self)=AWAITPIN and insertedPin(self)!=pin(currCard(self))) then
			par
				tillState(self) := AWAITCARD
				currCard(self) := undef
				outMess(self) := "Wrong pin" //debugging
			endpar
		endif

	macro rule r_chooseServiceBALANCE =
		if(tillState(self)=CHOOSE and selectedService(self)=BALANCE and databaseAvailable) then
			par
				tillState(self) := SHOWBALANCE
				outMess(self) := balance(cardAccount(currCard))
			endpar
		endif

	macro rule r_chooseServiceWITHDRAWAL =
		if(tillState(self)=CHOOSE and selectedService(self)=WITHDRAWAL and databaseAvailable) then
			par
				tillState(self) := CHOOSESIZEMONEY
				outMess(self) := "Select a money size"//debugging
			endpar
		endif

	macro rule r_chooseServiceWITHDRAWAL_CHOOSESIZEMONEY =
		par
			if(tillState(self)=CHOOSESIZEMONEY and databaseAvailable and 
					selectedMoney(self) <= moneyLeftInTill(self) and selectedMoney(self) <= balance(account(currCard(self)))) then
				par
					balance(cardAccount(currCard)) := balance(cardAccount(currCard)) - selectedMoney(self)
					moneyLeftInTill(self) := moneyLeftInTill(self) - selectedMoney(self)
					tillState(self) := FINISH
				endpar
			endif
		endpar
		
	macro rule r_chooseServiceWITHDRAWAL_CHOOSESIZEMONEY_fail =
		par
			if(tillState(self)=CHOOSESIZEMONEY and
								(not(databaseAvailable) or 
								 selectedMoney(self) > moneyLeftInTill(self) or
								 selectedMoney(self) > balance(cardAccount(currCard(self))) )
			 									) then
				tillState(self) := FINISH
			endif
		endpar

	macro rule r_finish =
		if tillState(self)=FINISH then
			r_ejectCard[]
		endif

	rule r_ejectCard =
		if not(databaseAvailable) and isInHierState(self) then 
			currCard(self) := undef
		endif

	rule r_tillProgram =
		par
			r_insertcard[]
			r_enterPinOK[]
			r_enterPinWRONG[]
			r_chooseServiceBALANCE[]
			r_chooseServiceWITHDRAWAL[]
			r_chooseServiceWITHDRAWAL_CHOOSESIZEMONEY[]
			r_chooseServiceWITHDRAWAL_CHOOSESIZEMONEY_fail[]
			r_chooseServiceSTATEMENT[]
			r_ejectCard[]
		endpar

	//to check that the same card is not inserted in two different tills
	invariant inv_card:
	(forall $till in Till with currCard($till) != undef implies
					not(exist $till2 in Till with $till2 != $till and
					                            currCard($till)=currCard($till2)))

	main rule r_Main =
		par
			//only one till running at a time
			choose $till in Till with true do
				program($till)
			//a card can be nondeterministically created
			choose $b in Boolean with true do
				if $b then
					extend Card with $card do
						balance($card) := 0n
						//TODO: associate the card with an account
				endif

default init s0:
	function tillState($till in Till) = AWAITCARD
	function moneyLeftInTill($till in Till) = 1000
	function balance($a in Account) = switch($a)
											case account1 : 3000
											case account2 : 1652
											case account3 : 548
										endswitch

	agent Till:
		r_tillProgram[]
