asm taxi_sing
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Coord subsetof Integer
	domain NumTravels subsetof Integer
	domain Step subsetof Integer
	enum domain TaxiState = {IDLETX | TOCLIENT | WITHCLIENT}
	enum domain ClientState = {WAITING | TRAVELLING | IDLECL | CALLTAXI | ENDTRAVELS}
	dynamic controlled taxiState: TaxiState
	dynamic controlled taxiPosX: Coord
	dynamic controlled taxiPosY: Coord
	dynamic controlled clientState: ClientState
	dynamic controlled clientPosX: Coord
	dynamic controlled clientPosY: Coord
	dynamic controlled clientDestX: Coord
	dynamic controlled clientDestY: Coord
	dynamic controlled clientNumTravels: NumTravels
	dynamic monitored stepX: Step
	dynamic monitored stepY: Step
	dynamic monitored decideToTravel: Boolean
	dynamic monitored destX: Coord
	dynamic monitored destY: Coord

definitions:
	domain Coord = {1..8}
	domain NumTravels = {0..2}
	domain Step ={-1..1}

	//updates coordinate $i of a taxi or of a client
	//that is travelling towards $j	
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
			if(clientPosX > 1 and clientPosX < 8) then
				clientPosX := clientPosX + stepX		
			endif
			if(clientPosY > 1 and clientPosY < 8) then
				clientPosY := clientPosY + stepY		
			endif
		endpar
	
	rule r_idle =
		if(clientState=IDLECL) then
			if(clientNumTravels>0) then
				if(decideToTravel) then
					par
						clientState := CALLTAXI
						clientDestX := destX 
						clientDestY := destY
					endpar
				else
					r_idleMove[]//if he doesn't call the taxi, he can walk around
				endif
			else
				clientState := ENDTRAVELS
			endif
		endif

	rule r_receiveCall = 
		if(clientState = CALLTAXI) then
			par
				taxiState := TOCLIENT //taxi goes to the client
				clientState := WAITING //client waits the taxi 
			endpar
		endif

	rule r_toClient = 
		if(taxiState=TOCLIENT) then
			if(taxiPosX = clientPosX and taxiPosY = clientPosY) then
				par		
					taxiState := WITHCLIENT //taxi picks up the client
					clientState := TRAVELLING //client is travelling with taxi
				endpar
			else
				par
					r_updateCoord[taxiPosX, clientPosX]
					r_updateCoord[taxiPosY, clientPosY]
				endpar
			endif
		endif
	
	rule r_withClient =
		if(taxiState=WITHCLIENT) then
			if(taxiPosX = clientDestX and taxiPosY = clientDestY) then
				par		
					taxiState := IDLETX //taxi has no clients on board
					clientState := IDLECL //client is no more on taxi
					clientNumTravels := clientNumTravels - 1
				endpar
			else
				par
					r_updateCoord[taxiPosX, clientDestX]
					r_updateCoord[taxiPosY, clientDestY]
					r_updateCoord[clientPosX, clientDestX]
					r_updateCoord[clientPosY, clientDestY]
				endpar
			endif
		endif
	
	//safety property: it the taxi is travelling with the
	//client their coordinates must be equal
	CTLSPEC ag(taxiState = WITHCLIENT implies (taxiPosX = clientPosX and taxiPosY = clientPosY))	
	
	//when the client has done his travels, he does not travel anymore
	CTLSPEC ag((clientState = ENDTRAVELS) implies ag(clientState = ENDTRAVELS))

	//liveness property: if the client is waiting the taxi, the taxi sooner or
	//later will arrive and the client will be able to travel
	CTLSPEC ag((clientState = WAITING) implies af(clientState = TRAVELLING))
	
	//safety property: the client is travelling only if
	//the taxi is in WITHCLIENT
	CTLSPEC ag(clientState = TRAVELLING iff taxiState = WITHCLIENT)
	
	//safety property: when the taxi is going to the client,
	//the client is waiting for it
	CTLSPEC ag(clientState = WAITING iff taxiState = TOCLIENT)

	//reachability property: it exists a state in which
	//the client calls a taxi that is in its position
	CTLSPEC ef(clientState = CALLTAXI and clientPosX = taxiPosX and clientPosY = taxiPosY)

	//deadlock absence
	CTLSPEC ag(ex(true))

	main rule r_Main =
		par
			r_idle[]
			r_receiveCall[]
			r_toClient[]
			r_withClient[]
		endpar

default init s0:
	function clientNumTravels = 2
	function clientState = IDLECL
	function taxiState = IDLETX
	function clientPosX = 2
	function clientPosY = 2
	function taxiPosX = 3
	function taxiPosY = 3