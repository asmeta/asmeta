//mondex concrete da articolo:
//A concept-driven construction of the mondex protocol using three refinements
//capitolo 3: From atomic transfers to messages
//versione fedele all'articolo

asm mondexConcreteCap3

import ../../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB | CC | DD}
	//nostro per identificare il tipo di messagio
	enum domain MessageType = {REQ | VAL | ACK | NONE}
	//nostro per fare la choose nella main rule
	enum domain RuleId = {LOSEMSGRULE | STARTTORULE | REQRULE | VALRULE | ACKRULE | ABORTRULE}
	domain TidDomain subsetof Natural
	domain MoneyDomain subsetof Natural

	dynamic controlled balance: Name -> Natural
	dynamic controlled exLogFrom: Name -> Powerset(Prod(Name, MoneyDomain, TidDomain))
	dynamic controlled exLogTo: Name -> Powerset(Prod(Name, MoneyDomain, TidDomain))
	dynamic controlled tids: Powerset(TidDomain)
	dynamic controlled inbox: Name -> Powerset(Prod(MessageType, Name, MoneyDomain, TidDomain))
	dynamic controlled outbox: Name -> Prod(MessageType, Name, MoneyDomain, TidDomain)

	dynamic monitored chosenRule: RuleId
	
	static authentic: Name -> Boolean
	static isNone: Prod(MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	static isReq: Prod(MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	static isVal: Prod(MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	static isAck: Prod(MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	//nostro per ottenere i dettagli di pagamento da un messaggio
	static paymentDetails: Prod(MessageType,Name,MoneyDomain,TidDomain) ->
								Prod(Name, MoneyDomain, TidDomain)

	static inv: Boolean
	static equalMessages: Prod(Prod(MessageType, Name, MoneyDomain, TidDomain), Prod(MessageType, Name, MoneyDomain, TidDomain)) -> Boolean
	
definitions:
	domain TidDomain = {1n..10n}
	domain MoneyDomain = {1n..90n}

	/*function equalMessages(($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain),
			($t2 in MessageType, $na2 in Name, $value2 in MoneyDomain, $tid2 in TidDomain)) = 
		if($t = $t2 and $na=$na2 and $value=$value2 and $tid=$tid2) then
			true
		else
			false
		endif*/
	function equalMessages($m1 in Prod(MessageType, Name, MoneyDomain, TidDomain), $m2 in Prod(MessageType, Name, MoneyDomain, TidDomain)) = 
		if(first($m1) = first($m2) and second($m1)=second($m2) and third($m1)=third($m1) and fourth($m1)=fourth($m2)) then
			true
		else
			false
		endif


	//se e' contenuto in inbox o in outbox il tid deve essere anche in tids
	function inv =
if(forall $card in Name, $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain
with(
(contains(inbox($card), ($t, $na, $value, $tid)) or equalMessages(outbox($card),($t, $na, $value, $tid))) implies contains(tids, $tid) ))
			then
		true
	else
		false
	endif

	function paymentDetails($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain) =
		($na, $value, $tid)

	function authentic($n in Name) = if($n = AA or $n = BB or $n=CC) then
						true
					else
						false
					endif

	function isNone($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain) =
		if($t=NONE) then
			true
		else
			false
		endif

	function isReq($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain) =
		if($t = REQ) then
			true
		else
			false
		endif

	function isVal($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain) =
		if($t = VAL) then
			true
		else
			false
		endif

	function isAck($t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain) =
		if($t = ACK) then
			true
		else
			false
		endif

	//ora ne cancella uno solo; ne dovrebbe cancellare un sottoinsieme
	rule r_loseMsg($receiver in Name) =
		inbox($receiver) := excluding(inbox($receiver), chooseone(inbox($receiver)))

	macro rule r_startTo($receiver in Name) =
		//if(isNone(outbox($receiver))) then
		if(first(outbox($receiver))=NONE) then
			choose $na in Name, $value in MoneyDomain, $tid in TidDomain with notin(tids, $tid)
							and authentic($na) and $na!= $receiver do
			par
				inbox($na) := including(inbox($na), (REQ, $na, $value, $tid))
				outbox($receiver) := (REQ, $na, $value, $tid)
				tids := including(tids, $tid)
			endpar
		endif

	macro rule r_req($receiver in Name) = 
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				contains(inbox($receiver), ($t, $na, $value, $tid)) and
				/*isReq(($t, $na, $value, $tid))*/ $t=REQ and authentic($na) and $na!=$receiver and
				$value <= balance($receiver) and /*isNone(outbox($receiver))*/ 
								first(outbox($receiver))=NONE do
			par
				inbox($na) := including(inbox($na), (VAL, $receiver, $value, $tid))
				outbox($receiver) := (VAL, $na, $value, $tid)
				seq
					balance($receiver) := balance($receiver) - $value
					inbox($receiver) := excluding(inbox($receiver),
									($t, $na, $value, $tid))
				endseq
			endpar
		
	macro rule r_val($receiver in Name) = 
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				contains(inbox($receiver), ($t, $na, $value, $tid)) and
				/*isVal(($t, $na, $value, $tid))*/ $t=VAL and /*isReq(outbox($receiver))*/ first(outbox($receiver))=REQ and
				
			/*paymentDetails(($t,$na,$value,$tid))=paymentDetails(outbox($receiver))*/
			($na=second(outbox($receiver)) and $value=third(outbox($receiver)) and $tid=fourth(outbox($receiver)))	 do
			par
				inbox($na) := including(inbox($na), (ACK, $receiver, $value, $tid))
				outbox($receiver) := (ACK, $na, $value, $tid)
				seq
					balance($receiver) := balance($receiver) + $value
					inbox($receiver) := excluding(inbox($receiver), 
									($t, $na, $value, $tid))
				endseq
			endpar

	macro rule r_ack($receiver in Name) =
		choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with
				contains(inbox($receiver), ($t, $na, $value, $tid)) and
				/*isAck(($t, $na, $value, $tid))*/ $t=ACK and /*isVal(outbox($receiver))*/ first(outbox($receiver))=VAL and
				/*paymentDetails($t,$na,$value,$tid)=paymentDetails(outbox($receiver))*/ ($na=second(outbox($receiver)) and $value=third(outbox($receiver)) and $tid=fourth(outbox($receiver))) do
			par
				outbox($receiver) := (NONE, second(outbox($receiver)),
						third(outbox($receiver)), fourth(outbox($receiver)))
				inbox($receiver) := excluding(inbox($receiver), ($t, $na, $value, $tid))
			endpar

	macro rule r_abort($receiver in Name) =
		seq
			if(isReq(outbox($receiver))) then
				exLogTo($receiver) := including(exLogTo($receiver),
								paymentDetails(outbox($receiver)))
			endif
			if(isVal(outbox($receiver))) then
				seq
					exLogFrom($receiver) := including(exLogFrom($receiver),
								paymentDetails(outbox($receiver)))
					outbox($receiver) := (NONE, second(outbox($receiver)),
						third(outbox($receiver)), fourth(outbox($receiver)))
				endseq
			endif
		endseq

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
	function balance($n in Name) = at({AA->10n,BB->0n, CC->2n, DD->4n},$n)
	function inbox($n in Name) = {}
	function outbox($n in Name) = (NONE, $n, 0n, 1n)
	function tids = {}
