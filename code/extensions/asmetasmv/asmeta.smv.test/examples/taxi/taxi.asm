asm taxi

import ../../../../../../asm_examples/STDL/StandardLibrary
import client
export *

signature:
	domain TaxiAgent subsetof Agent
	domain Taxi subsetof Integer	
	derived numtaxis: Taxi
	static neededTaxi: ClientAgent -> Taxi
	dynamic controlled keepTaxi: ClientAgent -> Taxi
	static taxi: TaxiAgent

definitions:
	domain Taxi = {0:3}

	function neededTaxi($c in ClientAgent) =
		if($c=cl1 or $c=cl2 or $c=cl3) then
			 1
		else
			if($c=gr2) then
				2
			else
				3
			endif
		endif

	function numtaxis = 3- keepTaxi(cl1) - keepTaxi(cl2) - keepTaxi(cl3) - keepTaxi(gr2) - keepTaxi(gr3)

	rule r_waiting = 
		choose $c in ClientAgent, $i in Durate
		with ((state($c) = WAITING) and numtaxis>=neededTaxi($c)) do
			par
				state($c) := TRAVELLING
				keepTaxi($c) := neededTaxi($c)				
				durata_viaggio($c) := $i//sceglie una durata del viaggio
			endpar

	rule r_freetaxi =
		forall $c in ClientAgent with durata_viaggio($c) = 1 do
			keepTaxi($c) := 0

	rule r_TaxiAgent =
		par
			r_waiting[]
			r_freetaxi[]
		endpar

default init s0:
