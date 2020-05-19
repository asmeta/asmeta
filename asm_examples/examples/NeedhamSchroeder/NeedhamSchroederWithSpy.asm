asm NeedhamSchroederWithSpy

//Model taken from paper "A Realistic Environment for Crypto-Protocol Analyses by ASMs"
//The steps of the protocol are:
//1. A -> B : {Na, A}Kb
//2. B -> A : {Na, Nb}Ka
//3. A -> B : {Nb}Kb

//This version can be attacked by Lowe's attack
//Version with spy

// Attack
// A -> Spy: A.Spy.{Na, A}Kspy (with recipent(Na) = Spy)
// Spy -> B: Spy.B.{Na, A}Kb
// B -> A: B.A.{Na, Nb}Ka (with recipent(Na) = A)
// A -> Spy: A.Spy.{Nb}Kspy

//Let A,B \notin COMPR. If A starts a run with Spy, then there exists a nonce Nb associated by B with A,
//such that Nb is not associated to a run between A and B, and Nb is compromised.

import ../../STDL/StandardLibrary

signature:
	//Network model domains
	dynamic abstract domain Nonce // NONCE
	abstract domain PubKey // PUBKEY
	abstract domain PriKey // PRIKEY
	dynamic abstract domain Message // MESSAGE
	dynamic abstract domain Traffic // TRAFFIC

	//NAK: Nonce, Agent, Key
	//NNK: Nonce, Nonce, Key
	//NK: Nonce, Key
	enum domain MessageType = {NAK | NNK | NK} // dominio per NeedhamSchroeder

	domain GoodAgent subsetof Agent
	domain BadAgent subsetof Agent

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
	//dynamic monitored wishToInitiate: Prod(Agent, Agent) -> Boolean
	dynamic monitored wishToInitiate: Agent -> Boolean
	dynamic monitored wishToInitiate: Prod(Agent, Agent) -> Boolean
	//dynamic monitored receive: Prod(Agent, Traffic) -> Boolean
	dynamic controlled message: Message -> Any // usata solo per completezza
	dynamic controlled messageType: Message -> MessageType // tipo di messaggio
	dynamic controlled messageNAK: Message -> Prod(Nonce, Agent, PubKey)
	dynamic controlled messageNNK: Message -> Prod(Nonce, Nonce, PubKey)
	dynamic controlled messageNK: Message -> Prod(Nonce, PubKey)
	dynamic controlled readTraffic: Traffic -> Boolean // indica se un elemento di Traffic e' stato letto (dal destinatario)

	dynamic controlled spyOutMess: String

	// ci sono tre funzioni di decrittazione, una per ogi tipo di messaggio
	derived decryptNAK: Prod(Message, PriKey) -> Prod(Nonce, Agent)
	derived decryptNNK: Prod(Message, PriKey) -> Prod(Nonce, Nonce)
	derived decryptNK: Prod(Message, PriKey) -> Nonce

	static agentA: GoodAgent
	static agentB: GoodAgent
	static spy: BadAgent

	static pubKeyA: PubKey
	static pubKeyB: PubKey
	static pubKeySpy: PubKey
	static priKeyA: PriKey
	static priKeyB: PriKey
	static priKeySpy: PriKey

	//static fake: BadAgent -> GoodAgent

definitions:
	/*function fake($ba in BadAgent) =
		if($ba=spy) then
			agentB
		endif*/

	function pubkey($a in Agent) =
		switch($a)
			case agentA: pubKeyA
			case agentB: pubKeyB
			case spy: pubKeySpy
		endswitch

	function prikey($a in Agent) =
		switch($a)
			case agentA: priKeyA
			case agentB: priKeyB
			case spy: priKeySpy
		endswitch

	function inv($pubKey in PubKey) =
		if(pubkey(self) = $pubKey) then
			switch($pubKey)
				case pubKeyA: priKeyA
				case pubKeyB: priKeyB
				case pubKeySpy: priKeySpy
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
			//sceglie un agente in Agent. Potrebbe anche essere la spia.
			choose $x in Agent with $x != self and wishToInitiate(self, $x) and
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

	/* rule r2
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
	rule r_receiveNAK =
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
									message($message) := ($nonce0, $NewNonce, pubkey($x))
									messageType($message) := NNK
									messageNNK($message) := ($nonce0, $NewNonce, pubkey($x))
									r_send[self, $x, $message]
								endpar
							r_clear[$t]
						endpar
				endlet
			endlet

	/* rule r3
	if(receive(Self, _.Self.msg) and decrypt(msg, K−1self) = {Nonce1, Nonce2}) then
		if(Nonce1 \in Nonce(Self)) then
			let X = recipient (Nonce1 )
				send Self.X.{Nonce2}Kx
			end-let
			clear _.Self.msg
		end-if
	end-if
	*/
	rule r_receiveNNK =
		// la ricezione del messaggio e' equivalente alla scelta di un messaggio del traffico che non e' ancora stato letto
		// e il cui destinatario e' l'agente corrente (self).
		// In questo caso siamo interessati alla ricezione dei messaggi NNK.
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(cont($t)) = NNK do
			let ($decrMessage = decryptNNK(cont($t), prikey(self))) in
				let($nonce1 = first($decrMessage), $nonce2 = second($decrMessage)) in
					if( contains(genNonces(self), $nonce1) ) then  // controlla di aver generato lui la $nonce1.
																	// L'attacco di Lowe si basa sul fatto che questo controllo e' troppo debole
																	// perche' nel messaggio NNK non c'e' l'informazione del mittente.
						par
							let ($x = recipient($nonce1)) in // legge il recipient del messaggio (quello a cui lui aveva mandato il messaggio NAK con la $nonce1)
								extend Message with $message do
									par
										message($message) := ($nonce2, pubkey($x))
										messageType($message) := NK
										messageNK($message) := ($nonce2, pubkey($x))
										r_send[self, $x, $message]
									endpar
							endlet
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

	rule r_initSessionSpy =
		skip // la spia non inizia sessioni. In teoria potrebbe anche farlo e comportarsi come un agente onesto.

	rule r_receiveNAKspy =
		// la spia legge un messaggio NAK indirizzato a lei
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(cont($t)) = NAK do
			let ($decrMessage = decryptNAK(cont($t), prikey(self))) in
				let($nonce0 = first($decrMessage), $x = second($decrMessage)) in
					par
						extend Message with $message do
							choose $dest in GoodAgent with $dest != $x do // sceglie un altro agente a cui girare il messaggio
								par
									message($message) := ($nonce0, $x, pubkey($dest))
									messageType($message) := NAK
									messageNAK($message) := ($nonce0, $x, pubkey($dest))
									r_send[self, $dest, $message]
								endpar
						r_clear[$t]
					endpar
				endlet
			endlet

	rule r_receiveNNKspy =
		skip //non riceve questo messaggio. Questo messaggio viene ricevuto dall'agente che aveva iniziato la sessione.

	rule r_receiveNKspy =
		// la spia legge un messaggio NK indirizzato a lei.
		// Questo e' possibile perche' l'agente A che aveva iniziato la sessione con la spia spy, al momento di mandare il
		// messaggio NK, lo manda all'agente associato alla nonce che aveva generato (spy) e non controlla che il
		// messaggio NNK provenga veramente da spy.
		// In questo modo spy puo' leggere la nonce Nb generata da B.
		choose $t in Traffic with not(readTraffic($t)) and second(val($t)) = self and messageType(cont($t)) = NK do
			let ($decrMessage = decryptNK(cont($t), prikey(self))) in
				let($nonce0 = $decrMessage) in
					par
						genNonces(self) := including(genNonces(self), $nonce0) // la spia puo' leggere il $nonce generato dall'altro agente
						r_clear[$t]
					endpar
				endlet
			endlet

	rule r_SpyIllegal =
		par
			r_initSessionSpy[]
			r_receiveNAKspy[]
			r_receiveNNKspy[]
			r_receiveNKspy[]
		endpar

	rule r_agentRule =
		par
			// devono essere in sequenza per evitare update inconsistenti su genNonces(self). Infatti
			// in tutte e due le regole e' possibile che ci sia una scrittura su genNonces(self): capita
			// quando un agente crea una nuova sessione e risponde con un messaggio NNK ad un messaggio
			// NAK ricevuto precedentemente.
			seq
				r_initSession[]
				r_receiveNAK[]
			endseq
			r_receiveNNK[]
			r_receiveNK[]
		endpar

	main rule r_Main =
		par
			forall $agent in GoodAgent with true do
				program($agent)
			program(spy)
		endpar

default init s0:
	function genNonces($a in Agent) = {}

	agent GoodAgent:
		r_agentRule[]

	agent BadAgent:
		r_SpyIllegal[]
