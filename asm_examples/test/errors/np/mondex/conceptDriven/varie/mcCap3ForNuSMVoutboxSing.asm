//mondex concrete da articolo:
//A concept-driven construction of the mondex protocol using three refinements
//capitolo 3: From atomic transfers to messages
//versione per NuSMV con 4 funzioni singole per l'outbox
//incluso l'abort, senza il loose message

asm mcCap3ForNuSMVoutboxSing

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
	dynamic controlled outboxMessage: Name -> MessageType
	dynamic controlled outboxName: Name -> Name
	dynamic controlled outboxMoney: Name -> MoneyDomain
	dynamic controlled outboxTid: Name -> TidDomain
	dynamic controlled outboxIsNone: Name -> Boolean

	dynamic monitored chosenRule: RuleId
	
	static authentic: Name -> Boolean	
	static inv: Boolean

definitions:
	domain TidDomain = {1n:2n}
	domain MoneyDomain = {0n, 5n, 10n}

	//se e' contenuto in inbox o in outbox il tid deve essere anche in tids
	function inv =
		if(forall $card in Name, $t in MessageType, $na in Name, $value in MoneyDomain,
			$tid in TidDomain with( (inbox($card, $t, $na, $value, $tid) or
						exLogFrom($card,$na, $value, $tid) or
						exLogTo($card, $na, $value, $tid) or
						(outboxIsNone($card) = false and
						outboxMessage($card) = $t and
						outboxName($card) = $na and
						outboxMoney($card) = $value and
						outboxTid($card) = $tid)) implies tids($tid)) )
			then
		true
	else
		false
	endif

	function authentic($n in Name) = if($n = AA or $n = BB or $n=CC) then
						true
					else
						false
					endif




	//ora ne cancella uno solo; ne dovrebbe cancellare un sottoinsieme
	rule r_loseMsg($receiver in Name) =
	choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with
							inbox($receiver, $t, $na, $value, $tid) do
		inbox($receiver, $t, $na, $value, $tid) := false

	macro rule r_startTo($receiver in Name) =
		if(outboxIsNone($receiver)) then
			choose $na in Name, $value in MoneyDomain, $tid in TidDomain with not(tids($tid))
							and authentic($na) and $na!= $receiver do
			par
				inbox($na, REQ, $receiver, $value, $tid) := true
				outboxMessage($receiver) := REQ
				outboxName($receiver) := $na
				outboxMoney($receiver) := $value
				outboxTid($receiver) := $tid
				outboxIsNone($receiver) := false
				tids($tid) := true
			endpar
		endif

	macro rule r_req($receiver in Name) = 
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				inbox($receiver, $t, $na, $value, $tid) and $t=REQ and authentic($na)
				and $na!=$receiver and $value <= balance($receiver) and
				(outboxIsNone($receiver)) do
			par
				inbox($na, VAL, $receiver, $value, $tid) := true
				outboxMessage($receiver) := VAL
				outboxName($receiver) := $na
				outboxMoney($receiver) := $value
				outboxTid($receiver) := $tid
				outboxIsNone($receiver) := false
				//seq
					balance($receiver) := balance($receiver) - $value
					inbox($receiver, $t, $na, $value, $tid) := false
				//endseq
			endpar
		
	macro rule r_val($receiver in Name) = 
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				inbox($receiver, $t, $na, $value, $tid) and $t=VAL and
				/*outbox($receiver, REQ, $na, $value, $tid)*/ 
				(outboxMessage($receiver) = REQ and
				outboxName($receiver) = $na and
				outboxMoney($receiver) = $value and
				outboxTid($receiver) = $tid) do
			par
				inbox($na, ACK, $receiver, $value, $tid) := true
				outboxMessage($receiver) := ACK
				//outboxName($receiver) := $na
				//outboxMoney($receiver) := $value
				//outboxTid($receiver) := $tid
				outboxIsNone($receiver) := false
				//seq
					balance($receiver) := balance($receiver) + $value
					inbox($receiver, $t, $na, $value, $tid) := false
				//endseq
			endpar

	macro rule r_ack($receiver in Name) =
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with
				inbox($receiver, $t, $na, $value, $tid) and $t=ACK and
				/*outbox($receiver, VAL, $na, $value, $tid)*/
				(outboxMessage($receiver) = VAL and
				outboxName($receiver) = $na and
				outboxMoney($receiver) = $value and
				outboxTid($receiver) = $tid) do
			par
				outboxIsNone($receiver) := true
				inbox($receiver, $t, $na, $value, $tid) := false
			endpar

	macro rule r_abort($receiver in Name) =
		par
			choose $na in Name, $value in MoneyDomain, $tid in TidDomain with
			($na=outboxName($receiver) and $value=outboxMoney($receiver) and
								$tid=outboxTid($receiver)) do
				par
					if(outboxMessage($receiver)=REQ) then
						exLogTo($receiver, $na, $value, $tid) := true
					endif
					if(outboxMessage($receiver)=VAL) then
						exLogFrom($receiver, $na, $value, $tid) := true
					endif
				endpar
			outboxIsNone($receiver) := true
		endpar

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
	function balance($n in Name) = at({AA->5n, BB->5n ,CC->0n, DD->10n},$n)
	function inbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	function exLogFrom($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	function exLogTo($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	function tids($tid in TidDomain) = false
	function outboxIsNone($n in Name) = true
	function outboxMessage($n in Name) = ACK
	function outboxName($n in Name) = AA
	function outboxMoney($n in Name) = 0n
	function outboxTid($n in Name) = 1n
