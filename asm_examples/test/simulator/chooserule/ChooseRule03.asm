asm ChooseRule03
import  ../../../STDL/StandardLibrary

signature:
controlled f: Integer
		
definitions:

main rule r_main = 
	choose $x in {1, 2, 3} with $x mod 3 = 1 do
		choose $y in {4, 5, 6} with $x = 2 and $y = 5 do
			f := 1
