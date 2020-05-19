asm turboReturnRule

import ../../STDL/StandardLibrary

signature:
	dynamic controlled value: Integer
	dynamic monitored inputA: Integer
	dynamic monitored inputB: Integer
	
definitions:

	turbo rule r_sum($x in Integer, $y in Integer) in Integer =
		result := $x + $y
			
	main rule r_Main =
		value <- r_sum(inputA, inputB)

default init s0:
	function value = 0