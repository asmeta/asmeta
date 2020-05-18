// when a function is defined over a domain
// and a value in that domain is update by a location value
// the function's location must be copied to preserve the value
// this variant must give an UpdateClash or sometinh similar

asm Functions2

import ../../../STDL/StandardLibrary

signature:

// dynamic functions
	
	controlled set: Powerset(Integer)
	
	controlled f: Integer -> Integer


// definitions 

definitions:
	
   main rule r_test =
   		     
   		par
   		// set f(1) to 5
   		f(0) := 5   		   		
       	choose $x in set with true do
       		par
       		$x := 1 // set($x) := false, set(1):= true
       		f($x) := 3
       		endpar
       		
       	// useful to get the set evaluated
       	set := set
       	endpar

default init s1:
	function set = {0}
  