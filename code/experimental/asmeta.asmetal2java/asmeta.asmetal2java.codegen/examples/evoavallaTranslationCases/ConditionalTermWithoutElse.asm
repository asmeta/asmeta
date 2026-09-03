asm ConditionalTermWithoutElse

import ../STDL/StandardLibrary

signature:
	controlled enabled: Boolean
	controlled value: Integer

definitions:
	main rule r_Main =
		value := if enabled then 1 endif

default init s0:
	function enabled = true
	function value = if enabled then 1 endif
