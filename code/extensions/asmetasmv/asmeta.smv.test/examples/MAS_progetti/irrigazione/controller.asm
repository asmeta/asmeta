asm controller

import ../../../../../../../asm_examples/STDL/StandardLibrary
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
	
				
	rule r_aprivalvola_doppio =
		if (availablewater = 2) then
			choose $c in ZoneAgent
			with ((state_sensor($c) = NEED) and (state_valvola($c) = CLOSED) and
				(forall $o in ZoneAgent with (($o != $c) and (state_valvola($o) = CLOSED) and (state_sensor($o) = NEED)) implies (state_priority($c) >= state_priority($o)))) do
			par
				state_valvola($c) := OPEN
				
				choose $d in ZoneAgent
				with ((state_sensor($d) = NEED) and (state_valvola($d) = CLOSED) and ($d != $c) and
					(forall $n in ZoneAgent with (($n != $d) and (state_valvola($n) = CLOSED) and (state_sensor($n) = NEED) and ($n != $c)) implies (state_priority($c) >= state_priority($n)))) do
					
					state_valvola($d) := OPEN

			endpar
		endif
			
		
	rule r_aprivalvola_singolo =
		if (availablewater = 1) then
			choose $c in ZoneAgent
			with ((state_sensor($c) = NEED) and (state_valvola($c) = CLOSED) and
				(forall $o in ZoneAgent with (($o != $c) and (state_valvola($o) = CLOSED)) implies (state_priority($c) >= state_priority($o)))) do
				state_valvola($c) := OPEN
		endif
		
	
	rule r_ControllerAgent =
		par
			r_aprivalvola_singolo[]
			r_aprivalvola_doppio[]
		endpar
	
default init s0:
