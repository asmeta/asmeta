// a simple example with a tic tac toe game

asm testStaticFun

import STDL/StandardLibrary

signature:

     abstract domain NumCard

    abstract domain Account
   abstract domain Card
   abstract domain Till
   abstract domain Date

   enum domain Side = {LEFT | RIGHT}




    dynamic controlled balance: NumCard -> Integer

	static minMoney: Integer
	static maxPrelievo: Integer
    derived allowed: Prod(NumCard, Integer) -> Boolean
    

    static withdrawLimit: Integer
    static encodedPin : Card -> Integer
   static cardAccount : Card -> Account

   derived oppositeSide: Side -> Side





   static card1: Card
   static card2: Card
   static card3: Card
   static till1 : Till
   static account1 : Account
   static monday : Date
   static tuesday : Date

   static car1 : NumCard


definitions:


      function encodedPin($c in Card) =
		switch($c)
			case card1 : 1
			case card2 : 2
		endswitch

   	function cardAccount($c in Card) =
		switch($c)
			case card1 : account1
			case card2 : account1
		endswitch


   function withdrawLimit = 1000


	function minMoney = 200 //quantita' minima di soldi necessaria per il funzionamento del bancomat
	function maxPrelievo = 1000



	//il prelievo e' possibile se la cifra richiesta e' inferiore al saldo
	function allowed($c in NumCard, $m in Integer) =
		balance($c) >= $m

			function oppositeSide($s in Side) =
		if($s = LEFT) then
			RIGHT
		else
			LEFT
		endif

		main rule r_main =
		skip

