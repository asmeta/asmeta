module importedAsm

import StandardLibrary

export *

signature:
	// DOMAINS
	enum domain Stato = {ON | OFF}
	// FUNCTIONS
	controlled stato: Stato

definitions:
	rule r_RuleChiamata ($b in Boolean) =
		if ($b)
		then stato := ON
		else stato := OFF
		endif

	rule r_importedRule ($b in Boolean) =
		skip
