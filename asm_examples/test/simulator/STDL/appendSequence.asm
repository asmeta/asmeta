asm appendSequence

import ../../../STDL/StandardLibrary
	
signature:
	controlled sequence: Seq(Integer)
	controlled intValue: Integer

definitions:

	main rule r_main =
		par
			sequence := append(sequence, intValue)
			if intValue = undef then
				intValue := 0
			else 
				intValue := intValue + 1
			endif
		endpar

default init s0:
	function sequence = []