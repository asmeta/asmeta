//mondex concrete da articolo:
//A concept-driven construction of the mondex protocol using three refinements
//capitolo 3: From atomic transfers to messages
//versione per NuSMV

asm mcCap3ForNuSMV

import ../../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB | CC | DD}
	//nostro per identificare il tipo di messagio
	enum domain MessageType = {REQ | VAL | ACK}
	//nostro per fare la choose nella main rule
	enum domain RuleId = {LOSEMSGRULE | STARTTORULE | REQRULE | VALRULE | ACKRULE | ABORTRULE}
	domain TidDomain subsetof Natural
	domain MoneyDomain subsetof Natural

	dynamic controlled balance: Name -> MoneyDomain

	//dynamic controlled exLogFrom: Name -> Powerset(Prod(Name, MoneyDomain, TidDomain))
	//dynamic controlled exLogTo: Name -> Powerset(Prod(Name, MoneyDomain, TidDomain))
	//dynamic controlled tids: Powerset(TidDomain)
	//dynamic controlled inbox: Name -> Powerset(Prod(MessageType, Name, MoneyDomain, TidDomain))
	//dynamic controlled outbox: Name -> Prod(MessageType, Name, MoneyDomain, TidDomain)
	//rese con funzioni caratteristiche
	dynamic controlled exLogFrom: Prod(Name, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled exLogTo: Prod(Name, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled tids: TidDomain -> Boolean
	dynamic controlled inbox: Prod(Name, MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled outbox: Prod(Name, MessageType, Name, MoneyDomain, TidDomain) -> Boolean

	dynamic monitored chosenRule: RuleId
	
	static authentic: Name -> Boolean
	static outMessIsReq: Name -> Boolean
	static outMessIsVal: Name -> Boolean
	//static outMessIsAck: Name -> Boolean
	static outMessIsNone: Name -> Boolean
	
	static inv: Boolean
	//static equalMessages: Prod(Prod(MessageType, Name, MoneyDomain, TidDomain), Prod(MessageType, Name, MoneyDomain, TidDomain)) -> Boolean
	
definitions:
	domain TidDomain = {1n..3n}
	domain MoneyDomain = {0n, 5n, 10n}

	//se e' contenuto in inbox o in outbox il tid deve essere anche in tids
	function inv =
if(forall $card in Name, $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain
with(
(inbox($card, $t, $na, $value, $tid) or outbox($card, $t, $na, $value, $tid)) implies tids($tid)) )
			then
		true
	else
		false
	endif

	//function paymentDetails($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain) =
	//	($na, $value, $tid)

	function authentic($n in Name) = if($n = AA or $n = BB or $n=CC) then
						true
					else
						false
					endif



	function outMessIsReq($receiver in Name) =
		if(exist $na in Name, $value in MoneyDomain, $tid in TidDomain with 				outbox($receiver, REQ, $na, $value, $tid)) then
			true
		else
			false
		endif

	function outMessIsVal($receiver in Name) =
		if(exist $na in Name, $value in MoneyDomain, $tid in TidDomain with 				outbox($receiver, VAL, $na, $value, $tid)) then
			true
		else
			false
		endif

	/*function outMessIsAck($receiver in Name) =
		if(exist $na in Name, $value in MoneyDomain, $tid in TidDomain with 				outbox($receiver, ACK, $na, $value, $tid)) then
			true
		else
			false
		endif*/

	function outMessIsNone($receiver in Name) =
		if(not(exist $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 				outbox($receiver, $t, $na, $value, $tid))) then
			true
		else
			false
		endif

	rule r_setOutMessNone($receiver in Name) =
		forall $m in MessageType, $n in Name, $v in MoneyDomain, $k in TidDomain with true do
			outbox($receiver, $m, $n, $v, $k) := false


	//ora ne cancella uno solo; ne dovrebbe cancellare un sottoinsieme
	rule r_loseMsg($receiver in Name) =
	choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with
							inbox($receiver, $t, $na, $value, $tid) do
		inbox($receiver, $t, $na, $value, $tid) := false

	macro rule r_startTo($receiver in Name) =
		if(outMessIsNone($receiver)) then
			choose $na in Name, $value in MoneyDomain, $tid in TidDomain with not(tids($tid))
							and authentic($na) and $na!= $receiver do
			par
				inbox($na, REQ, $na, $value, $tid) := true
				outbox($receiver, REQ, $na, $value, $tid) := true
				tids($tid) := true
			endpar
		endif

	macro rule r_req($receiver in Name) = 
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				inbox($receiver, $t, $na, $value, $tid) and $t=REQ and authentic($na)
				and $na!=$receiver and $value <= balance($receiver) and
				(outMessIsNone($receiver)) do
			par
				inbox($na, VAL, $receiver, $value, $tid) := true
				outbox($receiver, VAL, $na, $value, $tid) := true
				//seq
					balance($receiver) := balance($receiver) - $value
					inbox($receiver, $t, $na, $value, $tid) := false
				//endseq
			endpar
		
	macro rule r_val($receiver in Name) = 
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				inbox($receiver, $t, $na, $value, $tid) and $t=VAL and
				outbox($receiver, REQ, $na, $value, $tid) do
			par
				inbox($na, ACK, $receiver, $value, $tid) := true
				outbox($receiver, ACK, $na, $value, $tid) := true
				//seq
					balance($receiver) := balance($receiver) + $value
					inbox($receiver, $t, $na, $value, $tid) := false
				//endseq
			endpar

	macro rule r_ack($receiver in Name) =
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with
				inbox($receiver, $t, $na, $value, $tid) and $t=ACK and
				outbox($receiver, VAL, $na, $value, $tid) do
			par
				r_setOutMessNone[$receiver]
				inbox($receiver, $t, $na, $value, $tid) := false
			endpar

	macro rule r_abort($receiver in Name) =
		par//seq
			choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain
						with outbox($receiver, $t, $na, $value, $tid) do
				par
					if(outMessIsReq($receiver)) then
						exLogTo($receiver, $na, $value, $tid) := true
					endif
					if(outMessIsVal($receiver)) then
						exLogFrom($receiver, $na, $value, $tid) := false
					endif
				endpar
			outbox($receiver,$t, $na, $value, $tid) := false
		endpar//endseq

	invariant over tids: inv

	main rule r_irule =
		choose $receiver in Name with authentic($receiver) do
			switch(chosenRule)
				case LOSEMSGRULE:
					r_loseMsg[$receiver]
				case STARTTORULE:
					r_startTo[$receiver]
				case REQRULE:
					r_req[$receiver]
				case VALRULE:
					r_val[$receiver]
				case ACKRULE:
					r_ack[$receiver]
				case ABORTRULE:
					r_abort[$receiver]
			endswitch

default init s0:
	function balance($n in Name) = at({AA->5n,BB->0n, CC->5n, DD->15n},$n)
	///function inbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function outbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function exLogFrom($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function exLogTo($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	function tids($tid in TidDomain) = false 
