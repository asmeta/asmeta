asm Yahalom

//from paper "Relations between Formal and Semi-formal Specification Systems: a Case Study"
//Kas (Kbs) is the key shared between A (B) and the server. In the paper they are called Ka and Kb.
//1. A -> B: A, Na
//2. B -> S: B, {A, Na, Nb}Kbs
//3. S -> A: {B, Kab, Na, Nb}Kas, {A, Kab}Kbs
//4. A -> B: {A, Kab}Kb, {Nb}Kab

//Flawed version
//1. A -> B: A, Na
//2. B -> S: B, Nb, {A, Na}Kbs
//3. S -> A: {B, Kab, Na, Nb}Ka, {A, Kab}Kbs
//4. A -> B: {A, Kab}Kbs, {Nb}Kab

import ../../STDL/StandardLibrary

signature:
	domain Server subsetof Agent
	domain Actor subsetof Agent
	dynamic abstract domain Packet // PACKET
	dynamic abstract domain Message // MESSAGE
	dynamic abstract domain Nonce // NONCE

	dynamic abstract domain ShrKey // SHRKEY
	dynamic abstract domain SesKey // SESKEY

	//AN: A, Na
	//AANNK: B, {A, Na, Nb}Kbs
	//AKNNKM: {B, Kab, Na, Nb}Ka, {A, Kab}Kbs
	//AKK: {A, Kab}Kbs
	//MNK: {A, Kab}Kbs, {Nb}Kab
	//enum domain MessageType = {AN | A_ANNK | AKNNK_AKK | AKK_NK | ANNK | AKNNK | AKK | NK}
	enum domain MessageType = {AN | AANNK | AKNNKAKK | AKKNK | ANNK | AKNNK | AKK | NK}
	dynamic controlled messageType: Message -> MessageType

	dynamic controlled messageAN: Message -> Prod(Agent, Nonce)
	dynamic controlled messageA_ANNK: Message -> Prod(Agent, Message)
	dynamic controlled messageAKNNK_AKK: Message -> Prod(Message, Message)
	dynamic controlled messageAKK_NK: Message -> Prod(Message, Message)

	dynamic controlled messageANNK: Message -> Prod(Agent, Nonce, Nonce, ShrKey)
	dynamic controlled messageAKNNK: Message -> Prod(Agent, SesKey, Nonce, Nonce, ShrKey)
	dynamic controlled messageAKK: Message -> Prod(Agent, SesKey, ShrKey)
	dynamic controlled messageNK: Message -> Prod(Nonce, SesKey)
	dynamic controlled traffic: Packet -> Prod(Agent, Agent, Message) // traffic
	derived cont: Prod(Agent, Agent, Message) -> Message //cont
	dynamic controlled recipient: Nonce -> Agent // recipient
	dynamic controlled isFresh: Nonce -> Boolean // isFresh
	dynamic controlled nonce: Agent -> Powerset(Nonce)
	dynamic controlled receive: Agent -> Packet // receive
	derived shrkey: Agent -> ShrKey // shrkey
	dynamic monitored wish2init: Actor -> Boolean
	dynamic monitored wish2init: Prod(Actor, Actor) -> Boolean
	derived decryptANNK: Prod(Message, ShrKey) -> Prod(Agent, Nonce, Nonce)
	derived decryptAKNNK: Prod(Message, ShrKey) -> Prod(Agent, SesKey, Nonce, Nonce)
	derived decryptAKK: Prod(Message, ShrKey) -> Prod(Agent, SesKey)
	derived decryptNK: Prod(Message, SesKey) -> Nonce

	dynamic monitored burglary: Boolean
	dynamic controlled notes: SesKey -> Boolean

	dynamic controlled authOk: Prod(Actor, Actor) -> Boolean
	static server: Server

	static agentA: Actor
	static agentB: Actor

	derived shrkeyA: ShrKey
	derived shrkeyB: ShrKey

definitions:
	function shrkey($a in Agent) =
		switch($a)
			case agentA: shrkeyA
			case agentB: shrkeyB
		endswitch

	function decryptANNK($messageANNK in Message, $shrKey in ShrKey) =
		let ($message = messageANNK($messageANNK)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if($shrKey = fourth($message)) then
					(first($message), second($message), third($message))
				endif
			endif
		endlet

	function decryptAKNNK($messageAKNNK in Message, $shrKey in ShrKey) =
		let ($message = messageAKNNK($messageAKNNK)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if($shrKey = fifth($message)) then
					(first($message), second($message), third($message), fourth($message))
				endif
			endif
		endlet

	function decryptAKK($messageAKK in Message, $shrKey in ShrKey) =
		let ($message = messageAKK($messageAKK)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if($shrKey = third($message)) then
					(first($message), second($message))
				endif
			endif
		endlet

	function decryptNK($messageNK in Message, $sesKey in SesKey) =
		let ($message = messageNK($messageNK)) in
			if( isDef($message) ) then //potrebbe essere arrivato un altro tipo di messaggio
				if($sesKey = second($message)) then
					first($message)
				endif
			endif
		endlet

	rule r_routeMsg =
		forall $x in Agent with true do
			choose $p in Packet with true do
				receive($x) := $p

	rule r_send($x in Agent, $y in Agent, $msg in Message) =
		extend Packet with $p do
			par
				traffic($p) := ($x, $y, $msg)
				receive($y) := $p
			endpar

	rule r_clear($p in Packet) =
		receive(self) := undef

	rule r_addNonce($agent in Agent, $nonce in Nonce) =
		nonce($agent) := including(nonce($agent), $nonce)

	rule r_serverActivity =
		if(isDef(receive(self))) then // c'e' un pacchetto nel canale
			let ($traffic = traffic(receive(self))) in
				if(second($traffic)=self) then // il messaggio e' destinato all'agente
					let($receivedMessage = third($traffic)) in
						if(messageType($receivedMessage)=AANNK) then // e' un messaggio per questa regola
							let($y = first(messageA_ANNK($receivedMessage)), $msg = second(messageA_ANNK($receivedMessage)),
									$decrMsg = decryptANNK($msg, shrkey($y)),
										$x=first($decrMsg), $n1 = second($decrMsg), $n2 = third($decrMsg),
										$Kx = shrkey($x), $Ky = shrkey($y)  ) in
								par
									extend Message with $messageAKNNK_AKK, $messageAKNNK, $messageAKK do
										extend SesKey with $Kxy do
											par
												messageType($messageAKNNK_AKK) := AKNNKAKK
												messageType($messageAKNNK) := AKNNK
												messageType($messageAKK) := AKK

												messageAKNNK_AKK($messageAKNNK_AKK) := ($messageAKNNK, $messageAKK)
												messageAKNNK($messageAKNNK) := ($y, $Kxy, $n1, $n2, $Kx)
												messageAKK($messageAKK) := ($x, $Kxy, $Ky)
												r_send[self, $x, $messageAKNNK_AKK]
												if(burglary) then
													notes($Kxy) := true
												else
													notes($Kxy) := false
												endif
											endpar
									r_clear[receive(self)]
								endpar
							endlet
						endif
					endlet
				endif
			endlet
		endif

	rule r_yahalom1 =
		//if(wish2init(self, X)) then
		if(wish2init(self)) then
			choose $x in Actor with $x != self and wish2init(self, $x) do
				//let ($newNonce = nonce(Self, x)) in
				extend Nonce with $newNonce do
					par
						recipient($newNonce) := $x
						isFresh($newNonce) := true
						r_addNonce[self, $newNonce]
						extend Message with $messageAN do
							par
								messageType($messageAN) := AN // new
								messageAN($messageAN) := (self, $newNonce)
								r_send[self, $x, $messageAN]
							endpar
						//wish2init(self, X) := false
					endpar
				//endlet
		endif

	rule r_yahalom2 =
		if(isDef(receive(self))) then // c'e' un pacchetto nel canale
			let ($traffic = traffic(receive(self))) in
				if(second($traffic)=self) then // il messaggio e' destinato all'agente
					let($receivedMessage = third($traffic)) in
						if(messageType($receivedMessage)=AN) then // e' un messaggio per questa regola
							let($x = first(messageAN($receivedMessage)), $n0 = second(messageAN($receivedMessage))) in
								par
									extend Nonce with $newNonce do
										par
											recipient($newNonce):= $x
											isFresh($newNonce) := true
											r_addNonce[self, $newNonce]
											extend Message with $messageA_ANNK, $messageANNK  do
												par
													messageType($messageA_ANNK) := AANNK
													messageType($messageANNK) := ANNK
													messageA_ANNK($messageA_ANNK) := (self, $messageANNK)
													messageANNK($messageANNK) := ($x, $n0, $newNonce, shrkey(self))
													r_send[self, server, $messageA_ANNK]
												endpar
										endpar
									r_clear[receive(self)]
								endpar
							endlet
						endif
					endlet
				endif
			endlet
		endif

	rule r_yahalom3 =
		if(isDef(receive(self))) then // c'e' un pacchetto nel canale
			let ($traffic = traffic(receive(self))) in
				if(second($traffic)=self) then // il messaggio e' destinato all'agente
					let($receivedMessage = third($traffic)) in
						if(messageType($receivedMessage)=AKNNKAKK) then // e' un messaggio per questa regola
							let($msg1 = first(messageAKNNK_AKK($receivedMessage)),
								$msg2 = second(messageAKNNK_AKK($receivedMessage)),
								$msg1Decr = decryptAKNNK($msg1, shrkey(self)),
								$x = first($msg1Decr), $Kxy=second($msg1Decr), $n1 = third($msg1Decr), $n2 = fourth($msg1Decr)
								) in
								par
									if(contains(nonce(self), $n1) and recipient($n1)=$x and isFresh($n1)) then
										extend Message with $messageAKK_NK, $messageNK do
											par
												messageType($messageAKK_NK) := AKKNK
												messageType($messageNK) := NK
												messageAKK_NK($messageAKK_NK) := ($msg2, $messageNK)
												messageNK($messageNK) := ($n2, $Kxy)
												r_send[self, $x, $messageAKK_NK]
											endpar
									endif
									r_clear[receive(self)]
								endpar
							endlet
						endif
					endlet
				endif
			endlet
		endif

	rule r_yahalom4 =
		if(isDef(receive(self))) then // c'e' un pacchetto nel canale
			let ($traffic = traffic(receive(self))) in
				if(second($traffic)=self) then // il messaggio e' destinato all'agente
					let($receivedMessage = third($traffic)) in
						if(messageType($receivedMessage)=AKKNK) then // e' un messaggio per questa regola
							let($msg1 = first(messageAKK_NK($receivedMessage)),
									$msg2 = second(messageAKK_NK($receivedMessage)),
									$msg1Decr = decryptAKK($msg1, shrkey(self)),
									$x = first($msg1Decr), $Kxy = second($msg1Decr),
									$n0 = decryptNK($msg2, $Kxy) ) in
								par
									if(contains(nonce(self), $n0) and recipient($n0)=$x and isFresh($n0)) then
										par
											isFresh($n0) := false
											authOk($x, self) := true
										endpar
									endif
									r_clear[receive(self)]
								endpar
							endlet
						endif
					endlet
				endif
			endlet
		endif

	rule r_actorRule =
		par
			r_yahalom1[]
			r_yahalom2[]
			r_yahalom3[]
			r_yahalom4[]
		endpar

	main rule r_Main =
		par
			forall $actor in Actor with true do
				program($actor)
			program(server)
		endpar

default init s0:
	function nonce($agent in Agent) = {}
	function authOk($a in Actor, $b in Actor) = false

	agent Actor:
		r_actorRule[]

	agent Server:
		r_serverActivity[]
