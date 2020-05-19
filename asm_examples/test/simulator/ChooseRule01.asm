asm ChooseRule01
import ../../STDL/StandardLibrary

signature:
controlled f: Integer
		
definitions:

main rule r_main = 
	choose $x in {1, 7, 57, 20, 99} with $x mod 10 = 0 do
		f := $x
	ifnone f := 0
