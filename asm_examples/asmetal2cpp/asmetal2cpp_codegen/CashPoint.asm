// 

asm CashPoint

import ../../STDL/StandardLibrary

signature:
	// domains
   abstract domain Account  
   abstract domain Card
   abstract domain Till
   abstract domain Date
   
   //domain CashAmount subsetof Integer
   
   enum domain TillState = { WAITCARD | WAITPIN | VALIDPIN}
        
   controlled tillState : Till -> TillState
   
//   controlled balance : Account -> CashAmount
   controlled balance : Account -> Integer
 
   controlled legalCard: Card -> Boolean
   
   controlled daily_withdraw_sum : Card -> Integer

   controlled insertedCard : Till -> Card
   
   monitored enteredPin: Till -> Integer
   
   monitored amount : Till -> Integer
      
   static withdrawLimit: Integer
   
   static encodedPin : Card -> Integer
   
   static cardAccount : Card -> Account
   
   // current date != date stored in the card
   monitored currentDate : Date
   controlled cardLastUseDate : Card -> Date
   
   derived stableState: Boolean
   
   static card1: Card
   static card2: Card
   static till1 : Till
   static account1 : Account
   static monday : Date
   static tuesday : Date
   
definitions:
  
//  	domain CashAmount = {-100 : 100}
  
  
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

    				
    // try to insert card $c in till $t
    rule r_insertedCard($t in Till, $c in Card) =
    if tillState($t) = WAITCARD then
      par
        tillState($t) := WAITPIN
        insertedCard($t) := $c
      endpar
    endif 
   
   	rule r_insertCard($t in Till) = 
		choose $c in Card with true do r_insertedCard[$t,$c]   
   
    rule r_enterPin($t in Till) =
    if tillState($t) = WAITPIN then
      if legalCard(insertedCard($t)) then
        if enteredPin($t) = encodedPin(insertedCard($t)) then
          par
            if cardLastUseDate(insertedCard($t)) != currentDate then
               par
            	// reset the new daily amount sum
            	daily_withdraw_sum(insertedCard($t)) := 0
               cardLastUseDate(insertedCard($t)) := currentDate
               endpar
            endif
        	tillState($t) := VALIDPIN
         endpar
        endif
      endif
    endif
    
    rule r_validPin($t in Till) =
	    if tillState($t) = VALIDPIN then
	      // witdraw
	      let ($acc =  cardAccount(insertedCard($t))) in
	      // withdraw
	      if amount($t) > 0 and 
	          amount($t) <= balance($acc) and
	          daily_withdraw_sum(insertedCard($t)) < withdrawLimit - amount($t)then
	          par
	          	balance($acc) := balance($acc) - amount($t)
	          	daily_withdraw_sum(insertedCard($t)) := daily_withdraw_sum(insertedCard($t)) - amount($t)  
	          endpar
	     endif
	     endlet
	   endif
   

   invariant inv_b0 over balance: (forall $a in Account with (balance($a) > 0))
   
	main rule r_main =
		choose $till in Till with true do //not necessary
			par
			r_insertCard[$till]
			r_enterPin[$till]
			r_validPin[$till]
			endpar

default init s_0:
	function tillState($m in Till) = WAITCARD
	function legalCard($m in Card) = true
    function balance($a in Account) = chooseone({1000,2000,3000})
    function daily_withdraw_sum($c in Card) = 0
    function cardLastUseDate($c in Card) = monday
