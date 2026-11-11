asm TemperatureSensor2

import ../StandardLibrary

signature:
	dynamic monitored highTemperatureInput2: Boolean
	dynamic out highTemperature2: Boolean

definitions:

	main rule r_Main =
		highTemperature2 := highTemperatureInput2

default init s0:
	function highTemperature2 = false
