// use of location vars
// add zero to a set

asm NumSets2

import ../../../STDL/StandardLibrary

		
signature:
		      
   domain NumSets subsetof Powerset(Integer)
         
   controlled setofnumbers: Powerset(NumSets)
   
definitions:

//Main Rule
  
    main rule r_test =
            
        choose $y in  setofnumbers with not contains($y,0) do $y := including($y,0)   
					// con f caratt,: $y(0) := true ad esempio {1,2,3}(0):= true
      											
  
  default init initial_state:

     function setofnumbers = {{},{3,4}}
