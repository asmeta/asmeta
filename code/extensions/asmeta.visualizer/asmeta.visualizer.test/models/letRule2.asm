asm letRule2

import StandardLibrary

signature:
	monitored x: Integer
	monitored y: Integer
	controlled z: Integer

definitions:

	main rule r_Main =
		let($k = x + y, $h = x * y, $i = 2*x) in
			z := $k + $h + $i
		endlet