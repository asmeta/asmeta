asm addedStateExampleAbstract

import StandardLibrary

signature:
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	dynamic controlled phase: PhaseDomain
	monitored timePassed: Boolean
	
definitions:
	
	main rule r_Main =
		if phase = FULLYCLOSED then
			if timePassed then
				phase := FULLYOPEN
			endif
		endif

default init s0:
	function phase = FULLYCLOSED