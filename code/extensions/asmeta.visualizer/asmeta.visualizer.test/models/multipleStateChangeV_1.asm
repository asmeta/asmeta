asm multipleStateChangeV_1

import StandardLibrary

signature:
	controlled state: Integer
	monitored mon: Boolean

definitions:

	main rule r_Main =
		par
			if state = 0 then
				state := 1
			endif
			if state = 1 then
				if mon then
					state := 2
				else
					state := 3
				endif
			endif
			if state = 3 then
				state := 1
			endif
		endpar

default init s0:
	function state = 0