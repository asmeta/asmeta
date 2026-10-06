asm SmokeSensor

import ../StandardLibrary

signature:
	dynamic monitored smokeInput: Boolean
	dynamic out smokeDetected: Boolean

definitions:

	main rule r_Main =
		smokeDetected := smokeInput

default init s0:
	function smokeDetected = false
