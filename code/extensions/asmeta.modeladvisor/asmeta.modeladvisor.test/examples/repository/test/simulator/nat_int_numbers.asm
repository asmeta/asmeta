asm nat_int_numbers	
import   ../../STDL/StandardLibrary
	
signature:
controlled f: Natural
controlled f1: Natural
controlled f2: Integer 
controlled f3: Integer
		
definitions:	                 	
main rule r_main = 
    par
       //f := + 2n  Error, locating the unary plus on Natural. It doesn't make sense! 
       //f1:= + 2  Error, + is taken as unary plus and 2 is integer
       f2 := + 2 //OK!
       f1 := 0n //OK!  
       f3 := 0 //OK!
    endpar