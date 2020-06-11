//mondex concrete da articolo:
//A concept-driven construction of the mondex protocol using three refinements
//capitolo 3: From atomic transfers to messages
//versione per NuSMV con 4 funzioni singole per l'outbox
//due carte autentiche
//tutti choose: nessuna monitorata
//controllo nella r_startTo che non ci siano pervenute
//richieste dalla carta alla quale vogliamo fare una richiesta

asm mcCap3ForNuSMVoutboxSingCheckOnStart
//non c'e' il metodo abort ed il metodo loose messages

import ../../../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../../../asm_examples/STDL/CTLlibrary

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
	derived check: Prod(Name, Name) -> Boolean
	
	static authentic: Name -> Boolean	
	static useOfTid: Boolean
	static noSelfMessage: Boolean
	static noSelfOutMessage: Boolean
	static outBoxEqualInbox: Boolean

definitions:
	domain TidDomain = {1n..2n}
	domain MoneyDomain = {0n, 5n, 10n}
	
		
	//controlla che non la carta $receiver non abbia ricevuto una richiesta da $na
	//In caso affermativo $receiver puo' fare una richiesta a $na
	function check ($receiver in Name, $na in Name) =
		if(not(exist $v in MoneyDomain, $t in TidDomain with inbox($receiver, REQ, $na, $v, $t))) then
			true
		else
			false
		endif
	
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

	//se un messaggio e' nella inbox di $card1, deve essere anche nella outbox
	//di $card2 che lo ha inviato. Vale solo per i messaggi di REQ e VAL
	function outBoxEqualInbox =
		if(forall $card1 in Name, $t in MessageType, $card2 in Name, $value in MoneyDomain,
			$tid in TidDomain with( (inbox($card1, $t, $card2, $value, $tid) and ($t!=ACK)) implies
						(not(outboxIsNone($card2)) and
						outboxMessage($card2) = $t and
						outboxName($card2) = $card1 and
						outboxMoney($card2) = $value and
						outboxTid($card2) = $tid) )) then
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
			//versione articolo
			//choose $na in Name, $value in MoneyDomain, $tid in TidDomain with not(tids($tid))
			//				and authentic($na) and $na!= $receiver) do
			choose $na in Name, $value in MoneyDomain, $tid in TidDomain
					 with not(tids($tid)) and authentic($na) and $na!= $receiver
					 and check($receiver, $na) do
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
		choose $na in Name, $value in MoneyDomain, $tid in TidDomain with 
				inbox($receiver, REQ, $na, $value, $tid) and authentic($na)
				and $na!=$receiver and $value <= balance($receiver) and
				(isNone($receiver)) do
			par
				inbox($na, VAL, $receiver, $value, $tid) := true
				outboxMessage($receiver) := VAL
				outboxName($receiver) := $na
				outboxMoney($receiver) := $value
				outboxTid($receiver) := $tid
				outboxIsNone($receiver) := false
				balance($receiver) := balance($receiver) - $value
				inbox($receiver, REQ, $na, $value, $tid) := false
			endpar
		
	macro rule r_val($receiver in Name) = 
		choose $na in Name, $value in MoneyDomain, $tid in TidDomain with
				inbox($receiver, VAL, $na, $value, $tid) and 
				(outboxIsNone($receiver) = false and
				outboxMessage($receiver) = REQ and
				outboxName($receiver) = $na and
				outboxMoney($receiver) = $value and
				outboxTid($receiver) = $tid) do
			par
				inbox($na, ACK, $receiver, $value, $tid) := true
				outboxMessage($receiver) := ACK
				outboxName($receiver) := $na
				outboxMoney($receiver) := $value
				outboxTid($receiver) := $tid
				outboxIsNone($receiver) := false
				balance($receiver) := balance($receiver) + $value
				inbox($receiver, VAL, $na, $value, $tid) := false
			endpar

	macro rule r_ack($receiver in Name) =
		choose $na in Name, $value in MoneyDomain, $tid in TidDomain with
				inbox($receiver, ACK, $na, $value, $tid) and
				(outboxIsNone($receiver) = false and
				outboxMessage($receiver) = VAL and
				outboxName($receiver) = $na and
				outboxMoney($receiver) = $value and
				outboxTid($receiver) = $tid) do
			par
				outboxIsNone($receiver) := true
				inbox($receiver, ACK, $na, $value, $tid) := false
			endpar

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
						exLogFrom($receiver, $na, $value, $tid) := false
					endif
				endpar
			outboxIsNone($receiver) := true
		endpar*/

	CTLSPEC ag(useOfTid)
	CTLSPEC ag(noSelfMessage)
	CTLSPEC ag(noSelfOutMessage)
	
	
	//Problema che si presentava in no_abort_no_loose/2cards/mcCap3ForNuSMVoutboxSing.asm:
	//se una carta e' in req, l'altra carta prima o poi sara' in val
	//con ag non funziona. funziona con af, ma non e' la stessa cosa.
	//Il problema nasce con il seguente scenario.
	//All'inizio le outbox di AA e BB sono vuote (isNone = true).
	//AA esegue StartTo verso BB (richiede soldi a BB); in questo modo scrive sulla inbox di BB
	//e sulla sua outbox (isNone(AA) diventa false).
	//Poi BB esegue anche lui startTo: chiede soldi ad AA. Scrive sulla inbox di AA e sulla sua 
	//outbox (isNone(BB) diventa false).
	//Ora sia BB che AA non possono soddisfare alla richiesta dell'altra carta con la regola r_req
	//perche' per poter eseguire r_req bisogna avere la outbox vuota (isNone = true) e nessuna
	//delle due e' vuota perche' entrambe le carte hanno fatto una richiesta
	
	//Con il controllo check($receiver, $na) nella regola r_startTo
	//il problema prima descritto sembra risolto
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 1n) implies ef(inbox(BB, VAL, AA, 0n, 1n)))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 1n) implies ef(inbox(AA, VAL, BB, 0n, 1n)))
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 2n) implies ef(inbox(BB, VAL, AA, 0n, 2n)))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 2n) implies ef(inbox(AA, VAL, BB, 0n, 2n)))
	//Per i prossimi bisogna essere meno restrittivi (non si puo' usare AG): bisogna usare EF
	//perche' in alcuni casi la transizioni con potrebbero concludersi perche' non ci sono soldi
	//sul conto
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 1n) implies ef(inbox(BB, VAL, AA, 5n, 1n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 1n) implies ef(inbox(AA, VAL, BB, 5n, 1n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 2n) implies ef(inbox(BB, VAL, AA, 5n, 2n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 2n) implies ef(inbox(AA, VAL, BB, 5n, 2n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 1n) implies ef(inbox(BB, VAL, AA, 10n, 1n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 1n) implies ef(inbox(AA, VAL, BB, 10n, 1n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 2n) implies ef(inbox(BB, VAL, AA, 10n, 2n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 2n) implies ef(inbox(AA, VAL, BB, 10n, 2n)))
	//Contengono le precedenti ed altri casi.
	//Reintroducono AG ma inserendo il controllo sulla presenza di soldi sul conto.
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 1n) and ef( balance(AA) >= 5n)) implies ef(inbox(BB, VAL, AA, 5n, 1n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 1n) and ef( balance(BB) >= 5n)) implies ef(inbox(AA, VAL, BB, 5n, 1n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 2n) and ef( balance(AA) >= 5n)) implies ef(inbox(BB, VAL, AA, 5n, 2n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 2n) and ef( balance(BB) >= 5n)) implies ef(inbox(AA, VAL, BB, 5n, 2n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 1n) and ef( balance(AA) >= 10n)) implies ef(inbox(BB, VAL, AA, 10n, 1n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 1n) and ef( balance(BB) >= 10n)) implies ef(inbox(AA, VAL, BB, 10n, 1n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 2n) and ef( balance(AA) >= 10n)) implies ef(inbox(BB, VAL, AA, 10n, 2n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 2n) and ef( balance(BB) >= 10n)) implies ef(inbox(AA, VAL, BB, 10n, 2n)))
	
	//un messaggio di REQ rimane nella inbox fino a quando non viene messo il messaggio di VAL nella inbox
	//dell'altra scheda
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 1n) implies e(inbox(AA, REQ, BB, 0n, 1n), inbox(BB, VAL, AA, 0n, 1n)))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 1n) implies e(inbox(BB, REQ, AA, 0n, 1n), inbox(AA, VAL, BB, 0n, 1n)))
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 2n) implies e(inbox(AA, REQ, BB, 0n, 2n), inbox(BB, VAL, AA, 0n, 2n)))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 2n) implies e(inbox(BB, REQ, AA, 0n, 2n), inbox(AA, VAL, BB, 0n, 2n)))
	//Per i prossimi bisogna essere meno restrittivi (non si puo' usare AG): bisogna usare EF
	//perche' in alcuni casi la transizioni con potrebbero concludersi perche' non ci sono soldi
	//sul conto
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 1n) implies e(inbox(AA, REQ, BB, 5n, 1n), inbox(BB, VAL, AA, 5n, 1n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 1n) implies e(inbox(BB, REQ, AA, 5n, 1n), inbox(AA, VAL, BB, 5n, 1n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 2n) implies e(inbox(AA, REQ, BB, 5n, 2n), inbox(BB, VAL, AA, 5n, 2n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 2n) implies e(inbox(BB, REQ, AA, 5n, 2n), inbox(AA, VAL, BB, 5n, 2n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 1n) implies e(inbox(AA, REQ, BB, 10n, 1n), inbox(BB, VAL, AA, 10n, 1n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 1n) implies e(inbox(BB, REQ, AA, 10n, 1n), inbox(AA, VAL, BB, 10n, 1n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 2n) implies e(inbox(AA, REQ, BB, 10n, 2n), inbox(BB, VAL, AA, 10n, 2n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 2n) implies e(inbox(BB, REQ, AA, 10n, 2n), inbox(AA, VAL, BB, 10n, 2n)))
	//Contengono le precedenti ed altri casi.
	//Reintroducono AG ma inserendo il controllo sulla presenza di soldi sul conto.
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 1n) and (balance(AA)>=5n)) implies e(inbox(AA, REQ, BB, 5n, 1n), inbox(BB, VAL, AA, 5n, 1n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 1n) and (balance(BB)>=5n)) implies e(inbox(BB, REQ, AA, 5n, 1n), inbox(AA, VAL, BB, 5n, 1n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 2n) and (balance(AA)>=5n)) implies e(inbox(AA, REQ, BB, 5n, 2n), inbox(BB, VAL, AA, 5n, 2n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 2n) and (balance(BB)>=5n)) implies e(inbox(BB, REQ, AA, 5n, 2n), inbox(AA, VAL, BB, 5n, 2n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 1n) and (balance(AA)>=10n)) implies e(inbox(AA, REQ, BB, 10n, 1n), inbox(BB, VAL, AA, 10n, 1n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 1n) and (balance(BB)>=10n)) implies e(inbox(BB, REQ, AA, 10n, 1n), inbox(AA, VAL, BB, 10n, 1n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 2n) and (balance(AA)>=10n)) implies e(inbox(AA, REQ, BB, 10n, 2n), inbox(BB, VAL, AA, 10n, 2n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 2n) and (balance(BB)>=10n)) implies e(inbox(BB, REQ, AA, 10n, 2n), inbox(AA, VAL, BB, 10n, 2n)))
	
	//se una carta e' in REQ con la regola R_req ne uscira. Quello stato (tripla valore-tid-REQ)
	//non si potra' piu' presentare
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 1n) implies ef(ag(not(inbox(AA, REQ, BB, 0n, 1n)))))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 1n) implies ef(ag(not(inbox(BB, REQ, AA, 0n, 1n)))))
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 2n) implies ef(ag(not(inbox(AA, REQ, BB, 0n, 2n)))))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 2n) implies ef(ag(not(inbox(BB, REQ, AA, 0n, 2n)))))
	//Per i prossimi bisogna essere meno restrittivi (non si puo' usare AG): bisogna usare EF
	//perche' in alcuni casi la transizioni con potrebbero concludersi perche' non ci sono soldi
	//sul conto
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 1n) implies ef(ag(not(inbox(AA, REQ, BB, 5n, 1n)))))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 1n) implies ef(ag(not(inbox(BB, REQ, AA, 5n, 1n)))))
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 2n) implies ef(ag(not(inbox(AA, REQ, BB, 5n, 2n)))))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 2n) implies ef(ag(not(inbox(BB, REQ, AA, 5n, 2n)))))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 1n) implies ef(ag(not(inbox(AA, REQ, BB, 10n, 1n)))))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 1n) implies ef(ag(not(inbox(BB, REQ, AA, 10n, 1n)))))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 2n) implies ef(ag(not(inbox(AA, REQ, BB, 10n, 2n)))))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 2n) implies ef(ag(not(inbox(BB, REQ, AA, 10n, 2n)))))
	//Contengono le precedenti ed altri casi.
	//Reintroducono AG ma inserendo il controllo sulla presenza di soldi sul conto.
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 1n) and ef(balance(AA)>=5n)) implies ef(ag(not(inbox(AA, REQ, BB, 5n, 1n)))))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 1n) and ef(balance(BB)>=5n)) implies ef(ag(not(inbox(BB, REQ, AA, 5n, 1n)))))
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 2n) and ef(balance(AA)>=5n)) implies ef(ag(not(inbox(AA, REQ, BB, 5n, 2n)))))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 2n) and ef(balance(BB)>=5n)) implies ef(ag(not(inbox(BB, REQ, AA, 5n, 2n)))))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 1n) and ef(balance(AA)>=10n)) implies ef(ag(not(inbox(AA, REQ, BB, 10n, 1n)))))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 1n) and ef(balance(BB)>=10n)) implies ef(ag(not(inbox(BB, REQ, AA, 10n, 1n)))))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 2n) and ef(balance(AA)>=10n)) implies ef(ag(not(inbox(AA, REQ, BB, 10n, 2n)))))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 2n) and ef(balance(BB)>=10n)) implies ef(ag(not(inbox(BB, REQ, AA, 10n, 2n)))))
	
	//se una carta e' in val, l'altra carta prima o poi sara' in ack
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 1n) implies ef(inbox(BB, ACK, AA, 0n, 1n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 1n) implies ef(inbox(AA, ACK, BB, 0n, 1n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 2n) implies ef(inbox(BB, ACK, AA, 0n, 2n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 2n) implies ef(inbox(AA, ACK, BB, 0n, 2n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 1n) implies ef(inbox(BB, ACK, AA, 5n, 1n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 1n) implies ef(inbox(AA, ACK, BB, 5n, 1n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 2n) implies ef(inbox(BB, ACK, AA, 5n, 2n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 2n) implies ef(inbox(AA, ACK, BB, 5n, 2n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 10n, 1n) implies ef(inbox(BB, ACK, AA, 10n, 1n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 10n, 1n) implies ef(inbox(AA, ACK, BB, 10n, 1n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 10n, 2n) implies ef(inbox(BB, ACK, AA, 10n, 2n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 10n, 2n) implies ef(inbox(AA, ACK, BB, 10n, 2n)))

	//un messaggio di VAL rimane nella inbox fino a quando non viene messo il messaggio di ACK nella inbox
	//dell'altra scheda
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 1n) implies e(inbox(AA, VAL, BB, 0n, 1n), inbox(BB, ACK, AA, 0n, 1n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 1n) implies e(inbox(BB, VAL, AA, 0n, 1n), inbox(AA, ACK, BB, 0n, 1n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 2n) implies e(inbox(AA, VAL, BB, 0n, 2n), inbox(BB, ACK, AA, 0n, 2n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 2n) implies e(inbox(BB, VAL, AA, 0n, 2n), inbox(AA, ACK, BB, 0n, 2n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 1n) implies e(inbox(AA, VAL, BB, 5n, 1n), inbox(BB, ACK, AA, 5n, 1n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 1n) implies e(inbox(BB, VAL, AA, 5n, 1n), inbox(AA, ACK, BB, 5n, 1n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 2n) implies e(inbox(AA, VAL, BB, 5n, 2n), inbox(BB, ACK, AA, 5n, 2n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 2n) implies e(inbox(BB, VAL, AA, 5n, 2n), inbox(AA, ACK, BB, 5n, 2n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 10n, 1n) implies e(inbox(AA, VAL, BB, 10n, 1n), inbox(BB, ACK, AA, 10n, 1n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 10n, 1n) implies e(inbox(BB, VAL, AA, 10n, 1n), inbox(AA, ACK, BB, 10n, 1n)))
	CTLSPEC ag(inbox(AA, VAL, BB, 10n, 2n) implies e(inbox(AA, VAL, BB, 10n, 2n), inbox(BB, ACK, AA, 10n, 2n)))
	CTLSPEC ag(inbox(BB, VAL, AA, 10n, 2n) implies e(inbox(BB, VAL, AA, 10n, 2n), inbox(AA, ACK, BB, 10n, 2n)))
	
	//se una carta e' in VAL con la regola r_val ne uscira. Quello stato (tripla valore-tid-VAL)
	//non si potra' piu' presentare
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 1n) implies ef(ag(not(inbox(AA, VAL, BB, 0n, 1n)))))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 1n) implies ef(ag(not(inbox(BB, VAL, AA, 0n, 1n)))))
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 2n) implies ef(ag(not(inbox(AA, VAL, BB, 0n, 2n)))))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 2n) implies ef(ag(not(inbox(BB, VAL, AA, 0n, 2n)))))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 1n) implies ef(ag(not(inbox(AA, VAL, BB, 5n, 1n)))))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 1n) implies ef(ag(not(inbox(BB, VAL, AA, 5n, 1n)))))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 2n) implies ef(ag(not(inbox(AA, VAL, BB, 5n, 2n)))))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 2n) implies ef(ag(not(inbox(BB, VAL, AA, 5n, 2n)))))
	
	//se una carta e' in ACK con la regola r_ack ne uscira. Quello stato (tripla valore-tid-ACK)
	//non si potra piu' presentare
	CTLSPEC ag(inbox(AA, ACK, BB, 0n, 1n) implies ef(ag(not(inbox(AA, ACK, BB, 0n, 1n)))))
	CTLSPEC ag(inbox(BB, ACK, AA, 0n, 1n) implies ef(ag(not(inbox(BB, ACK, AA, 0n, 1n)))))
	CTLSPEC ag(inbox(AA, ACK, BB, 0n, 2n) implies ef(ag(not(inbox(AA, ACK, BB, 0n, 2n)))))
	CTLSPEC ag(inbox(BB, ACK, AA, 0n, 2n) implies ef(ag(not(inbox(BB, ACK, AA, 0n, 2n)))))
	CTLSPEC ag(inbox(AA, ACK, BB, 5n, 1n) implies ef(ag(not(inbox(AA, ACK, BB, 5n, 1n)))))
	CTLSPEC ag(inbox(BB, ACK, AA, 5n, 1n) implies ef(ag(not(inbox(BB, ACK, AA, 5n, 1n)))))
	CTLSPEC ag(inbox(AA, ACK, BB, 5n, 2n) implies ef(ag(not(inbox(AA, ACK, BB, 5n, 2n)))))
	CTLSPEC ag(inbox(BB, ACK, AA, 5n, 2n) implies ef(ag(not(inbox(BB, ACK, AA, 5n, 2n)))))
	CTLSPEC ag(inbox(AA, ACK, BB, 10n, 1n) implies ef(ag(not(inbox(AA, ACK, BB, 10n, 1n)))))
	CTLSPEC ag(inbox(BB, ACK, AA, 10n, 1n) implies ef(ag(not(inbox(BB, ACK, AA, 10n, 1n)))))
	CTLSPEC ag(inbox(AA, ACK, BB, 10n, 2n) implies ef(ag(not(inbox(AA, ACK, BB, 10n, 2n)))))
	CTLSPEC ag(inbox(BB, ACK, AA, 10n, 2n) implies ef(ag(not(inbox(BB, ACK, AA, 10n, 2n)))))
	
	//se una carta ha ricevuto la REQ, prima o poi dovrebbe ricevere anche l'ACK
	//Dovrebbe valere per proprieta' transitiva dai gruppi di regole sopra, ma non si sa mai...
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 1n) implies ef(inbox(AA, ACK, BB, 0n, 1n)))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 1n) implies ef(inbox(BB, ACK, AA, 0n, 1n)))
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 2n) implies ef(inbox(AA, ACK, BB, 0n, 2n)))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 2n) implies ef(inbox(BB, ACK, AA, 0n, 2n)))
	//Per i prossimi bisogna essere meno restrittivi (non si puo' usare AG): bisogna usare EF
	//perche' in alcuni casi la transizioni con potrebbero concludersi perche' non ci sono soldi
	//sul conto
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 1n) implies ef(inbox(AA, ACK, BB, 5n, 1n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 1n) implies ef(inbox(BB, ACK, AA, 5n, 1n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 2n) implies ef(inbox(AA, ACK, BB, 5n, 2n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 2n) implies ef(inbox(BB, ACK, AA, 5n, 2n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 1n) implies ef(inbox(AA, ACK, BB, 10n, 1n)))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 1n) implies ef(inbox(BB, ACK, AA, 10n, 1n)))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 2n) implies ef(inbox(AA, ACK, BB, 10n, 2n)))
	//Contengono le precedenti ed altri casi.
	//Reintroducono AG ma inserendo il controllo sulla presenza di soldi sul conto.
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 1n) and ef(balance(AA)>=5n)) implies ef(inbox(AA, ACK, BB, 5n, 1n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 1n) and ef(balance(BB)>=5n)) implies ef(inbox(BB, ACK, AA, 5n, 1n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 5n, 2n) and ef(balance(AA)>=5n)) implies ef(inbox(AA, ACK, BB, 5n, 2n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 5n, 2n) and ef(balance(BB)>=5n)) implies ef(inbox(BB, ACK, AA, 5n, 2n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 1n) and ef(balance(AA)>=10n)) implies ef(inbox(AA, ACK, BB, 10n, 1n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 1n) and ef(balance(BB)>=10n)) implies ef(inbox(BB, ACK, AA, 10n, 1n)))
	CTLSPEC ag((inbox(AA, REQ, BB, 10n, 2n) and ef(balance(AA)>=10n)) implies ef(inbox(AA, ACK, BB, 10n, 2n)))
	CTLSPEC ag((inbox(BB, REQ, AA, 10n, 2n) and ef(balance(BB)>=10n)) implies ef(inbox(BB, ACK, AA, 10n, 2n)))
		
	//se un messaggio e' nella inbox di $card1, deve essere anche nella outbox
	//di $card2 che lo ha inviato. Vale solo per i messaggi di REQ e VAL
	CTLSPEC ag(outBoxEqualInbox)
	//la stessa proprieta' scomposta 
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 1n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = REQ and outboxName(BB) = AA and outboxMoney(BB) = 0n and outboxTid(BB) = 1n))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 1n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = REQ and outboxName(AA) = BB and outboxMoney(AA) = 0n and outboxTid(AA) = 1n))
	CTLSPEC ag(inbox(AA, REQ, BB, 5n, 1n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = REQ and outboxName(BB) = AA and outboxMoney(BB) = 5n and outboxTid(BB) = 1n))
	CTLSPEC ag(inbox(BB, REQ, AA, 5n, 1n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = REQ and outboxName(AA) = BB and outboxMoney(AA) = 5n and outboxTid(AA) = 1n))
	CTLSPEC ag(inbox(AA, REQ, BB, 10n, 1n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = REQ and outboxName(BB) = AA and outboxMoney(BB) = 10n and outboxTid(BB) = 1n))
	CTLSPEC ag(inbox(BB, REQ, AA, 10n, 1n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = REQ and outboxName(AA) = BB and outboxMoney(AA) = 10n and outboxTid(AA) = 1n))
	CTLSPEC ag(inbox(AA, REQ, BB, 0n, 2n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = REQ and outboxName(BB) = AA and outboxMoney(BB) = 0n and outboxTid(BB) = 2n))
	CTLSPEC ag(inbox(BB, REQ, AA, 0n, 2n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = REQ and outboxName(AA) = BB and outboxMoney(AA) = 0n and outboxTid(AA) = 2n))
	CTLSPEC ag(inbox(AA, REQ, BB, 5n, 2n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = REQ and outboxName(BB) = AA and outboxMoney(BB) = 5n and outboxTid(BB) = 2n))
	CTLSPEC ag(inbox(BB, REQ, AA, 5n, 2n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = REQ and outboxName(AA) = BB and outboxMoney(AA) = 5n and outboxTid(AA) = 2n))
	CTLSPEC ag(inbox(AA, REQ, BB, 10n, 2n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = REQ and outboxName(BB) = AA and outboxMoney(BB) = 10n and outboxTid(BB) = 2n))
	CTLSPEC ag(inbox(BB, REQ, AA, 10n, 2n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = REQ and outboxName(AA) = BB and outboxMoney(AA) = 10n and outboxTid(AA) = 2n))
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 1n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = VAL and outboxName(BB) = AA and outboxMoney(BB) = 0n and outboxTid(BB) = 1n))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 1n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = VAL and outboxName(AA) = BB and outboxMoney(AA) = 0n and outboxTid(AA) = 1n))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 1n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = VAL and outboxName(BB) = AA and outboxMoney(BB) = 5n and outboxTid(BB) = 1n))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 1n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = VAL and outboxName(AA) = BB and outboxMoney(AA) = 5n and outboxTid(AA) = 1n))
	CTLSPEC ag(inbox(AA, VAL, BB, 10n, 1n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = VAL and outboxName(BB) = AA and outboxMoney(BB) = 10n and outboxTid(BB) = 1n))
	CTLSPEC ag(inbox(BB, VAL, AA, 10n, 1n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = VAL and outboxName(AA) = BB and outboxMoney(AA) = 10n and outboxTid(AA) = 1n))
	CTLSPEC ag(inbox(AA, VAL, BB, 0n, 2n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = VAL and outboxName(BB) = AA and outboxMoney(BB) = 0n and outboxTid(BB) = 2n))
	CTLSPEC ag(inbox(BB, VAL, AA, 0n, 2n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = VAL and outboxName(AA) = BB and outboxMoney(AA) = 0n and outboxTid(AA) = 2n))
	CTLSPEC ag(inbox(AA, VAL, BB, 5n, 2n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = VAL and outboxName(BB) = AA and outboxMoney(BB) = 5n and outboxTid(BB) = 2n))
	CTLSPEC ag(inbox(BB, VAL, AA, 5n, 2n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = VAL and outboxName(AA) = BB and outboxMoney(AA) = 5n and outboxTid(AA) = 2n))
	CTLSPEC ag(inbox(AA, VAL, BB, 10n, 2n) implies (not(outboxIsNone(BB)) and outboxMessage(BB) = VAL and outboxName(BB) = AA and outboxMoney(BB) = 10n and outboxTid(BB) = 2n))
	CTLSPEC ag(inbox(BB, VAL, AA, 10n, 2n) implies (not(outboxIsNone(AA)) and outboxMessage(AA) = VAL and outboxName(AA) = BB and outboxMoney(AA) = 10n and outboxTid(AA) = 2n))	

	//tutte false: esiste uno stato iniziale per cui il messaggio nella inbox non si presenta mai
	/*CTLSPEC ef(inbox(AA, REQ, BB, 0n, 1n))
	CTLSPEC ef(inbox(BB, REQ, AA, 0n, 1n))
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 1n))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 1n))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 1n))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 1n))
	CTLSPEC ef(inbox(AA, REQ, BB, 0n, 2n))
	CTLSPEC ef(inbox(BB, REQ, AA, 0n, 2n))
	CTLSPEC ef(inbox(AA, REQ, BB, 5n, 2n))
	CTLSPEC ef(inbox(BB, REQ, AA, 5n, 2n))
	CTLSPEC ef(inbox(AA, REQ, BB, 10n, 2n))
	CTLSPEC ef(inbox(BB, REQ, AA, 10n, 2n))
	CTLSPEC ef(inbox(AA, VAL, BB, 0n, 1n))
	CTLSPEC ef(inbox(BB, VAL, AA, 0n, 1n))
	CTLSPEC ef(inbox(AA, VAL, BB, 5n, 1n))
	CTLSPEC ef(inbox(BB, VAL, AA, 5n, 1n))
	CTLSPEC ef(inbox(AA, VAL, BB, 10n, 1n))
	CTLSPEC ef(inbox(BB, VAL, AA, 10n, 1n))
	CTLSPEC ef(inbox(AA, VAL, BB, 0n, 2n))
	CTLSPEC ef(inbox(BB, VAL, AA, 0n, 2n))
	CTLSPEC ef(inbox(AA, VAL, BB, 5n, 2n))
	CTLSPEC ef(inbox(BB, VAL, AA, 5n, 2n))
	CTLSPEC ef(inbox(AA, VAL, BB, 10n, 2n))
	CTLSPEC ef(inbox(BB, VAL, AA, 10n, 2n))
	CTLSPEC ef(inbox(AA, ACK, BB, 0n, 1n))
	CTLSPEC ef(inbox(BB, ACK, AA, 0n, 1n))
	CTLSPEC ef(inbox(AA, ACK, BB, 5n, 1n))
	CTLSPEC ef(inbox(BB, ACK, AA, 5n, 1n))
	CTLSPEC ef(inbox(AA, ACK, BB, 10n, 1n))
	CTLSPEC ef(inbox(BB, ACK, AA, 10n, 1n))
	CTLSPEC ef(inbox(AA, ACK, BB, 0n, 2n))
	CTLSPEC ef(inbox(BB, ACK, AA, 0n, 2n))
	CTLSPEC ef(inbox(AA, ACK, BB, 5n, 2n))
	CTLSPEC ef(inbox(BB, ACK, AA, 5n, 2n))
	CTLSPEC ef(inbox(AA, ACK, BB, 10n, 2n))
	CTLSPEC ef(inbox(BB, ACK, AA, 10n, 2n))*/

	//tutte false: danno un contro-esempio in cui si presenta il messaggio nella inbox
	//quindi esiste per ognuna uno stato iniziale che le permette di presentarsi
	CTLSPEC not(ef(inbox(AA, REQ, BB, 0n, 1n)))
	CTLSPEC not(ef(inbox(BB, REQ, AA, 0n, 1n)))
	CTLSPEC not(ef(inbox(AA, REQ, BB, 5n, 1n)))
	CTLSPEC not(ef(inbox(BB, REQ, AA, 5n, 1n)))
	CTLSPEC not(ef(inbox(AA, REQ, BB, 10n, 1n)))
	CTLSPEC not(ef(inbox(BB, REQ, AA, 10n, 1n)))
	CTLSPEC not(ef(inbox(AA, REQ, BB, 0n, 2n)))
	CTLSPEC not(ef(inbox(BB, REQ, AA, 0n, 2n)))
	CTLSPEC not(ef(inbox(AA, REQ, BB, 5n, 2n)))
	CTLSPEC not(ef(inbox(BB, REQ, AA, 5n, 2n)))
	CTLSPEC not(ef(inbox(AA, REQ, BB, 10n, 2n)))
	CTLSPEC not(ef(inbox(BB, REQ, AA, 10n, 2n)))
	CTLSPEC not(ef(inbox(AA, VAL, BB, 0n, 1n)))
	CTLSPEC not(ef(inbox(BB, VAL, AA, 0n, 1n)))
	CTLSPEC not(ef(inbox(AA, VAL, BB, 5n, 1n)))
	CTLSPEC not(ef(inbox(BB, VAL, AA, 5n, 1n)))
	CTLSPEC not(ef(inbox(AA, VAL, BB, 10n, 1n)))
	CTLSPEC not(ef(inbox(BB, VAL, AA, 10n, 1n)))
	CTLSPEC not(ef(inbox(AA, VAL, BB, 0n, 2n)))
	CTLSPEC not(ef(inbox(BB, VAL, AA, 0n, 2n)))
	CTLSPEC not(ef(inbox(AA, VAL, BB, 5n, 2n)))
	CTLSPEC not(ef(inbox(BB, VAL, AA, 5n, 2n)))
	CTLSPEC not(ef(inbox(AA, VAL, BB, 10n, 2n)))
	CTLSPEC not(ef(inbox(BB, VAL, AA, 10n, 2n)))
	CTLSPEC not(ef(inbox(AA, ACK, BB, 0n, 1n)))
	CTLSPEC not(ef(inbox(BB, ACK, AA, 0n, 1n)))
	CTLSPEC not(ef(inbox(AA, ACK, BB, 5n, 1n)))
	CTLSPEC not(ef(inbox(BB, ACK, AA, 5n, 1n)))
	CTLSPEC not(ef(inbox(AA, ACK, BB, 10n, 1n)))
	CTLSPEC not(ef(inbox(BB, ACK, AA, 10n, 1n)))
	CTLSPEC not(ef(inbox(AA, ACK, BB, 0n, 2n)))
	CTLSPEC not(ef(inbox(BB, ACK, AA, 0n, 2n)))
	CTLSPEC not(ef(inbox(AA, ACK, BB, 5n, 2n)))
	CTLSPEC not(ef(inbox(BB, ACK, AA, 5n, 2n)))
	CTLSPEC not(ef(inbox(AA, ACK, BB, 10n, 2n)))
	CTLSPEC not(ef(inbox(BB, ACK, AA, 10n, 2n)))
	
	//proprieta' sui conti
	//CTLSPEC ag(balance(AA) = 0n)//giustamente e' falsa
	//CTLSPEC ag(balance(BB) = 0n)//giustamente e' falsa
	//CTLSPEC ag(balance(AA) = 5n)//giustamente e' falsa
	//CTLSPEC ag(balance(BB) = 5n)//giustamente e' falsa
	//CTLSPEC ag(balance(AA) = 10n)//giustamente e' falsa
	//CTLSPEC ag(balance(BB) = 10n)//giustamente e' falsa
	CTLSPEC ag(balance(AA) != 0n)//giustamente e' falsa e mi mostra un esempio
	CTLSPEC ag(balance(BB) != 0n)//giustamente e' falsa e mi mostra un esempio
	CTLSPEC ag(balance(AA) != 5n)//giustamente e' falsa e mi mostra un esempio
	CTLSPEC ag(balance(BB) != 5n)//giustamente e' falsa e mi mostra un esempio
	CTLSPEC ag(balance(AA) != 10n)//giustamente e' falsa e mi mostra un esempio
	CTLSPEC ag(balance(BB) != 10n)//giustamente e' falsa e mi mostra un esempio
	//CTLSPEC ef(balance(AA) = 0n)//falsa: perche'? problema di stato iniziale
	//CTLSPEC ef(balance(BB) = 0n)//falsa: perche'? problema di stato iniziale
	//CTLSPEC ef(balance(AA) = 5n)//lo e' in tutti gli stati iniziali
	//CTLSPEC ef(balance(BB) = 5n)//lo e' in tutti gli stati iniziali
	//CTLSPEC ef(balance(AA) = 10n)//falsa: perche'? problema di stato iniziale
	//CTLSPEC ef(balance(BB) = 10n)//falsa: perche'? problema di stato iniziale
	
	//assenza di deadlock: sara' sempre verificata perche' le variabili
	//che modellano le choose possono sempre cambiare il loro valore
	CTLSPEC ag(ex(true))
	
	main rule r_irule =
		choose $receiver in Name, $rule in RuleId with authentic($receiver) do
			switch($rule)
				//case LOSEMSGRULE:
				//	r_loseMsg[$receiver]
				case STARTTORULE:
					r_startTo[$receiver]
				case REQRULE:
					r_req[$receiver]
				case VALRULE:
					r_val[$receiver]
				case ACKRULE:
					r_ack[$receiver]
			//	case ABORTRULE:
			//		r_abort[$receiver]
			endswitch

default init s0:
	function balance($n in Name) = at({AA->5n, BB->5n},$n)
	function inbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function exLogFrom($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	//function exLogTo($n in Name, $na in Name, $value in MoneyDomain,$tid in TidDomain) = false
	function tids($tid in TidDomain) = false
	function outboxIsNone($n in Name) = true
	function outboxMessage($n in Name) = ACK
	function outboxName($n in Name) = AA
	function outboxMoney($n in Name) = 0n
	function outboxTid($n in Name) = 1n
