asm SetCt01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Powerset(Integer)
		
definitions:
main rule r_main =
	let ($x = 36) in
		f := {$y in {1..36} | $x mod $y = 0 : $y} 
	endlet
