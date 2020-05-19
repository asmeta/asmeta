asm Counter01
import ../../STDL/StandardLibrary
	
signature:
	controlled counter : Integer 
		
definitions:
main rule r_main = counter := counter + 25

default init s1:   
	function counter = 100
