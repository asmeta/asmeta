//mondex concrete da articolo:
//The mondex challenge: machine checked proofs for an electronic purse

asm mondexConcrete

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB | CC | DD}
	enum domain Status = {IDLE | EPR | EPV | EPA}
	enum domain EncrState = {REQ | VAL | ACK}
	enum domain FromTo = {FROM | TO}
	enum domain MessageType = {START | REQVALACK}
	domain Money subsetof Natural
	domain Numbers subsetof Natural
	dynamic controlled balance: Name -> Money
	static authentic: Name -> Boolean
	dynamic monitored fail: Boolean
	dynamic controlled state: Name -> Status
	dynamic controlled nextSeqNo: Name -> Numbers

	//5 funzioni per modellare pdAuth: name -> PayDetails
	dynamic controlled pdAuthFrom: Name -> Name
	dynamic controlled pdAuthFromNo: Name -> Natural
	dynamic controlled pdAuthTo: Name -> Name
	dynamic controlled pdAuthToNo: Name -> Natural
	dynamic controlled pdAuthValue: Name -> Natural

	//due funzioni per modellare l'ether perche' i messaggi sono di due tipi
	dynamic controlled ether: Prod(Name, Numbers, Name, Numbers, Money, EncrState) -> Boolean
	dynamic controlled ether: Prod(Name, Money, Numbers, FromTo) -> Boolean

	//due funzioni per modellare l'exLog perche' i messaggi sono di due tipi
	dynamic controlled exLog: Prod(Name, Name, Numbers, Name, Numbers, Money) -> Boolean

	dynamic monitored msgType: MessageType

	static isStartTo: FromTo -> Boolean
	static isStartFrom: FromTo -> Boolean
	static isReq: EncrState -> Boolean
	static isVal: EncrState -> Boolean
	static isAck: EncrState -> Boolean

	
definitions:
	domain Money = {0n..13n}
	domain Numbers = {1n..4n}

	function authentic($n in Name) = if($n = AA or $n = BB or $n=CC) then
						true
					else
						false
					endif
	
	function isStartTo($t in FromTo) = if($t=TO) then
						true
					else
						false
					endif

	function isStartFrom($t in FromTo) = if($t=FROM) then
						true
					else
						false
					endif

	function isReq($t in EncrState) = if($t=REQ) then
						true
					else
						false
					endif

	function isVal($t in EncrState) = if($t=VAL) then
						true
					else
						false
					endif

	function isAck($t in EncrState) = if($t=ACK) then
						true
					else
						false
					endif

	rule r_reqMsg($from in Name, $fromNo in Numbers, $to in Name, $toNo in Numbers, $value in Money) = 
		ether($from, $fromNo, $to, $toNo, $value, REQ) := true

	rule r_valMsg($value in Money, $from in Name, $fromNo in Numbers, $to in Name, $toNo in Numbers) = 
		ether($from, $fromNo, $to, $toNo, $value, VAL) := true

	rule r_ackMsg($from in Name, $fromNo in Numbers, $to in Name,$value in Money, $toNo in Numbers) = 
		ether($from, $fromNo, $to, $toNo, $value, ACK) := true

	macro rule r_mkpd($receiver in Name, $from in Name, $fromNo in Numbers, $to in Name,
							$toNo in Numbers, $value in Money) =
		par
			pdAuthFrom($receiver) := $from
			pdAuthFromNo($receiver) := $fromNo
			pdAuthTo($receiver) := $to
			pdAuthToNo($receiver) := $toNo
			pdAuthValue($receiver) := $value
		endpar

	macro rule r_setExLog($receiver in Name) =
		exLog($receiver, pdAuthFrom($receiver), pdAuthFromNo($receiver),
			pdAuthTo($receiver), pdAuthToNo($receiver), pdAuthValue($receiver)) := true

	rule r_setOutEmptyMsg =
		skip

	macro rule r_startFrom($receiver in Name, $m in Name, $v in Money, $n in Numbers) =
		if(authentic($m) and $receiver!=$m and $v<=balance($receiver)) then
			//choose $k in Numbers with nextSeqNo($receiver) + 1n = $k do
				par
					r_mkpd[$receiver, $receiver, nextSeqNo($receiver), $m, $n, $v]
					state($receiver) := EPR
					//nextSeqNo($receiver) := $k
					nextSeqNo($receiver) := nextSeqNo($receiver) + 1n
					r_setOutEmptyMsg[]
				endpar
		else
			r_setOutEmptyMsg[]
		endif

	macro rule r_startTo($receiver in Name, $m in Name, $v in Money, $n in Numbers) =
		if(authentic($m) and $receiver!=$m and $v<=balance($receiver)) then
			//choose $k with nextSeqNo($receiver) < $k do
				par
					r_mkpd[$receiver, $m, $n, $receiver, nextSeqNo($receiver), $v]
					state($receiver) := EPV
					r_reqMsg[$m, $n, $receiver, nextSeqNo($receiver), $v]
					//nextSeqNo(receiver) := $k
					nextSeqNo($receiver) := nextSeqNo($receiver) + 1n
				endpar
		else
			r_setOutEmptyMsg[]
		endif

	macro rule r_req($receiver in Name, $from in Name, $fromNo in Numbers, $to in Name,
			$toNo in Numbers, $value in Money, $t in EncrState) = 
		if($from=pdAuthFrom($receiver) and $fromNo=pdAuthFromNo($receiver) and
			 $to=pdAuthTo($receiver) and $toNo=pdAuthToNo($receiver) and
			$value=pdAuthValue($receiver) and $t=REQ) then
			par
				balance($receiver) := balance($receiver) - pdAuthValue($receiver)
				state($receiver) := EPA
				r_valMsg[$from, $fromNo, $to, $toNo, $value]
			endpar
		else
			r_setOutEmptyMsg[]
		endif

	macro rule r_val($receiver in Name, $from in Name, $fromNo in Numbers, $to in Name,
			$toNo in Numbers, $value in Money, $t in EncrState) = 
		if($from=pdAuthFrom($receiver) and $fromNo=pdAuthFromNo($receiver) and
			 $to=pdAuthTo($receiver) and $toNo=pdAuthToNo($receiver) and
			$value=pdAuthValue($receiver) and $t=VAL) then
			par
				balance($receiver) := balance($receiver) + pdAuthValue($receiver)
				state($receiver) := IDLE
				r_ackMsg[$from, $fromNo, $to, $toNo, $value]
			endpar
		else
			r_setOutEmptyMsg[]
		endif

	macro rule r_ack($receiver in Name, $from in Name, $fromNo in Numbers, $to in Name,
				$toNo in Numbers, $value in Money, $t in EncrState) =
		if($from=pdAuthFrom($receiver) and $fromNo=pdAuthFromNo($receiver) and
			 $to=pdAuthTo($receiver) and $toNo=pdAuthToNo($receiver) and
			$value=pdAuthValue($receiver) and $t=ACK) then
			par
				state($receiver) := IDLE
				r_setOutEmptyMsg[]
			endpar
		else
			r_setOutEmptyMsg[]
		endif

	macro rule r_loginIfNeeded($receiver in Name) = 
		if(state($receiver)=EPA or state($receiver)=EPV) then
			r_setExLog($receiver)
		endif

	macro rule r_abort($receiver in Name) =
		par
			r_loginIfNeeded[]
			state($receiver) := IDLE
			nextSeqNo($receiver) := nextSeqNo($receiver) + 1n
			r_setOutEmptyMsg[]
		endpar

	main rule r_Bop =
		choose $receiver in Name with authentic($receiver) and not(fail) do
			if(msgType = START) then
				choose $m in Name, $v in Money, $n in Numbers, $t in FromTo
						with ether($m, $v, $n, $t) do
					par
						if(isStartTo($t) and state($receiver)=IDLE) then
							r_startTo[$receiver, $m, $v, $n]
						endif
						if(isStartFrom($t) and state($receiver)=EPR) then
							r_startFrom[$receiver, $m, $v, $n]
						endif
						if(not(isStartTo($t) and state($receiver)=IDLE) and
						not(isStartFrom($t) and state($receiver)=EPR)) then
							r_abort[$receiver]
						endif
					endpar
			else
				choose $es in EncrState, $from in Name, $fromNo in Numbers, $to in Name,
						$toNo in Numbers, $value in Money
						with ether($from, $fromNo, $to, $toNo, $value, $es) do
					par
					if(isReq($es) and state($receiver)=EPR) then
						r_req[$receiver, $from, $fromNo, $to,$toNo, $value, $es]
					endif
					if(isVal($es) and state($receiver)=EPV) then
						r_val[$receiver, $from, $fromNo, $to,$toNo, $value, $es]
					endif
					if(isAck($es) and state($receiver)=EPA) then
						r_ack[$receiver, $from, $fromNo, $to,$toNo, $value, $es]
					endif
					if(not(isReq($es) and state($receiver)=EPR) and
							not(isVal($es) and state($receiver)=EPV) and
							not(isAck($es) and state($receiver)=EPA)) then
						r_abort[$receiver]	
					endif
					endpar
			endif

default init s0:
	function balance($n in Name) = at({AA->10n,BB->0n, CC->2n, DD->4n},$n)
	function ether($from in Name, $fromNo in Numbers, $to in Name, $toNo in Numbers,
							$value in Money, $es in EncrState) = false
	function ether($m in Name, $v in Money, $n in Numbers, $t in FromTo) = false
	function exLog($receiver in Name, $from in Name, $fromNo in Numbers, $to in Name,
					$toNo in Numbers, $value in Money, $es in EncrState) = false
