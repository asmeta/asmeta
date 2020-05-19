asm ChooseRule02
import ../../STDL/StandardLibrary

signature:
controlled f: Prod(Integer,Integer)->Boolean
		
definitions:

main rule r_main = 
	choose $x in {1, 2, 3}, $y in {4, 5, 6} with $x = 2 and $y = 5 do
		f($x,$y) := true
