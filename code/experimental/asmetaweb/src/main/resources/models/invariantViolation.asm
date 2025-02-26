asm invariantViolation
import ../libraries/StandardLibrary
	
signature:
controlled f: Integer
		
		
definitions:	                 	
invariant over f: f <= 1 
main rule r_main = 
	f :=  8
	
default init s0:
	function f = 0
