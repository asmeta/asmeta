// using and comparing strings with functions 
asm StringExpr02
import  ../../STDL/StandardLibrary
	
signature:

   abstract domain People

   controlled name: People -> String   
   
   static me: People
		
definitions:	                 	
main rule r_main = skip

default init s0:
    function name($e in People) = at({me->"angelo"},$e)