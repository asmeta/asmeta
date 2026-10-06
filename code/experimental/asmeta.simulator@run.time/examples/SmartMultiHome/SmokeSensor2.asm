asm SmokeSensor2

import ../StandardLibrary

signature:
	dynamic monitored smokeInput2: Boolean
	dynamic out smokeDetected2: Boolean

definitions:

	main rule r_Main =
		smokeDetected2 := smokeInput2

default init s0:
	function smokeDetected2 = false
