/** example of invariant violation
Given a set of products with a stock availability, 
decrease the available quantity randomly between 0 and 20.
Initially the availability of all the products is 100.
 **/

asm Invariants

import ../../STDL/StandardLibrary

signature:

	abstract domain Products

	static p1: Products
	static p2: Products
	static p3: Products

	controlled stockQuantity: Products -> Integer

definitions:

	invariant inv_stockgt0 over stockQuantity: 
		(forall $p in Products with stockQuantity($p) >= 0)

	main rule r_main =
		//select a quantity between 0 and 20 and a Product, and decrease its quantity in stock
		choose $q in {0 : 20 }, $p in Products with true do
			stockQuantity($p) := stockQuantity($p) - $q 

default init s0:

	function stockQuantity($p in Products) = 100 // initialize quantity of each product to 100
