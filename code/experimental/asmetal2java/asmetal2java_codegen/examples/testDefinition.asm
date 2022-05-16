// a simple example with a tic tac toe game

asm testDefinition

import STDL/StandardLibrary

signature:

	//dynamic abstract domain Dinam
	//dynamic domain NumeriD subsetof Integer
	//dynamic enum domain ColorD={RED | GREEN | BLUE}

	abstract domain NumCard
	abstract domain Till
	abstract domain Card
	abstract domain Date
	abstract domain Account
	domain CoinDomain subsetof Integer
	enum domain Product = {COFFEE | TEA | MILK}
	enum domain TillState = { WAITCARD | WAITPIN | VALIDPIN}
	domain QuantityDomain subsetof Integer

	domain Second subsetof Integer
	domain Minute subsetof Integer
	domain Hour subsetof Integer
	controlled seconds: Second
	controlled minutes: Minute
	controlled hours: Hour
	controlled daily_withdraw_sum : Card -> Integer


    controlled tillState : Till -> TillState

	dynamic controlled balance: NumCard -> Integer
	dynamic controlled accessible: NumCard -> Boolean
	controlled available: Product -> QuantityDomain
	controlled coins: CoinDomain
	controlled cardLastUseDate : Card -> Date
	//controlled balance : Account -> Integer

	static card1: NumCard
	static card2: NumCard
	static card3: NumCard

	static card11: Card
   static card21: Card

      static monday : Date
   static tuesday : Date

   static account1 : Account





definitions:

	domain Second = {0..59}
	domain Minute= {0..59}
	domain Hour = {0..23}


    main rule r_main =
    skip

    default init s0:

    function balance($c in NumCard) = switch($c)
										case card1 : 3000
										case card2 : 1652
										case card3 : 548
									endswitch

    function accessible($c in NumCard) = true

    function coins = 0
	function available($p in Product) = 10
	function tillState($m in Till) = WAITCARD

	function seconds = 0
	function minutes = 0
	function hours = 0


    //function balance($a in Account) = chooseone({1000,2000,3000})
    function daily_withdraw_sum($c in Card) = 0
    function cardLastUseDate($c in Card) = monday



