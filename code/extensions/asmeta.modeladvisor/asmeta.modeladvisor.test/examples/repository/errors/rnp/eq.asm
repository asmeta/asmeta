// eq con undef dovrebbe funzionare
asm eq

import ../../STDL/StandardLibrary

		
signature:
		
   controlled x: Integer

   
// definitions 

definitions:
	
//Main Rule

	main rule r_Spec = if x = undef then skip endif
 
//define the initial states 

/* default init initial_state:
	
	function prova  = true
*/
		
