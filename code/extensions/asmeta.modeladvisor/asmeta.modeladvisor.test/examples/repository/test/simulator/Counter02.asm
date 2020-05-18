asm Counter02
	
import ../../STDL/StandardLibrary
	
signature:

controlled counter1 : Integer
controlled counter2 : Integer		
controlled counter3 : Integer

definitions:
	                 	
main rule r_main = 
    par
        counter1 := counter1 - 1 
        counter2 := counter2 * counter1
    endpar

default init s1:   
    function counter1 = 5
    function counter2 = 1
    function counter3 = 0
