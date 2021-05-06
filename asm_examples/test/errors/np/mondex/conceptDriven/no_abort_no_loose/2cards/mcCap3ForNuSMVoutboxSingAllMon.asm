//mondex concrete da articolo:
//A concept-driven construction of the mondex protocol using three refinements
//capitolo 3: From atomic transfers to messages
//versione per NuSMV con 4 funzioni singole per l'outbox
//due carte autentiche
//con monitorata per scegliere la regola
//monitorate anche in tutte le regole per ovviare al choose che non funziona

asm mcCap3ForNuSMVoutboxSingAllMon
//non c'e' il metodo abort ed il metodo loose messages

import ../../../../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB}
	//nostro per identificare il tipo di messagio
	enum domain MessageType = {REQ | VAL | ACK}
	//nostro per fare la choose nella main rule
	enum domain RuleId = {/*LOSEMSGRULE |*/ STARTTORULE | REQRULE | VALRULE | ACKRULE /*| ABORTRULE*/}
	domain TidDomain subsetof Natural
	domain MoneyDomain subsetof Natural

	dynamic controlled balance: Name -> MoneyDomain

	//dynamic controlled exLogFrom: Name -> Powerset(Prod(Name, MoneyDomain, TidDomain))
	//dynamic controlled exLogTo: Name -> Powerset(Prod(Name, MoneyDomain, TidDomain))
	//dynamic controlled tids: Powerset(TidDomain)
	//dynamic controlled inbox: Name -> Powerset(Prod(MessageType, Name, MoneyDomain, TidDomain))
	//dynamic controlled outbox: Name -> Prod(MessageType, Name, MoneyDomain, TidDomain)
	//rese con funzioni caratteristiche
	//dynamic controlled exLogFrom: Prod(Name, Name, MoneyDomain, TidDomain) -> Boolean
	//dynamic controlled exLogTo: Prod(Name, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled tids: TidDomain -> Boolean
	dynamic controlled inbox: Prod(Name, MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled outboxMessage: Name -> MessageType
	dynamic controlled outboxName: Name -> Name
	dynamic controlled outboxMoney: Name -> MoneyDomain
	dynamic controlled outboxTid: Name -> TidDomain
	dynamic controlled outboxIsNone: Name -> Boolean
	
	derived isNone: Name -> Boolean

	dynamic monitored chosenRule: RuleId
	dynamic monitored receiver: Name
	
	dynamic monitored startTo_na: Name
	dynamic monitored startTo_value: MoneyDomain
	dynamic monitored startTo_tid: TidDomain
	dynamic monitored req_na: Name
	dynamic monitored req_value: MoneyDomain
	dynamic monitored req_tid: TidDomain
	dynamic monitored val_na: Name
	dynamic monitored val_value: MoneyDomain
	dynamic monitored val_tid: TidDomain
	dynamic monitored ack_na: Name
	dynamic monitored ack_value: MoneyDomain
	dynamic monitored ack_tid: TidDomain
	
	
	static authentic: Name -> Boolean	
	static useOfTid: Boolean
	static noSelfMessage: Boolean
	static noSelfOutMessage: Boolean

definitions:
	domain TidDomain = {1n:2n}
	domain MoneyDomain = {0n, 5n, 10n}
	
	function isNone($name in Name) =
		outboxIsNone($name) or outboxMessage($name) = ACK

	//se e' contenuto in inbox o in outbox il tid deve essere anche in tids
	function useOfTid =
		if(forall $card in Name, $t in MessageType, $na in Name, $value in MoneyDomain,
			$tid in TidDomain with( (inbox($card, $t, $na, $value, $tid) or
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
	
	//una carta non puo' mandare messaggi a se stessa
	function noSelfMessage = 
		if(forall $card in Name, $t in MessageType, $value in MoneyDomain,
			$tid in TidDomain with not(inbox($card, $t, $card, $value, $tid)) ) then
			true
		else
			false
		endif
		
	//una carta non puo' essere la destinataria del proprio messaggio
	function noSelfOutMessage = 
		if(forall $card in Name with not(outboxIsNone($card)) implies outboxName($card)!=$card ) then
			true
		else
			false
		endif
	
	
	function authentic($n in Name) = if($n = AA or $n = BB) then
						true
					else
						false
					endif




	//ora ne cancella uno solo; ne dovrebbe cancellare un sottoinsieme
	//rule r_loseMsg($receiver in Name) =
	//choose $t in MessageType, $na in Name, $value in MoneyDomain, $tid in TidDomain with
	//						inbox($receiver, $t, $na, $value, $tid) do
	//	inbox($receiver, $t, $na, $value, $tid) := false

	macro rule r_startTo($receiver in Name) =
		if(isNone($receiver)) then
			//choose $na in Name, $value in MoneyDomain, $tid in TidDomain with  do
			if(not(tids(startTo_tid))
							and authentic(startTo_na) and startTo_na!= $receiver) then
				par
					inbox(startTo_na, REQ, $receiver, startTo_value, startTo_tid) := true
					outboxMessage($receiver) := REQ
					outboxName($receiver) := startTo_na
					outboxMoney($receiver) := startTo_value
					outboxTid($receiver) := startTo_tid
					outboxIsNone($receiver) := false
					tids(startTo_tid) := true
				endpar
			endif
		endif

	macro rule r_req($receiver in Name) = 
		//choose $na in Name, $value in MoneyDomain, $tid in TidDomain with 
		if(inbox($receiver, REQ, req_na, req_value, req_tid) and authentic(req_na)
				and req_na!=$receiver and req_value <= balance($receiver) and
				(isNone($receiver))) then
			par
				inbox(req_na, VAL, $receiver, req_value, req_tid) := true
				outboxMessage($receiver) := VAL
				outboxName($receiver) := req_na
				outboxMoney($receiver) := req_value
				outboxTid($receiver) := req_tid
				outboxIsNone($receiver) := false
				balance($receiver) := balance($receiver) - req_value
				inbox($receiver, REQ, req_na, req_value, req_tid) := false
			endpar
		endif
		
	macro rule r_val($receiver in Name) = 
		//choose $na in Name, $value in MoneyDomain, $tid in TidDomain with 
		if(inbox($receiver, VAL, val_na, val_value, val_tid) and 
				(outboxIsNone($receiver) = false and
				outboxMessage($receiver) = REQ and
				outboxName($receiver) = val_na and
				outboxMoney($receiver) = val_value and
				outboxTid($receiver) = val_tid)) then
			par
				inbox(val_na, ACK, $receiver, val_value, val_tid) := true
				outboxMessage($receiver) := ACK
				//outboxName($receiver) := val_na
				//outboxMoney($receiver) := val_value
				//outboxTid($receiver) := val_tid
				outboxIsNone($receiver) := false
				balance($receiver) := balance($receiver) + val_value
				inbox($receiver, VAL, val_na, val_value, val_tid) := false
			endpar
		endif

	macro rule r_ack($receiver in Name) =
		//choose $na in Name, $value in MoneyDomain, $tid in TidDomain with
		if(inbox($receiver, ACK, ack_na, ack_value, ack_tid) and
				(outboxIsNone($receiver) = false and
				outboxMessage($receiver) = VAL and
				outboxName($receiver) = ack_na and
				outboxMoney($receiver) = ack_value and
				outboxTid($receiver) = ack_tid)) then
			par
				outboxIsNone($receiver) := true
				inbox($receiver, ACK, ack_na, ack_value, ack_tid) := false
			endpar
		endif

	/*macro rule r_abort($receiver in Name) =
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
		endpar*/

	invariant over tids: (useOfTid)
	invariant over tids: (noSelfMessage)
	invariant over tids: (noSelfOutMessage)
	
	

	//in questo modo la regola non puo' essere mappata in NuSMV
	main rule r_irule =
		//choose $receiver in Name with authentic($receiver) do
			switch(chosenRule)
				//case LOSEMSGRULE:
				//	r_loseMsg[receiver]
				case STARTTORULE:
					r_startTo[receiver]
				case REQRULE:
					r_req[receiver]
				case VALRULE:
					r_val[receiver]
				case ACKRULE:
					r_ack[receiver]
			//	case ABORTRULE:
			//		r_abort[receiver]
			endswitch

default init s0:
	function balance($n in Name) = at({AA->5n, BB->5n},$n)
	function inbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function exLogFrom($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function exLogTo($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	function tids($tid in TidDomain) = false
	function outboxIsNone($n in Name) = true
	//function outboxMessage($n in Name) = ACK
	//function outboxName($n in Name) = AA
	//function outboxMoney($n in Name) = 0n
	//function outboxTid($n in Name) = 1n
