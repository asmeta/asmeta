module RandomModule

import StandardLibrary
export *

signature:
	controlled random: Boolean

definitions:

	rule r_random =
		choose $b in Boolean with true do random := $b
		
