// use of location vars and seq

asm LocVarSeq

import ../../../STDL/StandardLibrary

		
signature:
		      
   controlled numbers : Powerset(Integer)
      
   
definitions:
	

//Main Rule
  
    main rule r_test =
       // take one element      
       choose $x in numbers with true do
       seq 
       $x := 5 
       $x := 6
       endseq
       
  default init initial_state:

     function numbers = {0}	 