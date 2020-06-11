asm taxiSingAgents

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

//it models the interaction between a client and a taxi

signature:
	domain Coord subsetof Integer
	domain Passo subsetof Integer
	domain TaxiAgent subsetof Agent
	enum domain TaxiState = {IDLETX | TOCLIENT | WITHCLIENT}
	domain ClientAgent subsetof Agent
	domain NumViaggi subsetof Integer
	enum domain ClientState = {WAITING | TRAVELLING | IDLECL | CALLTAXI | ENDTRAVELS}

	dynamic controlled taxiState: TaxiAgent -> TaxiState
	dynamic controlled taxiPosX: TaxiAgent -> Coord
	dynamic controlled taxiPosY: TaxiAgent -> Coord

	static taxi: TaxiAgent

	
	dynamic controlled clientState: ClientAgent -> ClientState
	dynamic controlled clientPosX: ClientAgent -> Coord
	dynamic controlled clientPosY: ClientAgent -> Coord
	dynamic controlled clientDestX: ClientAgent -> Coord
	dynamic controlled clientDestY: ClientAgent -> Coord
	dynamic controlled clientNumViaggi: ClientAgent -> NumViaggi
	dynamic monitored stepX: Passo
	dynamic monitored stepY: Passo
	dynamic monitored decideToTravel: Boolean
	dynamic monitored destX: Coord
	dynamic monitored destY: Coord
	static client: ClientAgent


definitions:
	domain NumViaggi = {0..2}
	domain Coord = {1..8}
	domain Passo ={-1..1}

	//aggiorna la coordinata $i di un taxi o di un cliente che sta
	//andando verso $j  
	rule r_updateCoord($i in Coord, $j in Coord) =	
		par		
			if($i > $j) then 
				$i := $i - 1
			endif
			if($i < $j) then 
				$i := $i + 1
			endif
		endpar

	rule r_idleMove =
		par
			if(clientPosX(self) > 1 and clientPosX(self) < 8) then
				clientPosX(self) := clientPosX(self) + stepX	
			endif
			if(clientPosY(self) > 1 and clientPosY(self) < 8) then
				clientPosY(self) := clientPosY(self) + stepY		
			endif
		endpar

	rule r_idle =
		if(clientState(self)=IDLECL) then
			if(clientNumViaggi(self)>0) then
				if(decideToTravel) then
					par
						clientState(self) := CALLTAXI
						clientDestX(self) := destX 
						clientDestY(self) := destY
					endpar
				else
					r_idleMove[]//se non chiama il taxi puo' muoversi
				endif
			else
				clientState(self) := ENDTRAVELS
			endif
		endif

	rule r_ClientAgent =
		r_idle[]

	rule r_receiveCall = 
		if(clientState(client) = CALLTAXI) then
			par
				taxiState(self) := TOCLIENT //il taxi va dal cliente
				clientState(client) := WAITING //il cliente si mette in attesa
			endpar
		endif

	rule r_toClient = 
		if(taxiState(self)=TOCLIENT) then
			if(taxiPosX(self) = clientPosX(client) and taxiPosY(self) = clientPosY(client)) then
				par			
					taxiState(self) := WITHCLIENT //il taxi carica il cliente
					clientState(client) := TRAVELLING //il cliente e' in viaggio con il taxi
				endpar
			else
				par
					r_updateCoord[taxiPosX(self), clientPosX(client)]
					r_updateCoord[taxiPosY(self), clientPosY(client)]
				endpar
			endif
		endif
	
	rule r_withClient =
		if(taxiState(self)=WITHCLIENT) then
			if(taxiPosX(self) = clientDestX(client) and taxiPosY(self) = clientDestY(client)) then
				par			
					taxiState(self) := IDLETX //il taxi scarica il cliente
					clientState(client) := IDLECL //il cliente non e' sul taxi
					clientNumViaggi(client) := clientNumViaggi(client) - 1
				endpar
			else
				par
					r_updateCoord[taxiPosX(self), clientDestX(client)]
					r_updateCoord[taxiPosY(self), clientDestY(client)]
					r_updateCoord[clientPosX(client), clientDestX(client)]
					r_updateCoord[clientPosY(client), clientDestY(client)]
				endpar
			endif
		endif

	rule r_TaxiAgent =
		par
			r_receiveCall[]
			r_toClient[]
			r_withClient[]
		endpar

	//proprieta' di safety: se il taxi e' in viaggio con il cliente
	//le loro coordinate devono essere uguali
	//safety property: if the taxi is traveling with the client, their coordinates must be equal
	CTLSPEC ag(taxiState(taxi) = WITHCLIENT implies
				(taxiPosX(taxi) = clientPosX(client) and taxiPosY(taxi) = clientPosY(client)))
	
	//quando il cliente ha terminato il numero di viaggi non viaggia piu'
	//when the client has reached the maximum number of travels he does not travel anymore
	CTLSPEC ag((clientState(client) = ENDTRAVELS) implies
						ag(clientState(client) = ENDTRAVELS))

	//proprieta' di liveness: se il cliente sta aspettando il taxi,
	//questo prima o poi arrivera' e il cliente potra' viaggiare
	//liveness property: if the client is waiting the taxi, the taxi sooner or later
	//will arrive and the client will be able to travel
	CTLSPEC ag((clientState(client) = WAITING) implies
					af(clientState(client) = TRAVELLING))
	
	//proprieta' di safety: il cliente e' in viaggio solo se il taxi e' nello stato WITHCLIENT
	//safety property: the client is travelling iff the taxi is in state WITHCLIENT
	CTLSPEC ag(clientState(client) = TRAVELLING iff taxiState(taxi)=WITHCLIENT)

	//proprieta' di safety: quando il taxi va dal cliente, questo lo sta aspettando
	//safety property: when the taxi i going to the client, the client is waiting for it
	CTLSPEC ag(clientState(client) = WAITING iff taxiState(taxi) = TOCLIENT)

	//proprieta' di reachability: esiste uno stato in cui il cliente
	//chiama il taxi che si trova nella sua stessa posizione
	//reachability property: it exists a state in which the client calls the taxi that 
	//is already in the same position
	CTLSPEC ef(clientState(client) = CALLTAXI and clientPosX(client) = taxiPosX(taxi)
					and clientPosY(client) = taxiPosY(taxi))

	//assenza di deadlock
	//deadlock absence (always true)
	CTLSPEC ag(ex(true))

	main rule r_Main =
		par
			program(client)
			program(taxi)
		endpar

default init s0:
	function clientNumViaggi($c in ClientAgent) = 2
	function clientState($c in ClientAgent) = IDLECL
	function taxiState($t in TaxiAgent) = IDLETX
	function clientPosX($c in ClientAgent) = 2
	function clientPosY($c in ClientAgent) = 2
	function taxiPosX($t in TaxiAgent) = 3
	function taxiPosY($t in TaxiAgent) = 3

	agent ClientAgent:
		r_ClientAgent[]

	agent TaxiAgent:
		r_TaxiAgent[]
