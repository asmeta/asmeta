asm CaseTermPair

import ../../STDL/StandardLibrary

signature:
	dynamic controlled pos: Integer
	static dir: Prod(Integer, Integer) -> Integer

definitions:
	
	function dir($a in Integer, $b in Integer) =
		switch($a, $b)
			case (1, 1): 11
			case (1, 2): 12
			case (2, 1): 21
			case (2, 2): 22
		endswitch


	main rule r_Main =
		pos := dir(1, 2)


default init s0:
	