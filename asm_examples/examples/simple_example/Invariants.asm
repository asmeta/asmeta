/** example of invariant violation **/

asm Invariants

import ../../STDL/StandardLibrary

signature:

	abstract domain Products

	static p1: Products
	static p2: Products
	static p3: Products

	controlled stockQuantity: Products -> Integer
//	monitored quantity: Products -> Integer

definitions:

	invariant inv_stockgt0 over stockQuantity: 
		(forall $p in Products with stockQuantity($p) >= 0)

	main rule r_main =
		choose $q in {0 : 20 }, $p in Products with true do
		
			stockQuantity($p) := stockQuantity($p) - $q // quantity($p)

default init s0:

	function stockQuantity($p in Products) = 100
