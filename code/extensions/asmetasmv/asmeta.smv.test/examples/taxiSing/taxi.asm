asm taxi

import ../../../../../../asm_examples/STDL/StandardLibrary
import client
import util

export *

signature:
	domain TaxiAgent subsetof Agent
	enum domain TaxiState = {IDLETX | TOCLIENT | WITHCLIENT}
	dynamic controlled taxiState: TaxiAgent -> TaxiState
	dynamic controlled taxiPosX: TaxiAgent -> Coord
	dynamic controlled taxiPosY: TaxiAgent -> Coord

	static taxi: TaxiAgent

definitions:

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

default init s0:
