asm SetCt03
import ../../STDL/StandardLibrary
	
signature:
controlled f: Powerset(Integer)
		
definitions:

macro rule r_foo($w in Integer, $z in Integer) =
	f := {$x in {10:17} | $x > $z : $x + $w}

main rule r_main =
		let ($x = 5, $y = 10) in
			r_foo[$x , $y]
		endlet

