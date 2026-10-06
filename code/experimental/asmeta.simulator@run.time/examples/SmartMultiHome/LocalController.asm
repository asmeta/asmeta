asm LocalController

import ../StandardLibrary

signature:
	dynamic monitored smokeDetected: Boolean
	dynamic monitored highTemperature: Boolean
	dynamic monitored centralAlarm: Boolean

	dynamic out alarmCommand: Boolean
	dynamic out openCommand: Boolean
	dynamic out smokeNotification: Boolean

definitions:

	main rule r_Main =
		par
			alarmCommand := smokeDetected or centralAlarm
			openCommand := highTemperature
			smokeNotification := smokeDetected
		endpar

default init s0:
	function alarmCommand = false
	function openCommand = false
	function smokeNotification = false
