asm asmMulti 
import StandardLibrary 

signature:
 
	monitored myinput: Integer //unbound input 4
	out funcMulti: Integer 
	
definitions: 
	main rule r_Main = 
	funcMulti := myinput *2