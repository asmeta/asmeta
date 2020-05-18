asm multipleStateChangeV_2

import StandardLibrary

signature:
	controlled state: Integer
	monitored mon: Boolean

definitions:
	rule r_1 =
		state := 1

	rule r_2 =
		state := 2

	rule r_3 =
		state := 3

	main rule r_Main =
		par
			if state = 0 then
				r_1[]
			endif
			if state = 1 then
				if mon then
					r_2[]
				else
					r_3[]
				endif
			endif
			if state = 3 then
				r_1[]
			endif
		endpar

default init s0:
	function state = 0