asm parkingSimple

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	domain AvailableSpaces subsetof Integer
	dynamic controlled free : AvailableSpaces
	dynamic monitored carExiting: Boolean//external nondeterminism
	dynamic monitored carEntering: Boolean//external nondeterminism
definitions:
	domain AvailableSpaces = {0 : 10}

	main rule r_Main =
		if carExiting and free > 0 then
			free := free - 1
		else if carEntering and free < 10 then
				free := free + 1
			endif
		endif

default init s0:
	function free = 0
