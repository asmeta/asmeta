asm LocalController2

import ../StandardLibrary

signature:
	dynamic monitored smokeDetected2: Boolean
	dynamic monitored highTemperature2: Boolean
	dynamic monitored centralAlarm: Boolean

	dynamic out alarmCommand2: Boolean
	dynamic out openCommand2: Boolean
	dynamic out smokeNotification2: Boolean

definitions:

	main rule r_Main =
		par
			alarmCommand2 := smokeDetected2 or centralAlarm
			openCommand2 := highTemperature2
			smokeNotification2 := smokeDetected2
		endpar

default init s0:
	function alarmCommand2 = false
	function openCommand2 = false
	function smokeNotification2 = false
