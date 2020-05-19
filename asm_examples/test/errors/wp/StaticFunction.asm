asm StaticFunction

import ../../../STDL/StandardLibrary
		
signature:

	monitored a: Boolean
// static 
	static b : Boolean 

		
definitions:	
      	
// it shoould be wrong (b should be dynamic)
	function b = a