asm LetRule03
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer
		
definitions:

macro rule r_m1($w in Integer, $z in Integer) =
    let ($x = $w, $y=$x+$z) in skip endlet

main rule r_main = 
	let ($x = 0, $y = 1) in
	    r_m1[$x, $y]
	endlet
