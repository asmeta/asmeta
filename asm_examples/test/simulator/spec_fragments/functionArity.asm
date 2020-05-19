// test the function arity
// two functions, whicha re the same with differrent arity
// x is user defined and has arity 1, is defined = to first which has arity 2
// if aritry =1, the argument will be tuple, otherwise it will be expanded 

asm functionArity

import ../../../STDL/StandardLibrary

		
signature:
		
   domain Point subsetof Prod(Real,Real)

// static functions 

   static x : Point -> Real
      
// definitions 

definitions:
	
   function x ($p in Point) = first($p)


//Main Rule
 
    main rule r_test =
        
    	  let ($temp = x((1.0,2.0))) in skip
    	  endlet 
 
