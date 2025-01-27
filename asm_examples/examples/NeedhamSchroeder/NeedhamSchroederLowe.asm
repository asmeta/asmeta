asm NeedhamSchroederLowe

//Model taken from paper "A Realistic Environment for Crypto-Protocol Analyses by ASMs"
//Lowe's version of the protocol.
//The steps of the protocol are:
//1. A -> B : {Na, A}Kb
//2. B -> A : {Na, Nb, B}Ka
//3. A -> B : {Nb}Kb

import ../../STDL/StandardLibrary

signature:
	//Network model domains
	dynamic abstract domain Nonce // NONCE
	abstract domain PubKey // PUBKEY
	abstract domain PriKey // PRIKEY
	dynamic abstract domain Message // MESSAGE
	dynamic abstract domain Traffic // TRAFFIC

	//NAK: Nonce, Agent, Key
	//NNAK: Nonce, Nonce, Agent, Key
	//NK: Nonce, Key
	enum domain MessageType = {NAK | NNAK | NK} // dominio per NeedhamSchroeder

	//Network model functions
	dynamic monitored nonce: Prod(Agent, Agent) -> Nonce // nonce
	dynamic controlled genNonces: Agent -> Powerset(Nonce) // Nonce
	dynamic controlled recipient: Nonce -> Agent // recipient
	derived inv: PubKey -> PriKey // inv
	derived pubkey: Agent -> PubKey // pubkey
	derived prikey: Agent -> PriKey // prikey
	dynamic controlled val: Traffic -> Prod(Agent, Agent, Message) //val
	derived cont: Traffic -> Message //cont

	//NeedhamSchroeder
	dynamic monitored wishToInitiate: Agent -> Boolean
	dynamic monitored wishToInitiate: Prod(Agent, Agent) -> Boolean
	dynamic controlled message: Message -> Any // usata solo per completezza
	dynamic controlled messageType: Message -> MessageType // tipo di messaggio
	dynamic controlled messageNAK: Message -> Prod(Nonce, Agent, PubKey)
	dynamic controlled messageNNAK: Message -> Prod(Nonce, Nonce, Agent, PubKey) // differenza con Needham Schroeder originale: qui viene mandato anche l'identificativo del mittente
	dynamic controlled messageNK: Message -> Prod(Nonce, PubKey)
	dynamic controlled readTraffic: Traffic -> Boolean // indica se un elemento di Traffic e' stato letto (dal destinatario)

	// ci sono tre funzioni di decrittazione, una per ogi tipo di messaggio
	derived decryptNAK: Prod(Message, PriKey) -> Prod(Nonce, Agent)
	derived decryptNNAK: Prod(Message, PriKey) -> Prod(Nonce, Nonce, Agent)
	derived decryptNK: Prod(Message, PriKey) -> Nonce

	static agentA: Agent
	static agentB: Agent

	derived pubKeyA: PubKey
	derived pubKeyB: PubKey
	static priKeyA: PriKey
	static priKeyB: PriKey

definitions:
	function pubkey($a in Agent) =
		switch($a)
			case agentA: pubKeyA
			case agentB: pubKeyB
		endswitch

	function prikey($a in Agent) =
		switch($a)
			case agentA: priKeyA
			case agentB: priKeyB
		endswitch

	function inv($pubKey in PubKey) =
		if(pubkey(self) = $pubKey) then
			switch($pubKey)
				case pubKeyA: priKeyA
				case pubKeyB: priKeyB
			endswitch
		endif

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

	function decryptNNAK($m in Message, $k in PriKey) =
		let ($message = messageNNAK($m)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if( inv(fourth($message)) = $k ) then
					(first($message), second($message), third($message))
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

	// clear _.Self.msg
	// la cancellazione di un elemento del traffico dovrebbe essere, secondo l'articolo,
	// val($t) := undef (la valutazione dell'elemento del traffico). Poiche' tale soluzione
	// crea dei problemi in altre regole, e' stata inserita una funzione readTraffic($t) che vale true
	// solo quando l'elemento e' stato letto
	rule r_clear($t in Traffic) =
		//val($t) := undef
		readTraffic($t) := true

	rule r_send($s in Agent, $r in Agent, $m in Message) =
		extend Traffic with $t do // creazione di un nuovo elemento di traffico
			par
				val($t) := ($s, $r, $m)
				readTraffic($t) := false // l'elemento di traffico non e' ancora stato letto dal receiver
			endpar

	/* rule r1
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
	rule r_initSession =
		if(wishToInitiate(self)) then //l'agente vuole iniziare una sessione con qualcuno
			choose $x in Agent with $x != self and //wishToInitiate(self, $x) and
									//A.B.{N, A}kb implies not(wishToInitiate(A, B))
									(forall $t in Traffic with ( (first(val($t))=self and second(val($t))=$x) implies
																			readTraffic($t)
																			)) do
				extend Nonce with $NewNonce do // creazione di un nuovo nonce
					par
						recipient($NewNonce) := $x
						genNonces(self) := including(genNonces(self), $NewNonce)
						extend Message with $message do // creazione di un nuovo messaggio
							par
								message($message) := ($NewNonce, self, pubkey($x))
								messageType($message) := NAK
								messageNAK($message) := ($NewNonce, self, pubkey($x))
								r_send[self, $x, $message]
							endpar
					endpar
			endif

	/*rule R2’
	if(receive (Self, _.Self.msg ) and decrypt (msg, K−1self) = {Nonce0, X}) then
		let NewNonce = nonce (Self, X)
			do in-parallel
				recipient (NewNonce):= X
				add NewNonce in Nonce(Self)
				send Self.X.{Nonce0, NewNonce, Self}Kx
				clear _.Self.msg
			end-par
		end-let
	end-if
	*/
	rule r_receiveNAKrefined =
		// la ricezione del messaggio e' equivalente alla scelta di un messaggio del traffico che non e' ancora stato letto
		// e il cui destinatario e' l'agente corrente (self).
		// In questo caso siamo interessati alla ricezione dei messaggi NAK.
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(cont($t)) = NAK do
			let ($decrMessage = decryptNAK(cont($t), prikey(self))) in
				let($nonce0 = first($decrMessage), $x = second($decrMessage)) in
					extend Nonce with $NewNonce do // creazione di un nuovo nonce
						par
							recipient($NewNonce):= $x
							genNonces(self) := including(genNonces(self), $NewNonce)
							extend Message with $message do // creazione di un nuovo messaggio
								par
									// ora il messaggio non e' piu' NNK, ma NNAK: viene inviato
									// anche l'agente mittente del messaggio (self)
									message($message) := ($nonce0, $NewNonce, self, pubkey($x))
									messageType($message) := NNAK
									messageNNAK($message) := ($nonce0, $NewNonce, self, pubkey($x))
									r_send[self, $x, $message]
								endpar
							r_clear[$t]
						endpar
				endlet
			endlet

	/* rule r3'
	if (receive (Self, _.Self.msg ) and decrypt (msg, K−1self) = {Nonce1, Nonce2, X}) then
		if (Nonce1 \in Nonce (Self) and recipient(Nonce1) = X) then
			send Self.X.{Nonce2 }Kx
			clear _.Self.msg
		end-if
	end-if
	*/
	rule r_receiveNNAKrefined =
		// la ricezione del messaggio e' equivalente alla scelta di un messaggio del traffico che non e' ancora stato letto
		// e il cui destinatario e' l'agente corrente (self).
		// In questo caso siamo interessati alla ricezione dei messaggi NNAK.
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(cont($t)) = NNAK do
			let ($decrMessage = decryptNNAK(cont($t), prikey(self))) in
				let($nonce1 = first($decrMessage), $nonce2 = second($decrMessage), $x = third($decrMessage)) in
					if( contains(genNonces(self), $nonce1) and // controlla di aver generato lui la $nonce1
						recipient($nonce1) = $x // nuovo controllo: controlla anche che l'agente contenuto nel messaggio sia quello a cui lui aveva mandato la $nonce1
					) then
						par
							extend Message with $message do
								par
									message($message) := ($nonce2, pubkey($x))
									messageType($message) := NK
									messageNK($message) := ($nonce2, pubkey($x))
									r_send[self, $x, $message]
								endpar
							r_clear[$t]
						endpar
					endif
				endlet
			endlet

	/* rule r4
	if (receive (Self, _.Self.msg ) and decrypt (msg, K−1self) = {Nonce0 }) then
		if Nonce0 2 Nonce (Self ) then
			clear .Self.msg
		end-if
	end-if
	*/
	rule r_receiveNK =
		// la ricezione del messaggio e' equivalente alla scelta di un messaggio del traffico che non e' ancora stato letto
		// e il cui destinatario e' l'agente corrente (self).
		// In questo caso siamo interessati alla ricezione dei messaggi NK.
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(cont($t)) = NK do
			let ($decrMessage = decryptNK(cont($t), prikey(self))) in
				let($nonce0 = $decrMessage) in
					if (contains(genNonces(self), $nonce0) ) then
						r_clear[$t]
					endif
				endlet
			endlet

	rule r_agentRule =
		par
			// devono essere in sequenza per evitare update inconsistenti su genNonces(self). Infatti
			// in tutte e due le regole e' possibile che ci sia una scrittura su genNonces(self): capita
			// quando un agente crea una nuova sessione e risponde con un messaggio NNAK ad un messaggio
			// NAK ricevuto precedentemente.
			seq
				r_initSession[]
				r_receiveNAKrefined[]
			endseq
			r_receiveNNAKrefined[]
			r_receiveNK[]
		endpar

	main rule r_Main =
		forall $agent in Agent with true do
			program($agent)

default init s0:
	function genNonces($a in Agent) = {}

	agent Agent:
		r_agentRule[]
