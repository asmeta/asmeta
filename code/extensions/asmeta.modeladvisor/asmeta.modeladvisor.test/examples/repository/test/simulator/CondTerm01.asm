asm CondTerm01
import ../../STDL/StandardLibrary
	
signature:
controlled f: Integer->Integer
		
definitions:
main rule r_main =
	let ($x = 11, $y = 2 * $x, $z = if $x > $y then $x else $y endif) in
		f($z) := if $x < $y then $x else $y endif
	endlet
		
