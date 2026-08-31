asm Natural

import ../STDL/StandardLibrary

signature:
	controlled counter: Natural
	controlled counter2: Natural
	controlled counter3: Boolean -> Natural
	controlled positive: Boolean
	controlled converted: Natural
	monitored integerValue: Integer
	monitored step: Natural
	monitored productNatural: Prod(Natural, Boolean) -> Natural

definitions:
	main rule r_Main =
		par
			converted := iton(integerValue)
			counter := counter + step
			if counter != 0n then
				counter2 := counter * 2n
			else
				counter2 := 0n
			endif
			counter3(true) := counter3(true) + step
			positive := counter > 0n
		endpar

default init s0:
	function counter = 1n
	function counter2 = 2n
	function counter3($flag in Boolean) =
		if $flag then
			1n
		else
			0n
		endif
	function positive = true
	function converted = 1n
