asm ExistsTerm

import ../../STDL/StandardLibrary
		
signature:

	domain Floor subsetof Natural

	static attracted : Boolean 

		
definitions:	
      	
	function attracted = true and (exists $f in Floor with $f = 1n) 