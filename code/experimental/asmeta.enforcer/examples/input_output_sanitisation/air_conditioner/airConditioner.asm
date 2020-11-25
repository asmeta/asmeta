asm airConditioner

import StandardLibrary

signature:
	domain AirSpeedDomain subsetof Integer
	dynamic out airSpeed: AirSpeedDomain
	dynamic monitored temperature: Integer

definitions:
	domain AirSpeedDomain = {0:2}

	invariant inv_a over temperature: temperature > 0  //negative values must be filtered out
	invariant inv_b over airSpeed: airSpeed < 2 //to avoid over speed
	
	main rule r_Main =
		if(temperature >= 25) then
			airSpeed := 2
		else
			if(temperature < 20) then
				airSpeed := 0
			else
				airSpeed := 1
			endif
		endif

default init s0:
	function airSpeed = 0