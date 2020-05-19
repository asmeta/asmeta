// use of logical var and macro:
// the parser coverts a logicalvariable in location var

asm logicalvar_macro

import ../../../STDL/StandardLibrary

		
signature:
		
   controlled set: Powerset(Integer)

   
// definitions 

definitions:
	
	macro rule r_take($p in Integer) = $p := 0 
	
//Main Rule

	main rule r_Spec = forall $x in set with true do 
		// se aggiungo seq $x := $x funziona perche' converte $x in location
		//seq $x := $x
                          r_take[$x]
		//endseq
 
default init initial_state:
	
	function set = {1,2,3}
		
