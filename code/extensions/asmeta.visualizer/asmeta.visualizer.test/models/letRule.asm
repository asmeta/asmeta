asm letRule

import StandardLibrary

signature:
	monitored x: Integer
	monitored y: Integer
	controlled z: Integer

definitions:

	main rule r_Main =
		let($k = x + y) in
			z := $k
		endlet