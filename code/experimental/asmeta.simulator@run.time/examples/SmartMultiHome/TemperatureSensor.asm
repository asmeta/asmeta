asm TemperatureSensor

import ../StandardLibrary

signature:
	dynamic monitored highTemperatureInput: Boolean
	dynamic out highTemperature: Boolean

definitions:

	main rule r_Main =
		highTemperature := highTemperatureInput

default init s0:
	function highTemperature = false
