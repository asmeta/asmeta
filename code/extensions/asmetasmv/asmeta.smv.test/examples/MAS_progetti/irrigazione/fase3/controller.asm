asm controller

import ../../../../../../../../asm_examples/STDL/StandardLibrary
import zona

export *


signature:
	domain ControllerAgent subsetof Agent
	domain AcquaDisponibile subsetof Integer

	derived availablewater: AcquaDisponibile
	
	
	static maincontroller: ControllerAgent
	
definitions:

	domain AcquaDisponibile = {0:2}
	
	
	function availablewater = 2 - utilizzo(zone01) - utilizzo(zone02) - utilizzo(zone03) - utilizzo(zone04) - utilizzo(zone05)
		
		
	rule r_aprivalvola_singolo =
		//Se c'� un "posto" libero cerchiamo una zona che abbia bisogno di essere irrigata
		if availablewater = 1 then
			choose $c in ZoneAgent
			with ((state_sensor($c) = NEED) and (state_valvola($c) = CLOSED)) do
				state_valvola($c) := OPEN
		
		endif
		
		
	rule r_aprivalvola_doppio =
		//Se ci sono due "posti" liberi cerchiamo due zone differenti che abbiano bisogno di essere irrigata
		if availablewater = 2 then
			choose $c in ZoneAgent, $d in ZoneAgent
			with 	($c != $d) and
					((state_sensor($c) = NEED) and (state_valvola($c) = CLOSED)) and
					((state_sensor($d) = NEED) and (state_valvola($d) = CLOSED)) do
						par
							state_valvola($c) := OPEN
							state_valvola($d) := OPEN
						endpar
					ifnone
						//Se non ci sono due zone differenticerchiamo se c'� una singola zona che abbia bisogno d'acqua
						choose $e in ZoneAgent
						with ((state_sensor($e) = NEED) and (state_valvola($e) = CLOSED)) do
							state_valvola($e) := OPEN
		endif
	
	rule r_ControllerAgent =
		par
			r_aprivalvola_singolo[]
			r_aprivalvola_doppio[]
		endpar
	
default init s0:
