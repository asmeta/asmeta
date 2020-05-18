// using and comparing strings with functions 
asm StringExpr03
import  ../../STDL/StandardLibrary
	
signature:

   abstract domain People
   
   dynamic domain Student subsetof People

   controlled name: People -> String   
   
   static me: Student
		
definitions:	                 	
main rule r_main = skip

default init s0:
    function name($e in People) = at({me->"angelo"},$e)