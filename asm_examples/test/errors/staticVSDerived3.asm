//
// error: in this case it is the domain that is dynamic
//
asm staticVSDerived3

import ../../STDL/StandardLibrary
	
	
signature:
	
	dynamic abstract domain Products
	
	static pendingOrder: Powerset(Products) -> Products
	

definitions:

	function pendingOrder($p in Powerset(Products)) =  chooseone($p) 


default init s_1:
