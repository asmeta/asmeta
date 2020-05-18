asm Counter03
	
import   ../../STDL/StandardLibrary
	
signature:

controlled counter :  Natural
controlled counter2 :  Natural
static counter3: Natural

definitions:

function counter3 = 1n 

main rule r_counter3 = 

seq  

       par      
         counter := counter + 1n 
         counter2 := counter 
       endpar 
         counter := counter + 1n  
endseq

default init s1:   
	
      function counter = 0n
      function counter2 = 0n  

