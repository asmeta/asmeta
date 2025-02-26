asm ArithmeticExpr02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Natural
		
definitions:	                 	
main rule r_main = f := iton((2n + 1n + 6n * 7n) - 5n)
