asm parS2

import StandardLibrary

signature:
	dynamic out y: Integer 
	dynamic monitored res: Integer
definitions:

	main rule r_Main =
		y := res + 2

default init s0:
	function y = 2