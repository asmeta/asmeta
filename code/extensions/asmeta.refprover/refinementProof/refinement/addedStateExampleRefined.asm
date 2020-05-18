asm addedStateExampleRefined

import StandardLibrary

signature:
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPEN }
	enum domain PhaseDomainRef = { FULLYCLOSED_REF | OPENING | FULLYOPEN_REF}
	dynamic controlled phaseRefined: PhaseDomainRef
	derived phase: PhaseDomain
	monitored timePassed: Boolean
	
definitions:
	function phase =
		if phaseRefined = FULLYCLOSED_REF or phaseRefined = OPENING then
			FULLYCLOSED
		else
			FULLYOPEN
		endif

	main rule r_Main =
		par
			if phaseRefined = FULLYCLOSED_REF then
				phaseRefined := OPENING
			endif
			if phaseRefined = OPENING then
				if timePassed then
					phaseRefined := FULLYOPEN_REF
				endif
			endif
		endpar

default init s0:
	function phaseRefined = FULLYCLOSED_REF