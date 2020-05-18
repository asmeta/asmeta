asm macroCallRule

import StandardLibrary

signature:
	controlled y: Integer -> Integer

definitions:
	rule r_otherRule =
		choose $v in {0 .. 5} with y($v) < 4 do
			y($v) := y($v) + 1
			ifnone
				forall $v2 in {0 .. 5} with y($v2) < 4 do
					y($v2) := 0

	main rule r_Main =
		r_otherRule[]
		
default init s0:
	function y($v in Integer) = $v