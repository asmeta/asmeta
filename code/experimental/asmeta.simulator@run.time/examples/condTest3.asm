asm condTest3

import StandardLibrary

signature:
	dynamic out y: Integer
	dynamic monitored x: Integer
	controlled cond: Boolean

definitions:
	
	main rule r_Main =
		par
			if (cond = true and x = 0) then
				y := 2
			endif
			if (cond = true and x = 2) then
				y := 4
			endif
			if (cond = true and x = 4) then
				y := 6
			endif
			if (cond = true and x = 6) then
				par
					y := 0
					cond := false
				endpar
			endif
		endpar

default init s0:
	function cond = true
	function y = 2