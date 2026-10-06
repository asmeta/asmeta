asm FireAlarm

import ../StandardLibrary

signature:
	dynamic monitored smokeNotification: Boolean
	dynamic out alarmOn: Boolean

definitions:

	main rule r_Main =
		alarmOn := smokeNotification

default init s0:
	function alarmOn = false
