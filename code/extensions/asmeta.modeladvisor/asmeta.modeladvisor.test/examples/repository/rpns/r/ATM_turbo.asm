// DANIELA CUGLIETTA
// Esercizio pag. 92
// AsmetaS cannot simulate it because it contains turbo rules
// there exist the ATM without turbo rules and that can be simulated


asm ATM_turbo
import ../../STDL/StandardLibrary

//declare dominoes and functions

	
signature:
		
    abstract domain Card
	abstract domain Id 
	abstract domain Money
	enum domain Msg = {ENTERPINMSG|CHOOSESERVICEMSG|WRONGPINMSG|ACCOUNTNOTACCESSIBLE|NOTALLOWEDMSG|EXITMSG| 	OUTOFSERVICE | NOTENOUGHMONEYLEFTMSG}		
	
	/*static*/ derived  moneyleftbelowrequest: Boolean
	dynamic controlled moneyleft: Integer
	dynamic monitored currin_id: Id -> Integer
	dynamic monitored currin_m: Money -> Boolean
	dynamic monitored curricard: Card -> Boolean
	dynamic monitored withdrawal: Boolean
	dynamic monitored standard: Boolean
	dynamic monitored otheramount: Boolean

//the function of output is divided in 3 functions:
	dynamic out output_msg:  Msg              //represents the message
	dynamic out output_card: Card -> Card     //represents the exit of the card
	dynamic out output_mon: Money            //represents the money's exit
	
	dynamic monitored inserted: Card -> Boolean
	static pin: Boolean -> Integer 		
	dynamic shared accessible: Money -> Boolean
	static account: Boolean -> Money
	static min: Integer
	dynamic monitored input: Integer
	
definitions:

//function moneyleftbelowrequest

	function moneyleftbelowrequest =
			if  moneyleft< input then 
				true
			else
				false
			endif


//rule of insertion of the card
		
	turbo rule r_InsertCard ($c in Card)=
		if inserted($c) then
			output_msg := ENTERPINMSG
		endif

//rule of insertion of a good pin

	turbo rule r_EnterGoodPin ($i in Id, $c in Card)=
		if currin_id($i) = pin (curricard($c)) and (accessible (account (curricard($c)))) then
			output_msg := CHOOSESERVICEMSG
		endif

//rule of insertion of a wrong pin

	turbo rule r_EnterWrongPin ($i in Id, $c in Card)=
		if currin_id ($i) != pin (curricard ($c)) then
			output_msg := WRONGPINMSG
		else 
		if not accessible (account (curricard ($c))) then
			output_msg := ACCOUNTNOTACCESSIBLE
		endif
		endif

//Macro Rule that sottrae il denaro dal conto
	
	rule r_SubtractFrom ($c in Card, $i in Id)=
		skip

//Macro Rule of recovery of the money

	rule r_GrantMoney ($m in Money, $c in Card, $i in Id)=
		seq
			output_msg:=EXITMSG
			output_mon := ($m)
			output_card($c) := ($c)
			r_SubtractFrom [$c ,$i]
			accessible (account (curricard($c))) := false
			moneyleft:= moneyleft - input
		endseq

//Macro Rule of request of the money

	rule r_ProcessMoneyRequest ($c in Card, $m in Money, $i in Id)=
		if curricard ($c) then
			seq
			r_GrantMoney [$m, $c, $i]
			moneyleft:=moneyleft - input
			endseq
		else
			seq
			output_msg := NOTALLOWEDMSG
			output_card ($c) := ($c)
			endseq
		endif

//rule of entrance of the money

	turbo rule r_EnterMoney ($m in Money, $c in Card, $i in Id) =
		if moneyleftbelowrequest = false and currin_m ($m) then
			r_ProcessMoneyRequest [$c, $m, $i]
		else 
			seq
			output_msg := NOTENOUGHMONEYLEFTMSG
			output_card ($c):= ($c)
			endseq
		endif

//Rule of choose the withdrawal

	turbo rule r_ChooseWithdrawal =
		if withdrawal = true then skip
		endif

//Rule of enter standard

	turbo rule r_EnterStandardAmount($m in Money,$c in Card,$i in Id) =
		if standard = true then 
			r_ProcessMoneyRequest [$c, $m, $i]
		endif

//Rule of choose other amount
	
	turbo rule r_ChooseOtherAmount =
		if otheramount = true then skip
		endif

//Rule of enter amount

	turbo rule r_EnterAmount ($m in Money,$c in Card,$i in Id) =
		if currin_m ($m) then
			r_ProcessMoneyRequest [$c, $m, $i]
		endif

//rule of out of service

	turbo rule r_GoOutOfService =
		if moneyleft < min then
			output_msg:= OUTOFSERVICE
		endif

//main rule

	main rule r_Atm=
		forall $m in Money, $c in Card, $i in Id with true do	
		seq
		r_InsertCard ($c)
		r_EnterGoodPin ($i, $c)
		r_EnterWrongPin ($i, $c)
		r_EnterMoney ($m, $c, $i)
		r_GoOutOfService ()
		r_ChooseWithdrawal()
		r_EnterStandardAmount($m,$c,$i)
		r_EnterAmount($m,$c,$i)
		r_ChooseOtherAmount()
		endseq
