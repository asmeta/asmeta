asm ForRule02
import ../../STDL/StandardLibrary

signature:
controlled f: Integer
		
definitions:

main rule r_main = 
	forall $x in {1:2}, $y in {8 : 9} with $x + $y = 9 do
		f := 1
