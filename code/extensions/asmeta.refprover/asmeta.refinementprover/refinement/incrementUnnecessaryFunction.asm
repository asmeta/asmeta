asm incrementUnnecessaryFunction

import StandardLibrary

//function "y" is unnecessary and removed in the refined model "increment.asm"
signature:
	controlled x: Integer
	controlled y: Integer

definitions:

	main rule r_Main =
		par
			x := x + 1
			y := x
		endpar

default init s0:
	function x = y
	function y = 0