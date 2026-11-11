asm FireAlarm2

import ../StandardLibrary

signature:
	dynamic monitored smokeNotification2: Boolean
	dynamic out alarmOn2: Boolean

definitions:

	main rule r_Main =
		alarmOn2 := smokeNotification2

default init s0:
	function alarmOn2 = false
