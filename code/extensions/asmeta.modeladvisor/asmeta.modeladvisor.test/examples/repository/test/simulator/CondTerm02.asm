asm CondTerm02
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
definitions:

macro rule r_m1($z in Integer, $x in Integer, $y in Integer) =
	f($z) := if $x < $y then $x else $y endif

main rule r_main =
	let ($x = 11, $y = 2 * $x, $z = if $x > $y then $x else $y endif) in
		r_m1[$z mod 7, $y, $x]
	endlet

