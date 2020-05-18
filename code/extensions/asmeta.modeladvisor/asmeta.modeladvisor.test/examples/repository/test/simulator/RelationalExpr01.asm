asm RelationalExpr01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Boolean
		
definitions:	                 	
main rule r_main = f := not 1 = 2 and 4 != 3 and 7 < 9 and not 9 > 19
