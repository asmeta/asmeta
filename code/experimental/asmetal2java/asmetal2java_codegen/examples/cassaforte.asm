// a simple example with a tic tac toe game

asm cassaforte

import STDL/StandardLibrary


signature:
	// DOMAINS
	domain Sensore subsetof Integer
	monitored switchSensore: Sensore
	controlled statoSensore: Sensore -> Boolean
	
	derived adiacenti: Sensore -> Boolean
	derived pericolo: Boolean
	

definitions:
	// DOMAIN DEFINITIONS
	domain Sensore = {0 : 3}

	function adiacenti($s in Sensore) = 
		if($s = 0) then statoSensore(3) and statoSensore(1)
		else
			if($s = 3) then statoSensore(0) and statoSensore(2)
			else statoSensore($s + 1 ) and statoSensore($s - 1)
		endif
		endif
	
	function pericolo =
		(forall $s in Sensore with not adiacenti($s) and not statoSensore($s))



	// MAIN RULE
	main rule r_Main =
		
		if(statoSensore(switchSensore) = false) then
			statoSensore(switchSensore) := not statoSensore(switchSensore)
		else
			if(adiacenti(switchSensore)) then
				statoSensore(switchSensore) := not statoSensore(switchSensore)
			endif
		endif


// INITIAL STATE
default init s0:
	function statoSensore($s in Sensore) = true
