asm airConditioner

import StandardLibrary

signature:
	domain AirSpeedDomain subsetof Integer
	dynamic out airSpeed: AirSpeedDomain 
	dynamic monitored airIntensity: AirSpeedDomain //output from the real system
	dynamic monitored temperature: Integer //same input for the real system

definitions:
	domain AirSpeedDomain = {0:2}

	invariant inv_a over temperature: temperature > 0  //negative values must be filtered out
	//invariant inv_c over airIntensity: airIntensity < 2 //output sanitisation: to avoid over speed
	
	 
	
	main rule r_Main =
		if(temperature >= 25) then
			//airSpeed := 2 
			airSpeed := 1 //output sanitisation via adaptation: to avoid over speed 
		else
			if(temperature < 20) then
				airSpeed := 0
			else
				airSpeed := 1
			endif
		endif

default init s0:
	function airSpeed = 0