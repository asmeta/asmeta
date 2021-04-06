asm client

import ../../../../../../asm_examples/STDL/StandardLibrary
import util
export client, ClientAgent, r_ClientAgent, clientState, ClientState, clientPosX, clientPosY, clientNumViaggi, clientDestX, clientDestY
//export *

signature:
	domain ClientAgent subsetof Agent
	domain NumViaggi subsetof Integer
	enum domain ClientState = {WAITING | TRAVELLING | IDLECL | CALLTAXI | ENDTRAVELS}
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
	domain NumViaggi = {0:2}

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

default init s0:
