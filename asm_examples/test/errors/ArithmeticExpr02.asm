// this shoudl give an error because with . the natural becomes inetger
asm ArithmeticExpr02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Natural
		
definitions:	                 	
main rule r_main = f := (2n + 1n + 6n * 7n) - 5n
