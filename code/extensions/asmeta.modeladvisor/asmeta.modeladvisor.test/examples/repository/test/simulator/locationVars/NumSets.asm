// use of location vars
// set a set to {1,2,3}

asm NumSets

import ../../../STDL/StandardLibrary

		
signature:
		      
   domain NumSets subsetof Powerset(Integer)
      
   controlled setofnumbers: Powerset(NumSets)
      
definitions:
	

//Main Rule
  
    main rule r_test =
            
    	  choose $x in setofnumbers with true do $x := {1,2,3}
											
  
  default init initial_state:

     function setofnumbers = {{},{3,4}}

