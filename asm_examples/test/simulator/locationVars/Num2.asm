// use of location vars
// take one element in a set and set to 20

asm Num2

import ../../../STDL/StandardLibrary

		
signature:
		      
   controlled numbers : Powerset(Integer)
      
   
definitions:
	

//Main Rule
  
    main rule r_test =
       // take one element and set to 20     
       choose $x in numbers with true do 
       		par 
       			$x := 20 // numbers($x) := false numbers(20):= true
       			$x := 30
       		endpar
       		
  default init initial_state:

     function numbers = {0,5,10}	 