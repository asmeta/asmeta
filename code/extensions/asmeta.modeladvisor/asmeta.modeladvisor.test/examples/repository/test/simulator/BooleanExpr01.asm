asm BooleanExpr01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Boolean
		
definitions:	                 	
main rule r_main = f := (true and false) implies true iff false
