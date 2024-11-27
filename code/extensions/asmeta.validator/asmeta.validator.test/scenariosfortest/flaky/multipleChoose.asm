asm multipleChoose

import StandardLibrary


signature:
	// DOMAINS
	domain Level subsetof Integer
	// FUNCTIONS

definitions:
	// DOMAIN DEFINITIONS
	domain Level = {0:100}
	
	rule r_NotCalledRule =
		choose $b in Boolean with true do if ($b) then skip else skip endif
		
	rule r_CalledRule($i in Integer) =
		choose $d in Level with ($d < $i) do skip ifnone skip

	// MAIN RULE
	main rule r_Main =
		par
			choose $i in Integer with true do skip
			r_NotCalledRule[]
			r_CalledRule[10]
		endpar

// INITIAL STATE
default init s0:
