asm incompleteCondTerm

import ../../STDL/StandardLibrary

signature:

	enum domain States = {AA | BB | CC }

	dynamic controlled check: States -> Integer

	controlled result1 : Integer	
	controlled result2 : Integer	

definitions:

	main rule r_main = 
	par
		result1 := check(AA)
		result2 := check(CC)
	endpar


default init s0:
//controlled functions

// it is ok also if inccomplete	
    function check($t in States) = if $t = AA	then 1 
    	else if $t = BB then 1 
//   	else if $t = CC then 2 // undef if not present
//	endif
	endif
	endif
