asm owtl_pulses

import ../../../../../../asm_examples/STDL/StandardLibrary
import owtl_ctlexport r_pulses, pulses, PULSESAgent

signature:
	domain PULSESAgent subsetof Agent
	
	static pulses: PULSESAgent
	
	
definitions:

	macro rule r_switch($l in Boolean) =
		$l := not($l)
	
	rule r_pulses =
		forall $l in LightUnit with true do
			par
				if(gPulse($l)) then
					par
						r_switch[goLight($l)]
						gPulse($l) := false
					endpar
				endif
				if(rPulse($l)) then
					par
						r_switch[stopLight($l)]
						rPulse($l) := false
					endpar
				endif
			endpar
