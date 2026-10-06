asm CentralController

import ../StandardLibrary

signature:
	dynamic monitored smokeNotification1: Boolean
	dynamic monitored smokeNotification2: Boolean
	dynamic out globalAlarm: Boolean

definitions:

	main rule r_Main =
		globalAlarm := smokeNotification1 or smokeNotification2

default init s0:
	function globalAlarm = false
