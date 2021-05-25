asm incompleteCondTerm

import ../../STDL/StandardLibrary

signature:

	enum domain States = {AA | BB | CC }

	dynamic controlled check: States -> Boolean
	

definitions:

default init s0:
//controlled functions

// va bene?? forse no o forse sì??? AG 21/05/2021	
    function check($t in States) = if $t = AA	then true 
    	else if $t = BB then true 
//   	else if $t = CC then false // wrong this should not be here
//	endif
	endif
	endif
