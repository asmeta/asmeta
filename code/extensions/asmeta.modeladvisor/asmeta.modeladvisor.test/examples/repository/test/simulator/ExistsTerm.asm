asm ExistsTerm

import ../../STDL/StandardLibrary
		
signature:

	domain Floor subsetof Natural

	static attracted : Boolean 

		
definitions:	
      	
	function attracted = true and (exist $f in Floor with $f = 1n) 