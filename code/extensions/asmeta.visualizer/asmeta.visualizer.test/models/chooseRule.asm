asm chooseRule

import StandardLibrary

signature:
	controlled x: Integer -> Integer

definitions:

	main rule r_Main =
		choose $v in {0 .. 5} with x($v) < 4 do
			x($v) := x($v) + 1
		
default init s0:
	function x($v in Integer) = $v