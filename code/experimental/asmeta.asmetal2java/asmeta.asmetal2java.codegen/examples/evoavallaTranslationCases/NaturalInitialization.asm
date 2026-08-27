asm NaturalInitialization

import ../STDL/StandardLibrary

signature:
	controlled counter: Natural
	controlled previousCounter: Natural
	controlled counterByFlag: Boolean -> Natural
	controlled positive: Boolean
	monitored step: Natural

definitions:
	main rule r_Main =
		par
			counter := counter + step
			previousCounter :=
				if counter > 0n then
					counter - 1n
				else
					0n
				endif
			counterByFlag(true) := counterByFlag(false) + step
			positive := counter > 0n
		endpar

default init s0:
	function counter = 1n
	function previousCounter = 0n
	function counterByFlag($flag in Boolean) =
		if $flag then
			1n
		else
			0n
		endif
	function positive = true
