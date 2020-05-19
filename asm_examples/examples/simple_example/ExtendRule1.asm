asm ExtendRule1

import ../../STDL/StandardLibrary

signature:

	dynamic abstract domain Products

	static p1: Products
	static p2: Products
	static p3: Products

definitions:

	main rule r_main =
		extend Products with $p do skip
