asm LetTerm02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer
		
definitions:

macro rule r_foo($w in Integer, $z in Integer) =
	f := 
		let ($x = $w) in 
			let ($y = $z) in 
				$x + $y
			endlet
		endlet

main rule r_main =
		let ($x = 11, $y = 22) in
			r_foo[$x , $y]
		endlet
