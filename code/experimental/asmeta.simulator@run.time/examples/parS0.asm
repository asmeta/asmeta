asm parS0

import StandardLibrary

signature:
	dynamic out res: Integer
	dynamic monitored x: Integer
	dynamic monitored y: Integer

definitions:
	
	main rule r_Main =
		res := x + y
	
default init s0:
	function res = 0