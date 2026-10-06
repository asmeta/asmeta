asm FireAlarm

import ../StandardLibrary

signature:
	dynamic monitored alarmCommand: Boolean
	dynamic out alarmOn: Boolean

definitions:

	main rule r_Main =
		alarmOn := alarmCommand

default init s0:
	function alarmOn = false
