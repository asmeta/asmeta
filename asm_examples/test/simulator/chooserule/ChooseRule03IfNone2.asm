asm ChooseRule03IfNone2
import  ../../../STDL/StandardLibrary

signature:
controlled f: Integer
		
definitions:

main rule r_main = 
	choose $x in {1, 2, 3} with false do
		f := 1
	ifnone
		f := 0
