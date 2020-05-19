asm ProdConcreteDomain

/** the product of domanins if domainis are concrete is rejected by the parser */

import ../../STDL/StandardLibrary
		
signature:

	abstract domain Order

	domain Quantity subsetof Natural

	controlled stock :  Prod(Order,Quantity) -> Natural

		
definitions:	
      	
