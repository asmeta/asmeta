// use of location vars in a let rule
// this is not allowed

asm LocationVarLet

import ../../../STDL/StandardLibrary

		
signature:
		
   controlled c : Integer
			

definitions:
	

//Main Rule
 
    main rule r_test = let ( $x = c ) in $x := 0
 
