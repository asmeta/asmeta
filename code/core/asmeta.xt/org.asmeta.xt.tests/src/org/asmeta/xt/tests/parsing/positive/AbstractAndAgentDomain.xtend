package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.validation.Issue
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class AbstractAndAgentDomain {

	@Inject ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void test1() {
		var result = test('''
			asm blankpage
			signature: 
				abstract domain Agent
				abstract domain Student
				basic domain Integer
				
				static x : Student -> Integer
				
			definitions: 
				function x($x in Student) = 4
		''')
		// asm test
		// header test																			
		Assert.assertEquals(0, result.headerSection.importClause.size)
		Assert.assertEquals(null, result.headerSection.exportClause)
		Assert.assertEquals(3, result.headerSection.signature.domain.size)
		println(result.headerSection.signature.domain.get(0).class)
		println(result.headerSection.signature.domain.get(1).class)
	}

	@Test
	def void testCashPoint() {
		var result = test('''
asm CashPoint

import StandardLibrary

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
		''')
		// asm test
		// header test																			
		Assert.assertEquals(1, result.headerSection.importClause.size)
		Assert.assertEquals(null, result.headerSection.exportClause)
		Assert.assertEquals(5, result.headerSection.signature.domain.size)
		println(result.headerSection.signature.domain.get(0).class)
		println(result.headerSection.signature.domain.get(1).class)
		println(result.headerSection.signature.domain.get(2).class)
		println(result.headerSection.signature.domain.get(3).class)
		println(result.headerSection.signature.domain.get(4).class)
	}
	
	def test(String s){
		var result = parseHelper.parse(s)
		result.assertNoErrors
		// validiazione
		// now apply the validation rules
		val issues = validate(result);
		for (Object o : issues) {
			val Issue issue = o as Issue;
			System.out.println(
				issue.getSeverity() + " " + issue.getMessage() + " (Line " + issue.getLineNumber() + ")");
		}
		return result;
	}

}
