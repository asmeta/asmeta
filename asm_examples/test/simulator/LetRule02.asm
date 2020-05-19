asm LetRule02
import ../../STDL/StandardLibrary
	
signature:
controlled f : Integer 
controlled g : Integer 
controlled z : Integer 
		
definitions:

main rule r_main = 
	let ($x1 = 9) in
		par
			f := $x1
			let ($x3 = 99, $x2 = 1) in 
				g := $x3 + $x2
			endlet
			z := $x1 * 7
		endpar
	endlet
