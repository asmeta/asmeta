asm ForRule01
import ../../STDL/StandardLibrary

signature:
controlled f: Prod(Integer,Integer)->Integer
		
definitions:

main rule r_main = 
	forall $x in {1 : 3} with $x mod 2 = 1 do
		forall $y in {5 : 9} with $y mod 2 = 0 do
			f($x,$y) := $x * $y
