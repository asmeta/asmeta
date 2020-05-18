asm ArithmeticExpr01
import  ../../STDL/StandardLibrary
	
signature:
controlled f: Integer
		
definitions:	                 	
main rule r_main = f := (((1 * 2 * 3 * 4 - 10) - 4) * 19 + 9) mod 11
