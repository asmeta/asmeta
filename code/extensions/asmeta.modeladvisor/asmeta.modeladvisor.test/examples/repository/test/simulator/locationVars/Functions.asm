// when a function is defined over a domain
// and a value in that domain is update by a location value
// the function's location must be copied to preserve the value

asm Functions

import ../../../STDL/StandardLibrary

signature:

// dynamic functions
	
	controlled set: Powerset(Integer)
	
	controlled f: Integer -> Integer


// definitions 

definitions:
	
   main rule r_test =
   		     
   		seq
   		// set f(1) to 5
   		f(1) := 5   		   		
       	choose $x in set with true do
       		par
       		$x := 1 // set($x) := false, set(1):= true
       		f($x) := 3
       		endpar
       		
       	// useful to get the set evaluated
       	set := set
       	endseq

default init s1:
	function set = {0}
  