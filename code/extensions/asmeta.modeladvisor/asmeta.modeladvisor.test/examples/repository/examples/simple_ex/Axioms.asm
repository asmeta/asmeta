/** example of invariant violation **/

asm Axioms

import ../../STDL/StandardLibrary

signature:

	abstract domain Products
	
	domain QuantityDomain subsetof Integer
	domain ChooseQuantityDomain subsetof Integer

	static p1: Products
	static p2: Products
	static p3: Products

	controlled stockQuantity: Products -> QuantityDomain
//	monitored quantity: Products -> Integer

definitions:
	domain QuantityDomain = {0:100}
	domain ChooseQuantityDomain = {0 : 20}
	invariant inv_stockgt0 over stockQuantity: 
		(forall $p in Products with stockQuantity($p) >= 0)

	main rule r_main =
		choose $q in ChooseQuantityDomain, $p in Products with true do
			stockQuantity($p) := stockQuantity($p) - $q // quantity($p)

default init s0:
	function stockQuantity($p in Products) = 100
