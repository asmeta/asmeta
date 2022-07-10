asm sluiceGateGround

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain Minutes subsetof Integer
	enum domain PhaseDomain = { FULLYCLOSED | FULLYOPENED }
	dynamic controlled phase: PhaseDomain
	dynamic monitored passed: Minutes -> Boolean

definitions:
	domain Minutes = {10, 170}
	
	rule r_open =
		skip
	
	rule r_shut =
		skip		
	
	invariant over phase: ag(phase=FULLYCLOSED and passed(170) implies ax(phase = FULLYOPENED))
	invariant over phase: ag(phase=FULLYOPENED and passed(10) implies ax(phase = FULLYCLOSED))

	main rule r_Main =
		par
			if(phase=FULLYCLOSED) then
				if(passed(170)) then
					par
						r_open[]
						phase := FULLYOPENED
					endpar
				endif
			endif
			if(phase=FULLYOPENED) then
				if(passed(10)) then
					par
						r_shut[]
						phase := FULLYCLOSED
					endpar
				endif
			endif
		endpar

default init s0:
	function phase = FULLYCLOSED
