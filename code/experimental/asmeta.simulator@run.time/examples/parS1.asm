asm parS1

import StandardLibrary

signature:
	dynamic out x: Integer 
	dynamic monitored res: Integer
definitions:

	main rule r_Main =
		x := res + 1

default init s0:
	function x = 1