asm forallRule2

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	controlled x: Integer -> Integer

definitions:
	domain ConcrDom = {0 .. 5}

	main rule r_Main =
		forall $v in ConcrDom with x($v) < 4 and x($v) != 2 do
			x($v) := x($v) + 1
		
default init s0:
	function x($v in Integer) = $v