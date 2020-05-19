asm ntoi
	
import ../../STDL/StandardLibrary
	
signature:

controlled counter :  Natural
controlled counter2 : Natural		
controlled counter3 : Integer

definitions:
	                 	
main rule r_counter2 = par 

      counter := counter + 1n //ok
      counter2 := counter2 + ( 1n + 1n) //ok
      //counter3 := counter + 1  Error, plus(Prod(Natural,Integer)) doesn't exist 
      counter3 := ntoi(counter) + 1 // ok  
endpar


default init s1:   
	
	function counter = 0n
	function counter2 = 0n
	function counter3 = 0