asm inconsistentUpdateEx
import ../libraries/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
		
definitions:	                 	
main rule r_main = 
par
	f(13) := 8 - 7
	f(13) := 7 - 8
endpar