asm simpleASMmape1

import ../StandardLibrary

signature:
	dynamic controlled x: Integer
	dynamic controlled y: Integer

definitions:

	rule r_mape1 =
		if x < 10 then
				x := x + 1
			else
				y := 0
			endif

	rule r_mape2 =
		if y < 20 then
				y := y + 1
			endif

	main rule r_Main =
		r_mape1[]

default init s0:
	function x = 0
	function y = 0