// a simple example with a tic tac toe game

asm testDefinition3

import STDL/StandardLibrary

signature:

	//dynamic abstract domain Dinam
	//dynamic domain NumeriD subsetof Integer
	//dynamic enum domain ColorD={RED | GREEN | BLUE}

	dynamic abstract domain Person

	abstract domain NumCard
	abstract domain Till
	abstract domain Card
	abstract domain Date
	abstract domain Account
	domain CoinDomain subsetof Integer
	enum domain Product = {COFFEE | TEA | MILK}
	enum domain TillState = { WAITCARD | WAITPIN | VALIDPIN}
	enum domain GenderDomain = {MALE | FEMALE}
	domain QuantityDomain subsetof Integer

	domain Second subsetof Integer
	domain Minute subsetof Integer
	domain Hour subsetof Integer
	monitored seconds: Second
	monitored minutes: Minute
	monitored hours: Hour

	controlled number: Integer

	monitored daily_withdraw_sum : Card -> Integer


    monitored tillState : Till -> TillState

	dynamic monitored balance: NumCard -> Integer
	dynamic monitored accessible: NumCard -> Boolean
	monitored available: Product -> QuantityDomain
	monitored coins: CoinDomain
	monitored cardLastUseDate : Card -> Date
	//controlled balance : Account -> Integer


	dynamic controlled gender: Person -> GenderDomain
	dynamic controlled age: Person -> Natural
	dynamic controlled alive: Person -> Boolean

	dynamic controlled mother: Person -> Person
	dynamic controlled father: Person -> Person

	controlled time: Prod(Second, Minute, Hour) -> Second
	controlled timeProva: Prod(Second, Minute, Hour)

	static male1: Person
	static female1: Person


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


    function mother($p in Person) = $p //Who are the mothers of the first individuals? Themselves
	function father($p in Person) = $p //Who are the fathers of the first individuals? Themselves
	function alive($p in Person) = true

/*
	function gender($p in Person) = if ($p = female1) then
										FEMALE
									else
										MALE
									endif

	function age($p in Person) = if ($p = female1) then
									16n
								else
									19n
								endif
*/
      function time($x in Second, $y in Minute, $z in Hour) = 0



