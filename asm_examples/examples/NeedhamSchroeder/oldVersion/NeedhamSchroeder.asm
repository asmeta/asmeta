asm NeedhamSchroeder

//Modello preso dall'articolo "A Realistic Environment for Crypto-Protocol Analyses by ASMs"

//vecchia versione con funzione monitorata receive

import ../../../STDL/StandardLibrary

signature:
	//Network model domains
	dynamic abstract domain Nonce // NONCE
	abstract domain PubKey // PUBKEY
	abstract domain PriKey // PRIKEY
	dynamic abstract domain Message // MESSAGE
	dynamic abstract domain Traffic // TRAFFIC

	enum domain MessageType = {NAK | NNK | NK} // dominio per NeedhamSchroeder

	//Network model functions
	dynamic monitored nonce: Prod(Agent, Agent) -> Nonce // nonce
	dynamic controlled genNonces: Agent -> Powerset(Nonce) // Nonce
	dynamic controlled recipient: Nonce -> Agent // recipient
	static inv: PubKey -> PriKey // inv
	static pubkey: Agent -> PubKey // pubkey
	static prikey: Agent -> PriKey // prikey
	dynamic controlled val: Traffic -> Prod(Agent, Agent, Message) //val
	derived cont: Traffic -> Message //cont

	//NeedhamSchroeder
	dynamic monitored wishToInitiate: Prod(Agent, Agent) -> Boolean
	dynamic monitored receive: Prod(Agent, Traffic) -> Boolean
	dynamic controlled message: Message -> Any // usata solo per completezza
	dynamic controlled messageType: Message -> MessageType // tipo di messaggio
	dynamic controlled messageNAK: Message -> Prod(Nonce, Agent, PubKey)
	dynamic controlled messageNNK: Message -> Prod(Nonce, Nonce, PubKey)
	dynamic controlled messageNK: Message -> Prod(Nonce, PubKey)
	dynamic controlled readTraffic: Traffic -> Boolean // indica se un elemento di Traffic e' stato letto (dal destinatario)

	// ci sono tre funzioni di decrittazione, una per ogi tipo di messaggio
	derived decryptNAK: Prod(Message, PriKey) -> Prod(Nonce, Agent)
	derived decryptNNK: Prod(Message, PriKey) -> Prod(Nonce, Nonce)
	derived decryptNK: Prod(Message, PriKey) -> Nonce

	static agentA: Agent
	static agentB: Agent

	static pubKeyA: PubKey
	static pubKeyB: PubKey
	static prikeyA: PriKey
	static prikeyB: PriKey

definitions:
	function pubkey($a in Agent) =
		switch($a)
			case agentA: pubKeyA
			case agentB: pubKeyB
		endswitch

	function prikey($a in Agent) =
		switch($a)
			case agentA: prikeyA
			case agentB: prikeyB
		endswitch

	function inv($pubKey in PubKey) =
		switch($pubKey)
			case pubKeyA: prikeyA
			case pubKeyB: prikeyB
		endswitch

	//cont(Sender.Recipient.msg) = msg
	function cont($t in Traffic) =
		third(val($t))

	//un messaggio puo' essere decrittato se c'e' corrispondenza tra la chiave pubblica e la chiave
	//privata. Tale corrispondenza viene testata tramite la funzione inv che associa ogni chiave pubblica
	//alla corrispondente chiave privata.
	function decryptNAK($m in Message, $k in PriKey) =
		let ($message = messageNAK($m)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if( inv(third($message)) = $k ) then
					(first($message), second($message))
				endif
			endif
		endlet

	function decryptNNK($m in Message, $k in PriKey) =
		let ($message = messageNNK($m)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if( inv(third($message)) = $k ) then
					(first($message), second($message))
				endif
			endif
		endlet

	function decryptNK($m in Message, $k in PriKey) =
		let ($message = messageNK($m)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if( inv(second(messageNK($m))) = $k ) then
					first($message)
				endif
			endif
		endlet

	rule r_clear($t in Traffic) =
		//val($t) := undef
		readTraffic($t) := true


	rule r_send($s in Agent, $r in Agent, $m in Message) =
		extend Traffic with $t do
			par
				val($t) := ($s, $r, $m)
				readTraffic($t) := false
			endpar

	/*
	if wishToInitiate(Self, X) then
		let NewNonce = nonce (Self, X)
			do in-parallel
				recipient (NewNonce ):= X
				add NewNonce in Nonce (Self )
				send Self.X.{NewNonce, Self }Kx
			end-par
		end-let
	end-if
	*/
	rule r_r1 =
		//choose $x in Agent with $x != self do
		choose $x in Agent with $x != self and
								//A.B.{N, A}kb implies not(wishToInitiate(A, B))
								(forall $t in Traffic with ( (first(val($t))=self and second(val($t))=$x) implies
																		readTraffic($t)
																		)) do
			if(wishToInitiate(self, $x)) then
				//let ($NewNonce = nonce(self, $x)) in
				extend Nonce with $NewNonce do
					par
						recipient($NewNonce) := $x
						genNonces(self) := including(genNonces(self), $NewNonce)
						extend Message with $message do
							par
								message($message) := ($NewNonce, self, pubkey($x))
								messageType($message) := NAK
								messageNAK($message) := ($NewNonce, self, pubkey($x))
								r_send[self, $x, $message]
							endpar
					endpar
				//endlet
			endif

	/*
	if(receive(Self, _.Self.msg) and decrypt (msg, K−1self) = {Nonce0, X}) then
		let NewNonce = nonce (Self, X)
			do in-parallel
				recipient (NewNonce ):= X
				add NewNonce in Nonce(Self)
				send Self.X.{Nonce0, NewNonce}Kx
				clear _.Self.msg
			end-par
		end-let
	end-if
	*/
	rule r_r2 = //receiveNAK
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(third(val($t))) = NAK do
			if(receive(self, $t)) then
				let ($decrMessage = decryptNAK(cont($t), prikey(self))) in
					let($nonce0 = first($decrMessage), $x = second($decrMessage)) in
						//let ($NewNonce = nonce(self, $x)) in
						extend Nonce with $NewNonce do
							par
								recipient($NewNonce):= $x
								genNonces(self) := including(genNonces(self), $NewNonce)
								extend Message with $message do
									par
										message($message) := ($nonce0, $NewNonce, pubkey($x))
										messageType($message) := NNK
										messageNNK($message) := ($nonce0, $NewNonce, pubkey($x))
										r_send[self, $x, $message]
									endpar
								r_clear[$t] // clear _.Self.msg
							endpar
						//endlet
					endlet
				endlet
			endif

	/*
	if(receive(Self, _.Self.msg) and decrypt(msg, K−1self) = {Nonce1, Nonce2}) then
		if(Nonce1 \in Nonce(Self)) then
			let X = recipient (Nonce1 )
				send Self.X.{Nonce2}Kx
			end-let
			clear _.Self.msg
		end-if
	end-if
	*/
	rule r_r3 =
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(third(val($t))) = NNK do
			if(receive(self, $t)) then
				let ($decrMessage = decryptNNK(cont($t), prikey(self))) in
					let($nonce1 = first($decrMessage), $nonce2 = second($decrMessage)) in
						if( contains(genNonces(self), $nonce1) ) then
							par
								let ($x = recipient($nonce1)) in
									//send Self.X.{Nonce2 }Kx
									extend Message with $message do
										par
											message($message) := ($nonce2, pubkey($x))
											messageType($message) := NK
											messageNK($message) := ($nonce2, pubkey($x))
											r_send[self, $x, $message]
										endpar
								endlet
								r_clear[$t] // clear _.Self.msg
							endpar
						endif
					endlet
				endlet
			endif

	/*
	if (receive (Self, _.Self.msg ) and decrypt (msg, K−1self) = {Nonce0 }) then
		if Nonce0 2 Nonce (Self ) then
			clear .Self.msg
		end-if
	end-if
	*/
	rule r_r4 =
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(third(val($t))) = NK do
			if(receive(self, $t)) then
				let ($decrMessage = decryptNK(cont($t), prikey(self))) in
					let($nonce0 = $decrMessage) in
						if (contains(genNonces(self), $nonce0) ) then
							r_clear[$t] // clear .Self.msg
						endif
					endlet
				endlet
			endif

	rule r_agentRule =
		par
			seq //devono essere in sequenza per evitare update inconsistenti su genNonces(self)
				r_r1[]
				r_r2[]
			endseq
			r_r3[]
			r_r4[]
		endpar

	main rule r_Main =
		forall $agent in Agent with true do
			program($agent)

default init s0:
	function genNonces($a in Agent) = {}

	agent Agent:
		r_agentRule[]
