asm STDLFunctions

import ../STDL/StandardLibrary

signature:
	controlled integerCalculation: Integer
	controlled realCalculation: Real
	controlled text: String

definitions:
	main rule r_Main =
		par
			integerCalculation := idiv(7, 2) + abs(-3) + min(4, 9) + max(4, 9) + floor(2.7) + round(2.7)
			realCalculation := sqrt(9.0) + pwr(2.0, 3.0) + (7 / 2)
			text := concat("power", "set")
		endpar

default init s0:
	function integerCalculation = 0
	function realCalculation = 0.0
	function text = ""
