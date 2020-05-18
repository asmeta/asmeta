asm LetRule01
import ../../STDL/StandardLibrary
	
signature:
controlled f : Integer 
		
definitions:

main rule r_main = 
	let ($x1 = 9, $x2 = $x1 + 1) in 
		f := $x1 * $x2
	endlet
