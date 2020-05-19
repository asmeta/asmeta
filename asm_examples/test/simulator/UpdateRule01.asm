asm UpdateRule01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
definitions:	                 	
main rule r_main = 
	f(13) := 7 - 8
